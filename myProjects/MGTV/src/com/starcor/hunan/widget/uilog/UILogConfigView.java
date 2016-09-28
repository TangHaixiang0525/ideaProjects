package com.starcor.hunan.widget.uilog;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.config.MgtvUrl;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.ToastUtil;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UILogConfigView extends LinearLayout
{
  public static int hideIndex;
  public static List<Integer> hideKey;
  public static List<Integer> nextKey;
  public static List<Integer> prevKey;
  public static int printIndex;
  public static List<Integer> printLogKey;
  public static int showIndex;
  public static List<Integer> showKey = new ArrayList();
  public List<String> datas = new ArrayList();
  public boolean isAdd = false;
  public LinearLayout.LayoutParams ll_params;
  public TextView macAddress;
  public TextView mainEntryAddress;
  public int page = 20;
  public int pageCount = 0;
  public int pageIndex = 0;
  public TextView showData;
  public TextView showInfo;
  public TextView versionCode;
  public WindowManager windowManager;

  static
  {
    hideKey = new ArrayList();
    nextKey = new ArrayList();
    prevKey = new ArrayList();
    printLogKey = new ArrayList();
    showIndex = 0;
    printIndex = 0;
    hideIndex = 0;
    showKey.add(Integer.valueOf(21));
    showKey.add(Integer.valueOf(22));
    showKey.add(Integer.valueOf(19));
    showKey.add(Integer.valueOf(20));
    showKey.add(Integer.valueOf(21));
    showKey.add(Integer.valueOf(19));
    showKey.add(Integer.valueOf(22));
    showKey.add(Integer.valueOf(20));
    showKey.add(Integer.valueOf(21));
    showKey.add(Integer.valueOf(21));
    showKey.add(Integer.valueOf(22));
    showKey.add(Integer.valueOf(22));
    showKey.add(Integer.valueOf(19));
    showKey.add(Integer.valueOf(19));
    showKey.add(Integer.valueOf(20));
    showKey.add(Integer.valueOf(20));
    hideKey.add(Integer.valueOf(4));
    nextKey.add(Integer.valueOf(20));
    prevKey.add(Integer.valueOf(19));
    printLogKey.add(Integer.valueOf(19));
    printLogKey.add(Integer.valueOf(19));
    printLogKey.add(Integer.valueOf(20));
    printLogKey.add(Integer.valueOf(20));
    printLogKey.add(Integer.valueOf(21));
    printLogKey.add(Integer.valueOf(21));
    printLogKey.add(Integer.valueOf(22));
    printLogKey.add(Integer.valueOf(22));
    printLogKey.add(Integer.valueOf(19));
    printLogKey.add(Integer.valueOf(19));
    printLogKey.add(Integer.valueOf(20));
    printLogKey.add(Integer.valueOf(20));
    printLogKey.add(Integer.valueOf(21));
    printLogKey.add(Integer.valueOf(21));
    printLogKey.add(Integer.valueOf(22));
    printLogKey.add(Integer.valueOf(22));
  }

  public UILogConfigView(Context paramContext)
  {
    super(paramContext);
    init(paramContext);
  }

  public UILogConfigView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext);
  }

  private void init(Context paramContext)
  {
    this.windowManager = ((WindowManager)paramContext.getApplicationContext().getSystemService("window"));
    this.ll_params = new LinearLayout.LayoutParams(-2, -2);
    setOrientation(1);
    this.macAddress = new TextView(paramContext);
    this.mainEntryAddress = new TextView(paramContext);
    this.versionCode = new TextView(paramContext);
    this.showData = new TextView(paramContext);
    this.showInfo = new TextView(paramContext);
    this.datas.add("<font color=yellow><i><b><u>AppFuncCfg内部字段如下:</u></b></i></font><br/>");
    AppFuncCfg localAppFuncCfg = new AppFuncCfg();
    Field[] arrayOfField = AppFuncCfg.class.getDeclaredFields();
    int i = arrayOfField.length;
    for (int j = 0; ; j++)
      if (j < i)
      {
        Field localField = arrayOfField[j];
        try
        {
          String str = localField.getName();
          Object localObject = localField.get(localAppFuncCfg);
          if (!(localObject instanceof Boolean))
            continue;
          if (((Boolean)localObject).booleanValue())
            this.datas.add("<font color=green><b>" + str + "=" + localObject + "</b></font><br/>");
          else
            this.datas.add("<font color=red><i>" + str + "=" + localObject + "</i></font><br/>");
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
      else
      {
        if (this.datas.size() % this.page == 0);
        for (this.pageCount = (this.datas.size() / this.page); ; this.pageCount = (1 + this.datas.size() / this.page))
        {
          System.out.println("pageCount=" + this.pageCount + "   datas.size()=" + this.datas.size());
          return;
        }
      }
  }

  public void addAppView()
  {
    Logger.i("dispatherEvent", "addAppView");
    this.macAddress.setText("mac地址:" + DeviceInfo.getMac());
    addView(this.macAddress, this.ll_params);
    this.mainEntryAddress.setText("主入口地址:" + MgtvUrl.N1_A());
    addView(this.mainEntryAddress, this.ll_params);
    this.versionCode.setText("版本" + DeviceInfo.getMGTVVersion());
    addView(this.versionCode, this.ll_params);
    addView(this.showData);
    this.showInfo.setText(Html.fromHtml("<font color=white><strong>当前页数:" + this.pageIndex + "/总共:" + this.pageCount + "页 </strong></font>"));
    addView(this.showInfo);
    WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams(2005);
    localLayoutParams.width = -1;
    localLayoutParams.height = -1;
    localLayoutParams.alpha = 0.9F;
    localLayoutParams.gravity = 53;
    localLayoutParams.flags = 192;
    localLayoutParams.setTitle("DialogView");
    this.windowManager.addView(this, localLayoutParams);
  }

  public void customOnKeyDown(Context paramContext, int paramInt)
  {
    showView(paramContext, paramInt);
    hideView(paramContext, paramInt);
    nextView(paramContext, paramInt);
    prevView(paramContext, paramInt);
    printLog(paramContext, paramInt);
  }

  public void forceHideView(Context paramContext)
  {
    Iterator localIterator = hideKey.iterator();
    while (localIterator.hasNext())
      hideView(paramContext, ((Integer)localIterator.next()).intValue());
  }

  public void hideView(Context paramContext, int paramInt)
  {
    if (hideIndex >= hideKey.size())
      hideIndex = 0;
    if (((Integer)hideKey.get(hideIndex)).intValue() == paramInt);
    for (hideIndex = 1 + hideIndex; ; hideIndex = 0)
    {
      if ((hideIndex == hideKey.size()) && (this.isAdd))
      {
        hideIndex = 0;
        removeAllViews();
        this.windowManager.removeView(this);
        this.isAdd = false;
      }
      return;
    }
  }

  public void nextView(Context paramContext, int paramInt)
  {
    if ((((Integer)nextKey.get(0)).intValue() == paramInt) && (this.isAdd))
    {
      this.pageIndex = (1 + this.pageIndex);
      removeAllViews();
      this.windowManager.removeView(this);
      if (this.pageIndex < this.pageCount)
      {
        StringBuffer localStringBuffer1 = new StringBuffer();
        for (int i = this.page * (-1 + this.pageIndex); i < this.page * this.pageIndex; i++)
          localStringBuffer1.append((String)this.datas.get(i));
        Spanned localSpanned1 = Html.fromHtml(localStringBuffer1.toString());
        this.showData.setText(localSpanned1);
        addAppView();
      }
    }
    else
    {
      return;
    }
    this.pageIndex = this.pageCount;
    StringBuffer localStringBuffer2 = new StringBuffer();
    for (int j = this.page * (-1 + this.pageIndex); j < this.datas.size(); j++)
      localStringBuffer2.append((String)this.datas.get(j));
    Spanned localSpanned2 = Html.fromHtml(localStringBuffer2.toString());
    this.showData.setText(localSpanned2);
    addAppView();
  }

  public void prevView(Context paramContext, int paramInt)
  {
    if ((((Integer)prevKey.get(0)).intValue() == paramInt) && (this.isAdd))
    {
      this.pageIndex = (-1 + this.pageIndex);
      removeAllViews();
      this.windowManager.removeView(this);
      if (this.pageIndex != 0)
      {
        StringBuffer localStringBuffer1 = new StringBuffer();
        for (int i = this.page * (-1 + this.pageIndex); i < this.page * this.pageIndex; i++)
          localStringBuffer1.append((String)this.datas.get(i));
        Spanned localSpanned1 = Html.fromHtml(localStringBuffer1.toString());
        this.showData.setText(localSpanned1);
        addAppView();
      }
    }
    else
    {
      return;
    }
    this.pageIndex = 1;
    StringBuffer localStringBuffer2 = new StringBuffer();
    for (int j = 0; j < this.page; j++)
      localStringBuffer2.append((String)this.datas.get(j));
    Spanned localSpanned2 = Html.fromHtml(localStringBuffer2.toString());
    this.showData.setText(localSpanned2);
    addAppView();
  }

  public void printLog(Context paramContext, int paramInt)
  {
    if (printIndex == printLogKey.size())
      printIndex = 0;
    if (((Integer)printLogKey.get(printIndex)).intValue() == paramInt);
    for (printIndex = 1 + printIndex; ; printIndex = 0)
    {
      if (printIndex == printLogKey.size())
      {
        printIndex = 0;
        if (!AppFuncCfg.FUNCTION_OPEN_LOGGER)
          break;
        GlobalEnv.getInstance().setLogConfig(false);
        ToastUtil.showToast(paramContext, "日志输出开关已经关闭  ！！！");
      }
      return;
    }
    GlobalEnv.getInstance().setLogConfig(true);
    ToastUtil.showToast(paramContext, "日志输出开关已经开启  ！！！");
  }

  public void showView(Context paramContext, int paramInt)
  {
    if (showIndex == showKey.size())
      showIndex = 0;
    if (((Integer)showKey.get(showIndex)).intValue() == paramInt);
    for (showIndex = 1 + showIndex; ; showIndex = 0)
    {
      if ((showIndex == showKey.size()) && (!this.isAdd))
      {
        showIndex = 0;
        addAppView();
        this.isAdd = true;
      }
      return;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.uilog.UILogConfigView
 * JD-Core Version:    0.6.2
 */