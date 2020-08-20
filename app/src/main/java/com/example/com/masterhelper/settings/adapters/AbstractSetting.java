package com.example.com.masterhelper.settings.adapters;

import com.example.com.masterhelper.core.app.DbHelpers;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.journey.contracts.JourneysContract;

public abstract class AbstractSetting implements ISetting {
  /** класс для работы с базой */
  protected DbHelpers dbHelpers = GlobalApplication.getDbHelpers();
}
