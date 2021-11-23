package com.inventor.dao.impls;

import com.inventor.dao.interfaces.footFamilyDAO;
import com.inventor.entities.FoodFamilyEntity;
import com.inventor.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class footsFamilyDAOImpls extends abstractUA<FoodFamilyEntity> implements footFamilyDAO {

    private static footsFamilyDAOImpls cDAOImpls;
    private SessionFactory sessionFactory = null;

    public footsFamilyDAOImpls() {
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

    public static footsFamilyDAOImpls getInstance() {
        if (cDAOImpls == null) {
            cDAOImpls = new footsFamilyDAOImpls();
        }
        return cDAOImpls;
    }

    @Override
    public List<FoodFamilyEntity> getAll() {
        isActiveSession();
        List<FoodFamilyEntity> list = new ArrayList<>(getSession().createCriteria(FoodFamilyEntity.class).list());
        list.removeIf(e -> e.getId() == 9);
        getSession().getTransaction().commit();
        return list;
    }

    @Override
    public FoodFamilyEntity get(long id) {
        isActiveSession();
        FoodFamilyEntity obj = getSession().get(FoodFamilyEntity.class, (int) id);
        getSession().getTransaction().commit();
        return obj;
    }

    @Override
    public boolean remove(long obj) {
        isActiveSession();
        FoodFamilyEntity var = getSession().load(FoodFamilyEntity.class, (int) obj);
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
                .createCriteria(FoodFamilyEntity.class)
                .setProjection(Projections
                        .property("name"))
                .list());
        getSession().getTransaction().commit();
        return list;
    }

    @Override
    public int getId(String name) {
        isActiveSession();
        int id = (int) getSession().createCriteria(FoodFamilyEntity.class)
                .add(Restrictions.eq("name", name)).uniqueResult();
        getSession().getTransaction().commit();

        return id;
    }
}
