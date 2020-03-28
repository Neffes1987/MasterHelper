package com.example.masterhelper.ui.enemy.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import com.example.masterhelper.R;
import com.example.masterhelper.ui.enemy.model.EnemyModel;

public class EnemyAdapter {
  /**  */
  int enemyViewFragmentLayout = R.layout.fragment_view_enemy_icon;
  View enemyView;

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

  /**  */
  EnemyIconClickListener mOnClickListener;

  public EnemyAdapter(EnemyModel enemyData, LayoutInflater inflater){
    enemyView = inflater.inflate(enemyViewFragmentLayout, null);
    enemyCard = enemyView.findViewById(enemyCardId);
    enemyDamageMask = enemyView.findViewById(enemyDamageMaskId);
    enemyIcon = enemyView.findViewById(enemyIconId);
    lastChanged = enemyView.findViewById(lastChangedId);

    enemyView.setOnClickListener(onEnemyIconClickListener);

    this.enemyData = enemyData;
    setDamageMaskHeight();
    setLastChanged(false);
  }

  public void setLastChanged(boolean newValue) {
    lastChanged.setVisibility(newValue ? View.VISIBLE : View.GONE);
  }

  public void setCurrentHealth(int currentHealth){
    enemyData.setCurrentHealth(currentHealth);
    setDamageMaskHeight();
  }


  public View getEnemyView() {
    return enemyView;
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

  public void setOnClickListener(EnemyIconClickListener l) {
    mOnClickListener = l;
  }

  private View.OnClickListener onEnemyIconClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if(mOnClickListener != null){
        mOnClickListener.onClick(enemyData.getId());
      }
    }
  };

}
