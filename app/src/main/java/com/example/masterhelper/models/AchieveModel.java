package com.example.masterhelper.models;

public class AchieveModel {
  /** */
  private String name="";

  /** */
  private int value=0;

  /** */
  private int id=0;

  /** */
  private ACHIEVE_CONST_TAGS tag;

  /** */
  public AchieveModel(int id, String name, int value){
    setValue(value);
    setName(name);
    setId(id);
  }

  /** */
  public AchieveModel(int id, String name, int value, ACHIEVE_CONST_TAGS tag){
    setValue(value);
    setName(name);
    setId(id);
    setTag(tag);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getValue() {
    return value;
  }


  public void setTag(ACHIEVE_CONST_TAGS tag) {
    this.tag = tag;
  }

  public ACHIEVE_CONST_TAGS getTag() {
    return tag;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public void setName(String name) {
    this.name = name;
  }
}
