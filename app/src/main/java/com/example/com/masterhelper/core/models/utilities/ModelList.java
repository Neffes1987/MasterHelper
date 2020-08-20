package com.example.com.masterhelper.core.models.utilities;

import com.example.com.masterhelper.core.models.DataModel;

import java.util.*;

public class ModelList {
  protected LinkedList<DataModel> list = new LinkedList<>();

  public void clear(){
    list.clear();
  }

  public void update(DataModel item) {
    int position = getPositionById(item.getId());
    if(position != -1){
      list.set(position, item);
    }
  }

  public void addToList(DataModel item) {
    int position = getPositionById(item.getId());
    if(position == -1){
      list.add(item);
    }
  }

  public void addToList(DataModel item, boolean toFirst){
    if(!toFirst){
      addToList(item);
      return;
    }
    list.addFirst(item);
  }

  public DataModel get(int id){
    int position = getPositionById(id);
    return list.get(position);
  }

  public int size(){
    return list.size();
  }

  public DataModel getItemByPosition(int position){
    return list.get(position);
  }

  public int getPositionById(int id){
    int result = -1;
    boolean isFound = false;
    for (DataModel model: values()) {
      result +=1;
      if(model.getId() == id){
        isFound = true;
        break;
      }
    }
    return isFound ? result : -1;
  }

  public void remove(int id){
    int position = getPositionById(id);
    list.remove(position);
  }

  public boolean containsKey(int id){
    Collection<Integer> ids = keySet();
    return ids.contains(id);
  }

  public Collection<DataModel> values(){
    ArrayList<DataModel> modeles = new ArrayList<>();
    Collections.addAll(modeles, list.toArray(new DataModel[0]));
    return modeles;
  }

  public Collection<Integer> keySet(){
    ArrayList<Integer> idsList = new ArrayList<>();
    for (DataModel item: list.toArray(new DataModel[0])) {
      idsList.add(item.getId());
    }
    return idsList;
  }

  public String idsToString(){
    return keySet().toString().replaceAll("\\[|\\]|\\s", "");
  };

  public String listToString() {
    StringBuilder result = new StringBuilder();
    for (DataModel item: list.toArray(new DataModel[0])) {
      result.append(item.modelToString());
      result.append("\r\n");
    }
    return result.toString();
  }

  public void setSelectedItems(String[] selectedIds){
    if(selectedIds == null){
      return;
    }
    for (DataModel model : list.toArray(new DataModel[0])) {
      model.setSelected(Arrays.asList(selectedIds).contains(model.getId()+""));
    }
  }

  public String[] getSelectedItems(){
    HashSet<String> result = new HashSet<>();
    for (DataModel model : list.toArray(new DataModel[0])) {
      if(model.isSelected){
        result.add(model.getId()+"");
      }
    }
    return result.toArray(new String[0]);
  }
}
