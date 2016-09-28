package com.starcor.report;

import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.message.MessageHandler;
import com.starcor.report.Column.DialogLoadColumn;
import com.starcor.report.Column.DialogLoadColumn.Builder;
import com.starcor.report.Column.FuncLoadColumn;
import com.starcor.report.Column.FuncLoadColumn.Builder;
import com.starcor.report.Column.PageClickColumn;
import com.starcor.report.Column.PageClickColumn.Builder;
import com.starcor.report.Column.PageFocusColumn;
import com.starcor.report.Column.PageFocusColumn.Builder;
import com.starcor.report.Column.PageLoadColumn;
import com.starcor.report.Column.PageLoadColumn.Builder;
import com.starcor.report.Column.RecommendColumn;
import com.starcor.report.Column.RecommendColumn.Builder;
import java.util.HashMap;

public class ReportFunc
{
  public static void reportClick(ReportPageInfo paramReportPageInfo1, ReportPageInfo paramReportPageInfo2, String paramString1, int paramInt, String paramString2, String paramString3)
  {
    reportClick(false, paramReportPageInfo1, paramReportPageInfo2, paramString1, paramInt, paramString2, paramString3);
  }

  public static void reportClick(boolean paramBoolean, ReportPageInfo paramReportPageInfo1, ReportPageInfo paramReportPageInfo2, String paramString1, int paramInt, String paramString2, String paramString3)
  {
    PageClickColumn.Builder localBuilder1 = new PageClickColumn.Builder();
    PageClickColumn.Builder localBuilder2 = localBuilder1.addExt1(paramInt);
    String str1;
    String str2;
    label57: String str3;
    label82: String str4;
    label114: StringBuilder localStringBuilder2;
    if (paramReportPageInfo2 == null)
    {
      str1 = "null";
      PageClickColumn.Builder localBuilder3 = localBuilder2.addExt2(str1).addExt3(paramString1).addExt4(paramString2).addExt5(paramString3);
      if (paramReportPageInfo1 != null)
        break label179;
      str2 = "null";
      localBuilder3.addExt10(str2);
      PageClickColumn localPageClickColumn = localBuilder1.build();
      if (paramReportPageInfo1 == null)
        break label188;
      str3 = paramReportPageInfo1.page;
      localPageClickColumn.addPageName(str3);
      StringBuilder localStringBuilder1 = new StringBuilder().append("页面点击类事件:");
      if (paramReportPageInfo1 == null)
        break label195;
      str4 = paramReportPageInfo1.page;
      reportData(str4, paramBoolean, localPageClickColumn);
      localStringBuilder2 = new StringBuilder().append("页面点击类事件----------->:");
      if (paramReportPageInfo1 == null)
        break label202;
    }
    label179: label188: label195: label202: for (String str5 = paramReportPageInfo1.page; ; str5 = "")
    {
      Logger.i("ReportService", str5);
      return;
      str1 = paramReportPageInfo2.page;
      break;
      str2 = paramReportPageInfo1.uuid;
      break label57;
      str3 = "";
      break label82;
      str4 = "";
      break label114;
    }
  }

  public static void reportClickRecommend(ReportPageInfo paramReportPageInfo, String paramString, HashMap<String, String> paramHashMap)
  {
    RecommendColumn.Builder localBuilder1 = new RecommendColumn.Builder();
    RecommendColumn.Builder localBuilder2 = localBuilder1.addPos((String)paramHashMap.get("pos")).addVer((String)paramHashMap.get("ver")).addReqid((String)paramHashMap.get("reqid")).addHitid((String)paramHashMap.get("hitid")).addOhitid((String)paramHashMap.get("ohitid")).addSohitid((String)paramHashMap.get("sohitid")).addRcdata((String)paramHashMap.get("rcdata")).addOrcdata((String)paramHashMap.get("orcdata")).addSorcdata((String)paramHashMap.get("sorcdata"));
    int i;
    RecommendColumn.Builder localBuilder3;
    int j;
    if (TextUtils.isEmpty((CharSequence)paramHashMap.get("page")))
    {
      i = 0;
      localBuilder3 = localBuilder2.addPage(i);
      boolean bool = TextUtils.isEmpty((CharSequence)paramHashMap.get("limit"));
      j = 0;
      if (!bool)
        break label268;
    }
    while (true)
    {
      localBuilder3.addLimit(j);
      RecommendColumn localRecommendColumn = localBuilder1.build(RecommendColumn.RECOMMEND_CLICK_EVENT, paramString);
      localRecommendColumn.addPageName(paramReportPageInfo.page);
      reportData("点击推荐事件:" + paramReportPageInfo.page, localRecommendColumn);
      Logger.i("ReportService", "点击推荐事件----------->:" + paramReportPageInfo.page);
      return;
      i = Integer.parseInt((String)paramHashMap.get("page"));
      break;
      label268: j = Integer.parseInt((String)paramHashMap.get("limit"));
    }
  }

