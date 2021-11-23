package com.inventor.dao.impls;

import com.inventor.dao.interfaces.cousineDAO;
import com.inventor.entities.CousineEntity;
import com.inventor.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class cousineDAOImpls extends abstractUA<CousineEntity> implements cousineDAO {

    private static cousineDAOImpls cDAOImpls;
    private SessionFactory sessionFactory = null;

    public cousineDAOImpls() {
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

    public static cousineDAOImpls getInstance() {
        if (cDAOImpls == null) {
            cDAOImpls = new cousineDAOImpls();
        }
        return cDAOImpls;
    }
    @Override
    public List<CousineEntity> getAll() {
        isActiveSession();
        List<CousineEntity> list = new ArrayList<>(getSession().createCriteria(CousineEntity.class).list());
        getSession().getTransaction().commit();
        return list;
    }

    @Override
    public CousineEntity get(long id) {
        isActiveSession();
        CousineEntity obj = getSession().get(CousineEntity.class, (int) id);
        getSession().getTransaction().commit();
        return obj;
    }

    @Override
    public boolean remove(long obj) {
        isActiveSession();
        CousineEntity var = getSession().load(CousineEntity.class, (int) obj);
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
                .createCriteria(CousineEntity.class)
                .setProjection(Projections
                        .property("name"))
                .list());
        getSession().getTransaction().commit();
        return list;
    }

    @Override
    public int getId(String name) {
        isActiveSession();
        int id = (int) getSession().createCriteria(CousineEntity.class)
                .add(Restrictions.eq("name", name)).uniqueResult();
        getSession().getTransaction().commit();

        return id;
    }
}
