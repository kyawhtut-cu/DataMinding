package com.kyawhtut.dataminding.util.data;

/**
 * Created by KyawHtut on 3/9/2018.
 */

public class XYModel {

    private String name;
    private float x;
    private float y;

    public XYModel(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
