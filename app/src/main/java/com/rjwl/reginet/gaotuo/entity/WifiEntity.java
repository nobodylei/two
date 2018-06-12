package com.rjwl.reginet.gaotuo.entity;

/**
 * Created by Administrator on 2018/1/24.
 */

public class WifiEntity {

    private String ssid ;
    private String bssid ;
    private String capabilities;
    private int frequency;
    private int level;

    public WifiEntity(String ssid, String bssid, String capabilities, int frequency, int level) {
        this.ssid = ssid;
        this.bssid = bssid;
        this.capabilities = capabilities;
        this.frequency = frequency;
        this.level = level;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
