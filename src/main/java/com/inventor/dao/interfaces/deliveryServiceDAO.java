package com.inventor.dao.interfaces;

import com.inventor.entities.DeleveryService;

public interface deliveryServiceDAO extends commonDAO<DeleveryService> {

    DeleveryService getByName(String name);
}
