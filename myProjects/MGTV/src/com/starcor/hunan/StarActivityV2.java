package com.starcor.hunan;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.starcor.core.domain.SearchActorStarList;
import com.starcor.core.domain.SearchActorStarList.ActorStar;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.uilogic.ColorizedFilmNameGenerator;
import com.starcor.sccms.api.SccmsApiGetHotActorStarListTask.ISccmsApiGetHotActorStarListTaskListener;
import com.starcor.sccms.api.SccmsApiSearchActorStarListTask.ISccmsApiSearchActorStarListTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulView;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

public class StarActivityV2 extends BaseActivity
{
  private static final String TAG = "StarActivity";
  int PAGE_SIZE = 30;
  int allCount = 0;
  int curCount = 0;
  int curPage = 0;
  boolean isFinished = false;
  String labelId = "";
  private ColorizedFilmNameGenerator mColorGenerator;
  private XulView mXulEmptyTips;
  private XulView mXulFilmListView;
  private XulMassiveAreaWrapper mXulFilmListWrapper;
  private XulView mXulSearchResultView;
  private XulSliderAreaWrapper mXulSearchResultWrapper;
  String oldSearchKey = "";
  String searchKey = "";
  private XulView searchTextBox;
  private SearchActorStarList starList = null;

  private void addItem(int paramInt, SearchActorStarList.ActorStar paramActorStar)
  {
    if (paramActorStar == null)
      return;
    XulDataNode localXulDataNode = XulDataNode.obtainDataNode("item");
    localXulDataNode.setAttribute("colorized_name", this.mColorGenerator.getSpannedLabelText(paramActorStar.name));
    localXulDataNode.setAttribute("name", paramActorStar.name);
    localXulDataNode.setAttribute("name", paramActorStar.name);
    localXulDataNode.setAttribute("img_h", paramActorStar.img_h);
    localXulDataNode.setAttribute("img_v", paramActorStar.img_v);
    localXulDataNode.setAttribute("img_s", paramActorStar.img_s);
    localXulDataNode.setAttribute("label_id", paramActorStar.label_id);
    localXulDataNode.setAttribute("actor_id", paramActorStar.id);
    if (paramInt > -1)
    {
      this.mXulFilmListWrapper.addItem(paramInt, localXulDataNode);
      return;
    }
    this.mXulFilmListWrapper.addItem(localXulDataNode);
  }

  private void doSearch()
  {
    this.labelId = "";
    this.curPage = 0;
    this.curCount = 0;
    if (this.searchTextBox == null)
      this.searchTextBox = xulFindViewById("search_text_box");
    if (this.searchTextBox != null)
      this.searchKey = this.searchTextBox.getAttrString("text");
    ((XulArea)this.mXulSearchResultView).setDynamicFocus(null);
    isShowHotStarText(false);
    if (this.oldSearchKey.equals(this.searchKey))
      return;
    if (TextUtils.isEmpty(this.searchKey))
      getHotActorStarList();
    while (true)
    {
      this.oldSearchKey = this.searchKey;
      return;
      getSearchActorStarList();
    }
  }

