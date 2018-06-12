package com.rjwl.reginet.gaotuo.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class Xiaoqu {

    private List<Kabao> towerPowerList;
    private User tower;
    private String home;
    private String card;

    public Xiaoqu(List<Kabao> towerPowerList, User tower, String card) {
        this.towerPowerList = towerPowerList;
        this.tower = tower;
        this.card = card;
    }

    public List<Kabao> getTowerPowerList() {
        return towerPowerList;
    }

    public void setTowerPowerList(List<Kabao> towerPowerList) {
        this.towerPowerList = towerPowerList;
    }

    public User getTower() {
        return tower;
    }

    public void setTower(User tower) {
        this.tower = tower;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    @Override
    public String toString() {
        return "Xiaoqu{" +
                "towerPowerList=" + towerPowerList +
                ", tower=" + tower +
                ", home='" + home + '\'' +
                ", card='" + card + '\'' +
                '}';
    }

    public Xiaoqu(List<Kabao> list) {
        this.towerPowerList = list;
    }

    public Xiaoqu(List<Kabao> list, String home) {
        this.towerPowerList = list;
        this.home = home;
    }

    public Xiaoqu() {

    }
}
