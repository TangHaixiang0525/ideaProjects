package com.starcor.core.upgrade;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.starcor.core.utils.AppKiller;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.widget.BaseDialog;
import com.starcor.ui.UITools;
import java.io.File;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.http.client.methods.HttpGet;

public class UpgradeActivity extends DialogActivity
{
  static final float HTTP_PERCENT_RANGE_BASE = 0.0F;
  static final float HTTP_PERCENT_SCALER = 0.75F;
  static final int MAX_RETRY_COUNTER = 3;
  private static final String TAG = "UpgradeActivity";
  ConfirmAlert _confirm_alert;
  File _download_file;
  int _download_url_idx = 0;
  String[] _download_urls;
  FileDownloader _downloader;
  String _error_start_action;
  ProgressBar _inst_progress;
  TextView _progress_text;
  TextView _progress_val;
  int _retry_counter = 0;
  private boolean isDownLoadFinished = false;
  boolean isPause = false;

  private void setProgressInfo(float paramFloat, String paramString)
  {
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Float.valueOf(paramFloat * 100.0F);
    if (paramString != null);
    for (String str = paramString; ; str = "(null)")
    {
      arrayOfObject1[1] = str;
      Log.d("setProgress", String.format("%.2f%% tip:%s", arrayOfObject1));
      TextView localTextView = this._progress_val;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Float.valueOf(paramFloat * 100.0F);
      localTextView.setText(String.format("%.2f%%", arrayOfObject2));
      this._inst_progress.setProgress((int)(paramFloat * this._inst_progress.getMax()));
      this._inst_progress.postInvalidate();
      if (paramString != null)
        this._progress_text.setText(paramString);
      return;
    }
  }

