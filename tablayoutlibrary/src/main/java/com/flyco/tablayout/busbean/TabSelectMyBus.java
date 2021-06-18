package com.flyco.tablayout.busbean;

/**
 * Created by 孟晨 on 2018/7/14.
 */

public class TabSelectMyBus {
    private boolean isSelectMy = false;

    public TabSelectMyBus(boolean isSelectMy) {
        this.isSelectMy = isSelectMy;
    }

    public boolean isSelectMy() {
        return isSelectMy;
    }

    public void setSelectMy(boolean selectMy) {
        isSelectMy = selectMy;
    }
}
