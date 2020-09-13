package com.example.com.masterhelper.core.components;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.masterhelper.R;

public class ComponentEditField extends Fragment {
  private EditText writeable;
  private TextView readable;
  private TextView caption;

  public void setCaption(int title){
    if(title == 0){
      caption.setVisibility(View.GONE);
    } else {
      caption.setVisibility(View.VISIBLE);
      caption.setText(title);
    }
  }

  public void setValue(String value, boolean isReadonly){
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

  public String getValue(){
    if(writeable.getVisibility() == View.VISIBLE){
      return writeable.getText().toString();
    }
    return readable.getText().toString();
  }

  public ComponentEditField(View parent) {
    readable = parent.findViewById(R.id.EDIT_FIELD_VALUE_ID);
    writeable = parent.findViewById(R.id.EDIT_FIELD_WRITEABLE_ID);
    caption = parent.findViewById(R.id.EDIT_FIELD_CAPTION_ID);
  }


  public ComponentEditField() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
    return  inflater.inflate(R.layout.fragment_component_edit_field, container, false);
  }
}
