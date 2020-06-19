package com.example.masterhelper.ui.viewCharacteristicRow;


import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.masterhelper.R;
import com.example.masterhelper.models.AchieveModel;


/**  */
public class ViewCharacteristicRow extends Fragment {
  int propertyTitleId = R.id.ROW_TITLE_ID;
  EditText propertyTitle;

  int propertyValueId = R.id.ROW_VALUE_ID;
  TextView propertyValue;

  int rowDecreaseValueId = R.id.ROW_DECREASE_VALUE_ID;
  ImageButton rowDecreaseValue;

  int rowIncreaseValueId = R.id.ROW_INCREASE_VALUE_ID;
  ImageButton rowIncreaseValue;

  int rowDeleteBtnId = R.id.ROW_CHANGE_ID;
  ImageButton rowDeleteBtn;

  int rowChangeId = R.id.ROW_CHANGE_ID;
  ImageButton rowChange;

  IViewCharacteristicRow mListener;

  String title = "";
  int value = 0;
  long rowId = 0;
  boolean editable = false;
  boolean isNew = false;

  public void setTitle(String title) {
    this.title = title;
    propertyTitle.setText(title);
  }

  public void setValue(int value) {
    this.value = value;
    propertyValue.setText(value + "");
  }

  public void setRowId(long rowId) {
    this.rowId = rowId;
  }

  public void setIsNew(boolean isNew) {
    this.isNew = isNew;
    if(isNew){
      setEditable(true);
    }
    setValueControlsVisible(!isNew);
  }

  public void setValueControlsVisible(boolean isVisible) {
    if(rowIncreaseValue != null){
      rowIncreaseValue.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
    if(propertyTitle != null){
      propertyTitle.setEnabled(!isVisible);
    }
    if(rowDecreaseValue != null){
      rowDecreaseValue.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
    if(propertyValue != null){
      propertyValue.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
    if(rowDeleteBtn != null){
      rowDeleteBtn.setImageResource(!isVisible ? R.mipmap.baseline_done_black_18dp : R.mipmap.baseline_clear_black_18dp);
      rowDeleteBtn.setVisibility(editable ? View.VISIBLE : View.GONE);
    }
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  private String getTitle() {
    title = propertyTitle.getText().toString();
    return title;
  }

  private int getValue() {
    return value;
  }

  public long getRowId() {
    return rowId;
  }

  View.OnClickListener onDeleteBtnBtnListener = v -> {
    if (mListener != null) {
      mListener.deleteRow(getRowId());
    }
  };

  View.OnClickListener onAddBtnBtnListener = v -> {
    if (mListener != null) {
      String title = getTitle();
      if(title.trim().length() > 0){
        mListener.addNewRow(getTitle());
        setTitle("");
        return;
      }
      Toast.makeText(getContext(), R.string.add_enemy_warning_title_placeholder, Toast.LENGTH_LONG).show();
    }
  };

  View.OnClickListener onClickChangeBtnBtnListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      int value = getValue();
      if (mListener != null) {
        int id = v.getId();
        if(id == rowIncreaseValueId){
          value += 1;
          mListener.changeValue(rowId, value);
        }
        if(id == rowDecreaseValueId){
          value -= 1;
          if(value < 0){
            value = 0;
          }
          mListener.changeValue(rowId, value);
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

  public void setDefaultValues(AchieveModel achieve, boolean isNew){
    editable = achieve.getTag() == null;
    title = achieve.getName();
    rowId = achieve.getId();
    value = achieve.getValue();
    this.isNew = isNew;
  }

  public void updateView(){
    setIsNew(isNew);
    setEditable(editable);
    setTitle(title);
    setValue(value);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(fragmentViewCharacteristicRowLayout, container, false);
    rowIncreaseValue = view.findViewById(rowIncreaseValueId);
    rowIncreaseValue.setOnClickListener(onClickChangeBtnBtnListener);

    rowDecreaseValue = view.findViewById(rowDecreaseValueId);
    rowDecreaseValue.setOnClickListener(onClickChangeBtnBtnListener);

    rowDeleteBtn = view.findViewById(rowDeleteBtnId);
    rowDeleteBtn.setOnClickListener(onDeleteBtnBtnListener);

    rowChange = view.findViewById(rowChangeId);
    rowChange.setOnClickListener(onAddBtnBtnListener);

    propertyTitle = view.findViewById(propertyTitleId);
    propertyValue = view.findViewById(propertyValueId);

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

  public interface IViewCharacteristicRow {
    void changeValue(long id, int value);
    void deleteRow(long rowId);
    void addNewRow(String title);
  }
}
