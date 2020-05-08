package com.example.masterhelper.commonAdapter.item;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.masterhelper.R;
import com.example.masterhelper.commonAdapter.CommonAdapter;
import com.example.masterhelper.models.SoundFileModel;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class MusicItem<Model> extends CommonItem<Model> {

  /** текстовое поле в с именем файла */
  private TextView title;

  /** файл выделен */
  private CheckBox selected;

  /** запустить проигрывание сцены */
  private ImageButton startSound;

  public MusicItem(View v, CommonAdapter<Model> adapter, boolean hideCheckboxes) {
    super(v, adapter);
    title = itemView.findViewById(R.id.FILE_NAME_ID);
    selected = itemView.findViewById(R.id.FILE_NAME_SELECTOR_ID);
    selected.setOnClickListener(commonListener);
    selected.setVisibility(hideCheckboxes ? View.GONE : View.VISIBLE);
    startSound = itemView.findViewById(R.id.RUN_MUSIC_FILE_ID);
    startSound.setOnClickListener(commonListener);

  }

  public void setTitle(String title) {
    this.title.setText(title);
  }

  public void setSelected(boolean selected) {
    this.selected.setSelected(selected);
  }

  public void updateHolderByData(Model itemData, int position) {
    SoundFileModel soundFileModel = (SoundFileModel) itemData;
    setTitle(soundFileModel.getFilename());
    setSelected(soundFileModel.getSelected());
    setPosition(position);
  }
}
