/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.MarkOfStudentDao;
import dao.MarkTableDao;
import dao.StudentDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Mark;
import model.Student;

/**
 *
 * @author Admin
 */
public class MarkOfStudent extends javax.swing.JFrame {
    Student student;
    StudentDao studentDao;
    MarkOfStudentDao  markOfStudentDao;
    DefaultTableModel dTM;
    MarkTableDao markTableDao;
    /**
     * Creates new form MarkOfStudent
     */
    public MarkOfStudent(int id) {
        initComponents();
        this.setLocationRelativeTo(null);
        studentDao=new StudentDao();
        student=studentDao.getStudentById(id);
        markOfStudentDao=new MarkOfStudentDao();
        markTableDao=new MarkTableDao();
        dTM=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        markStudentTable.setModel(dTM);
        dTM.addColumn("Stt");
        dTM.addColumn("Mã môn");
        dTM.addColumn("Tên môn");
        dTM.addColumn("Số tín");
        dTM.addColumn("Điểm quá trình");
        dTM.addColumn("Điểm học kỳ");
        dTM.addColumn("TK(10)");
        dTM.addColumn("TK(chữ)");
        dTM.addColumn("TK(4)");
        dTM.addTableModelListener(markStudentTable);
        
        markStudentTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        markStudentTable.getColumnModel().getColumn(1).setPreferredWidth(5);
        markStudentTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        markStudentTable.getColumnModel().getColumn(3).setPreferredWidth(5);
        markStudentTable.getColumnModel().getColumn(4).setPreferredWidth(30);
        markStudentTable.getColumnModel().getColumn(5).setPreferredWidth(5);
        markStudentTable.getColumnModel().getColumn(6).setPreferredWidth(5);
        markStudentTable.getColumnModel().getColumn(7).setPreferredWidth(5);
        markStudentTable.getColumnModel().getColumn(8).setPreferredWidth(5);
       
                
        
        maSV.setText(String.valueOf(student.getMaSv()));
        tenSV.setText(student.getHoTen());
        phai.setText(student.getGioiTinh());
        noiSinh.setText(student.getDiaChi());
        lop.setText(student.getLop());
        
        //setTableData(markOfStudentDao.getMark(id));
        List<String> hks=this.getAllHocKy(markOfStudentDao.getMark(id));
        for(String hk:hks){
            dTM.addRow(new Object[]{hk.toUpperCase()});
            setTableData(markOfStudentDao.getMarkByHK(id,hk));
            dTM.addRow(new Object[]{"Điểm trung bình học kỳ hệ 10: "+tbhk10(id, hk)});
            dTM.addRow(new Object[]{"Điểm trung bình học kỳ hệ 4: "+tbhk4(id, hk)});
            dTM.addRow(new Object[]{"Điểm trung bình tích lũy(10): "+tbtl10(id,hk)});
            dTM.addRow(new Object[]{"Điểm trung bình tích lũy(4): "+tbtl4(id,hk)});
            dTM.addRow(new Object[]{"Số tín chỉ đạt: "+soTinChiDat(id, hk)});
            dTM.addRow(new Object[]{"Số tín tích lũy: "+soTinTichLuy(id, hk)});
            dTM.addRow(new Object[]{"Phân loại ĐTB HK: "+pLHK(id,hk)});
            dTM.addRow(new Object[]{});
        }
    }
    private List<String> getAllHocKy(List<Mark> marks){
        List<String> hks=new ArrayList<>();
        for(Mark mark:marks){
            int maBD=mark.getMaBangDiem();
            String hk=markTableDao.getHocKy(maBD);
            if(hks.contains(hk)==false){
                hks.add(hk);
                //Collections.sort(hks);
            }
        }
        return hks;
    }
   
