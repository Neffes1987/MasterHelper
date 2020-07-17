package com.example.com.masterhelper.core.force.models;

import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;

public class ForceModel extends DataModel {
  ModelList relations; // some RelationModal
  ForceType type;
  String icon;
  ModelList involvedJourneys; // JourneyModels

  public void setRelations(ModelList relations) {
    this.relations = relations;
  }

  public ModelList getRelations() {
    return relations;
  }


  public void setIcon(String icon) {
    this.icon = icon;
  }

  public void setInvolvedJourneys(ModelList involvedJourneys) {
    this.involvedJourneys = involvedJourneys;
  }

  public void setType(ForceType type) {
    this.type = type;
  }

  public ForceType getType() {
    return type;
  }

  public String getTypeString() {
    return type == ForceType.group ? "Организация" : "Одиночка";
  }



  private ModelList getListByType(RelationModal.RelationType type, RelationModal.DirectionType directionType){
    ModelList result = new ModelList();
    for (DataModel model: relations.values()) {
      RelationModal relationModal =  (RelationModal) model;
      if(relationModal.getType() == type && relationModal.getDirection() == directionType){
        result.addToList(model);
      }
    }
    return result;
  }

  public ModelList getOwners() {
    return getListByType(RelationModal.RelationType.dependency, RelationModal.DirectionType.depended);
  }

  public ModelList getDepended() {
    return getListByType(RelationModal.RelationType.dependency, RelationModal.DirectionType.owned);
  }

  public ModelList getGoals() {
    return getListByType(RelationModal.RelationType.goal, null);
  }

  public ModelList getAdvantagesRelations() {
    return getListByType(RelationModal.RelationType.advance, RelationModal.DirectionType.advantage);
  }

  public ModelList getDisadvantagesRelations() {
    return getListByType(RelationModal.RelationType.advance, RelationModal.DirectionType.disadvantage);
  }

  public ModelList getInvolvedJourneys() {
    return involvedJourneys;
  }

  public ModelList getEnemies() {
    return getListByType(RelationModal.RelationType.force, RelationModal.DirectionType.enemy);
  }

  public ModelList getCooperators() {
    return getListByType(RelationModal.RelationType.force, RelationModal.DirectionType.cooperators);
  }

  public String getIcon() {
    return icon;
  }

  public enum ForceType{
    person,
    group
  }
}
