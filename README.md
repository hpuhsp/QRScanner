# QRScanner 安卓扫码工具库(支持二维码、条形码)

## A library of broom tools, custom camera sweep codes and PDA laser scanning,support for QR codes and barcodes.

---

* 手电筒模式开关并实现自动对焦。
* 附带扫码音效。
* 自定义扫码UI，可进行二次定制开发。

### Version

```
implementation 'com.shuanghui.mobile:scanner:1.2.0'
```
### Usage

* Step1：开启扫码
```
 if (EasyPermissions.hasPermissions(this, *CAMERA_PERMISSIONS)) {
            CaptureActivity.start(this@MainActivity, SCAN_REQUEST_CODE)
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "应用需要访问相机权限!",
                    SCAN_REQUEST_CODE,
                    *CAMERA_PERMISSIONS
            )
        }
```
* Step2：结果处理
```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == SCAN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
        val barCode = data?.getStringExtra(Constants.SCAN_BAR_CODE_RESULT)
        if (barCode.isNullOrEmpty()) {
            Toast.makeText(this@MainActivity, "扫描结果为空~", Toast.LENGTH_SHORT).show()
        } else {
            tv_result.text = barCode
        }
    }
}
```


