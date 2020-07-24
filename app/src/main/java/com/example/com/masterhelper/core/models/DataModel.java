package com.example.com.masterhelper.core.models;

public class DataModel {
  public boolean isSelected;

  /** */
  private String name;

  /** */
  private String description;

  /** */
  private int id;

  public void initGeneralFields(int id, String name, String description){
    setDescription(description);
    setId(id);
    setName(name);
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setName(String name) {
    this.name = name;
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

  public String modelToString(){
    return name;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }
}
