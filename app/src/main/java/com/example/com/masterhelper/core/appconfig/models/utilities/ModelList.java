package com.example.com.masterhelper.core.appconfig.models.utilities;

import com.example.com.masterhelper.core.appconfig.models.DataModel;

import java.util.Collection;
import java.util.LinkedHashMap;

public class ModelList {
  private LinkedHashMap<Integer, DataModel> list = new LinkedHashMap<>();

  public void clear(){
    list.clear();
  }

  public void addToList(DataModel item){
    list.put(item.getId(), item);
  }

  public DataModel get(int id){
    return list.get(id);
  }

  public int size(){
    return list.size();
  }

  public DataModel getItemByPosition(int position){
    return (DataModel) list.values().toArray()[position];
  }

  public void remove(int id){
    list.remove(id);
  }

  public Collection<DataModel> values(){
    return list.values();
  }

  public Collection<Integer> keySet(){
    return list.keySet();
  }
}
