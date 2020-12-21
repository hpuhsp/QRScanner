package com.hnsh.scanner.zbarUtils.utils;

import android.app.ProgressDialog;

/**
 * Created by John on 15/11/24.
 */
public interface DialogControl {

    public abstract void hideWaitDialog();

    public abstract ProgressDialog showWaitDialog();

    public abstract ProgressDialog showWaitDialog(int resid);

    public abstract ProgressDialog showWaitDialog(String text);
}
