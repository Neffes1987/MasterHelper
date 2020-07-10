package com.masterhelper.listFactory.commonAdapter.item;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.masterhelper.R;
import com.masterhelper.listFactory.commonAdapter.CommonAdapter;
import com.masterhelper.appconfig.models.AbilityModel;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class AbilityItem<Model> extends CommonItem<Model> {

  /** текстовое поле в с именем приключения */
  private TextView title;

  public AbilityItem(View v, CommonAdapter<Model> adapter, boolean hideCheckboxes) {
    super(v, adapter);
    title = itemView.findViewById(R.id.JOURNEY_TITLE_ID);
    title.setOnClickListener(commonListener);
    CheckBox selected = itemView.findViewById(R.id.JOURNEY_SELECTOR);
    selected.setOnClickListener(commonListener);
    selected.setVisibility(hideCheckboxes ? View.GONE : View.VISIBLE);

    ImageButton editPopup = itemView.findViewById(R.id.JOURNEY_EDIT_ID);
    editPopup.setImageResource(R.mipmap.baseline_clear_black_18dp);
    editPopup.setColorFilter(ContextCompat.getColor(editPopup.getContext(), R.color.colorDelete), android.graphics.PorterDuff.Mode.SRC_IN);

    editPopup.setOnClickListener(commonListener);
  }

  public void setTitle(String title) {
    this.title.setText(title);
  }

  public void updateHolderByData(Model itemData, int position) {
    AbilityModel journeyModel = (AbilityModel) itemData;
    setTitle(journeyModel.getName());
    setPosition(position);
  }
}
