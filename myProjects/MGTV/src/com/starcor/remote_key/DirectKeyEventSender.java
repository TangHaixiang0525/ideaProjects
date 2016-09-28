package com.starcor.remote_key;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import java.lang.reflect.Method;

public class DirectKeyEventSender extends KeyEventSender
{
  private static final String TAG = DirectKeyEventSender.class.getSimpleName();
  int _INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH;
  Context _ctx;
  Method _getInstance;
  Method _getServiceMethod;
  Method _injectInputEvent;
  Class<?> _inputEventClass;
  Class<?> _inputManager;
  Class<?> _serviceManager;

  // ERROR //
  public DirectKeyEventSender(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 39	com/starcor/remote_key/KeyEventSender:<init>	()V
    //   4: aload_0
    //   5: aload_1
    //   6: putfield 41	com/starcor/remote_key/DirectKeyEventSender:_ctx	Landroid/content/Context;
    //   9: aload_0
    //   10: invokestatic 47	java/lang/ClassLoader:getSystemClassLoader	()Ljava/lang/ClassLoader;
    //   13: ldc 49
    //   15: invokevirtual 53	java/lang/ClassLoader:loadClass	(Ljava/lang/String;)Ljava/lang/Class;
    //   18: putfield 55	com/starcor/remote_key/DirectKeyEventSender:_serviceManager	Ljava/lang/Class;
    //   21: aload_0
    //   22: aload_0
    //   23: getfield 55	com/starcor/remote_key/DirectKeyEventSender:_serviceManager	Ljava/lang/Class;
    //   26: ldc 57
    //   28: iconst_1
    //   29: anewarray 23	java/lang/Class
    //   32: dup
    //   33: iconst_0
    //   34: ldc 59
    //   36: aastore
    //   37: invokevirtual 63	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   40: putfield 65	com/starcor/remote_key/DirectKeyEventSender:_getServiceMethod	Ljava/lang/reflect/Method;
    //   43: aload_0
    //   44: invokestatic 47	java/lang/ClassLoader:getSystemClassLoader	()Ljava/lang/ClassLoader;
    //   47: ldc 67
    //   49: invokevirtual 53	java/lang/ClassLoader:loadClass	(Ljava/lang/String;)Ljava/lang/Class;
    //   52: putfield 69	com/starcor/remote_key/DirectKeyEventSender:_inputManager	Ljava/lang/Class;
    //   55: aload_0
    //   56: invokestatic 47	java/lang/ClassLoader:getSystemClassLoader	()Ljava/lang/ClassLoader;
    //   59: ldc 71
    //   61: invokevirtual 53	java/lang/ClassLoader:loadClass	(Ljava/lang/String;)Ljava/lang/Class;
    //   64: putfield 73	com/starcor/remote_key/DirectKeyEventSender:_inputEventClass	Ljava/lang/Class;
    //   67: aload_0
    //   68: aload_0
    //   69: getfield 69	com/starcor/remote_key/DirectKeyEventSender:_inputManager	Ljava/lang/Class;
    //   72: ldc 75
    //   74: iconst_0
    //   75: anewarray 23	java/lang/Class
    //   78: invokevirtual 63	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   81: putfield 77	com/starcor/remote_key/DirectKeyEventSender:_getInstance	Ljava/lang/reflect/Method;
    //   84: aload_0
    //   85: getfield 69	com/starcor/remote_key/DirectKeyEventSender:_inputManager	Ljava/lang/Class;
    //   88: astore 6
    //   90: iconst_2
    //   91: anewarray 23	java/lang/Class
    //   94: astore 7
    //   96: aload 7
    //   98: iconst_0
    //   99: aload_0
    //   100: getfield 73	com/starcor/remote_key/DirectKeyEventSender:_inputEventClass	Ljava/lang/Class;
    //   103: aastore
    //   104: aload 7
    //   106: iconst_1
    //   107: getstatic 82	java/lang/Integer:TYPE	Ljava/lang/Class;
    //   110: aastore
    //   111: aload_0
    //   112: aload 6
    //   114: ldc 84
    //   116: aload 7
    //   118: invokevirtual 87	java/lang/Class:getDeclaredMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   121: putfield 89	com/starcor/remote_key/DirectKeyEventSender:_injectInputEvent	Ljava/lang/reflect/Method;
    //   124: aload_0
    //   125: getfield 89	com/starcor/remote_key/DirectKeyEventSender:_injectInputEvent	Ljava/lang/reflect/Method;
    //   128: iconst_1
    //   129: invokevirtual 95	java/lang/reflect/Method:setAccessible	(Z)V
    //   132: aload_0
    //   133: getfield 69	com/starcor/remote_key/DirectKeyEventSender:_inputManager	Ljava/lang/Class;
    //   136: ldc 97
    //   138: invokevirtual 101	java/lang/Class:getDeclaredField	(Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   141: astore 8
    //   143: aload 8
    //   145: iconst_1
    //   146: invokevirtual 104	java/lang/reflect/Field:setAccessible	(Z)V
    //   149: aload_0
    //   150: aload 8
    //   152: aconst_null
    //   153: invokevirtual 108	java/lang/reflect/Field:getInt	(Ljava/lang/Object;)I
    //   156: putfield 110	com/starcor/remote_key/DirectKeyEventSender:_INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH	I
    //   159: return
    //   160: astore 10
    //   162: aload 10
    //   164: invokevirtual 113	java/lang/ClassNotFoundException:printStackTrace	()V
    //   167: goto -124 -> 43
    //   170: astore 9
    //   172: aload 9
    //   174: invokevirtual 114	java/lang/NoSuchMethodException:printStackTrace	()V
    //   177: goto -134 -> 43
    //   180: astore_2
    //   181: aload_2
    //   182: invokevirtual 115	java/lang/Exception:printStackTrace	()V
    //   185: goto -142 -> 43
    //   188: astore 5
    //   190: aload 5
    //   192: invokevirtual 113	java/lang/ClassNotFoundException:printStackTrace	()V
    //   195: return
    //   196: astore 4
    //   198: aload 4
    //   200: invokevirtual 114	java/lang/NoSuchMethodException:printStackTrace	()V
    //   203: return
    //   204: astore_3
    //   205: aload_3
    //   206: invokevirtual 115	java/lang/Exception:printStackTrace	()V
    //   209: return
    //
    // Exception table:
    //   from	to	target	type
    //   9	43	160	java/lang/ClassNotFoundException
    //   9	43	170	java/lang/NoSuchMethodException
    //   9	43	180	java/lang/Exception
    //   43	159	188	java/lang/ClassNotFoundException
    //   43	159	196	java/lang/NoSuchMethodException
    //   43	159	204	java/lang/Exception
  }

