package com.example.com.masterhelper.listFactory;

import com.example.com.masterhelper.core.models.utilities.ModelList;

public interface IListFactory {
  /** инициализировать фрагмент элементами списка */
  void updateListAdapter(ModelList data, CustomListItemsEnum itemType);
}
