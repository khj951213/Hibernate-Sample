import jakarta.persistence.*;
import java.util.List;

public class TestHibernate {
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("customer");

    public static void main(String[] args) {
        addCustomer("SpringSpring", "Boot");
        getCustomer(1);
        ENTITY_MANAGER_FACTORY.close();
    }

    public static void addCustomer(String firstName, String lastName) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            em.persist(customer);
            et.commit();
        } catch (Exception ex) {
            if(et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void getCustomer(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT c FROM customer c WHERE c.id  = :custId";
        TypedQuery<Customer> tq = em.createQuery(query, Customer.class);
        tq.setParameter("custId", id);
        Customer customer = null;
        try {
            customer = tq.getSingleResult();
            System.out.println(customer.getFirstName() + " " + customer.getLastName());
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void getCustomers() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String strQuery = "SELECT c FROM customer c";
        TypedQuery<Customer> tq = em.createQuery(strQuery, Customer.class);
        List<Customer> customers;
        try {
            customers = tq.getResultList();
            customers.forEach(customer -> System.out.println(customer.getFirstName() + " " + customer.getLastName()));
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void changeFirstName(int id, String firstName) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Customer customer = null;
        try {
            et = em.getTransaction();
            et.begin();
            customer = em.find(Customer.class, id);
            customer.setFirstName(firstName);
            em.persist(customer);
            et.commit();
        } catch (Exception ex) {
            if(et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void deleteCustomer(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Customer customer = null;
        try {
            et = em.getTransaction();
            et.begin();

            customer = em.find(Customer.class, id);
            em.remove(customer);

            em.persist(customer);
            et.commit();
        } catch (Exception ex) {
            if(et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}
