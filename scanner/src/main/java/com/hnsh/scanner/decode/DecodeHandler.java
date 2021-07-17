/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hnsh.scanner.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.hnsh.scanner.CaptureActivity;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import com.hnsh.scanner.R;
import com.hnsh.scanner.zbarUtils.utils.FileUtils;
import com.hnsh.scanner.zbarUtils.utils.ThreadUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class DecodeHandler extends Handler {
    private final WeakReference<CaptureActivity> activity;
    private boolean running = true;

    public static final String BAR_CODE_KEY = "bar_code_key";
    public static final String ALBUM_PIC_KEY = "album_pic_key";

    public DecodeHandler(CaptureActivity activity) {
        this.activity = new WeakReference<CaptureActivity>(activity);
    }

    @Override
    public void handleMessage(Message message) {
        if (!running) {
            return;
        }
        if (message.what == R.id.decode) {
            decode((byte[]) message.obj, message.arg1, message.arg2);
        } else if (message.what == R.id.quit) {
            running = false;
            Looper.myLooper().quit();
        }
    }

    /**
     * Decode the data within the viewfinder rectangle, and time how long it
     * took. For efficiency, reuse the same reader objects from one decode to
     * the next.
     *
     * @param data   The YUV preview frame.
     * @param width  The width of the preview frame.
     * @param height The height of the preview frame.
     */
    private void decode(byte[] data, int width, int height) {
        Size size = activity.get().getCameraManager().getPreviewSize();
        if (null != size) {
            // 这里需要将获取的data翻转一下，因为相机默认拿的的横屏的数据
            byte[] rotatedData = new byte[data.length];
            for (int y = 0; y < size.height; y++) {
                for (int x = 0; x < size.width; x++)
                    rotatedData[x * size.height + size.height - y - 1] = data[x + y * size.width];
            }

            // 宽高也要调整
            int tmp = size.width;
            size.width = size.height;
            size.height = tmp;
        }

        Rect rect = activity.get().getCropRect();
        final Image barcode = new Image(width, height, "Y800");
        barcode.setData(data);
        if (rect != null) {
            // zbar 解码库,不需要将数据进行旋转,因此设置裁剪区域是的x为 top, y为left,设置了裁剪区域,解码速度快了近5倍左右
            barcode.setCrop(rect.top, rect.left, rect.width(), rect.height());    // 设置截取区域，也就是你的扫描框在图片上的区域.
        }

        String resultQRcode = null;
        ImageScanner mImageScanner = new ImageScanner();
        // 若果Zbar 扫码不明确指定UPCA的话，默认使用EAN13解码，由于EAN13兼容UPCA，由于导致UPCA扫出来导致首位多出一个0
        mImageScanner.setConfig(Symbol.NONE, Config.ENABLE, 1);
        int result = mImageScanner.scanImage(barcode);
        if (result != 0) {
            SymbolSet symSet = mImageScanner.getResults();
            for (Symbol sym : symSet) {
                resultQRcode = sym.getData();
            }
        }

        Handler handler = activity.get().getHandler();
        if (!TextUtils.isEmpty(resultQRcode)) {
            // Don't log the barcode contents for security.
            if (handler != null) {
                final String decodeResult = resultQRcode;
                final int imgWidth = width;
                final int imgHeight = height;
                // 保存扫码的图片到本地
                ThreadUtils.executeByIo(new ThreadUtils.Task<String>() {
                    @Override
                    public String doInBackground() throws Throwable {
                        String imgPath = FileUtils.getTempPicPath(activity.get());
                        YuvImage yuvimage = new YuvImage(barcode.getData(), ImageFormat.NV21, imgWidth, imgHeight, null);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        yuvimage.compressToJpeg(new Rect(0, 0, imgWidth, imgHeight), 80, baos);
                        byte[] rectData = baos.toByteArray();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(rectData, 0, rectData.length);
                        // 旋转90度
                        Matrix matrix = new Matrix();
                        matrix.setRotate(90);
                        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, imgWidth, imgHeight, matrix, false);
                        FileOutputStream os = null;
                        try {
                            os = new FileOutputStream(new File(imgPath));
                            bmp.compress(Bitmap.CompressFormat.JPEG, 80, os);
                            if (!bmp.isRecycled()) bmp.recycle();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (os != null) {
                                    os.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return imgPath;
                    }

                    @Override
                    public void onSuccess(String result) {
                        decodeSuccess(decodeResult, result);
                    }

                    @Override
                    public void onCancel() {
                        decodeSuccess(decodeResult, "");
                    }

                    @Override
                    public void onFail(Throwable t) {
                        decodeSuccess(decodeResult, "");
                    }
                });
            }
        } else {
            if (handler != null) {
                Message message = Message.obtain(handler, R.id.decode_failed);
                message.sendToTarget();
            }
        }
    }

    private void decodeSuccess(String decodeResult, String imgPath) {
        Handler handler = activity.get().getHandler();
        Message message = Message.obtain(handler, R.id.decode_succeeded, decodeResult);
        Bundle bundle = new Bundle();
        bundle.putString(BAR_CODE_KEY, decodeResult);
        if (!TextUtils.isEmpty(imgPath)) {
            bundle.putString(ALBUM_PIC_KEY, imgPath);
        }
        message.setData(bundle);
        message.sendToTarget();
    }
}
