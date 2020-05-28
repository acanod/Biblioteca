package es.deusto.bd.gestor;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.datanucleus.store.query.QueryInterruptedException;

import es.deusto.bd.objetos.InfoObjetos;

public class GestorBD {

    private static GestorBD instance = null;
    private static PersistenceManagerFactory pmf = null;
    private static PersistenceManager pm = null;
    private static Logger logger = null;

    /**
     * Conectar a la base de datos
     */
    private void conectar() {
        pmf = JDOHelper.getPersistenceManagerFactory("CentralUnit");
    }

    /**
     * Instanciar el gestor de base de datos
     * 
     * @return la instancia del gestor
     */
    public static GestorBD getInstance() {
        if (instance == null) {
            instance = new GestorBD();
            instance.conectar();
        }
        return instance;
    }

    /**
     * Devuelve el PersistenceManagerFactory
     * 
     * @return PersistenceManagerFactory
     */
    protected static PersistenceManagerFactory getPMF() {
        getInstance();
        return pmf;
    }

    /**
     * Metodo que devuelve el Logger
     *
     * @return logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Logger que aporta informacion a la hora de ejecucion, reporta los eventos y
     * errores
     *
     * @param level     El nivel del error
     * @param msg       El mensaje del error
     * @param excepcion El excepcion, puede ser <code>null</code>
     */
    public static void log(Level level, String msg, Throwable excepcion) {
        if (logger == null) {
            logger = Logger.getLogger(GestorBD.class.getName());
            logger.setLevel(Level.ALL);
        }
        if (excepcion == null)
            logger.log(level, msg);
        else
            logger.log(level, msg, excepcion);
    }

    /**
     * Metodo para archivar los objetos en la base de datos
     *
     * @param <T>    El tipo de objecto
     * @param object que se archiva en la base de datos
     */
    public <T> void store(Class<T> object) {
        GestorBD.getInstance().storeObjectInDB(object);
    }

    /**
     * Metodo para asegurar que se ha guardado los objetos en la base de datos
     *
     * @param object que se guarda en la base de datos
     * @return <code>T/F</code> Si lo ha guardado
     */
    public boolean storeObjectInDB(Object object) {
        if (object == null) {
            throw new NullPointerException("Objecto para guardar es null");
        }

        Transaction transaction = null;
        try {
            pm = getPMF().getPersistenceManager();
            transaction = pm.currentTransaction();
            transaction.begin();
            pm.setIgnoreCache(true);
            pm.makePersistent(object);
            transaction.commit();
            log(Level.INFO, "Exito se han guardado todos los objetos", null);
        } catch (Exception e) {
            log(Level.SEVERE, "Error a la hora de guardar los objetos en la Base de datos", e);
            System.out.println("Error" + e.getMessage());
            return false;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            pm.close();
        }
        return true;
    }

    /**
     * Un metodo para actualizar un objecto en el BD. Hay que implemntar el
     * InfoObjetos y sus metodos
     *
     * @param objectClass El tipo de object que estas actualizando
     * @param object      El instancia del objecto
     * @param <T>         El tipo de objecto
     * @return <code>T/F</code> dependiante de si ha actualizado el objecto
     */
    public <T extends InfoObjetos> boolean updateObjectByID(Class<T> objectClass, T object) {
        if (object == null) {
            return false;
        }
        String id = object.getId();
        PersistenceManager pm = GestorBD.getPMF().getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Query<T> q = pm.newQuery(objectClass);
            q.setFilter("id  == :id");
            q.setParameters(id);
            q.ignoreCache(true);
            T result = q.executeUnique();
            if (result == null) {
                return false;
            } else {
                result.copyObjectData(object);
            }
            tx.commit();
            log(Level.INFO, "Exito a la hora de actualizar el objeto " + objectClass.getName().toString(), null);
            return true;
        } catch (Exception e) {
            log(Level.SEVERE, "Error a la hora de actualizar el objeto " + objectClass.getName().toString(), e);
            e.printStackTrace();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return false;
    }

