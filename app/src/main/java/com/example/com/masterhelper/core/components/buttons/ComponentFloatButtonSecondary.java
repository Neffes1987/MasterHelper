package com.example.com.masterhelper.core.components.buttons;


import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.com.masterhelper.core.components.buttons.ComponentFloatButtonPrimary;
import com.example.masterhelper.R;


public class ComponentFloatButtonSecondary extends ComponentFloatButtonPrimary {


  public ComponentFloatButtonSecondary(View parent) {
    button = parent.findViewById(R.id.COMPONENT_BUTTON_FLOAT_SECONDARY_ID);
    if(button == null){
      Log.i("TAG", "parent view does not have COMPONENT_BUTTON_FLOAT_SECONDARY_ID" );
    }
    // Required empty public constructor
  }

  public ComponentFloatButtonSecondary() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_component_float_button_secondary, container, false);
  }

}
