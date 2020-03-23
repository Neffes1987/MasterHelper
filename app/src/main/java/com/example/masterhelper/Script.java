package com.example.masterhelper;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.masterhelper.enemy.adapter.EnemyAdapter;
import com.example.masterhelper.enemy.model.EnemyModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Script extends AppCompatActivity {
  private int MIN_MARGIN = 200;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_view_script);
    TableLayout tableLayout = findViewById(R.id.enemies_grid);
    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    EnemyModel enemy1 = new EnemyModel("", 20, 10, 5, 1);
    EnemyAdapter enemyAdapter1 = new EnemyAdapter(enemy1, inflater);
    EnemyModel enemy2 = new EnemyModel("", 20, 20, 5, 2);
    EnemyAdapter enemyAdapter2 = new EnemyAdapter(enemy2, inflater);
    EnemyModel enemy3 = new EnemyModel("", 20, 5, 5, 3);
    EnemyAdapter enemyAdapter3 = new EnemyAdapter(enemy3, inflater);
    EnemyModel enemy4 = new EnemyModel("", 20, 15, 5, 4);
    EnemyAdapter enemyAdapter4 = new EnemyAdapter(enemy4, inflater);
    EnemyModel enemy5 = new EnemyModel("", 20, 15, 5, 4);
    EnemyAdapter enemyAdapter5 = new EnemyAdapter(enemy5, inflater);

    Set<EnemyAdapter> enemyAdapters = new HashSet<EnemyAdapter>();
    enemyAdapters.add(enemyAdapter1);
    enemyAdapters.add(enemyAdapter2);
    enemyAdapters.add(enemyAdapter3);
    enemyAdapters.add(enemyAdapter4);
    enemyAdapters.add(enemyAdapter5);
    divideEnemiesByRows(enemyAdapters, tableLayout);
  }

  private int getTableWidth(){
    DisplayMetrics metrics = this.getResources().getDisplayMetrics();
    int width = metrics.widthPixels;
    return width;
  }

  private void addRowToTable(TableLayout tableLayout, TableRow tableRow){
    tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
  }

  private TableRow createTableRow(){
    TableRow tableRow = new TableRow(this);
    tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
    tableRow.setGravity(Gravity.LEFT);
    return tableRow;
  }

  private void divideEnemiesByRows(Set<EnemyAdapter> enemies, TableLayout tableLayout){
    int tableWidth = getTableWidth() - MIN_MARGIN;
    Iterator<EnemyAdapter> EnemiesIterator = enemies.iterator();
    int currentWidth = 0;
    TableRow currentRow = createTableRow();

    while (EnemiesIterator.hasNext()){
      //получаем данные с карточки и считываем ширину картинки
      EnemyAdapter enemy = EnemiesIterator.next();
      int enemyImageWidth = enemy.getEnemyImageWidth();

      if(enemyImageWidth + currentWidth < tableWidth){
        //складываем все в одну строку пока помещается на экране
        currentRow.addView(enemy.getEnemyView());
        currentWidth += enemyImageWidth;
      } else {
        // добавляем заполненую строку в таблицу
        addRowToTable(tableLayout, currentRow);

        // устанавливаем текущий размер строки равный размеру текущего итерируемого элемента
        currentWidth = enemyImageWidth;
        // создаем новую строку и записываем в нее текущую карточку противника
        currentRow = createTableRow();
        currentRow.addView(enemy.getEnemyView());
      }
    }
    // добавляем последнюю строку в таблицу
    addRowToTable(tableLayout, currentRow);
   }

}
