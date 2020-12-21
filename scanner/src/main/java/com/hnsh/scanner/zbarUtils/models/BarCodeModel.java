package com.hnsh.scanner.zbarUtils.models;

/**
 * Function:
 * <br/>
 * Describe:
 * <br/>
 * Author: reesehu on 16/5/13.
 * <br/>
 * Email: reese@jiuhuar.com
 * {
 * "barcodePic": "string",
 * "beerName": "string",
 * "beerPic": "string",
 * "inputCode": "string",
 * "scanCode": "string"
 * }
 */


public class BarCodeModel {
    private String barcodePic;
    private String beerName;
    private String beerPic;
    private String inputCode;
    private String scanCode;

    public BarCodeModel() {
    }

    public BarCodeModel(String barcodePic, String beerName, String beerPic, String inputCode, String scanCode) {
        this.barcodePic = barcodePic;
        this.beerName = beerName;
        this.beerPic = beerPic;
        this.inputCode = inputCode;
        this.scanCode = scanCode;
    }

    public String getBarcodePic() {
        return barcodePic;
    }

    public void setBarcodePic(String barcodePic) {
        this.barcodePic = barcodePic;
    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public String getBeerPic() {
        return beerPic;
    }

    public void setBeerPic(String beerPic) {
        this.beerPic = beerPic;
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }
}