  // ERROR //
  boolean _injectKeyEvent(KeyEvent paramKeyEvent, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 89	com/starcor/remote_key/DirectKeyEventSender:_injectInputEvent	Ljava/lang/reflect/Method;
    //   4: ifnull +117 -> 121
    //   7: aload_0
    //   8: getfield 77	com/starcor/remote_key/DirectKeyEventSender:_getInstance	Ljava/lang/reflect/Method;
    //   11: ifnull +110 -> 121
    //   14: aload_0
    //   15: getfield 73	com/starcor/remote_key/DirectKeyEventSender:_inputEventClass	Ljava/lang/Class;
    //   18: aload_1
    //   19: invokevirtual 127	java/lang/Class:cast	(Ljava/lang/Object;)Ljava/lang/Object;
    //   22: astore 12
    //   24: aload_0
    //   25: getfield 77	com/starcor/remote_key/DirectKeyEventSender:_getInstance	Ljava/lang/reflect/Method;
    //   28: aconst_null
    //   29: iconst_0
    //   30: anewarray 129	java/lang/Object
    //   33: invokevirtual 133	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   36: astore 13
    //   38: aload_0
    //   39: getfield 89	com/starcor/remote_key/DirectKeyEventSender:_injectInputEvent	Ljava/lang/reflect/Method;
    //   42: astore 14
    //   44: iconst_2
    //   45: anewarray 129	java/lang/Object
    //   48: astore 15
    //   50: aload 15
    //   52: iconst_0
    //   53: aload 12
    //   55: aastore
    //   56: aload 15
    //   58: iconst_1
    //   59: aload_0
    //   60: getfield 110	com/starcor/remote_key/DirectKeyEventSender:_INJECT_INPUT_EVENT_MODE_WAIT_FOR_FINISH	I
    //   63: invokestatic 137	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   66: aastore
    //   67: aload 14
    //   69: aload 13
    //   71: aload 15
    //   73: invokevirtual 133	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   76: checkcast 139	java/lang/Boolean
    //   79: invokevirtual 143	java/lang/Boolean:booleanValue	()Z
    //   82: istore 16
    //   84: getstatic 29	com/starcor/remote_key/DirectKeyEventSender:TAG	Ljava/lang/String;
    //   87: new 145	java/lang/StringBuilder
    //   90: dup
    //   91: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   94: ldc 148
    //   96: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: iload 16
    //   101: invokevirtual 155	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   104: invokevirtual 158	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   107: invokestatic 164	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   110: pop
    //   111: iload 16
    //   113: ireturn
    //   114: astore 11
    //   116: aload 11
    //   118: invokevirtual 165	java/lang/IllegalAccessException:printStackTrace	()V
    //   121: aload_0
    //   122: getfield 65	com/starcor/remote_key/DirectKeyEventSender:_getServiceMethod	Ljava/lang/reflect/Method;
    //   125: ifnull +72 -> 197
    //   128: aload_0
    //   129: getfield 65	com/starcor/remote_key/DirectKeyEventSender:_getServiceMethod	Ljava/lang/reflect/Method;
    //   132: aconst_null
    //   133: iconst_1
    //   134: anewarray 129	java/lang/Object
    //   137: dup
    //   138: iconst_0
    //   139: ldc 167
    //   141: aastore
    //   142: invokevirtual 133	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   145: checkcast 169	android/os/IBinder
    //   148: invokestatic 175	android/view/IWindowManager$Stub:asInterface	(Landroid/os/IBinder;)Landroid/view/IWindowManager;
    //   151: aload_1
    //   152: iload_2
    //   153: invokeinterface 180 3 0
    //   158: istore 7
    //   160: getstatic 29	com/starcor/remote_key/DirectKeyEventSender:TAG	Ljava/lang/String;
    //   163: new 145	java/lang/StringBuilder
    //   166: dup
    //   167: invokespecial 146	java/lang/StringBuilder:<init>	()V
    //   170: ldc 182
    //   172: invokevirtual 152	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   175: iload 7
    //   177: invokevirtual 155	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   180: invokevirtual 158	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   183: invokestatic 164	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   186: pop
    //   187: iload 7
    //   189: ireturn
    //   190: astore 6
    //   192: aload 6
    //   194: invokevirtual 165	java/lang/IllegalAccessException:printStackTrace	()V
    //   197: iconst_0
    //   198: ireturn
    //   199: astore 10
    //   201: aload 10
    //   203: invokevirtual 183	java/lang/reflect/InvocationTargetException:printStackTrace	()V
    //   206: goto -85 -> 121
    //   209: astore 9
    //   211: aload 9
    //   213: invokevirtual 115	java/lang/Exception:printStackTrace	()V
    //   216: goto -95 -> 121
    //   219: astore 5
    //   221: aload 5
    //   223: invokevirtual 183	java/lang/reflect/InvocationTargetException:printStackTrace	()V
    //   226: goto -29 -> 197
    //   229: astore 4
    //   231: aload 4
    //   233: invokevirtual 184	android/os/RemoteException:printStackTrace	()V
    //   236: goto -39 -> 197
    //   239: astore_3
    //   240: aload_3
    //   241: invokevirtual 115	java/lang/Exception:printStackTrace	()V
    //   244: goto -47 -> 197
    //
    // Exception table:
    //   from	to	target	type
    //   14	111	114	java/lang/IllegalAccessException
    //   128	187	190	java/lang/IllegalAccessException
    //   14	111	199	java/lang/reflect/InvocationTargetException
    //   14	111	209	java/lang/Exception
    //   128	187	219	java/lang/reflect/InvocationTargetException
    //   128	187	229	android/os/RemoteException
    //   128	187	239	java/lang/Exception
  }

