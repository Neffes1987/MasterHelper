package RecyclerSceneViewFragment.model;

/**
 * Модель данных для адаптера поключения аккордиона в цеклический список сцены
 * @autor Neffes
 * */
public class RecycleSceneDataModel {

  /** название сцены выводится в строке списка(видна всегда)*/
  public String title;

  /** описание сцены, тескт внутри аккордиона, выводится если таб открыт*/
  public String description;

  /** количество скриптов внутри сцены*/
  public int scriptsTotal;

  /** количество пройденых скриптов данной сцены */
  public int scriptsFinished;

  /** флаг, что таб открыт */
  public boolean isExpand;

  /** флаг, что музыкальное сопровохжение включено */
  public boolean isMusicStarted;

  /**@constructor
   * генератор записи для адаптора
   * */
  public RecycleSceneDataModel(String title, String description, int scriptsFinished, int scriptsTotal, boolean isExpand, boolean isMusicStarted ){
    this.title = title;
    this.description = description;
    this.scriptsFinished = scriptsFinished;
    this.scriptsTotal = scriptsTotal;
    this.isExpand = isExpand;
    this.isMusicStarted = isMusicStarted;
  }
}