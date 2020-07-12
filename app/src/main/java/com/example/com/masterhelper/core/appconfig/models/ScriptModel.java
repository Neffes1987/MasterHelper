package com.example.com.masterhelper.core.appconfig.models;

/**
 * Модель данных для адаптера поключения аккордиона в цеклический список сцены
 * @autor Neffes
 * */
public class ScriptModel extends DataModel {

  /** в скрипте боевая сцена */
  public boolean hasBattleActionIcon;

  /** скрипт уже выполнен */
  public boolean isFinished;

  /**@constructor
   * генератор записи для адаптора
   * */
  public ScriptModel(String name, int id, String description, boolean hasBattleActionIcon, boolean isFinished ){
    initGeneralFields(id, name, description);
    setFinished(isFinished);
    setHasBattleActionIcon(hasBattleActionIcon);
  }

  public void setFinished(boolean finished) {
    isFinished = finished;
  }

  public void setHasBattleActionIcon(boolean hasBattleActionIcon) {
    this.hasBattleActionIcon = hasBattleActionIcon;
  }
}
