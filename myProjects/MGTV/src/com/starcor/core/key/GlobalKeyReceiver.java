package com.starcor.core.key;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.starcor.config.DeviceInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.jiushi.JiuShiKeyAction;
import java.io.Serializable;

public class GlobalKeyReceiver extends BroadcastReceiver
{
  public static final String ACTION_KEY_EVENT_FROM_OUTER = "com.starcor.hunan.logic_event.key_event_from_outer";
  private static final String TAG = "GlobalKeyReceiver";

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (!paramIntent.getAction().equals("com.starcor.hunan.logic_event.key_event_from_outer"))
      Logger.d("GlobalKeyReceiver", "unsupported intent Action:" + paramIntent.getAction());
    GlobalKeyInfo localGlobalKeyInfo;
    do
    {
      return;
      Bundle localBundle = paramIntent.getExtras();
      localGlobalKeyInfo = new GlobalKeyInfo();
      localGlobalKeyInfo.src = localBundle.getString("src");
      localGlobalKeyInfo.keyCode = localBundle.getInt("keyCode");
      localGlobalKeyInfo.keyAction = GlobalKeyReceiver.GlobalKeyInfo.ActionType.FromString(localBundle.getString("keyAction"));
      localGlobalKeyInfo.characters = localBundle.getString("characters");
      localGlobalKeyInfo.unicodeChar = localBundle.getInt("unicodeChar");
      localGlobalKeyInfo.repeatCount = localBundle.getInt("repeatCount");
      localGlobalKeyInfo.scanCode = localBundle.getInt("scanCode");
      localGlobalKeyInfo.deviceId = localBundle.getInt("deviceId");
      localGlobalKeyInfo.isAltPressed = localBundle.getBoolean("isAltPressed");
      localGlobalKeyInfo.isCtrlPressed = localBundle.getBoolean("isCtrlPressed");
      localGlobalKeyInfo.isShiftPressed = localBundle.getBoolean("isShiftPressed");
      localGlobalKeyInfo.isFunctionPressed = localBundle.getBoolean("isFunctionPressed");
      localGlobalKeyInfo.isMetaPressed = localBundle.getBoolean("isMetaPressed");
      localGlobalKeyInfo.isSymPressed = localBundle.getBoolean("isSymPressed");
      localGlobalKeyInfo.isScrollLockOn = localBundle.getBoolean("isScrollLockOn");
      localGlobalKeyInfo.isCapsLockOn = localBundle.getBoolean("isCapsLockOn");
      localGlobalKeyInfo.isNumLockOn = localBundle.getBoolean("isNumLockOn");
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(localGlobalKeyInfo.keyCode);
      arrayOfObject[1] = Integer.valueOf(localGlobalKeyInfo.scanCode);
      arrayOfObject[2] = localGlobalKeyInfo.keyAction.value();
      Logger.d("GlobalKeyReceiver", String.format("GlobalKeyReceiver Event %d(%d) %s", arrayOfObject));
    }
    while (900013001 != DeviceInfo.getFactory());
    JiuShiKeyAction.processKeyAction(localGlobalKeyInfo);
  }

  public static class GlobalKeyInfo
    implements Serializable
  {
    public String characters;
    public int deviceId;
    public boolean isAltPressed;
    public boolean isCapsLockOn;
    public boolean isCtrlPressed;
    public boolean isFunctionPressed;
    public boolean isMetaPressed;
    public boolean isNumLockOn;
    public boolean isScrollLockOn;
    public boolean isShiftPressed;
    public boolean isSymPressed;
    public ActionType keyAction;
    public int keyCode;
    public int repeatCount;
    public int scanCode;
    public String src;
    public int unicodeChar;

    public static enum ActionType
    {
      private String action;

      static
      {
        ACTION_MULTIPLE = new ActionType("ACTION_MULTIPLE", 2, "multiple");
        ACTION_UNKNOWN = new ActionType("ACTION_UNKNOWN", 3, "unknown");
        ActionType[] arrayOfActionType = new ActionType[4];
        arrayOfActionType[0] = ACTION_DOWN;
        arrayOfActionType[1] = ACTION_UP;
        arrayOfActionType[2] = ACTION_MULTIPLE;
        arrayOfActionType[3] = ACTION_UNKNOWN;
      }

      private ActionType(String paramString)
      {
        this.action = paramString;
      }

      public static ActionType FromString(String paramString)
      {
        for (ActionType localActionType : values())
          if (localActionType.action.equals(paramString))
            return localActionType;
        return ACTION_UNKNOWN;
      }

      public String value()
      {
        return this.action;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.key.GlobalKeyReceiver
 * JD-Core Version:    0.6.2
 */