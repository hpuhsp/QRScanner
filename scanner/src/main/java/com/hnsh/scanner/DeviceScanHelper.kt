package com.hnsh.scanner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

/**
 * @Description:
 * @Author:   Hsp
 * @Email:    1101121039@qq.com
 * @CreateTime:     2020/9/8 15:40
 * @UpdateRemark:   更新说明：
 */

class DeviceScanHelper constructor(private val mContext: Context) {
    companion object {
        private const val BARCODE_STRING_TAG = "barcode_string"
        private const val BARCODE_LENGTH_TAG = "length"
        private const val DECODE_DATA_TAG = "barcode"

        private const val SCAN_ACTION = "android.intent.ACTION_DECODE_DATA"
        private const val SCAN_ACTION_OLD1 = "android.intent.action.RECEIVE_SCANDATA_BROADCAST"
        private const val SCAN_ACTION_OLD2 = "com.android.server.scannerservice.broadcast"
        private const val SCAN_ACTION_OLD3 = "android.intent.action.SCANRESULT"
//        private const val SCAN_ACTION_OLD4 = "urovo.rcv.message"
        private const val SCAN_ACTION_OLD5 = "nlscan.action.SCANNER_RESULT"

    }

    private var mOnScanSuccessListener: OnScanSuccessListener? = null

    fun setOnScanSuccessListener(onScanSuccessListener: OnScanSuccessListener?) {
        mOnScanSuccessListener = onScanSuccessListener
    }

    private val mScanReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            var barcodeStr: String?
            barcodeStr = intent.getStringExtra(BARCODE_STRING_TAG)
            if (barcodeStr == null || barcodeStr.isEmpty()) {
                val barcode =
                    intent.getByteArrayExtra(DECODE_DATA_TAG)
                val barcodeLen = intent.getIntExtra(BARCODE_LENGTH_TAG, 0)
                if (barcode != null && barcode.size > 0) {
                    barcodeStr = String(barcode, 0, barcodeLen)
                }
            }
            if (barcodeStr == null || barcodeStr.isEmpty()) {
                barcodeStr = intent.getStringExtra("android.intent.extra.SCAN_BROADCAST_DATA")
            }
            if (barcodeStr == null || barcodeStr.isEmpty()) {
                barcodeStr = intent.getStringExtra("scannerdata")
            }
            if (barcodeStr == null || barcodeStr.isEmpty()) {
                barcodeStr = intent.getStringExtra("value")
            }
            if (barcodeStr == null || barcodeStr.isEmpty()) {
                barcodeStr = intent.getStringExtra("barcode")
            }
            if (barcodeStr == null || barcodeStr.isEmpty()) {
                val barcode = intent.getByteArrayExtra("barocode")
                val barocodelen = intent.getIntExtra("length", 0)
                barcodeStr = barcode?.let { String(it, 0, barocodelen) }
            }
            if (mOnScanSuccessListener != null) {
                mOnScanSuccessListener?.onSuccess(barcodeStr ?: "")
            }
        }
    }

    fun onResume() {
        try {
            val filter = IntentFilter()
            filter.addAction(SCAN_ACTION)
            filter.addAction(SCAN_ACTION_OLD1)
            filter.addAction(SCAN_ACTION_OLD2)
            filter.addAction(SCAN_ACTION_OLD3)
//            filter.addAction(SCAN_ACTION_OLD4)
            filter.addAction(SCAN_ACTION_OLD5)
            filter.priority = Int.MAX_VALUE
            mContext?.registerReceiver(mScanReceiver, filter)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onPause() {
        try {
            mContext?.unregisterReceiver(mScanReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    interface OnScanSuccessListener {
        fun onSuccess(result: String?)
    }
}