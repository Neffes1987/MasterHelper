package com.example.masterhelper.ui.RecyclerViewFragment;

/** Интерфейс для оповещения активности, что произошло событие внутри таба аккордтона*/
public interface IRecycleAdapter {
  /** Коллбек на событие изменения данных внутри аккордиона
   * @param position - порядковый номер аккордиона, где произошло событие
   * @param fieldName - имя поля, которое подверглось изменению
   * @param newValue - новое значение для измененного поля  */
  void onChangeItem(int position, RecyclerAccordionEvents fieldName, String newValue);
}
