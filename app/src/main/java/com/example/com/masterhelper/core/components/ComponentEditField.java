package com.example.com.masterhelper.core.components;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.masterhelper.R;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class ComponentEditField extends Fragment {
  public static void setCaption(View view, int title){
    TextView caption = view.findViewById(R.id.EDIT_FIELD_CAPTION_ID);
    if(title == 0){
      caption.setVisibility(View.GONE);
    } else {
      caption.setVisibility(View.VISIBLE);
      caption.setText(title);
    }
  }



  public static void setValue(View view, String value, boolean isReadonly){
    TextView readable = view.findViewById(R.id.EDIT_FIELD_VALUE_ID);
    TextView writeable = view.findViewById(R.id.EDIT_FIELD_WRITEABLE_ID);
    if(isReadonly){
      readable.setVisibility(View.VISIBLE);
      writeable.setVisibility(View.GONE);
      readable.setText(value);
    } else {
      readable.setVisibility(View.GONE);
      writeable.setVisibility(View.VISIBLE);
      writeable.setText(value);
    }
  }

  public static String getValue(View view){
    TextView readable = view.findViewById(R.id.EDIT_FIELD_VALUE_ID);
    TextView writeable = view.findViewById(R.id.EDIT_FIELD_WRITEABLE_ID);
    if(writeable.getVisibility() == View.VISIBLE){
      return writeable.getText().toString();
    }
    return readable.getText().toString();
  }


  public ComponentEditField() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
    return  inflater.inflate(R.layout.fragment_component_edit_field, container, false);
  }
}
