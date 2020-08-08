/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Mark;

/**
 *
 * @author Admin
 */
public class MarkOfStudentDao {
    public List<Mark> getMark(int maSV){
        List<Mark> marks=new ArrayList<>();
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT * FROM diemthi WHERE MaSv=?";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1, maSV);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                Mark m=new Mark();
                m.setMaBangDiem(rs.getInt("MaBangDiem"));
                m.setMaSv(rs.getInt("MaSv"));
                m.setDqt(rs.getDouble("Dqt"));
                m.setDhk(rs.getDouble("Dhk"));
                marks.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkOfStudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkOfStudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return marks;
    }
    public int getMaMon(int maBD){
        int maMon=0;
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT MaMon FROM BangDiem WHERE MaBangDiem=?";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,maBD);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                maMon=rs.getInt("MaMon");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkOfStudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkOfStudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maMon;
    }
    public List<Mark> getMarkByHK(int id,String hk){
        List<Mark> marks=new ArrayList<>();
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT * FROM hello.diemthi INNER JOIN hello.bangdiem ON diemthi.MaBangDiem=bangdiem.MaBangDiem WHERE (MaSv=? AND HocKi=?)";
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,id);
            p.setString(2,hk);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                Mark m=new Mark();
                m.setMaBangDiem(rs.getInt("MaBangDiem"));
                m.setMaSv(rs.getInt("MaSv"));
                m.setDqt(rs.getDouble("Dqt"));
                m.setDhk(rs.getDouble("Dhk"));
                marks.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkOfStudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkOfStudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return marks;
    }
}
