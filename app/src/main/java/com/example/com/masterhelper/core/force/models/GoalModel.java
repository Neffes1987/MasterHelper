package com.example.com.masterhelper.core.force.models;

import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.masterhelper.R;

public class GoalModel extends RelationModal {
  private DirectionType directionType;

  public GoalModel(int id, String name, String cause) {
    super(id, name, cause);
    setDirection(null);
  }

  @Override
  public String modelToString() {
    return getName();
  }

  @Override
  public RelationType getType() {
    return RelationType.goal;
  }

  @Override
  public DirectionType getDirection() {
    return directionType;
  }

  @Override
  public String getDirectionString() {
    return GlobalApplication.getAppContext().getString(R.string.goal);
  }

  @Override
  public void setDirection(DirectionType directionType) {
    this.directionType = directionType;
  }
}
