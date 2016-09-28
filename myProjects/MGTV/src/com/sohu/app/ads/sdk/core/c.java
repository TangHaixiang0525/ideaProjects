package com.sohu.app.ads.sdk.core;

import android.view.ViewGroup;
import com.sohu.app.ads.sdk.d.f;
import com.sohu.app.ads.sdk.iterface.PopWindowCallback;
import com.sohu.mobile.tracing.plugin.b;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAction;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAdBoby;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;
import java.util.ArrayList;
import java.util.Iterator;

class c
  implements f
{
  c(a parama, ViewGroup paramViewGroup, int[] paramArrayOfInt, PopWindowCallback paramPopWindowCallback)
  {
  }

  public void a(int paramInt, Object paramObject)
  {
    if ((paramInt == 2) && (paramObject != null))
    {
      com.sohu.app.ads.sdk.model.a locala = (com.sohu.app.ads.sdk.model.a)paramObject;
      if (!com.sohu.app.ads.sdk.f.d.a(locala.e()))
      {
        ArrayList localArrayList = locala.a();
        if ((localArrayList != null) && (localArrayList.size() > 0))
        {
          Iterator localIterator = localArrayList.iterator();
          while (localIterator.hasNext())
          {
            String str = (String)localIterator.next();
            b.b().a(Plugin_ExposeAdBoby.PAD, str, Plugin_VastTag.VAST_IMPRESSION, Plugin_ExposeAction.EXPOSE_SHOW);
          }
        }
      }
      else
      {
        a.a().a(locala, new d(this, locala));
        r.a().a(this.a, a.a(), this.b);
        this.c.onOpenResult(true);
      }
      return;
    }
    this.c.onOpenResult(false);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.c
 * JD-Core Version:    0.6.2
 */