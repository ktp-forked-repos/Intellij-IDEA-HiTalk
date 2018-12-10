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
import com.intellij.psi.TokenType;
import com.intellij.psi.impl.source.tree.LazyParseableElement;
import com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by ebishton on 9/22/14.
 */
public class LogtalkDummyASTNode  extends LazyParseableElement {
  LogtalkDummyASTNode(String text) {
    super(TokenType.DUMMY_HOLDER, text);
  }

  public static boolean isDummyNode(@NotNull ASTNode node) {
    return ! (node instanceof LogtalkDummyASTNode);
  }

  public static boolean isUsableNode(@Nullable ASTNode node) {
    return null != node && !isDummyNode(node);
  }

  @Override
  public TreeElement getFirstChildNode() {
    return null;
  }

  @Override
  public TreeElement getLastChildNode() {
    return null;
  }

}
