package com.sohutv.tv.player.util.a;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.sohu.logger.common.DeviceConstants;
import com.sohutv.tv.player.entity.PlayInfo;
import com.sohutv.tv.player.util.f;
import java.text.MessageFormat;

public class c
{
  public static boolean a = b.k;
  public static final boolean b = b.l;
  public static String c;
  public static String d;
  public static String e;
  public static String f = "120008";
  public static String g = "fd8abb860a1cd5884216dc29a760457f";
  public static String h;
  public static String i;
  public static String j;
  public static String k;
  public static String l;
  public static String m;

  static
  {
    a(b.a.booleanValue());
    String[] arrayOfString = a.a(l, a);
    c = arrayOfString[0];
    e = arrayOfString[1];
    d = c + "/v2/video/playInfo.json?";
    Log.i("Sohuplayer", "h1=" + c + " h2=" + e);
  }

  public static String a()
  {
    StringBuilder localStringBuilder = new StringBuilder().append(c);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = b();
    return String.format("/v2/util/systemconstant.json?%s", arrayOfObject);
  }

  public static String a(Context paramContext)
  {
    DeviceConstants.initInstance(paramContext);
    StringBuilder localStringBuilder = new StringBuilder(DeviceConstants.getInstance().getUID());
    localStringBuilder.append("-").append(k).append("-").append(l).append("-").append(h);
    return localStringBuilder.toString();
  }

  public static String a(PlayInfo paramPlayInfo, String paramString)
  {
    String str1 = d + "video_id=%s&album_id=%s&cate_code=%s&source=%s";
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = String.valueOf(paramPlayInfo.getVideoID());
    arrayOfObject[1] = String.valueOf(paramPlayInfo.getAlbumID());
    arrayOfObject[2] = paramPlayInfo.getCateCode();
    arrayOfObject[3] = paramString;
    String str2 = String.format(str1, arrayOfObject);
    return str2 + "&" + b();
  }

  private static void a(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      i = b.a().b();
      k = b.a().d();
      m = b.a().e();
      l = b.a().f();
      h = b.a().c();
      a = b.k;
      if (!TextUtils.isEmpty(i))
        break label337;
      i = "9999";
      label69: if (!TextUtils.isEmpty(k))
        break label349;
      k = "9999";
      label83: if (!TextUtils.isEmpty(m))
        break label361;
      m = "9999";
      label97: if (!TextUtils.isEmpty(l))
        break label373;
      l = "9999";
      label111: if (!TextUtils.isEmpty(k))
        break label385;
      k = "9999";
      label125: if (!TextUtils.isEmpty(h))
        break label397;
    }
    label385: label397: for (h = "9999"; ; h = h.trim())
    {
      Log.i("Sohuplayer", "isSDK=" + paramBoolean + " parterno=" + l + " poid=" + k + " platform=" + h + " sver=" + i);
      return;
      i = f.a("/assets/common.properties", f.b);
      k = f.a("/assets/common.properties", "poid");
      m = f.a("/assets/common.properties", "client");
      l = f.a("/assets/common.properties", f.a);
      h = f.a("/assets/common.properties", "platform");
      j = f.a("/assets/common.properties", f.c);
      if (TextUtils.isEmpty(j));
      for (j = "10"; ; j = j.trim())
      {
        String str = f.a("/assets/domaincontrol.properties", f.g);
        if (TextUtils.isEmpty(str))
          break;
        if (!"true".equals(str))
          break label330;
        a = true;
        break;
      }
      label330: a = false;
      break;
      label337: i = i.trim();
      break label69;
      label349: k = k.trim();
      break label83;
      label361: m = m.trim();
      break label97;
      label373: l = l.trim();
      break label111;
      k = k.trim();
      break label125;
    }
  }

  private static Object b()
  {
    if (b.a.booleanValue())
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      Object[] arrayOfObject1 = new Object[5];
      arrayOfObject1[0] = "7ad23396564b27116418d3c03a77db45";
      arrayOfObject1[1] = h;
      arrayOfObject1[2] = i;
      arrayOfObject1[3] = l;
      arrayOfObject1[4] = k;
      return MessageFormat.format("api_key={0}&plat={1}&sver={2}&partner={3}&poid={4}", arrayOfObject1) + "&ts=" + String.valueOf(System.currentTimeMillis());
    }
    StringBuilder localStringBuilder2 = new StringBuilder();
    Object[] arrayOfObject2 = new Object[6];
    arrayOfObject2[0] = "7ad23396564b27116418d3c03a77db45";
    arrayOfObject2[1] = h;
    arrayOfObject2[2] = i;
    arrayOfObject2[3] = l;
    arrayOfObject2[4] = k;
    arrayOfObject2[5] = j;
    return MessageFormat.format("api_key={0}&plat={1}&sver={2}&partner={3}&poid={4}&type={5}", arrayOfObject2) + "&ts=" + String.valueOf(System.currentTimeMillis());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.a.c
 * JD-Core Version:    0.6.2
 */