package com.example.com.masterhelper.core.dbAdaptersFactory.adapters;

import java.util.LinkedHashMap;

public interface IAdapterDB<Model> {
  Model get(int id);
  void add(Model newItem, int parentId);
  void delete(int deletedId);
  void update(Model updatedModel);
  LinkedHashMap<Integer, Model> getListByParentId(int parentId);
}
