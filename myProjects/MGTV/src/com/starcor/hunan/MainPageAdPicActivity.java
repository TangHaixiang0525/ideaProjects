package com.starcor.hunan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import com.starcor.core.domain.ImageTask;
import com.starcor.core.service.DownLoadService;
import com.starcor.core.utils.Logger;

public class MainPageAdPicActivity extends BaseActivity
{
  private static final int MESSAGE_GET_BITMAP = 4;
  private static final String TAG = "MainPageAdPicActivity";
  private ImageView imgvAdPic;
  private DownLoadService mDownLoadService;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 4:
      }
      Logger.i("MainPageAdPicActivity", "handleMessage() MESSAGE_GET_BITMAP");
      MainPageAdPicActivity.this.imgvAdPic.setImageBitmap((Bitmap)paramAnonymousMessage.obj);
    }
  };

  void loadBitmap(String paramString, Handler paramHandler)
  {
    ImageTask localImageTask = new ImageTask();
    localImageTask.setUrl(paramString);
    localImageTask.setHandler(paramHandler);
    localImageTask.setMessageNumber(4);
    this.mDownLoadService.addHighTask(localImageTask);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903065);
    this.imgvAdPic = ((ImageView)findViewById(2131165291));
    String str = getIntent().getExtras().getString("imgUrl");
    this.mDownLoadService = App.getInstance().getTaskService();
    loadBitmap(str, this.mHandler);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.MainPageAdPicActivity
 * JD-Core Version:    0.6.2
 */