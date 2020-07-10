package com.example.masterhelper.ui.viewCharacteristicRow;

import com.masterhelper.appconfig.models.AbilityModel;

public class AbilityRowAdapter {
  /**  */
  String title = "";

  /**  */
  int value = 0;

  /**  */
  int rowId = 0;

  /**  */
  boolean editable = false;

  /** */
  public void updateRowData(AbilityModel abilityModel){
    setEditable(abilityModel.getTag() == null);
    setRowId(abilityModel.getId());
    setTitle(abilityModel.getName());
    setValue(abilityModel.getValue());
  }

  /** */
  public void increaseValue(int offset){
    int newValue = getValue() + offset;
    setValue(newValue);
  }

  /** */
  public void decreaseValue(int offset){
    int newValue = getValue() - offset;
    if(newValue < 0){
      newValue = 0;
    }
    setValue(newValue);
  }

  /** */
  public void setValue(int value) {
    this.value = value;
  }

  /** */
  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  /** */
  public void setRowId(int rowId) {
    this.rowId = rowId;
  }

  /** */
  public void setTitle(String title) {
    this.title = title;
  }

  /** */
  public int getValue() {
    return value;
  }

  /** */
  public int getRowId() {
    return rowId;
  }

  /** */
  public String getTitle() {
    return title;
  }
}
