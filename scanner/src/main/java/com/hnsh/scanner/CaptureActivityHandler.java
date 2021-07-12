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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hnsh.scanner.decode.DecodeHandler;
import com.hnsh.scanner.decode.DecodeThread;
import com.hnsh.scanner.zbarUtils.camera.CameraManager;

import java.lang.ref.WeakReference;

public class CaptureActivityHandler extends Handler {
    private WeakReference<CaptureActivity> activity;

    private final DecodeThread decodeThread;
    private final CameraManager cameraManager;
    private State state;


    private enum State {
        PREVIEW, SUCCESS, DONE
    }

    public CaptureActivityHandler(CaptureActivity activity, CameraManager cameraManager, int decodeMode) {
        this.activity = new WeakReference<CaptureActivity>(activity);
        decodeThread = new DecodeThread(activity, decodeMode);
        decodeThread.start();
        state = State.SUCCESS;

        // Start ourselves capturing previews and decoding.
        this.cameraManager = cameraManager;
        cameraManager.startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(Message message) {
        if (message.what == R.id.restart_preview) {
            restartPreviewAndDecode();
        } else if (message.what == R.id.decode_succeeded) {
            state = State.SUCCESS;
            Bundle bundle = message.getData();
            activity.get().handleDecode(bundle.getString(DecodeHandler.BAR_CODE_KEY), bundle);
        } else if (message.what == R.id.decode_failed) {// We're decoding as fast as possible, so when one decode fails,
            // start another.
            state = State.PREVIEW;
            cameraManager.requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
        } else if (message.what == R.id.return_scan_result) {
            activity.get().setResult(Activity.RESULT_OK, (Intent) message.obj);
            activity.get().finish();
            activity.get().overridePendingTransition(R.anim.scanner_fade_in, R.anim.scanner_fade_out);
        }
    }

    public void quitSynchronously() {
        state = State.DONE;
        cameraManager.stopPreview();
        Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
        quit.sendToTarget();
        try {
            // Wait at most half a second; should be enough time, and onPause()
            // will timeout quickly
            decodeThread.join(500L);
        } catch (InterruptedException e) {
            // continue
        }

        // Be absolutely sure we don't send any queued up messages
        removeMessages(R.id.decode_succeeded);
        removeMessages(R.id.decode_failed);
    }

    private void restartPreviewAndDecode() {
        if (state == State.SUCCESS) {
            state = State.PREVIEW;
            cameraManager.requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
        }
    }
}
