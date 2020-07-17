package com.example.com.masterhelper.core.force.models;

import com.example.com.masterhelper.core.models.DataModel;

public abstract class RelationModal extends DataModel implements IRelation {
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

  public ResultType getResult() {
    return result;
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

  public enum RelationType {
    force,
    goal,
    advance,
    dependency
  }

  public enum ResultType {
    solved,
    failed,
    inProgress
  }

  public enum DirectionType {
    enemy,
    cooperators,
    depended,
    owned,
    positive,
    negative,
    advantage,
    disadvantage
  }
}
