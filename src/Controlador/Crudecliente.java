/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Util.Conexion;
import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import live.store.Cliente;
import live.store.Estado;

/**
 *
 * @author Javier Ignacio Parraguirre Lizarraga 20/10/23
 */
public class Crudecliente {
    
    public boolean Agregar(Cliente cli){
        cli.setRut(cli.VerificarRut(cli.getRut()));
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        PreparedStatement stmt;
        String query ="INSERT into cliente (nombre,rut,dv,ig,telefono,direccion,email,fec_nac) values(?,?,?,?,?,?,?,?)";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1,cli.getNombre());
            stmt.setString(2,cli.getRut());
            stmt.setString(3,cli.getDv());
            stmt.setString(4, cli.getIg());
            stmt.setString(5, cli.getTelefono());
            stmt.setString(6, cli.getDireccion());
            stmt.setString(7, cli.getCorreo());
            stmt.setDate(8, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(cli.getFecnac())));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente Ingresado Correctamente!!!");
            conn.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        } 
    }
        
    public Cliente Buscar(String args) throws ParseException{
        PreparedStatement stmt;
        ResultSet rs;
        Cliente cli = new Cliente();
        Conexion con= new Conexion();
        cli.setRut(cli.VerificarRut(args));
        Connection conn=con.conectarBD("live_store");
        String query =("SELECT * from cliente where rut=?");    
        try {
                stmt = conn.prepareStatement(query);
                stmt.setString(1,args);
                rs = stmt.executeQuery();
                while (rs.next()) {
                 cli.setNombre(rs.getString("nombre"));
                 cli.setRut(args);
                 cli.setDv(rs.getString("dv"));
                 cli.setIg(rs.getString("ig"));
                 cli.setTelefono(rs.getString("telefono"));
                 cli.setDireccion(rs.getString("direccion"));
                 cli.setCorreo(rs.getString("email"));
                 cli.setFecnac(rs.getDate("fec_nac"));
                 }
                rs.close();
                stmt.close();
                conn.close();
                return cli;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
    
    public List Impresion(Cliente cli){
        List lista = new List();    
        
        String rut = cli.getRut()+"-"+cli.getDv();
        lista.add(rut);
        String nombre = cli.getNombre();
        lista.add(nombre);
        String telefono = cli.getTelefono();
        lista.add(telefono);
        String correo = cli.getCorreo();
        lista.add(correo);
        String direc = cli.getDireccion();
        lista.add(direc);
        
        return lista;
    }
    
    public DefaultTableModel listaOrdenCliente(Cliente cliente){
        PreparedStatement stmt;
        ResultSet rs;
        Object[] fila=new Object[3];
        Conexion con= new Conexion();
        CrudeEstado crude = new CrudeEstado();
        Connection conn=con.conectarBD("live_store");
        String query =("select * from orden where cliente_rut=?");
        try {
            DefaultTableModel tabla=new DefaultTableModel();
            tabla.addColumn("Codigo Orden");
            tabla.addColumn("Cliente");
            tabla.addColumn("Estado");
            stmt = conn.prepareStatement(query); 
                stmt.setString(1, cliente.getRut());
                rs = stmt.executeQuery();
            while (rs.next()) { 
                fila[0]=rs.getInt("id_orden");
                fila[1]=cliente.getNombre()+" "+cliente.getRut();
                Estado est = crude.Buscar(rs.getInt("id_estado"));
                fila[2]=est.getNombre();
                tabla.insertRow(0, fila);
            }
            rs.close();
            stmt.close();
            conn.close();
            return tabla;
        }catch(SQLException ex){
        return null;}
    }
    
    public DefaultComboBoxModel ComboCliente(){
        Statement stmt;
        ResultSet rs;
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        java.util.List lista = new ArrayList();
        try {
            DefaultComboBoxModel combo = new DefaultComboBoxModel();
            stmt = conn.createStatement(); 
            rs = stmt.executeQuery("select * from cliente");
            while (rs.next()) { 
                lista.addFirst(rs.getString(2));
            }
            combo.addAll(lista);
            rs.close();
            stmt.close();
            conn.close();
            return combo;
        }catch(SQLException ex){
        return null;}
    }
    
    public boolean Modificar(Cliente cli){
        cli.setRut(cli.VerificarRut(cli.getRut()));
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        PreparedStatement stmt;
        String query ="Update cliente set nombre= ?,rut=?,dv=?,ig=?,telefono=?,direccion=?,email=?,fec_nac=? where rut=?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1,cli.getNombre());
            stmt.setString(2,cli.getRut());
            stmt.setString(3,cli.getDv());
            stmt.setString(4, cli.getIg());
            stmt.setString(5, cli.getTelefono());
            stmt.setString(6, cli.getDireccion());
            stmt.setString(7, cli.getCorreo());
            stmt.setDate(8, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(cli.getFecnac())));
             stmt.setString(9,cli.getRut());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente Modificado Correctamente!!!");
            conn.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        } 
    }
    
    public Cliente BuscarTelefono(String args) throws ParseException{
        PreparedStatement stmt;
        ResultSet rs;
        Cliente cli = new Cliente();
        Conexion con= new Conexion();
        cli.setRut(cli.VerificarRut(args));
        Connection conn=con.conectarBD("live_store");
        String query =("SELECT * from cliente where telefono=?");    
        try {
                stmt = conn.prepareStatement(query);
                stmt.setString(1,args);
                rs = stmt.executeQuery();
                while (rs.next()) {
                 cli.setNombre(rs.getString("nombre"));
                 cli.setRut(rs.getString("rut"));
                 cli.setDv(rs.getString("dv"));
                 cli.setIg(rs.getString("ig"));
                 cli.setTelefono(rs.getString("telefono"));
                 cli.setDireccion(rs.getString("direccion"));
                 cli.setCorreo(rs.getString("email"));
                 cli.setFecnac(rs.getDate("fec_nac"));
                 }
                rs.close();
                stmt.close();
                conn.close();
                return cli;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
     public DefaultTableModel listaOrdenClientefiltro(Cliente cliente, int estado){
        PreparedStatement stmt;
        ResultSet rs;
        Object[] fila=new Object[3];
        Conexion con= new Conexion();
        CrudeEstado crude = new CrudeEstado();
        Connection conn=con.conectarBD("live_store");
        String query =("select * from orden where cliente_rut=? and id_estado=?");
        try {
            DefaultTableModel tabla=new DefaultTableModel();
            tabla.addColumn("Codigo Orden");
            tabla.addColumn("Cliente");
            tabla.addColumn("Estado");
            stmt = conn.prepareStatement(query); 
                stmt.setString(1, cliente.getRut());
                stmt.setInt(2, estado);
                rs = stmt.executeQuery();
            while (rs.next()) { 
                fila[0]=rs.getInt("id_orden");
                fila[1]=cliente.getNombre()+" "+cliente.getRut();
                Estado est = crude.Buscar(rs.getInt("id_estado"));
                fila[2]=est.getNombre();
                tabla.insertRow(0, fila);
            }
            rs.close();
            stmt.close();
            conn.close();
            return tabla;
        }catch(SQLException ex){
        return null;}
    }
     
       
}
