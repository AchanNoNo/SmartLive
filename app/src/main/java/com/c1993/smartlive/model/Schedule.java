package com.c1993.smartlive.model;

import java.io.Serializable;

/**
 * Created by c1993 on 2016/5/11.
 */
public class Schedule implements Serializable{
    private int id;
    private String s_name;
    private String s_time;
    private String u_name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_time() {
        return s_time;
    }

    public void setS_time(String s_time) {
        this.s_time = s_time;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }
}
