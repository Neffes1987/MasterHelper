package com.example.com.masterhelper.core.factories.DBAdapters.adapters;

import com.example.com.masterhelper.core.models.utilities.ModelList;

public interface IAdapterDB<Model> {
  Model get(int id);
  void add(Model newItem, int parentId);
  void delete(int deletedId);
  void update(Model updatedModel);
  ModelList getListByParentId(int parentId);
}