    private void setTableData(List<Mark> marks){
        int i=1;
        //dTM.addRow("Học Kỳ");
        for(Mark mark:marks){
            int maSv=mark.getMaSv();
            int maBD=mark.getMaBangDiem();
            double dqt=mark.getDqt();
            double dhk=mark.getDhk();
            
            dTM.addRow(new Object[]{i++,markOfStudentDao.getMaMon(maBD),markTableDao.getTenMonByMBD(maBD),
                markTableDao.getSoTinByMBD(maBD),dqt,dhk,tK10(dqt,dhk),tKChu(dqt,dhk),tK4(dqt,dhk)});
        }
    }
    private float tK10(double dqt,double dhk){
        float tk=(float) (dqt*0.3+dhk*0.7);
        float tk1=(float)Math.round(tk * 10) / 10;
        return tk1;
    }
    private String tKChu(double dqt,double dhk){
        String tk=null;
        double tk10=tK10(dqt,dhk);
        if(tk10>=8.5){
            tk="A";
        }else if(tk10>=8){
            tk="B+";
        }else if(tk10>=7){
            tk="B";
        }else if(tk10>=6.5){
            tk="C+";
        }else if(tk10>=5.5){
            tk="C";
        }else if(tk10>=5){
            tk="D+";
        }else if(tk10>=4){
            tk="D";
        }else{
            tk="F";
        }
        return tk;
    }
    private double tK4(double dqt,double dhk){
        double tk=0;
        switch(tKChu(dqt,dhk)){
            case "A":
                tk=4;
                break;
            case "B+":
                tk=3.5;
                break;
            case "B":
                tk=3;
                break;
            case "C+":
                tk=2.5;
                break;
            case "C":
                tk=2;
                break;
            case "D+":
                tk=1.5;
                break;
            case "D":
                tk=1;
                break;
            case "F":
                tk=0;
                break;
           
        }
        return tk;
    }
    private int soTinChiHK(int id,String hk){
        int x=0;
        List<Mark> marks=new ArrayList<>();
        marks=markOfStudentDao.getMarkByHK(id, hk);
        for(Mark mark:marks){            
            x+=markTableDao.getSoTinByMBD(mark.getMaBangDiem());
        }
        return x;
    }
    private int soTinChiDat(int id,String hk){
        int x=0;
        List<Mark> marks=new ArrayList<>();
        marks=markOfStudentDao.getMarkByHK(id, hk);
        for(Mark mark:marks){
            if(tK4(mark.getDqt(),mark.getDhk())!=0){
                 x+=markTableDao.getSoTinByMBD(mark.getMaBangDiem());
            }   
        }
        return x;
    }
    private int soTinTichLuy(int id,String hk){
        int x=0;
        List<String> hks=this.getAllHocKy(markOfStudentDao.getMark(id));
        Collections.sort(hks);
        for(String hocky:hks){
            x+=soTinChiDat(id, hocky);
            if(hocky.equals(hk)){
                break;
            }
        }
         return x;
    }
    private int soTinCacHK(int id,String hk){
        int x=0;
        List<String> hks=this.getAllHocKy(markOfStudentDao.getMark(id));
        Collections.sort(hks);
        for(String hocky:hks){
            x+=soTinChiHK(id, hocky);
            if(hocky.equals(hk)){
                break;
            }
        }
         return x;
    }
    double tbhk10(int id,String hk){
        List<Mark> marks=markOfStudentDao.getMarkByHK(id, hk);
        double tb=0;
        double temp=0;
        for(Mark mark:marks){
            temp+=tK10(mark.getDqt(),mark.getDhk())*markTableDao.getSoTinByMBD(mark.getMaBangDiem());
        }
        tb=temp/soTinChiHK(id, hk);
        tb=(double) Math.round(tb * 100) / 100;
        return tb;
    }
    double tbhk4(int id,String hk){
        List<Mark> marks=markOfStudentDao.getMarkByHK(id, hk);
        double tb=0;
        double temp=0;
        for(Mark mark:marks){
            temp+=tK4(mark.getDqt(),mark.getDhk())*markTableDao.getSoTinByMBD(mark.getMaBangDiem());
            
        }   
        tb=temp/(soTinChiHK(id, hk));
        tb=(double) Math.round(tb * 100) / 100;
        return tb;
    }
    double tbtl10(int id,String hk){
        double tb=0;
        List<String> hks=this.getAllHocKy(markOfStudentDao.getMark(id));
        Collections.sort(hks);
        double temp=0;
        for(String hocky:hks){
            List<Mark> marks=markOfStudentDao.getMarkByHK(id,hocky);
            for(Mark mark:marks){
                if(tK10(mark.getDqt(),mark.getDhk())>=4){
                    temp+=tK10(mark.getDqt(),mark.getDhk())*markTableDao.getSoTinByMBD(mark.getMaBangDiem());
                   
                }
            }
            if(hocky.equals(hk)){
            break;
            }
        }
         tb=temp/soTinTichLuy(id,hk);
        double tb1=(double)(Math.round(tb*100))/100;
        return tb1;
    }
    double tbtl4(int id,String hk){
        double tb=0;
        double temp=0;
        List<String> hks=this.getAllHocKy(markOfStudentDao.getMark(id));
        Collections.sort(hks);
        for(String hocky:hks){
            List<Mark> marks=markOfStudentDao.getMarkByHK(id, hocky);
            for(Mark mark:marks){
                temp+=tK4(mark.getDqt(),mark.getDhk())*markTableDao.getSoTinByMBD(mark.getMaBangDiem());
                
            }
            if(hocky.equals(hk)){
                break;
            }
        }
        tb=temp/soTinTichLuy(id,hk);
        double tb1=(double)(Math.round(tb*100))/100;
        return tb1;
    }
    String pLHK(int id,String hk){
        String rs="";
        if(tbhk4(id, hk)>=3.6){
            rs="Xuất sắc";
        }else if(tbhk4(id, hk)>=3.2){
            rs="Giỏi";
        }else if(tbhk4(id, hk)>=2.5){
            rs="Khá";
        }else{
            rs="Trung bình";
        }
        return rs;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        maSV = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tenSV = new javax.swing.JLabel();
        phai = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        noiSinh = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lop = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        markStudentTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 1, 1, new java.awt.Color(0, 0, 0)));

        jLabel2.setText("Mã sinh viên");

        maSV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        maSV.setText("jLabel3");

        jLabel4.setText("Tên sinh viên");

        jLabel5.setText("Phái");

        tenSV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tenSV.setText("jLabel6");

        phai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        phai.setText("jLabel7");

        jLabel8.setText("Nơi sinh ");

        noiSinh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        noiSinh.setText("jLabel9");

        jLabel10.setText("Lớp");

        lop.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lop.setText("jLabel11");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(maSV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tenSV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(noiSinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(phai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lop, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(maSV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tenSV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(phai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(noiSinh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lop))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        markStudentTable.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        markStudentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(markStudentTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(362, 362, 362)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lop;
    private javax.swing.JLabel maSV;
    private javax.swing.JTable markStudentTable;
    private javax.swing.JLabel noiSinh;
    private javax.swing.JLabel phai;
    private javax.swing.JLabel tenSV;
    // End of variables declaration//GEN-END:variables
}
