package com.inventor.dao.impls;

import com.inventor.dao.interfaces.clientDAO;
import com.inventor.entities.ClientEntity;
import com.inventor.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class clientDAOImpls extends abstractUA<ClientEntity> implements clientDAO {

    private static clientDAOImpls cDAOImpls;
    private SessionFactory sessionFactory = null;

    public clientDAOImpls() {
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

    public static clientDAOImpls getInstance() {
        if (cDAOImpls == null) {
            cDAOImpls = new clientDAOImpls();
        }
        return cDAOImpls;
    }

    @Override
    public List<ClientEntity> getAll() {
        isActiveSession();
        List<ClientEntity> ls = new ArrayList<>(getSession().createCriteria(ClientEntity.class).list());
        getSession().getTransaction().commit();
        return ls;
    }

    @Override
    public ClientEntity get(long id) {
        isActiveSession();
        ClientEntity obj = getSession().load(ClientEntity.class, (int)id);
        getSession().getTransaction().commit();
        return obj;
    }

    @Override
    public boolean remove(long obj) {
        isActiveSession();
        ClientEntity var = getSession().load(ClientEntity.class, (int) obj);
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
                .createCriteria(ClientEntity.class)
                .setProjection(Projections
                        .property("phoneNumber"))
                .list());
        getSession().getTransaction().commit();
        return list;
    }

    @Override
    public int getId(String name) {
        isActiveSession();
        int id = (int) getSession().createCriteria(ClientEntity.class)
                .add(Restrictions.eq("name", name)).uniqueResult();
        getSession().getTransaction().commit();

        return id;
    }

    @Override
    public ClientEntity getByPhoneNum(String num) {
        isActiveSession();
        try {
            ClientEntity obj = (ClientEntity) getSession().createCriteria(ClientEntity.class)
                    .add(Restrictions.eq("phoneNumber", num)).uniqueResult();
            getSession().getTransaction().commit();
            return obj;
        } catch (Exception e) {
            return new ClientEntity();
        }
    }
}
