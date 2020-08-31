package com.example.com.masterhelper.enemies.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.masterhelper.R;


public class EnemyBottomButtonsFragment extends Fragment {

  private IScriptBottomButtonsFragment mListener;


  int duplicateBtnId = R.id.DUPLICATE_BTN_ID;
  ImageButton duplicateBtn;

  int doneEnemyEditId = R.id.DONE_ENEMY_EDIT_ID;
  ImageButton doneEnemyEdit;

  int editBtnId = R.id.EDIT_ENEMY_BTN_ID;
  ImageButton editBtn;

  int nextBtnId = R.id.NEXT_BTN_ID;
  ImageButton nextBtn;

  int deleteButtonId = R.id.DELETE_BUTTON_ID;
  ImageButton deleteButton;

  public EnemyBottomButtonsFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_dialog_script_bottom_buttons, container, false);
    duplicateBtn = view.findViewById(duplicateBtnId);
    duplicateBtn.setOnClickListener(onClickListener);

    editBtn= view.findViewById(editBtnId);
    editBtn.setOnClickListener(onClickListener);

    nextBtn = view.findViewById(nextBtnId);
    nextBtn.setOnClickListener(onClickListener);

    doneEnemyEdit = view.findViewById(doneEnemyEditId);
    doneEnemyEdit.setOnClickListener(onClickListener);

    deleteButton = view.findViewById(deleteButtonId);
    deleteButton.setOnClickListener(onClickListener);
    setControlMode(true);
    return view;
  }

  public void setControlMode(boolean readonly){
    if(readonly){
      doneEnemyEdit.setVisibility(View.GONE);
      deleteButton.setVisibility(View.GONE);

      duplicateBtn.setVisibility(View.VISIBLE);
      editBtn.setVisibility(View.VISIBLE);
      nextBtn.setVisibility(View.VISIBLE);
    } else {
      doneEnemyEdit.setVisibility(View.VISIBLE);
      deleteButton.setVisibility(View.VISIBLE);

      duplicateBtn.setVisibility(View.GONE);
      editBtn.setVisibility(View.GONE);
      nextBtn.setVisibility(View.GONE);
    }
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
        if(id == deleteButtonId){
          mListener.setScriptBottomButtonsAction(ScriptBottomButtonsActions.delete);
          return;
        }
        if(id == doneEnemyEditId){
          mListener.setScriptBottomButtonsAction(ScriptBottomButtonsActions.save);
          return;
        }
        mListener.setScriptBottomButtonsAction(ScriptBottomButtonsActions.empty);
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
        + " must implement IScriptBottomButtonsFragment");
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
    delete,
    save,
    empty,
  }

  public interface IScriptBottomButtonsFragment {
    void setScriptBottomButtonsAction(ScriptBottomButtonsActions scriptBottomButtonsAction);
  }
}
