package com.example.masterhelper.ui.enemy.model;

public class EnemyModel {
  /** */
  private String name;

  /** */
  private int totalHealth;

  /** */
  private int currentHealth;

  /** */
  private int damage;

  /** */
  private long id;

  /** */
  public EnemyModel(String name, int totalHealth, int currentHealth, int damage, long id){
    this.damage = damage;
    this.totalHealth = totalHealth;
    this.currentHealth = currentHealth;
    this.name = name;
    this.id = id;
  }

  public int getCurrentHealth() {
    return currentHealth;
  }

  public void setCurrentHealth(int currentHealth) {
    this.currentHealth = currentHealth;
  }

  public int getDamage() {
    return damage;
  }

  public int getTotalHealth() {
    return totalHealth;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
