package com.starcor.hunan.domain;

import com.starcor.core.domain.CollectListItem;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.IOTools;
import com.starcor.core.utils.Logger;
import java.io.File;
import java.util.HashMap;
import java.util.List;

public class LocalMediaDataManage
{
  private static final String TAG = LocalMediaDataManage.class.getSimpleName();
  private static LocalMediaDataManage localMediaDataManage = null;
  private String fileName = null;
  private HashMap<MediaDateType, List<CollectListItem>> localMediaData = null;
  private List<CollectListItem> localMediaDateList = null;

  public static LocalMediaDataManage getInstance()
  {
    if (localMediaDataManage == null)
    {
      Logger.i(TAG, "localMediaDataManage first create");
      localMediaDataManage = new LocalMediaDataManage();
    }
    return localMediaDataManage;
  }

  private List<CollectListItem> getSpecificMediaDataFromFile(MediaDateType paramMediaDateType)
  {
    Logger.i(TAG, "loadLocalMediaDataListToFile mediaDataType=" + paramMediaDateType);
    switch (1.$SwitchMap$com$starcor$hunan$domain$LocalMediaDataManage$MediaDateType[paramMediaDateType.ordinal()])
    {
    default:
      Logger.i(TAG, "loadLocalMediaDataListToFile default mediaDataType=" + paramMediaDateType);
      return null;
    case 1:
      Logger.i(TAG, "loadLocalMediaDataListToFile PLAYLIST mediaDataType=" + paramMediaDateType);
      this.fileName = (GlobalEnv.getInstance().getConfigPath() + File.separator + "LocalPlayListRecord.dat");
    case 2:
    case 3:
    }
    while (true)
    {
      Logger.i(TAG, "loadLocalMediaDataListToFile  mediaDataType=" + paramMediaDateType + ",filename=" + this.fileName);
      File localFile = new File(this.fileName);
      if (localFile.exists())
        Logger.i(TAG, "fileName=" + this.fileName + "  is exist");
      try
      {
        while (true)
        {
          this.localMediaDateList = ((List)IOTools.readObject(localFile));
          if (this.localMediaDateList != null)
            Logger.i(TAG, "loadLocalPlayRecordList load ok, file:" + this.fileName + ", size:" + this.localMediaDateList.size());
          return this.localMediaDateList;
          Logger.i(TAG, "loadLocalMediaDataListToFile COLLECTION mediaDataType=" + paramMediaDateType);
          this.fileName = (GlobalEnv.getInstance().getConfigPath() + File.separator + "LocalCollectionRecord.dat");
          break;
          Logger.i(TAG, "loadLocalMediaDataListToFile CATCH mediaDataType=" + paramMediaDateType);
          this.fileName = (GlobalEnv.getInstance().getConfigPath() + File.separator + "LocalCatchRecord.dat");
          break;
          Logger.i(TAG, "fileName=" + this.fileName + "  is  not exist");
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          Logger.e(TAG, "localMediaDateList");
          this.localMediaDateList = null;
        }
      }
    }
  }

  private boolean loadLocalMediaDataListToFile(MediaDateType paramMediaDateType, List<CollectListItem> paramList)
  {
    Logger.i(TAG, "loadLocalMediaDataListToFile");
    switch (1.$SwitchMap$com$starcor$hunan$domain$LocalMediaDataManage$MediaDateType[paramMediaDateType.ordinal()])
    {
    default:
      return false;
    case 1:
      this.fileName = (GlobalEnv.getInstance().getConfigPath() + File.separator + "LocalPlayListRecord.dat");
    case 2:
    case 3:
    }
    while (true)
    {
      Logger.i(TAG, "loadLocalMediaDataListToFile fileName=" + this.fileName);
      File localFile = new File(this.fileName);
      try
      {
        boolean bool2 = IOTools.writeObject(localFile, paramList);
        bool1 = bool2;
        if (bool1)
        {
          Logger.i(TAG, "saveLocalPlayRecordList size:" + paramList.size());
          return true;
          this.fileName = (GlobalEnv.getInstance().getConfigPath() + File.separator + "LocalCollectionRecord.dat");
          continue;
          this.fileName = (GlobalEnv.getInstance().getConfigPath() + File.separator + "LocalCatchRecord.dat");
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          boolean bool1 = false;
        }
        Logger.e(TAG, "saveLocalPlayRecordList  false size:" + paramList.size());
      }
    }
    return false;
  }

  public HashMap<MediaDateType, List<CollectListItem>> getAllMediaDataFromFile()
  {
    if (this.localMediaData == null)
      this.localMediaData = new HashMap();
    for (MediaDateType localMediaDateType : MediaDateType.values())
    {
      Logger.i(TAG, "getAllMediaDataFromFile mediaData=" + localMediaDateType + ",getSpecificMediaDataFromFile(mediaData)=" + getSpecificMediaDataFromFile(localMediaDateType));
      this.localMediaData.put(localMediaDateType, getSpecificMediaDataFromFile(localMediaDateType));
    }
    return this.localMediaData;
  }

  public boolean saveMediaDataToLocalFile(MediaDateType paramMediaDateType, List<CollectListItem> paramList)
  {
    if (paramList == null)
      return false;
    if (this.localMediaData == null)
      this.localMediaData = new HashMap();
    switch (1.$SwitchMap$com$starcor$hunan$domain$LocalMediaDataManage$MediaDateType[paramMediaDateType.ordinal()])
    {
    default:
      return false;
    case 1:
      this.localMediaData.put(MediaDateType.PLAYLIST, paramList);
    case 2:
    case 3:
    }
    while (true)
    {
      loadLocalMediaDataListToFile(paramMediaDateType, paramList);
      return true;
      this.localMediaData.put(MediaDateType.COLLECTION, paramList);
      continue;
      this.localMediaData.put(MediaDateType.CATCH, paramList);
    }
  }

  public static enum MediaDateType
  {
    static
    {
      COLLECTION = new MediaDateType("COLLECTION", 1);
      CATCH = new MediaDateType("CATCH", 2);
      MediaDateType[] arrayOfMediaDateType = new MediaDateType[3];
      arrayOfMediaDateType[0] = PLAYLIST;
      arrayOfMediaDateType[1] = COLLECTION;
      arrayOfMediaDateType[2] = CATCH;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.domain.LocalMediaDataManage
 * JD-Core Version:    0.6.2
 */