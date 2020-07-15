package com.example.com.masterhelper.settings.adapters;

import com.example.com.masterhelper.core.appconfig.models.DataModel;
import com.example.com.masterhelper.core.appconfig.models.utilities.ModelList;

public interface ISetting {
  void add(DataModel newModel);
  void create(String name, String description);
  void delete(int deletedId);
  ModelList list();
}
