package com.mstar.android.sip;

public abstract interface SipRegistrationListener
{
  public abstract void onRegistering(String paramString);

  public abstract void onRegistrationDone(String paramString, long paramLong);

  public abstract void onRegistrationFailed(String paramString1, int paramInt, String paramString2);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.sip.SipRegistrationListener
 * JD-Core Version:    0.6.2
 */