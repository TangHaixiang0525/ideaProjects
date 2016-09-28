package com.xiaomi.account.openauth;

import android.content.Context;
import com.xiaomi.account.openauth.utils.Network;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class AuthorizeApi
{
  private static String HOST = "open.account.xiaomi.com";
  private static String HTTP_PROTOCOL = "https://";
  private static String METHOD_GET = "GET";
  private static final String UTF8 = "UTF-8";

  public static String doHttpGet(Context paramContext, String paramString1, long paramLong, String paramString2)
    throws XMAuthericationException
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicNameValuePair("clientId", String.valueOf(paramLong)));
    localArrayList.add(new BasicNameValuePair("token", paramString2));
    try
    {
      String str = Network.downloadXml(paramContext, new URL(AuthorizeHelper.generateUrl(HTTP_PROTOCOL + HOST + paramString1, localArrayList)), null, null, null, null);
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new XMAuthericationException(localUnsupportedEncodingException);
    }
    catch (IOException localIOException)
    {
      throw new XMAuthericationException(localIOException);
    }
  }

  public static String doHttpGet(Context paramContext, String paramString1, long paramLong, String paramString2, String paramString3, String paramString4)
    throws XMAuthericationException
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicNameValuePair("clientId", String.valueOf(paramLong)));
    localArrayList.add(new BasicNameValuePair("token", paramString2));
    String str1 = AuthorizeHelper.generateNonce();
    try
    {
      String str2 = AuthorizeHelper.getMacAccessTokenSignatureString(str1, METHOD_GET, HOST, paramString1, URLEncodedUtils.format(localArrayList, "UTF-8"), paramString3, paramString4);
      String str3 = Network.downloadXml(paramContext, new URL(AuthorizeHelper.generateUrl(HTTP_PROTOCOL + HOST + paramString1, localArrayList)), null, null, AuthorizeHelper.buildMacRequestHead(paramString2, str1, str2), null);
      return str3;
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      throw new XMAuthericationException(localInvalidKeyException);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new XMAuthericationException(localNoSuchAlgorithmException);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new XMAuthericationException(localUnsupportedEncodingException);
    }
    catch (IOException localIOException)
    {
      throw new XMAuthericationException(localIOException);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.account.openauth.AuthorizeApi
 * JD-Core Version:    0.6.2
 */