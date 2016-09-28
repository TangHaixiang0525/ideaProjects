package com.starcor.settings.date;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import com.starcor.setting.service.ISettingService;
import com.starcor.settings.SettingActivity;
import com.starcor.settings.utils.Debug;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateHelper
{
  private int day;
  private ArrayList<Integer> digits = new ArrayList();
  private int hour;
  private Calendar mCalendar;
  private Context mContext;
  private int minute;
  private int month;
  private BroadcastReceiver timeReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (!"android.intent.action.TIME_TICK".equals(paramAnonymousIntent.getAction()))
        "android.intent.action.TIME_SET".equals(paramAnonymousIntent.getAction());
    }
  };
  private int year;

  public DateHelper(Context paramContext)
  {
    this.mContext = paramContext;
  }

  private void computeDayDigits()
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM");
    try
    {
      localGregorianCalendar.setTime(localSimpleDateFormat.parse(String.valueOf(getYear()) + "/" + String.valueOf(getMonth())));
      int i = localGregorianCalendar.getActualMaximum(5);
      j = 1;
      if (j >= i + 1)
        return;
    }
    catch (ParseException localParseException)
    {
      while (true)
      {
        int j;
        localParseException.printStackTrace();
        continue;
        this.digits.add(Integer.valueOf(j));
        j++;
      }
    }
  }

  private void computeHourDigits()
  {
    for (int i = 0; ; i++)
    {
      if (i >= 24)
        return;
      this.digits.add(Integer.valueOf(i));
    }
  }

  private void computeMinuteDigits(int paramInt)
  {
    int j;
    if (paramInt == 0)
    {
      j = 0;
      if (j < 32);
    }
    while (true)
    {
      return;
      this.digits.add(Integer.valueOf(j));
      j++;
      break;
      for (int i = 32; i < 60; i++)
        this.digits.add(Integer.valueOf(i));
    }
  }

  private void computeMonthDigits()
  {
    for (int i = 1; ; i++)
    {
      if (i >= 13)
        return;
      this.digits.add(Integer.valueOf(i));
    }
  }

  private void computeYearDigits(int paramInt)
  {
    for (int i = paramInt; ; i++)
    {
      if (i >= paramInt + 32)
        return;
      this.digits.add(Integer.valueOf(i));
    }
  }

  public Calendar getCalendar()
  {
    return this.mCalendar;
  }

  public int getDay()
  {
    return this.day;
  }

  public ArrayList<Integer> getDigits()
  {
    return this.digits;
  }

  public int getHour()
  {
    return this.hour;
  }

  public int getMinute()
  {
    return this.minute;
  }

  public int getMonth()
  {
    return this.month;
  }

  public int getYear()
  {
    return this.year;
  }

  public void refreshDigits(DateConfigFragment.DigitType paramDigitType, int paramInt)
  {
    this.digits.clear();
    switch ($SWITCH_TABLE$com$starcor$settings$date$DateConfigFragment$DigitType()[paramDigitType.ordinal()])
    {
    default:
      return;
    case 1:
      computeYearDigits(paramInt);
      return;
    case 2:
      computeMonthDigits();
      return;
    case 3:
      computeDayDigits();
      return;
    case 4:
      computeHourDigits();
      return;
    case 5:
    }
    computeMinuteDigits(paramInt);
  }

  public void refreshTime(Calendar paramCalendar)
  {
    this.mCalendar = paramCalendar;
    this.year = paramCalendar.get(1);
    this.month = (1 + paramCalendar.get(2));
    this.day = paramCalendar.get(5);
    this.hour = paramCalendar.get(11);
    this.minute = paramCalendar.get(12);
  }

  public void refreshTimeFromNetwork(final DateConfigFragment paramDateConfigFragment)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        Calendar localCalendar = Calendar.getInstance();
        try
        {
          URLConnection localURLConnection = new URL("http://www.bjtime.cn").openConnection();
          localURLConnection.connect();
          long l = localURLConnection.getDate();
          localCalendar.setTimeInMillis(l);
          ((SettingActivity)paramDateConfigFragment.getActivity()).getSettingService().setCurrentTimeMillis(l);
          DateHelper.this.refreshTime(localCalendar);
          Debug.v("luo", "in date help\nYear=" + DateHelper.this.getYear() + ",Month=" + DateHelper.this.getMonth() + ",Day=" + DateHelper.this.getDay() + ",Hour=" + DateHelper.this.getHour() + ",Minute=" + DateHelper.this.getMinute());
          paramDateConfigFragment.getActivity().runOnUiThread(new Runnable()
          {
            public void run()
            {
              this.val$fragment.setTime();
            }
          });
          return;
        }
        catch (MalformedURLException localMalformedURLException)
        {
          while (true)
            localMalformedURLException.printStackTrace();
        }
        catch (IOException localIOException)
        {
          while (true)
            localIOException.printStackTrace();
        }
        catch (RemoteException localRemoteException)
        {
          while (true)
            localRemoteException.printStackTrace();
        }
      }
    }).start();
  }

  public void registerTimeReceiver(Activity paramActivity)
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.TIME_TICK");
    localIntentFilter.addAction("android.intent.action.TIME_SET");
    paramActivity.registerReceiver(this.timeReceiver, localIntentFilter);
  }

  public void setTimeInMillis(DateConfigFragment.DigitType paramDigitType, int paramInt)
  {
    if (this.mCalendar == null)
      this.mCalendar = Calendar.getInstance();
    switch ($SWITCH_TABLE$com$starcor$settings$date$DateConfigFragment$DigitType()[paramDigitType.ordinal()])
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      refreshTime(this.mCalendar);
      return;
      this.mCalendar.set(1, paramInt);
      continue;
      this.mCalendar.set(2, paramInt - 1);
      continue;
      this.mCalendar.set(5, paramInt);
      continue;
      this.mCalendar.set(11, paramInt);
      continue;
      this.mCalendar.set(12, paramInt);
    }
  }

  public void unregisterTimeReceiver(Activity paramActivity)
  {
    paramActivity.unregisterReceiver(this.timeReceiver);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.date.DateHelper
 * JD-Core Version:    0.6.2
 */