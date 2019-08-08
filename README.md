# lastFmV

# Descripcion General

Es una aplicacion realizada nativamente en lenguaje Android que consta de 2 menus llamada lastFmV

| Menu | Descripción |
| ------------- | :---: |
| `Top de Artistas` | Muestra el listado de los artistas mas populares Informacion brindada por el API. |
| `Top de Canciones` | Muestra el listado de las canciones mas populares Informacion brindada por el API. |

Se uso un model MVP para la realizacion del proyecto. Las consultas y las capacidades existentes en el servicio, tienen un cache de 10 megas, en el momento en que se requiera buscar informacion offline. 

# Capas de la aplicación
Estas son las capas contempladas en el desarrollo:

| Capa | Paquete en el Proyecto | Descripción |
| ------------- | :---: | :---: |
| Modelo | `models, common` | Desarrollo de los clases encargas de instanciar los objetos utilizados en el API lastFM |
| Vista | `ui` | Se encarga de las interfaces mostradas al usuario. Contiene las actividades y adaptadores necesarios para los RecyclerView y anexo de contenido multimedia o animacion (Splashscreen y Video) |
| Conexion | `network, sqlite` | Desarrollo de clases encargadas de consumir y mapear la consulta de peticiones del API lastFM |
| Almacenamiento Local | `sqlite` | Encargado de almacenar de manera local toda la informaciono obtenida, en el momento de ser consultada hacia el API lastFM |
| Presentador | `contract` | Contiene el desarrollo del modelo MVP implementado en donde se encuentra los contratos, presentadores e interadores entre la vista general de la app y el detalle de cada una. |

# Tecnologias implementadas 
Estas son las tecnologias impletadas para el anexo de conexiones con el API TMDb, interfaz y animaciones:

| Tecnologia | URL |
| ------------- | :---: |
| Retrofit2 | `https://github.com/square/retrofit` |
| Okhttp3 | `https://github.com/square/okhttp` |
| ButherKnife | `https://github.com/JakeWharton/butterknife` |
| rxjava | `https://github.com/ReactiveX/RxJava` |
| rxandroid | `https://github.com/ReactiveX/RxAndroid` |
| Dagger2 | `https://github.com/google/dagger` |
| Glide | `https://github.com/bumptech/glide` |
| AwesomeSplash | `https://github.com/ViksaaSkool/AwesomeSplash` |
| MaterialSearchBar | `https://github.com/mancj/MaterialSearchBar` |