  private void getHotActorStarList()
  {
    ServerApiManager.i().APIGetHotActorStarListTask(new SccmsApiGetHotActorStarListTask.ISccmsApiGetHotActorStarListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        StarActivityV2.this.isFinished = true;
        StarActivityV2.this.showEmptyTips(true);
        StarActivityV2.this.isShowHotStarText(false);
        StarActivityV2.this.showErrorDialogAndReport("ISccmsApiGetHotActorStarListTaskListener.onError", paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, SearchActorStarList paramAnonymousSearchActorStarList)
      {
        StarActivityV2.this.isFinished = true;
        int i;
        if ((paramAnonymousSearchActorStarList != null) && (paramAnonymousSearchActorStarList.lists != null))
        {
          i = paramAnonymousSearchActorStarList.lists.size();
          if (i > 0)
            break label63;
        }
        label63: for (paramAnonymousSearchActorStarList.total_rows = 0; ; paramAnonymousSearchActorStarList.total_rows = i)
        {
          StarActivityV2.access$302(StarActivityV2.this, paramAnonymousSearchActorStarList);
          StarActivityV2.this.isShowHotStarText(true);
          StarActivityV2.this.processResultInfo();
          return;
        }
      }
    });
  }

  private void getMoreSearchActorStarList()
  {
    ServerApiManager.i().APISearchActorStarListTask(this.curPage, this.PAGE_SIZE, this.labelId, this.searchKey, new SccmsApiSearchActorStarListTask.ISccmsApiSearchActorStarListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        StarActivityV2.this.showErrorDialogAndReport("ISccmsApiSearchActorStarListTaskListener.onError", paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, SearchActorStarList paramAnonymousSearchActorStarList)
      {
        StarActivityV2.access$302(StarActivityV2.this, paramAnonymousSearchActorStarList);
        if ((StarActivityV2.this.starList != null) && (StarActivityV2.this.starList.lists != null))
        {
          StarActivityV2.this.allCount = StarActivityV2.this.starList.total_rows;
          StarActivityV2 localStarActivityV2 = StarActivityV2.this;
          localStarActivityV2.curCount += StarActivityV2.this.starList.lists.size();
        }
        StarActivityV2.this.mColorGenerator.setMatcher(StarActivityV2.this.searchKey);
        Iterator localIterator = StarActivityV2.this.starList.lists.iterator();
        while (localIterator.hasNext())
        {
          SearchActorStarList.ActorStar localActorStar = (SearchActorStarList.ActorStar)localIterator.next();
          if ((localActorStar != null) && (!TextUtils.isEmpty(localActorStar.id)))
            StarActivityV2.this.addItem(-1, localActorStar);
        }
        StarActivityV2.this.mXulFilmListWrapper.syncContentView();
      }
    });
  }

  private void getSearchActorStarList()
  {
    ServerApiManager.i().APISearchActorStarListTask(this.curPage, this.PAGE_SIZE, this.labelId, this.searchKey, new SccmsApiSearchActorStarListTask.ISccmsApiSearchActorStarListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        StarActivityV2.this.showErrorDialogAndReport("ISccmsApiSearchActorStarListTaskListener.onError", paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, SearchActorStarList paramAnonymousSearchActorStarList)
      {
        StarActivityV2.access$302(StarActivityV2.this, paramAnonymousSearchActorStarList);
        if ((StarActivityV2.this.starList != null) && (StarActivityV2.this.starList.lists != null))
        {
          StarActivityV2.this.allCount = StarActivityV2.this.starList.total_rows;
          StarActivityV2 localStarActivityV2 = StarActivityV2.this;
          localStarActivityV2.curCount += StarActivityV2.this.starList.lists.size();
        }
        StarActivityV2.this.isShowHotStarText(false);
        StarActivityV2.this.processResultInfo();
      }
    });
  }

  private void initViews()
  {
    this.mXulFilmListView = xulFindViewById("film_list_view");
    this.mXulFilmListWrapper = XulMassiveAreaWrapper.fromXulView(this.mXulFilmListView);
    this.mXulEmptyTips = xulFindViewById("page_details_empty_tips");
    this.searchTextBox = xulFindViewById("search_text_box");
    this.mXulSearchResultView = xulFindViewById("search_result_view");
    this.mXulSearchResultWrapper = XulSliderAreaWrapper.fromXulView(this.mXulSearchResultView);
    this.mColorGenerator = new ColorizedFilmNameGenerator();
    XulView localXulView = xulFindViewById("search_tip");
    if (localXulView != null)
    {
      localXulView.setAttr("text", "支持影人首字母输入");
      localXulView.resetRender();
    }
  }

  private void isShowHotStarText(boolean paramBoolean)
  {
    XulView localXulView = xulFindViewById("hot_star_label");
    if (localXulView != null)
      if (!paramBoolean)
        break label31;
    label31: for (String str = "block"; ; str = "none")
    {
      localXulView.setStyle("display", str);
      localXulView.resetRender();
      return;
    }
  }

  private void load_more_data(String paramString)
  {
    try
    {
      JSONObject localJSONObject1 = new JSONObject(paramString);
      localJSONObject2 = localJSONObject1;
      if ((localJSONObject2.optInt("idx") >= this.curCount - this.PAGE_SIZE / 2) && (this.curCount < this.allCount))
      {
        this.curPage = (this.curCount / this.PAGE_SIZE);
        Logger.i("StarActivity", "load_more_data page=" + this.curPage + ",all=" + this.allCount);
        getMoreSearchActorStarList();
      }
      return;
    }
    catch (Exception localException)
    {
      while (true)
        JSONObject localJSONObject2 = null;
    }
  }

  private void processResultInfo()
  {
    if (this.mXulSearchResultWrapper != null)
      this.mXulSearchResultWrapper.scrollTo(0, false);
    if ((this.starList == null) || (this.starList.lists == null) || (this.starList.lists.size() == 0))
    {
      showEmptyTips(true);
      isShowHotStarText(false);
      return;
    }
    showEmptyTips(false);
    this.mXulFilmListWrapper.clear();
    this.mColorGenerator.setMatcher(this.searchKey);
    Iterator localIterator = this.starList.lists.iterator();
    while (localIterator.hasNext())
    {
      SearchActorStarList.ActorStar localActorStar = (SearchActorStarList.ActorStar)localIterator.next();
      if ((localActorStar != null) && (!TextUtils.isEmpty(localActorStar.id)))
        addItem(-1, localActorStar);
    }
    this.mXulFilmListWrapper.syncContentView();
  }

  private void showEmptyTips(boolean paramBoolean)
  {
    String str2;
    XulView localXulView1;
    if (this.mXulFilmListView != null)
    {
      XulView localXulView2 = this.mXulFilmListView;
      if (!paramBoolean)
      {
        str2 = "block";
        localXulView2.setStyle("display", str2);
        this.mXulFilmListView.resetRender();
      }
    }
    else if (this.mXulEmptyTips != null)
    {
      this.mXulEmptyTips.setAttr("text", "对不起, 没有与搜索项符合的结果!");
      localXulView1 = this.mXulEmptyTips;
      if (!paramBoolean)
        break label93;
    }
    label93: for (String str1 = "block"; ; str1 = "none")
    {
      localXulView1.setStyle("display", str1);
      this.mXulEmptyTips.resetRender();
      return;
      str2 = "none";
      break;
    }
  }

  private void showErrorDialogAndReport(String paramString, ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
  {
    if (!this.hasDialog)
    {
      showErrorDialogWithReport(11, "1002008", paramString, paramServerApiTaskInfo, paramServerApiCommonError);
      this.hasDialog = true;
    }
  }

  public void dealKeyEvent(KeyEvent paramKeyEvent)
  {
    Logger.i("StarActivity", "dealKeyEvent " + paramKeyEvent.getKeyCode());
    if (paramKeyEvent.getKeyCode() == 20)
    {
      XulView localXulView = xulGetFocus();
      if (localXulView == null);
      do
      {
        return;
        if ((this.mXulFilmListView == null) || (!localXulView.hasClass("poster-item")))
          break;
      }
      while (this.allCount == 0);
      int i = this.mXulFilmListWrapper.getItemIdx(localXulView) / 5;
      if ((-1 + (5 + this.allCount)) / 5 == i + 2)
      {
        xulRequestFocus(((XulArea)this.mXulFilmListView).getLastChild());
        return;
      }
    }
    super.dealKeyEvent(paramKeyEvent);
  }

  public void onBackPressed()
  {
    if (!TextUtils.isEmpty(this.searchKey))
    {
      this.searchKey = "";
      if (this.searchTextBox == null)
        this.searchTextBox = xulFindViewById("search_text_box");
      if (this.searchTextBox != null)
      {
        this.searchTextBox.setAttr("text", "");
        this.searchTextBox.resetRender();
      }
      doSearch();
      return;
    }
    super.onBackPressed();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    initXul("StarPageV2", true);
  }

  // ERROR //
  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    // Byte code:
    //   0: ldc 8
    //   2: new 277	java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial 278	java/lang/StringBuilder:<init>	()V
    //   9: ldc_w 400
    //   12: invokevirtual 284	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   15: aload_2
    //   16: invokevirtual 284	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   19: ldc_w 402
    //   22: invokevirtual 284	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: aload_3
    //   26: invokevirtual 284	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: ldc_w 404
    //   32: invokevirtual 284	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: aload 4
    //   37: invokevirtual 284	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: ldc_w 406
    //   43: invokevirtual 284	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: aload 5
    //   48: invokevirtual 409	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   51: invokevirtual 293	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   54: invokestatic 297	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   57: aload_0
    //   58: invokevirtual 412	com/starcor/hunan/StarActivityV2:xulIsReady	()Z
    //   61: ifne +13 -> 74
    //   64: ldc 8
    //   66: ldc_w 414
    //   69: invokestatic 297	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   72: iconst_0
    //   73: ireturn
    //   74: ldc_w 415
    //   77: aload 4
    //   79: invokevirtual 179	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   82: ifeq +9 -> 91
    //   85: aload_0
    //   86: invokespecial 384	com/starcor/hunan/StarActivityV2:doSearch	()V
    //   89: iconst_1
    //   90: ireturn
    //   91: ldc_w 416
    //   94: aload_3
    //   95: invokevirtual 179	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   98: ifeq +12 -> 110
    //   101: aload_0
    //   102: aload 4
    //   104: invokespecial 418	com/starcor/hunan/StarActivityV2:load_more_data	(Ljava/lang/String;)V
    //   107: goto -18 -> 89
    //   110: ldc_w 420
    //   113: aload_3
    //   114: invokevirtual 179	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   117: ifeq -28 -> 89
    //   120: ldc 39
    //   122: astore 6
    //   124: ldc 39
    //   126: astore 7
    //   128: ldc 39
    //   130: astore 8
    //   132: new 267	org/json/JSONObject
    //   135: dup
    //   136: aload 4
    //   138: invokespecial 269	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   141: astore 9
    //   143: aload 9
    //   145: ldc 120
    //   147: invokevirtual 423	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   150: astore 6
    //   152: aload 9
    //   154: ldc_w 425
    //   157: invokevirtual 423	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   160: astore 7
    //   162: aload 9
    //   164: ldc_w 427
    //   167: invokevirtual 423	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   170: astore 15
    //   172: aload 15
    //   174: astore 8
    //   176: aload 7
    //   178: invokestatic 185	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   181: ifeq +15 -> 196
    //   184: iconst_1
    //   185: ireturn
    //   186: astore 10
    //   188: aload 10
    //   190: invokevirtual 430	java/lang/Exception:printStackTrace	()V
    //   193: goto -17 -> 176
    //   196: new 432	android/content/Intent
    //   199: dup
    //   200: aload_0
    //   201: ldc_w 434
    //   204: invokespecial 437	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
    //   207: astore 11
    //   209: aload 11
    //   211: ldc_w 439
    //   214: aload 6
    //   216: invokevirtual 443	android/content/Intent:putExtra	(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   219: pop
    //   220: aload 11
    //   222: ldc_w 425
    //   225: aload 7
    //   227: invokevirtual 443	android/content/Intent:putExtra	(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   230: pop
    //   231: aload 11
    //   233: ldc_w 427
    //   236: aload 8
    //   238: invokevirtual 443	android/content/Intent:putExtra	(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   241: pop
    //   242: aload_0
    //   243: aload 11
    //   245: invokevirtual 447	com/starcor/hunan/StarActivityV2:startActivity	(Landroid/content/Intent;)V
    //   248: goto -159 -> 89
    //   251: astore 10
    //   253: goto -65 -> 188
    //
    // Exception table:
    //   from	to	target	type
    //   132	143	186	java/lang/Exception
    //   143	172	251	java/lang/Exception
  }

  protected void xulOnRenderIsReady()
  {
    initViews();
    getHotActorStarList();
    super.xulOnRenderIsReady();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.StarActivityV2
 * JD-Core Version:    0.6.2
 */