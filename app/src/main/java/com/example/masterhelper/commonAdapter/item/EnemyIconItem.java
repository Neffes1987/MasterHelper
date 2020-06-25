package com.example.masterhelper.commonAdapter.item;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.example.masterhelper.R;
import com.example.masterhelper.commonAdapter.CommonAdapter;
import com.example.masterhelper.models.EnemyModel;

public class EnemyIconItem<Model> extends CommonItem<Model> {
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

  public EnemyIconItem(View enemyView, CommonAdapter<Model> adapter) {
    super(enemyView, adapter);

    enemyCard = enemyView.findViewById(R.id.ENEMY_CARD);
    enemyDamageMask = enemyView.findViewById(R.id.DAMAGE_MASK);
    enemyIcon = enemyView.findViewById(R.id.ENEMY_ICON);
    lastChanged = enemyView.findViewById(R.id.LAST_CHANGED);
    lastChanged.setVisibility(View.GONE);
    positionNumber = enemyView.findViewById(R.id.POSITION_NUMBER_ID);
    enemyTitle = enemyView.findViewById(R.id.ENEMY_NAME_ID);
    enemyHealthValue = enemyView.findViewById(R.id.ENEMY_HEALTH_VALUE_ID);

    enemyView.setOnClickListener(commonListener);
  }

  @Override
  public void updateHolderByData(Model itemData, int position) {
    enemyData = (EnemyModel)itemData;
    setPosition(position);
    int pos = position + 1;
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
