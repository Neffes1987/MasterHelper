package com.example.com.masterhelper.core.models;

/**
 * Модель данных для адаптера поключения аккордиона в цеклический список сцены
 * @autor Neffes
 * */
public class SceneModel extends DataModel {

  /** количество скриптов внутри сцены*/
  private int scriptsTotal;

  /** количество пройденых скриптов данной сцены */
  private int scriptsFinished;

  /** флаг, что таб открыт */
  public boolean isExpand;

  /**@constructor
   * генератор записи для адаптора
   * */
  public SceneModel(String name){
    initGeneralFields(0, name, "");
    setExpand(false);
    setScriptsFinished(0);
    setScriptsTotal(0);
  }

  public SceneModel(String name, int id, String description, int scriptsFinished, int scriptsTotal, boolean isExpand ){
    initGeneralFields(id, name, description);

    setExpand(isExpand);
    setScriptsFinished(scriptsFinished);
    setScriptsTotal(scriptsTotal);
  }


  public void setExpand(boolean expand) {
    isExpand = expand;
  }

  public void setScriptsFinished(int scriptsFinished) {
    this.scriptsFinished = scriptsFinished;
  }

  public void setScriptsTotal(int scriptsTotal) {
    this.scriptsTotal = scriptsTotal;
  }

  public int getScriptsFinished() {
    return scriptsFinished;
  }

  public int getScriptsTotal() {
    return scriptsTotal;
  }

  public boolean checkSceneIsFinished(){
    return scriptsTotal <= scriptsFinished && scriptsTotal != 0;
  }

  public String getSceneProgress() {
    return scriptsFinished +"/"+ scriptsTotal;
  }

}
