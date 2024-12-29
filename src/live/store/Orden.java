/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package live.store;

/**
 *
 * @author Javier Ignacio Parraguirre Lizarraga 20/10/23
 */
public class Orden {
    private int id;
    private Cliente cliente;
    private Estado estado;

    public Orden() {
    }

    public Orden(int id, Cliente cliente, Estado estado) {
        this.id = id;
        this.cliente = cliente;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    
    
}
