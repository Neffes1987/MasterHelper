package com.example.com.masterhelper.viewCharacteristicRow;


import android.content.Context;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.masterhelper.R;
import com.example.com.masterhelper.abilities.models.AbilityModel;


/**  */
public class ViewCharacteristicRow extends Fragment {
  /**  */
  TextView propertyTitle;

  /**  */
  TextView propertyValue;

  /**  */
  ImageButton rowDecreaseValue;

  /**  */
  ImageButton rowIncreaseValue;

  /**  */
  ImageButton rowDeleteBtn;

  /**  */
  IViewCharacteristicRow mListener;

  /** */
  AbilityRowAdapter abilityRowAdapter = new AbilityRowAdapter();

  /**  */
  public void setTitle(String title) {
    abilityRowAdapter.setTitle(title);
    propertyTitle.setText(title);
  }

  /**  */
  public void setValue() {
    int value = abilityRowAdapter.getValue();
    propertyValue.setText(value + "");
  }

  /** */
  public void setEditable(boolean editable) {
    rowDeleteBtn.setVisibility(editable ? View.VISIBLE : View.GONE);
  }

  /** */
  View.OnClickListener onDeleteBtnListener = v -> {
    if (mListener != null) {
      mListener.deleteRow(abilityRowAdapter.getRowId());
    }
  };


  /** */
  private boolean calculateNewValue(View v, int offset){
    if (mListener != null) {
      int id = v.getId();
      if(id == R.id.ROW_INCREASE_VALUE_ID){
        abilityRowAdapter.increaseValue(offset);
        mListener.changeValue(abilityRowAdapter.getRowId(), abilityRowAdapter.getValue());
      }
      if(id == R.id.ROW_DECREASE_VALUE_ID){
        abilityRowAdapter.decreaseValue(offset);
        mListener.changeValue(abilityRowAdapter.getRowId(), abilityRowAdapter.getValue());
      }
    }
    setValue();
    return true;
  }


  /** */
  View.OnClickListener onClickChangeBtnListener = v -> calculateNewValue(v, 1);
  View.OnLongClickListener onLongClickListener = v -> calculateNewValue(v, 10);

  /**  */
  int fragmentViewCharacteristicRowLayout = R.layout.fragment_view_characteristic_row;

  /** */
  public ViewCharacteristicRow() {
    // Required empty public constructor
  }

  /** */
  public void setDefaultValues(AbilityModel achieve){
    abilityRowAdapter.updateRowData(achieve);
  }

  /** */
  public void updateView(){
    setTitle(abilityRowAdapter.getTitle());
    setEditable(abilityRowAdapter.editable);
    setValue();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(fragmentViewCharacteristicRowLayout, container, false);
    rowIncreaseValue = view.findViewById(R.id.ROW_INCREASE_VALUE_ID);
    rowIncreaseValue.setOnClickListener(onClickChangeBtnListener);
    rowIncreaseValue.setOnLongClickListener(onLongClickListener);

    rowDecreaseValue = view.findViewById(R.id.ROW_DECREASE_VALUE_ID);
    rowDecreaseValue.setOnClickListener(onClickChangeBtnListener);
    rowDecreaseValue.setOnLongClickListener(onLongClickListener);

    rowDeleteBtn = view.findViewById(R.id.ROW_DELETE_ID);
    rowDeleteBtn.setOnClickListener(onDeleteBtnListener);

    propertyTitle = view.findViewById(R.id.ROW_TITLE_ID);
    propertyValue = view.findViewById(R.id.ROW_VALUE_ID);

    updateView();
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

  /** */
  public interface IViewCharacteristicRow {
    void changeValue(int id, int value);
    void deleteRow(int rowId);
  }
}
