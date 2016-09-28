package com.sohu.app.ads.sdk.model;

import java.util.ArrayList;

public class AdsResponse
{
  private int A = -1;
  private int a = 0;
  private int b = 0;
  private String c = "";
  private String d = "";
  private String e = "";
  private ArrayList<String> f = new ArrayList();
  private int g = 0;
  private String h = "";
  private String i = "";
  private String j = "";
  private String k = "";
  private String l = "";
  private String m = "";
  private String n = "";
  private String o = "";
  private String p = "";
  private String q;
  private ArrayList<d> r = new ArrayList();
  private ArrayList<c> s = new ArrayList();
  private boolean t = false;
  private String u = "";
  private String v = "";
  private ArrayList<String> w = new ArrayList();
  private int x = 0;
  private int y = 0;
  private ArrayList<c> z = new ArrayList();

  public int getAdSequence()
  {
    return this.a;
  }

  public String getAdSystem()
  {
    return this.c;
  }

  public String getAdTitle()
  {
    return this.d;
  }

  public String getClickThrough()
  {
    return this.h;
  }

  public String getClickTracking()
  {
    return this.p;
  }

  public String getComplete()
  {
    return this.o;
  }

  public String getCreativeView()
  {
    return this.j;
  }

  public String getDescription()
  {
    return this.e;
  }

  public String getDisplayKeyword()
  {
    return this.u;
  }

  public int getDuration()
  {
    return this.g;
  }

  public String getFirstQuartile()
  {
    return this.m;
  }

  public ArrayList<String> getImpression()
  {
    return this.f;
  }

  public int getLanguage()
  {
    return this.A;
  }

  public String getMediaFile()
  {
    return this.i;
  }

  public String getMidpoint()
  {
    return this.l;
  }

  public ArrayList<c> getSdkClickTracking()
  {
    return this.s;
  }

  public ArrayList<d> getSdkTracking()
  {
    return this.r;
  }

  public int getSkipSeconds()
  {
    return this.x;
  }

  public ArrayList<String> getSpeakKeyWords()
  {
    return this.w;
  }

  public String getStart()
  {
    return this.k;
  }

  public int getStartSkipSeconds()
  {
    return this.y;
  }

  public String getSuccessKeyword()
  {
    return this.v;
  }

  public String getThirdQuartile()
  {
    return this.n;
  }

  public String getTime()
  {
    return this.q;
  }

  public ArrayList<c> getVoiceExposes()
  {
    return this.z;
  }

  public int isOfflineAd()
  {
    return this.b;
  }

  public boolean isVoiceAd()
  {
    return this.t;
  }

  public void setAdSequence(int paramInt)
  {
    this.a = paramInt;
  }

  public void setAdSystem(String paramString)
  {
    this.c = paramString.trim();
  }

  public void setAdTitle(String paramString)
  {
    if (com.sohu.app.ads.sdk.f.d.a(paramString))
      this.d = paramString.trim();
  }

  public void setClickThrough(String paramString)
  {
    if (com.sohu.app.ads.sdk.f.d.a(paramString))
      this.h = paramString.trim();
  }

  public void setClickTracking(String paramString)
  {
    this.p = paramString;
  }

  public void setComplete(String paramString)
  {
    if (com.sohu.app.ads.sdk.f.d.a(paramString))
      this.o = paramString.trim();
  }

  public void setCreativeView(String paramString)
  {
    if (com.sohu.app.ads.sdk.f.d.a(paramString))
      this.j = paramString.trim();
  }

  public void setDescription(String paramString)
  {
    if (com.sohu.app.ads.sdk.f.d.a(paramString))
      this.e = paramString.trim();
  }

  public void setDisplayKeyword(String paramString)
  {
    this.u = paramString;
  }

  public void setDuration(int paramInt)
  {
    if (paramInt > 0)
    {
      this.g = paramInt;
      return;
    }
    this.g = 0;
  }

  public void setFirstQuartile(String paramString)
  {
    if (com.sohu.app.ads.sdk.f.d.a(paramString))
      this.m = paramString.trim();
  }

  public void setImpression(ArrayList<String> paramArrayList)
  {
    this.f = paramArrayList;
  }

  public void setLanguage(int paramInt)
  {
    this.A = paramInt;
  }

  public void setMediaFile(String paramString)
  {
    if (com.sohu.app.ads.sdk.f.d.a(paramString))
      this.i = paramString.trim();
  }

  public void setMidpoint(String paramString)
  {
    if (com.sohu.app.ads.sdk.f.d.a(paramString))
      this.l = paramString.trim();
  }

  public void setOfflineAd(int paramInt)
  {
    this.b = paramInt;
  }

  public void setSkipSeconds(int paramInt)
  {
    this.x = paramInt;
  }

  public void setStart(String paramString)
  {
    if (com.sohu.app.ads.sdk.f.d.a(paramString))
      this.k = paramString.trim();
  }

  public void setStartSkipSeconds(int paramInt)
  {
    this.y = paramInt;
  }

  public void setSuccessKeyword(String paramString)
  {
    this.v = paramString;
  }

  public void setThirdQuartile(String paramString)
  {
    if (com.sohu.app.ads.sdk.f.d.a(paramString))
      this.n = paramString.trim();
  }

  public void setTime(String paramString)
  {
    this.q = paramString;
  }

  public void setVoiceAd(boolean paramBoolean)
  {
    this.t = paramBoolean;
  }

  public String toString()
  {
    return "AdsResponse [AdSequence=" + this.a + ", isOfflineAd=" + this.b + ", AdSystem=" + this.c + ", AdTitle=" + this.d + ", Description=" + this.e + ", Impression=" + this.f + ", Duration=" + this.g + ", ClickThrough=" + this.h + ", MediaFile=" + this.i + ", creativeView=" + this.j + ", start=" + this.k + ", midpoint=" + this.l + ", firstQuartile=" + this.m + ", thirdQuartile=" + this.n + ", complete=" + this.o + ", ClickTracking=" + this.p + ", time=" + this.q + ", sdkTracking=" + this.r + ", sdkClickTracking=" + this.s + ", isVoiceAd=" + this.t + ", mDisplayKeyword=" + this.u + ", mSuccessKeyword=" + this.v + ", mSpeakKeyWords=" + this.w + ", mSkipSeconds=" + this.x + ", mStartSkipSeconds=" + this.y + ", mVoiceExposes=" + this.z + ", mLanguage=" + this.A + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.model.AdsResponse
 * JD-Core Version:    0.6.2
 */