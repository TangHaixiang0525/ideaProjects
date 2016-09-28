package com.sohutv.tv.player.util;

import android.util.Log;
import com.google.gson.Gson;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

public class HttpAPI
{
  public static String tag = "HTTPAPI";

  public static <T> void asypost(String paramString, List<NameValuePair> paramList, Class<T> paramClass, SuccessListener<T> paramSuccessListener, ErrorListener paramErrorListener)
  {
    doHttpPost(paramString, paramList, paramClass, paramSuccessListener, paramErrorListener);
  }

  private static void doFailure(int paramInt, Throwable paramThrowable, ErrorListener paramErrorListener)
  {
    if ((paramThrowable != null) && (paramThrowable.getMessage() != null))
    {
      paramErrorListener.onErrorResponse(paramInt, paramThrowable);
      return;
    }
    paramErrorListener.onErrorResponse(-1, new Throwable("网络请求错误"));
  }

  private static <T> void doHttpGet(String paramString, final Class<T> paramClass, SuccessListener<T> paramSuccessListener, final ErrorListener paramErrorListener)
  {
    b.a(paramString, new c()
    {
      public void a(int paramAnonymousInt, Header[] paramAnonymousArrayOfHeader, String paramAnonymousString)
      {
        HttpAPI.doScecess(paramAnonymousInt, paramAnonymousArrayOfHeader, paramAnonymousString, this.a, paramErrorListener, paramClass);
      }

      public void a(int paramAnonymousInt, Header[] paramAnonymousArrayOfHeader, String paramAnonymousString, Throwable paramAnonymousThrowable)
      {
        HttpAPI.doFailure(paramAnonymousInt, paramAnonymousThrowable, paramErrorListener);
      }
    });
  }

  private static <T> void doHttpPost(String paramString, List<NameValuePair> paramList, Class<T> paramClass, final SuccessListener<T> paramSuccessListener, final ErrorListener paramErrorListener)
  {
    b.a(paramString, paramList, new c()
    {
      public void a(int paramAnonymousInt, Header[] paramAnonymousArrayOfHeader, String paramAnonymousString)
      {
        try
        {
          Object localObject = new Gson().fromJson(paramAnonymousString, this.a);
          paramSuccessListener.onSuccessResponse(localObject);
          return;
        }
        catch (Exception localException)
        {
          HttpAPI.doFailure(paramAnonymousInt, localException.getCause(), paramErrorListener);
          localException.printStackTrace();
        }
      }

      public void a(int paramAnonymousInt, Header[] paramAnonymousArrayOfHeader, String paramAnonymousString, Throwable paramAnonymousThrowable)
      {
        HttpAPI.doFailure(paramAnonymousInt, paramAnonymousThrowable, paramErrorListener);
      }
    });
  }

  // ERROR //
  private static <T> void doScecess(int paramInt, Header[] paramArrayOfHeader, String paramString, SuccessListener<T> paramSuccessListener, ErrorListener paramErrorListener, Class<T> paramClass)
  {
    // Byte code:
    //   0: new 72	com/google/gson/Gson
    //   3: dup
    //   4: invokespecial 73	com/google/gson/Gson:<init>	()V
    //   7: astore 6
    //   9: new 75	org/json/JSONObject
    //   12: dup
    //   13: aload_2
    //   14: invokespecial 76	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   17: astore 7
    //   19: aload 7
    //   21: ldc 78
    //   23: invokevirtual 82	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   26: astore 10
    //   28: ldc 84
    //   30: new 86	java/lang/StringBuilder
    //   33: dup
    //   34: invokespecial 87	java/lang/StringBuilder:<init>	()V
    //   37: ldc 89
    //   39: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: aload_2
    //   43: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   49: invokestatic 102	com/sohu/logger/log/OutputLog:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   52: aload 10
    //   54: invokestatic 108	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   57: ifeq +28 -> 85
    //   60: aload 4
    //   62: iconst_m1
    //   63: new 32	java/lang/Throwable
    //   66: dup
    //   67: ldc 110
    //   69: invokespecial 47	java/lang/Throwable:<init>	(Ljava/lang/String;)V
    //   72: invokeinterface 42 3 0
    //   77: aload 6
    //   79: ifnull +98 -> 177
    //   82: goto +95 -> 177
    //   85: aload_3
    //   86: aload 6
    //   88: aload 10
    //   90: aload 5
    //   92: invokevirtual 114	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   95: invokeinterface 120 2 0
    //   100: aload 6
    //   102: ifnull +3 -> 105
    //   105: aload 7
    //   107: ifnull +75 -> 182
    //   110: return
    //   111: astore 8
    //   113: aconst_null
    //   114: astore 7
    //   116: aload 8
    //   118: invokevirtual 123	java/lang/Exception:printStackTrace	()V
    //   121: aload 4
    //   123: iconst_m1
    //   124: new 32	java/lang/Throwable
    //   127: dup
    //   128: ldc 125
    //   130: invokespecial 47	java/lang/Throwable:<init>	(Ljava/lang/String;)V
    //   133: invokeinterface 42 3 0
    //   138: aload 6
    //   140: ifnull +3 -> 143
    //   143: aload 7
    //   145: ifnull +37 -> 182
    //   148: return
    //   149: astore 9
    //   151: aconst_null
    //   152: astore 7
    //   154: aload 6
    //   156: ifnull +3 -> 159
    //   159: aload 7
    //   161: ifnull +3 -> 164
    //   164: aload 9
    //   166: athrow
    //   167: astore 9
    //   169: goto -15 -> 154
    //   172: astore 8
    //   174: goto -58 -> 116
    //   177: aload 7
    //   179: ifnull +3 -> 182
    //   182: return
    //
    // Exception table:
    //   from	to	target	type
    //   9	19	111	java/lang/Exception
    //   9	19	149	finally
    //   19	77	167	finally
    //   85	100	167	finally
    //   116	138	167	finally
    //   19	77	172	java/lang/Exception
    //   85	100	172	java/lang/Exception
  }

  public static <T> void get(String paramString, Class<T> paramClass, SuccessListener<T> paramSuccessListener, ErrorListener paramErrorListener)
  {
    doHttpGet(paramString, paramClass, paramSuccessListener, paramErrorListener);
  }

  public static <T> T synpost(String paramString, List<NameValuePair> paramList, Class<T> paramClass)
  {
    try
    {
      HttpResponse localHttpResponse = b.a(paramString, paramList);
      if ((localHttpResponse != null) && (localHttpResponse.getStatusLine().getStatusCode() == 200))
      {
        String str = EntityUtils.toString(localHttpResponse.getEntity(), "GBK");
        return new Gson().fromJson(str, paramClass);
      }
      Log.e("Sohuplayer", "synpost is fail");
      return null;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static abstract interface ErrorListener
  {
    public abstract void onErrorResponse(int paramInt, Throwable paramThrowable);
  }

  public static abstract interface SuccessListener<T>
  {
    public abstract void onSuccessResponse(T paramT);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.HttpAPI
 * JD-Core Version:    0.6.2
 */