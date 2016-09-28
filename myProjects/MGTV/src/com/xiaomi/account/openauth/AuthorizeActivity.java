package com.xiaomi.account.openauth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.xiaomi.account.IXiaomiAuthResponse;
import com.xiaomi.account.XiaomiOAuthResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class AuthorizeActivity extends Activity
{
  private static final String AUTHORIZE_PATH = AuthorizeHelper.OAUTH2_HOST + "/oauth2/authorize";
  private static final String KEY_MY_BUNDLE = "extra_my_bundle";
  private static final String KEY_MY_INTENT = "extra_my_intent";
  private static final String KEY_RESPONSE = "extra_response";
  private static final String KEY_RESULT_CODE = "extra_result_code";
  private static final String LOCALE_KEY_IN_URL = "_locale";
  private static final int REQUEST_CODE = 1001;
  public static int RESULT_CANCEL = 0;
  public static int RESULT_FAIL = 0;
  public static int RESULT_SUCCESS = 0;
  private static final String UTF8 = "UTF-8";
  private static HashMap<Locale, String> locale2UrlParamMap;
  private XiaomiOAuthResponse mResponse;
  private WebSettings mSettings;
  private String mUrl;
  private WebView mWebView;

  static
  {
    RESULT_FAIL = 1;
    RESULT_CANCEL = 0;
    locale2UrlParamMap = new HashMap();
    locale2UrlParamMap.put(Locale.SIMPLIFIED_CHINESE, "zh_CN");
    locale2UrlParamMap.put(Locale.CHINA, "zh_CN");
    locale2UrlParamMap.put(Locale.PRC, "zh_CN");
    locale2UrlParamMap.put(Locale.TRADITIONAL_CHINESE, "zh_TW");
    locale2UrlParamMap.put(Locale.TAIWAN, "zh_TW");
    locale2UrlParamMap.put(Locale.ENGLISH, "en");
    locale2UrlParamMap.put(Locale.UK, "en");
    locale2UrlParamMap.put(Locale.US, "en");
  }

  private Bundle addLocaleIfNeeded(Bundle paramBundle)
  {
    if ((paramBundle == null) || (paramBundle.containsKey("_locale")));
    Locale localLocale;
    do
    {
      return paramBundle;
      localLocale = Locale.getDefault();
    }
    while (!locale2UrlParamMap.containsKey(localLocale));
    paramBundle.putString("_locale", (String)locale2UrlParamMap.get(localLocale));
    return paramBundle;
  }

  public static Intent asMiddleActivity(Activity paramActivity, int paramInt, Bundle paramBundle)
  {
    Intent localIntent = new Intent(paramActivity, AuthorizeActivity.class);
    localIntent.putExtra("extra_my_bundle", paramBundle);
    localIntent.putExtra("extra_result_code", paramInt);
    return localIntent;
  }

  public static Intent asMiddleActivity(Activity paramActivity, Intent paramIntent, IXiaomiAuthResponse paramIXiaomiAuthResponse)
  {
    Intent localIntent = new Intent(paramActivity, AuthorizeActivity.class);
    localIntent.putExtra("extra_my_intent", paramIntent);
    localIntent.putExtra("extra_response", new XiaomiOAuthResponse(paramIXiaomiAuthResponse));
    return localIntent;
  }

  private View createView()
  {
    LinearLayout localLinearLayout = new LinearLayout(this);
    localLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    localLinearLayout.setOrientation(1);
    this.mWebView = new WebView(this);
    localLinearLayout.addView(this.mWebView, new ViewGroup.LayoutParams(-2, -2));
    return localLinearLayout;
  }

  public static Intent getIntent(Activity paramActivity, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Boolean paramBoolean, IXiaomiAuthResponse paramIXiaomiAuthResponse)
  {
    Intent localIntent = new Intent(paramActivity, AuthorizeActivity.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("client_id", String.valueOf(paramString1));
    localBundle.putString("redirect_uri", paramString2);
    localBundle.putString("response_type", paramString3);
    localBundle.putString("scope", paramString4);
    localBundle.putString("state", paramString5);
    if (paramBoolean != null)
      localBundle.putString("skip_confirm", String.valueOf(paramBoolean));
    localIntent.putExtra("url_param", localBundle);
    localIntent.putExtra("extra_response", new XiaomiOAuthResponse(paramIXiaomiAuthResponse));
    return localIntent;
  }

  private String parseBundle(Bundle paramBundle)
  {
    if (paramBundle == null)
      return "";
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = paramBundle.getString(str1);
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)))
        localArrayList.add(new BasicNameValuePair(str1, str2));
    }
    return URLEncodedUtils.format(localArrayList, "UTF-8");
  }

  private Bundle parseUrl(String paramString)
  {
    Bundle localBundle = new Bundle();
    if (paramString != null)
      try
      {
        Iterator localIterator = URLEncodedUtils.parse(new URI(paramString), "UTF-8").iterator();
        while (localIterator.hasNext())
        {
          NameValuePair localNameValuePair = (NameValuePair)localIterator.next();
          if ((!TextUtils.isEmpty(localNameValuePair.getName())) && (!TextUtils.isEmpty(localNameValuePair.getValue())))
            localBundle.putString(localNameValuePair.getName(), localNameValuePair.getValue());
        }
      }
      catch (URISyntaxException localURISyntaxException)
      {
        Log.e("openauth", localURISyntaxException.getMessage());
      }
    return localBundle;
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 1001)
      if (paramIntent == null)
        break label25;
    label25: for (Bundle localBundle = paramIntent.getExtras(); ; localBundle = null)
    {
      setResultAndFinish(paramInt2, localBundle);
      return;
    }
  }

  public void onBackPressed()
  {
    if (this.mWebView.canGoBack())
    {
      this.mWebView.goBack();
      return;
    }
    setResultAndFinish(RESULT_CANCEL, (Bundle)null);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent1 = getIntent();
    Bundle localBundle1 = localIntent1.getBundleExtra("extra_my_bundle");
    if (localBundle1 != null)
    {
      setResultAndFinish(localIntent1.getIntExtra("extra_result_code", -1), localBundle1);
      return;
    }
    this.mResponse = ((XiaomiOAuthResponse)localIntent1.getParcelableExtra("extra_response"));
    Intent localIntent2 = (Intent)localIntent1.getParcelableExtra("extra_my_intent");
    if (localIntent2 != null)
    {
      startActivityForResult(localIntent2, 1001);
      return;
    }
    setContentView(createView());
    this.mSettings = this.mWebView.getSettings();
    this.mSettings.setJavaScriptEnabled(true);
    this.mSettings.setSavePassword(false);
    this.mSettings.setSaveFormData(false);
    Bundle localBundle2 = addLocaleIfNeeded(localIntent1.getBundleExtra("url_param"));
    this.mUrl = (AUTHORIZE_PATH + "?" + parseBundle(localBundle2));
    this.mWebView.loadUrl(this.mUrl);
    this.mWebView.setWebViewClient(new AuthorizeWebViewClient());
  }

  void setResultAndFinish(int paramInt, Bundle paramBundle)
  {
    Intent localIntent = new Intent();
    if (paramBundle != null)
      localIntent.putExtras(paramBundle);
    setResult(paramInt, localIntent);
    if (this.mResponse != null)
    {
      if (paramInt != 0)
        break label47;
      this.mResponse.onCancel();
    }
    while (true)
    {
      finish();
      return;
      label47: this.mResponse.onResult(paramBundle);
    }
  }

  void setResultAndFinish(int paramInt, String paramString)
  {
    setResultAndFinish(paramInt, parseUrl(paramString));
  }

  class AuthorizeWebViewClient extends WebViewClient
  {
    AuthorizeWebViewClient()
    {
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      String str1 = new String(paramString);
      int i = str1.indexOf('?');
      if (i > 0)
      {
        String str3 = str1.substring(i + 1);
        if ((str3.startsWith("code=")) || (str3.contains("&code=")))
        {
          AuthorizeActivity.this.setResultAndFinish(AuthorizeActivity.RESULT_SUCCESS, str1);
          return true;
        }
        if ((str3.startsWith("error=")) || (str3.contains("&error=")))
        {
          AuthorizeActivity.this.setResultAndFinish(AuthorizeActivity.RESULT_FAIL, str1);
          return true;
        }
      }
      else
      {
        int j = str1.indexOf('#');
        if (j > 0)
        {
          String str2 = str1.substring(j + 1);
          if ((str2.startsWith("access_token=")) || (str2.contains("&access_token=")))
          {
            AuthorizeActivity.this.setResultAndFinish(AuthorizeActivity.RESULT_SUCCESS, str1.replace("#", "?"));
            return true;
          }
          if ((str2.startsWith("error=")) || (str2.contains("&error=")))
          {
            AuthorizeActivity.this.setResultAndFinish(AuthorizeActivity.RESULT_FAIL, str1.replace("#", "?"));
            return true;
          }
        }
      }
      return super.shouldOverrideUrlLoading(paramWebView, paramString);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.account.openauth.AuthorizeActivity
 * JD-Core Version:    0.6.2
 */