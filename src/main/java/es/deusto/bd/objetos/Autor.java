package es.deusto.bd.objetos;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Autor extends InfoObjetos<Autor> {

    @PrimaryKey
    @Column(name = "Autor_ID")
    private String id;

    @Persistent
    private String nombre;

    @Persistent
    private int edad;

    public Autor() {
        this.id = generarID();
    }

    public Autor(String nombre, int edad) {
        this.id = generarID();
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Autor [ID = " + id + ", nombre = " + nombre + ", edad = " + edad + "]";
    }

    @Override
    public String getId() {
        return this.id;
    }

}