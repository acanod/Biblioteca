package es.deusto.bd.objetos;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Libro extends InfoObjetos<Libro> {

    @PrimaryKey
    @Column(name = "Libro_ID")
    private String id;

    @Persistent
    private String titulo;
    @Persistent
    private String idioma;
    @Persistent
    private boolean reservado;

    @Element(column = "Autor_ID")
    @Persistent
    private String autor;

    @Element(column = "Libreria_ID")
    @Persistent
    private String libreria;

    public Libro() {
        this.id = generarID();
    }

    public Libro(String titulo, String idioma, boolean reservado, String autor, String libreria) {
        this.id = generarID();
        this.titulo = titulo;
        this.idioma = idioma;
        this.reservado = reservado;
        this.autor = autor;
        this.libreria = libreria;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public boolean isReservado() {
        return reservado;
    }

    public String getAutor() {
        return autor;
    }

    public String getLibreria() {
        return libreria;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setLibreria(String libreria) {
        this.libreria = libreria;
    }

    @Override
    public String toString() {
        return "Libro [ID = " + id + ", titulo = " + titulo + ", idioma = " + idioma + ", reservado = " + reservado
                + ", autor = " + autor + "]";
    }

    @Override
    public String getId() {
        return this.id;
    }

}