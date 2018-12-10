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
package com.intellij.plugins.logtalk.ide.index;

import com.intellij.openapi.util.Condition;
import com.intellij.plugins.logtalk.LogtalkComponentType;
import com.intellij.plugins.logtalk.lang.psi.LogtalkClass;
import com.intellij.plugins.logtalk.lang.psi.impl.AbstractLogtalkTypeDefImpl;
import com.intellij.plugins.logtalk.util.LogtalkResolveUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author: Fedor.Korotkov
 */
public class LogtalkInheritanceIndex extends FileBasedIndexExtension<String, List<LogtalkClassInfo>> {
  public static final ID<String, List<LogtalkClassInfo>> LOGTALK_INHERITANCE_INDEX = ID.create("LogtalkInheritanceIndex");
  private static final int INDEX_VERSION = LogtalkIndexUtil.BASE_INDEX_VERSION + 7;
  private final DataIndexer<String, List<LogtalkClassInfo>, FileContent> myIndexer = new MyDataIndexer();
  private final DataExternalizer<List<LogtalkClassInfo>> myExternalizer = new LogtalkClassInfoListExternalizer();

  @NotNull
  @Override
  public ID<String, List<LogtalkClassInfo>> getName() {
    return LOGTALK_INHERITANCE_INDEX;
  }

  @Override
  public int getVersion() {
    return INDEX_VERSION;
  }

  @Override
  public boolean dependsOnFileContent() {
    return true;
  }

  @Override
  public KeyDescriptor<String> getKeyDescriptor() {
    return new EnumeratorStringDescriptor();
  }

  @Override
  public DataExternalizer<List<LogtalkClassInfo>> getValueExternalizer() {
    return myExternalizer;
  }

  @Override
  public FileBasedIndex.InputFilter getInputFilter() {
    return LogtalkSdkInputFilter.INSTANCE;
  }

  @NotNull
  @Override
  public DataIndexer<String, List<LogtalkClassInfo>, FileContent> getIndexer() {
    return myIndexer;
  }

  private static class MyDataIndexer implements DataIndexer<String, List<LogtalkClassInfo>, FileContent> {
    @Override
    @NotNull
    public Map<String, List<LogtalkClassInfo>> map(final FileContent inputData) {
      final PsiFile psiFile = inputData.getPsiFile();
      final PsiElement[] fileChildren = psiFile.getChildren();
      final List<LogtalkClass> classes = ContainerUtil.map(ContainerUtil.filter(fileChildren, new Condition<PsiElement>() {
        @Override
        public boolean value(PsiElement element) {
          return element instanceof LogtalkClass && !(element instanceof AbstractLogtalkTypeDefImpl);
        }
      }), element -> (LogtalkClass)element);
      if (classes.isEmpty()) {
        return Collections.emptyMap();
      }
      final Map<String, List<LogtalkClassInfo>> result = new THashMap<String, List<LogtalkClassInfo>>(classes.size());
      final Map<String, String> qNameCache = new THashMap<String, String>();
      for (LogtalkClass logtalkClass : classes) {
        final LogtalkClassInfo value = new LogtalkClassInfo(logtalkClass.getQualifiedName(), LogtalkComponentType.typeOf(logtalkClass));
        logtalkClass.getLogtalkExtendsList().stream().filter(Objects::nonNull).<String>map(logtalkType -> logtalkType.getText())
          .map(classNameCandidate -> classNameCandidate.indexOf('.') != -1 ?
                                     classNameCandidate :
                                     getQNameAndCache(qNameCache, fileChildren, classNameCandidate))
          .forEachOrdered(key -> put(result, key, value));
        for (LogtalkType logtalkType : logtalkClass.getLogtalkImplementsList()) {
          if (logtalkType != null) {
            String classNameCandidate = logtalkType.getText();
            String key = classNameCandidate.indexOf('.') != -1 ?
                         classNameCandidate :
                         getQNameAndCache(qNameCache, fileChildren, classNameCandidate);
            put(result, key, value);
          }
        }
      }
      
      return result;
    }

    private static String getQNameAndCache(Map<String, String> qNameCache, PsiElement[] fileChildren, String classNameCandidate) {
      String result = qNameCache.get(classNameCandidate);
      if (result == null) {
        result = LogtalkResolveUtil.getQName(fileChildren, classNameCandidate, true);
        qNameCache.put(classNameCandidate, result);
      }
      return result;
    }

    private static void put(Map<String, List<LogtalkClassInfo>> map, String key, LogtalkClassInfo value) {
      List<LogtalkClassInfo> infos = map.get(key);
      if (infos == null) {
        infos = new ArrayList<>();
        map.put(key, infos);
      }
      infos.add(value);
    }
  }
}
