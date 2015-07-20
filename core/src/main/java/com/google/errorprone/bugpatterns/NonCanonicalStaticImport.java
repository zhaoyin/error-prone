/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.errorprone.bugpatterns;

import static com.google.errorprone.BugPattern.Category.JDK;
import static com.google.errorprone.BugPattern.MaturityLevel.MATURE;
import static com.google.errorprone.BugPattern.SeverityLevel.WARNING;

import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker.ImportTreeMatcher;
import com.google.errorprone.bugpatterns.UnnecessaryStaticImport.StaticTypeImportInfo;
import com.google.errorprone.fixes.SuggestedFix;
import com.google.errorprone.matchers.Description;

import com.sun.source.tree.ImportTree;

/**
 * Types shouldn't be statically by their non-canonical name.
 *
 * @author cushon@google.com (Liam Miller-Cushon)
 */
@BugPattern(name = "NonCanonicalStaticImport",
    summary = "Static import of type uses non-canonical name",
    category = JDK, severity = WARNING, maturity = MATURE)
public class NonCanonicalStaticImport extends BugChecker implements ImportTreeMatcher {

  @Override
  public Description matchImport(ImportTree tree, VisitorState state) {
    StaticTypeImportInfo importInfo = StaticTypeImportInfo.tryCreate(tree, state);
    if (importInfo == null || importInfo.isCanonical()) {
      return Description.NO_MATCH;
    }
    return describeMatch(tree, SuggestedFix.replace(tree, importInfo.importStatement()));
  }
}
