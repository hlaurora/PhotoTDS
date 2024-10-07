# PhotoTDS

Proyecto realizado para la asignatura Tecnologías de Desarrollo de Software (TDS) en la Universidad de Murcia, curso 2022-2023. 
Se trata de una aplicación para compartir fotos que incluye funciones como registrar usuarios, subir fotos, seguir a otros usuarios para ver sus fotos, darles *me gusta* o añadir comentarios, crear álbumes con varias fotos y buscar usuarios para visualizar su perfil.

Consulta el [MANUAL.md](MANUAL.md) para obtener una guía detallada sobre cómo interactuar con la interfaz de la aplicación.

## Diagrama de clases

![image](https://github.com/user-attachments/assets/1bf13a14-896d-4407-9706-7f9596b27e9d)


## Arquitectura de la aplicación

La arquitectura de la aplicación PhotoTDS sigue una estructura basada en tres capas:
Presentación, Lógica de Negocio y Almacenamiento, para lograr modularidad y una separación clara de responsabilidades. 

La capa de **presentación** corresponde al paquete *gui*, en el que se implementan las ventanas y controles necesarios para que los usuarios utilicen la aplicación, realizando el login, subiendo nuevas fotos, siguiendo a otros usuarios… Para la implementación de la interfaz de usuario se utiliza la tecnología `Java Swing`.

En cuanto a la capa de **Lógica de Negocio**, contiene la lógica principal de la aplicación y se encarga de procesar las solicitudes del usuario. Se compone de la clase `Controlador` y el paquete dominio, donde se incluyen los métodos necesarios para manejar el
login, registro de usuarios, creación de fotos y álbumes, gestión de notificaciones y las demás
funcionalidades de la aplicación.

La capa de **Acceso a Datos** se encarga de gestionar el acceso a la base de datos, donde se almacenarán los datos de los usuarios, fotos, álbumes y toda su información. Aquí se implementan los métodos y consultas para guardar y recuperar información de la base de
datos. Para el almacenamiento de los datos se utiliza un servicio de persistencia proporcionado que hace uso del servidor H2. Las clases persistentes son `Usuario`,`Publicación` (`Foto` y `Álbum`), `Comentario` y `Notificación` ya que son los objetos persistentes
sobre los que se realizan consultas.


Para la gestión de dependencias y la construcción del proyecto, se ha utilizado la herramienta `Maven`. Se ha configurado un archivo `pom.xml` en la raíz del proyecto en el que
se manejan las dependencias a librerías externas.


### Patrones de diseño

A continuación se incluye una breve explicación de los patrones de diseño utilizados, algunos
implementados directamente y otros por formar parte de alguna librería usada.

* Patrón DAO. Se utiliza para desacoplar la capa de almacenamiento del resto de la
aplicación. Se han implementado adaptadores para cada una de las clases persistentes,
a través de los cuales se interactúa con la capa de persistencia, encapsulando los
detalles de implementación de la base de datos.
* Singleton: utilizado en el Controlador de la aplicación y en los dos repositorios
(RepoUsuarios y RepoPublicaciones) para asegurar que solo haya una instancia de
estas clases con la que se comuniquen el resto de partes de la aplicación.
* Observador: utilizado en Java Swing para notificar a los componentes de la interfaz
de usuario sobre cambios en el estado de otros componentes. Hacemos uso de este
patrón con los listeners definidos en los distintos componentes, como por ejemplo los
botones de las ventanas.
* Decorador: utilizado en Java Swing para añadir funcionalidades adicionales a los
componentes sin modificar su estructura, por ejemplo se usa en la aplicación al añadir
características como bordes o scrollbars a los paneles.
* Plantilla: en la clase Descuento se define el método abstracto aplicarDescuento()
para que las subclases que heredan de ella, DescuentoEdad y DescuentoPopularidad
lo implementen de acuerdo a sus reglas.



## Componentes utilizados

En el desarrollo de la aplicación se ha hecho uso de varios componentes Java Bean. En primer lugar el componente `JCalendar` se utiliza en la ventana del Registro y permite al
usuario seleccionar su fecha de nacimiento a partir de un calendario.
Además se utilizan en conjunto el componente `Luz`, proporcionado por los profesores de la asignatura, y el componente `CargadorFotos`, implementado junto con la aplicación. El componente Luz representa un pulsador, que al pulsarlo cambia de estado y notifica a sus oyentes de este cambio. En cuanto al CargadorFotos, sirve para cargar nuevas fotos a la aplicación proporcionando un archivo xml con la descripción y ruta de dichas fotos.
El pulsador se encuentra en la ventana principal y al pulsarlo se solicita el archivo xml y se cargan las nuevas fotos.

![image](https://github.com/user-attachments/assets/a04c7683-455b-4865-a425-8c13bb8f9aa9)


## Despliegue

Para ejecutar la aplicación, es necesario poner en marcha la base de datos. Para ello hay que situarse en el directorio `ServidorPersistencia` y ejecutar `java -jar .\ServidorPersistenciaH2.jar`.
Una vez hecho esto, ya se puede lanzar la aplicación ejecutando el `Lanzador.java` del directorio `main`.
