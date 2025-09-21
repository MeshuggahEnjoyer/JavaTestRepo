package module2;

import org.hibernate.*;

public class UserDaoHibernate implements UserDAO {
    private final SessionFactory sessionFactory;

    public UserDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(User user) {
        Transaction t = null;
        try (Session session = sessionFactory.openSession()) {
            t = session.beginTransaction();
            session.persist(user);
            t.commit();
        }catch (JDBCException e) {
            if (t != null) {
                t.rollback();
            }
            System.err.println("JDBCException: " + e.getMessage());
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            System.err.println("HibernateException: " + e.getMessage());
        }
    }

    @Override
    public User getById(long id) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            user = session.find(User.class, id);
        }catch (JDBCException e) {
            System.err.println("JDBCException: " + e.getMessage());
        } catch (HibernateException e) {
            System.err.println("HibernateException: " + e.getMessage());
        }
        return user;
    }

    @Override
    public void update(User user) {
        Transaction t = null;
        try (Session session = sessionFactory.openSession()) {
            t = session.beginTransaction();
            session.merge(user);
            t.commit();
        } catch (JDBCException e) {
            if (t != null) {
                t.rollback();
            }
            System.err.println("JDBCException: " + e.getMessage());
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            System.err.println("HibernateException: " + e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        Transaction t = null;
        try (Session session = sessionFactory.openSession()) {
            t = session.beginTransaction();
            session.remove(getById(id));
            t.commit();
        } catch (JDBCException e) {
            if (t != null) {
                t.rollback();
            }
            System.err.println("JDBCException: " + e.getMessage());
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            System.err.println("HibernateException: " + e.getMessage());
        }
    }
}
