/*
 * Copyright 2000-2013 JetBrains s.r.o.
 * Copyright 2014-2018 AS3Boyan
 * Copyright 2014-2014 Elias Ku
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.plugins.logtalk.config;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.NonDefaultProjectConfigurable;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.plugins.haxe.LogtalkBundle;
import com.intellij.plugins.haxe.config.ui.LogtalkSettingsForm;
import com.intellij.plugins.haxe.util.LogtalkUtil;
import com.intellij.plugins.logtalk.LogtalkBundle;
import com.intellij.plugins.logtalk.util.LogtalkUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author: Fedor.Korotkov
 */
public class LogtalkSettingsConfigurable implements SearchableConfigurable, NonDefaultProjectConfigurable {
  private LogtalkSettingsForm mySettingsPane;
  private final Project myProject;

  public LogtalkSettingsConfigurable(Project project) {
    myProject = project;
  }

  public String getDisplayName() {
    return LogtalkBundle.message("logtalk.settings.name");
  }

  @NotNull
  public String getId() {
    return "logtalk.settings";
  }

  public String getHelpTopic() {
    return null;
  }

  public JComponent createComponent() {
    if (mySettingsPane == null) {
      mySettingsPane = new LogtalkSettingsForm();
    }
    reset();
    return mySettingsPane.getPanel();
  }

  public boolean isModified() {
    return mySettingsPane != null && mySettingsPane.isModified(getSettings());
  }

  public void apply() throws ConfigurationException {
    if (mySettingsPane != null) {
      final boolean modified = isModified();
      mySettingsPane.applyEditorTo(getSettings());
      if (modified) {
        LogtalkUtil.reparseProjectFiles(myProject);
      }
    }
  }

  public void reset() {
    if (mySettingsPane != null) {
      mySettingsPane.resetEditorFrom(getSettings());
    }
  }

  private LogtalkProjectSettings getSettings() {
    return LogtalkProjectSettings.getInstance(myProject);
  }

  public void disposeUIResources() {
    mySettingsPane = null;
  }

  public Runnable enableSearch(String option) {
    return null;
  }
}
