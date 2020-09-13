package com.example.com.masterhelper.core.components.buttons;


import android.os.Bundle;
import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.masterhelper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ComponentFloatButtonPrimary extends Fragment {
  protected FloatingActionButton button;

  public void setVisibility(boolean visibility) {
    if(visibility){
      button.show();
    } else {
      button.hide();
    }
  }

  public void setIconResource(int iconResource) {
    button.setImageResource(iconResource);
  }

  public void setListener(View.OnClickListener listener) {
    button.setOnClickListener(listener);
  }

  public void setLongListener(View.OnLongClickListener listener){
    button.setOnLongClickListener(listener);
  }

  public void setColorResource(int color){
    button.setBackgroundTintList(ContextCompat.getColorStateList(GlobalApplication.getAppContext(), color));
  }

  public ComponentFloatButtonPrimary(View parent) {
    button = parent.findViewById(R.id.COMPONENT_BUTTON_FLOAT_PRIMARY_ID);
    if(button == null){
      Log.i("TAG", "parent view does not have COMPONENT_BUTTON_FLOAT_PRIMARY_ID" );
    }
    // Required empty public constructor
  }

  public ComponentFloatButtonPrimary() {
    // Required empty public constructor
  }




  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_component_float_button_primary, container, false);
  }

}
