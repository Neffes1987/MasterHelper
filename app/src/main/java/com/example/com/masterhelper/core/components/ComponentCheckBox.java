package com.example.com.masterhelper.core.components;

import android.os.Bundle;
import android.widget.CheckBox;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.masterhelper.R;


public class ComponentCheckBox extends Fragment {
  static int CHECKBOX_ID = R.id.COMPONENT_CHECKBOX_ID;

  public static void setCheckbox(View parentView, boolean value){
    CheckBox checkBox = parentView.findViewById(CHECKBOX_ID);
    checkBox.setChecked(value);
  }

  public static boolean getCheckboxState(View parentView){
    CheckBox checkBox = parentView.findViewById(CHECKBOX_ID);
    return checkBox.isChecked();
  }

  public static void setVisibility(View parentView, boolean isVisible){
    CheckBox checkBox = parentView.findViewById(CHECKBOX_ID);
    checkBox.setVisibility(isVisible ? View.VISIBLE : View.GONE);
  }

  public static void setLabel(View parentView, int title){
    CheckBox checkBox = parentView.findViewById(CHECKBOX_ID);
    if(checkBox == null || title == 0){
      return;
    }
    checkBox.setText(title);
  }

  public ComponentCheckBox() {
    // Required empty public constructor
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
