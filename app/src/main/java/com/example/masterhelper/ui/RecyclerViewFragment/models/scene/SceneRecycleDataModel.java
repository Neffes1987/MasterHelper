package com.example.masterhelper.ui.RecyclerViewFragment.models.scene;

/**
 * Модель данных для адаптера поключения аккордиона в цеклический список сцены
 * @autor Neffes
 * */
public class SceneRecycleDataModel {

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
  public SceneRecycleDataModel(String title){
    this.title = title;
    this.description = "";
    this.scriptsFinished = 0;
    this.scriptsTotal = 0;
    this.isExpand = false;
    this.isMusicStarted = false;
  }

  public SceneRecycleDataModel(String title, String description, int scriptsFinished, int scriptsTotal, boolean isExpand, boolean isMusicStarted ){
    this.title = title;
    this.description = description;
    this.scriptsFinished = scriptsFinished;
    this.scriptsTotal = scriptsTotal;
    this.isExpand = isExpand;
    this.isMusicStarted = isMusicStarted;
  }
}
