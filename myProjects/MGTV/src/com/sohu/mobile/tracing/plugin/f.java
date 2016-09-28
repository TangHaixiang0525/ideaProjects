package com.sohu.mobile.tracing.plugin;

import com.sohu.mobile.a.a.d;
import com.sohu.mobile.a.a.e;
import com.sohu.mobile.tracing.plugin.b.a;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAdBoby;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;

class f
  implements Runnable
{
  private a b;

  f(b paramb, a parama)
  {
    this.b = parama;
  }

  private void a(String paramString1, Plugin_VastTag paramPlugin_VastTag, Plugin_ExposeAdBoby paramPlugin_ExposeAdBoby, String paramString2)
  {
    try
    {
      if ((paramPlugin_ExposeAdBoby == Plugin_ExposeAdBoby.OAD) && (paramPlugin_VastTag == Plugin_VastTag.VAST_IMPRESSION) && (paramString1.contains("sohu.com")))
        e.c().a(paramString2);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 19	com/sohu/mobile/tracing/plugin/f:b	Lcom/sohu/mobile/tracing/plugin/b/a;
    //   4: invokevirtual 62	com/sohu/mobile/tracing/plugin/b/a:c	()Ljava/lang/String;
    //   7: astore_2
    //   8: aload_0
    //   9: getfield 19	com/sohu/mobile/tracing/plugin/f:b	Lcom/sohu/mobile/tracing/plugin/b/a;
    //   12: invokevirtual 66	com/sohu/mobile/tracing/plugin/b/a:e	()Lcom/sohu/mobile/tracing/plugin/expose/Plugin_VastTag;
    //   15: astore_3
    //   16: aload_0
    //   17: getfield 19	com/sohu/mobile/tracing/plugin/f:b	Lcom/sohu/mobile/tracing/plugin/b/a;
    //   20: invokevirtual 70	com/sohu/mobile/tracing/plugin/b/a:f	()Lcom/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAction;
    //   23: astore 4
    //   25: aload_0
    //   26: getfield 19	com/sohu/mobile/tracing/plugin/f:b	Lcom/sohu/mobile/tracing/plugin/b/a;
    //   29: invokevirtual 74	com/sohu/mobile/tracing/plugin/b/a:d	()Lcom/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAdBoby;
    //   32: astore 5
    //   34: getstatic 79	com/sohu/mobile/tracing/plugin/d:a	[I
    //   37: aload_3
    //   38: invokevirtual 83	com/sohu/mobile/tracing/plugin/expose/Plugin_VastTag:ordinal	()I
    //   41: iaload
    //   42: tableswitch	default:+22 -> 64, 1:+145->187, 2:+245->287
    //   65: aload_2
    //   66: aload_3
    //   67: aload 5
    //   69: ldc 85
    //   71: invokespecial 87	com/sohu/mobile/tracing/plugin/f:a	(Ljava/lang/String;Lcom/sohu/mobile/tracing/plugin/expose/Plugin_VastTag;Lcom/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAdBoby;Ljava/lang/String;)V
    //   74: invokestatic 92	com/sohu/mobile/tracing/plugin/c/a:a	()Lcom/sohu/mobile/tracing/plugin/c/a;
    //   77: aload_0
    //   78: getfield 19	com/sohu/mobile/tracing/plugin/f:b	Lcom/sohu/mobile/tracing/plugin/b/a;
    //   81: invokevirtual 95	com/sohu/mobile/tracing/plugin/c/a:a	(Lcom/sohu/mobile/tracing/plugin/b/a;)Z
    //   84: ifeq +241 -> 325
    //   87: aload_0
    //   88: aload_2
    //   89: aload_3
    //   90: aload 5
    //   92: ldc 97
    //   94: invokespecial 87	com/sohu/mobile/tracing/plugin/f:a	(Ljava/lang/String;Lcom/sohu/mobile/tracing/plugin/expose/Plugin_VastTag;Lcom/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAdBoby;Ljava/lang/String;)V
    //   97: new 99	java/lang/StringBuilder
    //   100: dup
    //   101: invokespecial 100	java/lang/StringBuilder:<init>	()V
    //   104: ldc 102
    //   106: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: aload_3
    //   110: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   113: ldc 111
    //   115: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: aload_2
    //   119: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   125: invokestatic 117	com/sohu/mobile/tracing/plugin/d/b:a	(Ljava/lang/String;)V
    //   128: aload_0
    //   129: getfield 19	com/sohu/mobile/tracing/plugin/f:b	Lcom/sohu/mobile/tracing/plugin/b/a;
    //   132: invokevirtual 121	com/sohu/mobile/tracing/plugin/b/a:g	()Lcom/sohu/applist/a/b;
    //   135: astore 12
    //   137: aload 12
    //   139: ifnull +47 -> 186
    //   142: aload 12
    //   144: invokevirtual 126	com/sohu/applist/a/b:a	()Ljava/lang/Boolean;
    //   147: invokevirtual 132	java/lang/Boolean:booleanValue	()Z
    //   150: ifeq +14 -> 164
    //   153: invokestatic 137	com/sohu/mobile/tracing/plugin/b:d	()Landroid/content/Context;
    //   156: aload 12
    //   158: invokevirtual 139	com/sohu/applist/a/b:d	()Ljava/lang/String;
    //   161: invokestatic 145	com/sohu/upload/UploadPostData:deleteAppInfo	(Landroid/content/Context;Ljava/lang/String;)V
    //   164: aload 12
    //   166: invokevirtual 147	com/sohu/applist/a/b:b	()Ljava/lang/Boolean;
    //   169: invokevirtual 132	java/lang/Boolean:booleanValue	()Z
    //   172: ifeq +14 -> 186
    //   175: invokestatic 137	com/sohu/mobile/tracing/plugin/b:d	()Landroid/content/Context;
    //   178: aload 12
    //   180: invokevirtual 149	com/sohu/applist/a/b:e	()I
    //   183: invokestatic 153	com/sohu/upload/UploadPostData:deleteBaseStation	(Landroid/content/Context;I)V
    //   186: return
    //   187: new 99	java/lang/StringBuilder
    //   190: dup
    //   191: invokespecial 100	java/lang/StringBuilder:<init>	()V
    //   194: ldc 102
    //   196: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   199: aload_3
    //   200: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   203: ldc 155
    //   205: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: aload_2
    //   209: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   212: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   215: invokestatic 157	com/sohu/mobile/tracing/plugin/d/b:b	(Ljava/lang/String;)V
    //   218: getstatic 163	com/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAction:EXPOSE_SHOW	Lcom/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAction;
    //   221: astore 7
    //   223: aload 4
    //   225: aload 7
    //   227: if_acmpne +13 -> 240
    //   230: invokestatic 168	com/sohu/mma/tracking/a/b:a	()Lcom/sohu/mma/tracking/a/b;
    //   233: aload_2
    //   234: invokevirtual 171	java/lang/String:trim	()Ljava/lang/String;
    //   237: invokevirtual 172	com/sohu/mma/tracking/a/b:b	(Ljava/lang/String;)V
    //   240: getstatic 175	com/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAction:EXPOSE_CLICK	Lcom/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAction;
    //   243: astore 8
    //   245: aload 4
    //   247: aload 8
    //   249: if_acmpne -63 -> 186
    //   252: invokestatic 168	com/sohu/mma/tracking/a/b:a	()Lcom/sohu/mma/tracking/a/b;
    //   255: aload_2
    //   256: invokevirtual 171	java/lang/String:trim	()Ljava/lang/String;
    //   259: invokevirtual 176	com/sohu/mma/tracking/a/b:a	(Ljava/lang/String;)V
    //   262: return
    //   263: astore 9
    //   265: aload 9
    //   267: invokevirtual 56	java/lang/Exception:printStackTrace	()V
    //   270: return
    //   271: astore_1
    //   272: aload_1
    //   273: invokevirtual 56	java/lang/Exception:printStackTrace	()V
    //   276: return
    //   277: astore 10
    //   279: aload 10
    //   281: invokevirtual 56	java/lang/Exception:printStackTrace	()V
    //   284: goto -44 -> 240
    //   287: invokestatic 179	com/sohu/mobile/tracing/plugin/b:e	()Landroid/os/Handler;
    //   290: ifnull -104 -> 186
    //   293: invokestatic 179	com/sohu/mobile/tracing/plugin/b:e	()Landroid/os/Handler;
    //   296: bipush 10
    //   298: aload_2
    //   299: invokevirtual 171	java/lang/String:trim	()Ljava/lang/String;
    //   302: invokevirtual 185	android/os/Handler:obtainMessage	(ILjava/lang/Object;)Landroid/os/Message;
    //   305: invokevirtual 190	android/os/Message:sendToTarget	()V
    //   308: return
    //   309: astore 6
    //   311: aload 6
    //   313: invokevirtual 56	java/lang/Exception:printStackTrace	()V
    //   316: return
    //   317: astore 11
    //   319: aload 11
    //   321: invokevirtual 56	java/lang/Exception:printStackTrace	()V
    //   324: return
    //   325: aload_0
    //   326: aload_2
    //   327: aload_3
    //   328: aload 5
    //   330: ldc 192
    //   332: invokespecial 87	com/sohu/mobile/tracing/plugin/f:a	(Ljava/lang/String;Lcom/sohu/mobile/tracing/plugin/expose/Plugin_VastTag;Lcom/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAdBoby;Ljava/lang/String;)V
    //   335: new 99	java/lang/StringBuilder
    //   338: dup
    //   339: invokespecial 100	java/lang/StringBuilder:<init>	()V
    //   342: ldc 102
    //   344: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   347: aload_3
    //   348: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   351: ldc 194
    //   353: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   356: aload_2
    //   357: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   360: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   363: invokestatic 157	com/sohu/mobile/tracing/plugin/d/b:b	(Ljava/lang/String;)V
    //   366: invokestatic 197	com/sohu/mobile/tracing/plugin/b:f	()Lcom/sohu/mobile/tracing/plugin/a/b;
    //   369: aload_0
    //   370: getfield 19	com/sohu/mobile/tracing/plugin/f:b	Lcom/sohu/mobile/tracing/plugin/b/a;
    //   373: invokevirtual 202	com/sohu/mobile/tracing/plugin/a/b:a	(Lcom/sohu/mobile/tracing/plugin/b/a;)V
    //   376: return
    //
    // Exception table:
    //   from	to	target	type
    //   252	262	263	java/lang/Exception
    //   0	64	271	java/lang/Exception
    //   64	128	271	java/lang/Exception
    //   187	223	271	java/lang/Exception
    //   240	245	271	java/lang/Exception
    //   265	270	271	java/lang/Exception
    //   279	284	271	java/lang/Exception
    //   311	316	271	java/lang/Exception
    //   319	324	271	java/lang/Exception
    //   325	376	271	java/lang/Exception
    //   230	240	277	java/lang/Exception
    //   287	308	309	java/lang/Exception
    //   128	137	317	java/lang/Exception
    //   142	164	317	java/lang/Exception
    //   164	186	317	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mobile.tracing.plugin.f
 * JD-Core Version:    0.6.2
 */