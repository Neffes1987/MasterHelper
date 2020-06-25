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
  private int ordering;

  /** */
  private int id;


  /** */
  public EnemyModel(int id, String name, String description, int totalHealth, int currentHealth, int ordering){
    setName(name);
    setId(id);
    setDescription(description);
    setTotalHealth(totalHealth);
    setCurrentHealth(currentHealth);
    setOrdering(ordering);
  }

  public void setOrdering(int ordering) {
    this.ordering = ordering;
  }

  public int getOrdering() {
    return ordering;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(int id) {
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

  public int getId() {
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
