package com.example.com.masterhelper.core.force.models;

import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.masterhelper.R;

public class AdvanceModel extends RelationModal {
  private DirectionType directionType;

  public AdvanceModel(int id, String name, String cause, DirectionType directionType) {
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
  public String getDirectionString() {
    if(directionType == DirectionType.advantage){
      return GlobalApplication.getAppContext().getString(R.string.advantage);
    }
    return GlobalApplication.getAppContext().getString(R.string.disadvantage);
  }

  @Override
  public void setDirection(DirectionType directionType) {
    this.directionType = directionType;
  }
}
