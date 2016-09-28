package com.starcor.hunan;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.core.domain.ImageTask;
import com.starcor.core.domain.Skin;
import com.starcor.core.image.BitmapTools;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.service.DownLoadService;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.sccms.api.SccmsApiGetSkinTask.ISccmsApiGetSkinTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.ui.UITools;
import com.starcor.xul.XulWorker;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ChangeSkinActivity extends DialogActivity
  implements View.OnClickListener
{
  private static final int DOWN_PICTURE_SUCCESS = 2;
  private static final int SAVE_PICTURE = 1;
  private static final int SET_CACHED_PICTURE = 3;
  protected static final String TAG = "ChangeSkinActivity";
  private ImageButton bgselected;
  Bitmap bmp;
  CacheManager cacheManager;
  boolean downloadPicFinish = true;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      int i = paramAnonymousMessage.what;
      switch (i)
      {
      default:
      case 1:
        do
          return;
        while ((ChangeSkinActivity.this.skin == null) || (ChangeSkinActivity.this.skin.getUrl() == null));
        Logger.i("ChangeSkinActivity", "localpath=" + GeneralUtils.getImageNameFromUrl(ChangeSkinActivity.this.skin.getUrl()));
        GlobalEnv.getInstance().setMainActivityBackground(GeneralUtils.getImageNameFromUrl(ChangeSkinActivity.this.skin.getUrl()));
        GlobalEnv.getInstance().getPreLoadingBkg(ChangeSkinActivity.this.context, true);
        UITools.ShowCustomToast(ChangeSkinActivity.this, "设置成功");
        XulWorker.removeDrawableCachePermanently("file:///.app/skins/background");
        return;
      case 2:
      case 3:
      }
      ChangeSkinActivity.this.downloadPicFinish = true;
      Bitmap localBitmap = (Bitmap)paramAnonymousMessage.obj;
      if (i == 2);
      ChangeSkinActivity.this.dismissLoaddingDialog();
      if (localBitmap != null)
      {
        ChangeSkinActivity.this.showImageView.setImageBitmap(localBitmap);
        if (ChangeSkinActivity.this.bmp != null)
        {
          ChangeSkinActivity.this.bmp.recycle();
          ChangeSkinActivity.this.bmp = null;
        }
        ChangeSkinActivity.this.bmp = localBitmap;
        ChangeSkinActivity.this.tvCurrentPage.setText(String.valueOf(1 + ChangeSkinActivity.this.index));
        ChangeSkinActivity.this.tvPager.setText(" / " + ChangeSkinActivity.this.skins.size());
        ChangeSkinActivity.this.mImageButton_ok.setBackgroundResource(2130837655);
        ChangeSkinActivity.this.mImageButton_return.setBackgroundResource(2130837656);
        return;
      }
      ChangeSkinActivity.access$802(ChangeSkinActivity.this, false);
      ChangeSkinActivity.this.reportLoad(ChangeSkinActivity.this.isLoadSuccess, null);
      ChangeSkinActivity.this.showErrorDialogWithReport(11, "1002007", "bitmap null", null, null);
    }
  };
  private int height;
  private int index = 0;
  private boolean isLoadSuccess = false;
  boolean isNetWork = true;
  boolean isOkButton = false;
  boolean isReturnButton = false;
  private Button mImageButton_ok;
  private Button mImageButton_return;
  private ImageView showImageView;
  private int size = 0;
  private Skin skin;
  private List<Skin> skins;
  private TextView tvCurrentPage;
  private TextView tvPager;
  private int width;

  private Bitmap decodeResource(Resources paramResources, int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    paramResources.openRawResource(paramInt, localTypedValue);
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inTargetDensity = localTypedValue.density;
    return BitmapFactory.decodeResource(paramResources, paramInt, localOptions);
  }

  private void downloadPic()
  {
    ImageTask localImageTask = new ImageTask();
    localImageTask.setUrl(this.skin.getUrl());
    localImageTask.setHandler(this.handler);
    localImageTask.setMessageNumber(2);
    localImageTask.setProcess(2);
    localImageTask.setOutPixelFormat(Bitmap.Config.ARGB_8888);
    localImageTask.setScaledHeight(640);
    localImageTask.setScaledWidth(360);
    App.getInstance().getTaskService().addHighTask(localImageTask);
  }

  private void initViews()
  {
    this.mImageButton_ok = ((Button)findViewById(2131165234));
    this.mImageButton_ok.getLayoutParams().height = App.Operation(60.0F);
    this.mImageButton_ok.getLayoutParams().width = App.Operation(150.0F);
    this.mImageButton_ok.getPaint().setTextSize(App.Operation(24.0F));
    this.mImageButton_return = ((Button)findViewById(2131165235));
    this.mImageButton_return.getLayoutParams().height = App.Operation(60.0F);
    this.mImageButton_return.getLayoutParams().width = App.Operation(150.0F);
    ((LinearLayout.LayoutParams)this.mImageButton_return.getLayoutParams()).leftMargin = App.Operation(42.0F);
    this.mImageButton_return.getPaint().setTextSize(App.Operation(24.0F));
    ImageButton localImageButton1 = (ImageButton)findViewById(2131165228);
    Bitmap localBitmap1 = decodeResource(getResources(), 2130837614);
    ((RelativeLayout.LayoutParams)localImageButton1.getLayoutParams()).height = App.Operation(34.0F);
    ((RelativeLayout.LayoutParams)localImageButton1.getLayoutParams()).width = App.Operation(45.0F);
    ((RelativeLayout.LayoutParams)localImageButton1.getLayoutParams()).leftMargin = App.Operation(88.0F);
    localImageButton1.setBackgroundDrawable(new BitmapDrawable(localBitmap1));
    ImageButton localImageButton2 = (ImageButton)findViewById(2131165227);
    Bitmap localBitmap2 = decodeResource(getResources(), 2130837602);
    ((RelativeLayout.LayoutParams)localImageButton2.getLayoutParams()).height = App.Operation(34.0F);
    ((RelativeLayout.LayoutParams)localImageButton2.getLayoutParams()).width = App.Operation(45.0F);
    ((RelativeLayout.LayoutParams)localImageButton2.getLayoutParams()).rightMargin = App.Operation(88.0F);
    localImageButton2.setBackgroundDrawable(new BitmapDrawable(localBitmap2));
    this.bgselected = ((ImageButton)findViewById(2131165229));
    ((RelativeLayout.LayoutParams)this.bgselected.getLayoutParams()).addRule(14);
    ((RelativeLayout.LayoutParams)this.bgselected.getLayoutParams()).addRule(10);
    ((RelativeLayout.LayoutParams)this.bgselected.getLayoutParams()).height = App.Operation(470.0F);
    ((RelativeLayout.LayoutParams)this.bgselected.getLayoutParams()).width = App.Operation(835.0F);
    ((RelativeLayout.LayoutParams)this.bgselected.getLayoutParams()).topMargin = App.Operation(109.0F);
    this.bgselected.setBackgroundResource(2130837653);
    this.bgselected.requestFocus();
    this.mImageButton_ok.setOnClickListener(this);
    this.mImageButton_return.setOnClickListener(this);
    LinearLayout localLinearLayout1 = (LinearLayout)findViewById(2131165230);
    ((RelativeLayout.LayoutParams)localLinearLayout1.getLayoutParams()).topMargin = App.Operation(67.0F);
    ((RelativeLayout.LayoutParams)localLinearLayout1.getLayoutParams()).addRule(10);
    this.showImageView = ((ImageView)findViewById(2131165226));
    this.tvPager = ((TextView)findViewById(2131165232));
    this.tvPager.getPaint().setTextSize(App.Operation(25.0F));
    this.tvCurrentPage = ((TextView)findViewById(2131165231));
    this.tvCurrentPage.getPaint().setTextSize(App.Operation(25.0F));
    ((LinearLayout.LayoutParams)this.tvPager.getLayoutParams()).topMargin = App.Operation(5.0F);
    ((LinearLayout.LayoutParams)this.tvCurrentPage.getLayoutParams()).topMargin = App.Operation(5.0F);
    LinearLayout localLinearLayout2 = (LinearLayout)findViewById(2131165233);
    ((RelativeLayout.LayoutParams)localLinearLayout2.getLayoutParams()).addRule(3, 2131165229);
    ((RelativeLayout.LayoutParams)localLinearLayout2.getLayoutParams()).topMargin = App.Operation(37.0F);
  }

  public void downLoadPicture()
  {
    if (this.index < 0);
    while (!this.downloadPicFinish)
      return;
    this.downloadPicFinish = false;
    if (this.skins == null)
    {
      Logger.w("ChangeSkinActivity", "skins == null !!!");
      return;
    }
    this.skin = ((Skin)this.skins.get(this.index));
    showLoaddingDialog();
    Bitmap localBitmap = this.cacheManager.getBitmap(this.skin.getUrl());
    if (localBitmap != null)
    {
      Message localMessage = new Message();
      localMessage.what = 3;
      localMessage.obj = localBitmap;
      this.handler.sendMessage(localMessage);
      return;
    }
    downloadPic();
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if (i == 2131165234)
    {
      localMessage = new Message();
      localMessage.what = 1;
      this.handler.sendMessage(localMessage);
    }
    while (i != 2131165235)
    {
      Message localMessage;
      return;
    }
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903044);
    this.height = getResources().getDisplayMetrics().heightPixels;
    this.width = getResources().getDisplayMetrics().widthPixels;
    initViews();
    showLoaddingDialog();
    ServerApiManager.i().APIGetSkins(new SccmsApiGetSkinTask.ISccmsApiGetSkinTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ChangeSkinActivity.this.dismissLoaddingDialog();
        ChangeSkinActivity.access$802(ChangeSkinActivity.this, false);
        ChangeSkinActivity.this.reportLoad(ChangeSkinActivity.this.isLoadSuccess, null);
        ChangeSkinActivity.this.isNetWork = false;
        ChangeSkinActivity.this.showErrorDialogWithReport(11, "1002007", "APIGetSkins ISccmsApiGetSkinTaskListener.onError", paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, List<Skin> paramAnonymousList)
      {
        ChangeSkinActivity.access$402(ChangeSkinActivity.this, paramAnonymousList);
        ChangeSkinActivity.this.dismissLoaddingDialog();
        if (ChangeSkinActivity.this.skins == null)
        {
          ChangeSkinActivity.this.isNetWork = false;
          ChangeSkinActivity.access$802(ChangeSkinActivity.this, false);
          ChangeSkinActivity.this.reportLoad(ChangeSkinActivity.this.isLoadSuccess, null);
          ChangeSkinActivity.this.showErrorDialogWithReport(11, "1002007", "APIGetSkins ISccmsApiGetSkinTaskListener.onSuccess result null", paramAnonymousServerApiTaskInfo, null);
          return;
        }
        ChangeSkinActivity.access$902(ChangeSkinActivity.this, ChangeSkinActivity.this.skins.size());
        if (ChangeSkinActivity.this.size == 0)
        {
          ChangeSkinActivity.this.isNetWork = false;
          ChangeSkinActivity.access$802(ChangeSkinActivity.this, false);
          ChangeSkinActivity.this.reportLoad(ChangeSkinActivity.this.isLoadSuccess, null);
          ChangeSkinActivity.this.showErrorDialogWithReport(11, "1002007", "APIGetSkins ISccmsApiGetSkinTaskListener.onSuccess result size is zero.l", paramAnonymousServerApiTaskInfo, null);
          return;
        }
        ChangeSkinActivity.access$202(ChangeSkinActivity.this, 0);
        ChangeSkinActivity.this.downLoadPicture();
      }
    });
    this.isLoadSuccess = true;
    reportLoad(this.isLoadSuccess, null);
    this.cacheManager = new CacheManager(null);
  }

  protected void onDestroy()
  {
    if (this.bmp != null)
    {
      this.bmp.recycle();
      this.bmp = null;
      System.gc();
    }
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((this.isNetWork) && (this.bgselected.hasFocus()) && (paramInt == 21))
    {
      this.bgselected.setNextFocusLeftId(this.bgselected.getId());
      if (this.index < 1)
      {
        this.index = (-1 + this.size);
        downLoadPicture();
      }
    }
    while (true)
    {
      return super.onKeyDown(paramInt, paramKeyEvent);
      this.index = (-1 + this.index);
      break;
      if ((this.isNetWork) && (this.bgselected.hasFocus()) && (paramInt == 22))
      {
        this.bgselected.setNextFocusRightId(this.bgselected.getId());
        if (this.index >= -1 + this.size);
        for (this.index = 0; ; this.index = (1 + this.index))
        {
          downLoadPicture();
          break;
        }
      }
      if ((this.mImageButton_ok.hasFocus()) && (paramInt == 21))
        this.mImageButton_ok.setNextFocusLeftId(this.mImageButton_ok.getId());
      else if ((this.mImageButton_return.hasFocus()) && (paramInt == 22))
        this.mImageButton_return.setNextFocusRightId(this.mImageButton_return.getId());
    }
  }

  protected void onRestart()
  {
    super.onRestart();
    reportLoad(this.isLoadSuccess, null);
  }

  protected void onResume()
  {
    super.onResume();
  }

  protected void onStop()
  {
    super.onStop();
    reportExit(this.isLoadSuccess, null);
  }

  private class CacheManager
  {
    private CacheManager()
    {
    }

    Bitmap getBitmap(String paramString)
    {
      String str = GeneralUtils.getImageNameFromUrl(paramString);
      File localFile = new File(GlobalEnv.getInstance().getPicCachePath(), str);
      Logger.d("ChangeSkinActivity", "getBitmap, index=" + ChangeSkinActivity.this.index + ", fileName=" + str + ", file.exists=" + localFile.exists() + ", url=" + paramString);
      if (!localFile.exists())
        return null;
      Logger.d("ChangeSkinActivity", "getBitmap, decode bitmap start");
      Bitmap localBitmap = BitmapTools.decodeFile(localFile.getAbsolutePath());
      Logger.d("ChangeSkinActivity", "getBitmap, decode bitmap end, bitmap=" + localBitmap);
      return localBitmap;
    }

    void saveBitmap(final Bitmap paramBitmap, final String paramString)
    {
      if (paramBitmap == null)
      {
        Logger.d("ChangeSkinActivity", "saveBitmap, index=" + ChangeSkinActivity.this.index + ", bitmap=" + paramBitmap + ", url=" + paramString);
        return;
      }
      new Thread()
      {
        public void run()
        {
          String str = GeneralUtils.getImageNameFromUrl(paramString);
          Logger.d("ChangeSkinActivity", "saveBitmap, index=" + ChangeSkinActivity.this.index + ", fileName=" + str + ", url=" + paramString);
          try
          {
            File localFile = new File(GlobalEnv.getInstance().getPicCachePath(), str);
            localFile.createNewFile();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            paramBitmap.compress(Bitmap.CompressFormat.PNG, 90, localByteArrayOutputStream);
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
            localFileOutputStream.write(arrayOfByte);
            localFileOutputStream.flush();
            localFileOutputStream.close();
            Logger.d("ChangeSkinActivity", "saveBitmap result ----> succeed, index=" + ChangeSkinActivity.this.index + ", fileName=" + str + ", url=" + paramString);
            return;
          }
          catch (IOException localIOException)
          {
            localIOException.printStackTrace();
            Logger.d("ChangeSkinActivity", "saveBitmap result ----> failed, index=" + ChangeSkinActivity.this.index + ", fileName=" + str + ", url=" + paramString);
          }
        }
      }
      .start();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ChangeSkinActivity
 * JD-Core Version:    0.6.2
 */