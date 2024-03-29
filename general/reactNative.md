![](https://www.frsf.utn.edu.ar/templates/utn17/img/utnsantafe-color.png)

# Preparando nuestro entorno de desarrollo

Para desarrollar aplicaciones con react native necesitaremos contar con las siguientes herramientas instaladas en nuestra pc/mac
* Un editor de texto/IDE compatible (VS Code o Webstorm)
* Node
* OpenJDK 8+
* La variable de entorno ANDROID_HOME configurado correctamente
* Un emulador o simulador. Puede ser Android Studio (Necesario para ejecutar el emulador de android) o XCode (En caso de tener una mac y deseen ejecutar el simulador de iOS)

En caso de no contar con estas herramientas a continuación procederemos con su instalación 

## 1- Instalando Visual Studio Code

Visual studio code es un editor de código gratuito desarrollado por Microsoft el cual se encuentra disponible para Windows, Linux, macOS.

Para descargarlo solo debemos dirigirnos a [https://code.visualstudio.com/](https://code.visualstudio.com/)

![](imagenes/1-CodeVsCodeSite.png)

Como vs code realmente es un editor de texto para multiples lenguajes y frameworks tambien es conveniente realizar la instalación de la extension de react-native para el mismo

Para esto nos dirigimos a la pestaña de extensiones en el menu izquierdo de la herramienta

![](imagenes/7-vscodeextensions.PNG)

En la barra de busqueda tipeamos `React native tools` y seleccionaremos la opcion `install` a la extension publicada por Microsoft

![](imagenes/8-vscodereactnativetools.PNG)

### Instalando Webstorm [OPCIONAL: alternativa a vs code]
Como alternativ a VS Code y en caso de contar con las cuentas educativas de la facutad, también podemos obtener una licencia para  Webstorm, el ide de javascript de jetbrains.

Para acceder al beneficio es necesario contar con el 
[Github Student Developer Pack](https://education.github.com/pack) y luego acceder al sitio de [JetBrains](https://www.jetbrains.com/webstorm/) para la descarga.

![](imagenes/2-Webstorm.png)


## 2- Instalando Node y OpenJDK8+

### Linux
Para installar [nvm](https://github.com/nvm-sh/nvm) abrir una terminal y ejecutar

```
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.37.0/install.sh | bash
```

Para recargar la terminal actual con nvm podemos ejecutar

```
export NVM_DIR="$([ -z "${XDG_CONFIG_HOME-}" ] && printf %s "${HOME}/.nvm" || printf %s "${XDG_CONFIG_HOME}/nvm")"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh" # This loads nvm
```

Luego para comprobar que se haya instalado correctamente podemos ejecutar
```
nvm -v
```

Finalmente para instalar node v12 podemos ejecutar 
```
nvm install 12
```

Y para comprobar su correcta instalación ejecutar
```
node -v
```

Luego para realizar la instalacion de `openjdk` buscar el comando correspondiente a su distribución en [este link](https://openjdk.java.net/install/)

Por ejemplo, para ubuntu es:
```
sudo apt-get install openjdk-8-jre
```
### Mac
Abrir una terminal y ejecutar desde [brew](https://brew.sh/index_es) los siguientes comandos
```
brew install node
```
```
brew install watchman
```
```
brew cask install adoptopenjdk/openjdk/adoptopenjdk8
```
### Windows

Instalar paquetes desde windows no es algo muy sencillo de hacer sin herramientas externas, para simplificarnos la tarea de instalar OpenJDK y Node v12 promonemos en primera instancia realizar la instalación del gestor de paquetes `Chocolatey` que expone una funcionalidad similar a la de los gestores de paquetes que se encuentran en Mac y Linux.

> No es necesario instalar Chocolatey, si desea puede realizar la instalación de los elementos necesarios por cualquier otro metodo.

Para instalar `Chocolatey` es necesario primero ejecutar un CMD como administrador 

![](imagenes/9-chocolateycmdroot.png)

Desde dentro de la shell administrador de CMD ejecutar el comando 

```
@"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"
```

Luego comenzara la instalación de Chocolatey, la cual puede demorar unos minutos

![](imagenes/10-chocolateyinstalled.PNG)

Cuando finalice la instalación será necesario cerrar las ventanas de CMD actualmente abiertas. Luego, al reabrirlas ya tendremos disponibles el comando `choco` para realizar la instalación de paquetes.

Podemos comprobar su correcta instalación ejecutando
```
choco -v
```

Luego para realizar la instalación de Node y OpenJDk solo es necesario ejecutar
```
choco install -y nodejs-lts openjdk8
```
> Recordar que debe ejecutarse CMD como administrador o lanzará error.

## 3 - Configurando la variable ANDROID_HOME
La herramiente React Native tools requiere que la variable de entorno ANDROID_HOME este configurada

### Linux y Mac
Si estas usando bash ejecuta

```
nano ~/.bashrc 
```

> Si usas zsh edita tu `~/.zshrc`

y agrega las siguientes lineas a tu configuración

```
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/tools/bin
export PATH=$PATH:$ANDROID_HOME/platform-tools
```

Para recargar el bashrc sin salir y volver a loguearse podes ejecutar
```
source ~/.bashrc
``` 
> Si usas zsh reemplaza por `~/.zshrc`
### Windows
Buscar en el menu de windows la opcion `Editar las variables de entorno` o `Edit the system environment variables`

![](imagenes/11-environment.png)

![](imagenes/12-environmentbutton.PNG)

Apretamos `New` en la opcion de user variables 

![](imagenes/13-uservariables.PNG)

Agregamos la variables `ANDROID_HOME` con los valores

Variable Name
```
ANDROID_HOME
```

Variable Value
```
%LOCALAPPDATA%\Android\Sdk
```

![](imagenes/14-androidhome.PNG)

Luego presionamos `OK` y deberiamos ver nuestra nueva variable agregada

Ademas, es necesario agregar `platform-tools` a la variable `Path` que ya deberia existir entre nuestras variables de usuario (Si no existe crearla)

Para esto la buscamos y seleccionamos `Edit`

![](imagenes/14-path.PNG)

![](imagenes/15-pathnew.PNG)

Y agregamos la dirección 
```
C:\Users\Aca-va-tu-user-de-windows\AppData\Local\Android\sdk\platform-tools
```

![](imagenes/16-pathnewwindowsplsstop.PNG)

Finalmente, tambien es necesario agregar `emulator` a la variable `Path` para poder ejecutar comandos de consola para abrir el emulador sin necesidad de abrir Android Studio

De la misma manera que agregamos `platform-tools` agregaremos una entrada a la variable `Path` que sea
```
C:\Users\Aca-va-tu-user-de-windows\AppData\Local\Android\sdk\emulator
```

Esto nos permitira utilizar `emulator` para poder ejecutar comandos de consola para abrir el emulador sin necesidad de abrir Android Studio

> Algunos programas solo toman las actualizaciones de variables de entorno durante el arranque, la forma mas sencilla de asegurarse que tome estos cambios es reiniciando

## 4 - Probando que todo funcione! 

Si queres comprobar que todo funcione correctamente podes crear un proyecto de react-native de prueba con: 
```
npx react-native init MiNombreDeProyecto
```

Luego de finalizar, este comando nos habrá generado una carpeta con el nombre `mi_nombre_de_proyecto`, la abrimos con nuestro editor/ide y veremos algo similar a esto

![](imagenes/vscode.PNG)

Desde visual studio code la mayoria de las acciones se realizan desde una terminal. Para abir una terminal es necesario dirigirse `Terminal` -> `New Terminal`

![](imagenes/vscodeterminal.png)

Esto nos abrira una terminal (CMD, Powershel o bash segun corresponda) embebida en la ventana del editor.

Para listar nuestros emuladores creados podemos ejecutar

```
emulator -list-avds
```

![](imagenes/vscodeterminalemulators.PNG)

Luego de la lista de respuestas podemos iniciar uno ejecutando

```
emulator -avd [adv-name]
```

![](imagenes/vscodeterminalemulatopen.PNG)

Luego de tener nuestro emulador corriendo es tiempo de ejecutar nuestra aplicacion de react native, para esto presionaremos el boton `[+]` arriba a la derecha para crear una nueva terminal y ejecutaremos 

```
npx react-native run-android
```

![](imagenes/vscodereactnative.PNG)

Ya tenemos nuestro entorno listo para desarrollar, el codigo de la app que estamos viendo se encuentra en `App.js`, si lo abrimos podemos ver como se generan los elementos de la pantalla. Ademas, podemos editar el contenido, guardar y ver los cambios reflejados casi instantaneamente 

![](imagenes/reactnative.png)

> Nota: Esta feature la pueden encontrar con el nombre de `HotReload` y es de gran utilidad durante el desarrollo. Si bien algunos cambios requieren que relanzemos la app para que se re-instale en el disposivo (generalmente cuando instalamos paquetes o agregamos archivos nuevos). 
Utilizando `HotReload` uno puede probar sus cambios visuales muy repidamente.

## 5 - Problemas

En caso de haber tenido problemas durante la instalación dejanos una pregunta en Teams o por correo o la guia de react-native en [https://reactnative.dev/docs/environment-setup](https://reactnative.dev/docs/environment-setup)

### Failed to install the APP

A veces durante la primera ejecución de nuestra app al lanzar el comando de `npx react-native run-android` podemos ver un error como el siguiente

![](imagenes/21-failedToInstall.PNG)

Esto suele solucionarse abriendo la carpeta `android` de nuestro proyecto React Native desde Android Studio. Esta carpeta contiene los componentes nativos de nuestra aplicacion para que pueda ejecutarse en celulares android y al abrirlo le automaticamente Android Studio hace un sync del proyecto que suele solucionar el problema.

### TypeError: Cannot read properties of undefined (reading 'transformFile')

![](./imagenes/react-native-error.png)

Este error suele estar relacionado con la version 17 de node (o alguna otra current), para comprobarlo podés tirar en una terminal

```bash
$ node --version
```

Si la versión es es una de esa podés probar desinstalarla y reinstalar una versión lts.

Luego eliminar la carpeta `node_modules`, el archivo `package_json.lock` y volver a ejecutar `npm i`

#### Windows

```bash
$ choco uninstall nodejs

$ choco install nodejs-lts
```







