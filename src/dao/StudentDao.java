/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Student;
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
public class StudentDao {
    public List<Student> getAllStudent(){
        List<Student> students=new ArrayList<>();
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT * FROM sinhvien";
        
        PreparedStatement p;
        try {
            p = con.prepareStatement(sql);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                Student student=new Student();
                student.setMaSv(rs.getInt("MaSv"));
                student.setHoTen(rs.getString("HoTen"));
                student.setGioiTinh(rs.getString("GioiTinh"));
                student.setNgaySinh(rs.getDate("NgaySinh"));
                student.setLop(rs.getString("Lop"));
                student.setDiaChi(rs.getString("DiaChi"));
                students.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return students;  
        
    }
    public void addStudent(Student s){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="INSERT INTO sinhvien VALUES(?,?,?,?,?,?)";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,s.getMaSv());
            p.setString(2, s.getHoTen());
            p.setString(3, s.getGioiTinh());
            p.setDate(4,s.getNgaySinh());
            p.setString(5, s.getLop());
            p.setString(6, s.getDiaChi());
                    
            int rs=p.executeUpdate();
            System.out.println(rs);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void updateStudent(Student s){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="UPDATE sinhvien SET HoTen=?,GioiTinh=?,NgaySinh=?,Lop=?,DiaChi=? WHERE MaSv=?";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setString(1,s.getHoTen());
            p.setString(2,s.getGioiTinh());
            p.setDate(3,s.getNgaySinh());
            p.setString(4,s.getLop());
            p.setString(5,s.getDiaChi());
            p.setInt(6,s.getMaSv());
            int rs=p.executeUpdate();
            System.out.println(rs);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteStudent(int id){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="DELETE FROM sinhvien WHERE MaSv=?";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,id);
            
            int rs=p.executeUpdate();
            System.out.println(rs);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Student getStudentById(int id){
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT * FROM sinhvien WHERE MaSv=?";
        
        try {
            PreparedStatement p=con.prepareStatement(sql);
            p.setInt(1,id);
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                Student student=new Student();
                student.setMaSv(rs.getInt("MaSv"));
                student.setHoTen(rs.getString("HoTen"));
                student.setGioiTinh(rs.getString("GioiTinh"));
                student.setNgaySinh(rs.getDate("NgaySinh"));
                student.setLop(rs.getString("Lop"));
                student.setDiaChi(rs.getString("DiaChi"));
                con.close();
                return student;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
             
        }
        return null;
    }
    public ArrayList<Student> findAllStudentByName(String name) throws SQLException{
        Connection con=JDBCConnection.getJDBCConnection();
        String sql="SELECT * FROM sinhvien WHERE (MaSv='"+name+"' OR HoTen='"+name+"' OR Lop='"+name+"')";
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery(sql);
        ArrayList<Student> studentList=new ArrayList<>();
        while(rs.next()){
            Student student=new Student();
            student.setMaSv(rs.getInt("MaSv"));
            student.setHoTen(rs.getString("HoTen"));
            student.setGioiTinh(rs.getString("GioiTinh"));
            student.setNgaySinh(rs.getDate("NgaySinh"));
            student.setLop(rs.getString("Lop"));
            student.setDiaChi(rs.getString("Diachi"));
            studentList.add(student);
        }
        con.close();
        return studentList;
    }
}
