package com.inventor.dao.interfaces;

import java.util.List;

public interface commonDAO<T> extends abstractInterface<T> {

    List<T> getAll();

    T get(long id);

    boolean remove(long obj);

    List<String> getNames();

    int getId(String name);
}
