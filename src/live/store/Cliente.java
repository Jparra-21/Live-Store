/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package live.store;

import java.awt.List;
import java.util.Date;

/**
 *
 * @author Javier Ignacio Parraguirre Lizarraga 20/10/23
 */
public class Cliente {
    private String nombre;
    private String ig;
    private String rut;
    private String dv;
    private String telefono;
    private Date fecnac;
    private String direccion;
    private String correo;

    public Cliente() {
    }

    public Cliente(String nombre, String ig, String rut, String dv, String telefono, Date fecnac, String direccion, String correo) {
        this.nombre = nombre;
        this.ig = ig;
        this.rut = rut;
        this.dv = dv;
        this.telefono = telefono;
        this.fecnac = fecnac;
        this.direccion = direccion;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIg() {
        return ig;
    }

    public void setIg(String ig) {
        this.ig = ig;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecnac() {
        return fecnac;
    }

    public void setFecnac(Date fecnac) {
        this.fecnac = fecnac;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String VerificarRut(String rut) {
     
        if (rut.isEmpty()){
            return null;
        }else if(rut.length()< 7) {
            return null;
        }else if(rut.contains("-")){
        int guion=rut.indexOf("-");
        return rut.substring(0, guion);
        }else if(rut.length()> 8) {
            return null;
        }else 
         return rut;
    }
    
    
}
