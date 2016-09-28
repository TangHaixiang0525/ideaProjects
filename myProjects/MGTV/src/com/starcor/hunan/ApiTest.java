package com.starcor.hunan;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.test.AndroidTestCase;
import com.starcor.core.domain.ApiTask;
import com.starcor.core.epgapi.params.GetFrequentlyAskedQuestionsParams;
import com.starcor.core.parser.sax.GetFrequentlyAskedQuestionsSAXParser;
import com.starcor.core.service.DownLoadService;
import com.starcor.core.utils.Logger;
import java.util.ArrayList;

public class ApiTest extends AndroidTestCase
{
  private static final int MSG_FAQ_OK = 1;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 1:
      }
      do
        return;
      while (paramAnonymousMessage.obj == null);
      Logger.i(((ArrayList)paramAnonymousMessage.obj).toString());
    }
  };
  private ServiceConnection settingConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      ApiTask localApiTask = new ApiTask();
      localApiTask.setApi(new GetFrequentlyAskedQuestionsParams("37e07CikClwKANK02m6f7etMC45XocjNCn6sVvDstPDWLoAm6z7I8sU78R%2ChYJXPStR8fpXzRkaDIWKakIG3OIU", "X734HO34OMGBIFKPEBD7"));
      localApiTask.setParser(new GetFrequentlyAskedQuestionsSAXParser());
      localApiTask.setHandler(ApiTest.this.mHandler);
      localApiTask.setMessageNumber(1);
      ((DownLoadService)paramAnonymousIBinder).addTask(localApiTask);
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
    }
  };

  public void TestFAQ()
  {
    Intent localIntent = new Intent(getContext(), DownLoadService.class);
    getContext().bindService(localIntent, this.settingConnection, 1);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ApiTest
 * JD-Core Version:    0.6.2
 */