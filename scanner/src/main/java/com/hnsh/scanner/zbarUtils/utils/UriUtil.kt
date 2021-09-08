package com.hnsh.scanner.zbarUtils.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.net.toFile
import androidx.loader.content.CursorLoader

/**
 *  根据URI反向获取 真实路径
 *
 *  @author lfc-LFC
 *  created at 2021/9/8 10:11
 */

object UriUtil {
    public fun getRealPathApi19Above(context: Context, uri: Uri): String? {
        var filePath = ""
        try {
            // DocumentProvider
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }
                } else if (isDownloadsDocument(uri)) {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id)
                    )
                    return getDataColumn(context, contentUri, null, null)
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(
                        split[1]
                    )
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {
                // Return the remote address
                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                    context, uri, null, null
                )
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }
        } catch (e: Exception) {
            filePath = ""
        }
        return filePath
    }

    private fun getDataColumn(
        context: Context,
        uri: Uri?,
        selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver.query(
                uri!!,
                arrayOf(MediaStore.Images.Media.DATA),
                selection,
                selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }


    private fun getRealPathApi11to18(context: Context, contentUri: Uri): String? {
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            var result: String? = null
            val cursorLoader = CursorLoader(context, contentUri, proj, null, null, null)
            val cursor: Cursor? = cursorLoader.loadInBackground()
            if (cursor != null) {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                result = cursor.getString(column_index)
            }
            result
        } catch (e: Exception) {
            null
        }
    }


    fun getImagePath(context: Context, uri: Uri): String? {
        return if (Build.VERSION.SDK_INT < 19) getRealPathApi11to18(context, uri)
        else getRealPathApi19Above(context, uri)
    }

    fun deleteFileByUri(context: Context, uri: Uri) {
        if (uri.toString().startsWith("content://")) {
            context.contentResolver.delete(uri, null, null)
        } else {
            uri.toFile().let {
                if (it.exists() && it.isFile) {
                    it.delete()
                }
            }
        }
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }
}