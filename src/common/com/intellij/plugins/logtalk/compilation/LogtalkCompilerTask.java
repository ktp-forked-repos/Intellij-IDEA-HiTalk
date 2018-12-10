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
package com.intellij.plugins.logtalk.compilation;

import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileTask;
import com.intellij.openapi.compiler.FileProcessingCompiler;

/**
 * Created by as3boyan on 03.08.14.
 */
public class LogtalkCompilerTask implements CompileTask {

  static LogtalkCompiler haxeCompiler;

  @Override
  public boolean execute(CompileContext context) {
    if (haxeCompiler == null) {
      haxeCompiler = new LogtalkCompiler();
    }
    
    FileProcessingCompiler.ProcessingItem[] processingItems = haxeCompiler.getProcessingItems(context);
    haxeCompiler.process(context, processingItems);
    return true;
  }
}
