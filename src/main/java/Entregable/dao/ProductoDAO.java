/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Entregable.dao;

import Entregable.model.Producto;
import java.util.List;

/**
 *
 * @author hp
 */
public interface ProductoDAO {

    public void addProduct(Producto pr);

    public void updateProduct(Producto pr);

    public Producto getProductByName(String name);

    public List<Producto> getAllProduct();

    public void deletProduct(int id);

}
