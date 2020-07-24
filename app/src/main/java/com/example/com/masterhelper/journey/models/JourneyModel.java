package com.example.com.masterhelper.journey.models;

import com.example.com.masterhelper.core.models.DataModel;

public class JourneyModel extends DataModel {

  public JourneyModel(String name){
    initGeneralFields(0, name, null);
  }

  public JourneyModel(String name, int id){
    initGeneralFields(id, name, null);
  }
}
