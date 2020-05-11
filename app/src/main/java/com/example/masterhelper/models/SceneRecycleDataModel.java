package com.example.masterhelper.models;

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

  /** уникальный ид закиси */
  public int id;

  /**@constructor
   * генератор записи для адаптора
   * */
  public SceneRecycleDataModel(String title){
    setTitle(title);
    setDescription("");
    setExpand(false);
    setMusicStarted(false);
    setId(0);
    setScriptsFinished(0);
    setScriptsTotal(0);
  }

  public SceneRecycleDataModel(String title, int id,  String description, int scriptsFinished, int scriptsTotal, boolean isExpand, boolean isMusicStarted ){
    setTitle(title);
    setDescription(description);
    setExpand(isExpand);
    setMusicStarted(isMusicStarted);
    setId(id);
    setScriptsFinished(scriptsFinished);
    setScriptsTotal(scriptsTotal);
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setExpand(boolean expand) {
    isExpand = expand;
  }

  public void setMusicStarted(boolean musicStarted) {
    isMusicStarted = musicStarted;
  }

  public void setScriptsFinished(int scriptsFinished) {
    this.scriptsFinished = scriptsFinished;
  }

  public void setScriptsTotal(int scriptsTotal) {
    this.scriptsTotal = scriptsTotal;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public int getScriptsFinished() {
    return scriptsFinished;
  }

  public int getScriptsTotal() {
    return scriptsTotal;
  }

  public String getDescription() {
    return description;
  }
}
