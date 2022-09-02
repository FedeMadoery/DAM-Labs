![](https://www.frsf.utn.edu.ar/templates/utn17/img/utnsantafe-color.png)

# DAM - Práctica 1

## Objetivos

- Familiarizarse con Android Studio
- Aprender a configurar control de versiones con GIT
- Utilizar los distintos componentes gráfico disponibles
- Navegar entre pantallas
- Familiarizarnos con las librerias de compatibilidad de Android X

## Tareas a desarrollar

### 1. Crear el proyecto

1. Abrir android studio y seleccionar la opcion para comenzar un nuevo proyecto.
   ![](2022/practica-01/imagenes/0-CreateNew.png)
2. Seleccionar `Empty Activity` como template
   ![](2022/practica-01/imagenes/1-SelectProjectTemplate.png)
3. En la sección de configuración de proyecto seleccione.

   - Seleccione `JAVA` como lenguaje
   - Nombre de la aplicacion `BancoUTN`
   - Nombre del paquete (Puede utilizar su nombre que identifique a su grupo)
   - Seleccionar el Minimun SDK (Se recomienda API 23, como mínimo elija el API correspondiente a su dispositivo para poder ejecutar la aplicación)

   > Asegúrese de haber seleccionado Java como lenguaje ya que Android Studio en sus versiones más recientes pre-selecciona Kotlin.

   ![](2022/practica-01/imagenes/2-ConfigureYourProject.png)

### 2. Configurar GIT

1. Es necesario tener una cuenta de Github, en caso de no tenerla puede registra una en [https://github.com](https://github.com)
2. Desde Android Studio, en la barra superior de tareas seleccione la opcion VCS → Import into Version Control → Share Project on Github
   ![](2022/practica-01/imagenes/3-ShareProjectOnGithub.png)
3. Asignamos el nombre de nuestro repositorio en Github bajo `Repository Name` asegurandonos que la opcion `Private` se encuentre des-marcada y presionamos `Share`.

   ![](2022/practica-01/imagenes/4-RepoSettings.png)

   > Es preferible que el repositorio sea publico para que podamos revisarlos facilmente desde la cátedra. Para usos personales puede utilizar repositorios privados, los cuales solo pueden ser visualizados con la autorización del creador.

   > El campo `Remote` es el nombre con el cual git identificará localmente a este nuevo repositorio remoto en Github, es recomendado dejar el nombre `origin` ya que es la convención para los casos con un único repositorio remoto como el nuestro.

4. Para finalizar con la creación de nuestro repositorio debemos inicializarlo con un primer commit, la siguiente ventana de Android Studio nos presenta la opcion de agregar todos los archivos al seguimiento de GIT. Colocamos como mensaje para nuestro primer commit

   ```
   Iniciación de la practica 1
   ```

   Y luego presionamos `Add`

   ![](2022/practica-01/imagenes/5-RepoShare.png)

   > Recordemos que git solo realiza seguimiento de los archivos que le nosotros le indicamos o `tracked`, los que no, apareceran como `untracked`.

   > Apretar el boton `Add` en Android Studio para inicializar el repositorio realizo 2 acciones, primero realizo un `commit` a nuestro repositorio local y luego realizo un `push` al repositorio remoto en Github.

5. Verificar información en Github. La url del repositorio suele seguir la forma
   ```
   https://github.com/{USUARIO}/{NOMBRE_REPOSITORIO}
   ```
   ![](2022/practica-01/imagenes/6-GithubRepo.png)

6. Crear la rama/branch `develop`, haciendo click con el IDE (Android Studio) en la barra inferior del margen derecho.
![](2022/practica-01/imagenes/7-NewBranch.png)
![](2022/practica-01/imagenes/8-Develop.png)
![](2022/practica-01/imagenes/9-DevelopCreated.png)

7. Crear una nueva rama con el nombre `lab01`.

    ![](2022/practica-01/imagenes/10-Lab01Created.png)
    > Asegurarse de que se encuentre trabajando en la rama lab01 para la realizacion del laboratorio 1.

### 3. Configurar UI - (LinearLayout)
1. Para asegurarse que todo se encuentre funcionando correctamente probemos correr la aplicación generada presionando el boton verde con una flecha. Una vez que cargue deberíamos ver el mensaje 'Hello World' en pantalla
![](2022/practica-01/imagenes/11-HelloWorld.png)
2. El mensaje 'Hello World!' que podemos observar se encuentra en la carpeta 'res' → 'layout' → 'activity_main.xml'. Para la realizacion de esta práctica utilizaremos un `LinearLayout` de orientación vertical pero el archivo 'activity_main.xml' fue autogenerado utilizando un `ConstrainLayout` por lo que tendremos que cambiarlo
    1. Abra 'activity_main.xml' y asegurese de tener seleccionada la opción `design`.
    ![](2022/practica-01/imagenes/12-DesignView.png)
    2. Seleccione la `ContraintLayout` en el arbol de componentes y presione 'Convert view...' y seleccione `LinearLayout`
    ![](2022/practica-01/imagenes/13-ConvertView.png)
    ![](2022/practica-01/imagenes/14-ConvertViewLinearLayout.png)
    3. Por default el `LinearLayout` coloca los elementos de forma horizontal. Para cambiar este comportamiento realizamos click → LinearLayout → Convert orientation to vertical
    ![](2022/practica-01/imagenes/15-VerticalLinealLayout.png)

### 4. UI Propuesta

Como nuestro primer proyecto se plantea desarrollar una especie de home banking llamada BancoUTN de la cual implementaremos dos pantallas para la funcionalidad de constituir plazos fijos

| Pantalla ConstituirPlazoFijo | Pantalla SimularPlazoFijo |
| --- | --- |
| ![](2022/practica-01/imagenes/1-BancoUTN.jpg) | ![](2022/practica-01/imagenes/2-BancoUTN.jpg) |

| Confirmación | Constitución |
| --- | --- |
| ![](2022/practica-01/imagenes/3-BancoUTN.jpg)  | ![](2022/practica-01/imagenes/4-BancoUTN.jpg) | 

#### Consideraciones
- Generales:
	- Utilizar view binding en lugar de `findViewById` (revisar ppts de teoría).
	- La app no debe perder su estado al rotar el telefono.
	- Ambas pantallas tendran una imagen que contenga el logo de la facultad (https://upload.wikimedia.org/wikipedia/commons/6/67/UTN_logo.jpg) y el titulo **Banco UTN Santa Fe**

- Pantalla "ConstituirPlazoFijo":
	- Agregar un text view con el mensaje "Para constituir tu plazo fijo necesitamos que nos proveas algunos datos."
	- Agregar campos de texto para ingresar un nombre y otro para ingresar un apellido.
	- Agregar un spinner para ingresar el tipo de moneda.
	- Agregar un botón "Simular" que redireccione a la pantalla de "SimularPlazoFijo".
	- Se deberá colocar un botón "Constituir" inicialmente en estado deshabilitado, el cual solo se activará luego de regresar de la pantalla  "SimularPlazoFijo" habiendo confirmado el capital y los días del plazo fijo. Al presionar este botón debera mostrarse un `AlertDialog` con un titulo "Felicitaciones {nombre} {apellido}!" y un mensaje "Tu plazo fijo de {capital} {moneda} por {dias} ha sido constituido!"

- Pantalla "SimularPlazoFijo":
	- Debe contar con un campo de texto para ingresar la **Tasa Nominal Anual** que solo permita inputs numéricos.
	- Debe contar con un campo de texto para ingresar la **Tasa Efectiva Anual** que solo permita inputs numéricos.
	- Debe contar con un campo de texto para ingresar el capital a invertir que solo permita inputs numéricos.
	- Para la cantidad de meses de inversion se utilizara un seekbar que permita como valor máximo y un label por debajo con la conversion a días (asumir que todos los meses tienen 30 dias).
	- Agregar un botón confirmar que al ser presionado regrese a la pantalla de "ConstituirPlazoFijo" notificandole el `capital` y los `dias`completados.
	- Agregar TextViews que permitan visualizar los siguientes valores:
		- "Plazo: {dias} dias"
		- "Capital: {capital}"
		- "Intereses ganados: {interes}"
		- "Monto total: {interes + capital}"
		- "Monto total anual: {interesAnual + capital}"
	- Implementar un metodo `calcular()` que actualice los TextViews mencionados en el punto anterior y que sea llamado automaticamente al modificarse alguno de los valores de la pantalla. En caso de valores invalidos se deberá desabilitar el botón de confirmar.

### 5. Persistir en git y compartir en github
1. Verificar que la app se encuentre funcionando al presionar el botton `Play`
2. Desde android studio presionar `VCS` → `Commit` o el atajo de teclado `CTRL + K` (Win y Linux) o `CMD + K` (Mac)
    ![](2022/practica-01/imagenes/20-Commit.png)
3. En la ventana seleccione todos los archivos que desea persistir en el commit y asegurarse de:
    * Escribir un mensaje de commit significativo de los cambios que realizo
    * Completar el nombre del autor en formato `userDeGit <correo@electronico.com>`
    * Destildar `Perform code analysis` y `Check TODO` (No es estrictamente necesario, si no los destildamos nos mostrara un warning con cosas que el ide considera que podrian mejorarse o mensajes marcados como "TO-DO")

    ![](2022/practica-01/imagenes/21-CommitScreen.png)

    EJEMPLO:
    ![](2022/practica-01/imagenes/22-CommitScreenExample.png)
    > Notese que en este ejemplo solo se estan subiendo las clases del model, esto es porque en este caso ya se habian realizado commits previos con otras partes del lab. Ademas, no se esta subiendo el contenido de la carpeta `.idea` ya que son archivos de configuración del IDE y no es necesario. 
4. Una vez completados los datos del commit presionar el botón `Commit` para efectuar la acción
5. Con nuestros cambios commiteados en nuestro repositorio local es momento de persistirlos en github, para esto necesitamos realizar un `PUSH` a nuestro repositorio remoto (en github), desde el IDE podemos encontrarla presinando  `VCS` → `Git` → `Push` o el atajo de teclado `CTRL + SHIFT + K` (Win y Linux) o `CMD + SHIFT + K` (Mac)

    ![](2022/practica-01/imagenes/23-Push.png)
6. Luego nos aparecera una ventana de confirmacion con los commits que esten en nuestro repositorio local y no en el remoto, estos son los commits que seran pusheados.
    
    ![](2022/practica-01/imagenes/24-PushConfirmation.png)
    > En la imagen se puede ver que nos indica que la rama lab-01 (local) sera pusheado ( → ) a la rama lab-01 en github (origin, o el nombre que le hayamos asignado al `Remote` en el paso 3 del item 2 - Configurar GIT)
7. Si todo esta ok presionamos `Push` y comenzará a realizar el proceso para pushear nuestros cambios a github (Es posible que nos pida nuestras credenciales de acceso a github si no las habiamos puesto antes).
8. Comprobar en el sitio
    * Navegar a `https://github.com/{usuario}/{nombre-de-repo}/`
    * Seleccionar la rama lab-1
    * Comprobar que se vea reflejado el commit que hemos realizado
    
    ![](2022/practica-01/imagenes/25-GithubCommit.png)

