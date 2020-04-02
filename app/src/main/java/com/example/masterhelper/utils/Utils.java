package com.example.masterhelper.utils;

import java.util.HashSet;

public class Utils {
  public  static  String[] convertToArray(HashSet<String> data){
    if(data.size() == 0){
      return new String[]{};
    }
    return data.toString().replace("[", "").replace("]","").split(",");
  }
}
