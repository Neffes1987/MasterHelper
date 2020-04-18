package com.example.masterhelper.commonAdapter.item;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import com.example.masterhelper.R;
import com.example.masterhelper.commonAdapter.CommonAdapter;
import com.example.masterhelper.models.EnemyModel;

public class EnemyIconItem<Model> extends CommonItem<Model> {
  /**  */
  int enemyCardId = R.id.ENEMY_CARD;
  CardView enemyCard;

  /**  */
  int enemyDamageMaskId = R.id.DAMAGE_MASK;
  ImageView enemyDamageMask;

  /**  */
  int enemyIconId = R.id.ENEMY_ICON;
  ImageView enemyIcon;

  /**  */
  int lastChangedId = R.id.LAST_CHANGED;
  ImageView lastChanged;

  /**  */
  EnemyModel enemyData;

  public EnemyIconItem(View enemyView, CommonAdapter<Model> adapter) {
    super(enemyView, adapter);
    Log.i("TAG", "EnemyIconItem: ");
    enemyCard = enemyView.findViewById(enemyCardId);
    enemyDamageMask = enemyView.findViewById(enemyDamageMaskId);
    enemyIcon = enemyView.findViewById(enemyIconId);
    lastChanged = enemyView.findViewById(lastChangedId);
    enemyView.setOnClickListener(commonListener);
  }

  @Override
  public void updateHolderByData(Model itemData, int position) {
    enemyData = (EnemyModel)itemData;
    setPosition(position);
    setDamageMaskHeight();
  }

  //public void setLastChanged() {
    //toggleVisibility(lastChanged);
  //}

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
    int currentWidth = enemyDamageMask.getLayoutParams().width;
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(currentWidth, currentDamageMaskHeight);
    layoutParams.gravity = Gravity.BOTTOM;
    enemyDamageMask.setLayoutParams(layoutParams);
  }

  private int calculateMaskHeight (int currentHealth, int totalHealth, int enemyImageHeight){
    return enemyImageHeight - (currentHealth * enemyImageHeight) / totalHealth;
  }
}
