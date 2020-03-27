package com.example.masterhelper.enemy.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import com.example.masterhelper.R;
import com.example.masterhelper.enemy.model.EnemyModel;

public class EnemyAdapter {
  CardView enemyCard;
  ImageView enemyDamageMask;
  ImageView enemyIcon;
  ImageView lastChanged;
  View enemyView;
  EnemyModel enemyData;
  EnemyIconClickListener mOnClickListener;

  public EnemyAdapter(EnemyModel enemyData, LayoutInflater inflater){
    enemyView = inflater.inflate(R.layout.fragment_view_enemy_icon, null);
    enemyCard = enemyView.findViewById(R.id.enemy_card);
    enemyDamageMask = enemyView.findViewById(R.id.damage_mask);
    enemyIcon = enemyView.findViewById(R.id.enemyIcon);
    lastChanged = enemyView.findViewById(R.id.last_changed);

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
