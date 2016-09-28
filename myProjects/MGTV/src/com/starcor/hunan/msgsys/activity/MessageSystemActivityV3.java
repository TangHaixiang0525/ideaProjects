package com.starcor.hunan.msgsys.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.starcor.config.MgtvVersion;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.msgsys.data.MessageItemData;
import com.starcor.hunan.msgsys.data.MessageItemData.MessageType;
import com.starcor.hunan.msgsys.data.MessageItemData.SecondaryMessageType;
import com.starcor.hunan.msgsys.data.reservetopic.ReserveTopicMessageData;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.mqtt.service.MqttConnectService;
import com.starcor.hunan.msgsys.mqtt.service.MqttConnectService.LocalBinder;
import com.starcor.hunan.msgsys.utils.SharedPreferencesUtil;
import com.starcor.hunan.msgsys.widget.MessageView;
import com.starcor.hunan.msgsys.widget.MessageView.MessageViewType;
import com.starcor.hunan.msgsys.widget.MyMessageView;
import com.starcor.hunan.msgsys.widget.SystemMessageView;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.ui.UITools;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.XulView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageSystemActivityV3 extends DialogActivity
{
  private static final int DISMISS_LOADING_DIALOG = 1;
  public static final String INTENT_KEY_MY_MSG_ITEMS = "com.starcor.hunan.MessageSystemActivity.mymsg";
  public static final String INTENT_KEY_MY_MSG_UNREAD_NUM = "com.starcor.hunan.MessageSystemActivity.sysmsg_unread_num";
  public static final String INTENT_KEY_SYS_MSG_ITEMS = "com.starcor.hunan.MessageSystemActivity.sysmsg";
  public static final String INTENT_KEY_SYS_MSG_UNREAD_NUM = "com.starcor.hunan.MessageSystemActivity.mymsg_unread_num";
  public static final String MSG_SYS_ACTIVITY_TOPICS_KEY = "com.starcor.hunan.MessageSystemActivity.topics";
  private static final int MY_MESSAGE_BUTTON_ID = 65537;
  private static final int SHOW_CUSTOMIZED_TOAST = 0;
  private static final int SYSTEM_MESSAGE_BUTTON_ID = 65538;
  private static final String TAG = "MessageSystemActivityV3";
  private boolean isLoadSuccess = false;
  private boolean mAlreadyBinded = false;
  private Context mContext = null;
  private DBProvider mDBProvider = null;
  private LinearLayout mLeftContent;
  private MqttConnectService mMqttConnectService = null;
  private MyHandler mMyHandler = new MyHandler(null);
  private XulView mMyMessageBtn;
  private List<MessageItemData> mMyMessageItems = null;
  private int mMyMsgNum = 0;
  private int mMyMsgUnreadMsgNum = 0;
  private MessageView mMyMsgView = null;
  private LeftSideViewUpdater mMyNotifier = new LeftSideViewUpdater(null);
  private LinearLayout mRightContent;
  private XulView mSelectedBtn;
  private ServiceConnection mServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      Logger.i("MessageSystemActivityV3", "mServiceConnection onServiceConnected");
      if (paramAnonymousIBinder != null)
      {
        MqttConnectService.LocalBinder localLocalBinder = (MqttConnectService.LocalBinder)paramAnonymousIBinder;
        MessageSystemActivityV3.access$302(MessageSystemActivityV3.this, localLocalBinder.getService());
        MessageSystemActivityV3.access$202(MessageSystemActivityV3.this, true);
        Logger.i("MessageSystemActivityV3", "mAlreadyBinded=" + MessageSystemActivityV3.this.mAlreadyBinded);
        MessageSystemActivityV3.this.mMqttConnectService.loadMessagesFromDB();
      }
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      Logger.i("MessageSystemActivityV3", "mServiceConnection onServiceDisconnected");
      MessageSystemActivityV3.access$202(MessageSystemActivityV3.this, false);
    }
  };
  private List<MessageItemData> mSysMessageItems = null;
  private int mSysMsgNum = 0;
  private int mSysMsgUnreadMsgNum = 0;
  private MessageView mSysMsgView = null;
  private XulView mSystemMessagedBtn;
  private String mTopicFromPopupDialog = "";
  private List<String> mTopics = new ArrayList();
  private Handler mUiHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      Object localObject = paramAnonymousMessage.obj;
      int i = paramAnonymousMessage.what;
      Logger.i("MessageSystemActivityV3", "msgType=" + i);
      switch (i)
      {
      default:
        Logger.i("MessageSystemActivityV3", "default mode!");
      case 0:
        do
          return;
        while ((localObject == null) || (!(localObject instanceof String)));
        UITools.ShowCustomToast(MessageSystemActivityV3.this, (String)localObject);
        return;
      case 1:
      }
      MessageSystemActivityV3.this.dismissLoaddingDialog();
    }
  };
  private UpdateMessageViewsReceiver mUpdateMessageViewsReceiver = null;

  private void bindMqttService()
  {
    bindService(new Intent(this, MqttConnectService.class), this.mServiceConnection, 1);
  }

  private void init()
  {
    XulView localXulView1 = xulFindViewById("page_title_name");
    XulView localXulView2 = xulFindViewById("page_title_ext_name");
    localXulView1.setAttr("text", "消息");
    localXulView2.setAttr("text", "Message");
    localXulView1.resetRender();
    localXulView2.resetRender();
    XulView localXulView3 = xulFindViewById("message_system_view");
    if ((localXulView3 != null) && ((localXulView3.getExternalView() instanceof XulExt_LinerLayoutView)))
      this.mRightContent = ((LinearLayout)localXulView3.getExternalView());
    this.mMyMessageBtn = xulFindViewById("my_message_btn");
    this.mSystemMessagedBtn = xulFindViewById("sys_message_btn");
    this.mSelectedBtn = this.mSystemMessagedBtn;
  }

  private void initMyMessagePage()
  {
    if (this.mMyMsgView == null)
    {
      this.mMyMsgView = new MyMessageView(this.mContext, this.mMyMessageItems);
      this.mMyMsgView.setMessageNumberNotifier(this.mMyNotifier);
    }
    if (this.mMyMessageBtn != null)
      this.mMyMessageBtn.removeClass("page_content_item_radio_checked");
  }

  private void initSystemMessagePage()
  {
    if (this.mSysMsgView == null)
    {
      this.mSysMsgView = new SystemMessageView(this.mContext, this.mSysMessageItems);
      this.mSysMsgView.setMessageNumberNotifier(this.mMyNotifier);
    }
    if (this.mSystemMessagedBtn != null)
      this.mSystemMessagedBtn.removeClass("page_content_item_radio_checked");
  }

  private void initTopics()
  {
    String str1 = SharedPreferencesUtil.getString(this, "mgtv_client_sp", "client_id");
    String str2 = MgtvVersion.getVersion();
    this.mTopics.add("/public/" + str2);
    this.mTopics.add("/protected/admin");
    this.mTopics.add("/private/" + str1);
    Logger.i("MessageSystemActivityV3", "完成初始化主题数据！");
    Logger.i("MessageSystemActivityV3", "公共主题为" + (String)this.mTopics.get(0));
    Logger.i("MessageSystemActivityV3", "管理主题为" + (String)this.mTopics.get(1));
    Logger.i("MessageSystemActivityV3", "私有主题为" + (String)this.mTopics.get(2));
  }

  private void loadMyMessagePage()
  {
    if (this.mMyMsgView == null)
    {
      this.mMyMsgView = new MyMessageView(this.mContext, this.mMyMessageItems);
      this.mMyMsgView.setMessageNumberNotifier(this.mMyNotifier);
    }
    this.mSelectedBtn = this.mMyMessageBtn;
    this.mRightContent.removeAllViews();
    this.mRightContent.addView(this.mMyMsgView);
    if (!this.mMyMsgView.setFocusOnLastestMsg())
      xulRequestFocus(this.mMyMessageBtn);
    if (this.mMyMessageBtn != null)
      this.mMyMessageBtn.addClass("page_content_item_radio_checked");
  }

  private void loadSystemMessagePage()
  {
    if (this.mSysMsgView == null)
    {
      this.mSysMsgView = new SystemMessageView(this.mContext, this.mSysMessageItems);
      this.mSysMsgView.setMessageNumberNotifier(this.mMyNotifier);
    }
    this.mSelectedBtn = this.mSystemMessagedBtn;
    this.mRightContent.removeAllViews();
    this.mRightContent.addView(this.mSysMsgView);
    if (!this.mSysMsgView.setFocusOnLastestMsg())
      xulRequestFocus(this.mSystemMessagedBtn);
    while (true)
    {
      if (this.mSystemMessagedBtn != null)
        this.mSystemMessagedBtn.addClass("page_content_item_radio_checked");
      return;
      if (!TextUtils.isEmpty(this.mTopicFromPopupDialog))
      {
        RelativeLayout localRelativeLayout = this.mSysMsgView.getTitleLayoutOfFirstView();
        if (localRelativeLayout != null)
          localRelativeLayout.performClick();
      }
    }
  }

  private void setMessageNumberText(int paramInt)
  {
    boolean bool = true;
    if (65537 == paramInt)
      if (this.mMyMsgUnreadMsgNum > 0)
      {
        arrayOfObject2 = new Object[bool];
        arrayOfObject2[0] = Integer.valueOf(this.mMyMsgUnreadMsgNum);
        str2 = String.format("我的消息(%1$s)", arrayOfObject2);
        if (this.mMyMsgUnreadMsgNum <= 0)
          break label103;
        updateSetAllReadBtnText(paramInt, bool);
        if (this.mMyMessageBtn != null)
        {
          this.mMyMessageBtn.setAttr("text", str2);
          this.mMyMessageBtn.resetRender();
        }
      }
    label103: 
    while (65538 != paramInt)
      while (true)
      {
        Object[] arrayOfObject2;
        return;
        int j = this.mMyMsgUnreadMsgNum;
        String str2 = null;
        if (j == 0)
        {
          str2 = "我的消息";
          continue;
          bool = false;
        }
      }
    String str1;
    if (this.mSysMsgUnreadMsgNum > 0)
    {
      Object[] arrayOfObject1 = new Object[bool];
      arrayOfObject1[0] = Integer.valueOf(this.mSysMsgUnreadMsgNum);
      str1 = String.format("系统消息(%1$s)", arrayOfObject1);
      label148: if (this.mSysMsgUnreadMsgNum <= 0)
        break label207;
    }
    while (true)
    {
      updateSetAllReadBtnText(paramInt, bool);
      if (this.mSystemMessagedBtn == null)
        break;
      this.mSystemMessagedBtn.setAttr("text", str1);
      this.mSystemMessagedBtn.resetRender();
      return;
      int i = this.mSysMsgUnreadMsgNum;
      str1 = null;
      if (i != 0)
        break label148;
      str1 = "系统消息";
      break label148;
      label207: bool = false;
    }
  }

  private void updateSetAllReadBtnText(int paramInt, boolean paramBoolean)
  {
    Logger.d("MessageSystemActivityV3", "updateSetAllReadBtnText id: " + paramInt + ", isAllRead: " + paramBoolean);
    MessageView localMessageView;
    if (65538 == paramInt)
      localMessageView = this.mSysMsgView;
    while (true)
    {
      if (localMessageView != null)
      {
        localMessageView.updateSetAllReadBtnText(paramBoolean);
        localMessageView.saveAllReadFlag(paramBoolean);
      }
      return;
      localMessageView = null;
      if (65537 == paramInt)
        localMessageView = this.mMyMsgView;
    }
  }

  protected boolean needShowPopupDialog()
  {
    Logger.i("MessageSystemActivityV3", "needShowPopupDialog");
    return false;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mContext = this;
    if ((getIntent() != null) && (getIntent().hasExtra("topic")))
    {
      this.mTopicFromPopupDialog = getIntent().getStringExtra("topic");
      Logger.i("MessageSystemActivityV3", "topic=" + this.mTopicFromPopupDialog);
    }
    initXul("MessageSystem", true);
  }

  protected void onDestroy()
  {
    super.onDestroy();
    if (this.mUpdateMessageViewsReceiver != null)
      unregisterReceiver(this.mUpdateMessageViewsReceiver);
    if (this.mDBProvider != null)
    {
      this.mDBProvider.closeDB();
      this.mDBProvider = null;
    }
    if (this.mAlreadyBinded)
    {
      unbindService(this.mServiceConnection);
      this.mAlreadyBinded = false;
      Logger.i("MessageSystemActivityV3", "完成与MqttConnectService的解绑！");
    }
  }

  protected void onPause()
  {
    super.onPause();
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

  protected IXulExternalView xulCreateExternalView(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, XulView paramXulView)
  {
    if ("MessageSystemView".equals(paramString))
    {
      XulExt_LinerLayoutView localXulExt_LinerLayoutView = new XulExt_LinerLayoutView(this.context);
      xulAddView(localXulExt_LinerLayoutView);
      return localXulExt_LinerLayoutView;
    }
    return super.xulCreateExternalView(paramString, paramInt1, paramInt2, paramInt3, paramInt4, paramXulView);
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.i("MessageSystemActivityV3", "action=" + paramString1 + ",type=" + paramString2 + ",command=" + paramString3 + ",userData=" + paramObject);
    if ("click".equals(paramString1))
    {
      if (!"action_on_click_sys_msg_tag".equals(paramString3))
        break label102;
      Logger.i("MessageSystemActivityV3", "loadSystemMessagePage");
      loadSystemMessagePage();
    }
    while (true)
    {
      return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
      label102: if ("action_on_click_my_msg_tag".equals(paramString3))
      {
        Logger.i("MessageSystemActivityV3", "loadMyMessagePage");
        loadMyMessagePage();
      }
    }
  }

  protected void xulOnRenderIsReady()
  {
    init();
    initTopics();
    if (this.mUpdateMessageViewsReceiver == null)
    {
      this.mUpdateMessageViewsReceiver = new UpdateMessageViewsReceiver(null);
      IntentFilter localIntentFilter = new IntentFilter("com.starcor.hunan.new.message");
      registerReceiver(this.mUpdateMessageViewsReceiver, localIntentFilter);
    }
    this.mMyMessageItems = new ArrayList();
    this.mSysMessageItems = new ArrayList();
    bindMqttService();
    showLoaddingDialog();
    super.xulOnRenderIsReady();
  }

  private class LeftSideViewUpdater extends AbstractMQTTUIUpdateNotifier
  {
    private LeftSideViewUpdater()
    {
    }

    public void displayLoadingDialog(Object paramObject)
    {
      super.displayLoadingDialog(paramObject);
      MessageSystemActivityV3.this.showLoaddingDialog();
      if ((paramObject != null) && ((paramObject instanceof Bundle)))
      {
        Bundle localBundle = (Bundle)paramObject;
        if ((localBundle != null) && (localBundle.containsKey("message")))
        {
          String str = localBundle.getString("message");
          if (!TextUtils.isEmpty(str))
          {
            Message localMessage = new Message();
            localMessage.what = 0;
            localMessage.obj = str;
            MessageSystemActivityV3.this.mUiHandler.sendMessage(localMessage);
          }
        }
      }
    }

    public void finishLoadingAllMsg(List<MessageItemData> paramList1, List<MessageItemData> paramList2, int paramInt1, int paramInt2)
    {
      super.finishLoadingAllMsg(paramList1, paramList2, paramInt1, paramInt2);
      if (paramList1 != null)
      {
        Iterator localIterator2 = paramList1.iterator();
        while (localIterator2.hasNext())
        {
          MessageItemData localMessageItemData2 = (MessageItemData)localIterator2.next();
          if (localMessageItemData2 != null)
          {
            MessageSystemActivityV3.this.mSysMessageItems.add(localMessageItemData2);
            Logger.i("MessageSystemActivityV3", "加入一条系统消息为" + localMessageItemData2.toString());
          }
        }
        MessageSystemActivityV3.access$602(MessageSystemActivityV3.this, MessageSystemActivityV3.this.mSysMessageItems.size());
      }
      if (paramList2 != null)
      {
        Iterator localIterator1 = paramList2.iterator();
        while (localIterator1.hasNext())
        {
          MessageItemData localMessageItemData1 = (MessageItemData)localIterator1.next();
          if (localMessageItemData1 != null)
          {
            MessageSystemActivityV3.this.mMyMessageItems.add(localMessageItemData1);
            Logger.i("MessageSystemActivityV3", "加入一条我的消息为" + localMessageItemData1.toString());
          }
        }
        MessageSystemActivityV3.access$902(MessageSystemActivityV3.this, MessageSystemActivityV3.this.mMyMessageItems.size());
      }
      MessageSystemActivityV3.access$1002(MessageSystemActivityV3.this, paramInt2);
      MessageSystemActivityV3.access$702(MessageSystemActivityV3.this, paramInt1);
      if (MessageSystemActivityV3.this.mDBProvider != null)
        MessageSystemActivityV3.this.mDBProvider.closeDB();
      Logger.i("MessageSystemActivityV3", "before loading pages, mMyMsgNum=" + MessageSystemActivityV3.this.mMyMsgNum + " mSysMsgNum=" + MessageSystemActivityV3.this.mSysMsgNum + "topic=" + MessageSystemActivityV3.this.mTopicFromPopupDialog);
      if (TextUtils.isEmpty(MessageSystemActivityV3.this.mTopicFromPopupDialog))
      {
        if (MessageSystemActivityV3.this.mMyMsgNum > 0)
        {
          MessageSystemActivityV3.this.mMyHandler.sendEmptyMessage(0);
          return;
        }
        MessageSystemActivityV3.this.mMyHandler.sendEmptyMessage(1);
        return;
      }
      if (ReserveTopicMessageData.class.getSimpleName().equals(MessageSystemActivityV3.this.mTopicFromPopupDialog))
      {
        if (MessageSystemActivityV3.this.mMyMsgNum > 0)
        {
          MessageSystemActivityV3.this.mMyHandler.sendEmptyMessage(0);
          return;
        }
        MessageSystemActivityV3.this.mMyHandler.sendEmptyMessage(1);
        return;
      }
      if (MessageSystemActivityV3.this.mSysMsgNum > 0)
      {
        MessageSystemActivityV3.this.mMyHandler.sendEmptyMessage(1);
        return;
      }
      MessageSystemActivityV3.this.mMyHandler.sendEmptyMessage(0);
    }

    public void finishLoadingAllPublicAndPrivateMsg(List<MessageItemData> paramList1, List<MessageItemData> paramList2, int paramInt1, int paramInt2)
    {
      super.finishLoadingAllPublicAndPrivateMsg(paramList1, paramList2, paramInt1, paramInt2);
      if (paramList1 != null)
      {
        Iterator localIterator2 = paramList1.iterator();
        while (localIterator2.hasNext())
        {
          MessageItemData localMessageItemData2 = (MessageItemData)localIterator2.next();
          MessageSystemActivityV3.this.mMyMessageItems.add(localMessageItemData2);
          MessageSystemActivityV3.this.mSysMessageItems.add(localMessageItemData2);
          Logger.i("MessageSystemActivityV3", "加入一条公共消息为" + localMessageItemData2.toString());
        }
      }
      if (paramList2 != null)
      {
        Iterator localIterator1 = paramList2.iterator();
        while (localIterator1.hasNext())
        {
          MessageItemData localMessageItemData1 = (MessageItemData)localIterator1.next();
          MessageSystemActivityV3.this.mMyMessageItems.add(localMessageItemData1);
          MessageSystemActivityV3.this.mSysMessageItems.add(localMessageItemData1);
          Logger.i("MessageSystemActivityV3", "加入一条私有消息为" + localMessageItemData1.toString());
        }
      }
      MessageSystemActivityV3.access$902(MessageSystemActivityV3.this, MessageSystemActivityV3.this.mMyMessageItems.size());
      MessageSystemActivityV3.access$602(MessageSystemActivityV3.this, MessageSystemActivityV3.this.mSysMessageItems.size());
      MessageSystemActivityV3.access$1002(MessageSystemActivityV3.this, paramInt1 + paramInt2);
      MessageSystemActivityV3.access$702(MessageSystemActivityV3.this, paramInt1 + paramInt2);
      if (MessageSystemActivityV3.this.mDBProvider != null)
        MessageSystemActivityV3.this.mDBProvider.closeDB();
      MessageSystemActivityV3.this.mMyHandler.sendEmptyMessage(1);
      MessageSystemActivityV3.this.mMyHandler.sendEmptyMessage(0);
    }

    public void handleLeftEventFocus()
    {
      super.handleLeftEventFocus();
      if (MessageSystemActivityV3.this.mSystemMessagedBtn != null);
    }

    public void hideLoadingDialog(Object paramObject)
    {
      super.hideLoadingDialog(paramObject);
      if ((paramObject != null) && ((paramObject instanceof Bundle)))
      {
        Bundle localBundle = (Bundle)paramObject;
        if ((localBundle != null) && (localBundle.containsKey("message")))
        {
          String str = localBundle.getString("message");
          if (!TextUtils.isEmpty(str))
          {
            Message localMessage2 = new Message();
            localMessage2.what = 0;
            localMessage2.obj = str;
            MessageSystemActivityV3.this.mUiHandler.sendMessage(localMessage2);
          }
        }
      }
      Message localMessage1 = new Message();
      localMessage1.what = 1;
      MessageSystemActivityV3.this.mUiHandler.sendMessage(localMessage1);
    }

    public void loadWebDialog(String paramString)
    {
      super.loadWebDialog(paramString);
      if (!MessageSystemActivityV3.this.isFinishing())
      {
        MetadataInfo localMetadataInfo = new MetadataInfo();
        localMetadataInfo.action_type = "web";
        localMetadataInfo.url = paramString;
        Intent localIntent = new Intent();
        localIntent.putExtra("MetaDataInfo", localMetadataInfo);
        localIntent.setClass(MessageSystemActivityV3.this, ActivityAdapter.getInstance().getWebActivity());
        MessageSystemActivityV3.this.startActivity(localIntent);
      }
    }

    public void updateMessageNumberText(MessageView.MessageViewType paramMessageViewType, int paramInt)
    {
      super.updateMessageNumberText(paramMessageViewType, paramInt);
      if (paramMessageViewType != null)
      {
        Logger.i("MessageSystemActivityV3", "updateMessageNumberText type=" + paramMessageViewType.name());
        if (!MessageView.MessageViewType.SYSTEM_MESSAGE_PAGE.equals(paramMessageViewType))
          break label96;
        MessageSystemActivityV3.access$620(MessageSystemActivityV3.this, paramInt);
        if (MessageSystemActivityV3.this.mSysMsgNum <= 0)
        {
          MessageSystemActivityV3.access$602(MessageSystemActivityV3.this, 0);
          MessageSystemActivityV3.access$702(MessageSystemActivityV3.this, 0);
          MessageSystemActivityV3.this.setMessageNumberText(65538);
        }
      }
      label96: 
      do
      {
        do
          return;
        while (!MessageView.MessageViewType.MY_MESSAGE_PAGE.equals(paramMessageViewType));
        MessageSystemActivityV3.access$920(MessageSystemActivityV3.this, paramInt);
      }
      while (MessageSystemActivityV3.this.mMyMsgNum > 0);
      MessageSystemActivityV3.access$902(MessageSystemActivityV3.this, 0);
      MessageSystemActivityV3.access$1002(MessageSystemActivityV3.this, 0);
      MessageSystemActivityV3.this.setMessageNumberText(65537);
    }

    public void updateReadMyMsgNumber(int paramInt)
    {
      super.updateReadMyMsgNumber(paramInt);
      MessageSystemActivityV3.access$1020(MessageSystemActivityV3.this, paramInt);
      if (MessageSystemActivityV3.this.mMyMsgUnreadMsgNum < 0)
        MessageSystemActivityV3.access$1002(MessageSystemActivityV3.this, 0);
      MessageSystemActivityV3.this.setMessageNumberText(65537);
    }

    public void updateReadSysMsgNumber(int paramInt)
    {
      super.updateReadSysMsgNumber(paramInt);
      MessageSystemActivityV3.access$720(MessageSystemActivityV3.this, paramInt);
      if (MessageSystemActivityV3.this.mSysMsgUnreadMsgNum < 0)
        MessageSystemActivityV3.access$702(MessageSystemActivityV3.this, 0);
      MessageSystemActivityV3.this.setMessageNumberText(65538);
    }

    public void updateUnreadMyMsgNumber(int paramInt)
    {
      super.updateUnreadMyMsgNumber(paramInt);
      MessageSystemActivityV3.access$1012(MessageSystemActivityV3.this, paramInt);
      MessageSystemActivityV3.this.setMessageNumberText(65537);
    }

    public void updateUnreadSysMsgNumber(int paramInt)
    {
      super.updateUnreadSysMsgNumber(paramInt);
      MessageSystemActivityV3.access$712(MessageSystemActivityV3.this, paramInt);
      MessageSystemActivityV3.this.setMessageNumberText(65538);
    }
  }

  private class MyHandler extends Handler
  {
    public static final int LOAD_MY_MSG_PAG = 0;
    public static final int LOAD_SYS_MSG_PAG = 1;

    private MyHandler()
    {
    }

    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      switch (paramMessage.what)
      {
      default:
        return;
      case 0:
        MessageSystemActivityV3.this.setMessageNumberText(65537);
        MessageSystemActivityV3.this.setMessageNumberText(65538);
        MessageSystemActivityV3.this.dismissLoaddingDialog();
        MessageSystemActivityV3.this.loadMyMessagePage();
        MessageSystemActivityV3.this.initSystemMessagePage();
        return;
      case 1:
      }
      MessageSystemActivityV3.this.setMessageNumberText(65538);
      MessageSystemActivityV3.this.setMessageNumberText(65537);
      MessageSystemActivityV3.this.dismissLoaddingDialog();
      MessageSystemActivityV3.this.loadSystemMessagePage();
      MessageSystemActivityV3.this.initMyMessagePage();
    }
  }

  private class UpdateMessageViewsReceiver extends BroadcastReceiver
  {
    private UpdateMessageViewsReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      String str1;
      MessageItemData localMessageItemData;
      if (paramIntent != null)
      {
        str1 = paramIntent.getStringExtra("selector");
        if (str1 != null)
        {
          if (!"addMsg".equals(str1))
            break label243;
          localMessageItemData = (MessageItemData)paramIntent.getExtras().getParcelable("com.starcor.hunan.NEW_MSG_BODY");
          Logger.i("MessageSystemActivityV3", "收到新的消息数据 item = " + localMessageItemData.toString());
          MessageItemData.SecondaryMessageType localSecondaryMessageType = localMessageItemData.getSecondaryMessageType();
          switch (MessageSystemActivityV3.3.$SwitchMap$com$starcor$hunan$msgsys$data$MessageItemData$SecondaryMessageType[localSecondaryMessageType.ordinal()])
          {
          default:
          case 1:
          case 2:
          case 3:
          }
        }
      }
      label243: label379: 
      do
      {
        boolean bool;
        do
        {
          do
          {
            do
            {
              String str2;
              String str3;
              do
              {
                do
                {
                  do
                  {
                    do
                    {
                      do
                      {
                        do
                          return;
                        while (MessageSystemActivityV3.this.mSysMsgView == null);
                        Logger.i("MessageSystemActivityV3", "更新mSysMsgView信息");
                        MessageSystemActivityV3.access$608(MessageSystemActivityV3.this);
                        localMessageItemData.setMessageType(MessageItemData.MessageType.SYSTEM_MESSAGE);
                        MessageSystemActivityV3.this.mSysMsgView.updateMessageItemView(localMessageItemData);
                      }
                      while (MessageSystemActivityV3.this.mMyNotifier == null);
                      MessageSystemActivityV3.this.mMyNotifier.updateUnreadSysMsgNumber(1);
                      return;
                    }
                    while (MessageSystemActivityV3.this.mMyMsgView == null);
                    Logger.i("MessageSystemActivityV3", "更新mMyMsgView信息");
                    MessageSystemActivityV3.access$908(MessageSystemActivityV3.this);
                    localMessageItemData.setMessageType(MessageItemData.MessageType.MY_MESSAGE);
                    MessageSystemActivityV3.this.mMyMsgView.updateMessageItemView(localMessageItemData);
                  }
                  while (MessageSystemActivityV3.this.mMyNotifier == null);
                  MessageSystemActivityV3.this.mMyNotifier.updateUnreadMyMsgNumber(1);
                  return;
                  if (!"deleteMsg".equals(str1))
                    break label379;
                  str2 = paramIntent.getStringExtra("messageid");
                  str3 = paramIntent.getStringExtra("topic");
                  Logger.i("MessageSystemActivityV3", "delete " + str3 + " msgid=" + str2);
                  if ((!"public".equals(str3)) && (!"private".equals(str3)))
                    break;
                }
                while (MessageSystemActivityV3.this.mSysMsgView == null);
                MessageSystemActivityV3.this.mSysMsgView.deleteMessageItemView(str2);
                return;
              }
              while ((!"reserve".equals(str3)) || (MessageSystemActivityV3.this.mMyMsgView == null));
              MessageSystemActivityV3.this.mMyMsgView.deleteMessageItemView(str2);
              return;
              if (!"finishLoadingData".equals(str1))
                break;
            }
            while (MessageSystemActivityV3.this.mMyNotifier == null);
            if ((MessageSystemActivityV3.this.mAlreadyBinded) && (MessageSystemActivityV3.this.mMqttConnectService != null))
            {
              Logger.i("MessageSystemActivityV3", "收到广播，finishLoadingData，开始导入消息数据");
              MessageSystemActivityV3.this.mMyNotifier.finishLoadingAllMsg(MessageSystemActivityV3.this.mMqttConnectService.getSystemMessageData(), MessageSystemActivityV3.this.mMqttConnectService.getMyMessageData(), MessageSystemActivityV3.this.mMqttConnectService.getSystemMessageUnreadNumber(), MessageSystemActivityV3.this.mMqttConnectService.getMyMessageUnreadNumber());
              MessageSystemActivityV3.access$2402(MessageSystemActivityV3.this, true);
              MessageSystemActivityV3.this.reportLoad(MessageSystemActivityV3.this.isLoadSuccess, null);
              return;
            }
            MessageSystemActivityV3.access$2402(MessageSystemActivityV3.this, false);
            MessageSystemActivityV3.this.reportLoad(MessageSystemActivityV3.this.isLoadSuccess, null);
            MessageSystemActivityV3.this.bindMqttService();
            return;
          }
          while (!"deleteAllMsg".equals(str1));
          bool = paramIntent.getBooleanExtra("isUpdateDB", true);
          Logger.i("MessageSystemActivityV3", "userlogout  delete all message views! isUpdateDB=" + bool);
          if (MessageSystemActivityV3.this.mSysMsgView != null)
          {
            MessageSystemActivityV3.this.mSysMsgView.deleteAllMessageViews(bool);
            if (MessageSystemActivityV3.this.mSystemMessagedBtn != null)
              MessageSystemActivityV3.this.xulRequestFocus(MessageSystemActivityV3.this.mSystemMessagedBtn);
          }
        }
        while (MessageSystemActivityV3.this.mMyMsgView == null);
        MessageSystemActivityV3.this.mMyMsgView.deleteAllMessageViews(bool);
      }
      while (MessageSystemActivityV3.this.mMyMessageBtn == null);
      MessageSystemActivityV3.this.xulRequestFocus(MessageSystemActivityV3.this.mMyMessageBtn);
    }
  }

  private class XulExt_LinerLayoutView extends LinearLayout
    implements IXulExternalView
  {
    public XulExt_LinerLayoutView(Context arg2)
    {
      super();
    }

    public XulExt_LinerLayoutView(Context paramAttributeSet, AttributeSet arg3)
    {
      super(localAttributeSet);
    }

    public XulExt_LinerLayoutView(Context paramAttributeSet, AttributeSet paramInt, int arg4)
    {
      super(paramInt, i);
    }

    public void extDestroy()
    {
      removeAllViews();
    }

    public void extHide()
    {
      setVisibility(8);
    }

    public void extMoveTo(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
      localLayoutParams.leftMargin = paramInt1;
      localLayoutParams.topMargin = paramInt2;
      localLayoutParams.width = paramInt3;
      localLayoutParams.height = paramInt4;
      requestLayout();
    }

    public void extMoveTo(Rect paramRect)
    {
      extMoveTo(paramRect.left, paramRect.top, paramRect.width(), paramRect.height());
    }

    public void extOnBlur()
    {
      View localView = findFocus();
      if (localView != null)
        localView.clearFocus();
    }

    public void extOnFocus()
    {
      if (findFocus() == null)
        requestFocus();
    }

    public boolean extOnKeyEvent(KeyEvent paramKeyEvent)
    {
      return dispatchKeyEvent(paramKeyEvent);
    }

    public void extShow()
    {
      setVisibility(0);
    }

    public void extSyncData()
    {
    }

    public String getAttr(String paramString1, String paramString2)
    {
      return paramString2;
    }

    public boolean setAttr(String paramString1, String paramString2)
    {
      return false;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.activity.MessageSystemActivityV3
 * JD-Core Version:    0.6.2
 */