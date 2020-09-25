![](https://www.frsf.utn.edu.ar/templates/utn17/img/utnsantafe-color.png)

# Trabajo práctico integrador - Laboratorio 3
> Fecha de entrega para el laboratoratorio 2: 9 de octubre 2020

## Objetivos
- Enviar objetos parcelables entre activities
- Aprender a realizar tareas en background mediante AsyncTask
- Utilizar broadcast receiver y notificaciones locales

### 1. Crear actividad para nuevo pedido

Partiendo de la actividad para listar platos que realizamos en el laboratorio 2, en la toolbar, agregar la opción "Nuevo Pedido" que al ser presionado te llevará a una nueva Activity "PedidoActivity".

Esta nueva activity le permitirá a los usuarios cargar un nuevo pedido con la lista de platos que desean encargar.

Los datos que nos interesa guardar de un pedido son:

- El correo electronico del usuario
- La direccion para el envio
- Si tipo del pedido es para Envio o TakeAway
- La lista de platos a encargar

Esta activity contará con un boton de "Agregar platos" que nos llevara a la activity del listado de platos, el usuario podra seleccionar uno de los platos existentes y "PedidoActivity" recibira el resultado de esta seleccion. Para esto será necesario agregar al recycler un botón de "Pedir" que al ser apretado realice la accion de regresar con el plato seleccionado.

Además, la actividad de creación de pedidos contará con un listado que mostrará el nombre de los platos incluidos y un detalle mostrando la cantidad de productos en la orden y el precio total.

Finalmente, la activity contara con un boton "Confirmar Pedido" cuya funcionalidad implementaremos en el siguiente punto de esta guia

### 2. Confirmar pedido
