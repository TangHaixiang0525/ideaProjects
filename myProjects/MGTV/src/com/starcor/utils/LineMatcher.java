package com.starcor.utils;

import android.text.TextUtils;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineMatcher
{
  private static final String TAG = LineMatcher.class.getName();
  private static final int TYPE_GET_CONTENTS = 1;
  private static final int TYPE_GET_LINE = 2;
  private static final int TYPE_MODIFY_CONTNETS = 3;
  private static LineMatcher sTools;

  private void getContents(String paramString, MatchInfo paramMatchInfo)
  {
    if (paramMatchInfo.isInContents)
    {
      localMatcher = paramMatchInfo.contentPattern.matcher(paramString);
      if (!localMatcher.matches());
    }
    while (!paramMatchInfo.beginPattern.matcher(paramString).matches())
    {
      Matcher localMatcher;
      paramMatchInfo.contents.add(localMatcher.group(1));
      do
        return;
      while (!paramMatchInfo.endPattern.matcher(paramString).matches());
      paramMatchInfo.isInContents = false;
      return;
    }
    paramMatchInfo.isInContents = true;
  }

  private void getSingleLine(String paramString, MatchInfo paramMatchInfo)
  {
    Matcher localMatcher = paramMatchInfo.contentPattern.matcher(paramString);
    if (localMatcher.matches())
    {
      Logger.e(TAG, "the match is: " + localMatcher.group(1));
      paramMatchInfo.contents.add(localMatcher.group(1));
    }
  }

  public static LineMatcher instance()
  {
    if (sTools == null)
      sTools = new LineMatcher();
    return sTools;
  }

  private void modifyContents(String paramString, MatchInfo paramMatchInfo)
  {
    Matcher localMatcher = paramMatchInfo.contentPattern.matcher(paramString);
    if (localMatcher.matches())
    {
      StringBuffer localStringBuffer = new StringBuffer();
      String str1 = paramString.substring(0, localMatcher.start(1));
      String str2 = paramString.substring(localMatcher.end(1), paramString.length());
      String str3 = paramString.substring(localMatcher.start(1), localMatcher.end(1));
      if ((TextUtils.isEmpty(paramMatchInfo.oldValue)) || (paramMatchInfo.oldValue.equals(str3)))
      {
        localStringBuffer.append(str1).append(paramMatchInfo.newValue).append(str2);
        paramString = localStringBuffer.toString();
        Logger.d(TAG, "matches! new line is: " + paramString);
      }
    }
    paramMatchInfo.fileContents.append(paramString);
    paramMatchInfo.fileContents.append(System.getProperty("line.separator"));
  }

  private void select(String paramString, MatchInfo paramMatchInfo)
  {
    if (paramMatchInfo.type == 1)
      getContents(paramString, paramMatchInfo);
    do
    {
      return;
      if (paramMatchInfo.type == 2)
      {
        getSingleLine(paramString, paramMatchInfo);
        return;
      }
    }
    while (paramMatchInfo.type != 3);
    modifyContents(paramString, paramMatchInfo);
  }

  private void streamNormalParser(MatchInfo paramMatchInfo)
  {
    if (paramMatchInfo.outStream == null)
      return;
    if (paramMatchInfo.errorStream == null)
    {
      streamNormalParserNoErr(paramMatchInfo);
      return;
    }
    LineReader localLineReader1 = new LineReader(paramMatchInfo.outStream);
    LineReader localLineReader2 = new LineReader(paramMatchInfo.errorStream);
    boolean bool1 = waitFor(30);
    boolean bool2;
    if ((!localLineReader1.ready()) && (!bool1))
    {
      bool2 = false;
      label68: if ((localLineReader2.ready()) || (bool1))
        break label118;
    }
    label118: for (boolean bool3 = false; ; bool3 = true)
    {
      if (bool1)
      {
        localLineReader1.flush();
        localLineReader2.flush();
      }
      if ((bool2) || (bool3))
        break label131;
      if (!bool1)
        break;
      return;
      bool2 = true;
      break label68;
    }
    String str2;
    label131: 
    do
    {
      select(str2, paramMatchInfo);
      if (!bool2)
        break;
      str2 = localLineReader1.readLine();
    }
    while (str2 != null);
    while (true)
    {
      String str1;
      if (bool3)
      {
        str1 = localLineReader2.readLine();
        if (str1 != null);
      }
      else
      {
        bool2 = localLineReader1.ready();
        bool3 = localLineReader2.ready();
        break;
      }
      Logger.e(TAG, str1);
    }
  }

  private void streamNormalParserNoErr(MatchInfo paramMatchInfo)
  {
    LineReader localLineReader = new LineReader(paramMatchInfo.outStream);
    boolean bool1 = waitFor(30);
    boolean bool2;
    if ((!localLineReader.ready()) && (!bool1))
    {
      bool2 = false;
      label33: if (bool1)
        localLineReader.flush();
    }
    while (true)
    {
      if (!bool2)
      {
        if (!bool1)
          break;
        return;
        bool2 = true;
        break label33;
      }
      String str;
      do
      {
        select(str, paramMatchInfo);
        if (!bool2)
          break;
        str = localLineReader.readLine();
      }
      while (str != null);
      bool2 = localLineReader.ready();
    }
  }

  private boolean waitFor(int paramInt)
  {
    long l = paramInt;
    try
    {
      Thread.sleep(l);
      return true;
    }
    catch (IllegalThreadStateException localIllegalThreadStateException)
    {
      return false;
    }
    catch (InterruptedException localInterruptedException)
    {
      label11: break label11;
    }
  }

  public ArrayList<String> getContents(InputStream paramInputStream1, InputStream paramInputStream2, String paramString1, String paramString2, String paramString3)
  {
    MatchInfo localMatchInfo = new MatchInfo(null);
    localMatchInfo.contents = new ArrayList();
    localMatchInfo.outStream = paramInputStream1;
    localMatchInfo.errorStream = paramInputStream2;
    localMatchInfo.type = 1;
    localMatchInfo.beginPattern = Pattern.compile(paramString1);
    localMatchInfo.endPattern = Pattern.compile(paramString2);
    localMatchInfo.contentPattern = Pattern.compile(paramString3);
    localMatchInfo.isInContents = false;
    streamNormalParser(localMatchInfo);
    return localMatchInfo.contents;
  }

  public String getSingleLine(InputStream paramInputStream1, InputStream paramInputStream2, String paramString)
  {
    MatchInfo localMatchInfo = new MatchInfo(null);
    localMatchInfo.contents = new ArrayList();
    localMatchInfo.outStream = paramInputStream1;
    localMatchInfo.errorStream = paramInputStream2;
    localMatchInfo.type = 2;
    localMatchInfo.contentPattern = Pattern.compile(paramString);
    streamNormalParser(localMatchInfo);
    int i = localMatchInfo.contents.size();
    String str = null;
    if (i > 0)
      str = (String)localMatchInfo.contents.get(0);
    return str;
  }

  public String modifyContents(InputStream paramInputStream1, InputStream paramInputStream2, String paramString1, String paramString2, String paramString3)
  {
    MatchInfo localMatchInfo = new MatchInfo(null);
    localMatchInfo.contents = new ArrayList();
    localMatchInfo.outStream = paramInputStream1;
    localMatchInfo.errorStream = paramInputStream2;
    localMatchInfo.type = 3;
    localMatchInfo.contentPattern = Pattern.compile(paramString1);
    localMatchInfo.oldValue = paramString2;
    localMatchInfo.newValue = paramString3;
    localMatchInfo.fileContents = new StringBuilder();
    streamNormalParser(localMatchInfo);
    return localMatchInfo.fileContents.toString();
  }

  private class MatchInfo
  {
    Pattern beginPattern;
    Pattern contentPattern;
    ArrayList<String> contents;
    Pattern endPattern;
    InputStream errorStream;
    StringBuilder fileContents;
    boolean isInContents;
    String newValue;
    String oldValue;
    InputStream outStream;
    int type;

    private MatchInfo()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.utils.LineMatcher
 * JD-Core Version:    0.6.2
 */