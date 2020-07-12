package com.example.com.masterhelper.core.factorys.list;

import com.example.com.masterhelper.core.appconfig.models.utilities.ModelList;

public interface IListFactory {
  /** инициализировать фрагмент элементами списка */
  void updateListAdapter(ModelList data, CustomListItemsEnum itemType);
}
