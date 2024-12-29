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
import java.text.ParseException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import live.store.Cliente;
import live.store.Item;
import live.store.Live;
import live.store.Orden;

/**
 *
 * @author Javier Ignacio Parraguirre Lizarraga 20/10/23
 */
public class Crudeitem {
    public boolean Agregar(Item item){
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        PreparedStatement stmt;
        String query ="INSERT into item (nombre,precio,codigo,orden_id_orden,live_id_live) values(?,?,?,?,?)";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1,item.getNombre());
            stmt.setInt(2,item.getPrecio());
            stmt.setString(3, item.getCodigo());
            stmt.setInt(4,item.getOrden().getId());
            stmt.setInt(5, item.getLive().getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Item Ingresado Correctamente!!!");
            conn.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        } 
    }
    
    public DefaultComboBoxModel ComboItem(int live){
        PreparedStatement stmt;
        ResultSet rs;
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        String query =("SELECT * from item where live_id_live=?");    
        try {
                DefaultComboBoxModel list = new DefaultComboBoxModel();    
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, live);
                rs = stmt.executeQuery();
                while (rs.next()) {
                 String item= rs.getString("codigo");
                 list.addElement(item);
                 }
                rs.close();
                stmt.close();
                conn.close();
                return list;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
    
    public String NumeroItem(int id){
        PreparedStatement stmt;
        ResultSet rs;
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        String query =("SELECT count(*) from item where live_id_live=?");    
        try {
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, id);
                rs = stmt.executeQuery();
                rs.next();
                int cantidad =rs.getInt(1)+1;
                rs.close();
                stmt.close();
                String cantidadf = String.valueOf(cantidad);
                conn.close();
                return cantidadf;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
    
    public String GenerarCodigo(String args, Live live){
        
        String codigo = args+"/"+String.valueOf(live.getFecha());
        return codigo;
        
    }
    
    public DefaultTableModel listaItem(Live live){
        PreparedStatement stmt;
        ResultSet rs;
        Object[] fila=new Object[3];
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        String query =("select * from item where live_id_live=?");
        try {
            DefaultTableModel tabla=new DefaultTableModel();
            tabla.addColumn("Codigo item");
            tabla.addColumn("Nombre item");
            tabla.addColumn("Precio item");
            stmt = conn.prepareStatement(query); 
                stmt.setInt(1, live.getId());
                rs = stmt.executeQuery();
            while (rs.next()) { 
                fila[0]=rs.getString(4);
                fila[1]=rs.getString(2);
                fila[2]=rs.getInt(3);
                tabla.addRow(fila);
            }
            rs.close();
            stmt.close();
            conn.close();
            return tabla;
        }catch(SQLException ex){
        return null;}
    }
    
    public Item BuscarCodigo(String str, Live live, Orden orden) throws ParseException{
        PreparedStatement stmt;
        ResultSet rs;
        Item item = new Item();
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        String query =("SELECT * from item where codigo=?");    
        try {
                stmt = conn.prepareStatement(query);
                stmt.setString(1, str);
                rs = stmt.executeQuery();
                while (rs.next()) {
                 item.setId(rs.getInt("id"));
                 item.setNombre(rs.getString("nombre"));
                 item.setPrecio(rs.getInt("precio"));
                 item.setCodigo(str);
                 item.setOrden(orden);
                 item.setLive(live);
                 }
                rs.close();
                stmt.close();
                conn.close();
                return item;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
    
    public Item BuscarCodigo2(String str, Live live) throws ParseException{
        Crudeorden crudo = new Crudeorden();
        PreparedStatement stmt;
        ResultSet rs;
        Item item = new Item();
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        String query =("SELECT * from item where codigo=?");    
        try {
                stmt = conn.prepareStatement(query);
                stmt.setString(1, str);
                rs = stmt.executeQuery();
                while (rs.next()) {
                 item.setId(rs.getInt("id"));
                 item.setNombre(rs.getString("nombre"));
                 item.setPrecio(rs.getInt("precio"));
                 item.setCodigo(str);
                 item.setOrden(crudo.Buscar(rs.getInt("orden_id_orden")));
                 item.setLive(live);
                 }
                rs.close();
                stmt.close();
                conn.close();
                return item;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
    
    public boolean ModificarItemOrden(Item item){
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        PreparedStatement stmt;
        String query ="UPDATE item SET orden_id_orden = ? WHERE id =?";
        try {
            if (item.getOrden().getId() != 0)   {
                JOptionPane.showMessageDialog(null, "Item Pertenece a la orden: "+item.getOrden().getId());
            }
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, item.getOrden().getId());
            stmt.setInt(2,item.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Item Ingresado a orden: "+item.getOrden().getId()+" Correctamente!");
            conn.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        } 
    }
    public DefaultTableModel listaItemOrden(Orden orden){
        PreparedStatement stmt;
        ResultSet rs;
        Object[] fila=new Object[3];
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        String query =("select * from item where orden_id_orden=?");
        try {
            DefaultTableModel tabla=new DefaultTableModel();
            tabla.addColumn("Codigo item");
            tabla.addColumn("Nombre item");
            tabla.addColumn("Precio item");
            stmt = conn.prepareStatement(query); 
                stmt.setInt(1, orden.getId());
                rs = stmt.executeQuery();
            while (rs.next()) { 
                fila[0]=rs.getString(4);
                fila[1]=rs.getString(2);
                fila[2]=rs.getInt(3);
                tabla.addRow(fila);
            }
            rs.close();
            stmt.close();
            conn.close();
            return tabla;
        }catch(SQLException ex){
        return null;}
    }
    
    public int Total(Orden orden) throws ParseException{
        PreparedStatement stmt;
        ResultSet rs;
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        String query =("SELECT sum(precio) from item where orden_id_orden=?");    
        try {
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, orden.getId());
                rs = stmt.executeQuery();
                rs.next();
                int cantidad =rs.getInt(1);
                rs.close();
                stmt.close();
                conn.close();
                //String total = String.valueOf(cantidad);
                return cantidad;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return 0;
        }
    }
    
    public int TotalLive(Live live) throws ParseException{
        PreparedStatement stmt;
        ResultSet rs;
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        String query =("SELECT sum(precio) from item i left join orden o on i.orden_id_orden= o.id_orden where o.id_estado > 0 and i.live_id_live=?");    
        try {
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, live.getId());
                rs = stmt.executeQuery();
                rs.next();
                int cantidad =rs.getInt(1);
                rs.close();
                stmt.close();
                conn.close();
                //String total = String.valueOf(cantidad);
                return cantidad;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return 0;
        }
    }
     public DefaultTableModel listaOrdenLive(Live live) throws ParseException{
        PreparedStatement stmt;
        ResultSet rs;
        Object[] fila=new Object[3];
        Conexion con= new Conexion();
        Crudeorden crudo = new Crudeorden();
        Connection conn=con.conectarBD("live_store");
        String query =("select distinct(orden_id_orden) from item where live_id_live=?");
        try {
            DefaultTableModel tabla=new DefaultTableModel();
            tabla.addColumn("Codigo Orden");
            tabla.addColumn("Nombre");
            tabla.addColumn("Total");
            stmt = conn.prepareStatement(query); 
                stmt.setInt(1, live.getId());
                rs = stmt.executeQuery();
            while (rs.next()) { 
                Orden orden =crudo.Buscar(rs.getInt("orden_id_orden"));
                fila[0]= orden.getId();
                fila[1]=orden.getCliente().getRut()+"-"+orden.getCliente().getDv();
                fila[2]=this.Total(orden);
                if (!(fila[0].equals(0))){
                    tabla.addRow(fila);
                }
                
            }
            rs.close();
            stmt.close();
            conn.close();
            return tabla;
        }catch(SQLException ex){
        return null;}
    }
     
     public int TotalLive2(Live live) throws ParseException{
        PreparedStatement stmt;
        ResultSet rs;
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        String query =("SELECT sum(precio) from item where live_id_live=?");    
        try {
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, live.getId());
                rs = stmt.executeQuery();
                rs.next();
                int cantidad =rs.getInt(1);
                rs.close();
                stmt.close();
                conn.close();
                return cantidad;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return 0;
        }
    }
     
     public DefaultTableModel listaItemOrdenEsatdo(Cliente cliente , int estado){
        PreparedStatement stmt;
        ResultSet rs;
        Object[] fila=new Object[3];
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        String query =("select * from item LEFT JOIN orden ON item.orden_id_orden = orden.id_orden LEFT JOIN estado ON orden.id_estado = estado.id_estado WHERE estado.id_estado = ? and orden.cliente_rut=?");
        try {
            DefaultTableModel tabla=new DefaultTableModel();
            tabla.addColumn("Codigo item");
            tabla.addColumn("Nombre item");
            tabla.addColumn("Precio item");
            stmt = conn.prepareStatement(query); 
                stmt.setInt(1, estado);
                stmt.setString(2, cliente.getRut());
                rs = stmt.executeQuery();
            while (rs.next()) { 
                fila[0]=rs.getString(4);
                fila[1]=rs.getString(2);
                fila[2]=rs.getInt(3);
                tabla.addRow(fila);
            }
            rs.close();
            stmt.close();
            conn.close();
            return tabla;
        }catch(SQLException ex){
        return null;}
    }
     
     public boolean Modificar(Item item){
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        PreparedStatement stmt;
        String query ="Update item set nombre= ?,precio=?,codigo=? where codigo=?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1,item.getNombre());
            stmt.setInt(2,item.getPrecio());
            stmt.setString(3,item.getCodigo());
            stmt.setString(4,item.getCodigo());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Item Modificado Correctamente!!!");
            conn.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        } 
    }
     
      public boolean Eliminar(Item item){
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        PreparedStatement stmt;
        String query ="Delete from item where codigo=?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1,item.getCodigo());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Item Eliminado Correctamente!!!");
            conn.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        } 
    }
      
    public boolean EliminarItemOrden(Orden orden){
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("live_store");
        PreparedStatement stmt;
        int ord = 0;
        String query ="UPDATE item SET orden_id_orden = ? WHERE orden_id_orden =?";
        try {
            if (orden.getId() != 0)   {
                JOptionPane.showMessageDialog(null, "Item Pertenece a la orden: "+orden.getId());
            }
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, ord);
            stmt.setInt(2,orden.getId());
            stmt.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        } 
    }
}

