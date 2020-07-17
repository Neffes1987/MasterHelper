package com.example.com.masterhelper.core.factories.dialogs.dialogs;

import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.masterhelper.R;

public abstract class CommonDialog extends AppCompatDialogFragment implements IDialog {
  public  static final int DIALOG_CREATE_ACTIVITY_RESULT = 1000;
  public  static final int DIALOG_UPDATE_ACTIVITY_RESULT = 2000;
  public  static final int DIALOG_UPDATE_SETTING_ACTIVITY_RESULT = 3000;
  public  static final int DIALOG_CREATE_SETTING_ACTIVITY_RESULT = 4000;
  private int title = R.string.add_enemy_achieve_placeholder;

  private DialogInterface.OnClickListener emptyListener = (dialog, id) -> {};

  protected DialogInterface.OnClickListener resolveListener = emptyListener;
  protected DialogInterface.OnClickListener rejectListener = emptyListener;
  protected int getTitle(){
    return title;
  }

  @Override
  public void setTitle(int title) {
    this.title = title;
  }

  @Override
  public void setOnResolveListener(DialogInterface.OnClickListener newClickListener) {
    if(newClickListener != null){
      resolveListener = newClickListener;
    }
  }

  @Override
  public void setOnRejectListener(DialogInterface.OnClickListener newClickListener) {
    if(newClickListener != null){
      rejectListener = newClickListener;
    }
  }
}
