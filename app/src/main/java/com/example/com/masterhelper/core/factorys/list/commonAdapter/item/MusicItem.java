package com.example.com.masterhelper.core.factorys.list.commonAdapter.item;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.factorys.list.commonAdapter.CommonAdapter;
import com.example.com.masterhelper.core.appconfig.models.SoundFileModel;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class MusicItem<Model> extends CommonItem<Model> {

  /** текстовое поле в с именем файла */
  private TextView title;

  /** файл выделен */
  private CheckBox selected;

  /** запустить проигрывание медиафайла */
  private ImageButton startSound;

  /** остановить проигрывание */
  private ImageButton stopSound;

  public MusicItem(View v, CommonAdapter<Model> adapter, boolean isGeneral) {
    super(v, adapter);
    title = itemView.findViewById(R.id.FILE_NAME_ID);
    selected = itemView.findViewById(R.id.FILE_NAME_SELECTOR_ID);
    selected.setOnClickListener(commonListener);
    selected.setVisibility(isGeneral ? View.GONE : View.VISIBLE);


    startSound = itemView.findViewById(R.id.RUN_MUSIC_FILE_ID);
    stopSound = itemView.findViewById(R.id.STOP_MUSIC_FILE_ID);

    startSound.setOnClickListener(toggleSoundPlaying);
    stopSound.setOnClickListener(toggleSoundPlaying);

    ImageButton deleteSound = itemView.findViewById(R.id.MUSIC_DELETE_ROW_ID);
    deleteSound.setVisibility(!isGeneral ? View.GONE : View.VISIBLE);
    deleteSound.setOnClickListener(commonListener);
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

  private void setMusicStarted(boolean isStarted){
    startSound.setVisibility(isStarted ? View.GONE : View.VISIBLE);
    stopSound.setVisibility(isStarted ? View.VISIBLE : View.GONE);
  }

  public void setTitle(String title) {
    this.title.setText(title);
  }

  public void setSelected(boolean selected) {
    this.selected.setChecked(selected);
  }

  public void updateHolderByData(Model itemData, int position) {
    SoundFileModel soundFileModel = (SoundFileModel) itemData;
    setTitle(soundFileModel.getName());
    setSelected(soundFileModel.getSelected());
    setPosition(position);
    setMusicStarted(false);
  }
}
