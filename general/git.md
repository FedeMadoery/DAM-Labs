# GIT

Git es un sistema para realizar el seguimiento de cambios en conjunto de archivos de texto. Actualmente es la herramienta estándar para la colaboración de equipos en el mundo profesional y una herramienta muy valiosa para aprender a utilizar.

La forma en que realiza seguimiento es a través de `commits` que guardan el estado de un grupo de archivos en un determinado tiempo, como si fueran fotos.  

Cada commit tiene un id que lo identifica unicamente y esta unido a un commit padre. Un commit contiene información de las diferencias (`diffs`) linea por linea de los archivos modificados con respecto al commit padre. Esto nos permite recorrer hacia atras esta historia para volver a generar cualquier estado previo de nuestro proyecto.

### Que es Github y que relación tiene con GIT?

**Github** es un servicio de hosting para repositorios de Git. Nos permite almacenar nuestro repositorio de git de forma remota y compartirlo con otras personas para que ellos puedan colaborar. Es importante distinguir **Github** (servicio de hosting) de **Git** (herramienta de control de versiones).

Además de Github existen otros proveedores de servicios similares como **Gitlab** y **Bitbucket** entre otros