    /**
     * Para sacar un objectoo varios objetos desde la base de datos
     *
     * @param id          El ID del objecto
     * @param objectClass el Clase del objecto
     * @param <T>         El tipo de objecto
     * @return objectClass indicado
     */
    public <T> T getObjectByID(String id, Class<T> objectClass) {
        T resultado = null;
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Query<T> q = pm.newQuery(objectClass);
            q.setFilter("id  == :id");
            q.setParameters(id);
            q.ignoreCache(true);
            T res = q.executeUnique();
            tx.commit();
            log(Level.INFO, "Exito a la hora de sacar el objeto " + objectClass.getName().toString(), null);
            resultado = res;
        } catch (Exception e) {
            log(Level.SEVERE, "Error a la hora de sacar el objeto " + objectClass.getName().toString(), e);
            e.printStackTrace();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return resultado;
    }

    /**
     * Metodo para sacar todos los objetos de la base de datos en una lista
     *
     * @param objectoClass del cual se quiere seleccionar
     * @param <T>          El tipo de objecto
     * @return lista de objetos seleccionados
     */
    public <T> List<T> selectListaObjectos(Class<T> objectoClass) {
        List<T> resultados;
        pm = getPMF().getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Query<T> q = pm.newQuery(objectoClass);
            q.ignoreCache(true);
            List<T> res = q.executeList();
            tx.commit();
            log(Level.INFO, "Exito a la hora de sacar la lista de objetos " + objectoClass.getName().toString(), null);
            resultados = res;
        } catch (Exception e) {
            log(Level.SEVERE, "Error a la hora de sacar la lista de objetos " + objectoClass.getName().toString(), e);
            e.printStackTrace();
            return null;
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        return resultados;
    }

    /**
     * Borra un objeto de la BD
     *
     * @param object del cual se quiere seleccionar
     * @param <T>    El tipo de objecto
     */
    public <T extends InfoObjetos> void deleteObject(T object) {
        if (object == null) {
            return;
        }
        try {
            PersistenceManager pm = getPMF().getPersistenceManager();
            Transaction tran = pm.currentTransaction();
            Query<? extends InfoObjetos> query = pm.newQuery(object.getClass());
            query.setFilter("id  == :id");
            query.setParameters(object.getId());
            InfoObjetos res = query.executeUnique();
            pm.deletePersistent(res);
        } catch (Exception e) {
            log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Borra un objeto determinado de la BD
     *
     * @param <T>   El tipo de objecto
     * @param id    objeto a seleccionar
     * @param clazz tipo del objeto
     */
    public <T extends InfoObjetos> void deleteObjectByID(String id, Class<T> clazz) {
        T res = getObjectByID(id, clazz);
        deleteObject(res);
    }

    /**
     * Borra todos los objetos segun una lista de la BD
     *
     * @param <T>      El tipo de objecto
     * @param objectos tipo de objeto a borrar
     */
    public <T extends InfoObjetos> void deleteObjectsInList(List<T> objectos) {
        if (objectos == null || objectos.isEmpty()) {
            return;
        }
        pm = getPMF().getPersistenceManager();
        Transaction tran = pm.currentTransaction();
        try {
            tran.begin();
            for (T obj : objectos) {
                Query<? extends InfoObjetos> query = pm.newQuery(obj.getClass());
                query.setFilter("id  == :id");
                query.setParameters(obj.getId());
                InfoObjetos res = query.executeUnique();
                pm.deletePersistent(res);
            }
            tran.commit();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (tran.isActive()) {
                tran.rollback();
                System.err.println("Transaction rolled back");
                throw new QueryInterruptedException("Query Failed");
            }
            pm.close();
        }
    }

    /**
     * Borra todos los objetos de la BD
     *
     * @param <T>          El tipo de objecto
     * @param objectoClass tipo de objeto a borrar
     */
    public <T extends InfoObjetos> void deleteAllObjectsByClass(Class<T> objectoClass) {
        List<T> tempList = this.selectListaObjectos(objectoClass);
        if (tempList != null) {
            deleteObjectsInList(tempList);
        }
    }
}