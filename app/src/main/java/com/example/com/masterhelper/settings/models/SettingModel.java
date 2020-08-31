package com.example.com.masterhelper.settings.models;

import com.example.com.masterhelper.core.models.DataModel;

public class SettingModel extends DataModel {
  private String positiveResult = "";
  private String negativeResult = "";
  private String type = "";

  private void initMainFields(String Name, String description, String type){
    setType(type);
    setDescription(description);
    setName(Name);
  }

  private void initRelationFields(String positive, String negative){
    setPositiveResult(positive);
    setNegativeResult(negative);
  }

  public SettingModel(String name, String description, String type){
    initMainFields(name, description, type);
  }

  public SettingModel(int id, String name, String description, String type){
    setId(id);
    initMainFields(name, description, type);
  }

  public SettingModel(int id, String name, String description, String type, String positiveResult, String negativeResult){
    setId(id);
    initMainFields(name, description, type);
    initRelationFields(positiveResult, negativeResult);
  }

  public String getNegativeResult() {
    return negativeResult;
  }

  public String getPositiveResult() {
    return positiveResult;
  }

  public void setNegativeResult(String negativeResult) {
    this.negativeResult = negativeResult;
  }

  public void setPositiveResult(String positiveResult) {
    this.positiveResult = positiveResult;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
