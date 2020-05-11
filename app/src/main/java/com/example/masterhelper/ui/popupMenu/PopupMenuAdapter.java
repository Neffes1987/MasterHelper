package com.example.masterhelper.ui.popupMenu;

import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import com.example.masterhelper.R;

public class PopupMenuAdapter {
  private PopupMenuEvents parentView;
  public PopupMenu popupMenu;

  public PopupMenuAdapter(View v) throws Exception {
    popupMenu = new PopupMenu(v.getContext(), v);
    popupMenu.inflate(R.menu.popupmenu);
    if(v instanceof PopupMenuEvents){
      parentView = (PopupMenuEvents) v;
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
