package com.example.com.masterhelper.core.appconfig.models.forces;

public class AdvanceModel extends RelationModal {
  private AdvanceType type;

  AdvanceModel(int id, String name, String cause, AdvanceType type) {
    super(id, name, cause);
    setType(type);
  }

  public void setType(AdvanceType type) {
    this.type = type;
  }

  public AdvanceType getType() {
    return type;
  }

  @Override
  public String modelToString() {
    return getName();
  }

  enum AdvanceType {
    positive,
    negative
  }
}
