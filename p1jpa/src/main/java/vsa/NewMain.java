package vsa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

public class NewMain {

  public static void main(String[] args) {
      System.out.println("Built run successfully");
      citajKnihy();
//      zmenaCien();
//      vytvorKnihu();
//      najdiKnihu();
  
  }

    /* Ilustruje 
        EntityManagerFactory 
        EntityManager 
        createNativeQuery
     */
    public static void citajKnihy() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("vsaPU");
        EntityManager em = emf.createEntityManager();

        Query q = em.createNativeQuery("select * from kniha", Kniha.class);
        List<Kniha> knihy = q.getResultList();

        for (Kniha k : knihy) {
            System.out.println("" + k);
        }
    }

    /* Zlava na vsetky knihy - ilustruje:
        modifikaciu nacitaneho (manazovaneho) objektu kniha 
        otvorenie a commit transakcie
    
    Skuste najprv bez pouzitia transakcie    
     */
    public static void zmenaCien() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("vsaPU");
        EntityManager em = emf.createEntityManager();

        Query q = em.createNativeQuery("select * from kniha", Kniha.class);
        List<Kniha> knihy = q.getResultList();

        em.getTransaction().begin();
        for (Kniha k : knihy) {
            k.setCena(k.getCena() * 0.8);
        }
        em.getTransaction().commit();

        for (Kniha k : knihy) {
            System.out.println("" + k);
        }
    }

    /* Vyhladanie knihy podla kluca - ilustruje: 
        find
    Skuste tiez 
        vyhladat neexiatujucu knihu
     */
    public static void najdiKnihu() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("vsaPU");
        EntityManager em = emf.createEntityManager();

        Kniha k = em.find(Kniha.class, "Pipi");
        System.out.println("" + k);
    }
    
    /* 
        persist
     */
    public static void vytvorKnihu() {
        Kniha k = new Kniha();
        k.setNazov("Pipi");
        k.setCena(9.5);
        k.setAutor("niekto");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("vsaPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(k);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }    
}
