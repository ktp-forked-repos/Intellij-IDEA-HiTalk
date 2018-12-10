/*
 * Copyright 2017-2017 Ilya Malanin
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
import com.intellij.plugins.haxe.lang.psi.LogtalkReferenceExpression;
import com.intellij.plugins.haxe.lang.psi.LogtalkUsingStatement;
import com.intellij.plugins.haxe.lang.psi.LogtalkVisitor;
import com.intellij.plugins.haxe.model.LogtalkUsingModel;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LogtalkUsingStatementPsiMixinImpl extends LogtalkStatementPsiMixinImpl implements LogtalkUsingStatement {
  public LogtalkUsingStatementPsiMixinImpl(ASTNode node) {
    super(node);
  }

  @NotNull
  @Override
  public LogtalkUsingModel getModel() {
    return new LogtalkUsingModel(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LogtalkVisitor) {
      ((LogtalkVisitor)visitor).visitUsingStatement(this);
    }
    else {
      super.accept(visitor);
    }
  }

  @Override
  @Nullable
  public LogtalkReferenceExpression getReferenceExpression() {
    return findChildByClass(LogtalkReferenceExpression.class);
  }
}
