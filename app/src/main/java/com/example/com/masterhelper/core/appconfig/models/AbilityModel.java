package com.example.com.masterhelper.core.appconfig.models;

public class AbilityModel {
  /** */
  private String name="";

  /** */
  private int value=0;

  /** */
  private int id=0;

  /** */
  private ACHIEVE_CONST_TAGS tag;

  /** */
  public AbilityModel(int id, String name, int value){
    setValue(value);
    setName(name);
    setId(id);
  }

  /** */
  public AbilityModel(String name){
    setValue(0);
    setName(name);
    setId(0);
  }

  /** */
  public AbilityModel(int id, String name, int value, ACHIEVE_CONST_TAGS tag){
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

  public enum ACHIEVE_CONST_TAGS {
    HEALTH,
    ORDERING
  }
}
