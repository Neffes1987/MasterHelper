package com.example.com.masterhelper.core.factorys.list.commonAdapter.item;

import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.com.masterhelper.core.appconfig.models.DataModel;
import com.example.com.masterhelper.core.appconfig.models.forces.ForceModel;
import com.example.com.masterhelper.core.appconfig.models.utilities.ModelList;
import com.example.com.masterhelper.core.factorys.list.commonAdapter.CommonAdapter;
import com.example.masterhelper.R;

/** Модель для управления интерфейсом внутри элемента для циклического списка
 * Model - тип модели данных, который следует передать в обработчик жлемента списка для инициализаци
 * */
public class ForceItem extends CommonItem {

  /** текстовое поле в с именем приключения */
  private TextView title;
  private TextView journeyList;
  private TextView type;
  private TextView goals;
  private TextView cooperators;
  private TextView enemies;
  private TextView advantage;
  private TextView disadvantage;
  private TextView owned;
  private TextView depended;
  private TextView description;

  private ImageView icon;
  private ImageButton expand;

  private LinearLayout body;


  public ForceItem(View v, CommonAdapter adapter) {
    super(v, adapter);
    title = itemView.findViewById(R.id.FORCE_TITLE_ID);
    journeyList = itemView.findViewById(R.id.FORCE_JOURNEY_ID);
    type = itemView.findViewById(R.id.FORCE_TYPE_ID);
    goals = itemView.findViewById(R.id.FORCE_GOALS_AND_MOTIVATION_ID);
    cooperators = itemView.findViewById(R.id.FORCE_COOPERATORS_ID);
    enemies = itemView.findViewById(R.id.FORCE_ENEMIES_ID);
    advantage = itemView.findViewById(R.id.FORCE_ADVANTAGES);
    disadvantage = itemView.findViewById(R.id.FORCE_DISADVANTAGES_ID);
    owned = itemView.findViewById(R.id.FORCE_OWN_ID);
    depended = itemView.findViewById(R.id.FORCE_DEPENDED_ID);
    description = itemView.findViewById(R.id.FORCE_DESCRIPTIONS_ID);
    icon = itemView.findViewById(R.id.FORCE_ICON_ID);
    expand = itemView.findViewById(R.id.FORCE_TOGGLER_ID);

    body = itemView.findViewById(R.id.FORCE_ACCORDION_BODY_ID);
    LinearLayout titleBar = v.findViewById(R.id.FORCE_ACCORDION_HEADER_ID);
    titleBar.setOnClickListener(itemToggle);
  }

  public void setTitle(String title) {
    this.title.setText(title);
  }

  public void setType(String type) {
    this.type.setText(type);
  }

  public void setDisadvantage(ModelList disadvantage) {
    this.disadvantage.setText(disadvantage.listToString());
  }

  public void setIcon(String icon) {
    this.icon.setImageURI(Uri.parse(icon));
  }

  public void setEnemies(ModelList enemies) {
    this.enemies.setText(enemies.listToString());
  }

  public void setCooperators(ModelList cooperators) {
    this.cooperators.setText(cooperators.listToString());
  }

  public void setGoals(ModelList goals) {
    this.goals.setText(goals.listToString());
  }

  public void setDescription(String description) {
    this.description.setText(description);
  }

  public void setAdvantage(ModelList advantage) {
    this.advantage.setText(advantage.listToString());
  }

  public void setDepended(ModelList depended) {
    this.depended.setText(depended.listToString());
  }

  public void setOwned(ModelList owned) {
    this.owned.setText(owned.listToString());
  }

  public void setJourneyList(ModelList journeyList) {
    this.journeyList.setText(journeyList.listToString());
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
  }

  View.OnClickListener itemToggle =  new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      toggleVisibility(body);
      if(body.getVisibility() == View.VISIBLE){
        expand.setRotation(180);
      } else {
        expand.setRotation(0);
      }
    }
  };
}
