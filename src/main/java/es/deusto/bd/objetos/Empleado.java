package es.deusto.bd.objetos;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Empleado extends InfoObjetos<Empleado> {

    @PrimaryKey
    @Column(name = "Empleado_ID")
    private String id;

    @Persistent
    private String nombre;

    @Element(column = "Libreria_ID")
    @Persistent
    private String libreria;

    public Empleado() {
        this.id = generarID();
    }

    public Empleado(String nombre, String libreria) {
        this.id = generarID();
        this.nombre = nombre;
        this.libreria = libreria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLibreria() {
        return libreria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLibreria(String libreria) {
        this.libreria = libreria;
    }

    @Override
    public String toString() {
        return "Empleado [ID = " + id + ", nombre = " + nombre + "]";
    }

    @Override
    public String getId() {
        return this.id;
    }

}