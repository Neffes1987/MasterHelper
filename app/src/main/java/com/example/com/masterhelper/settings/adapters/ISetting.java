package com.example.com.masterhelper.settings.adapters;

import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;

public interface ISetting {
  void add(DataModel newModel);
  void create(String name, String description);
  void create(String name, String description, String[] selectedItems);
  void update(int id, String name, String description, String[] selectedItems);
  void delete(int deletedId);
  ModelList list();
}
