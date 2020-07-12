package com.example.com.masterhelper.core.factorys.list.commonAdapter.item;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.masterhelper.R;
import com.example.com.masterhelper.core.factorys.list.commonAdapter.CommonAdapter;
import com.example.com.masterhelper.core.appconfig.models.JourneyModel;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class JourneyItem<Model> extends CommonItem<Model> {

  /** текстовое поле в с именем приключения */
  private TextView title;

  public JourneyItem(View v, CommonAdapter<Model> adapter, boolean hideCheckboxes) {
    super(v, adapter);
    title = itemView.findViewById(R.id.JOURNEY_TITLE_ID);
    title.setOnClickListener(commonListener);
    CheckBox selected = itemView.findViewById(R.id.JOURNEY_SELECTOR);
    selected.setOnClickListener(commonListener);
    selected.setVisibility(hideCheckboxes ? View.GONE : View.VISIBLE);

    ImageButton editPopup = itemView.findViewById(R.id.JOURNEY_EDIT_ID);
    editPopup.setOnClickListener(commonListener);
  }

  public void setTitle(String title) {
    this.title.setText(title);
  }

  public void updateHolderByData(Model itemData, int position) {
    JourneyModel journeyModel = (JourneyModel) itemData;
    setTitle(journeyModel.getTitle());
    setPosition(position);
  }
}