  public static void reportData(String paramString, Object paramObject)
  {
    reportData(paramString, false, paramObject);
  }

  public static void reportData(String paramString, boolean paramBoolean, Object paramObject)
  {
    ReportMessage localReportMessage = new ReportMessage();
    localReportMessage.setIsLiveReport(paramBoolean);
    localReportMessage.mExtData = paramObject;
    if (paramString == null)
      paramString = "";
    localReportMessage.setDesc(paramString);
    MessageHandler.instance().doNotify(localReportMessage);
  }

  public static void reportDialogClick(String paramString1, int paramInt, String paramString2)
  {
    PageClickColumn.Builder localBuilder = new PageClickColumn.Builder();
    localBuilder.addExt1(paramInt).addExt5(paramString2);
    PageClickColumn localPageClickColumn = localBuilder.build();
    localPageClickColumn.addPageName(ReportArea.getValue(paramString1));
    StringBuilder localStringBuilder1 = new StringBuilder().append("页面点击类事件:");
    String str1;
    StringBuilder localStringBuilder2;
    if (ReportArea.getValue(paramString1) != null)
    {
      str1 = ReportArea.getValue(paramString1);
      reportData(str1, localPageClickColumn);
      localStringBuilder2 = new StringBuilder().append("页面点击类事件----------->:");
      if (ReportArea.getValue(paramString1) == null)
        break label126;
    }
    label126: for (String str2 = ReportArea.getValue(paramString1); ; str2 = "")
    {
      Logger.i("ReportService", str2);
      return;
      str1 = "";
      break;
    }
  }

  public static void reportDialogExit(String paramString1, int paramInt, String paramString2)
  {
    DialogLoadColumn.Builder localBuilder = new DialogLoadColumn.Builder();
    localBuilder.addExt1(1).addExt2(paramInt).addExt3(paramString2);
    DialogLoadColumn localDialogLoadColumn = localBuilder.build();
    localDialogLoadColumn.addPageName(ReportArea.getValue(paramString1));
    StringBuilder localStringBuilder = new StringBuilder().append("对话框退出事件:");
    if (ReportArea.getValue(paramString1) != null);
    for (String str = ReportArea.getValue(paramString1); ; str = "")
    {
      reportData(str, localDialogLoadColumn);
      return;
    }
  }

  public static void reportDialogLoad(String paramString1, int paramInt, String paramString2)
  {
    DialogLoadColumn.Builder localBuilder = new DialogLoadColumn.Builder();
    localBuilder.addExt1(0).addExt2(paramInt).addExt3(paramString2);
    DialogLoadColumn localDialogLoadColumn = localBuilder.build();
    localDialogLoadColumn.addPageName(ReportArea.getValue(paramString1));
    StringBuilder localStringBuilder = new StringBuilder().append("对话框展示事件:");
    if (ReportArea.getValue(paramString1) != null);
    for (String str = ReportArea.getValue(paramString1); ; str = "")
    {
      reportData(str, localDialogLoadColumn);
      return;
    }
  }

  public static void reportFocus(ReportPageInfo paramReportPageInfo1, ReportPageInfo paramReportPageInfo2, String paramString, int paramInt, String[] paramArrayOfString)
  {
    PageFocusColumn localPageFocusColumn = null;
    String str1 = "";
    String str2 = "";
    int i;
    if (paramArrayOfString != null)
      i = 0;
    try
    {
      int j = paramArrayOfString.length;
      if (i < j)
      {
        if (i == 0)
          str1 = paramArrayOfString[0];
        else if (i == 1)
          str2 = paramArrayOfString[1];
      }
      else
      {
        PageFocusColumn.Builder localBuilder = new PageFocusColumn.Builder();
        localBuilder.addExt1(paramInt);
        if (paramReportPageInfo2 == null);
        for (String str3 = "null"; ; str3 = paramReportPageInfo2.page)
        {
          localBuilder.addExt2(str3);
          localBuilder.addExt3(paramString);
          localBuilder.addExt4(str1);
          localBuilder.addExt5(str2);
          localBuilder.addExt10(paramReportPageInfo1.uuid);
          localPageFocusColumn = localBuilder.build();
          localPageFocusColumn.addPageName(paramReportPageInfo1.page);
          reportData("光标聚焦类事件:" + paramReportPageInfo1.page, localPageFocusColumn);
          Logger.i("ReportService", "光标聚焦类事件----------->:" + paramReportPageInfo1.page);
          return;
        }
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        Logger.w("ReportService", "json exception!", localException);
        continue;
        i++;
      }
    }
  }

