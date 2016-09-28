package com.starcor.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.utils.Logger;
import java.util.Iterator;
import java.util.Set;

public class IntentDataManager
{
  public static final String LOGO = IntentDataManager.class.getSimpleName() + "_com_starcor_special";
  protected static final String TAG = IntentDataManager.class.getSimpleName();
  private Bundle _bundle;
  private Intent intent;
  private boolean isDataSwitched;

  public IntentDataManager()
  {
  }

  public IntentDataManager(Intent paramIntent)
  {
    bind(paramIntent);
    preParse();
  }

  private void bind(Intent paramIntent)
  {
    this.intent = paramIntent;
    this._bundle = getOriginBundle();
  }

  public static String buildIntentExtraToString(Intent paramIntent)
  {
    String str;
    if (paramIntent == null)
      str = "intent is null";
    while (true)
    {
      return str;
      Bundle localBundle = paramIntent.getExtras();
      if (localBundle == null)
        return "bundle is null";
      Set localSet = localBundle.keySet();
      if (localSet == null)
        return "bundle.keySet is null";
      Object[] arrayOfObject = localSet.toArray();
      str = "";
      for (int i = 0; i < arrayOfObject.length; i++)
        str = str + arrayOfObject[i] + "=" + localBundle.get(new StringBuilder().append(arrayOfObject[i]).toString()) + "---";
    }
  }

  public static void dumpBundleData(Bundle paramBundle)
  {
    Logger.i("dump bundle", "-------start dump bundle ---------");
    if (paramBundle == null)
    {
      Logger.i("dump bundle", "bundle is null");
      Logger.i("dump intent", "-------end dump bundle ---------");
      return;
    }
    Iterator localIterator = paramBundle.keySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        Logger.i("dump intent", "-------end dump bundle ---------");
        return;
      }
      String str = (String)localIterator.next();
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = str;
      arrayOfObject[1] = paramBundle.get(str);
      Logger.i("dump intent", String.format("%s : %s", arrayOfObject));
    }
  }

  public static void dumpIntentData(Intent paramIntent)
  {
    if (paramIntent == null);
    Bundle localBundle;
    do
    {
      return;
      Logger.i("dump intent", "-------start dump intent ---------");
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = paramIntent.getAction();
      Logger.i("dump intent", String.format("action : %s", arrayOfObject1));
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(paramIntent.getFlags());
      Logger.i("dump intent", String.format("flag : %s", arrayOfObject2));
      localBundle = paramIntent.getExtras();
    }
    while (localBundle == null);
    Iterator localIterator = localBundle.keySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        Logger.i("dump intent", "-------end dump intent ---------");
        return;
      }
      String str = (String)localIterator.next();
      Object[] arrayOfObject3 = new Object[2];
      arrayOfObject3[0] = str;
      arrayOfObject3[1] = localBundle.get(str);
      Logger.i("dump intent", String.format("%s : %s", arrayOfObject3));
    }
  }

  private Bundle getNewestBundle()
  {
    if (isDataSwitched())
      return this._bundle;
    return getOriginBundle();
  }

  private Bundle getOriginBundle()
  {
    if (this.intent.getExtras() == null)
      return new Bundle();
    return this.intent.getExtras();
  }

  public void clearIntent()
  {
    this.intent = new Intent();
  }

  public String getAction()
  {
    return this.intent.getAction();
  }

  public boolean getBooleanValue(String paramString)
  {
    return getBooleanValue(paramString, false);
  }

  public boolean getBooleanValue(String paramString, boolean paramBoolean)
  {
    String str = keyWrapper(paramString);
    if (TextUtils.isEmpty(str))
      return false;
    return this._bundle.getBoolean(str, paramBoolean);
  }

  public Object getData()
  {
    return getOriginBundleClone();
  }

  public int getFlags()
  {
    return this.intent.getFlags();
  }

  public int getIntFromString(String paramString)
  {
    String str1 = keyWrapper(paramString);
    int i = -1;
    try
    {
      String str2 = getStringValue(str1);
      if (str2 != null)
      {
        int j = Integer.parseInt(str2);
        i = j;
      }
      return i;
    }
    catch (Exception localException)
    {
    }
    return -1;
  }

  public int getIntValue(String paramString)
  {
    return getIntValue(paramString, 0);
  }

  public int getIntValue(String paramString, int paramInt)
  {
    String str = keyWrapper(paramString);
    int i;
    if (TextUtils.isEmpty(str))
      i = -1;
    do
    {
      return i;
      i = getIntFromString(str);
    }
    while (i != -1);
    return this._bundle.getInt(str, paramInt);
  }

  public Bundle getOriginBundleClone()
  {
    return (Bundle)getOriginBundle().clone();
  }

  public String getStringValue(String paramString)
  {
    return getStringValue(paramString, "");
  }

  public String getStringValue(String paramString1, String paramString2)
  {
    String str = keyWrapper(paramString1);
    if (TextUtils.isEmpty(str))
      return null;
    return this._bundle.getString(str, paramString2);
  }

  public boolean isDataSwitched()
  {
    return this.isDataSwitched;
  }

  public String keyWrapper(String paramString)
  {
    String str = paramString;
    if (TextUtils.isEmpty(paramString))
      return paramString;
    if (paramString.contains("_"))
      str = paramString.replace("_", "");
    return str.toLowerCase();
  }

  public void preParse()
  {
    if (isDataSwitched())
    {
      this._bundle = getNewestBundle();
      return;
    }
    Bundle localBundle1 = this.intent.getExtras();
    Bundle localBundle2 = new Bundle();
    Iterator localIterator;
    if ((localBundle1 != null) && (localBundle1.size() > 0))
      localIterator = localBundle1.keySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        this._bundle = localBundle2;
        this.isDataSwitched = true;
        return;
      }
      String str1 = (String)localIterator.next();
      String str2 = keyWrapper(str1);
      if (!TextUtils.isEmpty(str2))
      {
        Logger.i(TAG, "newKey is:　" + str2);
        Object localObject = localBundle1.get(str1);
        if ((localObject instanceof Integer))
          localBundle2.putInt(str2, ((Integer)localObject).intValue());
        else if ((localObject instanceof String))
          localBundle2.putString(str2, (String)localObject);
        else if ((localObject instanceof Boolean))
          localBundle2.putBoolean(str2, ((Boolean)localObject).booleanValue());
        else if ((localObject instanceof Float))
          localBundle2.putFloat(str2, ((Float)localObject).floatValue());
        else if ((localObject instanceof Double))
          localBundle2.putDouble(str2, ((Double)localObject).doubleValue());
      }
    }
  }

  public void putBooleanValue(String paramString, boolean paramBoolean)
  {
    String str = keyWrapper(paramString);
    if (TextUtils.isEmpty(str))
      return;
    this._bundle.putBoolean(str, paramBoolean);
  }

  public void putIntValue(String paramString, int paramInt)
  {
    String str = keyWrapper(paramString);
    if (TextUtils.isEmpty(str))
      return;
    this._bundle.putInt(str, paramInt);
  }

  public void putStringValue(String paramString1, String paramString2)
  {
    String str = keyWrapper(paramString1);
    if (TextUtils.isEmpty(str))
      return;
    this._bundle.putString(str, paramString2);
  }

  public void setAction(String paramString)
  {
    this.intent.setAction(paramString);
  }

  public void setFlags(int paramInt)
  {
    this.intent.setFlags(paramInt);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.common.IntentDataManager
 * JD-Core Version:    0.6.2
 */