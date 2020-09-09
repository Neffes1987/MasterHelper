package com.example.com.masterhelper.enemies.ui;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.listFactory.commonAdapter.item.CommonItem;
import com.example.masterhelper.R;
import com.example.com.masterhelper.enemies.models.EnemyModel;

public class EnemyIconItem extends CommonItem {
  /**  */
  CardView enemyCard;

  /**  */
  ImageView enemyDamageMask;

  /**  */
  ImageView enemyIcon;

  /**  */
  ImageView lastChanged;

  TextView positionNumber;

  TextView enemyTitle;

  TextView enemyHealthValue;

  /**  */
  EnemyModel enemyData;

  public EnemyIconItem() {}

  public void updateHolderByData(DataModel itemData) {
    super.updateHolderByData(itemData);

    enemyCard = itemView.findViewById(R.id.ENEMY_CARD);
    enemyDamageMask = itemView.findViewById(R.id.DAMAGE_MASK);
    enemyIcon = itemView.findViewById(R.id.ENEMY_ICON);
    lastChanged = itemView.findViewById(R.id.LAST_CHANGED);
    lastChanged.setVisibility(View.GONE);
    positionNumber = itemView.findViewById(R.id.POSITION_NUMBER_ID);
    enemyTitle = itemView.findViewById(R.id.ENEMY_NAME_ID);
    enemyHealthValue = itemView.findViewById(R.id.ENEMY_HEALTH_VALUE_ID);

    itemView.setOnClickListener(commonListener);

    enemyData = (EnemyModel)itemData;
    positionNumber.setText(enemyData.getOrdering() + "");
    enemyHealthValue.setText(enemyData.getCurrentHealth() + " / " + enemyData.getTotalHealth());
    enemyTitle.setText(enemyData.getName());
    setDamageMaskHeight();
  }

  public void setCurrentHealth(int currentHealth){
    enemyData.setCurrentHealth(currentHealth);
    setDamageMaskHeight();
  }

  public int getEnemyImageWidth(){
    return enemyIcon.getLayoutParams().width;
  }

  private int getEnemyImageHeight(){
    return enemyIcon.getLayoutParams().height;
  }

  private void setDamageMaskHeight(){

    int enemyCardMaxHeight = getEnemyImageHeight();
    int currentDamageMaskHeight = calculateMaskHeight(enemyData.getCurrentHealth(), enemyData.getTotalHealth(), enemyCardMaxHeight );
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getEnemyImageWidth(), currentDamageMaskHeight);
    layoutParams.gravity = Gravity.BOTTOM;
    enemyDamageMask.setLayoutParams(layoutParams);
  }

  private int calculateMaskHeight (int currentHealth, int totalHealth, int enemyImageHeight){
    return enemyImageHeight - (currentHealth * enemyImageHeight) / totalHealth;
  }
}
