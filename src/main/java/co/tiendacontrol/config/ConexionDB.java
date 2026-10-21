package co.tiendacontrol.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Centraliza la conexion JDBC con MySQL. Las credenciales se leen del entorno. */
public final class ConexionDB {
    private static final String URL_POR_DEFECTO =
            "jdbc:mysql://localhost:3306/tiendacontrol?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO_POR_DEFECTO = "root";
    private static final String CLAVE_POR_DEFECTO = "";

    private ConexionDB() {
        // Evita instanciar esta clase de utilidades.
    }

    public static Connection obtenerConexion() throws SQLException {
        String url = valorEntorno("MYSQL_URL", URL_POR_DEFECTO);
        String usuario = valorEntorno("MYSQL_USER", USUARIO_POR_DEFECTO);
        String clave = valorEntorno("MYSQL_PASSWORD", CLAVE_POR_DEFECTO);
        return DriverManager.getConnection(url, usuario, clave);
    }

    private static String valorEntorno(String nombre, String valorDefecto) {
        String valor = System.getenv(nombre);
        return valor == null ? valorDefecto : valor;
    }
}
