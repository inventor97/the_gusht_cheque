package com.inventor.dao.interfaces;

import com.inventor.entities.FoodsEntity;

import java.util.List;

public interface footsDAO extends commonDAO<FoodsEntity>{

    List<FoodsEntity> getByFamily(int id);
}
