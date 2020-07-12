package com.example.com.masterhelper.core.appconfig.models.forces;

import com.example.com.masterhelper.core.appconfig.models.DataModel;
import com.example.com.masterhelper.core.appconfig.models.utilities.ModelList;

public class ForceModel extends DataModel {
  ModelList goals; // RelationModels
  ModelList otherForcesRelations; // ForceRelations
  ModelList dependencies; // DependenciesModels
  ModelList advancesRelations; // AdvanceModels
  ForceType type;
  String icon;
  ModelList involvedJourneys; // JourneyModels

  public void setGoals(ModelList goals) {
    this.goals = goals;
  }

  public void setDependencies(ModelList dependencies) {
    this.dependencies = dependencies;
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

  enum ForceType{
    person,
    group
  }
}
