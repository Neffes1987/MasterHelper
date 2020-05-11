package com.example.masterhelper.ui.popupMenu;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import com.example.masterhelper.R;

public class PopupMenuAdapter {
  private PopupMenuEvents parentView;
  public PopupMenu popupMenu;

  public PopupMenuAdapter(Context context, View v) throws Exception {
    popupMenu = new PopupMenu(context, v);
    popupMenu.inflate(R.menu.popupmenu);
    if(context instanceof PopupMenuEvents){
      parentView = (PopupMenuEvents) context;
      popupMenu.setOnMenuItemClickListener(listener);
    } else {
      throw new Exception("Activity должен реализовывать интерфейс PopupMenuEvents");
    }
  }

  PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem item) {
      parentView.onPopupMenuSelected(item);
      return true;
    }
  };
}
