# TiendaControl - modulo de productos (GA7-220501096-AA2-EV01)

**Aprendiz:** Andres Felipe Gomez Pinto  
**Programa:** Analisis y Desarrollo de Software - SENA  
**Tecnologia:** Java 17, JDBC, MySQL y Maven  
**Modulo:** administracion de productos de inventario.

## 1. Objetivo

Implementar un modulo de software que permita registrar, consultar, actualizar y eliminar productos de una tienda, conectandose a una base de datos relacional MySQL mediante JDBC y aplicando convenciones de codificacion.

## 2. Funcionalidades incluidas

1. Registrar productos con codigo de barras, nombre, descripcion, precio y stock.
2. Consultar la lista de productos.
3. Buscar un producto por su identificador.
4. Actualizar la informacion de un producto.
5. Eliminar un producto con confirmacion.
6. Validar campos obligatorios, numeros enteros, precio no negativo y stock no negativo.

Las operaciones SQL emplean `PreparedStatement`, que separa los valores de los comandos SQL y ayuda a evitar inyeccion SQL. Las conexiones, sentencias y resultados se cierran con `try-with-resources`.

## 3. Estructura del proyecto

```text
TiendaControl/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    └── main/
        ├── java/co/tiendacontrol/
        │   ├── app/Main.java
        │   ├── config/ConexionDB.java
        │   ├── dao/ProductoDAO.java
        │   └── model/Producto.java
        └── resources/sql/crear_base_datos.sql
```

## 4. Requisitos previos

- JDK 17 o superior.
- MySQL Server y una herramienta para ejecutar SQL (por ejemplo, MySQL Workbench).
- Apache Maven 3.8 o superior, si se ejecuta desde la terminal.
- Conexion a Internet en la primera compilacion para que Maven descargue MySQL Connector/J.

## 5. Configurar la base de datos

1. Inicie MySQL Server.
2. Abra MySQL Workbench y conectese a su servidor local.
3. Abra `src/main/resources/sql/crear_base_datos.sql` y ejecute todo el archivo.
4. El script crea la base de datos `tiendacontrol`, la tabla `productos` y cinco productos de ejemplo.

## 6. Configurar la conexion

La aplicacion toma estos valores de variables de entorno:

- `MYSQL_URL`: URL JDBC. Si no se define, se usa `jdbc:mysql://localhost:3306/tiendacontrol?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`.
- `MYSQL_USER`: usuario MySQL. Por defecto `root`.
- `MYSQL_PASSWORD`: contrasena MySQL. Por defecto queda vacia.

En Windows PowerShell, antes de ejecutar:

```powershell
$env:MYSQL_USER = "root"
$env:MYSQL_PASSWORD = "ESCRIBA_AQUI_SU_CLAVE"
$env:MYSQL_URL = "jdbc:mysql://localhost:3306/tiendacontrol?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
```

Si la cuenta local no tiene contrasena, puede dejar `MYSQL_PASSWORD` como cadena vacia. No comparta sus credenciales ni las suba al repositorio.

## 7. Ejecutar desde terminal

Abra una terminal en la carpeta `TiendaControl` y ejecute:

```powershell
mvn clean compile exec:java
```

Maven descargara la dependencia del controlador JDBC la primera vez. Aparecera un menu para registrar, listar, buscar, actualizar o eliminar productos.

Tambien se puede abrir la carpeta como proyecto Maven desde un IDE compatible con Java, como Apache NetBeans o Visual Studio Code con las extensiones de Java instaladas.

## 8. Estandares de codificacion aplicados

- Paquetes en minuscula: `co.tiendacontrol.dao`.
- Clases con PascalCase: `Producto`, `ProductoDAO`, `ConexionDB` y `Main`.
- Variables y metodos en camelCase: `codigoBarras`, `obtenerConexion()`, `listarTodos()`.
- Constantes con mayusculas y guion bajo: `SQL_INSERTAR`, `URL_POR_DEFECTO`.
- Una clase por responsabilidad: modelo, acceso a datos, conexion y ejecucion.
- Uso de `BigDecimal` para el precio y no `double`, para evitar errores comunes de representacion decimal.
- Consultas parametrizadas y recursos JDBC gestionados con `try-with-resources`.
- Restricciones en la base de datos para codigo unico, precio no negativo y stock no negativo.

## 9. Matriz inicial de pruebas

| ID | Accion | Datos | Resultado esperado |
|---|---|---|---|
| CP-01 | Consultar inventario | Ejecutar opcion 2 | Mostrar los productos existentes. |
| CP-02 | Registrar producto valido | Codigo y nombre no vacios, precio 1200.00, stock 5 | Insertar y confirmar el registro. |
| CP-03 | Registrar sin nombre | Nombre vacio | Solicitar nuevamente el campo. |
| CP-04 | Ingresar precio no numerico | Precio `abc` | Mostrar validacion y solicitar un precio valido. |
| CP-05 | Ingresar stock negativo | Stock `-2` | Rechazar el valor y solicitar de nuevo. |
| CP-06 | Buscar ID inexistente | ID `999999` | Informar que no existe el producto. |
| CP-07 | Actualizar producto | ID existente y datos validos | Actualizar y confirmar el cambio. |
| CP-08 | Eliminar producto | ID existente y confirmar `SI` | Eliminar el registro. |
| CP-09 | Cancelar eliminacion | Escribir algo diferente de `SI` | Mantener el producto y cancelar. |
| CP-10 | Codigo de barras duplicado | Registrar codigo ya existente | MySQL rechaza la duplicacion por restriccion UNIQUE. |

**Nota sobre pruebas:** la matriz describe los casos previstos. Debe ejecutarlos en su equipo con MySQL configurado y capturar pantallazos reales para anexarlos si el instructor los solicita. No se afirma que las pruebas de integracion con un servidor MySQL externo hayan sido ejecutadas en este entorno.

## 10. Control de versiones Git

Desde esta carpeta del proyecto:

```powershell
git init
git add .
git commit -m "Implementa modulo CRUD de productos con JDBC"
git branch -M main
git remote add origin https://github.com/TU-USUARIO/Programa-Git.git
git push -u origin main
```

Cree primero un repositorio remoto vacio en su cuenta, sustituya la URL de ejemplo por la URL real y registre esa URL en el archivo `../ENLACE_REPOSITORIO.txt`. No publique contrasenas ni otros datos privados.

## 11. Alcance

Esta evidencia implementa el modulo de productos mediante una interfaz de consola para demostrar las operaciones CRUD y la conexion JDBC. No incluye autenticacion ni modulo de facturacion. La base de datos debe estar instalada y en ejecucion para probar las operaciones.
