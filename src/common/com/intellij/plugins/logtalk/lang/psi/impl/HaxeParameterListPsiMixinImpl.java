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
package com.intellij.plugins.logtalk.lang.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.plugins.haxe.lang.psi.LogtalkParameter;
import com.intellij.plugins.haxe.lang.psi.LogtalkParameterListPsiMixin;
import com.intellij.plugins.haxe.util.UsefulPsiTreeUtil;
import com.intellij.plugins.logtalk.lang.psi.LogtalkParameterListPsiMixin;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * @author: Srikanth.Ganapavarapu
 */
public class LogtalkParameterListPsiMixinImpl extends LogtalkPsiCompositeElementImpl implements LogtalkParameterListPsiMixin {

  private static LogtalkParameter[] EMPTY_ARRAY = new LogtalkParameter[0];

  public LogtalkParameterListPsiMixinImpl(ASTNode node) {
    super(node);
  }

  @NotNull
  @Override
  public LogtalkParameterPsiMixinImpl[] getParameters() {
    LogtalkParameterPsiMixinImpl[] psiParameters = UsefulPsiTreeUtil.getChildrenOfType(this, LogtalkParameterPsiMixinImpl.class, null);
    if (psiParameters == null) {
      psiParameters = new LogtalkParameterPsiMixinImpl[0];
    }
    return psiParameters;
  }

  public List<LogtalkParameter> getParametersAsList() {
    LogtalkParameter[] parameters = UsefulPsiTreeUtil.getChildrenOfType(this, LogtalkParameter.class, null);
    if (parameters == null) {
      parameters = LogtalkParameterListPsiMixinImpl.EMPTY_ARRAY;
    }
    return Arrays.asList(parameters);
  }

  @Override
  public int getParameterIndex(PsiParameter parameter) {
    return PsiImplUtil.getParameterIndex(parameter, this);
  }

  @Override
  public int getParametersCount() {
    LogtalkParameterPsiMixinImpl[] params = getParameters();
    return params == null ? 0 : getParameters().length;
  }
}
