/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Subject;
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
public class SubjectDao {
    public List<Subject> getALLSubject(){
        List<Subject> subjects=new ArrayList<Subject>();
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="Select * from monhoc";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                Subject subject=new Subject();
                subject.setMaMon(rs.getInt("MaMon"));
                subject.setTenMon(rs.getString("Tenmon"));
                subject.setSoTin(rs.getInt("SoTin"));
                
                subjects.add(subject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }
    public void addSubject(Subject s){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="Insert into monhoc value(?,?,?)";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,s.getMaMon());
            p.setString(2,s.getTenMon());
            p.setInt(3,s.getSoTin());
            
            int rs=p.executeUpdate();
            System.out.println(rs);
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateSubject(Subject s){
        Connection con=JDBCConnection.getJDBCConnection();
        
        String sql="Update monhoc set TenMon=?,SoTin=? where MaMon=?";
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setString(1,s.getTenMon());
            p.setInt(2,s.getSoTin());
            p.setInt(3,s.getMaMon());
            int rs=p.executeUpdate();
            System.out.println(rs);
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteSubject(int id){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="delete from monhoc where MaMon=?";
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1, id);
            int rs=p.executeUpdate();
            System.out.println(rs);
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Subject getSubjectById(int id){
        
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="Select * from monhoc where MaMon=?";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                Subject subject=new Subject();
                subject.setMaMon(rs.getInt("MaMon"));
                subject.setTenMon(rs.getString("Tenmon"));
                subject.setSoTin(rs.getInt("SoTin"));
                
                return subject;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<Subject> findAllSubjectByName(String name) throws SQLException{
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT * FROM monhoc WHERE (TenMon='"+name+"' OR MaMon='"+name+"')";
        //trong sql string the hien bang dau nhay don,name la bien String chu ko phai chuoi ki tu nen phai them +
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery(sql);
        ArrayList<Subject> subjectList=new ArrayList<Subject>();
        while(rs.next()){
            Subject subject=new Subject();
            subject.setMaMon(rs.getInt("MaMon"));
            subject.setTenMon(rs.getString("TenMon"));
            subject.setSoTin(rs.getInt("SoTin"));
            subjectList.add(subject);
        }
        con.close();
        return subjectList;
    }
}
