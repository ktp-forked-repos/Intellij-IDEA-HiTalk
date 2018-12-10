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
package com.intellij.plugins.logtalk.ide.actions;

import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.plugins.logtalk.LogtalkBundle;
import com.intellij.plugins.logtalk.ide.LogtalkFileTemplateUtil;
import com.intellij.plugins.logtalk.ide.module.LogtalkModuleType;
import com.intellij.psi.PsiDirectory;

/**
 * @author: Fedor.Korotkov
 */
public class CreateNMMLFileAction extends CreateFileFromTemplateAction implements DumbAware {
  public CreateNMMLFileAction() {
    super(LogtalkBundle.message("create.nmml.file.action"), LogtalkBundle.message("create.nmml.file.action.description"),
          icons.LogtalkIcons.Nmml_16);
  }

  @Override
  protected boolean isAvailable(DataContext dataContext) {
    final Module module = LangDataKeys.MODULE.getData(dataContext);
    return super.isAvailable(dataContext) && module != null && ModuleType.get(module) == LogtalkModuleType.getInstance();
  }

  @Override
  protected void buildDialog(Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
    builder.setTitle(LogtalkBundle.message("create.nmml.file.action"));
    for (FileTemplate fileTemplate : LogtalkFileTemplateUtil.getNMMLTemplates()) {
      final String templateName = fileTemplate.getName();
      final String shortName = LogtalkFileTemplateUtil.getTemplateShortName(templateName);
      builder.addKind(shortName, icons.LogtalkIcons.Nmml_16, templateName);
    }
  }

  @Override
  protected String getActionName(PsiDirectory directory, String newName, String templateName) {
    return LogtalkBundle.message("create.nmml.file.action");
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof CreateNMMLFileAction;
  }
}
