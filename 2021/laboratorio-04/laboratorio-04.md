# Trabajo Practico Integrador 2 - Etapa 2
> Laboratorio 4
>
> Presentado: _Viernes 5 de Noviembre 2021_
>
> Entrega: TBD

## Objetivos
- Persistencia de datos de manera local utilizando ROOM
- Persistencia de datos remotos utilizando RETROFIT


## Introducción

En el laboratorio anterior habíamos presentado la siguiente interfaz:

```java
public interface RecordatorioDataSource {
    interface GuardarRecordatorioCallback {
        void resultado(final boolean exito);
    }

    interface RecuperarRecordatorioCallback {
        void resultado(final boolean exito, final List<RecordatorioModel> recordatorios);
    }

    void guardarRecordatorio(final RecordatorioModel recordatorio, final GuardarRecordatorioCallback callback);
    void recuperarRecordatorios(final RecuperarRecordatorioCallback callback);
}
```

Para continuar el desarrollo anterior en esta etapa volveremos a implementar persistencia con dos nuevas estrategias:
- Una base de datos relacional utilizando la librería de android ROOM.
- Una api rest mediante RETROFIT

Esto implica generar dos nuevas implementaciones concretas de la interface que podrían llamarse `RecordatorioRoomDataSource` y `RecordatorioRetrofitDataSource`, cada una con sus detalles de implementación pertinentes.

## ROOM

Implementar la interfaz RecordatorioDataSource para que persista y recupere datos de una base local utilizando ROOM. Para esto debera generar la Entity, dao, database y mapper a objeto de modelo.

## Retrofit

Implementar la interfaz RecordatorioDataSource para que persista y recupere datos de api rest usando RETROFIT. Para esto debera generar el Service, la instancia del cliente, dto y mapper a objeto de modelo.

### Api de ejemplo

Para facilitar la implementació de este punto desarrollamos una api para guardar y recuperar recordatorios. No es obligatorio utilizarla pero es necesario contar con una para testear la funcionalidad.

Esta api utiliza autenticacion en las request mediante el sysacad usando basic auth. ([Aquí un ejemplo](https://stackoverflow.com/questions/43366164/retrofit-and-okhttp-basic-authentication) de como agregar basic auth usando okhttp3)

Solo existe un recurso en esta api y es [https://recordatorio-api.duckdns.org/recordatorio](https://recordatorio-api.duckdns.org/recordatorio). A esta url podemos hacerle:
- GET: Para recuperar el listado de recordatorios generados por el usuario
- POST: Con un body de la forma `{"mensaje": "mensaje recordatorio","fecha": "2021-11-10T15:00:00.000Z",}` se crea un nuevo recordatorio
- DELETE: Con un body que incluya `{"id": 1}` siendo id del recordatorio que queremos borrar. Retorna la cantidad de rows afectadas (0 si no se borro nada).

En caso de querer hacer pruebas desde postman también le ofrecemos [esta collection](./dam-recordatorio-rest.postman_collection.json) para importar. Recuerde reemplzar los valores de `{{SYSACAD_LEGAJO}}` y `{{SYSACAD_PASS}}`. 

El código de las apis utilizadas es publico y puede revisarse en los siguientes repositorios en caso de que lo desee:
- Recordatorio API: [https://github.com/eldroan/dam-recordatorio-api](https://github.com/eldroan/dam-recordatorio-api)
- API de autenticación con sysacad: [https://github.com/eldroan/sysacad-api](https://github.com/eldroan/sysacad-api)

## Testear la app cambiando de datasource

Recondando el lab anterior habíamos introducido el siguiente repositorio de datos 

```java
public class RecordatorioRepository {
    private final RecordatorioDataSource datasource;
    public RecordatorioRepository(final RecordatorioDataSource datasource) {
        this.datasource = datasource;
    }
	
	// Metodos que recuperan los recordatorios usando el data source
	
}	
```

Sumado a la estrategia de persistencia que desarrollamos en el, nuestra app ahora permite realizar la persistencia de las siguientes 3 maneras:
- Local con Shared Preferences
- Local con DB relacional usando ROOM
- Remota en una api usando RETROFIT

Como las 3 comparten la misma interfaz en nuestra aplicación con solo cambiar la implementacion que le pasamos por constructor a nuestro repository la app debería cambiar la forma de realizar la persistencia de manera transparente para el resto de la aplicacion. 