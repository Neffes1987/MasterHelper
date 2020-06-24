package com.example.masterhelper.models;

public class EnemyModel {

  /** */
  private String name;

  /** */
  private String description;

  /** */
  private int totalHealth;

  /** */
  private int currentHealth;

  /** */
  private long id;


  /** */
  public EnemyModel(long id, String name, String description, int totalHealth, int currentHealth){
    setName(name);
    setId(id);
    setDescription(description);
    setTotalHealth(totalHealth);
    setCurrentHealth(currentHealth);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(long id) {
    this.id = id;
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


  public int getCurrentHealth() {
    return currentHealth;
  }

  public void setCurrentHealth(int currentHealth) {
    this.currentHealth = currentHealth;
  }
}
