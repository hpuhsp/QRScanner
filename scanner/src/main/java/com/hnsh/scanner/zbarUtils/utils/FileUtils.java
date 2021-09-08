package com.hnsh.scanner.zbarUtils.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileFilter;

/**
 * @Description:
 * @Author: Hsp
 * @Email: 1101121039@qq.com
 * @CreateTime: 2021/7/12 9:37
 * @UpdateRemark: 更新说明：
 */
public class FileUtils {

    /**
     * 获取保存图片地址
     */
    public static String getTempPicPath(Context context) {
        return getPicSaveDir(context) + File.separator + System.currentTimeMillis() + ".png";
    }

    /**
     * 获取缓存文件保存路径
     */
    public static String getPicSaveDir(Context context) {
        File appCacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && hasExternalStoragePermission(context)
        ) {
            appCacheDir = new File(
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath(),
                    "TempPic");
        }
        if (appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir == null ? "" : appCacheDir.getPath();
    }

    /**
     * 判断权限
     */
    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */

    @SuppressLint("ObsoleteSdkInt")
    public static String getRealPathFromUri(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return getRealPathFromUriAboveApiAndroidQ(context, uri);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return getRealPathFromUriAboveApiAndroidK(context, uri);
        } else {
            return getRealPathFromUriBelowApiAndroidK(context, uri);
        }
    }

    /**
     * 适配 Android 10以上相册选取照片操作
     *
     * @param context 上下文
     * @param uri     图片uri
     * @return 图片地址
     */
    private static String getRealPathFromUriAboveApiAndroidQ(Context context, Uri uri) {
        Cursor cursor = null;
        String path = getRealPathFromUriAboveApiAndroidK(context, uri);
        if (true)
            return path;
        try {
            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                    new String[]{path}, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/images/media");
                return String.valueOf(Uri.withAppendedPath(baseUri, "" + id));
            } else {
                // 如果图片不在手机的共享图片数据库，就先把它插入。
                if (new File(path).exists()) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.DATA, path);
                    return String.valueOf(context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values));
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * 适配Android 4.4以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowApiAndroidK(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * 适配Android 4.4及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApiAndroidK(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) {
                // 使用':'分割
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }


    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;
        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * Delete the all in directory.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean deleteAllInDir(Context context) {
        return deleteFilesInDirWithFilter(new File(getPicSaveDir(context)), new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    /**
     * Delete all files that satisfy the filter in directory.
     *
     * @param dir    The directory.
     * @param filter The filter.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean deleteFilesInDirWithFilter(final File dir, final FileFilter filter) {
        if (dir == null || filter == null)
            return false;
        // dir doesn't exist then return true
        if (!dir.exists())
            return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory())
            return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete())
                            return false;
                    } else if (file.isDirectory()) {
                        if (!deleteDir(file))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Delete the directory.
     *
     * @param dir The directory.
     * @return {@code true}: success<br>{@code false}: fail
     */
    private static boolean deleteDir(final File dir) {
        if (dir == null)
            return false;
        // dir doesn't exist then return true
        if (!dir.exists())
            return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory())
            return false;
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete())
                        return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file))
                        return false;
                }
            }
        }
        return dir.delete();
    }

}