  public static void reportFuncLoad(ReportPageInfo paramReportPageInfo1, ReportPageInfo paramReportPageInfo2, String paramString1, int paramInt1, String paramString2, String paramString3, long paramLong, boolean paramBoolean, int paramInt2, String paramString4)
  {
    reportFuncLoad(false, paramReportPageInfo1, paramReportPageInfo2, paramString1, paramInt1, paramString2, paramString3, paramLong, paramBoolean, paramInt2, paramString4);
  }

  public static void reportFuncLoad(boolean paramBoolean1, ReportPageInfo paramReportPageInfo1, ReportPageInfo paramReportPageInfo2, String paramString1, int paramInt1, String paramString2, String paramString3, long paramLong, boolean paramBoolean2, int paramInt2, String paramString4)
  {
    FuncLoadColumn.Builder localBuilder1 = new FuncLoadColumn.Builder();
    FuncLoadColumn.Builder localBuilder2 = localBuilder1.addExt1(paramInt1);
    String str1;
    String str2;
    label77: FuncLoadColumn localFuncLoadColumn;
    if (paramReportPageInfo2 == null)
    {
      str1 = "null";
      FuncLoadColumn.Builder localBuilder3 = localBuilder2.addExt2(str1).addExt3(paramString1).addExt4(paramString2).addExt5(paramString3).addExt6(paramLong).addExt7(paramBoolean2).addExt8(paramInt2).addExt9(paramString4);
      if (paramReportPageInfo1 != null)
        break label173;
      str2 = "null";
      localBuilder3.addExt10(str2);
      localFuncLoadColumn = localBuilder1.build();
      if (paramReportPageInfo1 == null)
        break label182;
    }
    label173: label182: for (String str3 = paramReportPageInfo1.page; ; str3 = "")
    {
      localFuncLoadColumn.addPageName(str3);
      reportData("功能加载类事件: funcType: " + paramInt1, paramBoolean1, localFuncLoadColumn);
      Logger.i("ReportService", "功能加载类事件: funcType: " + paramInt1);
      return;
      str1 = paramReportPageInfo2.page;
      break;
      str2 = paramReportPageInfo1.uuid;
      break label77;
    }
  }

  public static void reportLoad(ReportPageInfo paramReportPageInfo1, boolean paramBoolean, long paramLong, ReportPageInfo paramReportPageInfo2, String paramString, String[] paramArrayOfString)
  {
    PageLoadColumn localPageLoadColumn = null;
    String str1 = "";
    String str2 = "";
    int i;
    if (paramArrayOfString != null)
      i = 0;
    try
    {
      int j = paramArrayOfString.length;
      if (i < j)
      {
        if (i == 0)
          str1 = paramArrayOfString[0];
        else if (i == 1)
          str2 = paramArrayOfString[1];
      }
      else
      {
        PageLoadColumn.Builder localBuilder = new PageLoadColumn.Builder();
        localBuilder.addExt1(0);
        String str3;
        String str4;
        if (paramReportPageInfo2 == null)
        {
          str3 = "null";
          localBuilder.addExt2(str3);
          localBuilder.addExt3(paramString);
          localBuilder.addExt4(str1);
          localBuilder.addExt5(paramBoolean);
          if (paramLong < 0L)
            paramLong = 0L;
          localBuilder.addExt6(paramLong);
          localBuilder.addExt7(0L);
          localBuilder.addExt8(str2);
          if (paramReportPageInfo2 != null)
            break label263;
          str4 = "null";
          label155: localBuilder.addExt9(str4);
          if (paramReportPageInfo1 != null)
            break label273;
        }
        label263: label273: for (String str5 = "null"; ; str5 = paramReportPageInfo1.uuid)
        {
          localBuilder.addExt10(str5);
          localPageLoadColumn = localBuilder.build();
          localPageLoadColumn.addPageName(paramReportPageInfo1.page);
          reportData("页面加载事件:" + paramReportPageInfo1.page, localPageLoadColumn);
          Logger.i("ReportService", "页面加载事件----------->:" + paramReportPageInfo1.page);
          return;
          str3 = paramReportPageInfo2.page;
          break;
          str4 = paramReportPageInfo2.uuid;
          break label155;
        }
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        Logger.w("ReportService", "json exception!", localException);
        continue;
        i++;
      }
    }
  }

