package com.example.com.masterhelper.core.listFactory.commonAdapter.item;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.example.com.masterhelper.core.components.dialogs.dialogs.MultiChooseDialog;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.force.models.ForceModel;
import com.example.com.masterhelper.fragments.PropertyRow.PropertyRow;
import com.example.masterhelper.R;

import static android.content.DialogInterface.BUTTON_POSITIVE;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class ForceItem extends CommonItem {
  View view;
  ForceModel forceModel;

  MultiChooseDialog multiChooseDialog;

  public void setMultiChooseDialog(View view) {
    Activity host = (Activity) view.getContext();
    this.multiChooseDialog = new MultiChooseDialog(host);
  }

  private void setJourneyProperties(View v, String value){
    View journeysProperty = v.findViewById(R.id.FORCE_JOURNEYS_PROPERTY_ID);
    PropertyRow.initEditButton(journeysProperty, true, onJourneyEdit);
    PropertyRow.setTitle(journeysProperty, R.string.force_journeys_title);
    PropertyRow.setValue(journeysProperty, value);
  }

  private void setGoalsProperties(View v, String value){
    View journeysProperty = v.findViewById(R.id.FORCE_GOALS_PROPERTY_ID);
    PropertyRow.initEditButton(journeysProperty, true, commonListener);
    PropertyRow.setTitle(journeysProperty, R.string.force_goal_motivation_title);
    PropertyRow.setValue(journeysProperty, value);
  }

  public ForceItem() {
    view = itemView;
    LinearLayout titleBar = view.findViewById(R.id.FORCE_ACCORDION_HEADER_ID);
    titleBar.setOnClickListener(itemToggle);
  }

  public void updateHolderByData(DataModel itemData) {
    forceModel = (ForceModel) itemData;
    setJourneyProperties(view, forceModel.getInvolvedJourneys().listToString());
    setGoalsProperties(view, "goals");
    setMultiChooseDialog(view);
    setEditBtn();
  }

  public void setEditBtn() {
    ImageButton editBtn = itemView.findViewById(R.id.ITEM_EDIT_ID);
    editBtn.setOnClickListener(commonListener);
  }

  View.OnClickListener itemToggle = v -> {
    LinearLayout body = itemView.findViewById(R.id.FORCE_ACCORDION_BODY_ID);
    toggleVisibility(body);
  };


  View.OnClickListener onJourneyEdit = v -> {
    multiChooseDialog.setListOfItems(forceModel.getInvolvedJourneys().getSelectedItems());
    multiChooseDialog.setOnResolveListener((dialogInterface, id) -> {
      if(id == BUTTON_POSITIVE){
        boolean[] list = multiChooseDialog.getSelectedItems();
        //forceModel.getInvolvedJourneys().setSelectedItems(list);
      }
    });
    multiChooseDialog.show();
  };
  View.OnClickListener onGoalsEdit = v -> {};
}
