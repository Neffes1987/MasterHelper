package com.masterhelper.appconfig.models;

public class JourneyModel {
  String title;
  int id;

  public void setTitle(String title) {
    this.title = title;
  }

  public void setId(int id) {
    this.id = id;
  }

  public JourneyModel(String title){
    setTitle(title);
    setId(0);
  }

  public JourneyModel(String title, int id){
    setTitle(title);
    setId(id);
  }

  public String getTitle() {
    return title;
  }

  public int getId() {
    return id;
  }
}
