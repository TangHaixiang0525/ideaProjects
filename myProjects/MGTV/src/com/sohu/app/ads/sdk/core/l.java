package com.sohu.app.ads.sdk.core;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.sohu.app.ads.sdk.c.a;

class l extends WebViewClient
{
  l(PadDetailsActivity paramPadDetailsActivity)
  {
  }

  public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
  {
    a.b("TEST", "Loading==" + paramString);
    this.a.a(paramWebView, paramString);
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.l
 * JD-Core Version:    0.6.2
 */