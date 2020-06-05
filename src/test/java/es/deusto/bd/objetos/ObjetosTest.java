package es.deusto.bd.objetos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Clase para probar si los objetos funcionana correctamente
 */
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

    /**
     * Metodo para comprobar el correcto funcionamiento de autor
     */
    @Test
    public void autorTest() {
        Autor test = Mockito.mock(Autor.class);
        Autor autor2 = new Autor("nombre", 45);
        libro.setAutor(autor2.getNombre());

        Mockito.when(libro.getAutor()).thenReturn("nombre");
        when(test.getEdad()).thenReturn(45);
        when(test.getNombre()).thenReturn("nombre");

        assertEquals("nombre", test.getNombre());
        assertEquals(45, test.getEdad());
        assertEquals(autor2.getNombre(), libro.getAutor());
        assertFalse(test.equals(autor2));
        assertNotEquals(test.generarID(), autor2.generarID());
    }

    /**
     * Metodo para comprobar el correcto funcionamiento de empleado
     */
    @Test
    public void empleadosTest() {
        Empleado test = Mockito.mock(Empleado.class);
        libreria.setNombre("nombre");
        empleado.setLibreria(libreria.getNombre());

        when(test.getNombre()).thenReturn("value");
        Mockito.when(empleado.getLibreria()).thenReturn("nombre");

        assertEquals("nombre", empleado.getLibreria());
        assertNotEquals(empleado.getNombre(), "second");
        assertEquals("value", test.getNombre());
    }

    /**
     * Metodo para comprobar el correcto funcionamiento de libro
     */
    @Test
    public void libroTest() {
        Libro test = Mockito.mock(Libro.class);
        libreria.setNombre("nombre");
        libro.setLibreria(libreria.getNombre());

        when(test.getAutor()).thenReturn("autor");
        when(test.getIdioma()).thenReturn("ing");
        when(test.getTitulo()).thenReturn("value");
        when(test.isReservado()).thenReturn(false);
        Mockito.when(libro.getLibreria()).thenReturn("nombre");

        assertEquals("nombre", libro.getLibreria());
        assertEquals("autor", test.getAutor());
        assertEquals("ing", test.getIdioma());
        assertEquals("value", test.getTitulo());
        assertFalse(test.isReservado());
    }

}