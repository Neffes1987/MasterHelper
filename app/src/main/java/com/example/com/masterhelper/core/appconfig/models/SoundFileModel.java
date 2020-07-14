package com.example.com.masterhelper.core.appconfig.models;

public class SoundFileModel extends DataModel {
  private long createTime;
  private String path;
  boolean selected;

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public boolean getSelected() {
    return selected;
  }

  public long getCreateTime() {
    return createTime;
  }


  public String getPath() {
    return path;
  }

  public SoundFileModel(String name, long createTime, String path, int id, boolean isSelected){
    initGeneralFields(id, name, null);
    setCreateTime(createTime);
    setPath(path);
    setSelected(isSelected);
  }

  public SoundFileModel(String path, int id){
    initGeneralFields(id, null, null);
    setCreateTime(0);
    setPath(path);
    setSelected(false);
  }

  private void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  private void setPath(String path) {
    this.path = path;
  }
}
