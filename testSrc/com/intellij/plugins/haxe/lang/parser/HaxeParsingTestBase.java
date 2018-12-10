/*
 * Copyright 2000-2013 JetBrains s.r.o.
 * Copyright 2014-2014 AS3Boyan
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
package com.intellij.plugins.haxe.lang.parser;

import com.intellij.lang.LanguageASTFactory;
import com.intellij.plugins.haxe.LogtalkFileType;
import com.intellij.plugins.haxe.LogtalkLanguage;
import com.intellij.plugins.haxe.util.LogtalkTestUtils;
import com.intellij.testFramework.ParsingTestCase;

abstract public class LogtalkParsingTestBase extends ParsingTestCase {
  public LogtalkParsingTestBase(String... path) {
    super(getPath(path), LogtalkFileType.DEFAULT_EXTENSION, new LogtalkParserDefinition());
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    addExplicitExtension(LanguageASTFactory.INSTANCE, LogtalkLanguage.INSTANCE, new LogtalkAstFactory());
  }

  private static String getPath(String... args) {
    final StringBuilder result = new StringBuilder();
    for (String folder : args) {
      if (result.length() > 0) {
        result.append("/");
      }
      result.append(folder);
    }
    return result.toString();
  }

  @Override
  protected String getTestDataPath() {
    return LogtalkTestUtils.BASE_TEST_DATA_PATH;
  }

  @Override
  protected boolean skipSpaces() {
    return true;
  }
}
