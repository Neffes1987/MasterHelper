package com.example.masterhelper.RecyclerViewFragment.models.script;

/**
 * Модель данных для адаптера поключения аккордиона в цеклический список сцены
 * @autor Neffes
 * */
public class ScriptRecycleDataModel {

  /** название сцены выводится в строке списка(видна всегда)*/
  public String title;

  /** описание сцены, тескт внутри аккордиона, выводится если таб открыт*/
  public String description;

  /** в скрипте боевая сцена */
  public boolean hasBattleActionIcon;

  /** скрипт уже выполнен */
  public boolean isFinished;

  /** флаг, что таб открыт */
  public boolean isExpand;

  /** флаг, что музыкальное сопровохжение включено */
  public boolean isMusicStarted;

  /**@constructor
   * генератор записи для адаптора
   * */
  public ScriptRecycleDataModel(String title, String description, boolean hasBattleActionIcon, boolean isFinished, boolean isExpand, boolean isMusicStarted ){
    this.title = title;
    this.description = description;
    this.hasBattleActionIcon = hasBattleActionIcon;
    this.isExpand = isExpand;
    this.isMusicStarted = isMusicStarted;
    this.isFinished = isFinished;
  }
}
