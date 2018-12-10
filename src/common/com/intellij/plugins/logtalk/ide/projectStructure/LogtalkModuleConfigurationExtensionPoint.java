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
package com.intellij.plugins.logtalk.ide.projectStructure;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.options.UnnamedConfigurable;
import com.intellij.plugins.logtalk.ide.module.LogtalkModuleSettings;
import org.jetbrains.annotations.NotNull;

public class LogtalkModuleConfigurationExtensionPoint {
  ExtensionPointName<LogtalkModuleConfigurationExtensionPoint> EP_NAME =
    ExtensionPointName.create("com.intellij.plugins.logtalk.module.config");

  UnnamedConfigurable createConfigurable(@NotNull LogtalkModuleSettings settings);
}
}
