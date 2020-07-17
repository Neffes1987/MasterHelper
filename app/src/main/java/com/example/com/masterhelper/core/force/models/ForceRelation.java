package com.example.com.masterhelper.core.force.models;


public class ForceRelation extends RelationModal {
  private DirectionType direction;

  ForceRelation(int id, String name, String cause, DirectionType direction) {
    super(id, name, cause);
    setDirection(direction);
  }

  public DirectionType getDirection() {
    return direction;
  }

  @Override
  public String getDirectionString() {
    return null;
  }

  @Override
  public void setDirection(DirectionType directionType) {
    this.direction = directionType;
  }

  @Override
  public String modelToString() {
    String result = super.modelToString();
    if(result.length() > 0){
      return result;
    }

    String stringType = direction == DirectionType.enemy ? "[Враг] " : "[Союзник] ";

    return stringType + getName();
  }

  @Override
  public RelationModal.RelationType getType() {
    return RelationModal.RelationType.force;
  }
}
