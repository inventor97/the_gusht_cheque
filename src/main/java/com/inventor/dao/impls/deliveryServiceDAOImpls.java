package com.inventor.dao.impls;

import com.inventor.dao.interfaces.deliveryServiceDAO;
import com.inventor.entities.DeleveryService;
import com.inventor.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class deliveryServiceDAOImpls extends abstractUA<DeleveryService> implements deliveryServiceDAO {

    private static deliveryServiceDAOImpls cDAOImpls;
    private SessionFactory sessionFactory = null;

    public deliveryServiceDAOImpls() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    private void isActiveSession() {
        if (!getSession().getTransaction().isActive()) {
            getSession().beginTransaction();
        }
    }

    public static deliveryServiceDAOImpls getInstance() {
        if (cDAOImpls == null) {
            cDAOImpls = new deliveryServiceDAOImpls();
        }
        return cDAOImpls;
    }

    @Override
    public List<DeleveryService> getAll() {
        isActiveSession();
        List<DeleveryService> list = new ArrayList<>(getSession().createCriteria(DeleveryService.class).list());
        list.removeIf(e -> e.getId() == 9);
        getSession().getTransaction().commit();
        return list;
    }

    @Override
    public DeleveryService get(long id) {
        isActiveSession();
        DeleveryService obj = getSession().get(DeleveryService.class, (int) id);
        getSession().getTransaction().commit();
        return obj;
    }

    @Override
    public boolean remove(long obj) {
        isActiveSession();
        DeleveryService var = getSession().load(DeleveryService.class, (int) obj);
        if (var != null) {
            getSession().delete(var);
            return true;
        }
        getSession().getTransaction().commit();
        return false;
    }

    @Override
    public List<String> getNames() {
        isActiveSession();
        List<String> list = new ArrayList<>(getSession()
                .createCriteria(DeleveryService.class)
                .setProjection(Projections
                        .property("name"))
                .list());
        getSession().getTransaction().commit();
        return list;
    }

    @Override
    public int getId(String name) {
        isActiveSession();
        int id = (int) getSession().createCriteria(DeleveryService.class)
                .add(Restrictions.eq("name", name)).uniqueResult();
        getSession().getTransaction().commit();

        return id;
    }

    @Override
    public DeleveryService getByName(String name) {
        isActiveSession();
        try {
            DeleveryService obj = (DeleveryService) getSession().createCriteria(DeleveryService.class)
                    .add(Restrictions.eq("name", name)).uniqueResult();
            return obj;
        }
        catch (Exception e) {
            return new DeleveryService();
        }
    }
}
