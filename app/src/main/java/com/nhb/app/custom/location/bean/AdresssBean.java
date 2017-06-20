package com.nhb.app.custom.location.bean;

import android.support.annotation.Keep;

import java.io.Serializable;

/**
 * 
 *@Title:
 *@Description:地址的bean
 */
@Keep
public class AdresssBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2868117647435156788L;


    public String address;

    public double latitude;

    public double longitude;

    public double radius;

    public String province;

    public String city;

    public String district;

    @Override
    public String toString() {
        return "AdresssBean [address=" + address + ", latitude=" + latitude + ", longitude=" + longitude + ", radius=" + radius + ", province=" + province + ", city=" + city + ", district=" + district + "]";
    }
}
