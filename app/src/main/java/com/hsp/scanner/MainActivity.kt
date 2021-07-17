package com.hsp.scanner

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ImageUtils
import com.bumptech.glide.Glide
import com.hnsh.scanner.CaptureActivity
import com.hnsh.scanner.DeviceScanHelper
import com.hnsh.scanner.decode.DecodeHandler
import com.hnsh.scanner.zbarUtils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

/**
 * @Description:
 * @Author: Hsp
 * @Email:  1101121039@qq.com
 * @CreateTime: 2021/7/17 17:18
 * @UpdateRemark:
 */
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks,
    View.OnClickListener {
    companion object {
        /**
         * 注意添加必需权限
         */
        val CAMERA_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        const val SCAN_REQUEST_CODE = 1001
    }

    private lateinit var scanHelper: DeviceScanHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_scanner.setOnClickListener(this)
        initScanner()
    }

    private fun initScanner() {
        scanHelper = DeviceScanHelper(this)
        scanHelper.setOnScanSuccessListener(object : DeviceScanHelper.OnScanSuccessListener {
            override fun onSuccess(result: String?) {
                result?.let {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                    tv_result.text = it
                }
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCAN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val bundle = data?.getBundleExtra(Constants.SCAN_RESULT_DATA)
            if (null == bundle) {
                Toast.makeText(this@MainActivity, "扫描结果为空~", Toast.LENGTH_SHORT).show()
            } else {
                val barCode = bundle.getString(DecodeHandler.BAR_CODE_KEY) ?: ""
                tv_result.text = barCode

                val albumPicPath = bundle.getString(DecodeHandler.ALBUM_PIC_KEY) ?: ""

                Log.d(
                    "TAG",
                    "onActivityResult: ----------------------barCode---->${barCode}"
                )
                Log.d(
                    "TAG",
                    "onActivityResult: ----------------------albumPicPath---->${albumPicPath}"
                )
                if (albumPicPath.isNotEmpty()) {
                    Glide.with(this).load(albumPicPath).into(iv_result)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        scanHelper.onResume()
    }

    override fun onPause() {
        super.onPause()
        scanHelper.onPause()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == SCAN_REQUEST_CODE) {
            CaptureActivity.start(this@MainActivity, SCAN_REQUEST_CODE, true)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this@MainActivity, "应用没有拍照权限，请在手机设置中修改~", Toast.LENGTH_SHORT).show()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_scanner -> {
                checkScannerPermission()
            }
            else -> {
            }
        }
    }

    @AfterPermissionGranted(SCAN_REQUEST_CODE)
    private fun checkScannerPermission() {
        if (EasyPermissions.hasPermissions(this, *CAMERA_PERMISSIONS)) {
            CaptureActivity.start(this@MainActivity, SCAN_REQUEST_CODE, true)
        } else {
            EasyPermissions.requestPermissions(
                this,
                "应用需要访问相机权限!",
                SCAN_REQUEST_CODE,
                *CAMERA_PERMISSIONS
            )
        }
    }
}