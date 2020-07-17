package com.example.com.masterhelper.core.force.models;

public interface IRelation {
  RelationModal.RelationType getType();
  RelationModal.DirectionType getDirection();
  String getDirectionString();
  void setDirection(RelationModal.DirectionType directionType);
}
