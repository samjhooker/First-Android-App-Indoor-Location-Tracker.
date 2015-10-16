package com.jocusinteractive.firstproject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import java.lang.Math;

/**
 * Created by Samuel on 14/10/15.
 */
public class Location{

    private String name;
    private Double x;
    private Double y;
    private Double z;


    public Location (String name, Double x, Double y, Double z){
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getZ() {
        return z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Pair<Double, Double> getLatLng(){
        Double radius = 6378137.0;

        Double nDistance = this.getY();
        Double eDistance = this.getX();

        Double dLat = nDistance/radius;
        Double dLng = eDistance/(radius*Math.cos(Math.PI * dLat / 180));

        return new Pair<>((dLat * 180/Math.PI), (dLng * 180/Math.PI));

    }

    public String getDescription(){
        String string =  "x:"+x+"    y:" + y+ "   g z:" +z;
        return string;
    }


}
