package com.example.com.masterhelper.core.appconfig.models.forces;


public class ForceRelation extends RelationModal {
  private RelationType type;

  ForceRelation(int id, String name, String cause, RelationType type) {
    super(id, name, cause);
    setType(type);
  }

  public void setType(RelationType type) {
    this.type = type;
  }

  public RelationType getType() {
    return type;
  }

  @Override
  public String modelToString() {
    String result = super.modelToString();
    if(result.length() > 0){
      return result;
    }

    String stringType = type == RelationType.enemy ? "[Враг] " : "[Союзник] ";

    return stringType + getName();
  }

  enum RelationType{
    enemy,
    cooperators
  }
}
