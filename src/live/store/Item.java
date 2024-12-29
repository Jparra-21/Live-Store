/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package live.store;

/**
 *
 * @author Javier Ignacio Parraguirre Lizarraga 20/10/23
 */
public class Item {
  private int id;
  private String nombre;
  private int precio;
  private String codigo;
  private Orden orden;
  private Live live;

    public Item() {
    }

    public Item(int id, String nombre, int precio, String codigo, Orden orden, Live live) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.codigo = codigo;
        this.orden = orden;
        this.live = live;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }


    public Live getLive() {
        return live;
    }

    public void setLive(Live live) {
        this.live = live;
    }
  
  
}
