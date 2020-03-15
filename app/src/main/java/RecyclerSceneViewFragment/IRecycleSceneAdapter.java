package RecyclerSceneViewFragment;

import RecyclerSceneViewFragment.model.SceneAccordionEvents;

/** Интерфейс для оповещения активности, что произошло событие внутри таба аккордтона*/
public interface IRecycleSceneAdapter {
  /** Коллбек на событие изменения данных внутри аккордиона
   * @param position - порядковый номер аккордиона, где произошло событие
   * @param fieldName - имя поля, которое подверглось изменению
   * @param newValue - новое значение для измененного поля  */
  void onChangeItem(int position, SceneAccordionEvents fieldName, String newValue);
}
