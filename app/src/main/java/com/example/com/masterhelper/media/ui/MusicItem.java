package com.example.com.masterhelper.media.ui;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.listFactory.commonAdapter.item.CommonItem;
import com.example.masterhelper.R;
import com.example.com.masterhelper.media.adapters.SoundFileModel;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class MusicItem extends CommonItem {

  private boolean isGeneral;

  public MusicItem(boolean isGeneral) {
    this.isGeneral=isGeneral;
  }

  View.OnClickListener toggleSoundPlaying = v->{
    commonListener.onClick(v);
    switch (v.getId()){
      case R.id.STOP_MUSIC_FILE_ID:
        setMusicStarted(false);
        break;
      case R.id.RUN_MUSIC_FILE_ID:
        setMusicStarted(true);
        break;
    }
  };

  private void setDeleteControl(){
    ImageButton deleteSound = itemView.findViewById(R.id.MUSIC_DELETE_ROW_ID);
    deleteSound.setVisibility(!isGeneral ? View.GONE : View.VISIBLE);
    deleteSound.setOnClickListener(commonListener);
  }

  private void setMusicStarted(boolean isStarted){
    ImageButton startSound = itemView.findViewById(R.id.RUN_MUSIC_FILE_ID);
    ImageButton stopSound = itemView.findViewById(R.id.STOP_MUSIC_FILE_ID);

    startSound.setOnClickListener(toggleSoundPlaying);
    stopSound.setOnClickListener(toggleSoundPlaying);
    startSound.setVisibility(isStarted ? View.GONE : View.VISIBLE);
    stopSound.setVisibility(isStarted ? View.VISIBLE : View.GONE);
  }

  public void setTitle(String title) {
    TextView title1 = itemView.findViewById(R.id.FILE_NAME_ID);
    title1.setText(title);
  }

  public void setSelected(boolean selected) {
    CheckBox selectedView = itemView.findViewById(R.id.FILE_NAME_SELECTOR_ID);
    selectedView.setOnClickListener(commonListener);
    selectedView.setVisibility(isGeneral ? View.GONE : View.VISIBLE);
    selectedView.setChecked(selected);
  }

  public void updateHolderByData(DataModel itemData) {
    super.updateHolderByData(itemData);
    SoundFileModel soundFileModel = (SoundFileModel) itemData;
    setTitle(soundFileModel.getName());
    setSelected(soundFileModel.getSelected());
    setMusicStarted(false);
    setDeleteControl();
  }
}
