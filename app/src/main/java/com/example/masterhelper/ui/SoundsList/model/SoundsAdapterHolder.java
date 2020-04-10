package com.example.masterhelper.ui.SoundsList.model;

import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.masterhelper.R;
import com.example.masterhelper.ui.RecyclerViewFragment.IRecycleAdapter;
import com.example.masterhelper.ui.RecyclerViewFragment.RecyclerAccordionEvents;
import com.example.masterhelper.ui.RecyclerViewFragment.models.scene.SceneRecycleDataModel;

/** Модель для управления интерфейсом внутри аккордиона для цеклического списка*/
public class SoundsAdapterHolder extends RecyclerView.ViewHolder{

  /** текстовое поле в с именем файла */
  private TextView title;

  /** файл выделен */
  private CheckBox selected;

  /** запустить проигрывание сцены */
  private ImageButton startSound;

  private int position;

  View parentFragment;

  /** @constructor генератор указателей на элементы UI для адаптера */
  public SoundsAdapterHolder(View v) {
    super(v);
    title = v.findViewById(R.id.FILE_NAME_ID);
    selected = v.findViewById(R.id.FILE_NAME_SELECTOR_ID);
    selected.setOnClickListener(musicItemSelectedListener);
    startSound = v.findViewById(R.id.RUN_MUSIC_FILE_ID);
    startSound.setOnClickListener(startMusicListener);
    parentFragment = v;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  View.OnClickListener startMusicListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if(parentFragment instanceof ISoundsAdapterHolder){
        ((ISoundsAdapterHolder) parentFragment).onSoundStarted(position);
      }
    }
  };

  View.OnClickListener musicItemSelectedListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if(parentFragment instanceof ISoundsAdapterHolder){
        ((ISoundsAdapterHolder) parentFragment).onSoundSelected(position, selected.isSelected());
      }
    }
  };

  public void setTitle(String title) {
    this.title.setText(title);
  }

  public void setSelected(boolean selected) {
    this.selected.setSelected(selected);
  }

  public void updateHolderByData(SoundFileModel itemData, int position) {
    setTitle(itemData.getFilename());
    setSelected(itemData.getSelected());
    setPosition(position);
  }
}
