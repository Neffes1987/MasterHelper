package com.masterhelper.listFactory;

import java.util.LinkedHashMap;


public interface IListFactory {
  /** инициализировать фрагмент элементами списка */
  void updateListAdapter(LinkedHashMap data, CustomListItemsEnum itemType);
}
