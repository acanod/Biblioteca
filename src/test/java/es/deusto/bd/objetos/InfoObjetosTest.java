package es.deusto.bd.objetos;

import static org.junit.Assert.*;

import org.junit.*;

public class InfoObjetosTest {

    InfoObjetos<String> test = new InfoObjetos<String>() {
        @Override
        public String getId() {
            return this.generarID();
        }
    };

    @Test
    public void generarID() {
        assertNotEquals(test.generarID(), test.generarID());
    }

    @Test
    public void getId() {
        assertTrue(test.getId().getClass().equals(String.class));
    }
}