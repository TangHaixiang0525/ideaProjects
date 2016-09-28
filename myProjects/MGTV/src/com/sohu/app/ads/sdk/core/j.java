package com.sohu.app.ads.sdk.core;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.sohu.app.ads.sdk.exception.SdkException;
import com.sohu.app.ads.sdk.iterface.IDisplayLoader;
import com.sohu.app.ads.sdk.iterface.ImageDownloadCallback;
import com.sohu.mobile.tracing.plugin.b;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAction;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAdBoby;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class j extends i
  implements IDisplayLoader
{
  private final String c = "9999";

  public j(Context paramContext)
  {
    super(paramContext);
  }

  private void a(com.sohu.app.ads.sdk.model.a parama)
  {
    ArrayList localArrayList1 = parama.a();
    if ((localArrayList1 != null) && (localArrayList1.size() > 0))
    {
      Iterator localIterator2 = localArrayList1.iterator();
      while (localIterator2.hasNext())
      {
        String str3 = (String)localIterator2.next();
        b.b().a(Plugin_ExposeAdBoby.OPEN, str3, Plugin_VastTag.VAST_IMPRESSION, Plugin_ExposeAction.EXPOSE_SHOW);
      }
    }
    while (true)
    {
      String str1;
      String str2;
      try
      {
        ArrayList localArrayList2 = parama.b();
        if ((localArrayList2 != null) && (localArrayList2.size() > 0))
        {
          Iterator localIterator1 = localArrayList2.iterator();
          if (localIterator1.hasNext())
          {
            com.sohu.app.ads.sdk.model.d locald = (com.sohu.app.ads.sdk.model.d)localIterator1.next();
            str1 = locald.a();
            str2 = locald.b();
            if (!"admaster".equalsIgnoreCase(str1))
              break label166;
            b.b().a(Plugin_ExposeAdBoby.OPEN, str2, Plugin_VastTag.ADMASTER, Plugin_ExposeAction.EXPOSE_SHOW);
            continue;
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return;
      label166: if ("sohutv".equalsIgnoreCase(str1))
        b.b().a(Plugin_ExposeAdBoby.OPEN, str2, Plugin_VastTag.SOHUSDK, Plugin_ExposeAction.EXPOSE_SHOW);
      else if ("miaozhen".equalsIgnoreCase(str1))
        b.b().a(Plugin_ExposeAdBoby.OPEN, str2, Plugin_VastTag.MIAOZHEN, Plugin_ExposeAction.EXPOSE_SHOW);
    }
  }

  public void destory()
  {
  }

  public void requestSystemImage(Context paramContext, String paramString, HashMap<String, String> paramHashMap, ImageDownloadCallback paramImageDownloadCallback)
  {
    if (paramHashMap == null)
      throw new SdkException("HashMap mParams is null");
    if (!paramHashMap.containsKey("oplat"))
      throw new SdkException("map中缺少IParams.PARAM_OPLAT参数");
    if (!paramHashMap.containsKey("partner"))
      throw new SdkException("map中缺少IParams.PARAM_PARTNER[产品渠道]100101参数");
    if (!paramHashMap.containsKey("oprod"))
      throw new SdkException("map中缺少IParams.PARAM_OPROD参数");
    com.sohu.app.ads.sdk.a.d locald = new com.sohu.app.ads.sdk.a.d(paramContext);
    if (paramHashMap.containsKey("url"));
    for (String str1 = (String)paramHashMap.remove("url"); ; str1 = "http://m.aty.sohu.com/m")
    {
      paramHashMap.put("pt", "bootscreen");
      paramHashMap.put("plat", "ott1");
      paramHashMap.put("poscode", "boot_hmd_1");
      paramHashMap.put("sysver", Build.VERSION.SDK);
      paramHashMap.put("prot", "vast");
      paramHashMap.put("protv", "3.0");
      paramHashMap.put("density", com.sohu.app.ads.sdk.f.d.g());
      paramHashMap.put("displayMetrics", com.sohu.app.ads.sdk.f.d.f());
      paramHashMap.put("manufacturer", Build.MANUFACTURER);
      paramHashMap.put("sdkVersion", "2.1.13");
      paramHashMap.put("imei", com.sohu.app.ads.sdk.f.d.b());
      paramHashMap.put("imsi", com.sohu.app.ads.sdk.f.d.c());
      paramHashMap.put("appid", "ott-net");
      paramHashMap.put("c", "ottnet");
      paramHashMap.put("poid", "1");
      paramHashMap.put("oplat", "15");
      paramHashMap.put("oprod", "12");
      paramHashMap.put("UUID", com.sohu.app.ads.sdk.f.d.j());
      StringBuilder localStringBuilder = new StringBuilder();
      Iterator localIterator = paramHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str4 = (String)localIterator.next();
        localStringBuilder.append(str4 + "=" + (String)paramHashMap.get(str4) + "&");
      }
      String str2 = localStringBuilder.toString();
      String str3 = str2.substring(0, -1 + str2.length());
      this.b.a(str1, str3, new k(this, paramImageDownloadCallback, locald), 6);
      return;
    }
  }

  public void uploadSystemImageData(Context paramContext)
  {
    com.sohu.app.ads.sdk.model.a locala = new com.sohu.app.ads.sdk.a.d(paramContext).b("9999");
    if (locala != null)
      a(locala);
    b.b().a();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.j
 * JD-Core Version:    0.6.2
 */