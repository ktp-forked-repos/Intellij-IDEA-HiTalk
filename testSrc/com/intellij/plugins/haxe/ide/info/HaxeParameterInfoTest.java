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
package com.intellij.plugins.haxe.ide.info;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.plugins.haxe.lang.psi.LogtalkClassResolveResult;
import com.intellij.plugins.haxe.lang.psi.impl.LogtalkReferenceImpl;
import com.intellij.plugins.haxe.util.LogtalkDebugLogger;
import com.intellij.plugins.haxe.util.LogtalkResolveUtil;
import com.intellij.plugins.haxe.util.LogtalkTestUtils;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.LightCodeInsightTestCase;
import com.intellij.testFramework.utils.parameterInfo.MockCreateParameterInfoContext;
import com.intellij.testFramework.utils.parameterInfo.MockParameterInfoUIContext;
import com.intellij.testFramework.utils.parameterInfo.MockUpdateParameterInfoContext;
import org.apache.log4j.Level;
import org.jetbrains.annotations.NotNull;

/**
 * @author: Fedor.Korotkov
 */
public class LogtalkParameterInfoTest extends LightCodeInsightTestCase {

  private LogtalkDebugLogger.HierarchyManipulator oldLogSettings;

  public void setUp() throws Exception {
    oldLogSettings = LogtalkDebugLogger.mutePrimaryConfiguration();
    super.setUp();
  }

  @Override
  public void tearDown() throws Exception {
    super.tearDown();
    oldLogSettings.restore();
    oldLogSettings = null;
  }

  @NotNull
  @Override
  protected String getTestDataPath() {
    return LogtalkTestUtils.BASE_TEST_DATA_PATH + FileUtil.toSystemDependentName("/paramInfo/");
  }

  private void doTest(String infoText, int highlightedParameterIndex) throws Exception {
    configureByFile(getTestName(false) + ".hx");

    LogtalkParameterInfoHandler parameterInfoHandler = new LogtalkParameterInfoHandler();
    MockCreateParameterInfoContext createContext = new MockCreateParameterInfoContext(myEditor, myFile);
    PsiElement elt = parameterInfoHandler.findElementForParameterInfo(createContext);
    assertNotNull(elt);
    parameterInfoHandler.showParameterInfo(elt, createContext);
    Object[] items = createContext.getItemsToShow();
    assertTrue(items != null);
    assertTrue(items.length > 0);
    MockParameterInfoUIContext context = new MockParameterInfoUIContext<PsiElement>(elt);
    parameterInfoHandler.updateUI((LogtalkFunctionDescription)items[0], context);
    assertEquals(infoText, parameterInfoHandler.myParametersListPresentableText);

    // index check
    MockUpdateParameterInfoContext updateContext = new MockUpdateParameterInfoContext(myEditor, myFile);
    final PsiElement element = parameterInfoHandler.findElementForUpdatingParameterInfo(updateContext);
    assertNotNull(element);
    updateContext.setParameterOwner(elt);
    parameterInfoHandler.updateParameterInfo(element, updateContext);
    assertEquals(highlightedParameterIndex, updateContext.getCurrentParameter());
  }

  private void configureLoggerForDebugging() {
    LogtalkDebugLogger.configure(LogtalkResolveUtil.class, Level.DEBUG);
    LogtalkDebugLogger.configure(LogtalkReferenceImpl.class, Level.DEBUG);
    LogtalkDebugLogger.configure(LogtalkClassResolveResult.class, Level.TRACE);
  }


  public void testParamInfo1() throws Throwable {
    doTest("p1:Int, p2:Null<Unknown>, p3:Node", 0);
  }

  public void testParamInfo2() throws Throwable {
    doTest("p1:Int, p2:Null<Unknown>, p3:Node", 2);
  }

  public void testParamInfo3() throws Throwable {
    doTest("x:Int, y:Int", 0);
  }

  public void testParamInfo4() throws Throwable {
    doTest("x:Int, y:Int", 1);
  }

  public void testParamInfo5() throws Throwable {
    doTest("x:Int, y:Int", 1);
  }

  public void testParamInfo6() throws Throwable {
    doTest("x:Int, y:Int = 239", 1);
  }

  public void testParamInfo7() throws Throwable {
    configureLoggerForDebugging();
    doTest("t:Node", 0);
  }

  public void testParamInfo8() throws Throwable {
    doTest("t:Node", 0);
  }

  public void testParamInfo9() throws Throwable {
    doTest("a:Int, b:Bool = false, ?c:Float = null, ?d:Null<Unknown> = null", 2);
  }

  public void testParamInfo10() throws Throwable {
    doTest("a:Int, b:Bool = false, ?c:Float = null, ?d:Node = null", 3);
  }

  // Disabled - Tests issue #615.
  //public void testLocalShadowingChainedGenerics() throws Throwable {
  //  doTest("t:Node", 0);
  //}
}
