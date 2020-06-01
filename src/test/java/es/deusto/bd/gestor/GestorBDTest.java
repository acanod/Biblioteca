package es.deusto.bd.gestor;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.deusto.CrearDatos;
import es.deusto.bd.objetos.Autor;
import es.deusto.bd.objetos.Empleado;
import es.deusto.bd.objetos.Libreria;
import es.deusto.bd.objetos.Libro;

public class GestorBDTest {

    private Autor autor;
    private Libreria libreria;
    private Empleado empleado;
    private Libro libro;

    @Before
    public void setBD() {
        GestorBD.getInstance();
        GestorBD.getInstance().deleteAllObjectsByClass(Autor.class);
        GestorBD.getInstance().deleteAllObjectsByClass(Libreria.class);
        GestorBD.getInstance().deleteAllObjectsByClass(Empleado.class);
        GestorBD.getInstance().deleteAllObjectsByClass(Libro.class);

        autor = new Autor("asd", 34);
        libreria = new Libreria("nombre", "ciudad");
        empleado = new Empleado("nombre", libreria.getNombre());
        libro = new Libro("titulo", "idioma", true, autor.getNombre(), libreria.getNombre());
    }

    @Test
    public void getInstance() {
        assertNotNull(GestorBD.getInstance());
        assertTrue(GestorBD.getInstance().getClass().equals(GestorBD.class));
    }

    @Test
    public void storeObjectInDBTest() {
        GestorBD.storeObjectInDB(autor);

        List<Autor> autores = GestorBD.getInstance().selectListaObjectos(Autor.class);

        assertEquals(true, autores.contains(autor));
    }

    /**
     * El metodo generarAutores contiene la funcion storeObjects y aqui comprobamos
     * su funcionamiento
     */
    @Test
    public void storeObjectsTest() {
        CrearDatos.generarAutores();
        CrearDatos.generarLibrerias();
        CrearDatos.generarEmpleados();
        CrearDatos.generarLibros();

        List<Autor> autores = GestorBD.getInstance().selectListaObjectos(Autor.class);
        List<Libreria> librerias = GestorBD.getInstance().selectListaObjectos(Libreria.class);
        List<Empleado> empleados = GestorBD.getInstance().selectListaObjectos(Empleado.class);
        List<Libro> libros = GestorBD.getInstance().selectListaObjectos(Libro.class);

        assertEquals(99, autores.size());
        assertEquals(100, librerias.size());
        assertEquals(100, empleados.size());
        assertEquals(100, libros.size());

        GestorBD.getInstance().deleteAllObjectsByClass(Autor.class);
        GestorBD.getInstance().deleteAllObjectsByClass(Libreria.class);
        GestorBD.getInstance().deleteAllObjectsByClass(Empleado.class);
        GestorBD.getInstance().deleteAllObjectsByClass(Libro.class);
    }

    @Test
    public void getObjectByIDTest() {
        GestorBD.storeObjectInDB(autor);
        GestorBD.storeObjectInDB(libreria);
        GestorBD.storeObjectInDB(empleado);
        GestorBD.storeObjectInDB(libro);

        assertSame(autor.hashCode(), GestorBD.getInstance().getObjectByID(autor.getId(), Autor.class).hashCode());
        assertNotEquals(libreria.hashCode(),
                GestorBD.getInstance().getObjectByID(libreria.getId(), Libreria.class).hashCode());
        assertNotEquals(empleado.hashCode(),
                GestorBD.getInstance().getObjectByID(empleado.getId(), Empleado.class).hashCode());
        assertNotEquals(libro.hashCode(), GestorBD.getInstance().getObjectByID(libro.getId(), Libro.class).hashCode());
    }

    @Test
    public void updateObjectByIDTest() {
        this.autor.setNombre("qwerty");
        this.libro.setTitulo("qwerty");
        this.empleado.setNombre("qwerty");
        this.libreria.setNombre("qwerty");

        GestorBD.getInstance().updateObjectByID(Autor.class, autor);
        GestorBD.getInstance().updateObjectByID(Libro.class, libro);
        GestorBD.getInstance().updateObjectByID(Empleado.class, empleado);
        GestorBD.getInstance().updateObjectByID(Libreria.class, libreria);

        assertEquals("qwerty", autor.getNombre());
        assertEquals("qwerty", libro.getTitulo());
        assertEquals("qwerty", empleado.getNombre());
        assertEquals("qwerty", libreria.getNombre());
    }

}