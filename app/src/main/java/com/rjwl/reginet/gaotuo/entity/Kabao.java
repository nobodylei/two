package com.rjwl.reginet.gaotuo.entity;

/**
 * Created by Administrator on 2018/1/23.
 */

public class Kabao {

    private String wifiName;
    private String wifiPassword;
    private String code;
    private String elevatorNumber;
    private int id;
    private String powerName;
    private int powerNumber;

    public Kabao(String powerName) {
        this.powerName = powerName;
    }

    public String getElevatorNumber() {
        return elevatorNumber;
    }

    public void setElevatorNumber(String elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public int getPowerNumber() {
        return powerNumber;
    }

    public void setPowerNumber(int powerNumber) {
        this.powerNumber = powerNumber;
    }

    public Kabao() {
    }

    public Kabao(String wifiName, String wifiPassword, String code) {
        this.wifiName = wifiName;
        this.wifiPassword = wifiPassword;
        this.code = code;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getWifiPassword() {
        return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Kabao{" +
                "wifiName='" + wifiName + '\'' +
                ", wifiPassword='" + wifiPassword + '\'' +
                ", code='" + code + '\'' +
                ", elevatorNumber='" + elevatorNumber + '\'' +
                ", id=" + id +
                ", powerName='" + powerName + '\'' +
                ", powerNumber=" + powerNumber +
                '}';
    }
}
