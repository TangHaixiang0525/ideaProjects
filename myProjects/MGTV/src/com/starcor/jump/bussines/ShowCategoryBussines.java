package com.starcor.jump.bussines;

import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.common.IntentDataManager;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.domain.MetadataGroupPageIndex;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.InitApiActivity;
import com.starcor.hunan.SplashActivity;
import com.starcor.jump.bussines.data.CommonData;
import java.util.ArrayList;
import java.util.Iterator;

public class ShowCategoryBussines extends CommonBussines
{
  private static final String TAG = ShowCategoryBussines.class.getSimpleName();

  private void getCategoryItemIntent(String paramString)
  {
    String str = "";
    setClassForActivity(SplashActivity.class);
    Bundle localBundle1 = DialogActivity.findMenuItem(new String[] { paramString }, null, null);
    if (localBundle1 != null)
    {
      Bundle localBundle2 = localBundle1.getBundle("args");
      if (localBundle2 != null)
        str = localBundle2.getString("media_asset_id");
    }
    if (!TextUtils.isEmpty(str))
      putExtra("packet_id", str);
    toCategory(paramString);
  }

  private void showCategory()
  {
    String str1 = this._data.packetId;
    String str2 = this._data.categoryId;
    String str3 = this._data.videoId;
    String str4 = this._data.manager.getStringValue("date");
    String str5 = this._data.videoBeginTime;
    Logger.i(TAG, "CMD_COMMON_DO_SHOW_CATEGORY:startNextActivity packet_id:" + str1 + ",category_id:" + str2);
    Bundle localBundle1 = findMenuItem(null, null, str1);
    IntentDataManager.dumpBundleData(localBundle1);
    if ((localBundle1 != null) && (localBundle1.getBundle("args") != null))
    {
      Bundle localBundle2 = localBundle1.getBundle("args");
      localBundle2.putString("category_id", str2);
      localBundle2.putString("video_id", str3);
      localBundle2.putString("begin_time", str4 + str5);
      startActivityByCommand(localBundle1.getString("action"), localBundle2);
      return;
    }
    Logger.e(TAG, "没有找到您想进入的接口，即将进入主界面");
    processForMainActivity();
  }

  private void toCategory(String paramString)
  {
    Logger.i(TAG, "tcl_toCategory");
    ArrayList localArrayList = GlobalLogic.getInstance().getN3A2MetaGroups();
    setClassForActivity(SplashActivity.class);
    putExtra("isbroadcast", true);
    if (localArrayList == null)
      Logger.i(TAG, "metadataGoup is null");
    label45: label46: MetadataGoup localMetadataGoup;
    label211: 
    do
    {
      MetadataInfo localMetadataInfo2;
      do
      {
        return;
        break label46;
        break label46;
        Iterator localIterator5;
        do
        {
          Iterator localIterator4;
          do
          {
            Iterator localIterator1;
            do
            {
              localIterator1 = localArrayList.iterator();
              break label272;
              break;
            }
            while (!localIterator1.hasNext());
            localMetadataGoup = (MetadataGoup)localIterator1.next();
            if (!"menu".equals(localMetadataGoup.type))
              break label211;
            Logger.i(TAG, "tcl_toCategory menu");
            localIterator4 = localMetadataGoup.meta_index_list.iterator();
            break;
          }
          while (!localIterator4.hasNext());
          localIterator5 = ((MetadataGroupPageIndex)localIterator4.next()).meta_item_list.iterator();
        }
        while (!localIterator5.hasNext());
        localMetadataInfo2 = (MetadataInfo)localIterator5.next();
      }
      while (!paramString.equals(localMetadataInfo2.name));
      Logger.i(TAG, "MetadataInfo=" + localMetadataInfo2.toString());
      putExtra("category_id", localMetadataInfo2.category_id);
      putExtra("packet_id", localMetadataInfo2.packet_id);
      return;
    }
    while (!"tclcategory".equals(localMetadataGoup.type));
    Logger.i(TAG, "toCategory  tclcategory type:" + localMetadataGoup.type + "  category:" + paramString);
    Iterator localIterator2 = localMetadataGoup.meta_index_list.iterator();
    label272: label300: MetadataInfo localMetadataInfo1;
    do
    {
      break label300;
      if (!localIterator2.hasNext())
        break;
      Iterator localIterator3 = ((MetadataGroupPageIndex)localIterator2.next()).meta_item_list.iterator();
      if (!localIterator3.hasNext())
        break label45;
      localMetadataInfo1 = (MetadataInfo)localIterator3.next();
    }
    while (!paramString.equals(localMetadataInfo1.name));
    Logger.i(TAG, "MetadataInfo=" + localMetadataInfo1.toString());
    putExtra("category_id", localMetadataInfo1.category_id);
    putExtra("packet_id", localMetadataInfo1.packet_id);
    Logger.i(TAG, "toCategory  tclcategory TO MOIVElIST");
  }

  protected void commonStart()
  {
    putExtra("Cmd", "com.starcor.hunan.cmd.common.show_category");
  }

  protected void startFromActivity()
  {
    showCategory();
  }

  protected void startFromReciever()
  {
    if (!GlobalLogic.getInstance().isAppInterfaceReady())
    {
      setClassForActivity(InitApiActivity.class);
      return;
    }
    showCategory();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.ShowCategoryBussines
 * JD-Core Version:    0.6.2
 */