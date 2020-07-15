package com.example.com.masterhelper.settings.adapters;

import com.example.com.masterhelper.core.appconfig.DbHelpers;
import com.example.com.masterhelper.core.appconfig.GlobalApplication;

public abstract class AbstractSetting implements ISetting {
  /** класс для работы с базой */
  protected DbHelpers dbHelpers = GlobalApplication.getDbHelpers();
}
