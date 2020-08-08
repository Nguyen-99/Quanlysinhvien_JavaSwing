/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.MarkTable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen
 */
public class MarkTableDao {
    public List<MarkTable> getAllMarkTable(){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT * FROM bangdiem";
        List<MarkTable> mTs=new ArrayList<>();
        try {
            PreparedStatement p=con.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                MarkTable mT=new MarkTable();
                mT.setMaBangDiem(rs.getInt("MaBangDiem"));
                mT.setMaMon(rs.getInt("MaMon"));
                mT.setLopMon(rs.getString("LopMon"));
                mT.setNgayThi(rs.getDate("NgayThi"));
                mT.setHocKi(rs.getString("HocKi"));
                mTs.add(mT);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MarkTableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkTableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mTs;
    }
    public void addMarkTable(MarkTable mT){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="INSERT INTO bangdiem VALUES(?,?,?,?,?)";
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,mT.getMaBangDiem());
            p.setInt(2,mT.getMaMon());
            p.setString(3,mT.getLopMon());
            p.setDate(4,mT.getNgayThi());
            p.setString(5,mT.getHocKi());
            
            int rs=p.executeUpdate();
            System.out.println(rs);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkTableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateMarkTable(MarkTable mT){
        try {
            Connection con=JDBCConnection.getJDBCConnection();
            String sql="UPDATE bangdiem SET MaMon=?,LopMon=?,NgayThi=?,HocKi=? WHERE MaBangDiem=?";
            
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,mT.getMaMon());
            p.setString(2,mT.getLopMon());
            p.setDate(3,mT.getNgayThi());
            p.setString(4,mT.getHocKi());
            p.setInt(5,mT.getMaBangDiem());
            int rs=p.executeUpdate();
            System.out.println(rs);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkTableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteMarkTable(int id){
        try {
            Connection con=JDBCConnection.getJDBCConnection();
            String sql="DELETE FROM bangdiem WHERE MaBangDiem=?";
            
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,id);
            int rs=p.executeUpdate();
            System.out.println(rs);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MarkTableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public MarkTable getMarkTableById(int id){
        try {
            Connection con=JDBCConnection.getJDBCConnection();
            String sql="SELECT * FROM bangdiem WHERE MaBangDiem=?";
            
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                MarkTable mT=new MarkTable();
                mT.setMaBangDiem(id);
                mT.setMaMon(rs.getInt("MaMon"));
                mT.setLopMon(rs.getString("LopMon"));
                mT.setNgayThi(rs.getDate("NgayThi"));
                mT.setHocKi(rs.getString("HocKi"));
                con.close();
                return mT;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkTableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public String getTenMonByMBD(int id){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT TenMon FROM monhoc WHERE MaMon=(SELECT MaMon FROM bangdiem WHERE MaBangDiem=?)";
        
        try {  
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,id);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                String tm=rs.getString("TenMon");
                con.close();
                return tm;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkTableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public int getSoTinByMBD(int id){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT SoTin FROM monhoc WHERE MaMon=(SELECT MaMon FROM bangdiem WHERE MaBangDiem=?)";
        
        try {  
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,id);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                int  tm=rs.getInt("SoTin");
                con.close();
                return tm;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkTableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public String getLop(int id){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT LopMon FROM bangdiem WHERE MaBangDiem=?";
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                String lm=rs.getString("LopMon");
                con.close();
                return lm;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkTableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public String getHocKy(int id){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT HocKi FROM bangdiem WHERE MaBangDiem=?";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                String hk=rs.getString("HocKi");
                con.close();
                return hk;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkTableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public String getTenMon(int id){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT TenMon FROM monhoc WHERE MaMon=?";
        
        try {  
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,id);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                String tm=rs.getString("TenMon");
                con.close();
                return tm;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarkTableDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public ArrayList<MarkTable> findMarkTableByName(String name) throws SQLException{
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT * FROM bangdiem WHERE MaBangDiem='"+name+"' "
                +"OR LopMon='"+name+"' OR MaMon=(SELECT MaMon FROM monhoc WHERE TenMon='"+name+"')";
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery(sql);
        ArrayList<MarkTable> markTables=new ArrayList<>(); 
        while(rs.next()){
            MarkTable markTable=new MarkTable();
            markTable.setMaBangDiem(rs.getInt("MaBangDiem"));
            markTable.setMaMon(rs.getInt("MaMon"));
            markTable.setLopMon(rs.getString("LopMon"));
            markTable.setNgayThi(rs.getDate("NgayThi"));
            markTable.setHocKi(rs.getString("HocKi"));
            markTables.add(markTable);
        }
        con.close();
        return markTables;
    }
}
