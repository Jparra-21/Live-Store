/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import live.store.Estado;
import live.store.Live;

/**
 *
 * @author Javier Ignacio Parraguirre Lizarraga 20/10/23
 */
public class Crudelive {
     public boolean Agregar(Live live){
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        PreparedStatement stmt;
        String query ="INSERT into live (fecha_live) values(?)";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setDate(1, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(live.getFecha())));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Live Ingresado Correctamente!!!");
            conn.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        } 
    }
     
    public DefaultComboBoxModel ComboLive(){
        Statement stmt;
        ResultSet rs;
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        List lista = new ArrayList();
        try {
            DefaultComboBoxModel combo = new DefaultComboBoxModel();
            stmt = conn.createStatement(); 
            rs = stmt.executeQuery("select * from live");
            while (rs.next()) { 
                String fecha=new SimpleDateFormat("dd-MM-yyyy").format(rs.getDate(2));
                lista.addFirst(fecha);
                if(lista.size()>10)
                    lista.remove(lista.size()-1);
                
            }
            combo.addAll(lista);
            rs.close();
            stmt.close();
            conn.close();
            return combo;
        }catch(SQLException ex){
        return null;}
    }
    
     public Live Buscar(Date date){
        PreparedStatement stmt;
        ResultSet rs;
        Live live = new Live();
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("jplcl_mecashop");
        String query =("SELECT * from live where fecha_live=?");    
        try {
                stmt = conn.prepareStatement(query);
                stmt.setDate(1, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(date)));
                rs = stmt.executeQuery();
                while (rs.next()) {
                 live.setId(rs.getInt("id_live"));
                 live.setFecha(rs.getDate("fecha_live"));
                 }
                rs.close();
                stmt.close();
                conn.close();
                return live;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
     
     public DefaultTableModel listaOrden(int estado){
        PreparedStatement stmt;
        ResultSet rs;
        Object[] fila=new Object[3];
        Conexion con= new Conexion();
        CrudeEstado crude = new CrudeEstado();
        Connection conn=con.conectarBD("live_store");
        String query =("select * from orden where id_estado=?");
        try {
            DefaultTableModel tabla=new DefaultTableModel();
            tabla.addColumn("Codigo Orden");
            tabla.addColumn("Cliente");
            tabla.addColumn("Estado");
            stmt = conn.prepareStatement(query); 
                stmt.setInt(1, estado);
                rs = stmt.executeQuery();
            while (rs.next()) { 
                fila[0]=rs.getInt(1);
                fila[1]=rs.getString(2);
                Estado est = crude.Buscar(rs.getInt(3));
                fila[2]=est.getNombre();
                tabla.addRow(fila);
            }
            rs.close();
            stmt.close();
            conn.close();
            return tabla;
        }catch(SQLException ex){
        return null;}
    }
     
}
