package com.example.com.masterhelper.enemies.models;

import com.example.com.masterhelper.core.models.DataModel;

public class AbilityModel extends DataModel {

  /** */
  private int value=0;

  /** */
  private ACHIEVE_CONST_TAGS tag;

  /** */
  public AbilityModel(int id, String name, int value){
    setValue(value);
    initGeneralFields(id, name, null);
  }

  /** */
  public AbilityModel(String name){
    setValue(0);
    initGeneralFields(0, name, null);
  }

  /** */
  public AbilityModel(int id, String name, int value, ACHIEVE_CONST_TAGS tag){
    setValue(value);
    setTag(tag);
    initGeneralFields(id, name, null);
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


  public void setValue(int value) {
    this.value = value;
  }

  public enum ACHIEVE_CONST_TAGS {
    HEALTH,
    ORDERING
  }
}
