package co.tiendacontrol.model;

import java.math.BigDecimal;

/** Representa un producto almacenado en la tabla productos. */
public class Producto {
    private int id;
    private String codigoBarras;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stock;

    public Producto(int id, String codigoBarras, String nombre, String descripcion,
                    BigDecimal precio, int stock) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public Producto(String codigoBarras, String nombre, String descripcion,
                    BigDecimal precio, int stock) {
        this(0, codigoBarras, nombre, descripcion, precio, stock);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("ID: %d | Codigo: %s | Producto: %s | Precio: $%s | Stock: %d | Descripcion: %s",
                id, codigoBarras, nombre, precio.toPlainString(), stock,
                descripcion == null || descripcion.isBlank() ? "Sin descripcion" : descripcion);
    }
}
