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
package com.intellij.plugins.logtalk.ide.refactoring.move;

import com.intellij.openapi.roots.impl.DirectoryIndex;
import com.intellij.openapi.util.Key;
import com.intellij.plugins.haxe.lang.psi.LogtalkFile;
import com.intellij.plugins.haxe.lang.psi.LogtalkPackageStatement;
import com.intellij.plugins.haxe.model.LogtalkFileModel;
import com.intellij.plugins.haxe.util.LogtalkElementGenerator;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.move.moveFilesOrDirectories.MoveFileHandler;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.IncorrectOperationException;

import java.util.List;
import java.util.Map;

/**
 * @author: Fedor.Korotkov
 */
public class LogtalkFileMoveHandler extends MoveFileHandler {
  public static final Key<String> destinationPackageKey = Key.create("logtalk.destination.package.key");

  @Override
  public boolean canProcessElement(PsiFile element) {
    return element instanceof LogtalkFile;
  }

  @Override
  public void prepareMovedFile(PsiFile file, PsiDirectory moveDestination, Map<PsiElement, PsiElement> oldToNewMap) {
    file.putUserData(destinationPackageKey, DirectoryIndex.getInstance(file.getProject()).getPackageName(moveDestination.getVirtualFile()));
  }

  @Override
  public List<UsageInfo> findUsages(PsiFile psiFile, PsiDirectory newParent, boolean searchInComments, boolean searchInNonJavaFiles) {
    return null;
  }

  @Override
  public void retargetUsages(List<UsageInfo> usageInfos, Map<PsiElement, PsiElement> oldToNewMap) {
  }

  @Override
  public void updateMovedFile(PsiFile file) throws IncorrectOperationException {
    final LogtalkPackageStatement newPackageStatement =
      LogtalkElementGenerator.createPackageStatementFromPath(file.getProject(), file.getUserData(destinationPackageKey));
    if (newPackageStatement != null) {
      LogtalkFileModel.fromElement(file).replaceOrCreatePackageStatement(newPackageStatement);
    }
  }
}
