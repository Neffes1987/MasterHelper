package com.example.com.masterhelper.core.models.forces;

public interface IRelation {
  RelationModal.RelationType getType();
  RelationModal.DirectionType getDirection();
  void setDirection(RelationModal.DirectionType directionType);
}
