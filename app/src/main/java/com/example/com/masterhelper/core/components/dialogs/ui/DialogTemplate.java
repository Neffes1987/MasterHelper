package com.example.com.masterhelper.core.components.dialogs.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.masterhelper.R;

public class DialogTemplate extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dialog_template);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
