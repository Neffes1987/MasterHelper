package com.example.com.masterhelper.core.components.dialogs.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.masterhelper.R;

public abstract class CommonDialog {
  public  static final int DIALOG_CREATE_ACTIVITY_RESULT = 1000;
  public  static final int DIALOG_UPDATE_ACTIVITY_RESULT = 2000;
  public  static final int DIALOG_UPDATE_SETTING_ACTIVITY_RESULT = 3000;
  public  static final int DIALOG_CREATE_SETTING_ACTIVITY_RESULT = 4000;
  private int title = R.string.add_enemy_achieve_placeholder;

  private DialogInterface.OnClickListener emptyListener = (dialog, id) -> { };

  protected DialogInterface.OnClickListener resolveListener = emptyListener;
  protected DialogInterface.OnClickListener rejectListener = emptyListener;
  protected int getTitle(){
    return title;
  }

  public void setTitle(int title) {
    this.title = title;
  }

  public void setOnResolveListener(DialogInterface.OnClickListener newClickListener) {
    if(newClickListener != null){
      resolveListener = newClickListener;
    }
  }

  public void setOnRejectListener(DialogInterface.OnClickListener newClickListener) {
    if(newClickListener != null){
      rejectListener = newClickListener;
    }
  }

  public void show(Activity context, DataModel settings){}
  public void show(Activity context){}
}
