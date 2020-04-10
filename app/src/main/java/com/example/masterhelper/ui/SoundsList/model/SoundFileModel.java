package com.example.masterhelper.ui.SoundsList.model;

import java.util.Date;

public class SoundFileModel {
  String filename;
  Date createTime;
  String path;
  int fileId;
  boolean selected;

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public boolean getSelected() {
    return selected;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public int getFileId() {
    return fileId;
  }

  public String getFilename() {
    return filename;
  }

  public String getPath() {
    return path;
  }

  public SoundFileModel(String filename, Date createTime, String path, int fileId){
    setCreateTime(createTime);
    setFileId(fileId);
    setFilename(filename);
    setPath(path);
  }

  private void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  private void setFileId(int fileId) {
    this.fileId = fileId;
  }

  private void setFilename(String filename) {
    this.filename = filename;
  }

  private void setPath(String path) {
    this.path = path;
  }
}
