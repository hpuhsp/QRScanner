package com.hnsh.scanner.zbarUtils.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import com.hnsh.scanner.R;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Function:
 * <br/>
 * Describe:TextView富文本工具
 * <br/>
 * Author: reesehu on 16/3/30.
 * <br/>
 * Email: reese@jiuhuar.com
 */
public class ReplacerUtils {

    private final CharSequence mSource;
    private final CharSequence mReplacement;
    private final Matcher mMatcher;
    private int mAppendPosition;
    private final boolean mIsSpannable;

    public static CharSequence replace(CharSequence source, String regex,
                                       CharSequence replacement) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        return new ReplacerUtils(source, matcher, replacement).doReplace();
    }

    private ReplacerUtils(CharSequence source, Matcher matcher,
                          CharSequence replacement) {
        mSource = source;
        mReplacement = replacement;
        mMatcher = matcher;
        mAppendPosition = 0;
        mIsSpannable = replacement instanceof Spannable;
    }

    private CharSequence doReplace() {
        SpannableStringBuilder buffer = new SpannableStringBuilder();
        while (mMatcher.find()) {
            appendReplacement(buffer);
        }
        return appendTail(buffer);
    }

    private void appendReplacement(SpannableStringBuilder buffer) {
        buffer.append(mSource.subSequence(mAppendPosition, mMatcher.start()));
        CharSequence replacement = mIsSpannable
                ? copyCharSequenceWithSpans(mReplacement)
                : mReplacement;
        buffer.append(replacement);
        mAppendPosition = mMatcher.end();
    }

    public SpannableStringBuilder appendTail(SpannableStringBuilder buffer) {
        buffer.append(mSource.subSequence(mAppendPosition, mSource.length()));
        return buffer;
    }

    // This is a weird way of copying spans, but I don't know any better way.
    private CharSequence copyCharSequenceWithSpans(CharSequence string) {
        Parcel parcel = Parcel.obtain();
        try {
            TextUtils.writeToParcel(string, parcel, 0);
            parcel.setDataPosition(0);
            return TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        } finally {
            parcel.recycle();
        }
    }

    public String replaceTextColor(String resource, String keyword) {
        String res = null;

        return res;
    }

    public static SpannableString matcherSearchTitle(int color, String text,
                                                     String keyword) {
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     * 多个关键字高亮变色
     *
     * @param text    文字
     * @param keyword 文字中的关键字数组
     * @return
     */
    public static SpannableString matcherMoreKeywordtLight(Context context, String text,
                                                           String[] keyword) {
        SpannableString s = new SpannableString(text);
        for (int i = 0; i < keyword.length; i++) {
            Pattern p = Pattern.compile(keyword[i]);
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.scanner_green)), start, end,
                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        return s;
    }

    /**
     * 多个关键字实现富文本
     */
    public static SpannableString matcherSearchTitle(int color, String text,
                                                     ArrayList<String> keyword) {
        SpannableString s = new SpannableString(text);
        for (int i = 0; i < keyword.size(); i++) {
            Pattern p = Pattern.compile(keyword.get(i));
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }

    /**
     * 新的实现方法
     */
    public static SpannableStringBuilder matcherNewSearchTitle(Context context, String text,
                                                               ArrayList<String> keyword) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        for (int i = 0; i < keyword.size(); i++) {
            Pattern p = Pattern.compile(keyword.get(i));
            Matcher m = p.matcher(style);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
//                style.setSpan(new BackgroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的背景颜色
                style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.scanner_green)), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
            }
        }
        return style;
    }


    /**
     * 先求出所有字符串的子集
     */
    public static ArrayList<String> getSubSequences(String word) {
        ArrayList<String> list = new ArrayList<String>();
        doGetSubSequences(word, "", list);
        return list;
    }

    private static void doGetSubSequences(String word, String s, ArrayList<String> list) {
        if (word.length() == 0) {
            list.add(s);
            return;
        }

        String tail = word.substring(1);
        doGetSubSequences(tail, s, list);
        doGetSubSequences(tail, s + word.charAt(0), list);
    }

    /**
     * 正则匹配
     * 高亮关键词
     *
     * @param keyword
     * @param text
     * @param context
     * @return
     */
    public static SpannableStringBuilder makeLightKeyWord(String keyword, String text, Context context) {

        String reg = "\\w+";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(keyword.trim());

        StringBuilder builder = new StringBuilder();

        SpannableStringBuilder s = new SpannableStringBuilder(text);
        while (matcher.find()) {
            char[] chars = matcher.group().toCharArray();

            for (int i = 0; i < chars.length; i++) {

                if (builder.toString().contains(String.valueOf(chars[i]))) {

                } else {
                    builder.append(chars[i]);
                    Pattern p = Pattern.compile(String.valueOf(chars[i]), Pattern.CASE_INSENSITIVE | Pattern.COMMENTS);
                    Matcher m = p.matcher(s);
                    while (m.find()) {
                        int start = m.start();
                        int end = m.end();
                        s.setSpan(new ForegroundColorSpan(ResourcesUtil.getColor(context, R.color.scanner_green)), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    }
                }
            }

        }

        return s;
    }

    public static String getUrlRule(String api) {
        String result = null;
        if (!TextUtils.isEmpty(api)) {
            String[] split = api.split("/");

            if (split.length == 3) {
                if (!TextUtils.isEmpty(split[2])) {
                    result = split[2].toString();
                }
            }

        }
        return result;
    }


    /**
     * 正则匹配
     * 去除<b></b>标签并高亮其内容
     *
     * @param context
     * @param text
     * @return
     */
    public static SpannableStringBuilder makeHeightLightKeyWord(Context context, Spannable text) {
        String reg = "<B>(.*?)</B>"; // 查找标签内容
        Pattern tagPattern = Pattern.compile(reg);
        Matcher tagMatcher = tagPattern.matcher(text);
//        DebugLog.i("text--------0-------->" + text);
//        DebugLog.i("textlength---------------->" + text.length());

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        int length = 0;
        while (tagMatcher.find()) { // 高亮匹配到的内容
//            DebugLog.i("tagMatcher---------------->" + tagMatcher.group());

            int origStart, origEnd;
            origStart = tagMatcher.start() - length;
            origEnd = tagMatcher.end() - length;

//            DebugLog.i("origStart------------->" + origStart);

            int start = origStart + 3;
            int end = origEnd - 4;
            if (0 <= start && end < spannableStringBuilder.length()) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(ResourcesUtil.getColor(context, R.color.scanner_green)),
                        start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE); // 高亮之后长度不会增加
            }
//            DebugLog.i("length--------+-------->" + spannableStringBuilder.length());

            // 标签前半部分“<b>”的开始部分
            if (0 <= origStart && origStart < spannableStringBuilder.length()) {
//                DebugLog.i("remove---------------->");
                spannableStringBuilder.replace(origStart, start, "");
            }

            // 在上一步处理后标签前半部分“</b>”的开始处
            int bTagEnd = origEnd - 3;
            int bTagStart = origEnd - 7;
            if (0 <= bTagStart && bTagStart < spannableStringBuilder.length()) {
                spannableStringBuilder.replace(bTagStart, bTagEnd, "");
            }

            length = length + 7;
//            DebugLog.i("spannableStringBuilder---------------->" + spannableStringBuilder.toString());
        }

        return spannableStringBuilder;
    }


    /**
     * 正则匹配
     * 去除<b></b>标签并加粗其内容
     *
     * @param context
     * @param text
     * @return
     */
    public static SpannableStringBuilder makeBoldKeyWord(Context context, Spannable text) {
        String reg = "<B>(.*?)</B>"; // 查找标签内容
        Pattern tagPattern = Pattern.compile(reg);
        Matcher tagMatcher = tagPattern.matcher(text);
//        DebugLog.i("text--------0-------->" + text);
//        DebugLog.i("textlength---------------->" + text.length());

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        int length = 0;
        while (tagMatcher.find()) { // 高亮匹配到的内容
//            DebugLog.i("tagMatcher---------------->" + tagMatcher.group());

            int origStart, origEnd;
            origStart = tagMatcher.start() - length;
            origEnd = tagMatcher.end() - length;

//            DebugLog.i("origStart------------->" + origStart);

            int start = origStart + 3;
            int end = origEnd - 4;
            if (0 <= start && end < spannableStringBuilder.length()) {
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD),
                        start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE); // 高亮之后长度不会增加
            }
//            DebugLog.i("length--------+-------->" + spannableStringBuilder.length());

            // 标签前半部分“<b>”的开始部分
            if (0 <= origStart && origStart < spannableStringBuilder.length()) {
//                DebugLog.i("remove---------------->");
                spannableStringBuilder.replace(origStart, start, "");
            }

            // 在上一步处理后标签前半部分“</b>”的开始处
            int bTagEnd = origEnd - 3;
            int bTagStart = origEnd - 7;
            if (0 <= bTagStart && bTagStart < spannableStringBuilder.length()) {
                spannableStringBuilder.replace(bTagStart, bTagEnd, "");
            }

            length = length + 7;
//            DebugLog.i("spannableStringBuilder---------------->" + spannableStringBuilder.toString());
        }

        return spannableStringBuilder;
    }

}
