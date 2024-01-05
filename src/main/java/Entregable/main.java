/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package Entregable;

import Entregable.model.Producto;
import Entregable.pojo.ProductoPojo;
import Entregable.utils.LeerCSV;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author hp
 */
public class main {

    public static void main(String[] args) throws IOException {
        cargarDatos();
        ProductoPojo pr = new ProductoPojo();
        String comando;
        do {
            System.out.println("Introduzca comando ");
            comando = new Scanner(System.in).nextLine().strip().toLowerCase();
            if (comando.equalsIgnoreCase("listar")) {
                listar(pr);
            } else if (comando.startsWith("usar")) {
                usar(pr, comando);
            } else if (comando.startsWith("hay")) {
                hay(pr, comando);
            } else if (comando.startsWith("adquirir")) {
                adquirir(pr, comando);
            } else {
                System.out.println("comando no reconocido");
                System.out.println("listar -> para ver lista productos");
                System.out.println("usar <cantidad> <producto> -> actualiza el producto dependiendo del uso");
                System.out.println("hay <producto> -> verificar el stock actual del producto deseado");
                System.out.println("adquirir <cantidad> <producto> -> agrega o actualiza el producto deseado y su stock");

            }

        } while (!comando.equalsIgnoreCase("salir"));
    }

    public static void cargarDatos() {
        try {
            ProductoPojo pr = new ProductoPojo();
            if (pr.getAllProduct().isEmpty()) {
                List<Producto> productos = LeerCSV.cargarDatosDesdeCSV("compra.csv");
                for (Producto producto : productos) {
                    // Verificar si el producto ya existe en la base de datos por su nombre
                    Producto productoExistente = pr.getProductByName(producto.getName());
                    if (productoExistente == null) {
                        // El producto no existe, realizar inserción
                        pr.addProduct(producto);
                    } else {
                        // El producto ya existe, realizar actualización sumando las cantidades
                        productoExistente.setCantidad(productoExistente.getCantidad() + producto.getCantidad());
                        pr.updateProduct(productoExistente);
                    }
                }
            }
        } catch (IOException | SQLException error) {
            System.err.println(error);
        }
    }

    public static void listar(ProductoPojo pr) {
        for (Producto producto : pr.getAllProduct()) {
            System.out.println(producto);
        }
    }

    public static void usar(ProductoPojo pr, String comando) {
        // matches realiza un coincidir con (Validar el formato del comando) 
        if (!comando.matches("^usar \\d+ [a-zA-Z ]+$")) {
            System.out.println("Formato de comando incorrecto. Debe ser 'usar <cantidad> <producto>'");
            return;  // Salir del método si el formato no es correcto
        }
        String[] partes = comando.split(" ", 3);
        String producto = partes[2];
        int x = Integer.parseInt(partes[1]);
        Producto productoExistente = pr.getProductByName(producto);
        if (productoExistente != null) {
            if (productoExistente.getCantidad() < x) {
                System.out.println("No hay Stock suficiente.");
            } else if (productoExistente.getCantidad() == x) {
                pr.deletProduct(productoExistente.getId());
                System.out.println("producto eliminado");
            } else {
                productoExistente.setCantidad(productoExistente.getCantidad() - x);
                pr.updateProduct(productoExistente);
            }
        } else {
            System.out.println("El producto no existe.");
        }

    }

    public static void hay(ProductoPojo pr, String comando) {
        if (!comando.matches("^usar [a-zA-Z ]+$")) {
            System.out.println("Formato de comando incorrecto. Debe ser 'hay <producto>'");
            return;  // Salir del método si el formato no es correcto
        }
        String[] partes = comando.split(" ", 2);
        String producto = partes[1];
        Producto productoExistente = pr.getProductByName(producto);
        if (productoExistente == null) {
            System.out.println("El producto no existe.");
        } else {
            System.out.println("Cantidad restante : " + productoExistente.getCantidad());
        }
    }

    public static void adquirir(ProductoPojo pr, String comando) {
        if (!comando.matches("^usar \\d+ [a-zA-Z ]+$")) {
            System.out.println("Formato de comando incorrecto. Debe ser 'usar <cantidad> <producto>'");
            return;  // Salir del método si el formato no es correcto
        }
        String[] partes = comando.split(" ", 3);
        String producto = partes[2];
        int x = Integer.parseInt(partes[1]);
        Producto productoExistente = pr.getProductByName(producto);
        if (productoExistente == null) {
            pr.addProduct(new Producto(x, producto));
        } else {
            productoExistente.setCantidad(productoExistente.getCantidad() + x);
            pr.updateProduct(productoExistente);
        }
    }
}
