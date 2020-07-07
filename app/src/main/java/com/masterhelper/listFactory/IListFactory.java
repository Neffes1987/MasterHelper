package com.masterhelper.listFactory;

import java.util.LinkedHashMap;


public interface IListFactory<Model> {
  /** инициализировать фрагмент элементами списка */
  void updateListAdapter(LinkedHashMap<Integer, Model> data);

  /** указать тип элемента сцены */
  void setItemType(CustomListItemsEnum itemType);
}
