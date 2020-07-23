package com.example.com.masterhelper.core.factories.list.commonAdapter.item;

import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.force.models.ForceModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.core.factories.list.commonAdapter.CommonAdapter;
import com.example.masterhelper.R;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class ForceItem extends CommonItem {

  public ForceItem(View v, CommonAdapter adapter) {
    super(v, adapter);
    LinearLayout titleBar = v.findViewById(R.id.FORCE_ACCORDION_HEADER_ID);
    titleBar.setOnClickListener(itemToggle);
  }

  public void setTitle(String title) {
    TextView title1 = itemView.findViewById(R.id.FORCE_TITLE_ID);
    title1.setText(title);
  }

  public void setType(String type) {
    TextView type1 = itemView.findViewById(R.id.FORCE_TYPE_ID);
    type1.setText(type);
  }

  public void setDisadvantage(ModelList disadvantage) {
    TextView disadvantage1 = itemView.findViewById(R.id.FORCE_DISADVANTAGES_ID);
    disadvantage1.setText(disadvantage.listToString());
  }

  public void setIcon(String icon) {
    ImageView icon1 = itemView.findViewById(R.id.FORCE_ICON_ID);
    if(icon != null){
      icon1.setImageURI(Uri.parse(icon));
    }
  }

  public void setEnemies(ModelList enemies) {
    TextView enemies1 = itemView.findViewById(R.id.FORCE_ENEMIES_ID);
    enemies1.setText(enemies.listToString());
  }

  public void setCooperators(ModelList cooperators) {
    TextView cooperators1 = itemView.findViewById(R.id.FORCE_COOPERATORS_ID);
    cooperators1.setText(cooperators.listToString());
  }

  public void setGoals(ModelList goals) {
    TextView goals1 = itemView.findViewById(R.id.FORCE_GOALS_AND_MOTIVATION_ID);
    goals1.setText(goals.listToString());
  }

  public void setDescription(String description) {
    TextView description1 = itemView.findViewById(R.id.FORCE_DESCRIPTIONS_ID);
    description1.setText(description);
  }

  public void setAdvantage(ModelList advantage) {
    TextView advantage1 = itemView.findViewById(R.id.FORCE_ADVANTAGES);
    advantage1.setText(advantage.listToString());
  }

  public void setDepended(ModelList depended) {
    TextView depended1 = itemView.findViewById(R.id.FORCE_DEPENDED_ID);
    depended1.setText(depended.listToString());
  }

  public void setOwned(ModelList owned) {
    TextView owned1 = itemView.findViewById(R.id.FORCE_OWN_ID);
    owned1.setText(owned.listToString());
  }

  public void setJourneyList(ModelList journeyList) {
    if(journeyList == null){
      return;
    }
    TextView journeyList1 = itemView.findViewById(R.id.FORCE_JOURNEY_ID);
    journeyList1.setText(journeyList.listToString());
  }

  public void updateHolderByData(DataModel itemData, int position) {
    ForceModel forceModel = (ForceModel) itemData;
    setTitle(forceModel.getName());
    setPosition(position);
    setIcon(forceModel.getIcon());
    setAdvantage(forceModel.getAdvantagesRelations());
    setCooperators(forceModel.getCooperators());
    setDepended(forceModel.getDepended());
    setDescription(forceModel.getDescription());
    setEnemies(forceModel.getEnemies());
    setGoals(forceModel.getGoals());
    setType(forceModel.getTypeString());
    setJourneyList(forceModel.getInvolvedJourneys());
    setOwned(forceModel.getOwners());
    setDisadvantage(forceModel.getDisadvantagesRelations());
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
}
