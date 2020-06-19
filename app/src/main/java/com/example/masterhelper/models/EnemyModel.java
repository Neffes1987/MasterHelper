package com.example.masterhelper.models;

import java.util.LinkedHashMap;


public class EnemyModel {

  /** */
  private String name;

  /** */
  private String description;

  /** */
  private int totalHealth;

  /** */
  private long id;

  /** */
  private LinkedHashMap<Integer, AchieveModel> achieves;

  /** */
  public EnemyModel(long id, String name, String description,  LinkedHashMap<Integer, AchieveModel> achieves, int totalHealth){
    setName(name);
    setId(id);
    setAchieves(achieves);
    setDescription(description);
    setTotalHealth(totalHealth);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setAchieves(LinkedHashMap<Integer, AchieveModel> achieves) {
    this.achieves = achieves;
  }

  public int getTotalHealth() {
    return totalHealth;
  }

  public void setTotalHealth(int totalHealth) {
    this.totalHealth = totalHealth;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public LinkedHashMap<Integer, AchieveModel> getAchieves() {
    return achieves;
  }

  public AchieveModel getAchieveById(int key) {
    return achieves.get(key);
  }

  public int getCurrentHealth() {
    AchieveModel achieve = (AchieveModel) achieves.values().toArray()[0];
    if(achieve.getTag().equals(ACHIEVE_CONST_TAGS.HEALTH)){
      return achieve.getValue();
    }
    return 0;
  }
  public void setCurrentHealth(int value) {
    AchieveModel achieve = (AchieveModel) achieves.values().toArray()[0];
    if(!achieve.getTag().equals(ACHIEVE_CONST_TAGS.HEALTH)){
      achieve.setValue(value);
    }
  }
}
