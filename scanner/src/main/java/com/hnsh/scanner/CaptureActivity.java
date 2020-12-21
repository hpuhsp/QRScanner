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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.hnsh.scanner.decode.DecodeThread;
import com.hnsh.scanner.zbarUtils.Constants;
import com.hnsh.scanner.zbarUtils.camera.CameraManager;
import com.hnsh.scanner.zbarUtils.utils.BeepManager;
import com.hnsh.scanner.zbarUtils.utils.InactivityTimer;
import com.hnsh.scanner.zbarUtils.utils.ReplacerUtils;

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

    private Rect mCropRect = null;
    private TextView scanDsc;

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private boolean isHasSurface = false;

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
     * @param type
     */
    public static void start(Fragment fragment, int type) {
        Intent intent = new Intent(fragment.getContext(), CaptureActivity.class);
        fragment.startActivityForResult(intent, type);
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
//        mBarCodeCheckCode = getIntent().getIntExtra(Constants.CHECK_BAR_FOR_RESULT_CODE, -1);
        findViewById();
        addEvents();
        initViews();
    }

    private void findViewById() {
        scanPreview = findViewById(R.id.capture_preview);
        scanContainer = findViewById(R.id.capture_container);
        scanCropView = findViewById(R.id.capture_crop_view);

        scanLightOpt = findViewById(R.id.capture_restart_scan);
        scanDsc = findViewById(R.id.tv_scan_dsc);

        scanRotate1 = findViewById(R.id.capture_scan_rotate_1);
        scanRotate2 = findViewById(R.id.capture_scan_rotate_2);
        scanRotate3 = findViewById(R.id.capture_scan_rotate_3);

        imgCancle = findViewById(R.id.img_cancle);
        imgCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.scanner_fade_in, R.anim.scanner_fade_out);
            }
        });

    }

    private void addEvents() {
        scanLightOpt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    Toast.makeText(CaptureActivity.this, getString(R.string.scanner_no_flash), Toast.LENGTH_LONG).show();
                } else {
                    toggleLight();
                }
            }
        });
    }

    private void initViews() {
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
//        scanDsc.setText(ReplacerUtils.makeLightKeyWord(keyWord, textDsc, this));
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
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(final String rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();

//        handler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                handleText(rawResult);
//            }
//        }, 500);

        handleText(rawResult);
    }

    private void handleText(String text) {
        scanRotate1.clearAnimation();
        scanRotate2.clearAnimation();
        scanRotate3.clearAnimation();

        Intent intent = new Intent();
        intent.putExtra(Constants.SCAN_BAR_CODE_RESULT, text);
        setResult(Activity.RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.scanner_fade_in, R.anim.scanner_fade_out);
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
}