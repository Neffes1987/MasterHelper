package com.example.masterhelper.ui.viewCharacteristicRow;


import android.content.Context;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.masterhelper.R;


/**  */
public class ViewCharacteristicRow extends Fragment {
  int propertyTitleId = R.id.ROW_TITLE_ID;
  TextView propertyTitle;

  int propertyValueId = R.id.ROW_VALUE_ID;
  TextView propertyValue;

  int rowDecreaseValueId = R.id.ROW_DECREASE_VALUE_ID;
  ImageButton rowDecreaseValue;

  int rowIncreaseValueId = R.id.ROW_INCREASE_VALUE_ID;
  ImageButton rowIncreaseValue;

  IViewCharacteristicRow mListener;
  int title;
  int value = 0;

  public void setTitle(int title) {
    this.title = title;
    propertyTitle.setText(title);
  }

  public void setValue(int value) {
    this.value = value;
    propertyValue.setText(value+"");
  }

  private int getTitle() {
    return title;
  }

  private int getValue() {
    return value;
  }

  View.OnClickListener onClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      int title = getTitle();
      int value = getValue();
      if (mListener != null) {
        int id = v.getId();
        if(id == rowIncreaseValueId){
          value += 1;
          mListener.setViewCharacteristicRowActions(title, value);
        }
        if(id == rowDecreaseValueId){
          value -= 1;
          if(value < 0){
            value = 0;
          }
          mListener.setViewCharacteristicRowActions(title, value);
        }
        setValue(value);
      }
    }
  };

  /**  */
  int fragmentViewCharacteristicRowLayout = R.layout.fragment_view_characteristic_row;

  public ViewCharacteristicRow() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(fragmentViewCharacteristicRowLayout, container, false);
    rowIncreaseValue = view.findViewById(rowIncreaseValueId);
    rowIncreaseValue.setOnClickListener(onClickListener);

    rowDecreaseValue = view.findViewById(rowDecreaseValueId);
    rowDecreaseValue.setOnClickListener(onClickListener);

    propertyTitle = view.findViewById(propertyTitleId);
    propertyValue = view.findViewById(propertyValueId);
    return view;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof IViewCharacteristicRow) {
      mListener = (IViewCharacteristicRow) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public interface IViewCharacteristicRow {
    void setViewCharacteristicRowActions(int title, int value);
  }
}
