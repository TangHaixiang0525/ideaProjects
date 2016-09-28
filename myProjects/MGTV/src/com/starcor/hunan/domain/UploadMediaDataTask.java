package com.starcor.hunan.domain;

import com.starcor.hunan.service.CollectAndPlayListLogic;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class UploadMediaDataTask
{
  private CollectAndPlayListLogic collectLogic = null;
  private HashMap<LocalMediaDataManage.MediaDateType, MediaDataTypeInterface> mediaDataTypeList = null;

  public UploadMediaDataTask()
  {
    this.collectLogic.setErrorCallBack(null);
    try
    {
      this.mediaDataTypeList = UploadCombineMediaDataManageFactory.getInstance().getMediaDataTypeListInstance();
      return;
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
      return;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
  }

  public void executeAsync()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        UploadMediaDataTask.this.upload();
      }
    }).start();
  }

  public void executeSync()
  {
    upload();
  }

  public void upload()
  {
    if (this.mediaDataTypeList != null)
    {
      Iterator localIterator = this.mediaDataTypeList.values().iterator();
      while (localIterator.hasNext())
      {
        MediaDataTypeInterface localMediaDataTypeInterface = (MediaDataTypeInterface)localIterator.next();
        if (localMediaDataTypeInterface != null)
        {
          localMediaDataTypeInterface.setCollectAndPlayListLogicObject(this.collectLogic);
          localMediaDataTypeInterface.getUnLoginList();
          localMediaDataTypeInterface.dealMediaDataAndUploadMediaData();
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.domain.UploadMediaDataTask
 * JD-Core Version:    0.6.2
 */