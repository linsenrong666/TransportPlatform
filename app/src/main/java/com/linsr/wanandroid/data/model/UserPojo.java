package com.linsr.wanandroid.data.model;

import io.reactivex.internal.operators.single.SingleDoAfterTerminate;
import lombok.Data;

/**
 * Desc
 * Created by linsenrong on 2018/4/9.
 */
@Data
public class UserPojo {

    private Long id;
    private String user_name;
    private String password;
    private String phone_number;
    private String certified_status;
    private String address;
    private String id_card;
    private String driving_license;
    private String vehicle_license;
    private String vehicle_number;
    private String vehicle_mode;
    private Double vehicle_length;
    private Double vehicle_load;
    private String vehicle_brand;
    private String vehicle_production_date;
    private String user_code;
    private Integer gender;
    private String create_time;
    private String token;

}