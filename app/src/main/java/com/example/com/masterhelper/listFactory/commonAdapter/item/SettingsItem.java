package com.example.com.masterhelper.listFactory.commonAdapter.item;

import android.view.View;
import android.widget.*;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.force.models.RelationModal;
import com.example.masterhelper.R;
import com.example.com.masterhelper.listFactory.commonAdapter.CommonAdapter;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class SettingsItem extends CommonItem {

  private boolean hideDescription = true;
  private boolean hideCheckboxes = true;
  private boolean showDeleteButton = true;
  private boolean showRelationBlock = false;

  public SettingsItem(View v, CommonAdapter adapter, SettingsType type) {
    super(v, adapter);
    switch (type){
      case abilities:
        break;
      case journey:
        showDeleteButton = false;
        hideDescription = false;
        break;
      case global:
        hideDescription = false;
        break;
      case selectable:
        hideDescription = false;
        hideCheckboxes = false;
        break;
      case relation:
        hideDescription = false;
        showRelationBlock = true;
        break;
      default:
    }
  }

  private void setRelationBlock(DataModel model){
    LinearLayout relation = itemView.findViewById(R.id.RELATION_BLOCK_ID);
    if(!showRelationBlock){
      relation.setVisibility(View.GONE);
      return;
    }
    if(!(model instanceof RelationModal)){
      return;
    }
    RelationModal relationModal = (RelationModal) model;
    relation.setVisibility(View.VISIBLE);
    TextView positive = itemView.findViewById(R.id.RELATION_POSITIVE_STATUS_ID);
    positive.setText(relationModal.getResolveResult());
    TextView negative = itemView.findViewById(R.id.RELATION_NEGATIVE_STATUS_ID);
    negative.setText(relationModal.getRejectResult());
  }

  private void setTitle(String caption) {
    TextView title = itemView.findViewById(R.id.ITEM_TITLE_ID);
    if(caption != null){
      title.setText(caption);
    }
    title.setOnClickListener(commonListener);
  }

  private void setClickableZone() {
    LinearLayout zone = itemView.findViewById(R.id.ROW_ITEM_CLICKABLE_ID);
    zone.setOnClickListener(commonListener);
  }

  public void setDescription(DataModel item) {

    String descriptionText = item.getDescription();
    if(item instanceof RelationModal){
      RelationModal model = (RelationModal) item;
      descriptionText= "["+model.getDirectionString() + "]: " + descriptionText;
    }

    TextView description = itemView.findViewById(R.id.ITEM_DESCRIPTION_ID);

    if(descriptionText == null || descriptionText.length() == 0){
      description.setVisibility(View.GONE);
      return;
    }

    if(hideDescription){
      description.setVisibility(View.GONE);
    } else {
      description.setVisibility(View.VISIBLE);
    }
    description.setText(descriptionText);
  }

  private void setCheckbox(boolean checked){
    CheckBox selected = itemView.findViewById(R.id.ITEM_SELECTOR_ID);
    selected.setOnClickListener(commonListener);
    selected.setVisibility(hideCheckboxes ? View.GONE : View.VISIBLE);
    selected.setChecked(checked);
  }

  private void setDeleteBtn(){
    ImageButton deleteButton = itemView.findViewById(R.id.ITEM_DELETE_BUTTON);
    if(showDeleteButton){
      deleteButton.setVisibility(View.VISIBLE);
    }
    deleteButton.setOnClickListener(commonListener);
  }

  private void setEditBtn(){
    ImageButton editPopup = itemView.findViewById(R.id.ITEM_EDIT_ID);
    editPopup.setOnClickListener(commonListener);
  }

  public void updateHolderByData(DataModel itemData, int position) {
    setTitle(itemData.getName());
    setDescription(itemData);
    setCheckbox(itemData.isSelected);
    setPosition(position);
    setDeleteBtn();
    setEditBtn();
    setClickableZone();
    setRelationBlock(itemData);
  }

  public enum SettingsType{
    journey,
    abilities,
    global,
    selectable,
    relation
  }
}
