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
package com.intellij.plugins.logtalk.util;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.plugins.logtalk.config.sdk.LogtalkSdkAdditionalDataBase;
import com.intellij.plugins.logtalk.ide.HXMLCompletionItem;
import com.intellij.plugins.logtalk.logtalklib.LogtalklibCache;
import com.intellij.plugins.logtalk.logtalklib.LogtalklibCommandUtils;
import com.intellij.plugins.haxe.ide.HXMLCompletionItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by as3boyan on 15.11.14.
 */
public class LogtalkHelpCache {
  static LogtalkHelpCache instance = null;
  public static final Pattern META_TAG_PATTERN = Pattern.compile("@:([^\\r\\n\\t\\s]+)[^:]+:[\\t\\s]+([^\\r\\n]+)");
  public static final Pattern DEFINE_PATTERN = Pattern.compile("([^\\r\\n\\t\\s]+)[^:]+:[\\t\\s]([^\\r\\n]+)");

  public List<HXMLCompletionItem> getMetaTags() {
    return metaTags;
  }
  public List<HXMLCompletionItem> getDefines() {
    return defines;
  }

  private static List<HXMLCompletionItem> metaTags;
  private static List<HXMLCompletionItem> defines;

  public LogtalkHelpCache() {
    if (ApplicationManager.getApplication().isUnitTestMode()) {
      metaTags = Collections.EMPTY_LIST;
      defines = Collections.EMPTY_LIST;
      return;
    }

    load();
  }

  public static LogtalkHelpCache getInstance() {
    if (instance == null) {
      instance = new LogtalkHelpCache();
    }

    return instance;
  }

  private void load() {
    ArrayList<String> commandLineArguments = new ArrayList<String>();
    Module module = LogtalklibCache.getLogtalkModule();
    String haxePath = LogtalkHelpUtil.getLogtalkPath(module);
    LogtalkSdkAdditionalDataBase haxeSdkData = LogtalkSdkUtilBase.getSdkData(module);

    commandLineArguments.add(haxePath);
    commandLineArguments.add("--help-metas");

    List<String> strings = LogtalklibCommandUtils.getProcessStdout(commandLineArguments, haxeSdkData);

    metaTags = new ArrayList<HXMLCompletionItem>();

    for (int i = 0, size = strings.size(); i < size; i++) {
      String string = strings.get(i);
      Matcher matcher = META_TAG_PATTERN.matcher(string);

      if (matcher.find()) {
        metaTags.add(new HXMLCompletionItem(matcher.group(1), matcher.group(2)));
      }
    }

    commandLineArguments.clear();
    commandLineArguments.add(haxePath);
    commandLineArguments.add("--help-defines");

    strings = LogtalklibCommandUtils.getProcessStdout(commandLineArguments, haxeSdkData);

    defines = new ArrayList<HXMLCompletionItem>();

    for (int i = 0; i < strings.size(); i++) {
      String string = strings.get(i);
      Matcher matcher = DEFINE_PATTERN.matcher(string);

      if (matcher.find()) {
        defines.add(new HXMLCompletionItem(matcher.group(1), matcher.group(2)));
      }
    }
  }
}
