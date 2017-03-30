/*
 * Copyright 2000-2017 JetBrains s.r.o.
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
package com.intellij.psi.formatter;

import com.intellij.lang.html.HTMLLanguage;
import com.intellij.lang.injection.InjectedLanguageManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsManager;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageManagerImpl;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.testFramework.PlatformTestUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class HtmlFormatterTest extends XmlFormatterTestBase {
  @Override
  protected String getBasePath() {
    return "psi/formatter/html";
  }

  @Override
  protected String getFileExtension() {
    return "html";
  }
  
  private interface OptionsSetup {
    void setupOptions(@NotNull CodeStyleSettings settings);
  }

  public void test1() throws Exception {
    final CodeStyleSettings settings = CodeStyleSettingsManager.getSettings(getProject());
    settings.HTML_KEEP_BLANK_LINES = 0;
    settings.setDefaultRightMargin(140);
    settings.HTML_ALIGN_ATTRIBUTES = false;
    settings.HTML_ATTRIBUTE_WRAP = CommonCodeStyleSettings.DO_NOT_WRAP;
    settings.HTML_ALIGN_TEXT = false;
    settings.HTML_KEEP_LINE_BREAKS = false;
    settings.HTML_KEEP_LINE_BREAKS_IN_TEXT = false;
    settings.HTML_KEEP_WHITESPACES = false;

    doTest();                             
  }

  public void test2() throws Exception {
    doTest();
  }

  public void test3() throws Exception {
    doTest();
  }

  public void test4() throws Exception {
    doTest();
  }

  public void test5() throws Exception {
    final CodeStyleSettings settings = CodeStyleSettingsManager.getSettings(getProject());
    settings.HTML_ATTRIBUTE_WRAP = CommonCodeStyleSettings.DO_NOT_WRAP;
    doTest();
  }

  public void test6() throws Exception {
    final CodeStyleSettings settings = CodeStyleSettingsManager.getSettings(getProject());
    settings.setDefaultRightMargin(140);
    settings.HTML_ALIGN_ATTRIBUTES = false;
    settings.HTML_TEXT_WRAP = CommonCodeStyleSettings.WRAP_AS_NEEDED;
    settings.HTML_ATTRIBUTE_WRAP = CommonCodeStyleSettings.DO_NOT_WRAP;
    settings.HTML_KEEP_WHITESPACES = false;
    doTest();
  }

  public void test7() throws Exception {
    final CodeStyleSettings settings = CodeStyleSettingsManager.getSettings(getProject());
    settings.setDefaultRightMargin(140);
    settings.HTML_ALIGN_TEXT = false;
    settings.HTML_TEXT_WRAP = CommonCodeStyleSettings.WRAP_AS_NEEDED;
    settings.HTML_KEEP_LINE_BREAKS = false;
    settings.HTML_KEEP_WHITESPACES = false;
    doTest();
  }

  public void test8() throws Exception {
    final CodeStyleSettings settings = CodeStyleSettingsManager.getSettings(getProject());
    settings.setDefaultRightMargin(140);
    settings.HTML_ALIGN_TEXT = false;
    settings.HTML_TEXT_WRAP = CommonCodeStyleSettings.WRAP_AS_NEEDED;
    settings.HTML_ATTRIBUTE_WRAP = CommonCodeStyleSettings.DO_NOT_WRAP;
    settings.HTML_KEEP_LINE_BREAKS = false;
    settings.HTML_KEEP_WHITESPACES = false;
    doTest();
  }

  public void test9() throws Exception {
    final CodeStyleSettings settings = CodeStyleSettingsManager.getSettings(getProject());
    settings.setDefaultRightMargin(140);
    settings.HTML_ALIGN_TEXT = false;
    settings.HTML_ALIGN_ATTRIBUTES = false;
    settings.HTML_TEXT_WRAP = CommonCodeStyleSettings.WRAP_AS_NEEDED;
    settings.HTML_ATTRIBUTE_WRAP = CommonCodeStyleSettings.DO_NOT_WRAP;
    settings.HTML_KEEP_LINE_BREAKS = false;
    settings.HTML_KEEP_WHITESPACES = false;
    doTest();
  }

  public void test10() throws Exception {
    final CodeStyleSettings settings = CodeStyleSettingsManager.getSettings(getProject());
    settings.HTML_KEEP_LINE_BREAKS = false;
    settings.setDefaultRightMargin(140);
    settings.HTML_ALIGN_ATTRIBUTES = false;
    settings.HTML_ATTRIBUTE_WRAP = CommonCodeStyleSettings.DO_NOT_WRAP;
    settings.HTML_ALIGN_TEXT = false;
    settings.HTML_KEEP_LINE_BREAKS = false;
    settings.HTML_KEEP_WHITESPACES = false;
    doTest();
  }

  public void test11() throws Exception {
    final CodeStyleSettings settings = CodeStyleSettingsManager.getSettings(getProject());
    settings.HTML_KEEP_LINE_BREAKS = false;
    settings.setDefaultRightMargin(140);
    settings.HTML_ALIGN_TEXT = false;
    settings.HTML_KEEP_LINE_BREAKS = false;
    settings.HTML_KEEP_WHITESPACES = false;
    doTest();
  }

  public void test12() throws Exception {
    final CodeStyleSettings settings = CodeStyleSettingsManager.getSettings(getProject());
    settings.HTML_KEEP_LINE_BREAKS = false;
    settings.setDefaultRightMargin(140);
    settings.HTML_ALIGN_TEXT = false;
    settings.HTML_KEEP_LINE_BREAKS = false;
    settings.HTML_KEEP_WHITESPACES = false;
    doTest();
  }

  public void test13() throws Exception {
    getSettings().HTML_KEEP_LINE_BREAKS = false;
    doTextTest("<root>\n" + "    <aaa/>\n" + "    <aaa></aaa>\n" + "    <aaa/>\n" + "</root>",
               "<root>\n" + "    <aaa/>\n" + "    <aaa></aaa>\n" + "    <aaa/>\n" + "</root>");
  }

  public void testSpaces() throws Exception {
    doTextTest("<div> text <div/> text <div> text </div> </div>",
               "<div> text\n" + "    <div/>\n" + "    text\n" + "    <div> text</div>\n" + "</div>");
  }

  public void testClosingDivOnNextLine() throws Throwable {
    //getSettings().HTML_PLACE_ON_NEW_LINE += ",div";
    doTextTest("<div>ReSharper</div>", "<div>ReSharper</div>");
    doTextTest("<div>Re\nSharper</div>", "<div>Re\n    Sharper\n</div>");
    doTextTest("<div>Re\nSharper </div>", "<div>Re\n    Sharper\n</div>");
  }

  public void testLineFeedAfterWrappedTag() throws Exception {
    doTextTest("<html><body><table></table></body></html>", "<html>\n" + "<body>\n" + "<table></table>\n" + "</body>\n" + "</html>");

    doTextTest("<html><body><table></table><tag></tag></body></html>",
               "<html>\n" + "<body>\n" + "<table></table>\n" + "<tag></tag>\n" + "</body>\n" + "</html>");


    doTextTest("<html><body><table></table> text</body></html>",
               "<html>\n" + "<body>\n" + "<table></table>\n" + "text\n" + "</body>\n" + "</html>");

    doTextTest("<html><body><table></table>text</body></html>",
               "<html>\n" + "<body>\n" + "<table></table>\n" + "text\n" + "</body>\n" + "</html>");

  }

  public void testSCR3654() throws Exception {
    getSettings().setDefaultRightMargin(5);
    getSettings().HTML_TEXT_WRAP = CommonCodeStyleSettings.WRAP_AS_NEEDED;

    doTextTest("<html>\n" + "text textV&aelig;lg JE\n" + "</html>", "<html>\n" + "text\n" + "textV&aelig;lg\n" + "JE\n" + "</html>");

    getSettings().setDefaultRightMargin(2);

    doTextTest("<html><a>&aelig;</a></html>", "<html>\n" + "<a>&aelig;</a>\n" + "</html>");
  }

  public void testBody() throws Exception {
    doTest();
  }

  public void testDontAddBreaksInside() throws Exception {
    doTest();
  }

  public void testRemoveSpacesBeforeSpanInBody() throws Exception {
    doTest();
  }

  public void testH1() throws Exception {
    doTest();
  }

  public void testTableformatting() throws Exception {
    doTest();
  }
                                    
  public void testHtmlReformatDoesntProduceAssertion() throws Exception {
    @NonNls String fileText =
      "<html>\n" +
      "  <head><title>Simple jsp page</title></head>\n" +
      "  <body>\n" +
      "<p>Place your co</p>\n" +
      "<p>ntent here</p>\n" +
      "</body>\n" +
      "</html>";
    XmlFile file = (XmlFile)PsiFileFactory.getInstance(getProject()).createFileFromText("test.html", fileText);
    final XmlTag bodyTag = file.getDocument().getRootTag().getSubTags()[1];
    CodeStyleManager.getInstance(getProject()).reformatRange(
      bodyTag,
      bodyTag.getTextRange().getStartOffset(),
      bodyTag.getTextRange().getEndOffset());

  }

  public void testXhtmlReformatDoesntProduceAssertion() throws Exception {
    @NonNls String fileText =
      "<html>\n" +
      "  <head><title>Simple jsp page</title></head>\n" +
      "  <body>\n" +
      "<p>Place your co</p>\n" +
      "<p>ntent here</p>\n" +
      "</body>\n" +
      "</html>";
    XmlFile file = (XmlFile)PsiFileFactory.getInstance(getProject()).createFileFromText("test.xhtml", fileText);
    final XmlTag bodyTag = file.getDocument().getRootTag().getSubTags()[1];
    CodeStyleManager.getInstance(getProject()).reformatRange(
      bodyTag,
      bodyTag.getTextRange().getStartOffset(),
      bodyTag.getTextRange().getEndOffset());

  }
  
  public void testPerformance() throws Exception {
    doTestPerformance("html reformat with range", 5000, null);
  }

  public void doTestPerformance(@NotNull String message, int expectedTime, OptionsSetup optionsSetup) throws Exception {
    CodeStyleSettings settings = new CodeStyleSettings();
    if (optionsSetup != null) optionsSetup.setupOptions(settings);
    CodeStyleSettingsManager.getInstance(getProject()).setTemporarySettings(settings);
    final FileEditorManager editorManager = FileEditorManager.getInstance(getProject());
    String text = loadFile(getTestName(true) + ".html", null);
    // Cache injectors beforehand
    ((InjectedLanguageManagerImpl)InjectedLanguageManager.getInstance(getProject()))
      .processInjectableElements(Arrays.asList(PsiElement.EMPTY_ARRAY), null);
    final PsiFile file = createFileFromText(text, "before.html", PsiFileFactory.getInstance(getProject()));
    try {
      PlatformTestUtil.startPerformanceTest(
        message, expectedTime, () -> {
          try {
            WriteCommandAction.runWriteCommandAction(getProject(), () ->
              CodeStyleManager.getInstance(getProject())
                .reformatText(file, file.getTextRange().getStartOffset(), file.getTextRange().getEndOffset()));
          }
          catch (Exception ex) {
            throw new RuntimeException(ex);
          }
          finally {
            doReformatRangeTest = false;
          }
        }
      ).cpuBound().useLegacyScaling().assertTiming();
    }
    finally {
      CodeStyleSettingsManager.getInstance(getProject()).dropTemporarySettings();
      final VirtualFile[] selectedFiles = editorManager.getSelectedFiles();
      if (selectedFiles.length > 0) editorManager.closeFile(selectedFiles[0]);
    }
    String expected = loadFile(getTestName(true) + "_after.html", null);
    assertEquals(expected, file.getText());
  }

  public void testInvalidChar() throws Exception {
    doTest();
  }

  public void testIndent() throws Exception {
    CommonCodeStyleSettings.IndentOptions htmlIndentOptions = getSettings().getCommonSettings(HTMLLanguage.INSTANCE).getIndentOptions();
    assert  htmlIndentOptions != null : "HTML Indent options not found!";
    int indentSize = htmlIndentOptions.INDENT_SIZE = 2;
    int contIndentSize = htmlIndentOptions.CONTINUATION_INDENT_SIZE;
    try {
      htmlIndentOptions.INDENT_SIZE = 2;
      htmlIndentOptions.CONTINUATION_INDENT_SIZE = 3;
      doTest();
    }
    finally {
      htmlIndentOptions.INDENT_SIZE = indentSize;
      htmlIndentOptions.CONTINUATION_INDENT_SIZE = contIndentSize;
    }
  }

  public void testWeb456() throws Exception {
    doTextTest(
      "<html>\n" +
      "<body>\n" +
      "<label>\n" +
      "    <textarea>\n" +
      "This my text which should appear as is\n" +
      "</textarea>\n" +
      "</label>\n" +
      "</body>\n" +
      "</html>",

      "<html>\n" +
      "<body>\n" +
      "<label>\n" +
      "    <textarea>\n" +
      "This my text which should appear as is\n" +
      "</textarea>\n" +
      "</label>\n" +
      "</body>\n" +
      "</html>"
    );
  }

  public void testWeb2405() throws Exception {
    final CodeStyleSettings settings = CodeStyleSettingsManager.getSettings(getProject());
    int noIndentMinLines = settings.HTML_DO_NOT_ALIGN_CHILDREN_OF_MIN_LINES;
    settings.HTML_DO_NOT_ALIGN_CHILDREN_OF_MIN_LINES = 3;
    try {
      doTextTest(
        "<table>\n" +
        "<tr>\n" +
        "    <td>Cell 1.</td>\n" +
        "    <td>Cell 2.</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "    <td>Cell 3.</td>\n" +
        "</tr>\n" +
        "</table>",

        "<table>\n" +
        "<tr>\n" +
        "<td>Cell 1.</td>\n" +
        "<td>Cell 2.</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        "    <td>Cell 3.</td>\n" +
        "</tr>\n" +
        "</table>"
      );
    }
    finally {
      settings.HTML_DO_NOT_ALIGN_CHILDREN_OF_MIN_LINES = noIndentMinLines;
    }
  }

  public void testWeb12840() throws Exception {
    doTextTest(
      "<div \n" +
      "id=\"some\"\n" +
      "class=\"some\"\n" +
      ">",

      "<div\n" +
      "        id=\"some\"\n" +
      "        class=\"some\"\n" +
      ">"
    );
  }

  public void testWeb16223() throws Exception {
    doTextTest(
      "<img\n" +
      "id=\"image-id\"\n" +
      "className=\"thumbnail\"\n" +
      "src=\"/some/path/to/the/images/image-name.png\"\n" +
      "alt='image'\n" +
      "/>",

      "<img\n" +
      "        id=\"image-id\"\n" +
      "        className=\"thumbnail\"\n" +
      "        src=\"/some/path/to/the/images/image-name.png\"\n" +
      "        alt='image'\n" +
      "/>"
    );
  }

  public void testWeb12937() throws Exception  {
    doTextTest(
      "<div id=\"top\"></div><!-- /#top -->\n" +
      "<div id=\"nav\">\n" +
      "<div id=\"logo\"></div><!-- /#logo -->\n" +
      "</div>",

      "<div id=\"top\"></div><!-- /#top -->\n" +
      "<div id=\"nav\">\n" +
      "    <div id=\"logo\"></div><!-- /#logo -->\n" +
      "</div>"
    );
  }

  @Override
  protected boolean doCheckDocumentUpdate() {
    return "Performance".equals(getTestName(false));
  }
  
  public void test10809() throws Exception {
    doTextTest(
      "<p>foobar</p>\n" +
      "<div>foobar</div>\n" +
      "<p>foobar</p>\n" +
      "<div>foobar</div>\n" +
      "<div>foobar</div>\n" +
      "<p>foobar</p>\n" +
      "<p>foobar</p>\n" +
      "<div>\n" +
      "    <p>foobar</p>\n" +
      "    <div>foobar</div>\n" +
      "    <p>foobar</p>\n" +
      "    <div>foobar</div>\n" +
      "    <div>foobar</div>\n" +
      "    <p>foobar</p>\n" +
      "    <p>foobar</p>\n" +
      "</div>",
      
      "<p>foobar</p>\n" +
      "<div>foobar</div>\n" +
      "<p>foobar</p>\n" +
      "<div>foobar</div>\n" +
      "<div>foobar</div>\n" +
      "<p>foobar</p>\n" +
      "<p>foobar</p>\n" +
      "<div>\n" +
      "    <p>foobar</p>\n" +
      "    <div>foobar</div>\n" +
      "    <p>foobar</p>\n" +
      "    <div>foobar</div>\n" +
      "    <div>foobar</div>\n" +
      "    <p>foobar</p>\n" +
      "    <p>foobar</p>\n" +
      "</div>"
    );
  }

  public void testWeb18909() {
    doTextTest("<!doctype html>\n" +
               "<html>\n" +
               "<body>\n" +
               "<section>\n" +
               "    <pre><code class=\"language-javascript\">function test(i) {\n" +
               "    if (i===1) {\n" +
               "        console.log('output');\n" +
               "    }\n" +
               "}</code></pre>\n" +
               "</section>\n" +
               "</body>\n" +
               "</html>",
               "<!doctype html>\n" +
               "<html>\n" +
               "<body>\n" +
               "<section>\n" +
               "    <pre><code class=\"language-javascript\">function test(i) {\n" +
               "    if (i===1) {\n" +
               "        console.log('output');\n" +
               "    }\n" +
               "}</code></pre>\n" +
               "</section>\n" +
               "</body>\n" +
               "</html>");
  }

  public void testSingleQuotes() throws Exception {
    final CodeStyleSettings settings = CodeStyleSettingsManager.getSettings(getProject());
    CodeStyleSettings.QuoteStyle quoteStyle = settings.HTML_QUOTE_STYLE;
    boolean enforce = settings.HTML_ENFORCE_QUOTES;
    try {
      settings.HTML_QUOTE_STYLE = CodeStyleSettings.QuoteStyle.Single;
      settings.HTML_ENFORCE_QUOTES = true;
      doTest();
    }
    finally {
      settings.HTML_QUOTE_STYLE = quoteStyle;
      settings.HTML_ENFORCE_QUOTES = enforce;
    }
  }
  
  public void testQuotesReplacementPerformance() throws Exception {
    doTestPerformance("Quotes replacement", 1500, (settings)->{
      settings.HTML_QUOTE_STYLE = CodeStyleSettings.QuoteStyle.Single;
      settings.HTML_ENFORCE_QUOTES = true;
    });
  }

  public void testWeb18213() {
    doTextTest(
      "<div class=\"s\">\n" +
      "    <span class=\"loading\"></span>\n" +
      "        <span>\n" +
      "        Loading...\n" +
      "        </span>\n" +
      "</div>",

      "<div class=\"s\">\n" +
      "    <span class=\"loading\"></span>\n" +
      "    <span>\n" +
      "        Loading...\n" +
      "        </span>\n" +
      "</div>"
    );
  }
}
