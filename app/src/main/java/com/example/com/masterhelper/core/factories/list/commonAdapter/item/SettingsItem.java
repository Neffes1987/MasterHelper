package com.example.com.masterhelper.core.factories.list.commonAdapter.item;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.factories.list.commonAdapter.CommonAdapter;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class SettingsItem extends CommonItem {

  /** текстовое поле в с именем приключения */
  private TextView title;

  /** текстовое поле в с именем приключения */
  private TextView description;

  public SettingsItem(View v, CommonAdapter adapter) {
    super(v, adapter);
    initItem(true, true, false);
  }

  public SettingsItem(View v, CommonAdapter adapter, boolean hideCheckboxes, boolean hideDescription, boolean changeIcon) {
    super(v, adapter);
    initItem(hideCheckboxes, hideDescription, changeIcon);
  }

  private void initItem(boolean hideCheckboxes, boolean hideDescription, boolean changeIcon){
    title = itemView.findViewById(R.id.ITEM_TITLE_ID);
    description = itemView.findViewById(R.id.ITEM_DESCRIPTION_ID);

    if(hideDescription){
      description.setVisibility(View.GONE);
    } else {
      description.setVisibility(View.VISIBLE);
    }

    title.setOnClickListener(commonListener);

    CheckBox selected = itemView.findViewById(R.id.ITEM_SELECTOR_ID);
    selected.setOnClickListener(commonListener);
    selected.setVisibility(hideCheckboxes ? View.GONE : View.VISIBLE);

    ImageButton editPopup = itemView.findViewById(R.id.ITEM_EDIT_ID);
    if(changeIcon){
      editPopup.setImageResource(R.mipmap.baseline_clear_black_18dp);
      editPopup.setColorFilter(ContextCompat.getColor(editPopup.getContext(), R.color.colorDelete), android.graphics.PorterDuff.Mode.SRC_IN);
    }
    editPopup.setOnClickListener(commonListener);
  }


  public void setTitle(String title) {
    this.title.setText(title);
  }

  public void setDescription(String description) {
    this.description.setText(description);
  }

  public void updateHolderByData(DataModel itemData, int position) {
    setTitle(itemData.getName());
    setDescription(itemData.getDescription());
    setPosition(position);
  }
}
