package com.example.com.masterhelper.core.appconfig.models.forces;

public class DependenciesModel extends RelationModal {
  private DependencyType type;

  DependenciesModel(int id, String name, String cause, DependencyType type) {
    super(id, name, cause);
    setType(type);
  }

  public void setType(DependencyType type) {
    this.type = type;
  }

  public DependencyType getType() {
    return type;
  }

  @Override
  public String modelToString() {
    return getName();
  }

  enum DependencyType {
    depended,
    owned
  }
}
