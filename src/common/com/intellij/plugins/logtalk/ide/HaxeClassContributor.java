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
package com.intellij.plugins.logtalk.ide;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.plugins.haxe.ide.index.LogtalkComponentIndex;
import com.intellij.plugins.haxe.lang.psi.LogtalkComponent;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author: Fedor.Korotkov
 */
public class LogtalkClassContributor implements ChooseByNameContributor {
  @NotNull
  @Override
  public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
    final GlobalSearchScope scope = includeNonProjectItems ? GlobalSearchScope.allScope(project) : GlobalSearchScope.projectScope(project);
    final Collection<LogtalkComponent> result = LogtalkComponentIndex.getItemsByName(name, project, scope);
    return result.toArray(new NavigationItem[result.size()]);
  }

  @NotNull
  @Override
  public String[] getNames(Project project, boolean includeNonProjectItems) {
    final Collection<String> result = LogtalkComponentIndex.getNames(project);
    return ArrayUtil.toStringArray(result);
  }
}
