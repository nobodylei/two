package com.rjwl.reginet.gaotuo.entity;

import java.util.List;

/**
 * Created by yanle on 2018/6/6.
 */

public class AllCard {
    private List<Xiaoqu> xiaoquList;

    public AllCard() {

    }
    public AllCard(List<Xiaoqu> list) {
        this.xiaoquList = list;
    }

    public List<Xiaoqu> getXiaoquList() {
        return xiaoquList;
    }

    public void setXiaoquList(List<Xiaoqu> xiaoquList) {
        this.xiaoquList = xiaoquList;
    }

    @Override
    public String toString() {
        return "AllCard{" +
                "xiaoquList=" + xiaoquList +
                '}';
    }
}
