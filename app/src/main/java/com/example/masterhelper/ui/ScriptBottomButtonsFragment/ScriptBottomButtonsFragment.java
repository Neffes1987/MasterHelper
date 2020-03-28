package com.example.masterhelper.ui.ScriptBottomButtonsFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.masterhelper.R;


public class ScriptBottomButtonsFragment extends Fragment {

  private IScriptBottomButtonsFragment mListener;

  int fragmentScriptBottomButtonsLayout = R.layout.fragment_script_bottom_buttons;

  int duplicateBtnId = R.id.DUPLICATE_BTN_ID;
  ImageButton duplicateBtn;

  int editBtnId = R.id.EDIT_BUTTON_ID;
  ImageButton editBtn;

  int nextBtnId = R.id.NEXT_BTN_ID;
  ImageButton nextBtn;

  public ScriptBottomButtonsFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view =  inflater.inflate(fragmentScriptBottomButtonsLayout, container, false);
    duplicateBtn = view.findViewById(duplicateBtnId);
    duplicateBtn.setOnClickListener(onClickListener);
    editBtn= view.findViewById(editBtnId);
    editBtn.setOnClickListener(onClickListener);
    nextBtn = view.findViewById(nextBtnId);
    nextBtn.setOnClickListener(onClickListener);
    return view;
  }

  View.OnClickListener onClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if (mListener != null) {
        int id = v.getId();
        if(id == duplicateBtnId){
          mListener.setScriptBottomButtonsAction(ScriptBottomButtonsActions.duplicate);
          return;
        }
        if(id == nextBtnId){
          mListener.setScriptBottomButtonsAction(ScriptBottomButtonsActions.next);
          return;
        }
        if(id == editBtnId){
          mListener.setScriptBottomButtonsAction(ScriptBottomButtonsActions.edit);
          return;
        }
        mListener.setScriptBottomButtonsAction(ScriptBottomButtonsActions.empty);
        return;
      }
    }
  };

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof IScriptBottomButtonsFragment) {
      mListener = (IScriptBottomButtonsFragment) context;
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

  public enum ScriptBottomButtonsActions{
    edit,
    next,
    duplicate,
    empty,
  }

  public interface IScriptBottomButtonsFragment {
    void setScriptBottomButtonsAction(ScriptBottomButtonsActions scriptBottomButtonsAction);
  }
}
