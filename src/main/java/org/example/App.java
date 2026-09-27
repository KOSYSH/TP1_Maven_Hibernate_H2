package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.h2.tools.Server;

import org.example.model.Produit;

import java.math.BigDecimal;
import java.util.List;

/**
 * TP1_Projet_Maven_hiber_h2
 */
public class App {
    public static void main(String[] args) {
        try {
            Server.createWebServer("-web", "-webPort", "8082").start();

            System.out.println(
                    "Console H2 disponible sur : http://localhost:8082"
            );

        } catch (Exception e) {
            System.out.println("Erreur lors du démarrage de la console H2");
            e.printStackTrace();
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-demo");

                /*
                System.out.println("Generated ID is : " + p1.getId());
                        System.out.println("Generated ID is : " + p2.getId());
                        System.out.println("Generated ID is : " + p3.getId());
                        */
        try {
            insererProduits(emf);
            lireProduits(emf);

        } finally {
            emf.close();

        }
    }


    private static void insererProduits(EntityManagerFactory emf) {


        EntityManager em = emf.createEntityManager();

        Produit p1 = new Produit("Laptop", new BigDecimal("999.99"));
        Produit p2 = new Produit("Smartphone", new BigDecimal("499.99"));
        Produit p3 = new Produit("Tablette", new BigDecimal("299.99"));

        try {
            em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    private static void lireProduits(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();

        try {
            List<Produit> produits = em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
            for (Produit produit : produits) {

                System.out.println(produit);

            }
            Produit produitRecherche = em.find(Produit.class, 2L);
            if (produitRecherche != null) {
                System.out.println(produitRecherche);
            } else {
                System.out.println("Produitnon trouve");
            }
                        /*  Produit produitLu1 = emL.find(Produit.class, p1.getId());
                        Produit produitLu2 = em.find(Produit.class, p2.getId());
                        Produit produitLu3 = em.find(Produit.class, p3.getId());
                        System.out.println("Produit retreieved : " + produitLu1);
                        System.out.println("Produit retreieved : " + produitLu2);
                        System.out.println("Produit retreieved : " + produitLu3);*/

        } finally {
            em.close();

        }
    }
}
