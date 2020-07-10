package com.masterhelper.appconfig.models;

/**
 * Модель данных для адаптера поключения аккордиона в цеклический список сцены
 * @autor Neffes
 * */
public class ScriptModel {

  /** название сцены выводится в строке списка(видна всегда)*/
  public String title;

  /** ид скрипта */
  public int id;

  /** описание сцены, тескт внутри аккордиона, выводится если таб открыт*/
  public String description;

  /** в скрипте боевая сцена */
  public boolean hasBattleActionIcon;

  /** скрипт уже выполнен */
  public boolean isFinished;

  /**@constructor
   * генератор записи для адаптора
   * */
  public ScriptModel(String title, int id, String description, boolean hasBattleActionIcon, boolean isFinished ){
    setTitle(title);
    setDescription(description);
    setFinished(isFinished);
    setHasBattleActionIcon(hasBattleActionIcon);
    setId(id);
  }

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public int getId() {
    return id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setFinished(boolean finished) {
    isFinished = finished;
  }

  public void setHasBattleActionIcon(boolean hasBattleActionIcon) {
    this.hasBattleActionIcon = hasBattleActionIcon;
  }
}
