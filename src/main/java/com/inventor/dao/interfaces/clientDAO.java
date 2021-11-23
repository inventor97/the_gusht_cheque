package com.inventor.dao.interfaces;

import com.inventor.entities.ClientEntity;

public interface clientDAO extends commonDAO<ClientEntity>{

    ClientEntity getByPhoneNum(String num);
}
