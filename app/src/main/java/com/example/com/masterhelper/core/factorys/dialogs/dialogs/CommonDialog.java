package com.example.com.masterhelper.core.factorys.dialogs.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.masterhelper.R;

public class CommonDialog extends AppCompatDialogFragment implements IDialog {
  private int title = R.string.add_enemy_achieve_placeholder;

  private DialogInterface.OnClickListener emptyListener = (dialog, id) -> {};

  protected DialogInterface.OnClickListener resolveListener = emptyListener;
  protected DialogInterface.OnClickListener rejectListener = emptyListener;
  protected int getTitle(){
    return title;
  }

  @Override
  public void show(Context context) { }

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
