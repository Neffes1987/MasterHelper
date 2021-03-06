package com.example.com.masterhelper.force.models;

public class DependenciesModel extends RelationModal {
  private DirectionType direction;

  DependenciesModel(int id, String name, String cause, DirectionType direction) {
    super(id, name, cause);
    setDirection(direction);
  }


  @Override
  public RelationType getType() {
    return RelationType.dependency;
  }

  @Override
  public DirectionType getDirection() {
    return direction;
  }

  @Override
  public String getDirectionString() {
    return null;
  }

  @Override
  public void setDirection(DirectionType directionType) {
    direction = directionType;
  }

  @Override
  public String modelToString() {
    return getName();
  }

}