  private void startDownload(String paramString)
  {
    this._retry_counter = 0;
    String str1 = paramString.trim();
    if (TextUtils.isEmpty(str1))
    {
      setProgressInfo(0.0F + 0.75F * this._downloader.getProgress(), "更新地址为空!!, 更新失败!");
      return;
    }
    try
    {
      String str2 = new HttpGet(str1).getURI().getPath();
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(str2.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      String str3 = "";
      int i = arrayOfByte.length;
      for (int j = 0; j < i; j++)
      {
        int k = arrayOfByte[j];
        str3 = str3 + Integer.toHexString(k & 0xFF);
      }
      File localFile1 = getFilesDir();
      File[] arrayOfFile = localFile1.listFiles();
      int m = arrayOfFile.length;
      for (int n = 0; n < m; n++)
        arrayOfFile[n].delete();
      File localFile2 = new File(localFile1, str3);
      this._download_file = localFile2;
      this._downloader.start(str1, this._download_file, true, new Handler(new Handler.Callback()
      {
        public boolean handleMessage(Message paramAnonymousMessage)
        {
          UpgradeActivity.this.onHttpMessage(paramAnonymousMessage);
          return false;
        }
      }));
      return;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      setProgressInfo(0.0F + 0.75F * this._downloader.getProgress(), "内部错误(无法创建临时文件), 更新失败!");
      localNoSuchAlgorithmException.printStackTrace();
      return;
    }
    catch (Exception localException)
    {
      setProgressInfo(0.0F + 0.75F * this._downloader.getProgress(), "内部错误, 更新失败!");
      localException.printStackTrace();
    }
  }

  protected void onApkMessage(Message paramMessage)
  {
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    initXul("CommonPage");
    xulHideTitle();
  }

  protected void onDestroy()
  {
    if (this._downloader != null)
      this._downloader.stop();
    PackageMonitor.bindHandler(null);
    File[] arrayOfFile = getFilesDir().listFiles();
    int i = arrayOfFile.length;
    for (int j = 0; j < i; j++)
      arrayOfFile[j].delete();
    super.onDestroy();
  }

  protected void onHttpMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      this._downloader.stop();
      return;
    case 3:
      this.isDownLoadFinished = true;
      setProgressInfo(0.0F + 0.75F * this._downloader.getProgress(), "文件下载完成,准备安装...");
      if (!Upgrade.isApkCanInstall())
      {
        UITools.ShowCustomToast(this.context, Upgrade.CAN_NOT_UPGRADE_TIP);
        return;
      }
      Uri localUri = Uri.fromFile(new File(this.context.getFilesDir() + File.separator + "tmp.apk"));
      Logger.d("upgradeActivity", "install_apk_silent:" + this.context.getFilesDir() + File.separator + "tmp.apk");
      Intent localIntent = new Intent("android.intent.action.VIEW");
      localIntent.setDataAndType(localUri, "application/vnd.android.package-archive");
      localIntent.addFlags(268435456);
      this.context.startActivity(localIntent);
      return;
    case 1:
      setProgressInfo(0.0F + 0.75F * this._downloader.getProgress(), "文件下载中...");
      return;
    case 4:
    case 5:
      setProgressInfo(0.0F + 0.75F * this._downloader.getProgress(), null);
      return;
    case 2:
    }
    this._retry_counter = (1 + this._retry_counter);
    if (this._retry_counter < 3)
    {
      setProgressInfo(0.0F + 0.75F * this._downloader.getProgress(), "文件下载失败!! 正在重试...");
      this._downloader.resume();
      return;
    }
    this._download_url_idx = (1 + this._download_url_idx);
    if (this._download_url_idx >= this._download_urls.length)
    {
      setProgressInfo(0.0F + 0.75F * this._downloader.getProgress(), "更新失败 - 文件下载错误!");
      this._downloader.stop();
      return;
    }
    Log.d("onHttpMessage", "try next url");
    this._downloader.stop();
    startDownload(this._download_urls[this._download_url_idx]);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((4 == paramInt) && ((this._downloader.getError() != 0) || (this.isDownLoadFinished)))
    {
      AppKiller.getInstance().killApp();
      return true;
    }
    return false;
  }

  protected void onPause()
  {
    super.onPause();
    this.isPause = true;
    Log.d("UpgradeActivity", "onPause");
  }

  protected void onResume()
  {
    super.onResume();
    Log.d("UpgradeActivity", "onResume");
    if (this.isPause)
    {
      finish();
      AppKiller.getInstance().killApp();
    }
  }

  protected void onStart()
  {
    super.onStart();
  }

  protected void onStop()
  {
    super.onStop();
  }

  protected void xulOnRenderIsReady()
  {
    super.xulOnRenderIsReady();
    setContentView(2130903049);
    this._progress_val = ((TextView)findViewById(2131165252));
    this._progress_text = ((TextView)findViewById(2131165254));
    this._inst_progress = ((ProgressBar)findViewById(2131165253));
    this._inst_progress.setProgress(0);
    this._confirm_alert = new ConfirmAlert(this);
    this._downloader = new FileDownloader(this);
    ((TextView)findViewById(2131165251)).setText("软件更新");
    Bundle localBundle = getIntent().getExtras();
    if (localBundle == null)
    {
      Toast.makeText(getBaseContext(), "启动参数错误!缺少启动参数!", 1).show();
      Log.e("init", "invalid start Intent, no extras info.");
      finish();
      return;
    }
    Boolean localBoolean = Boolean.valueOf(localBundle.getBoolean("upgrade_silently", false));
    this._download_urls = localBundle.getStringArray("url");
    if ((this._download_urls == null) || (this._download_urls.length == 0))
    {
      Toast.makeText(getBaseContext(), "启动参数错误!没有更新地址!", 1).show();
      Log.e("init", "invalid start Intent, url is empty.");
      finish();
      return;
    }
    String[] arrayOfString = this._download_urls;
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++)
      if (arrayOfString[j].equals(""))
      {
        Toast.makeText(getBaseContext(), "启动参数错误!更新地址有误!", 1).show();
        Log.e("init", "invalid start Intent, invalid url.");
        finish();
        return;
      }
    this._error_start_action = localBundle.getString("error_start_package");
    if ((this._error_start_action != null) && (!TextUtils.isEmpty(this._error_start_action)))
      this._error_start_action = this._error_start_action.trim();
    PackageMonitor.bindHandler(new Handler(new Handler.Callback()
    {
      public boolean handleMessage(Message paramAnonymousMessage)
      {
        UpgradeActivity.this.onApkMessage(paramAnonymousMessage);
        return false;
      }
    }));
    if (!localBoolean.booleanValue())
    {
      this._confirm_alert.setTitle("升级");
      this._confirm_alert.setInfo("检查到系统更新,是否开始更新系统?");
      this._confirm_alert.setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          if (UpgradeActivity.this._confirm_alert.getConfirmCode() != 0)
          {
            UpgradeActivity.this.finish();
            return;
          }
          UpgradeActivity.this.startDownload(UpgradeActivity.this._download_urls[UpgradeActivity.this._download_url_idx]);
        }
      });
      this._confirm_alert.show();
      return;
    }
    Toast.makeText(getBaseContext(), "检查到系统更新,开始更新系统...", 0).show();
    startDownload(this._download_urls[this._download_url_idx]);
  }

  private class ConfirmAlert extends BaseDialog
  {
    Button _backward;
    int _confirm_code = 0;
    TextView _info;
    Button _ok;
    TextView _title;

    public ConfirmAlert(Context arg2)
    {
      super(2131296263);
      requestWindowFeature(1);
      setContentView(2130903057);
      setCancelable(false);
      this._ok = ((Button)findViewById(2131165279));
      this._ok.setText("确定");
      this._backward = ((Button)findViewById(2131165280));
      this._backward.setText("返回");
      this._title = ((TextView)findViewById(2131165277));
      this._info = ((TextView)findViewById(2131165278));
      this._ok.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          UpgradeActivity.ConfirmAlert.this.onOk();
        }
      });
      this._backward.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          UpgradeActivity.ConfirmAlert.this.onCancel();
        }
      });
    }

    public int getConfirmCode()
    {
      return this._confirm_code;
    }

    protected void onCancel()
    {
      Log.d("ConfirmAlert", "onCancel");
      this._confirm_code = 1;
      cancel();
    }

    protected void onOk()
    {
      Log.d("ConfirmAlert", "onOk");
      this._confirm_code = 0;
      cancel();
    }

    protected void onStop()
    {
      Log.d("ConfirmAlert", "onStop");
      super.onStop();
    }

    public void setInfo(int paramInt)
    {
      this._info.setText(paramInt);
    }

    public void setInfo(CharSequence paramCharSequence)
    {
      this._info.setText(paramCharSequence);
    }

    public void setTitle(int paramInt)
    {
      this._title.setText(paramInt);
    }

    public void setTitle(CharSequence paramCharSequence)
    {
      this._title.setText(paramCharSequence);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.upgrade.UpgradeActivity
 * JD-Core Version:    0.6.2
 */