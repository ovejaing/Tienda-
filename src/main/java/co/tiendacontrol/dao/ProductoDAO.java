package co.tiendacontrol.dao;

import co.tiendacontrol.config.ConexionDB;
import co.tiendacontrol.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Acceso a datos de productos mediante JDBC y consultas parametrizadas. */
public class ProductoDAO {
    private static final String SQL_INSERTAR = "INSERT INTO productos "
            + "(codigo_barras, nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_LISTAR = "SELECT id, codigo_barras, nombre, descripcion, precio, stock "
            + "FROM productos ORDER BY id";
    private static final String SQL_BUSCAR_ID = "SELECT id, codigo_barras, nombre, descripcion, precio, stock "
            + "FROM productos WHERE id = ?";
    private static final String SQL_ACTUALIZAR = "UPDATE productos SET codigo_barras = ?, nombre = ?, "
            + "descripcion = ?, precio = ?, stock = ? WHERE id = ?";
    private static final String SQL_ELIMINAR = "DELETE FROM productos WHERE id = ?";

    public void insertar(Producto producto) throws SQLException {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {
            completarParametros(sentencia, producto);
            sentencia.executeUpdate();
        }
    }

    public List<Producto> listarTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR);
             ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                productos.add(mapearProducto(resultado));
            }
        }
        return productos;
    }

    public Optional<Producto> buscarPorId(int id) throws SQLException {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_ID)) {
            sentencia.setInt(1, id);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapearProducto(resultado));
                }
            }
        }
        return Optional.empty();
    }

    public boolean actualizar(int id, Producto producto) throws SQLException {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR)) {
            completarParametros(sentencia, producto);
            sentencia.setInt(6, id);
            return sentencia.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR)) {
            sentencia.setInt(1, id);
            return sentencia.executeUpdate() > 0;
        }
    }

    private void completarParametros(PreparedStatement sentencia, Producto producto) throws SQLException {
        sentencia.setString(1, producto.getCodigoBarras());
        sentencia.setString(2, producto.getNombre());
        sentencia.setString(3, producto.getDescripcion());
        sentencia.setBigDecimal(4, producto.getPrecio());
        sentencia.setInt(5, producto.getStock());
    }

    private Producto mapearProducto(ResultSet resultado) throws SQLException {
        return new Producto(
                resultado.getInt("id"),
                resultado.getString("codigo_barras"),
                resultado.getString("nombre"),
                resultado.getString("descripcion"),
                resultado.getBigDecimal("precio"),
                resultado.getInt("stock")
        );
    }
}
