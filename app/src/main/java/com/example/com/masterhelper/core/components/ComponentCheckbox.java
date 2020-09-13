package com.example.com.masterhelper.core.components;

import android.os.Bundle;
import android.widget.CheckBox;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.masterhelper.R;


public class ComponentCheckbox extends Fragment {
  static int CHECKBOX_ID = R.id.COMPONENT_CHECKBOX_ID;
  CheckBox checkBox;

  public void setCheckbox(boolean value){
    checkBox.setChecked(value);
  }

  public boolean getCheckboxState(){
    return checkBox.isChecked();
  }

  public void setVisibility(boolean isVisible){
    checkBox.setVisibility(isVisible ? View.VISIBLE : View.GONE);
  }

  public void setLabel(int title){
    if(checkBox == null || title == 0){
      return;
    }
    checkBox.setText(title);
  }

  public ComponentCheckbox() {
    // Required empty public constructor
  }

  public ComponentCheckbox(View parentView) {
    checkBox = parentView.findViewById(CHECKBOX_ID);
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_component_check_box, container, false);
  }
}
