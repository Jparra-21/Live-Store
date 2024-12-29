/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Util.Conexion;
import java.sql.Statement; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import live.store.Cliente;
import live.store.Estado;
import live.store.Orden;

/**
 *
 * @author Javier Ignacio Parraguirre Lizarraga 20/10/23
 */
public class Crudeorden {
    public Orden Buscar(int ord) throws ParseException{
        PreparedStatement stmt;
        ResultSet rs;
        Orden orden = new Orden();
        Conexion con= new Conexion();
        Crudecliente crudcli = new Crudecliente();
        CrudeEstado crude = new CrudeEstado();
        Connection conn=con.conectarBD("live_store");
        String query =("SELECT * from orden where id_orden=?");    
        try {
                stmt = conn.prepareStatement(query);
                stmt.setInt(1,ord);
                rs = stmt.executeQuery();
                while (rs.next()) {
                 orden.setId(rs.getInt("id_orden"));
                 orden.setCliente(crudcli.Buscar(rs.getString("cliente_rut")));
                 orden.setEstado(crude.Buscar(rs.getInt("id_estado")));
                 }
                rs.close();
                stmt.close();
                conn.close();
                return orden;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
    public int Agregar(Orden orden){
        int id = 1;
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        PreparedStatement stmt;
        String query ="INSERT into orden(cliente_rut,id_estado) values(?,?)";
        try {
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,orden.getCliente().getRut());
            stmt.setInt(2,orden.getEstado().getId());
            stmt.executeUpdate();
             ResultSet rs = stmt.getGeneratedKeys(); 
            if (rs.next()) { 
                id = rs.getInt(1); 
            } 
            JOptionPane.showMessageDialog(null, "Orden Ingresado Correctamente!!!");
            conn.close();
            return id; 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return 0;
        } 
    }
    
    public DefaultComboBoxModel ComboOrden(){
        Statement stmt;
        ResultSet rs;
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        List lista = new ArrayList();
        try {
            DefaultComboBoxModel combo = new DefaultComboBoxModel();
            stmt = conn.createStatement(); 
            rs = stmt.executeQuery("select * from orden");
            while (rs.next()) { 
                String id=rs.getString(1);
                lista.addFirst(id);
                if(lista.size()>151)
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
    
    public void Modificar(Orden orden, int estado){
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        PreparedStatement stmt;
        String query ="UPDATE orden SET id_estado = ? WHERE id_orden =?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,estado);
            stmt.setInt(2,orden.getId());
            stmt.executeUpdate();
            conn.close();
            JOptionPane.showMessageDialog(null, "Estado de orden Modificado");
            return; 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return;
        } 
    }
    
    public DefaultTableModel listaOrden(int estado) throws ParseException{
        PreparedStatement stmt;
        ResultSet rs;
        Object[] fila=new Object[3];
        Conexion con= new Conexion();
        CrudeEstado crude = new CrudeEstado();
        Crudecliente crud = new Crudecliente();
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
                Cliente cli = crud.Buscar(rs.getString(2));
                fila[1]=cli.getNombre()+"  "+cli.getRut();    
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
    
    public DefaultComboBoxModel ComboOrdenEnvio(){
        Statement stmt;
        ResultSet rs;
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        List lista = new ArrayList();
        try {
            DefaultComboBoxModel combo = new DefaultComboBoxModel();
            stmt = conn.createStatement(); 
            rs = stmt.executeQuery("select * from orden where id_estado=3");
            while (rs.next()) { 
                String id=rs.getString(1);
                lista.addFirst(id);
                if(lista.size()>50)
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
    
     public DefaultTableModel TablaOrden(Orden orden){
        PreparedStatement stmt;
        ResultSet rs;
        Object[] fila=new Object[3];
        Conexion con= new Conexion();
        CrudeEstado crude = new CrudeEstado();
        Connection conn=con.conectarBD("live_store");
        String query =("select * from orden where id_orden=?");
        try {
            DefaultTableModel tabla=new DefaultTableModel();
            tabla.addColumn("Codigo Orden");
            tabla.addColumn("Cliente");
            tabla.addColumn("Estado");
            stmt = conn.prepareStatement(query); 
                stmt.setInt(1, orden.getId());
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
     
public void Eliminar(Orden orden){
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        PreparedStatement stmt;
        String query ="Delete from orden WHERE id_orden =?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,orden.getId());
            stmt.executeUpdate();
            conn.close();
            JOptionPane.showMessageDialog(null, "Orden Eliminada");
            return; 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return;
        } 
    }
}
