/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author Nguyen
 */
public class MarkTable {
    private int maBangDiem;
    private int maMon;
    private String lopMon;
    private Date ngayThi;
    private String hocKi;

    public int getMaBangDiem() {
        return maBangDiem;
    }

    public void setMaBangDiem(int maBangDiem) {
        this.maBangDiem = maBangDiem;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public String getLopMon() {
        return lopMon;
    }

    public void setLopMon(String lopMon) {
        this.lopMon = lopMon;
    }

    public Date getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(Date ngayThi) {
        this.ngayThi = ngayThi;
    }

    public String getHocKi() {
        return hocKi;
    }

    public void setHocKi(String hocKi) {
        this.hocKi = hocKi;
    }
    
}
