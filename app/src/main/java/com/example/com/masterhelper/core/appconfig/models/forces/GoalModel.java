package com.example.com.masterhelper.core.appconfig.models.forces;

public class GoalModel extends RelationModal {
  private DirectionType directionType;

  GoalModel(int id, String name, String cause) {
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
  public void setDirection(DirectionType directionType) {
    this.directionType = directionType;
  }
}
