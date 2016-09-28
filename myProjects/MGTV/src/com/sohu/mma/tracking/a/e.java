package com.sohu.mma.tracking.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.sohu.mma.tracking.b.b;
import com.sohu.mma.tracking.b.c;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class e
{
  private Context a;
  private Map<String, String> b;

  public e(Context paramContext)
  {
    this.a = paramContext;
    this.b = com.sohu.mma.tracking.c.d.h(paramContext);
  }

  private long a(b paramb, Long paramLong)
  {
    if (paramb.d.b != null)
      return 1000L * Long.parseLong(paramb.d.b.trim()) + paramLong.longValue();
    return 86400000L + paramLong.longValue();
  }

  private void a(com.sohu.mma.tracking.b.h paramh)
  {
    this.b.put("LBS", com.sohu.mma.tracking.c.e.a(this.a).a());
    StringBuilder localStringBuilder1 = new StringBuilder();
    String str1 = com.sohu.mma.tracking.c.a.a(paramh.a());
    com.sohu.mma.tracking.b.g localg = com.sohu.mma.tracking.c.h.b(this.a);
    Iterator localIterator1;
    Object localObject1;
    if ((localg != null) && (localg.b != null))
    {
      localIterator1 = localg.b.iterator();
      localObject1 = localStringBuilder1;
    }
    while (true)
    {
      if (!localIterator1.hasNext())
        return;
      b localb = (b)localIterator1.next();
      if (str1.endsWith(localb.b.a))
      {
        ArrayList localArrayList1 = new ArrayList();
        Iterator localIterator2 = localb.e.a.iterator();
        Object localObject2 = "";
        Object localObject3 = "";
        label147: Iterator localIterator3;
        label203: StringBuilder localStringBuilder2;
        if (!localIterator2.hasNext())
        {
          ((StringBuilder)localObject1).append((String)com.sohu.mma.tracking.c.a.a(paramh.a(), localArrayList1, (String)localObject3, (String)localObject2, "").get("URL"));
          localIterator3 = localb.e.a.iterator();
          if (localIterator3.hasNext())
            break label404;
          ArrayList localArrayList2 = new ArrayList();
          localStringBuilder2 = new StringBuilder(com.sohu.mma.tracking.c.a.a(((StringBuilder)localObject1).toString(), localArrayList2, (String)localObject3, (String)localObject2));
          localStringBuilder2.append("");
          localStringBuilder2.append(a.e);
        }
        try
        {
          String str5 = URLEncoder.encode(localStringBuilder2.toString(), "UTF-8").toLowerCase().replaceAll("%2f", "/").replaceAll("%3a", ":");
          str4 = str5;
          com.sohu.mma.tracking.c.g.a("mma_request_url" + str4);
          com.sohu.mma.tracking.c.j.a(this.a, "cn.com.mma.mobile.tracking.normal", str4, a(localb, Long.valueOf(paramh.b())));
          localObject1 = localStringBuilder2;
          continue;
          com.sohu.mma.tracking.b.a locala1 = (com.sohu.mma.tracking.b.a)localIterator2.next();
          if (!locala1.d)
            break label147;
          String str2 = localb.f;
          String str3 = localb.g;
          localArrayList1.add(locala1.b);
          localObject3 = str2;
          localObject2 = str3;
          break label147;
          label404: com.sohu.mma.tracking.b.a locala2 = (com.sohu.mma.tracking.b.a)localIterator3.next();
          if (!locala2.d)
            break label203;
          if ("TS".equals(locala2.a))
          {
            StringBuilder localStringBuilder5 = new StringBuilder(String.valueOf(localb.f)).append(locala2.b);
            String str8;
            label477: StringBuilder localStringBuilder6;
            if (localb.g != null)
            {
              str8 = localb.g;
              localStringBuilder6 = localStringBuilder5.append(str8);
              if (!localb.h)
                break label526;
            }
            label526: for (long l = paramh.b(); ; l = paramh.b())
            {
              ((StringBuilder)localObject1).append(l);
              break;
              str8 = "";
              break label477;
            }
          }
          if ("MUDS".equals(locala2.a))
          {
            StringBuilder localStringBuilder4 = new StringBuilder(String.valueOf(localb.f)).append(locala2.b);
            if (localb.g != null);
            for (String str7 = localb.g; ; str7 = "")
            {
              ((StringBuilder)localObject1).append(str7 + com.sohu.mma.tracking.c.a.a(paramh.a, locala2, localb));
              break;
            }
          }
          if ("REDIRECTURL".equals(locala2.a))
          {
            Matcher localMatcher = Pattern.compile(localb.f + locala2.b + ".*").matcher(paramh.a());
            if (!localMatcher.find())
              break label203;
            a.e = localMatcher.group(0);
            com.sohu.mma.tracking.c.g.a("mma_redirect_url :" + a.e);
            break label203;
          }
          StringBuilder localStringBuilder3 = new StringBuilder(String.valueOf(localb.f)).append(locala2.b);
          if (localb.g != null);
          for (String str6 = localb.g; ; str6 = "")
          {
            ((StringBuilder)localObject1).append(str6 + com.sohu.mma.tracking.c.a.a((String)this.b.get(locala2.a), locala2, localb));
            break;
          }
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          while (true)
          {
            localUnsupportedEncodingException.printStackTrace();
            String str4 = localStringBuilder2.toString();
          }
        }
      }
      else
      {
        com.sohu.mma.tracking.c.g.a("domain不匹配" + str1 + " company.domain.url:" + localb.b.a);
      }
    }
  }

  public void a(String paramString)
  {
    if (com.sohu.mma.tracking.c.d.j(this.a))
      com.sohu.mma.tracking.c.g.a("模拟器不记录，不发送数据");
    do
    {
      return;
      com.sohu.mma.tracking.b.h localh = new com.sohu.mma.tracking.b.h();
      localh.a(System.currentTimeMillis());
      localh.a(paramString.replaceAll(" ", ""));
      a(localh);
    }
    while (com.sohu.mma.tracking.c.j.a(this.a, "cn.com.mma.mobile.tracking.normal").getAll().keySet().size() < a.a);
    f.a(this.a).a();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mma.tracking.a.e
 * JD-Core Version:    0.6.2
 */