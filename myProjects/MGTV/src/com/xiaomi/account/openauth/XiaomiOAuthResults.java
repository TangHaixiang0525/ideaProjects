package com.xiaomi.account.openauth;

import android.os.Bundle;

public class XiaomiOAuthResults
{
  public final Error errorResult;
  public final Success successResult;

  public XiaomiOAuthResults(Error paramError)
  {
    this.successResult = null;
    this.errorResult = paramError;
  }

  public XiaomiOAuthResults(Success paramSuccess)
  {
    this.successResult = paramSuccess;
    this.errorResult = null;
  }

  private static int getIntCompatibly(Bundle paramBundle, String paramString1, String paramString2)
  {
    if (paramBundle.containsKey(paramString1))
      return paramBundle.getInt(paramString1);
    return paramBundle.getInt(paramString2);
  }

  private static String getStringCompatibly(Bundle paramBundle, String paramString1, String paramString2)
  {
    if (paramBundle.containsKey(paramString1))
      return paramBundle.getString(paramString1);
    return paramBundle.getString(paramString2);
  }

  public static XiaomiOAuthResults parseBundle(Bundle paramBundle)
  {
    if (paramBundle == null)
      return null;
    if (getIntCompatibly(paramBundle, "extra_error_code", "error") != 0)
      return new XiaomiOAuthResults(Error.parseBundle(paramBundle));
    return new XiaomiOAuthResults(Success.parseBundle(paramBundle));
  }

  public String toString()
  {
    if (this.successResult != null)
      return this.successResult.toString();
    if (this.errorResult != null)
      return this.errorResult.toString();
    throw new IllegalStateException("should not be here.");
  }

  public static class Error
  {
    public final int errorCode;
    public final String errorMessage;

    public Error(int paramInt, String paramString)
    {
      this.errorCode = paramInt;
      this.errorMessage = paramString;
    }

    private static Error parseBundle(Bundle paramBundle)
    {
      return new Error(Integer.valueOf(XiaomiOAuthResults.getStringCompatibly(paramBundle, "extra_error_code", "error")).intValue(), XiaomiOAuthResults.getStringCompatibly(paramBundle, "extra_error_description", "error_description"));
    }

    public String toString()
    {
      return "errorCode=" + this.errorCode + ",errorMessage=" + this.errorMessage;
    }
  }

  public static class Success
  {
    public final String accessToken;
    public final String code;
    public final String expiresIn;
    public final String macAlgorithm;
    public final String macKey;
    public final String scopes;
    public final String state;
    public final String tokenType;

    public Success(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
    {
      this.accessToken = paramString1;
      this.expiresIn = paramString2;
      this.scopes = paramString3;
      this.state = paramString4;
      this.tokenType = paramString5;
      this.macKey = paramString6;
      this.macAlgorithm = paramString7;
      this.code = paramString8;
    }

    private static Success parseBundle(Bundle paramBundle)
    {
      String str1 = XiaomiOAuthResults.getStringCompatibly(paramBundle, "access_token", "extra_access_token");
      String str2 = XiaomiOAuthResults.getStringCompatibly(paramBundle, "mac_key", "extra_mac_key");
      String str3 = XiaomiOAuthResults.getStringCompatibly(paramBundle, "mac_algorithm", "extra_mac_algorithm");
      return new Success(str1, XiaomiOAuthResults.getStringCompatibly(paramBundle, "expires_in", "extra_expires_in"), XiaomiOAuthResults.getStringCompatibly(paramBundle, "scope", "extra_scope"), XiaomiOAuthResults.getStringCompatibly(paramBundle, "state", "extra_state"), XiaomiOAuthResults.getStringCompatibly(paramBundle, "token_type", "extra_token_type"), str2, str3, XiaomiOAuthResults.getStringCompatibly(paramBundle, "code", "extra_code"));
    }

    public String toString()
    {
      return "accessToken=" + this.accessToken + ",expiresIn=" + this.expiresIn + ",scope=" + this.scopes + ",state=" + this.state + ",tokenType=" + this.tokenType + ",macKey=" + this.macKey + ",macAlogorithm=" + this.macAlgorithm + ",code=" + this.code;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.account.openauth.XiaomiOAuthResults
 * JD-Core Version:    0.6.2
 */