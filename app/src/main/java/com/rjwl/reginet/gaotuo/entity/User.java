package com.rjwl.reginet.gaotuo.entity;

/**
 * Created by Administrator on 2018/1/27.
 */

public class User {
    private String userName;
    private String passWord;
    private String Phone;
    private String tel;//手机号
    private String state;
    private String name;
    private int companyId;
    private int id;
    private String wifi;
    private String towerName;
    private String elevatorNumber;
    private int elevatoId;
    private String companyName;

    public User() {

    }

    public User(String wifi, String towerName, String elevatorNumber) {
        this.wifi = wifi;
        this.towerName = towerName;
        this.elevatorNumber = elevatorNumber;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getTowerName() {
        return towerName;
    }

    public void setTowerName(String towerName) {
        this.towerName = towerName;
    }

    public String getElevatorNumber() {
        return elevatorNumber;
    }

    public void setElevatorNumber(String elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
    }

    public int getElevatoId() {
        return elevatoId;
    }

    public void setElevatoId(int elevatoId) {
        this.elevatoId = elevatoId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", Phone='" + Phone + '\'' +
                ", tel='" + tel + '\'' +
                ", state='" + state + '\'' +
                ", name='" + name + '\'' +
                ", companyId=" + companyId +
                ", id=" + id +
                ", wifi='" + wifi + '\'' +
                ", towerName='" + towerName + '\'' +
                ", elevatorNumber='" + elevatorNumber + '\'' +
                ", elevatoId=" + elevatoId +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
