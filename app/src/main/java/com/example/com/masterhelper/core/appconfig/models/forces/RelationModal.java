package com.example.com.masterhelper.core.appconfig.models.forces;

import com.example.com.masterhelper.core.appconfig.models.DataModel;

public class RelationModal extends DataModel {
  private ResultType result = ResultType.inProgress;
  private String resolveResult="";
  private String rejectResult="";

  RelationModal(int id, String name, String cause){
    initGeneralFields(id, name, cause);
  }

  public void setRejectResult(String rejectResult) {
    this.rejectResult = rejectResult;
  }

  public void setResolveResult(String resolveResult) {
    this.resolveResult = resolveResult;
  }

  public void setResult(ResultType result) {
    this.result = result;
  }

  public String getRejectResult() {
    return rejectResult;
  }

  public String getResolveResult() {
    return resolveResult;
  }

  @Override
  public String modelToString() {
    switch (result){
      case failed: return getRejectResult();
      case solved: return getResolveResult();
      default: return "";
    }
  }



  public enum ResultType {
    solved,
    failed,
    inProgress
  }
}
