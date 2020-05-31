package es.deusto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.github.javafaker.Faker;

import es.deusto.bd.gestor.GestorBD;
import es.deusto.bd.objetos.Autor;
import es.deusto.bd.objetos.Empleado;
import es.deusto.bd.objetos.Libreria;
import es.deusto.bd.objetos.Libro;

/**
 * Clase para generar datos aleatorios y meterlos dentro de la base de datos
 */
public class CrearDatos {

    private static int NUM_MAX = 100;

    /**
     * Metodo para generar autores y agregar a la base de datos
     */
    public static void generarAutores() {
        Faker faker = new Faker(new Locale("es"));
        List<Autor> listaAutores = new ArrayList<>(NUM_MAX);
        for (int i = 1; i < NUM_MAX; i++) {
            Autor temp = new Autor();
            temp.setEdad(faker.number().numberBetween(20, 80));
            temp.setNombre(faker.name().fullName());
            listaAutores.add(temp);
        }
        // System.out.println(listaAutores);
        GestorBD.storeObjects(listaAutores);
    }

    /**
     * Metodo para generar libros y agregar a la base de datos
     */
    public static void generarLibros() {
        Faker faker = new Faker(new Locale("es"));
        List<Libro> listaLibros = new ArrayList<>(NUM_MAX);
        for (int i = 0; i < NUM_MAX; i++) {
            Libro temp = new Libro();
            temp.setAutor(faker.name().fullName());
            temp.setIdioma(faker.country().name());
            temp.setTitulo(faker.funnyName().name());
            temp.setLibreria(faker.name().name());
            temp.setReservado(faker.bool().bool());
            listaLibros.add(temp);
        }
        GestorBD.storeObjects(listaLibros);
    }

    /**
     * Metodo para generar Librerias y agregar a la base de datos
     */
    public static void generarLibrerias() {
        Faker faker = new Faker(new Locale("es"));
        List<Libreria> listaLibrerias = new ArrayList<>(NUM_MAX);
        for (int i = 0; i < NUM_MAX; i++) {
            Libreria temp = new Libreria();
            temp.setCiudad(faker.country().name());
            temp.setNombre(faker.name().fullName());
            listaLibrerias.add(temp);
        }
        GestorBD.storeObjects(listaLibrerias);
    }

    /**
     * Metodo para generar Empleados y agregar a la base de datos
     */
    public static void generarEmpleados() {
        Faker faker = new Faker(new Locale("es"));
        List<Empleado> listaEmpleados = new ArrayList<>(NUM_MAX);
        for (int i = 0; i < NUM_MAX; i++) {
            Empleado temp = new Empleado();
            temp.setLibreria(faker.country().name());
            temp.setNombre(faker.name().fullName());
            listaEmpleados.add(temp);
        }
        GestorBD.storeObjects(listaEmpleados);
    }
}