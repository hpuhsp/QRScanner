/*
 * Copyright (C) 2008 ZXing authors
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
package com.hnsh.scanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.hnsh.scanner.decode.DecodeHandler;
import com.hnsh.scanner.decode.DecodeThread;
import com.hnsh.scanner.zbarUtils.Constants;
import com.hnsh.scanner.zbarUtils.camera.CameraManager;
import com.hnsh.scanner.zbarUtils.utils.BeepManager;
import com.hnsh.scanner.zbarUtils.utils.InactivityTimer;
import com.hnsh.scanner.zbarUtils.utils.QRCodeParseUtils;
import com.hnsh.scanner.zbarUtils.utils.ReplacerUtils;
import com.hnsh.scanner.zbarUtils.utils.FileUtils;
import com.hnsh.scanner.zbarUtils.utils.ThreadUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * This activity opens the camera and does the actual scanning on a background
 * thread. It draws a viewfinder to help the user place the barcode correctly,
 * shows feedback as the image processing is happening, and then overlays the
 * results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    private static final int OPEN_ALBUM_REQUEST_CODE = 1091;

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;

    private SurfaceView scanPreview = null;
    private RelativeLayout scanContainer;
    private FrameLayout scanCropView;
    private ImageView scanLightOpt;

    private ImageView scanRotate1;
    private ImageView scanRotate2;
    private ImageView scanRotate3;
    private ImageView imgCancle;
    private ImageView btnOpenAlbum;

    private Rect mCropRect = null;
    private TextView scanDsc;

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private boolean isHasSurface = false;
    private static final String ALBUM_PICKER = "album_picker";

    /**
     * 外部调用，以ForResult方式返回扫码结果
     *
     * @param activity
     * @param requestCode
     */
    public static void start(FragmentActivity activity, int requestCode, boolean albumPicker) {
        Intent intent = new Intent(activity, CaptureActivity.class);
        intent.putExtra(ALBUM_PICKER, albumPicker);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 外部调用，以ForResult方式返回扫码结果
     *
     * @param activity
     * @param requestCode
     */
    public static void start(FragmentActivity activity, int requestCode) {
        Intent intent = new Intent(activity, CaptureActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 外部调用，以ForResult方式返回扫码结果
     *
     * @param fragment
     * @param requestCode
     */
    public static void start(Fragment fragment, int requestCode, boolean albumPicker) {
        Intent intent = new Intent(fragment.getContext(), CaptureActivity.class);
        intent.putExtra(ALBUM_PICKER, albumPicker);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 外部调用，以ForResult方式返回扫码结果
     *
     * @param fragment
     * @param requestCode
     */
    public static void start(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), CaptureActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置为全屏模式
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_capture);
        initView();
        clearCache();
    }

    private void initView() {
        scanPreview = findViewById(R.id.capture_preview);
        scanContainer = findViewById(R.id.capture_container);
        scanCropView = findViewById(R.id.capture_crop_view);

        scanLightOpt = findViewById(R.id.capture_restart_scan);
        btnOpenAlbum = findViewById(R.id.btn_open_album);
        scanDsc = findViewById(R.id.tv_scan_dsc);

        scanRotate1 = findViewById(R.id.capture_scan_rotate_1);
        scanRotate2 = findViewById(R.id.capture_scan_rotate_2);
        scanRotate3 = findViewById(R.id.capture_scan_rotate_3);

        imgCancle = findViewById(R.id.img_cancel);
        imgCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.scanner_fade_in, R.anim.scanner_fade_out);
            }
        });

        // 打开照明
        scanLightOpt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    Toast.makeText(CaptureActivity.this, getString(R.string.scanner_no_flash), Toast.LENGTH_LONG).show();
                } else {
                    toggleLight();
                }
            }
        });

        // 打开相册
        btnOpenAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });
        btnOpenAlbum.setVisibility(getIntent().getBooleanExtra(ALBUM_PICKER, false) ? View.VISIBLE : View.GONE);

        initScannerViews();
    }

    private void initScannerViews() {
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        Animation operatingAnim1 = AnimationUtils.loadAnimation(this, R.anim.barcode_rotate_1);
        Animation operatingAnim2 = AnimationUtils.loadAnimation(this, R.anim.barcode_rotate_2);
        Animation operatingAnim3 = AnimationUtils.loadAnimation(this, R.anim.barcode_rotate_3);

        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim1.setInterpolator(lin);
        operatingAnim2.setInterpolator(lin);
        operatingAnim3.setInterpolator(lin);

        scanRotate1.startAnimation(operatingAnim1);
        scanRotate2.startAnimation(operatingAnim2);
        scanRotate3.startAnimation(operatingAnim3);

        String textDsc = getString(R.string.scanner_scan_code_dsc);
        String keyWord = getString(R.string.scanner_scan_code_key_word);
        scanDsc.setText(ReplacerUtils.matcherMoreKeywordtLight(this, textDsc, new String[]{keyWord}));
    }

    /**
     * 打开系统相册
     */
    private void openAlbum() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, OPEN_ALBUM_REQUEST_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is
        // necessary because we don't
        // want to open the camera driver and measure the screen size if we're
        // going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the
        // wrong size and partially
        // off screen.
        cameraManager = new CameraManager(getApplication());

        handler = null;

        if (isHasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(scanPreview.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            scanPreview.getHolder().addCallback(this);
        }

        inactivityTimer.onResume();
    }

    @Override
    public void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_ALBUM_REQUEST_CODE && resultCode == RESULT_OK) { // 打开相册
            if (null != data && null != data.getData()) {
                String path = FileUtils.getRealPathFromUri(CaptureActivity.this, data.getData());
                if (null != path && !path.isEmpty()) {
                    new QrCodeAsyncTask(this, path, data.getData()).execute(path);
                } else {
                    Toast.makeText(CaptureActivity.this, "获取图片路径失败！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CaptureActivity.this, "获取图片路径失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param message decode data
     */
    public void handleDecode(Message message) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();
        backForScannerResult(message);
    }

    /**
     * 返回扫描结果
     */
    private void backForScannerResult(Message message) {
        scanRotate1.clearAnimation();
        scanRotate2.clearAnimation();
        scanRotate3.clearAnimation();

        Intent intent = new Intent();
        Bundle bundle = message.getData();
        if (null != bundle) {
            intent.putExtra(Constants.SCAN_BAR_CODE_RESULT, bundle.getString(DecodeHandler.BAR_CODE_KEY));
            intent.putExtra(Constants.SCAN_RESULT_DATA, bundle);
        }

        Message targetMessage = Message.obtain(handler, R.id.return_scan_result, intent);
        targetMessage.sendToTarget();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager, DecodeThread.ALL_MODE);
            }

            initCrop();

        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        // camera error
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.scanner_prompt));
        builder.setMessage(getString(R.string.scanner_open_camera_fail));
        builder.setPositiveButton(getString(R.string.scanner_confirm), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean flag = false;

    /**
     * 切换闪光灯状态
     */
    protected void toggleLight() {
        if (!flag) {
            flag = true;
            // 开闪光灯
            cameraManager.openLight();
            scanLightOpt.setSelected(true);
        } else {
            flag = false;
            // 关闪光灯
            cameraManager.offLight();
            scanLightOpt.setSelected(flag);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.scanner_fade_in, R.anim.scanner_fade_out);
    }

    /**
     * AsyncTask 静态内部类，防止内存泄漏
     */
    static class QrCodeAsyncTask extends AsyncTask<String, Integer, String> {
        private WeakReference<CaptureActivity> mWeakReference;
        private String path;
        private Uri uri;

        public QrCodeAsyncTask(CaptureActivity activity, String path, Uri uri) {
            mWeakReference = new WeakReference<CaptureActivity>(activity);
            this.path = path;
            this.uri = uri;
        }

        @Override
        protected String doInBackground(String... strings) {
            return QRCodeParseUtils.decodeQRCodeByPath(path);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Message message = Message.obtain(mWeakReference.get().handler, R.id.decode_succeeded, OPEN_ALBUM_REQUEST_CODE);
            Bundle bundle = new Bundle();
            bundle.putString(DecodeHandler.BAR_CODE_KEY, null == s ? "" : s);
            bundle.putString(DecodeHandler.ALBUM_PIC_KEY, path);
            message.setData(bundle);
            message.sendToTarget();
        }
    }

    /**
     * 清楚本地缓存
     */
    private void clearCache() {
        ThreadUtils.executeByIo(new ThreadUtils.Task<String>() {
            @Override
            public String doInBackground() throws Throwable {
                FileUtils.deleteAllInDir(CaptureActivity.this);
                return null;
            }

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onFail(Throwable t) {

            }
        });
    }
}