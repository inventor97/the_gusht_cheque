package com.inventor.dao.impls;

import com.inventor.dao.interfaces.deleveryDataDAO;
import com.inventor.entities.DeleveryDataEntity;
import com.inventor.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class deliveryDataDAOImpls extends abstractUA<DeleveryDataEntity> implements deleveryDataDAO {

    private static deliveryDataDAOImpls cDAOImpls;
    private SessionFactory sessionFactory = null;

    public deliveryDataDAOImpls() {
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

    public static deliveryDataDAOImpls getInstance() {
        if (cDAOImpls == null) {
            cDAOImpls = new deliveryDataDAOImpls();
        }
        return cDAOImpls;
    }

    @Override
    public List<DeleveryDataEntity> getAll() {
        isActiveSession();
        List<DeleveryDataEntity> list = new ArrayList<>(getSession().createCriteria(DeleveryDataEntity.class).list());
        list.removeIf(e -> e.getId() == 9);
        getSession().getTransaction().commit();
        return list;
    }

    @Override
    public DeleveryDataEntity get(long id) {
        isActiveSession();
        DeleveryDataEntity obj = getSession().get(DeleveryDataEntity.class, (int) id);
        getSession().getTransaction().commit();
        return obj;
    }

    @Override
    public boolean remove(long obj) {
        isActiveSession();
        DeleveryDataEntity var = getSession().load(DeleveryDataEntity.class, (int) obj);
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
                .createCriteria(DeleveryDataEntity.class)
                .setProjection(Projections
                        .property("dateCreated"))
                .list());
        getSession().getTransaction().commit();
        return list;
    }

    @Override
    public int getId(String name) {
//        isActiveSession();
//        int id = (int) getSession().createCriteria(DeleveryDataEntity.class)
//                .add(Restrictions.eq("c", name)).uniqueResult();
//        getSession().getTransaction().commit();

        return -1;
    }
}
