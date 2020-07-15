package com.example.com.masterhelper.core.models;

public class JourneyModel extends DataModel {

  public JourneyModel(String name){
    initGeneralFields(0, name, null);
  }

  public JourneyModel(String name, int id){
    initGeneralFields(id, name, null);
  }
}
