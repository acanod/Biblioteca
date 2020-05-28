package es.deusto.bd.objetos;

import java.util.UUID;

public abstract class InfoObjetos<T> {

    public InfoObjetos() {

    }

    public String generarID() {
        Class<?> callerClass = getClass();
        return callerClass.getSimpleName() + "_" + UUID.randomUUID();
    }

    public abstract String getId();

    public void copyObjectData(T object) {
    }
}