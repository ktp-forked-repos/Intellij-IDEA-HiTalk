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
import com.intellij.plugins.logtalk.lang.psi.LogtalkPackageStatementPsiMixin;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;

/**
 * Created by srikanthg on 10/6/14.
 */
abstract public class LogtalkPackageStatementPsiMixinImpl extends LogtalkStatementPsiMixinImpl implements LogtalkPackageStatementPsiMixin {
  public LogtalkPackageStatementPsiMixinImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public PsiJavaCodeReferenceElement getPackageReference() {
    return findChildByClass(LogtalkReferenceExpression.class);
  }

  @Override
  public String getPackageName() {
    LogtalkReferenceExpression ref = findChildByClass(LogtalkReferenceExpression.class);
    if (null!= ref) {
      return ref.getQualifiedName();
    }
    return "";
  }

  @Override
  public PsiModifierList getAnnotationList() {
    // The Logtalk BNF we're using doesn't allow for any annotations on package statements.
    return null;
  }
}
