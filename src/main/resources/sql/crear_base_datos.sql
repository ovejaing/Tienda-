-- TiendaControl - Esquema MySQL para el modulo CRUD de productos.
-- Ejecutar este archivo en MySQL Workbench antes de iniciar la aplicacion.

CREATE DATABASE IF NOT EXISTS tiendacontrol
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE tiendacontrol;

CREATE TABLE IF NOT EXISTS productos (
    id INT NOT NULL AUTO_INCREMENT,
    codigo_barras VARCHAR(50) NOT NULL,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(255) NULL,
    precio DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    stock INT NOT NULL DEFAULT 0,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_productos_codigo_barras UNIQUE (codigo_barras),
    CONSTRAINT chk_productos_precio CHECK (precio >= 0),
    CONSTRAINT chk_productos_stock CHECK (stock >= 0)
) ENGINE=InnoDB;

-- Datos de ejemplo para facilitar la demostracion. Se pueden borrar si no se necesitan.
INSERT INTO productos (codigo_barras, nombre, descripcion, precio, stock) VALUES
('770100100001', 'Arroz 1 kg', 'Arroz blanco paquete de un kilogramo', 4200.00, 25),
('770100100002', 'Leche 1 L', 'Leche entera larga vida', 3900.00, 18),
('770100100003', 'Aceite 900 ml', 'Aceite vegetal', 8900.00, 12),
('770100100004', 'Azucar 1 kg', 'Azucar blanca paquete de un kilogramo', 4500.00, 20),
('770100100005', 'Cafe 250 g', 'Cafe molido paquete de 250 gramos', 7800.00, 14)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);
