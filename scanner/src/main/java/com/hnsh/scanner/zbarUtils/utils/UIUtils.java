//package com.hnsh.scanner.zbarUtils.utils;
//
//import android.app.Activity;
//import android.app.Application;
//import android.content.ComponentCallbacks;
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Point;
//import android.graphics.Rect;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.text.Html;
//import android.text.InputType;
//import android.text.TextPaint;
//import android.util.DisplayMetrics;
//import android.view.Display;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.appcompat.widget.Toolbar;
//import java.io.InputStream;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
////import com.jiuhuar.android.ui.widgets.JHImageSpan;
//
///**
// * Created by John on 15/3/28.
// * View点击事件工具类
// */
//public final class UIUtils {
//
//    private final static int UPPER_LEFT_X = 0;
//    private final static int UPPER_LEFT_Y = 0;
//
//    private static long mLastClickTime = 0;
//
//    private static float sNoncompatDensity;
//    private static float sNoncompatScaledDensity;
//
//    private UIUtils() {
//
//    }
//
//    public static Drawable convertViewToDrawable(View view) {
//        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        view.measure(spec, spec);
//        view.layout(UPPER_LEFT_X, UPPER_LEFT_Y, view.getMeasuredWidth(), view.getMeasuredHeight());
//        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(b);
//        c.translate(-view.getScrollX(), -view.getScrollY());
//        view.draw(c);
//        view.setDrawingCacheEnabled(true);
//        Bitmap cacheBmp = view.getDrawingCache();
//        Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
//        cacheBmp.recycle();
//        view.destroyDrawingCache();
//        return new BitmapDrawable(viewBmp);
//    }
//
//    /**
//     * 判断view上的点击是不是快速多次点击
//     *
//     * @return
//     */
//    public static boolean isFastDoubleClick() {
//        long time = System.currentTimeMillis();
//        long timeT = time - mLastClickTime;
//        if (0 < timeT && timeT < 500) {
//            return true;
//        } else {
//            mLastClickTime = time;
//            return false;
//        }
//    }
//
//
//    /**
//     * 获取屏幕大小
//     *
//     * @param context
//     * @return
//     */
//    public static Point getDisplayPixelSize(Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        return size;
//    }
//
//
//    /**
//     * 设置全屏，隐藏状态栏
//     */
//    public static void hideStateBar(Activity activity) {
////        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    }
//
//
//    /**
//     * 获得屏幕宽度
//     * 返回像素
//     *
//     * @param context
//     * @return
//     */
//    public static int getScreenWidth(Context context) {
//        WindowManager wm = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(outMetrics);
//        return outMetrics.widthPixels;
//    }
//
//    /**
//     * 新创建
//     *
//     * @param context
//     */
//
//    public static float getScreendensity(Context context) {
//        WindowManager wm = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(outMetrics);
//        return outMetrics.density;
//    }
//
//
//    /**
//     * @param context
//     * @param width
//     */
//    public static void setScreenWidth(Context context, int width) {
//        WindowManager wm = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(outMetrics);
//
//        outMetrics.widthPixels = width;
//
//    }
//
//    /**
//     * 获得屏幕高度,不包括状态栏
//     *
//     * @param context
//     * @return
//     */
//    public static int getScreenHeight(Context context) {
//        WindowManager wm = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(outMetrics);
//        return outMetrics.heightPixels;
//    }
//
//    public static void setScreenHeight(Context context, int height) {
//        WindowManager wm = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(outMetrics);
//        outMetrics.heightPixels = height;
//    }
//
//    /**
//     * 获得状态栏的高度
//     *
//     * @param context
//     * @return
//     */
//    public static int getStatusHeight(Context context) {
//
//        int statusHeight = -1;
//        try {
//            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
//            Object object = clazz.newInstance();
//            int height = Integer.parseInt(clazz.getField("status_bar_height")
//                    .get(object).toString());
//            statusHeight = context.getResources().getDimensionPixelSize(height);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return statusHeight;
//    }
//
//
//    /**
//     * 获取当前屏幕截图，包含状态栏
//     *
//     * @param activity
//     * @return
//     */
//    public static Bitmap snapShotWithStatusBar(Activity activity) {
//        View view = activity.getWindow().getDecorView();
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//        Bitmap bmp = view.getDrawingCache();
//        int width = getScreenWidth(activity);
//        int height = getScreenHeight(activity);
//        Bitmap bp = null;
//        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
//        view.destroyDrawingCache();
//        return bp;
//
//    }
//
//    /**
//     * 获取当前屏幕截图，不包含状态栏
//     *
//     * @param activity
//     * @return
//     */
//    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
//        View view = activity.getWindow().getDecorView();
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//        Bitmap bmp = view.getDrawingCache();
//        Rect frame = new Rect();
//        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        int statusBarHeight = frame.top;
//
//        int width = getScreenWidth(activity);
//        int height = getScreenHeight(activity);
//        Bitmap bp = null;
//        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
//                - statusBarHeight);
//        view.destroyDrawingCache();
//        return bp;
//
//    }
//
//
//    /**
//     * 得到设备的密度
//     */
//    public static float getScreenDensity(Context context) {
//        return context.getResources().getDisplayMetrics().density;
//    }
//
//
//    /**
//     * 根据手机分辨率从dp转成px
//     *
//     * @param context
//     * @param dpValue
//     * @return
//     */
//    public static int dipTopx(Context context, float dpValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }
//
//    /**
//     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
//     */
//    public static int pxTodip(Context context, float pxValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (pxValue / scale + 0.5f) - 15;
//    }
//
//    /**
//     * 将px值转换为sp值，保证文字大小不变
//     *
//     * @param pxValue （DisplayMetrics类中属性scaledDensity）
//     * @return
//     */
//    public static int px2sp(Context context, float pxValue) {
//        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
//        return (int) (pxValue / fontScale + 0.5f);
//    }
//
//    /**
//     * 将sp值转换为px值，保证文字大小不变
//     *
//     * @param spValue （DisplayMetrics类中属性scaledDensity）
//     * @return
//     */
//    public static int sp2px(Context context, float spValue) {
//        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
//        return (int) (spValue * fontScale + 0.5f);
//    }
////
////    /**
////     * 获取状态栏的高度
////     *
////     * @param context
////     * @return
////     */
////    public static int getStatusBarHeight(Context context) {
////        Class<?> c = null;
////        Object obj = null;
////        Field field = null;
////        int x = 0, statusBarHeight = 0;
////        try {
////            c = Class.forName("com.android.internal.R$dimen");
////            obj = c.newInstance();
////            field = c.getField("status_bar_height");
////            x = Integer.parseInt(field.get(obj).toString());
////            statusBarHeight = context.getResources().getDimensionPixelSize(x);
////        } catch (Exception e1) {
////            e1.printStackTrace();
////        }
////        return statusBarHeight;
////    }
//
//    /**
//     * 获取状态栏高度
//     *
//     * @param context
//     * @return
//     */
//    private static int getStatusBarHeight(Context context) {
//        int result = 0;
//        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = context.getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }
//
//    /**
//     * 模拟home键
//     *
//     * @param context
//     */
//    public void goToDestop(Context context) {
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        context.startActivity(intent);
//    }
//
//
//    /**
//     * 获取屏幕原始尺寸高度，包括虚拟功能键高度
//     *
//     * @param context
//     * @return
//     */
//    public static int getScreenHeightContain(Context context) {
//        WindowManager wm = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//
//        int dpi = 0;
//        Display display = wm.getDefaultDisplay();
//        DisplayMetrics dm = new DisplayMetrics();
//        @SuppressWarnings("rawtypes")
//        Class c;
//        try {
//            c = Class.forName("android.view.Display");
//            @SuppressWarnings("unchecked")
//            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
//            method.invoke(display, dm);
//            dpi = dm.heightPixels;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return dpi;
//    }
//
//    /**
//     * 判断是否有虚拟按键
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isHasVirtualButton(Context context) {
//        int tempWindowHeight = getScreenHeight(context) - getScreenHeightContain(context);
//
//        DebugLog.i("getScreenHeight:" + getScreenHeight(context));
//        DebugLog.i("getScreenHeightContain:" + getScreenHeightContain(context));
//
//        if (tempWindowHeight != 0) {
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     * 判断是否存在软键盘，如果存在则直接隐藏
//     */
//
//    public static void checkInputMethodAndHide(Activity activity) {
//        View view = activity.getWindow().peekDecorView();
//        if (view != null) {
//            //隐藏虚拟键盘
//            InputMethodManager inputmanger = (InputMethodManager) activity
//                    .getSystemService(activity.INPUT_METHOD_SERVICE);
//            inputmanger.hideSoftInputFromWindow(view.getWindowToken(),
//                    0);
//        }
//    }
//
//    /**
//     * 收起软键盘
//     */
//    public static void collapseSoftInputMethod(Context context, View v) {
//        if (v != null) {
//            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//        }
//    }
//
//    /**
//     * 显示软键盘
//     */
//    public static void showSoftInputMethod(Context context, View v) {
//        Activity activity = (Activity) context;
//        if (v != null) {
//            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(v, 0);
//        }
//    }
//
//    /**
//     * 直接调用系统软键盘
//     */
//
//    public static void showSoftInputMethodDirectly(Activity activity) {
//        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//    }
//
//
//    /**
//     * 验证邮箱
//     */
//
//    public static boolean checkEmail(String email) {
//        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
//
//    /**
//     * 验正手机号
//     * 添加11开头的马甲号段
//     * 正则校验可参考："^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$"
//     *
//     * @param mobiles
//     * @return
//     */
//    public static boolean isMobileNO(String mobiles) {
////        Pattern p = Pattern.compile("^((13[0-9])|(15[0,0-9])|(18[0,0-9])|(17[0,0-9])|(14[0,0-9])|(11[0,0-9]))\\d{8}$");
//        // 最新手机号校验正则，166、199号段相继启用，移动198号段也已启动
//        Pattern p = Pattern.compile("^(1[13456789])\\d{9}$");
//        Matcher m = p.matcher(mobiles);
//        return m.matches();
//    }
//
//
//    /**
//     * 判断当前字符串是否包含汉字
//     */
//
//    public static boolean isContainChinese(String str) {
//
//        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
//        Matcher m = p.matcher(str);
//        if (m.find()) {
//            return true;
//        }
//        return false;
//    }
//
//
//    public static Bitmap readBitMap(Context context, int resId) {
//        BitmapFactory.Options opt = new BitmapFactory.Options();
//        opt.inPreferredConfig = Bitmap.Config.RGB_565;
//        opt.inPurgeable = true;
//        opt.inInputShareable = true;
//        //获取资源图片
//        InputStream is = context.getResources().openRawResource(resId);
//        return BitmapFactory.decodeStream(is, null, opt);
//    }
//
//
//    //获取屏幕原始尺寸高度，包括虚拟功能键高度
//    public static int getDpi(Context context) {
//        int dpi = 0;
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = windowManager.getDefaultDisplay();
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        @SuppressWarnings("rawtypes")
//        Class c;
//        try {
//            c = Class.forName("android.view.Display");
//            @SuppressWarnings("unchecked")
//            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
//            method.invoke(display, displayMetrics);
//            dpi = displayMetrics.heightPixels;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return dpi;
//    }
//
//    /**
//     * 获取 虚拟按键的高度
//     *
//     * @param context
//     * @return
//     */
//    public static int getBottomStatusHeight(Context context) {
//        int totalHeight = getDpi(context);
//
//        int contentHeight = getScreenHeight(context);
//
//        return totalHeight - contentHeight;
//    }
//
//    /**
//     * 标题栏高度
//     *
//     * @return
//     */
//    public static int getTitleHeight(Activity activity) {
//        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
//    }
//
//
//    /**
//     * @param a
//     * @param b
//     * @return
//     */
//    public static float limitValue(float a, float b) {
//        float valve = 0;
//        final float min = Math.min(a, b);
//        final float max = Math.max(a, b);
//        valve = valve > min ? valve : min;
//        valve = valve < max ? valve : max;
//        return valve;
//    }
//
//
//    /**
//     * 动态计算重新定义图片的尺寸
//     *
//     * @param context
//     * @param url
//     * @return
//     */
//    public static int[] getImageSizeFromUrl(Context context, String url, int mCurrentW) {
//        int[] mImageSizeArray = new int[2];
//
//        int maxWSize = mCurrentW;  // 竖图的最大宽
//        int maxHSize = mCurrentW * 2; // 竖图的最大高
//
//        int pxOneWidth = mCurrentW;
//        int pxOneHeight = mCurrentW;
//
//        String[] strWH = url.split("\\|");
//        if (strWH.length == 3 && UIUtils.isNumeric(strWH[1]) && UIUtils.isNumeric(strWH[2])) {
//            int width = Integer.valueOf(strWH[1]);
//            int height = Integer.valueOf(strWH[2]);
//
//            if (width > 2 * height) { // 宽图
//                pxOneWidth = mCurrentW;
//                pxOneHeight = (mCurrentW * height) / width; //要定原始的高度等比缩放啊
//
//            } else if (height >= 2 * width) {// 长图
//                pxOneWidth = maxWSize;
//                pxOneHeight = maxHSize;
//            } else {
//                if (width != height) { // 普通图横图（宽度大于屏幕）
//                    if (width > mCurrentW) { //大于屏幕宽度
//                        pxOneWidth = mCurrentW;
//                        pxOneHeight = (pxOneWidth * height) / width;
//                    } else {  //小于或等于屏幕宽度
//
//                        pxOneWidth = mCurrentW;
//
//                        if (width < mCurrentW) {
//                            pxOneHeight = (mCurrentW * height) / width;
//                        } else {
//                            pxOneWidth = width;
//                            pxOneHeight = height;
//                        }
//                    }
//                } else { // 正方图
//                    pxOneWidth = mCurrentW;
//                    pxOneHeight = mCurrentW;
//                }
//
//            }
//
//        } else {
//            pxOneWidth = mCurrentW;
//            pxOneHeight = mCurrentW * 2 / 3;
//        }
//        mImageSizeArray[0] = pxOneWidth;
//        mImageSizeArray[1] = pxOneHeight;
//
//        return mImageSizeArray;
//    }
//
//    /**
//     * 实现自适应图片宽高,适配手机屏幕宽度
//     *
//     * @param context
//     * @param url
//     * @return
//     */
//    public static int[] getAutoImageSizeFromUrl(Context context, String url, int mCurrentW) {
//        int[] mImageSizeArray = new int[2];
//
//        int pxOneWidth = mCurrentW;
//        int pxOneHeight = mCurrentW;
//
//        String[] strWH = url.split("\\|");
//        if (strWH.length == 3 && UIUtils.isNumeric(strWH[1]) && UIUtils.isNumeric(strWH[2])) {
//            int width = Integer.valueOf(strWH[1]); // 图片的原始宽度
//            int height = Integer.valueOf(strWH[2]); // 图片的原始高度
//
//            if (width > 2 * height) { // 宽图
//                pxOneWidth = mCurrentW;
//                pxOneHeight = (mCurrentW * height) / width; //要定原始的高度等比缩放啊
//
//            } else if (height >= 2 * width) { // 长图，实现自适应高度
//                pxOneWidth = mCurrentW;
//                pxOneHeight = (mCurrentW * height) / width;
//            } else {
//                if (width != height) { // 普通图横图（宽度大于屏幕）
//                    if (width > mCurrentW) { //大于屏幕宽度
//                        pxOneWidth = mCurrentW;
//                        pxOneHeight = (mCurrentW * height) / width;
//                    } else {  //小于或等于屏幕宽度
//                        pxOneWidth = mCurrentW;
//
//                        if (width < mCurrentW) {
//                            pxOneHeight = (mCurrentW * height) / width;
//                        } else {
//                            pxOneWidth = width;
//                            pxOneHeight = height;
//                        }
//                    }
//                } else { // 正方图
//                    pxOneWidth = mCurrentW;
//                    pxOneHeight = mCurrentW;
//                }
//
//            }
//
//        } else {
//            pxOneWidth = mCurrentW;
//            pxOneHeight = mCurrentW * 2 / 3;
//        }
//        mImageSizeArray[0] = pxOneWidth;
//        mImageSizeArray[1] = pxOneHeight;
//
//        return mImageSizeArray;
//    }
//
//    /**
//     * 修改图片尺寸适配指定区域高度
//     *
//     * @param context
//     * @param url
//     * @param containerHeight 容器固定高度
//     * @return
//     */
//    public static int[] formateImageSizeByHeight(Context context, String url, int containerHeight) {
//        // Remote图片
//        String[] strWH = url.split("\\|");
//        if (strWH.length == 3 && UIUtils.isNumeric(strWH[1]) && UIUtils.isNumeric(strWH[2])) {
//
//            int width = Integer.valueOf(strWH[1]); // 图片的原始宽度
//            int height = Integer.valueOf(strWH[2]); // 图片的原始高度
//            return makeWidthHeightAccurate(context, width, height, containerHeight);
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * 处理编辑图片宽高的规范化,可处理本地图片
//     *
//     * @param width
//     * @param height
//     * @return
//     */
//    public static int[] makeWidthHeightAccurate(Context context, int width, int height, int containerHeight) {
//        int[] mImageSizeArray = new int[2];
//        int pxWidth = getScreenWidth(context);
//        int pxHeight = containerHeight;
//
//        if (width < pxWidth && height < pxHeight) { // 均小于则进行等比放大
//            float ratio = Math.min(pxWidth / width, pxHeight / height); // 取最小的比值
//            if (width > height) {
//                pxHeight = (int) (height * ratio);
//            } else {
//                pxWidth = (int) (width * ratio);
//            }
//        }
//
//        mImageSizeArray[0] = pxWidth;
//        mImageSizeArray[1] = pxHeight;
//        return mImageSizeArray;
//    }
//
//    /**
//     * 将富文本转成CharSequence
//     *
//     * @param commonStr 普通内容
//     * @param bqId      表情图片
//     * @return
//     */
//    public static CharSequence transferImageText(String commonStr, int bqId, Html.ImageGetter imgGetter) {
//        return Html.fromHtml("<img src=\"" + bqId + "\">  " + commonStr, imgGetter, null);
//    }
//
//
////    /TODO 找不到类JHImageSpan，先注释
////    public static SpannableString getTextSpand(String commonStr, @NonNull JHImageSpan imageSpan) {
////        SpannableString showString = new SpannableString(commonStr);
////        showString.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////        return showString;
////    }
//
//
//    /**
//     * 禁止Edittext弹出软件盘，光标依然正常显示。
//     *
//     * @param editText
//     */
//    public static void disableShowSoftInput(EditText editText) {
//        if (Build.VERSION.SDK_INT <= 10) {
//            editText.setInputType(InputType.TYPE_NULL);
//        } else {
//            Class<EditText> cls = EditText.class;
//            Method method;
//            try {
//                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
//                method.setAccessible(true);
//                method.invoke(editText, false);
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//
//            try {
//                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
//                method.setAccessible(true);
//                method.invoke(editText, false);
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//        }
//    }
//
//    public static int getFontHeight(TextView textView) {
//        Paint paint = new Paint();
//        paint.setTextSize(textView.getTextSize());
//        Paint.FontMetrics fm = paint.getFontMetrics();
//        return (int) Math.ceil(fm.bottom - fm.top);
//    }
//
//
//    /**
//     * 获取toobar高度
//     *
//     * @param toolbar
//     * @return
//     */
//    public static int getToolBarHeight(Toolbar toolbar) {
//        if (null != toolbar) {
//            int w = View.MeasureSpec.makeMeasureSpec(0,
//                    View.MeasureSpec.UNSPECIFIED);
//            int h = View.MeasureSpec.makeMeasureSpec(0,
//                    View.MeasureSpec.UNSPECIFIED);
//            toolbar.measure(w, h);
//            return toolbar.getMeasuredHeight();
//        } else {
//            return 0;
//        }
//    }
//
//    /**
//     * 计算文本的长度是否超过最大行
//     *
//     * @param textView
//     * @param textString
//     * @return
//     */
//    public static boolean calLines(Context context, TextView textView, String textString) {
//        // 获得字体的宽度，sp转px的方法，网上很多，14为textview中所设定的textSize属性值
//        TextPaint mTextPaint = textView.getPaint();
//        int textWidth = (int) mTextPaint.measureText(textString);
//        int width = UIUtils.getScreenWidth(context) - UIUtils.dipTopx(context, 106);
//
//        if (textWidth > width) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public static void setMargins(View v, int l, int t, int r, int b) {
//        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
//            p.setMargins(l, t, r, b);
//            v.requestLayout();
//        }
//    }
//
//    /**
//     * 判断是否是的数字
//     *
//     * @param str
//     * @return
//     */
//    public static boolean isNumeric(String str) {
//        Pattern pattern = Pattern.compile("[0-9]*");
//        Matcher isNum = pattern.matcher(str);
//        if (!isNum.matches()) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 屏幕适配工具，根据screenDp来适配，依据设计稿的宽度dp或者高度dp来做适配
//     *
//     * @param activity
//     * @param application
//     * @param screenDp
//     */
//    public static void setCustomDensity(@Nonnull Activity activity, @Nonnull final Application application, final int screenDp) {
//        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
//
//        if (sNoncompatDensity == 0) {
//            sNoncompatDensity = appDisplayMetrics.density;
//            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
//            application.registerComponentCallbacks(new ComponentCallbacks() {
//                @Override
//                public void onConfigurationChanged(Configuration newConfig) {
//                    if (newConfig != null && newConfig.fontScale > 0) {
//                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
//                    }
//                }
//
//                @Override
//                public void onLowMemory() {
//
//                }
//            });
//        }
//
//        final float targetDensity = appDisplayMetrics.widthPixels / screenDp;
//        final float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
//        final int targetDensityDpi = (int) (160 * targetDensity);
//
//        appDisplayMetrics.density = targetDensity;
//        appDisplayMetrics.scaledDensity = targetScaledDensity;
//        appDisplayMetrics.densityDpi = targetDensityDpi;
//
//        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
//        activityDisplayMetrics.density = targetDensity;
//        activityDisplayMetrics.scaledDensity = targetScaledDensity;
//        activityDisplayMetrics.densityDpi = targetDensityDpi;
//
//    }
//
//    /**
//     * 获取view当前占据屏幕的百分比
//     *
//     * @param view
//     * @return 0-100
//     */
//    public static int getVisiblePercent(View view) {
//        if (view != null && view.isShown()) {
//            DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
//            int displayWidth = displayMetrics.widthPixels;
//            Rect rect = new Rect();
//            boolean globalVisibleRect = view.getGlobalVisibleRect(rect);
//            if (!globalVisibleRect) {
//                return -1;
//            }
//            if ((rect.top > 0) && (rect.left < displayWidth)) {
//                double areaVisible = rect.width() * rect.height();
//                double areaTotal = view.getWidth() * view.getHeight();
//                return (int) (((areaVisible) / areaTotal) * 100);
//            } else {
//                return -1;
//            }
//        }
//        return -1;
//    }
//
//
//    public static void setRecyclerViewMaxFlingVelocity(RecyclerView recyclerView, int velocity) {
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
//            try {
//                Field field = recyclerView.getClass().getDeclaredField("mMaxFlingVelocity");
//                field.setAccessible(true);
//                field.set(recyclerView, velocity);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
