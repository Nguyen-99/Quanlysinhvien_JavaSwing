/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Mark;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen
 */
public class MarkDao {
    public List<Mark> getMark(int maBangDiem){
        List<Mark> marks=new ArrayList<>();
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT * FROM diemthi WHERE MaBangDiem=?";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,maBangDiem);
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
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return marks;
    }
    public String getHoTen(int id){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT HoTen FROM sinhvien WHERE MaSv=?";      
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,id);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                String ht=rs.getString("HoTen");
                return ht;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public String getLop(int id){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT Lop FROM sinhvien WHERE MaSv=?";      
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,id);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                String l=rs.getString("Lop");
                return l;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void addMark(Mark m){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="INSERT INTO diemthi VALUE(?,?,?,?) ";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1, m.getMaBangDiem());
            p.setInt(2, m.getMaSv());
            p.setDouble(3, m.getDqt());
            p.setDouble(4, m.getDhk());
            
            int rs=p.executeUpdate();
            System.out.println(rs);
        } catch (SQLException ex) {
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteMark(int mBD,int mSv){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="DELETE FROM diemthi WHERE MaBangDiem=? AND MaSv=?";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,mBD);
            p.setInt(2,mSv);
            
            int rs=p.executeUpdate();
            System.out.println(rs);
        } catch (SQLException ex) {
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Mark getMarkById(int mBD,int mSV){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT * FROM diemthi where MaBangDiem=? AND MaSv=?";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,mBD);
            p.setInt(2, mSV);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                Mark m=new Mark();
                m.setMaBangDiem(rs.getInt("MaBangDiem"));
                m.setMaSv(rs.getInt("MaSv"));
                m.setDqt(rs.getDouble("Dqt"));
                m.setDhk(rs.getDouble("Dhk"));
                return m;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void updateMark(Mark m,int mSV){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="UPDATE `hello`.`diemthi` SET `MaSv` =?, `Dqt` =?, `Dhk` =? WHERE (`MaBangDiem` = ?) and (`MaSv` = ?)";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,m.getMaSv());
            p.setDouble(2,m.getDqt());
            p.setDouble(3,m.getDhk());
            p.setInt(4,m.getMaBangDiem());
            p.setInt(5,mSV);
            
            int rs=p.executeUpdate();
            System.out.println(rs);
        } catch (SQLException ex) {
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