  public static void reportShowRecommend(ReportPageInfo paramReportPageInfo, String paramString, HashMap<String, String> paramHashMap)
  {
    RecommendColumn.Builder localBuilder1 = new RecommendColumn.Builder();
    RecommendColumn.Builder localBuilder2 = localBuilder1.addPos((String)paramHashMap.get("pos")).addVer((String)paramHashMap.get("ver")).addReqid((String)paramHashMap.get("reqid")).addHitid((String)paramHashMap.get("hitid")).addOhitid((String)paramHashMap.get("ohitid")).addSohitid((String)paramHashMap.get("sohitid")).addRcdata((String)paramHashMap.get("rcdata")).addOrcdata((String)paramHashMap.get("orcdata")).addSorcdata((String)paramHashMap.get("sorcdata"));
    int i;
    RecommendColumn.Builder localBuilder3;
    int j;
    if (TextUtils.isEmpty((CharSequence)paramHashMap.get("page")))
    {
      i = 0;
      localBuilder3 = localBuilder2.addPage(i);
      boolean bool = TextUtils.isEmpty((CharSequence)paramHashMap.get("limit"));
      j = 0;
      if (!bool)
        break label270;
    }
    while (true)
    {
      localBuilder3.addLimit(j);
      RecommendColumn localRecommendColumn = localBuilder1.build(RecommendColumn.RECOMMEND_SHOW_EVENT, paramString);
      localRecommendColumn.addPageName(paramReportPageInfo.page);
      reportData("展示推荐事件:" + paramReportPageInfo.page, localRecommendColumn);
      Logger.i("ReportService", "展示推荐事件----------->:" + paramReportPageInfo.page);
      return;
      i = Integer.parseInt((String)paramHashMap.get("page"));
      break;
      label270: j = Integer.parseInt((String)paramHashMap.get("limit"));
    }
  }

  public static void reportStop(ReportPageInfo paramReportPageInfo1, boolean paramBoolean, long paramLong, ReportPageInfo paramReportPageInfo2, String paramString, String[] paramArrayOfString)
  {
    PageLoadColumn localPageLoadColumn = null;
    String str1 = "";
    String str2 = "";
    int i;
    if (paramArrayOfString != null)
      i = 0;
    try
    {
      int j = paramArrayOfString.length;
      if (i < j)
      {
        if (i == 0)
          str1 = paramArrayOfString[0];
        else if (i == 1)
          str2 = paramArrayOfString[1];
      }
      else
      {
        PageLoadColumn.Builder localBuilder = new PageLoadColumn.Builder();
        localBuilder.addExt1(1);
        String str3;
        if (paramReportPageInfo2 == null)
        {
          str3 = "null";
          localBuilder.addExt2(str3);
          localBuilder.addExt3(paramString);
          localBuilder.addExt4(str1);
          localBuilder.addExt5(paramBoolean);
          localBuilder.addExt6(0L);
          if (paramLong < 0L)
            paramLong = 0L;
          localBuilder.addExt7(paramLong);
          localBuilder.addExt8(str2);
          if (paramReportPageInfo2 != null)
            break label257;
        }
        label257: for (String str4 = "null"; ; str4 = paramReportPageInfo2.uuid)
        {
          localBuilder.addExt9(str4);
          localBuilder.addExt10(paramReportPageInfo1.uuid);
          localPageLoadColumn = localBuilder.build();
          localPageLoadColumn.addPageName(paramReportPageInfo1.page);
          reportData("页面退出事件:" + paramReportPageInfo1.page, localPageLoadColumn);
          Logger.i("ReportService", "页面退出事件----------->:" + paramReportPageInfo1.page);
          return;
          str3 = paramReportPageInfo2.page;
          break;
        }
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        Logger.w("ReportService", "json exception!", localException);
        continue;
        i++;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.ReportFunc
 * JD-Core Version:    0.6.2
 */