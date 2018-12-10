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

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author: Fedor.Korotkov
 */
public enum NMETarget {

  // The LogtalkTarget values declared here are the most obvious intention.
  // They may not be correct.  They follow the mapping that the lime command
  // does when invoked for a target OS.  They are used by the classpath generator
  // the create the implicit classpath that the Logtalk compiler uses.
  // This may lead to the wrong source file being presented when debugging,
  // but it's a definite step up from showing the interface file.
  // Note that the LogtalkTarget is only used for IDEA's convenience and is not
  // passed to the compiler (lime) command, while the flags (third and later
  // arguments) are passed to the compiler.

  IOS("iOS", LogtalkTarget.NEKO, "ios", "-simulator"),
  ANDROID("Android", LogtalkTarget.NEKO, "android"),
  WEBOS("webOS", LogtalkTarget.NEKO, "webos"),
  BLACKBERRY("BlackBerry", LogtalkTarget.NEKO, "blackberry"),
  WINDOWS("Windows", LogtalkTarget.NEKO, "windows"),
  MAC("Mac OS", LogtalkTarget.NEKO, "mac"),
  LINUX("Linux", LogtalkTarget.NEKO, "linux"),
  LINUX64("Linux 64", LogtalkTarget.NEKO,  "linux", "-64"),
  FLASH("Flash", LogtalkTarget.FLASH, "flash"),
  HTML5("HTML5", LogtalkTarget.JAVA_SCRIPT, "html5"),
  NEKO("Neko", LogtalkTarget.NEKO, "neko");

  private final String[] flags;
  private final String description;
  private final LogtalkTarget outputTarget;

  NMETarget(String description, LogtalkTarget target, String... flags) {
    this.flags = flags;
    this.description = description;
    this.outputTarget = target;
  }

  public String getTargetFlag() {
    return flags.length > 0 ? flags[0] : "";
  }

  public String[] getFlags() {
    return flags;
  }

  public LogtalkTarget getOutputTarget() { return outputTarget; }

  public static void initCombo(@NotNull DefaultComboBoxModel comboBoxModel) {
    for (NMETarget target : NMETarget.values()) {
      comboBoxModel.insertElementAt(target, 0);
    }
  }

  @Override
  public String toString() {
    return description;
  }
}
