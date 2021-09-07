package com.hnsh.scanner.zbarUtils.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Hsp
 * @Email: 1101121039@qq.com
 * @CreateTime: 2021/7/12 9:56
 * @UpdateRemark: 更新说明：
 */
public class QRCodeParseUtils {

    static final Map<DecodeHintType, Object> HINT_MAP = new EnumMap<>(DecodeHintType.class);

    static {
        List<BarcodeFormat> allFormatList = new ArrayList<>();
        allFormatList.add(BarcodeFormat.AZTEC);
        allFormatList.add(BarcodeFormat.CODABAR);
        allFormatList.add(BarcodeFormat.CODE_39);
        allFormatList.add(BarcodeFormat.CODE_93);
        allFormatList.add(BarcodeFormat.CODE_128);
        allFormatList.add(BarcodeFormat.DATA_MATRIX);
        allFormatList.add(BarcodeFormat.EAN_8);
        allFormatList.add(BarcodeFormat.EAN_13);
        allFormatList.add(BarcodeFormat.ITF);
        allFormatList.add(BarcodeFormat.MAXICODE);
        allFormatList.add(BarcodeFormat.PDF_417);
        allFormatList.add(BarcodeFormat.QR_CODE);
        allFormatList.add(BarcodeFormat.RSS_14);
        allFormatList.add(BarcodeFormat.RSS_EXPANDED);
        allFormatList.add(BarcodeFormat.UPC_A);
        allFormatList.add(BarcodeFormat.UPC_E);
        allFormatList.add(BarcodeFormat.UPC_EAN_EXTENSION);

        // 可能的编码格式
        HINT_MAP.put(DecodeHintType.POSSIBLE_FORMATS, allFormatList);
        // 花更多的时间用于寻找图上的编码，优化准确性，但不优化速度
        HINT_MAP.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        // 复杂模式，开启 PURE_BARCODE 模式（带图片 LOGO 的解码方案）
//        ALL_HINT_MAP.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        // 编码字符集
        HINT_MAP.put(DecodeHintType.CHARACTER_SET, "utf-8");
    }

    /**
     * 同步解析本地图片二维码。该方法是耗时操作，请在子线程中调用。
     *
     * @param picturePath 要解析的二维码图片本地路径
     * @return 返回二维码图片里的内容 或 null
     */
    public static String decodeQRCodeByPath(String picturePath) {
//        不剪裁图片 原图展示
        return syncDecodeQRCode(BitmapFactory.decodeFile(picturePath, null));
//        return syncDecodeQRCode(BitmapUtil.getBitmap(picturePath, 960, 720));
    }

    /**
     * 同步解析本地图片二维码。该方法是耗时操作，请在子线程中调用。
     *
     * @param bitmap 要解析的二维码图片本地路径，可外部自定义Bitmap优化解析策略
     * @return 返回二维码图片里的内容 或 null
     */
    public static String decodeQRCodeByBitmap(Bitmap bitmap) {
        return syncDecodeQRCode(bitmap);
    }

    /**
     * 同步解析bitmap二维码。该方法是耗时操作，请在子线程中调用。
     *
     * @param bitmap 要解析的二维码图片
     * @return 返回二维码图片里的内容 或 null
     */
    public static String syncDecodeQRCode(Bitmap bitmap) {
        Result result;
        RGBLuminanceSource source = null;
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] pixels = new int[width * height];
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
            /**
             *  尝试另一种解析方式
             *  @author lfc-LFC
             *  created at 2021/9/7 18:22
             */
            QRCodeReader reader = new QRCodeReader();
            Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);//优化精度
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");//解码设置编码方式为：utf-8
            result = reader.decode(new BinaryBitmap(new HybridBinarizer(new RGBLuminanceSource(width, height, pixels))), hints);
            /**
             *  之前的解析方式
             */
//            source = new RGBLuminanceSource(width, height, pixels);
//            result = new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(source)), HINT_MAP);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            if (source != null) {
                try {
                    result = new MultiFormatReader().decode(new BinaryBitmap(new GlobalHistogramBinarizer(source)), HINT_MAP);
                    return result.getText();
                } catch (Throwable e2) {
                    e2.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * 将本地图片文件转换成可解码二维码的 Bitmap。为了避免图片太大，这里对图片进行了压缩。感谢 https://github.com/devilsen 提的 PR
     *
     * @param picturePath 本地图片文件路径
     * @return
     */
    public static Bitmap getDecodeAbleBitmap(String picturePath) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(picturePath, options);
            int sampleSize = options.outHeight / 400;
            if (sampleSize <= 0) {
                sampleSize = 1;
            }
            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(picturePath, options);
        } catch (Exception e) {
            return null;
        }
    }
}
