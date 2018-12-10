/*
 * Copyright 2018 Ilya Malanin
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
package com.intellij.plugins.logtalk.model;

import com.intellij.plugins.haxe.lang.psi.LogtalkExternClassDeclaration;
import com.intellij.plugins.haxe.lang.psi.LogtalkExternClassDeclarationBody;
import com.intellij.plugins.haxe.lang.psi.LogtalkPsiCompositeElement;
import com.intellij.plugins.logtalk.lang.psi.LogtalkPsiCompositeElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LogtalkExternClassModel extends LogtalkClassModel {
  public LogtalkExternClassModel(@NotNull LogtalkExternClassDeclaration haxeClass) {
    super(haxeClass);
  }

  @Nullable
  @Override
  public LogtalkPsiCompositeElement getBodyPsi() {
    return getExternBody();
  }

  public LogtalkExternClassDeclaration getExternClass() {
    return (LogtalkExternClassDeclaration)getBasePsi();
  }

  private LogtalkExternClassDeclarationBody getExternBody() {
    return getExternClass().getExternClassDeclarationBody();
  }
}
