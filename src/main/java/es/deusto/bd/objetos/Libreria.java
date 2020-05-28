package es.deusto.bd.objetos;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Libreria extends InfoObjetos<Libreria> {

    @PrimaryKey
    @Column(name = "Libreria_ID")
    private String id;

    @Persistent
    private String nombre;

    @Persistent
    private String ciudad;

    public Libreria() {
        this.id = generarID();
    }

    public Libreria(String nombre, String ciudad) {
        this.id = generarID();
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Libreria [ID = " + id + ", nombre = " + nombre + ", ciudad = " + ciudad + "]";
    }

    @Override
    public String getId() {
        return this.id;
    }

}