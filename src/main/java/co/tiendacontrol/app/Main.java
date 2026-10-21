package co.tiendacontrol.app;

import co.tiendacontrol.dao.ProductoDAO;
import co.tiendacontrol.model.Producto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/** Interfaz de consola para demostrar el CRUD de productos de TiendaControl. */
public class Main {
    private static final Scanner ENTRADA = new Scanner(System.in);
    private static final ProductoDAO PRODUCTO_DAO = new ProductoDAO();

    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("       TIENDACONTROL - PRODUCTOS     ");
        System.out.println("====================================");

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opcion: ");
            try {
                switch (opcion) {
                    case 1 -> registrarProducto();
                    case 2 -> listarProductos();
                    case 3 -> buscarProducto();
                    case 4 -> actualizarProducto();
                    case 5 -> eliminarProducto();
                    case 0 -> continuar = false;
                    default -> System.out.println("Opcion no valida. Intente de nuevo.");
                }
            } catch (SQLException e) {
                mostrarErrorBaseDatos(e);
            }
            System.out.println();
        }
        System.out.println("Sesion finalizada. Gracias por utilizar TiendaControl.");
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Registrar producto");
        System.out.println("2. Consultar todos los productos");
        System.out.println("3. Buscar producto por ID");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("0. Salir");
    }

    private static void registrarProducto() throws SQLException {
        System.out.println("\n--- REGISTRAR PRODUCTO ---");
        Producto producto = leerDatosProducto();
        PRODUCTO_DAO.insertar(producto);
        System.out.println("Producto registrado correctamente.");
    }

    private static void listarProductos() throws SQLException {
        System.out.println("\n--- INVENTARIO ---");
        List<Producto> productos = PRODUCTO_DAO.listarTodos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        productos.forEach(System.out::println);
        System.out.println("Total de productos: " + productos.size());
    }

    private static void buscarProducto() throws SQLException {
        int id = leerEntero("ID del producto: ");
        Optional<Producto> producto = PRODUCTO_DAO.buscarPorId(id);
        if (producto.isPresent()) {
            System.out.println(producto.get());
        } else {
            System.out.println("No existe un producto con el ID indicado.");
        }
    }

    private static void actualizarProducto() throws SQLException {
        int id = leerEntero("ID del producto que desea actualizar: ");
        Optional<Producto> encontrado = PRODUCTO_DAO.buscarPorId(id);
        if (encontrado.isEmpty()) {
            System.out.println("No existe un producto con el ID indicado.");
            return;
        }
        System.out.println("Datos actuales: " + encontrado.get());
        System.out.println("Ingrese los nuevos datos del producto:");
        Producto datos = leerDatosProducto();
        if (PRODUCTO_DAO.actualizar(id, datos)) {
            System.out.println("Producto actualizado correctamente.");
        } else {
            System.out.println("No fue posible actualizar el producto.");
        }
    }

    private static void eliminarProducto() throws SQLException {
        int id = leerEntero("ID del producto que desea eliminar: ");
        Optional<Producto> producto = PRODUCTO_DAO.buscarPorId(id);
        if (producto.isEmpty()) {
            System.out.println("No existe un producto con el ID indicado.");
            return;
        }
        System.out.println("Se eliminara: " + producto.get());
        String confirmacion = leerTexto("Escriba SI para confirmar: ");
        if ("SI".equalsIgnoreCase(confirmacion)) {
            boolean eliminado = PRODUCTO_DAO.eliminar(id);
            System.out.println(eliminado ? "Producto eliminado correctamente." : "No fue posible eliminar el producto.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private static Producto leerDatosProducto() {
        String codigoBarras = leerTextoObligatorio("Codigo de barras: ");
        String nombre = leerTextoObligatorio("Nombre: ");
        String descripcion = leerTexto("Descripcion (opcional): ");
        BigDecimal precio = leerPrecio("Precio: ");
        int stock = leerEnteroNoNegativo("Cantidad en inventario (stock): ");
        return new Producto(codigoBarras, nombre, descripcion, precio, stock);
    }

    private static String leerTextoObligatorio(String mensaje) {
        while (true) {
            String valor = leerTexto(mensaje);
            if (!valor.isBlank()) {
                return valor;
            }
            System.out.println("Este campo es obligatorio.");
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return ENTRADA.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    private static int leerEnteroNoNegativo(String mensaje) {
        while (true) {
            int valor = leerEntero(mensaje);
            if (valor >= 0) {
                return valor;
            }
            System.out.println("El valor no puede ser negativo.");
        }
    }

    private static BigDecimal leerPrecio(String mensaje) {
        while (true) {
            String valor = leerTexto(mensaje).replace(',', '.');
            try {
                BigDecimal precio = new BigDecimal(valor);
                if (precio.signum() >= 0) {
                    return precio;
                }
                System.out.println("El precio no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un precio valido, por ejemplo 2500.00.");
            }
        }
    }

    private static void mostrarErrorBaseDatos(SQLException e) {
        System.out.println("Error de base de datos: " + e.getMessage());
        System.out.println("Verifique que MySQL este iniciado, que haya ejecutado src/main/resources/sql/crear_base_datos.sql y que las variables MYSQL_URL, MYSQL_USER y MYSQL_PASSWORD sean correctas.");
        if (e.getMessage() != null && e.getMessage().toLowerCase().contains("access denied")) {
            System.out.println("MySQL rechazo las credenciales. Configure la clave de su usuario en MYSQL_PASSWORD.");
        }
    }
}
