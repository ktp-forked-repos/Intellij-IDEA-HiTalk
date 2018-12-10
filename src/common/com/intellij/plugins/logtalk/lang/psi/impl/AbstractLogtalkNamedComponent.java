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

import com.intellij.find.findUsages.PsiElement2UsageTargetAdapter;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.Pair;
import com.intellij.plugins.logtalk.LogtalkComponentType;
import com.intellij.plugins.logtalk.lang.psi.LogtalkClass;
import com.intellij.plugins.logtalk.lang.psi.LogtalkNamedComponent;
import com.intellij.plugins.logtalk.lang.psi.LogtalkPsiCompositeElement;
import com.intellij.plugins.logtalk.model.type.ResultHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.SourceTreeToPsiMap;
import com.intellij.psi.impl.source.tree.ChildRole;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Set;

import static com.intellij.util.containers.ContainerUtil.getFirstItem;

/**
 * @author: Fedor.Korotkov
 */
abstract public class AbstractLogtalkNamedComponent extends LogtalkMetaContainerElementImpl
  implements LogtalkNamedComponent, PsiNamedElement {

  public ResultHolder _cachedType;
  public long _cachedTypeStamp;

  public AbstractLogtalkNamedComponent(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  @Nullable
  @NonNls
  public String getName() {
    final LogtalkComponentName name = getComponentName();
    if (name != null) {
      return name.getText();
    }
    return super.getName();
  }

  @Override
  public String getText() {
    return super.getText();
  }

  @Override
  public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
    final LogtalkComponentName componentName = getComponentName();
    if (componentName != null) {
      componentName.setName(name);
    }
    return this;
  }

  @Override
  public Icon getIcon(int flags) {
    final LogtalkComponentType type = LogtalkComponentType.typeOf(this);
    return type == null ? null : type.getIcon();
  }

  @Override
  public ItemPresentation getPresentation() {
    return new ItemPresentation() {
      @Override
      public String getPresentableText() {
        final StringBuilder result = new StringBuilder();
        LogtalkMemberModel member = LogtalkMemberModel.fromPsi(AbstractLogtalkNamedComponent.this);

        if (member == null) {
          result.append(AbstractLogtalkNamedComponent.this.getName());
        }
        else {
          if (isFindUsageRequest()) {
            LogtalkClassModel klass = member.getDeclaringClass();
            if (null != klass) {
              result.append(klass.getName());
              result.append('.');
            }
          }

          if (member instanceof LogtalkEnumValueModel) {
            return member.getPresentableText(null);
          }

          result.append(member.getName());

          if (member instanceof LogtalkMethodModel) {
            final String parameterList = LogtalkPresentableUtil.getPresentableParameterList(member.getNamedComponentPsi());
            result.append("(").append(parameterList).append(")");
          }

          final ResultHolder resultType = member.getResultType();
          if (resultType != null) {
            result.append(":");
            result.append(member.getResultType().toString());
          }
        }

        return result.toString();
      }

      @Override
      public String getLocationString() {
        LogtalkClass haxeClass = AbstractLogtalkNamedComponent.this instanceof LogtalkClass
                              ? (LogtalkClass)AbstractLogtalkNamedComponent.this
                              : PsiTreeUtil.getParentOfType(AbstractLogtalkNamedComponent.this, LogtalkClass.class);
        if (haxeClass instanceof LogtalkAnonymousType) {
          final LogtalkTypedefDeclaration typedefDeclaration = PsiTreeUtil.getParentOfType(haxeClass, LogtalkTypedefDeclaration.class);
          if (typedefDeclaration != null) {
            haxeClass = typedefDeclaration;
          }
        }
        if (haxeClass == null) {
          return "";
        }
        final Pair<String, String> qName = LogtalkResolveUtil.splitQName(haxeClass.getQualifiedName());
        if (haxeClass == AbstractLogtalkNamedComponent.this) {
          return qName.getFirst();
        }
        return haxeClass.getQualifiedName();
      }

      @Override
      public Icon getIcon(boolean open) {
        return AbstractLogtalkNamedComponent.this.getIcon(0);
      }


      private boolean isFindUsageRequest() {
        // HACK: Checking the stack is a bad answer for this, but we don't have a good way to
        // determine whether this particular request is from findUsages because all FindUsages queries
        // run on background threads, and they could be running at the same time as another access.
        // (AND, we can't change IDEA's shipping products on which this must run...)
        return LogtalkDebugUtil.appearsOnStack(PsiElement2UsageTargetAdapter.class);
      }

    };
  }

  @Override
  public LogtalkNamedComponent getTypeComponent() {
    final LogtalkTypeTag typeTag = PsiTreeUtil.getChildOfType(getParent(), LogtalkTypeTag.class);
    final LogtalkTypeOrAnonymous typeOrAnonymous = typeTag != null ? getFirstItem(typeTag.getTypeOrAnonymousList()) : null;
    final LogtalkType type = typeOrAnonymous != null ? typeOrAnonymous.getType() : null;
    final PsiReference reference = type != null ? type.getReference() : null;
    if (reference != null) {
      final PsiElement result = reference.resolve();
      if (result instanceof LogtalkNamedComponent) {
        return (LogtalkNamedComponent)result;
      }
    }
    return null;
  }

  @Override
  public boolean isPublic() {
    if (PsiTreeUtil.getParentOfType(this, LogtalkExternClassDeclaration.class) != null) {
      return true;
    }
    if (PsiTreeUtil.getParentOfType(this, LogtalkInterfaceDeclaration.class, LogtalkEnumDeclaration.class) != null) {
      return true;
    }
    if (PsiTreeUtil.getParentOfType(this, LogtalkAnonymousType.class) != null) {
      return true;
    }
    final PsiElement parent = getParent();
    return hasPublicAccessor(this) || (parent instanceof LogtalkPsiCompositeElement && hasPublicAccessor((LogtalkPsiCompositeElement)parent));
  }

  private static boolean hasPublicAccessor(LogtalkPsiCompositeElement element) {
    // do not change the order of these if-statements
    if (UsefulPsiTreeUtil.getChildOfType(element, LogtalkTokenTypes.KPRIVATE) != null) {
      return false; // private
    }
    if (UsefulPsiTreeUtil.getChildOfType(element, LogtalkTokenTypes.KPUBLIC) != null) {
      return true; // public
    }

    final LogtalkDeclarationAttribute[] declarationAttributeList = PsiTreeUtil.getChildrenOfType(element, LogtalkDeclarationAttribute.class);
    if (declarationAttributeList != null) {
      final Set<IElementType> declarationTypes = LogtalkResolveUtil.getDeclarationTypes((declarationAttributeList));
      // do not change the order of these if-statements
      if (declarationTypes.contains(LogtalkTokenTypes.KPRIVATE)) {
        return false; // private
      }
      if (declarationTypes.contains(LogtalkTokenTypes.KPUBLIC)) {
        return true; // public
      }
    }

    return false; // <default>: private
  }

  @Override
  public boolean isStatic() {
    AbstractLogtalkNamedComponent element = this;

    final LogtalkDeclarationAttribute[] declarationAttributeList = PsiTreeUtil.getChildrenOfType(element, LogtalkDeclarationAttribute.class);
    return LogtalkResolveUtil.getDeclarationTypes(declarationAttributeList).contains(LogtalkTokenTypes.KSTATIC);
  }

  @Override
  public boolean isOverride() {
    final LogtalkDeclarationAttribute[] declarationAttributeList = PsiTreeUtil.getChildrenOfType(this, LogtalkDeclarationAttribute.class);
    return LogtalkResolveUtil.getDeclarationTypes(declarationAttributeList).contains(LogtalkTokenTypes.KOVERRIDE);
  }

  @Override
  public boolean isInline() {
    final LogtalkDeclarationAttribute[] declarationAttributeList = PsiTreeUtil.getChildrenOfType(this, LogtalkDeclarationAttribute.class);
    return LogtalkResolveUtil.getDeclarationTypes(declarationAttributeList).contains(LogtalkTokenTypes.KINLINE);
  }

  @Nullable
  public final PsiElement findChildByRoleAsPsiElement(int role) {
    ASTNode element = findChildByRole(role);
    if (element == null) return null;
    return SourceTreeToPsiMap.treeElementToPsi(element);
  }

  @Nullable
  public ASTNode findChildByRole(int role) {
    // assert ChildRole.isUnique(role);
    PsiElement firstChild = getFirstChild();

    if (firstChild == null) return null;

    for (ASTNode child = firstChild.getNode(); child != null; child = child.getTreeNext()) {
      if (getChildRole(child) == role) return child;
    }
    return null;
  }

  public int getChildRole(ASTNode child) {
    if (child.getElementType() == LogtalkTokenTypes.PLCURLY) {
      return ChildRole.LBRACE;
    }
    else if (child.getElementType() == LogtalkTokenTypes.PRCURLY) {
      return ChildRole.RBRACE;
    }

    return 0;
  }

  protected final int getChildRole(ASTNode child, int roleCandidate) {
    if (findChildByRole(roleCandidate) == child) {
      return roleCandidate;
    }
    return 0; //ChildRole.NONE;
  }
}
