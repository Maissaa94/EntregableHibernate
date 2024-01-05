/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entregable.pojo;

import Entregable.dao.ProductoDAO;
import Entregable.model.Producto;
import Entregable.utils.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author hp
 */
public class ProductoPojo implements ProductoDAO{

    @Override
    public void addProduct(Producto pr) {
        Transaction tx = null;
        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(pr);//insert
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(e);
        }
    }

    @Override
    public void updateProduct(Producto pr) {
         Transaction tx = null;
        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            if (pr.getId() != 0) {
                session.merge(pr);
            }
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(e);
        }
    }

    @Override
    public Producto getProductByName(String name) {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        Query<Producto> query = session.createQuery("FROM Producto WHERE name = :value", Producto.class);
        query.setParameter("value", name);
        return query.uniqueResult(); // asumimos que el nombre es único, si no, usa getResultList y maneja la lista
    } catch (HibernateException e) {
        System.err.println(e);
        return null;
    }
}

    @Override
    public List<Producto> getAllProduct() {
        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Producto> query = session.createQuery("FROM Producto", Producto.class);
            return query.list();
        } catch (HibernateException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public void deletProduct(int id) {
          Transaction tx = null;
        try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Producto pr = session.get(Producto.class, id);//hazme un selegt de la clase estudent funciona por la primary key 
            if (pr != null) {
                session.remove(pr);
            }
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(e);
        }
    }
    
}
