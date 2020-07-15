package com.example.com.masterhelper.core.models.forces;

public class AdvanceModel extends RelationModal {
  private DirectionType directionType;

  AdvanceModel(int id, String name, String cause,DirectionType directionType) {
    super(id, name, cause);
    setDirection(directionType);
  }


  @Override
  public String modelToString() {
    return getName();
  }

  @Override
  public RelationType getType() {
    return RelationType.advance;
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
