# Biblioteca
[![codecov](https://codecov.io/gh/acanod/Biblioteca/branch/master/graph/badge.svg)](https://codecov.io/gh/acanod/Biblioteca)
[![Build Status](https://travis-ci.org/acanod/Biblioteca.svg?branch=master)](https://travis-ci.org/acanod/Biblioteca)

Proyecto de software y calidad

## Instalar proyecto

Para instalar correctamente el proyecto  ejecute los siguientes comandos:

1. `mvn clean install`
2. `mvn javafx:run`

***

### Nuevas tablas en la base de datos

En caso de que quiera añadir o modificar las tablas de la base de datos de este proyecto tiene que modificar el fichero _persistence.xml_ . Una vez modificado debe ejecutar estos dos comando:

1. `mvn datanucleus:enhance`
2. `mvn datanucleus:schema-create`

## Documentación

Para más información sobre la funcionalidad de las clases y métodos que hay implemetnados en el proyecto aquí esta el [enlace](https://acanod.github.io/Biblioteca/javadoc/) para la documentación.

## Herramientas
Para la creación de este proyecto se ha utilicado:
- Travis CI
- Jacoco
- Datanucleus
- Codecov
- Mockito
- Javadoc
