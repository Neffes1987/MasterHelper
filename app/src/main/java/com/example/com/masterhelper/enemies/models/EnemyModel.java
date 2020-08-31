package com.example.com.masterhelper.enemies.models;

import com.example.com.masterhelper.core.models.DataModel;

public class EnemyModel extends DataModel {


  /** */
  private int totalHealth;

  /** */
  private int currentHealth;

  /** */
  private int ordering;

  /** */
  public EnemyModel(int id, String name, String description, int totalHealth, int currentHealth, int ordering){
    initGeneralFields(id, name, description);
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

  public int getTotalHealth() {
    return totalHealth;
  }

  public void setTotalHealth(int totalHealth) {
    this.totalHealth = totalHealth;
  }


  public int getCurrentHealth() {
    return currentHealth;
  }

  public void setCurrentHealth(int currentHealth) {
    this.currentHealth = currentHealth;
  }
}
