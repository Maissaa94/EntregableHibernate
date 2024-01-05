/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entregable.utils;

import Entregable.model.Producto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class LeerCSV {

 
    public static List<Producto> cargarDatosDesdeCSV(String rutaCSV) throws IOException, SQLException {
        List<Producto> retornar = new ArrayList<>();
        try ( BufferedReader br = new BufferedReader(new FileReader(new File(rutaCSV)))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] valores = line.split(";");
                if (valores.length == 2) {
                    Integer cantidad = Integer.parseInt(valores[0]);
                    String suministro = valores[1];
                    Producto producto = new Producto(cantidad, suministro);
                    retornar.add(producto);
                } else {
                    System.out.println("error ");
                }
            }
        }
        return retornar;
    }
}
