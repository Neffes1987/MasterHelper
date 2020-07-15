package com.example.com.masterhelper.core.models.utilities;

import com.example.com.masterhelper.core.models.DataModel;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class ModelList {
  protected LinkedHashMap<Integer, DataModel> list = new LinkedHashMap<>();

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

  public boolean containsKey(int id){
    return list.containsKey(id);
  }

  public Collection<DataModel> values(){
    return list.values();
  }

  public Collection<Integer> keySet(){
    return list.keySet();
  }

  public String idsToString(){
    return list.keySet().toString().replaceAll("\\[|\\]|\\s", "");
  };

  public String listToString() {
    StringBuilder result = new StringBuilder();
    for (DataModel item: list.values()) {
      result.append(item.modelToString());
      result.append("\r\n");
    }
    return result.toString();
  }

  public void setSelectedItems(String[] selectedIds){
    if(selectedIds == null){
      return;
    }
    for (DataModel model : list.values()) {
      model.setSelected(Arrays.asList(selectedIds).contains(model.getId()+""));
    }
  }

  public String[] getSelectedItems(){
    HashSet<String> result = new HashSet<>();
    for (DataModel model : list.values()) {
      if(model.isSelected){
        result.add(model.getId()+"");
      }
    }
    return result.toArray(new String[0]);
  }
}
