package com.linsr.wanandroid.data.model.to;

import android.content.Intent;

import java.io.Serializable;

import lombok.Data;

/**
 * Description
 @author Linsr
 */
@Data
public class AuthVehicleTo implements Serializable{

    private String vehicleNum;
    private String vehicleMode;
    private String vehicleLength;
    private String vehicleLoad;
    private String vehicleBrand;
    private String vehicleDate;
    private String idcard;
    private Integer gender;
    private String userName;

    public AuthVehicleTo() {

    }

    public AuthVehicleTo(String vehicleNum,
                         String vehicleMode,
                         String vehicleLength,
                         String vehicleLoad,
                         String vehicleBrand,
                         String vehicleDate) {
        this.vehicleNum = vehicleNum;
        this.vehicleMode = vehicleMode;
        this.vehicleLength = vehicleLength;
        this.vehicleLoad = vehicleLoad;
        this.vehicleBrand = vehicleBrand;
        this.vehicleDate = vehicleDate;
    }
}
