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