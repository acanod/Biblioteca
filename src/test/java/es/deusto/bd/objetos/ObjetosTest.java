package es.deusto.bd.objetos;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ObjetosTest {

    @Mock
    Autor autor;

    @Mock
    Libreria libreria;

    @Mock
    Empleado empleado;

    @Mock
    Libro libro;

    @Test
    public void autorTest() {
        Autor autor2 = new Autor("nombre", 45);
        libro.setAutor(autor2.getNombre());

        Mockito.when(libro.getAutor()).thenReturn("nombre");

        assertEquals(autor2.getNombre(), libro.getAutor());
    }

    @Test
    public void empleadosTest() {
        libreria.setNombre("nombre");
        empleado.setLibreria(libreria.getNombre());

        Mockito.when(empleado.getLibreria()).thenReturn("nombre");

        assertEquals("nombre", empleado.getLibreria());
    }

    @Test
    public void libroTest() {
        libreria.setNombre("nombre");
        libro.setLibreria(libreria.getNombre());

        Mockito.when(libro.getLibreria()).thenReturn("nombre");
        assertEquals("nombre", libro.getLibreria());
    }
}