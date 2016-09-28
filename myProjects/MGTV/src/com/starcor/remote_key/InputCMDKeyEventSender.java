package com.starcor.remote_key;

import android.content.Context;
import android.util.Log;

public class InputCMDKeyEventSender extends KeyEventSender
{
  private static final String TAG = InputCMDKeyEventSender.class.getSimpleName();
  Context _ctx;

  public InputCMDKeyEventSender(Context paramContext)
  {
    this._ctx = paramContext;
  }

  // ERROR //
  private int _execCmdTimeout(String paramString, int paramInt)
  {
    // Byte code:
    //   0: getstatic 18	com/starcor/remote_key/InputCMDKeyEventSender:TAG	Ljava/lang/String;
    //   3: ldc 34
    //   5: invokestatic 40	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   8: pop
    //   9: invokestatic 46	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
    //   12: aload_1
    //   13: invokevirtual 50	java/lang/Runtime:exec	(Ljava/lang/String;)Ljava/lang/Process;
    //   16: astore 5
    //   18: new 52	java/lang/Thread
    //   21: dup
    //   22: new 54	com/starcor/remote_key/InputCMDKeyEventSender$1
    //   25: dup
    //   26: aload_0
    //   27: aload 5
    //   29: invokespecial 57	com/starcor/remote_key/InputCMDKeyEventSender$1:<init>	(Lcom/starcor/remote_key/InputCMDKeyEventSender;Ljava/lang/Process;)V
    //   32: invokespecial 60	java/lang/Thread:<init>	(Ljava/lang/Runnable;)V
    //   35: astore 6
    //   37: aload 6
    //   39: invokevirtual 63	java/lang/Thread:start	()V
    //   42: iload_2
    //   43: i2l
    //   44: lstore 7
    //   46: aload 6
    //   48: lload 7
    //   50: invokevirtual 67	java/lang/Thread:join	(J)V
    //   53: aload 6
    //   55: invokevirtual 71	java/lang/Thread:isAlive	()Z
    //   58: ifeq +13 -> 71
    //   61: aload 6
    //   63: invokevirtual 74	java/lang/Thread:interrupt	()V
    //   66: aload 6
    //   68: invokevirtual 76	java/lang/Thread:join	()V
    //   71: aload 6
    //   73: invokevirtual 71	java/lang/Thread:isAlive	()Z
    //   76: ifeq +8 -> 84
    //   79: aload 6
    //   81: invokevirtual 79	java/lang/Thread:stop	()V
    //   84: aload 5
    //   86: invokevirtual 85	java/lang/Process:exitValue	()I
    //   89: istore 11
    //   91: iload 11
    //   93: ireturn
    //   94: astore_3
    //   95: aload_3
    //   96: invokevirtual 88	java/io/IOException:printStackTrace	()V
    //   99: iconst_m1
    //   100: ireturn
    //   101: astore 9
    //   103: aload 9
    //   105: invokevirtual 89	java/lang/InterruptedException:printStackTrace	()V
    //   108: goto -37 -> 71
    //   111: astore 10
    //   113: aload 10
    //   115: invokevirtual 90	java/lang/Exception:printStackTrace	()V
    //   118: iconst_m1
    //   119: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   0	18	94	java/io/IOException
    //   46	71	101	java/lang/InterruptedException
    //   84	91	111	java/lang/Exception
  }

  public boolean sendKey(int paramInt1, int paramInt2)
  {
    boolean bool = true;
    if (paramInt2 != 0)
      return false;
    int i = paramInt1 & 0xFFFF0000;
    int j = paramInt1 & 0xFFFF;
    int k = i & 0x40000;
    int m = 0;
    if (k == 262144)
      m = 0 + 2;
    if ((i & 0x10000) == 65536)
      (m + 1);
    Object[] arrayOfObject = new Object[bool];
    arrayOfObject[0] = Integer.valueOf(j);
    if (_execCmdTimeout(String.format("input keyevent %d &", arrayOfObject), 2000) == 0);
    while (true)
    {
      return bool;
      bool = false;
    }
  }

  public boolean sendString(String paramString)
  {
    return _execCmdTimeout(String.format("input text %s &", new Object[] { paramString }), 2000) == 0;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.remote_key.InputCMDKeyEventSender
 * JD-Core Version:    0.6.2
 */