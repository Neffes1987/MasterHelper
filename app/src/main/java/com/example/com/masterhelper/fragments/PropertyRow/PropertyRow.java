package com.example.com.masterhelper.fragments.PropertyRow;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.masterhelper.R;


/** */
public class PropertyRow extends Fragment {

  public PropertyRow() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public static void setTitle(View currentView, int titleValue){
    TextView title = currentView.findViewById(R.id.PROPERTY_TITLE_ID);
    title.setText(titleValue);
  }

  public static void setValue(View currentView, String titleValue){
    TextView value = currentView.findViewById(R.id.PROPERTY_CONTENT_ID);
    value.setText(titleValue);
  }

  public static void initEditButton(View currentView, boolean show, View.OnClickListener listener){
    ImageButton editBtn = currentView.findViewById(R.id.PROPERTY_EDIT_BUTTON);
    if(show){
      editBtn.setVisibility(View.VISIBLE);
      editBtn.setOnClickListener(listener);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_property_row, container, false);
  }
}
