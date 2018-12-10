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
package com.intellij.plugins.logtalk.lang.lexer;

import com.intellij.plugins.logtalk.LogtalkLanguage;
import com.intellij.plugins.logtalk.lang.psi.impl.LogtalkModifierListPsiMixinImpl;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.stubs.EmptyStub;
import com.intellij.psi.stubs.EmptyStubElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class LogtalkMacroClassListElementType extends EmptyStubElementType<PsiModifierList> {

  public LogtalkMacroClassListElementType(@NotNull @NonNls String debugName) {
    super(debugName, LogtalkLanguage.INSTANCE);
  }

  @Override
  public PsiModifierList createPsi(@NotNull EmptyStub stub) {
    return new LogtalkModifierListPsiMixinImpl(stub.getPsi().getNode());
  }
}