package com.inventor.dao.impls;

import com.inventor.dao.interfaces.footsDAO;
import com.inventor.entities.FoodsEntity;
import com.inventor.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class footsDAOImpls extends abstractUA<FoodsEntity> implements footsDAO {

    private static footsDAOImpls cDAOImpls;
    private SessionFactory sessionFactory = null;

    public footsDAOImpls() {
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

    public static footsDAOImpls getInstance() {
        if (cDAOImpls == null) {
            cDAOImpls = new footsDAOImpls();
        }
        return cDAOImpls;
    }

    @Override
    public List<FoodsEntity> getAll() {
        isActiveSession();
        List<FoodsEntity> list = new ArrayList<>(getSession().createCriteria(FoodsEntity.class).list());
        getSession().getTransaction().commit();
        return list;
    }

    @Override
    public FoodsEntity get(long id) {
        isActiveSession();
        FoodsEntity obj = getSession().get(FoodsEntity.class, (int) id);
        getSession().getTransaction().commit();
        return obj;
    }

    @Override
    public boolean remove(long obj) {
        isActiveSession();
        FoodsEntity var = getSession().load(FoodsEntity.class, (int) obj);
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
                .createCriteria(FoodsEntity.class)
                .setProjection(Projections
                        .property("name"))
                .list());
        getSession().getTransaction().commit();
        return list;
    }

    @Override
    public int getId(String name) {
        isActiveSession();
        int id = (int) getSession().createCriteria(FoodsEntity.class)
                .add(Restrictions.eq("name", name)).uniqueResult();
        getSession().getTransaction().commit();

        return id;
    }

    @Override
    public List<FoodsEntity> getByFamily(int id) {
        isActiveSession();
        List<FoodsEntity> ls = new ArrayList<>(getSession()
                .createCriteria(FoodsEntity.class)
                .add(Restrictions
                        .eq("familyId", id)).list());
        getSession().getTransaction().commit();
        return ls;
    }
}