  public boolean sendKey(int paramInt1, int paramInt2)
  {
    Log.d(TAG, "send key begin!!!");
    int i = paramInt1 & 0xFFFF0000;
    int j = paramInt1 & 0xFFFF;
    System.currentTimeMillis();
    int k = 0x40000 & i;
    int m = 0;
    if (k == 262144)
      m = 0 + 2;
    if ((0x10000 & i) == 65536)
      m++;
    if ((0x10000 & i) == 131072)
      m += 4096;
    KeyEvent localKeyEvent1 = new KeyEvent(0L, 0L, 0, j, 0, m);
    KeyEvent localKeyEvent2 = new KeyEvent(0L, 0L, 1, j, 0, m);
    switch (paramInt2)
    {
    default:
      Log.d(TAG, "send key failed!!!");
      Log.d(TAG, "send key success!!!");
      return false;
    case 0:
      return (_injectKeyEvent(localKeyEvent1, true)) && (_injectKeyEvent(localKeyEvent2, true));
    case 1:
      return _injectKeyEvent(localKeyEvent1, true);
    case 2:
    }
    return _injectKeyEvent(localKeyEvent2, true);
  }

  public boolean sendString(String paramString)
  {
    int i = 0;
    if (i < paramString.length())
    {
      int j = KeyMap.getKeyCode(paramString.substring(i, i + 1));
      if (j < 0);
      while (sendKey(j, 0))
      {
        i++;
        break;
      }
      return false;
    }
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.remote_key.DirectKeyEventSender
 * JD-Core Version:    0.6.2
 */