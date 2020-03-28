package com.example.masterhelper;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**  */
public class view_characteristic_row extends Fragment {
  /**  */
  int fragmentViewCharacteristicRowLayout = R.layout.fragment_view_characteristic_row;

  public view_characteristic_row() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(fragmentViewCharacteristicRowLayout, container, false);
  }

}
