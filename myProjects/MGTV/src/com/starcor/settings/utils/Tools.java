package com.starcor.settings.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.starcor.settings.R.style;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Tools
{
  public static final int DESIGN_HEIGHT = 720;
  public static final int DESIGN_WIDTH = 1280;
  public static int SCREEN_HEIGHT = 720;
  private static String ip_regex = "\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}";

  public static int Operation(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramContext.getResources().getDisplayMetrics().heightPixels * (1.0F * paramFloat / 720.0F));
  }

  public static int OperationHeight(int paramInt)
  {
    return (int)(0.5F + SCREEN_HEIGHT * (1.0F * paramInt / 720.0F));
  }

  public static boolean isLegitimateIP(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    if (TextUtils.isEmpty(paramString1))
    {
      ToastUtil.showToast(paramContext, paramString2);
      return false;
    }
    if (!Pattern.matches(ip_regex, paramString1))
    {
      ToastUtil.showToast(paramContext, paramString3);
      return false;
    }
    return true;
  }

  public static String long2Ip(long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramLong & 0xFF).append(".");
    localStringBuilder.append(0xFF & paramLong >> 8).append(".");
    localStringBuilder.append(0xFF & paramLong >> 16).append(".");
    localStringBuilder.append(0xFF & paramLong >> 24);
    return localStringBuilder.toString();
  }

  public static int maskStringToLength(String paramString)
    throws IllegalArgumentException
  {
    int i = 0;
    String[] arrayOfString = paramString.split("\\.");
    Integer[] arrayOfInteger = new Integer[9];
    arrayOfInteger[0] = Integer.valueOf(0);
    arrayOfInteger[1] = Integer.valueOf(128);
    arrayOfInteger[2] = Integer.valueOf(192);
    arrayOfInteger[3] = Integer.valueOf(224);
    arrayOfInteger[4] = Integer.valueOf(240);
    arrayOfInteger[5] = Integer.valueOf(248);
    arrayOfInteger[6] = Integer.valueOf(252);
    arrayOfInteger[7] = Integer.valueOf(254);
    arrayOfInteger[8] = Integer.valueOf(255);
    List localList = Arrays.asList(arrayOfInteger);
    int j = 0;
    int k = arrayOfString.length;
    if (i >= k)
      return j;
    int m = Integer.parseInt(arrayOfString[i]);
    if (!localList.contains(Integer.valueOf(m)))
      throw new IllegalArgumentException("閿欒鐨勫瓙缃戞帺鐮佹暟瀛�: " + m);
    label210: for (int n = 1; ; n++)
    {
      if (n > 8);
      while (true)
      {
        i++;
        break;
        if (((0xFF & m << n) != 0) || (m == 0))
          break label210;
        j += n;
      }
    }
  }

  public static String prefixLengthToMaskString(int paramInt)
  {
    if (paramInt == 0)
      return "0.0.0.0";
    if ((paramInt < 0) || (paramInt > 32))
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    int i = -1 << 32 - paramInt;
    localStringBuilder.append(0xFF & i >> 24).append(".");
    localStringBuilder.append(0xFF & i >> 16).append(".");
    localStringBuilder.append(0xFF & i >> 8).append(".");
    localStringBuilder.append(i & 0xFF);
    return localStringBuilder.toString();
  }

  public static void showDialog(Activity paramActivity, String paramString1, String paramString2)
  {
    CommDialog localCommDialog = new CommDialog(paramActivity, R.style.dialogNoTitle);
    localCommDialog.setTitle(paramString1);
    localCommDialog.setMessage(paramString2);
    localCommDialog.setType(1);
    localCommDialog.setNegativeButton(new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
      }
    });
    localCommDialog.show();
  }

  public static void showFragment(FragmentActivity paramFragmentActivity, Class<?> paramClass)
  {
    showFragment(paramFragmentActivity.getSupportFragmentManager(), paramClass);
  }

  public static void showFragment(FragmentManager paramFragmentManager, Class<?> paramClass)
  {
    Fragment localFragment = paramFragmentManager.findFragmentByTag(paramClass.getSimpleName());
    if ((localFragment == null) || (!localFragment.isVisible()));
    try
    {
      paramClass.getMethod("showReplace", new Class[] { FragmentManager.class }).invoke(paramClass, new Object[] { paramFragmentManager });
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.utils.Tools
 * JD-Core Version:    0.6.2
 */