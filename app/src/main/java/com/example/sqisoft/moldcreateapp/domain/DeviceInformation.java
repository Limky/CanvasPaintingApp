package com.example.sqisoft.moldcreateapp.domain;

/**
 * Created by SQISOFT on 2017-04-17.
 */

public class DeviceInformation {
    private String deviceSeq;
    private String deviceCode;
    private String deviceType;
    private String deviceName;
    private String deviceIp;
    private String devicePort;
    private String deviceRegDate;
    private String deviceModDate;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceSeq() {
        return deviceSeq;
    }

    public void setDeviceSeq(String deviceSeq) {
        this.deviceSeq = deviceSeq;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getDevicePort() {
        return devicePort;
    }

    public void setDevicePort(String devicePort) {
        this.devicePort = devicePort;
    }

    public String getDeviceRegDate() {
        return deviceRegDate;
    }

    public void setDeviceRegDate(String deviceRegDate) {
        this.deviceRegDate = deviceRegDate;
    }

    public String getDeviceModDate() {
        return deviceModDate;
    }

    public void setDeviceModDate(String deviceModDate) {
        this.deviceModDate = deviceModDate;
    }



}
