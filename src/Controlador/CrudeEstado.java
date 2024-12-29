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
import javax.swing.JOptionPane;
import live.store.Estado;

/**
 *
 * @author Javier Ignacio Parraguirre Lizarraga 20/10/23
 */
public class CrudeEstado {
    public Estado Buscar(int est){
        PreparedStatement stmt;
        ResultSet rs;
        Estado estado = new Estado();
        Conexion con= new Conexion();
        Connection conn=con.conectarBD("jplcl_mecashop");
        String query =("SELECT * from estado where id_estado=?");    
        try {
                stmt = conn.prepareStatement(query);
                stmt.setInt(1,est);
                rs = stmt.executeQuery();
                while (rs.next()) {
                 estado.setId(rs.getInt("id_estado"));
                 estado.setNombre(rs.getString("nombre_estado"));
                 }
                rs.close();
                stmt.close();
                conn.close();
                return estado;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        
    }
}
