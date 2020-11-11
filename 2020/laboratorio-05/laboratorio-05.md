![](https://www.frsf.utn.edu.ar/templates/utn17/img/utnsantafe-color.png)

# Trabajo práctico integrador - Laboratorio 5
> Fecha de entrega para el laboratorio 5: XX - XX - XXXX


## Objetivos
- Utilizar el servicio de Google Mapas
    - Utilizar Markers
    - Dibujar formas
- Interactuar con la cámara del dispositivo para obtener una imagen 
- Utilizar Firebase para lograr la persistencia de imágenes
- Utilizar Notificaciones Push

# Intro
En este momento


### 1. Clonar el proyecto realizado en el laboratorio anterior

###### (De no tenerlo ya de manera local) 

![](../laboratorio-01/imagenes/6-GithubRepo.png)

A continuación abrir el proyecto en Android Studio y hacer un checkout de la branch develop, esta rama debe contener los cambios realizados durante el [laboratorio 4](../laboratorio-04/laboratorio-04.md). 


A partir de develop, crear una nueva branch llamada lab05. Esta es la branch sobre la cual se realizará este laboratorio.  

### 2. Obtener una clave de google developers y agregarla a la App

 * 2.1 - Con un usuario de google (uno por grupo), ingresar a [Consola de Google](https://console.developers.google.com)
 * 2.2 - Ingresar a [Administra recursos](https://console.developers.google.com/iam-admin/projects)
 * 2.3 - Crear un nuevo proyecto
 ![](../laboratorio-05/imagenes/01_nuevo_proyect.png)
 * 2.4 - En el menu lateral, ingresar a 'Credenciales' -> '+ Crear Credenciales' -> 'Clave de API' -> 'Restringir'
 * 2.5 - Restricciones de aplicaciones
    * Apps de Android
 * 2.6 - Restricciones de API
    * Maps SDK for Android
  ![](../laboratorio-05/imagenes/02_restringir_key.png)
 * 2.7 - Copiar y guardar el API Key
 
Una vez que tenemos nuestra API key, debemos agregarla al `manifest.xml` para que pueda ser usada.
Vamos a `values.xml` y creamos una entrada para la clave.
```xml
<string name="google_maps_key">AIzaS……</string> 
```
Luego en el `manifest.xml`
```xml
<application 
    <!--....-->
    android:theme="@style/AppTheme" > 
    <meta-data 
        android:name="com.google.android.geo.API_KEY" 
        android:value="@string/google_maps_key" /> 
```

### 3. Crear actividad para usar el mapa
Crear una nueva activity que implemente `OnMapReadyCallback`

```java
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{ 
    private GoogleMap myMap; 
 
    @Override 
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_map); 
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager() 
                .findFragmentById(R.id.map); 
        mapFragment.getMapAsync(this); 
    } 
 
 
    @Override 
    public void onMapReady(GoogleMap googleMap) { 
        myMap=googleMap; 
        // COMPLETAR     
    } 
}
```
Ahora solo resta modificar el Layout agregando un fragmento para el mapa
```xml
<fragment 
    android:id="@+id/map" 
    android:name="com.google.android.gms.maps.SupportMapFragment" 
    android:layout_width="match_parent" 
    android:layout_height="match_parent"  /> 
```

### 4. Indicar la ubicación de envío

Al momento de realizar un pedido, se debe agregar la opción de seleccionar la ubicación a la cual se va a enviar el pedido.
Para esto debemos agregar un método que nos permita obtener la ubicación al momento de realizar el pedido, en dicho método
implementar el llamado a la actividad definida anteriormente mediante `startActivityForResult` pasando como parámetro la
ubicación [LatLng](https://developers.google.com/android/reference/com/google/android/gms/maps/model/LatLng) donde el usuario hizo click en el mapa. Para tomar la ubicación usar `OnMapLongClickListener`.

Al momento de abrir el mapa se debe ir a donde esta posicionado, por lo que debe solicitar el permiso `ACCESS_FINE_LOCATION`
(recuerde que a partir de Android 6 debe [solicitarlo en tiempo de ejecución](https://developers.google.com/maps/documentation/android-sdk/location?hl=es-419#request_runtime_permissions). Luego cuando el mapa carga indicar 
`setMyLocationEnabled(true)` y aparecerá la posibilidad de ir a las coordenadas actuales. 

> _Recordar modificar la entidad Pedido para contener el atributo Ubicación._

### 5. Indicar la ubicación del local

Modificar la activity creada anteriormente, para generar un punto aleatorio que indica donde está el restaurante que te
envía el pedido (Usar un Marker distintivo para indicar la posición).

Para generar el punto aleatorio usar [SphericalUtil](https://googlemaps.github.io/android-maps-utils/javadoc/com/google/maps/android/SphericalUtil.html), 
que es una clase de [Android Map Utils](https://github.com/googlemaps/android-maps-utils), la cual vamos a agregar de igual
forma que veníamos haciendo con las anteriores en el gradle.

```
dependencies {
    // Utilities for Maps SDK for Android (requires Google Play Services) 
    implementation 'com.google.maps.android:android-maps-utils:2.1.0'

    // Alternately - Utilities for Maps SDK v3 BETA for Android (does not require Google Play Services)
    implementation 'com.google.maps.android:android-maps-utils-v3:2.1.0'
}
```

Podemos utilizar el metodo `computeOffset` para generar nuestro punto aleatorio de la siguiente manera:

``` java 
Random r = new Random();

// Una direccion aleatoria de 0 a 359 grados
int direccionRandomEnGrados = r.nextInt(360);

// Una distancia aleatoria de 100 a 1000 metros 
int distanciaMinima = 100;
int distanciaMaxima = 1000;
int distanciaRandomEnMetros = r.nextInt(distanciaMaxima - distanciaMinima) + distanciaMinima;

LatLng nuevaPosicion = SphericalUtil.computeOffset(
    posicionOriginal,
    distanciaRandomEnMetros,
    direccionRandomEnGrados
);
```

Usar la Latlng generada para crear un marcador en el mapa.

### 6. Dibujar el recorrido del pedido

Para este propósito, vamos a suponer que la ruta de envió va a ser en linea recta entre ambos puntos, no es un caso de uso
real pero para los efectos prácticos es valido.

A esta altura tenemos generados ambos puntos, el del local y donde se va a recibir el pedido, con esta información vamos
a crear una línea recta que los una. Para esto vamos a usar Polyline
```
var polylineOptions = new PolylineOptions();
polylineOptions.InvokeColor(0x66FF0000);

// Agregar ambos puntos 
polylineOptions.Add(new LatLng(position.Latitude, position.Longitude));

myMap.AddPolyline(polylineOptions);
```

### 7. Agregar y configurar Firebase

Ahora vamos a crear nuestro proyecto en firebase y añadir nuestra App al mismo, para esto

 * 7.1- Ingresar a la [Consola de Firebase](https://console.firebase.google.com/?hl=es) con la misma cuenta de google 
 que usamos en el punto dos.
 * 7.2- Añadimos un nuevo proyecto.
    ![](../laboratorio-05/imagenes/03_nuevo_firebase_proyect.png)
    ###### Pueden elegir automáticamente el mismo nombre que el proyecto que creamos en el punto dos
    ![](../laboratorio-05/imagenes/04_nuevo_firebase_nombre.png)
 * 7.3- Ahora vamos a añadir firebase a nuestra App
    ###### Luego de crear el proyecto, en la pagina principal veran la opción de añadir una aplicación android
    ![](../laboratorio-05/imagenes/05_añadir_android.png)
 * 7.4- Completar el registro paso a paso.
 
> [Paso a paso en mayor detalle](https://firebase.google.com/docs/android/setup?authuser=0)

Ahora que ya tenemos firebase en nuestra aplicación, ya podemos comenzar a hacer uso de todas sos prestaciones. Entre las
cuales podemos destacar: 
- [Authentication](https://firebase.google.com/docs/auth?authuser=0)
- [Cloud Storage](https://firebase.google.com/docs/storage?authuser=0)
- [Realtime Database](https://firebase.google.com/docs/database?authuser=0)
- [Cloud Messaging](https://firebase.google.com/docs/cloud-messaging?authuser=0)
- [App Distribution](https://firebase.google.com/docs/app-distribution)

De las cuales nosotros vamos a usar solamente Cloud Storage, para guardar una foto al momento de dar de alta un plato.

### 8. Configurar Cloud Storage
Lo primero que vamos a hacer es agregar las dependencias que necesitamos:
```
dependencies {
    // Import the BoM for the Firebase platform
    implementation platform('com.google.firebase:firebase-bom:25.12.0')

    // Declare the dependency for the Cloud Storage library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation 'com.google.firebase:firebase-storage'
    // Declare the dependency for the Firebase Authentication library
    implementation 'com.google.firebase:firebase-auth'
}
```
Luego, en el menu lateral izquierdo de firebase, vamos a ir a Desarrollo/Storage y vamos a habilitarlo.
![](../laboratorio-05/imagenes/06_cloud_storage.png)

Hacemos click en 'Empezar' y nos va a salir un modal como el siguiente
![](../laboratorio-05/imagenes/07_reglas_cloud_storage.png)

El cual nos indica que por default las reglas de seguridad de Cloud Storage, permiten lectura y escritura solo a usuario 
autenticados, por lo que nuestro próximo paso será habilitar la autenticación anónima. Para completar la configuración de 
Cloud Storage, deben hacer click en siguiente y por ultimo en listo.

> Autenticación anónima:  Crea cuentas anónimas temporales para permitir el uso de funciones que requieran autenticación 
> sin exigir que los usuarios accedan primero. Firebase se encarga de gestionar este tipo de cuentas, las cuales solamente
> se pueden crear desde nuestra aplicación la cual fue previamente registrada en un proyecto, y dichas cuentas tienen acceso 
>restringido a ese proyecto en particular.

<details>
  <summary>Aclaración: </summary>
  No es objetivo del trabajo practico hacer uso de Firebase Authentication, solamente vamos a configurar la autenticación anónima para tener un minimo de seguridad sobre nuestras operaciones de lectura/escritura, lo cual es recomendado.
</details>

Para habilitar la Autenticación anónima, debemos ir a Desarrollo/Authentication y habilitar "Anónimo"
![](../laboratorio-05/imagenes/08_auth_anonimo.png)

Una vez echo esto, debemos agregar el siguiente código a nuestra actividad principal

```java
public class MainActivity {
    private FirebaseAuth mAuth;
    // ...
    
   @Override
   protected void onCreate(Bundle savedInstanceState) {
    // ...
    // Inicializar Firebase Auth
    mAuth = FirebaseAuth.getInstance();
    // Iniciar Session como usuario anónimo
    signInAnonymously();
   }

    private void signInAnonymously() {
    mAuth.signInAnonymously()
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Exito
                    Log.d(TAG, "signInAnonymously:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    // Error
                    Log.w(TAG, "signInAnonymously:failure", task.getException());
                    Toast.makeText(AnonymousAuthActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
```

Ahora cada vez que nuestra aplicación inicie, se va a autenticar automáticamente como un usuario anónimo. Ahora si, ya estamos 
listos para usar Cloud Storage.

### 9. Guardar archivos en Cloud Storage.

Como mencionamos anteriormente, la idea de usar Cloud Storage es que nos permita almacenar las imágenes de los platos que se crean.
Para esto deberán agregar, en la actividad encargada de dar de alta los platos, un intent a la cámara y al recibir el resultado,
lo almacenaran en Cloud Storage; Esta operación nos retornara un "path", que es la dirección en la cual se guardo la imagen, 
dicho path lo vamos a almacenar en el objeto Plato que ya teníamos de los laboratorios anteriores.

Para lograr esto, debemos hacer un Intent para obtener una imagen, ya sea desde la cámara o desde los archivos

```java
public class SomeActivity {
    static final int CAMARA_REQUEST = 1;
    static final int GALERIA_REQUEST = 2;
    
    private void lanzarCamara() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camaraIntent, CAMARA_REQUEST);
    }
    
    private void abrirGaleria() {
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeriaIntent, GALERIA_REQUEST);
    }
    
    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if ((requestCode == CAMARA_REQUEST ||  requestCode == GALERIA_REQUEST) && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray(); // Imagen en arreglo de bytes
            }
        }
}
```
Recordar que debemos pedir los permisos necesarios, en este caso `IMAGE_CAPTURE`.

Una vez que tenemos nuestra imagen, vamos a subirla al Storage

```java
public class SomeActivity {
    // ... 
    private void someFunction() {
       // Creamos una referencia a nuestro Storage
       StorageReference storageRef = storage.getReference();
    
       // Creamos una referencia a 'images/plato_id.jpg'
       StorageReference platosImagesRef = storageRef.child("images/plato_id.jpg");
   }
}
```

Una vez que crees una referencia apropiada, podemos llamar al método `putBytes()`, `putFile()` o `putStream()` para subir el archivo 
a Cloud Storage.

```java
public class SomeActivity {
    // ... 
    // Función donde vamos a guardar la imagen
    private void someFunction() {

        // Cual quiera de los tres métodos tienen la misma implementación, se debe utilizar el que corresponda
        UploadTask uploadTask = platosImagesRef.putBytes(data);
        // UploadTask uploadTask = platosImagesRef.putFile(file);
        // UploadTask uploadTask = platosImagesRef.putStream(stream);
       
        // Registramos un listener para saber el resultado de la operación
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
        
                // Continuamos con la tarea para obtener la URL
                return platosImagesRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // URL de descarga del archivo
                    Uri downloadUri = task.getResult();
                } else {
                    // Fallo
                }
            }
        });
    }
}
```

Una vez que obtenemos la Uri, la debemos guardar como una propiedad de nuestro objeto plata para luego poder descargarla 
y mostrarla.

### 10. Descargar archivos de Cloud Storage

Ahora que nuestros platos tienen una imagen guardada, lo que vamos a hacer es utilizarla en la preview, para esto debemos 
descargar la imagen y colocarla en el `imageView` que ya teníamos anteriormente en la lista de platos.

Para esto lo que debemos hacer es tomar la dirección a la imagen, el path, que persistimos en nuestro objeto Plato y crear
una referencia a Firebase Storage, luego con el método `getBytes()` podremos obtener lo que almacenamos previamente.

```java
public class SomeActivity {
    // ... 
    // Función donde vamos a descargar la imagen
    private void someFunction() {
        // Creamos una referencia al storage con la Uri de la img
        StorageReference gsReference = storage.getReferenceFromUrl("gs://bucket/images/something.jpg");
        
        final long THREE_MEGABYTE = 3 * 1024 * 1024;
        gsReference.getBytes(THREE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Exito
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
    
                imageView.setMinimumHeight(dm.heightPixels);
                imageView.setMinimumWidth(dm.widthPixels);
                imageView.setImageBitmap(bm);
           }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Error - Cargar una imagen por defecto
            }
        });
    }
}
```
> Descarga el archivo a un `byte[]` con el método `getBytes()`. Es la forma más fácil de descargar un archivo, pero requiere 
>cargar todo su contenido en la memoria. Si solicitas un archivo más grande que la memoria disponible, fallará. Por lo 
>tanto `getBytes()`, necesita el tamaño máximo con un valor que sepas que la app pueda controlar.

### 11. Recibir Notificaciones Push
Para recibir notificaciones push, lo que vamos a hacer es configurar el servicio de Firebase Cloud Messaging, que nos va
a permitir recibir mensajes de firebase y realizar una acción en consecuencia, la cual puede variar dependiendo del tipo
o el contenido del mensaje, en este caso el comportamiento va a ser siempre el mismo y es mostrar una notificación que diga
_'Tu pedido ha sido confirmado por el local.'_
 
Para esto, lo primero que vamos a hacer es agregar las dependencias que necesitamos para nuevo modulo:

```
dependencies {
    // ...
    implementation 'com.google.firebase:firebase-messaging'
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'com.google.firebase:firebase-messaging-directboot:20.2.0'

}
```

Una vez que tenemos las nuevas dependencias, vamos a tener que agregar el siguiente código al `manifest.xml`

```xml
<service
    android:name=".java.MyFirebaseMessagingService"
    android:directBootAware="true"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>
```

Lo que hace este código, es registrar un servicio llamado `MyFirebaseMessagingService`, que va a ser quien escuche la llegada de nuestras notificaciones.

> Si desean cambiar el icono de la notificacion y/o el color, pueden hacerlo como indica el 2do item [aquí](https://firebase.google.com/docs/cloud-messaging/android/client?hl=es#manifest)

Ahora, vamos a crear nuestro servicio `MyFirebaseMessagingService`

```java
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...
        Log.d(TAG, "From: " + remoteMessage.getFrom());
  
        // Pueden validar si el mensaje trae datos
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Payload del mensaje: " + remoteMessage.getData());
            // Realizar alguna acción en consecuencia
        }
        // Pueden validar si el mensaje trae una notificación
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Cuerpo de la notificación del mensaje: " + remoteMessage.getNotification().getBody());
            // También pueden usar:
            // remoteMessage.getNotification().getTitle()
        }
       // Cualquier otra acción que quieran realizar al recibir un mensaje de firebase, la pueden realizar aca
       // EJ:        
       sendNotification('Cuerpo de la notificacion');
    }

    // Función para crear una notificación (completar)
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // PendingIntent pendingIntent = ....

        //NotificationCompat.Builder notificationBuilder =
        //        new NotificationCompat.Builder(this, channelId)
        //                          ....
        //               .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        //    NotificationChannel channel = new NotificationChannel(....);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID notificación */, notificationBuilder.build());
    }

}
```

Una vez que tenemos esto en nuestra aplicación, esta lista para recibir mensajes de FCM y crear una notificación con el 
mismo.

Para poder probarlo, lo unico que nos falta es obtener el token que referencia a nuestro dispositivo en FCM, para esto
vamos a usar el siguiente codigo:

```java
FirebaseMessaging.getInstance().getToken()
    .addOnCompleteListener(new OnCompleteListener<String>() {
        @Override
        public void onComplete(@NonNull Task<String> task) {
          if (!task.isSuccessful()) {
            // Error
            return;
          }

          // FCM token
          String token = task.getResult();

          // Imprimirlo en un toast y en logs
          Log.d('[FCM - TOKEN]', token);
          Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
        }
    });
```

Ahora, una ves que tenemos nuestro token, vamos a ir a la consola de firebase [aquí](https://console.firebase.google.com/project/bar-sells-poc/notification?hl=es)

![](../laboratorio-05/imagenes/09_firebase_cm.jpg)

Vamos a 'Enviar tu primer mensaje' y nos va a mostrar un formulario con los campos que podemos enviar.

![](../laboratorio-05/imagenes/10_enviar_mensaje.png)

Luego va a requerir el token que tomamos de los logs

![](../laboratorio-05/imagenes/11_ingresar_token.png)

Una vez la hayan enviado, van a poder ver el estado de esta y otras notificaciones que hayan creado desde la web de Firebase.

![](../laboratorio-05/imagenes/12_ver_envios.png)