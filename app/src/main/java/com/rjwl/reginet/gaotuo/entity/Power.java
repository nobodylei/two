package com.rjwl.reginet.gaotuo.entity;

/**
 * Created by Administrator on 2018/5/30.
 * 卡包数据
 */

public class Power {
    private String elevatorNumber;
    private int id;
    private String powerName;
    private int powerNumber;

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

    @Override
    public String toString() {
        return "Power{" +
                "elevatorNumber='" + elevatorNumber + '\'' +
                ", id=" + id +
                ", powerName='" + powerName + '\'' +
                ", powerNumber=" + powerNumber +
                '}';
    }
}
