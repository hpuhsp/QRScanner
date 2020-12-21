//package com.hnsh.scanner.zbarUtils.utils;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.text.Html;
//import android.text.TextUtils;
//import android.view.Gravity;
//import android.view.View;
//
//import com.hnsh.core.widget.CustomProgressDialog;
//import com.hnsh.scanner.R;
////
////import com.jiuhuar.android.R;
////import com.jiuhuar.android.ui.widgets.CustomRewardRuleDailog;
////import com.jiuhuar.android.ui.widgets.MyLoadingDialog;
////import com.jiuhuar.android.ui.widgets.dialog.flydialog.MyCustomDialog;
////import com.jiuhuar.android.ui.widgets.dialog.flydialog.NormalDialog;
////import com.jiuhuar.android.ui.widgets.dialog.flydialog.NormalListDialog;
////import com.jiuhuar.android.ui.widgets.dialog.listener.OnMyItemClick;
////import com.jiuhuar.media.tencent.dialog.CustomProgressDialog;
//
///**
// * Function:
// * <br/>
// * Describe: Dialog工具类
// * <br/>
// * Create: 2018/5/21
// * Author: reese
// * <br/>
// * Email: reese@jiuhuar.com
// */
//public class DialogUtils {
//    /**
//     * 一些原生AlterDialog的组成方法
//     */
//
//    /***
//     * 获取一个耗时等待对话框
//     *
//     * @param context
//     * @param message
//     * @return
//     */
//    public static ProgressDialog getWaitDialog(Context context, String message) {
//        ProgressDialog waitDialog = new ProgressDialog(context);
//        if (!TextUtils.isEmpty(message)) {
//            waitDialog.setMessage(message);
//        }
//        return waitDialog;
//    }
//
//    /***
//     * 获取一个信息对话框，注意需要自己手动调用show方法显示
//     *
//     * @param context
//     * @param message
//     * @param onClickListener
//     * @return
//     */
//    public static AlertDialog.Builder getMessageDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
//        AlertDialog.Builder builder = getDialog(context);
//        builder.setMessage(message);
//        builder.setPositiveButton(context.getResources().getString(R.string.confirm), onClickListener);
//        return builder;
//    }
//
//    public static AlertDialog.Builder getMessageDialog(Context context, String message) {
//        return getMessageDialog(context, message, null);
//    }
//
//    /***
//     * 获取一个信息对话框，注意需要自己手动调用show方法显示
//     *
//     * @param context
//     * @param message
//     * @param onClickListener
//     * @return
//     */
//    public static AlertDialog.Builder getSettingMessageDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
//        AlertDialog.Builder builder = getDialog(context);
//        builder.setMessage(message);
//        builder.setPositiveButton(context.getResources().getString(R.string.setting), onClickListener);
//        builder.setNegativeButton(context.getResources().getString(R.string.cancel), null);
//        return builder;
//    }
//
//
//    /***
//     * 提示对话框
//     *
//     * @param context
//     * @param message
//     * @param onClickListener
//     * @return
//     */
//    public static AlertDialog.Builder getMessageCloseDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
//        AlertDialog.Builder builder = getDialog(context);
//        if (!TextUtils.isEmpty(title)) {
//            builder.setTitle(title);
//        }
//        builder.setMessage(message);
//        builder.setPositiveButton(context.getResources().getString(R.string.close), onClickListener);
//        return builder;
//    }
//
//    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
//        AlertDialog.Builder builder = getDialog(context);
//        builder.setMessage(Html.fromHtml(message));
//        builder.setPositiveButton(context.getResources().getString(R.string.confirm), onClickListener);
//        builder.setNegativeButton(context.getResources().getString(R.string.cancel), null);
//        return builder;
//    }
//
//    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onOkClickListener, DialogInterface.OnClickListener onCancleClickListener) {
//        AlertDialog.Builder builder = getDialog(context);
//        builder.setMessage(message);
//        builder.setPositiveButton(context.getResources().getString(R.string.confirm), onOkClickListener);
//        builder.setNegativeButton(context.getResources().getString(R.string.cancel), onCancleClickListener);
//        return builder;
//    }
//
//    public static AlertDialog.Builder getSelectDialog(Context context, String title, String[] arrays, DialogInterface.OnClickListener onClickListener) {
//        AlertDialog.Builder builder = getDialog(context);
//        builder.setItems(arrays, onClickListener);
//        if (!TextUtils.isEmpty(title)) {
//            builder.setTitle(title);
//        }
//        builder.setPositiveButton(context.getResources().getString(R.string.cancel), null);
//        return builder;
//    }
//
//    public static AlertDialog.Builder getItemSelectDialog(Context context, String[] arrays, DialogInterface.OnClickListener onClickListener) {
//        AlertDialog.Builder builder = getDialog(context);
//        builder.setItems(arrays, onClickListener);
//        return builder;
//    }
//
//    public static NormalListDialog getAnimItemSelectDialog(Context context, String[] arrays, OnMyItemClick clickL) {
//        NormalListDialog dialog = DialogUtils.getAnimArrayDialog(context, arrays);
//        return dialog;
//    }
//
//    public static AlertDialog.Builder getSelectDialog(Context context, String[] arrays, DialogInterface.OnClickListener onClickListener) {
//        return getSelectDialog(context, "", arrays, onClickListener);
//    }
//
//    /**
//     * 获取原生列表Dialog
//     *
//     * @param context
//     * @param title
//     * @param arrays
//     * @param selectIndex
//     * @param onClickListener
//     * @return
//     */
//    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String title, String[] arrays, int selectIndex, DialogInterface.OnClickListener onClickListener) {
//        AlertDialog.Builder builder = getDialog(context);
//        builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
//        if (!TextUtils.isEmpty(title)) {
//            builder.setTitle(title);
//        }
//        builder.setNegativeButton(context.getResources().getString(R.string.cancel), null);
//        return builder;
//    }
//
//    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String[] arrays, int selectIndex, DialogInterface.OnClickListener onClickListener) {
//        return getSingleChoiceDialog(context, "", arrays, selectIndex, onClickListener);
//    }
//
//    /***
//     * 获取一个普通Dialog
//     *
//     * @param context
//     * @return
//     */
//    public static AlertDialog.Builder getDialog(Context context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        return builder;
//    }
//
//    /***
//     * 获取原生进度对话框
//     *
//     * @param context
//     * @param message
//     * @return
//     */
//    public static ProgressDialog getWaitingDialog(Context context, String message) {
//        ProgressDialog waitDialog = new ProgressDialog(context);
//        if (!TextUtils.isEmpty(message)) {
//            waitDialog.setMessage(message);
//        }
//
//        return waitDialog;
//    }
//
//    /**
//     *======================一些自定义Dialog的调用=======>
//     */
//
//    /**
//     * 获取自定义dialog--不建议使用
//     *
//     * @param context
//     * @return
//     */
//    public static MyCustomDialog getMyCustomDialog(Context context) {
//        MyCustomDialog dialog = new MyCustomDialog(context, R.style.dialog_untran);
//        return dialog;
//    }
//
//    /**
//     * 获取自定义dialog
//     *
//     * @param context
//     * @return
//     */
//    public static CustomRewardRuleDailog getRewardRuleDailog(Context context) {
//        CustomRewardRuleDailog dialog = new CustomRewardRuleDailog(context);
//        return dialog;
//    }
//
//    /**
//     * 获取一个列表样式的Dialog
//     *
//     * @param context
//     * @param array
//     * @return
//     */
//    public static NormalListDialog getAnimArrayDialog(Context context, String[] array) {
//        NormalListDialog dialog = new NormalListDialog(context, array);
//        return dialog;
//    }
//
//    /**
//     * 获取列表菜单Dialog~item不执行动画
//     *
//     * @param context
//     * @param array
//     * @param hasAnim
//     * @param title
//     * @return
//     */
//    public static NormalListDialog getNonAnimArrayDialog(Context context, String[] array, boolean hasAnim, String title) {
//        NormalListDialog dialog = new NormalListDialog(context, array);
//        dialog.layoutAnimation(null)
//                .itemTextSize(16f)
//                .dividerHeight(0.3f)
//                .cornerRadius(12f)
//                .dividerColor(ResourcesUtil.getColor(context, R.color.divide_line))
//                .lvBgColor(ResourcesUtil.getColor(context, R.color.white));
//        if (!TextUtils.isEmpty(title)) {
//            dialog.isTitleShow(true)
//                    .title(title)
//                    .titleTextColor(ResourcesUtil.getColor(context, R.color.green))
//                    .titleBgColor(ResourcesUtil.getColor(context, R.color.white));
//        } else {
//            dialog.isTitleShow(false);
//        }
//        return dialog;
//    }
//
//    /**
//     * 获取菊花转进度等待Dialog
//     *
//     * @param context
//     * @param desc
//     * @return
//     */
//    public static MyLoadingDialog getMyLoadingDialog(Context context, String desc) {
//        MyLoadingDialog.Builder builder = new MyLoadingDialog.Builder(context)
//                .setShowMessage(false)
//                .setCancelable(false)
//                .setShowMessage(true)
//                .setMessage(!TextUtils.isEmpty(desc) ? desc : ResourcesUtil.getString(context, R.string.msg_load_ing));
//        return builder.create();
//    }
//
//    /**
//     * 获取2个按钮确认对话框-NormalDialog
//     *
//     * @param context
//     * @param title
//     * @return
//     */
//    public static NormalDialog getConfirmNormalDialog(Context context, String title, String content, boolean showTitle) {
//        NormalDialog normalDialog = new NormalDialog(context);
//        normalDialog
//                .title(title)
//                .isTitleShow(showTitle)
//                .btnNum(2)
//                .btnText(ResourcesUtil.getString(context, R.string.cancel), ResourcesUtil.getString(context, R.string.confirm))
//                .btnTextColor(ResourcesUtil.getColor(context, R.color.green), ResourcesUtil.getColor(context, R.color.green))
//                .btnTextSize(16, 16)
//                .content(content)
//                .contentGravity(Gravity.CENTER)
//                .contentTextColor(ResourcesUtil.getColor(context, R.color.text_black))
//                .contentTextSize(16)
//                .cornerRadius(12f);
//        return normalDialog;
//    }
//
//    /**
//     * 获取单个按钮的确认对话框---无标题
//     *
//     * @param context
//     * @param content
//     * @param btnStr
//     * @return
//     */
//    public static NormalDialog getSingleConfirmDialog(Context context, String title, String content, String btnStr) {
//        NormalDialog normalDialog = new NormalDialog(context);
//        normalDialog.btnNum(1)
//                .btnText(btnStr)
//                .btnTextColor(ResourcesUtil.getColor(context, R.color.green))
//                .btnTextSize(16)
//                .content(content)
//                .contentGravity(Gravity.CENTER)
//                .contentTextColor(ResourcesUtil.getColor(context, R.color.text_black))
//                .contentTextSize(16)
//                .cornerRadius(12f);
//
//        if (!TextUtils.isEmpty(title)) {
//            normalDialog.title(title)
//                    .titleTextColor(ResourcesUtil.getColor(context, R.color.text_black))
//                    .titleTextSize(16)
//                    .isTitleShow(true)
//                    .isTitleLineShow(false);
//        } else {
//            normalDialog.isTitleShow(false);
//            normalDialog.isTitleLineShow(false);
//        }
//
//        return normalDialog;
//    }
//
//    /**
//     * 获取确认对话框
//     *
//     * @param context
//     * @param title
//     * @param color   按键颜色
//     * @return
//     */
//    public static NormalDialog getConfirmNormalDialog(Context context, String title, String content, int color) {
//        NormalDialog normalDialog = new NormalDialog(context);
//        normalDialog
//                .btnNum(2)
//                .btnText(ResourcesUtil.getString(context, R.string.cancel), ResourcesUtil.getString(context, R.string.confirm))
//                .btnTextColor(color, color)
//                .btnTextSize(16, 16)
//                .content(content)
//                .contentGravity(Gravity.CENTER)
//                .contentTextColor(ResourcesUtil.getColor(context, R.color.text_black))
//                .contentTextSize(16)
//                .cornerRadius(12f);
//        if (!TextUtils.isEmpty(title)) {
//            normalDialog.title(title);
//            normalDialog.isTitleShow(true);
//            normalDialog.isTitleLineShow(true);
//        } else {
//            normalDialog.isTitleShow(false);
//            normalDialog.isTitleLineShow(false);
//        }
//
//        return normalDialog;
//    }
//
//    /**
//     * 获取编辑等相关页面回退操作对话框
//     *
//     * @param context
//     * @return
//     */
//    public static NormalDialog getBackConfirmDialog(Context context) {
//        NormalDialog normalDialog = new NormalDialog(context);
//        normalDialog.isTitleShow(false)
//                .btnNum(2)
//                .btnText(ResourcesUtil.getString(context, R.string.cancel), ResourcesUtil.getString(context, R.string.confirm))
//                .btnTextColor(ResourcesUtil.getColor(context, R.color.green), ResourcesUtil.getColor(context, R.color.green))
//                .btnTextSize(16, 16)
//                .content(ResourcesUtil.getString(context, R.string.is_sure_to_leave_edit))
//                .contentGravity(Gravity.CENTER)
//                .contentTextColor(ResourcesUtil.getColor(context, R.color.text_black))
//                .contentTextSize(16)
//                .cornerRadius(12f);
//        return normalDialog;
//    }
//
//    /**
//     * 获取自定义按钮颜色、按钮文案的对话框---没有标题且为2个按钮的风格
//     *
//     * @param context
//     * @param leftColor  左边按钮颜色
//     * @param rightColor 右边按钮颜色
//     * @param btnText    按钮文案
//     * @return
//     */
//    public static NormalDialog getCustomBtnConfirmDialog(Context context, String content, int leftColor, int rightColor, String... btnText) {
//        NormalDialog normalDialog = new NormalDialog(context);
//        normalDialog.isTitleShow(false)
//                .btnNum(2)
//                .btnText(btnText[0], btnText[1])
//                .btnTextColor(leftColor, rightColor)
//                .btnTextSize(16, 16)
//                .content(content)
//                .contentGravity(Gravity.CENTER)
//                .contentTextColor(ResourcesUtil.getColor(context, R.color.text_black))
//                .contentTextSize(16)
//                .cornerRadius(12f);
//        return normalDialog;
//    }
//
//    /**
//     * 获取自定义按钮文字的对话框
//     *
//     * @param context
//     * @param content
//     * @param btnText
//     * @return
//     */
//    public static NormalDialog getConfirmDialogForBtnText(Context context, String content, String... btnText) {
//        NormalDialog normalDialog = new NormalDialog(context);
//        normalDialog.isTitleShow(false)
//                .btnNum(2)
//                .btnText(btnText[0], btnText[1])
//                .btnTextColor(ResourcesUtil.getColor(context, R.color.green), ResourcesUtil.getColor(context, R.color.green))
//                .btnTextSize(16, 16)
//                .content(content)
//                .contentGravity(Gravity.CENTER)
//                .contentTextColor(ResourcesUtil.getColor(context, R.color.text_black))
//                .contentTextSize(16)
//                .cornerRadius(12f);
//        return normalDialog;
//    }
//
//    /**
//     * 自定义-提示切换城市的对话框
//     */
//    public static NormalDialog getLocationChangeDialog(Activity activity, String cityName) {
//        String contentStr = ResourcesUtil.getString(activity, R.string.user_curremnt_city
//                , cityName + "");
//
//        NormalDialog dialog = new NormalDialog(activity);
//        dialog.setCanceledOnTouchOutside(false);
//
//        dialog.isTitleShow(true)
//                .title(ResourcesUtil.getString(activity, R.string.prompt))
//                .titleTextSize(16)
//                .titleTextStyle()
//                .titleTextColor(ResourcesUtil.getColor(activity, R.color.text_black))
//                .widthScale(0.75f)
//                .cornerRadius(8)
//                .content(contentStr)
//                .contentTextSize(16)
//                .contentTextColor(ResourcesUtil.getColor(activity, R.color.text_black))
//                .btnText(ResourcesUtil.getString(activity, R.string.cancel)
//                        , ResourcesUtil.getString(activity, R.string.change_str))
//                .btnTextColor(ResourcesUtil.getColor(activity, R.color.text_black_green_selector)
//                        , ResourcesUtil.getColor(activity, R.color.text_green_gray_selector));
//
//        return dialog;
//    }
//
//    /**
//     * 设置自定义内容区域的对话框
//     *
//     * @param context
//     * @param title
//     * @param contentView
//     * @return
//     */
//    public static NormalDialog getCustomContentDialog(Context context, String title, View contentView) {
//        NormalDialog normalDialog = new NormalDialog(context);
//        normalDialog
//                .customContentView(contentView)
//                .btnNum(2)
//                .btnText(ResourcesUtil.getString(context, R.string.cancel), ResourcesUtil.getString(context, R.string.confirm))
//                .btnTextColor(ResourcesUtil.getColor(context, R.color.green), ResourcesUtil.getColor(context, R.color.green))
//                .btnTextSize(16, 16)
//                .cornerRadius(12f);
//        // 设置自定义View
//        if (!TextUtils.isEmpty(title)) {
//            normalDialog
//                    .title(title)
//                    .isTitleShow(true)
//                    .titleGravity(Gravity.CENTER)
//                    .isTitleLineShow(true)
//                    .titleTextColor(ResourcesUtil.getColor(context, R.color.white))
//                    .titleTextSize(16)
//                    .titleBgColor(ResourcesUtil.getColor(context, R.color.green));
//        } else {
//            normalDialog.isTitleShow(false);
//        }
//        return normalDialog;
//    }
//
//    public static CustomProgressDialog getCustomProgressDialog(Context context) {
//        CustomProgressDialog customProgressDialog = new CustomProgressDialog();
//        customProgressDialog.createLoadingDialog(context, "");
//        customProgressDialog.setCancelable(true); // 设置是否可以通过点击Back键取消
//        customProgressDialog.setCanceledOnTouchOutside(false); // 设置在点击Dialog外是否取消Dialog进度条
//        return customProgressDialog;
//    }
//}