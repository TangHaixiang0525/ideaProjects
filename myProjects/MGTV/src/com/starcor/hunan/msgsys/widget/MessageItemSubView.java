package com.starcor.hunan.msgsys.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.SpecialActivityV2;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.msgsys.data.MessageButtonBody;
import com.starcor.hunan.msgsys.data.MessageItemData;
import com.starcor.hunan.msgsys.data.MessageItemData.MessageActionType;
import com.starcor.hunan.msgsys.data.MessageItemData.SecondaryMessageType;
import com.starcor.hunan.msgsys.data.privatetopic.PrivateTopicMessageData;
import com.starcor.hunan.msgsys.data.publictopic.PublicTopicMessageData;
import com.starcor.hunan.msgsys.data.reservetopic.ReserveTopicMessageData;
import com.starcor.hunan.msgsys.dbupdater.MQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import com.starcor.hunan.msgsys.mediaplayerhelper.MediaPlayerHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.MediaAssetHelperCallback;
import com.starcor.ui.UITools;
import java.net.URLDecoder;
import java.util.Hashtable;
import java.util.List;

public class MessageItemSubView extends LinearLayout
  implements View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener
{
  private static final String TAG = MessageItemSubView.class.getSimpleName();
  private static final int UPDATE_BUTTON_TITLE;
  private static int mId = 100;
  private Button mActionButton0 = null;
  private Button mActionButton1 = null;
  private Button mActionButton2 = null;
  private Animation mAlphaAnimation = null;
  private MediaAssetHelperListener mAssetCallBack = new MediaAssetHelperListener(null);
  private LinearLayout mBtnLinearLayout = null;
  private List<MessageButtonBody> mButtonList = null;
  private LinearLayout mContentLayout = null;
  private Context mContext = null;
  private TextView mDate = null;
  private Handler mDelMsgHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      if ((MessageItemSubView.this.mSelf != null) && (MessageItemSubView.this.mNotifier != null))
        MessageItemSubView.this.mNotifier.removeMessage(MessageItemSubView.this.mSelf);
    }
  };
  private TextView mDescription = null;
  private RelativeLayout mDetailedContentLayout = null;
  private TextView mDetailedDate = null;
  private TextView mDetailedDescription = null;
  private TextView mDetailedTitle = null;
  private ImageView mDetailedUnreadImg = null;
  private Animation mDownAnimation = null;
  private MessageButtonBody mFirstButton = null;
  private boolean mIsLastItem = false;
  private Animation mLeftToRightAnimation = null;
  private MessageItemData mMsgItem = null;
  public MessageItemSubView mNextMsgItem = null;
  private AbstractMQTTUIUpdateNotifier mNotifier = null;
  private ScrollView mParentScrollView = null;
  public MessageItemSubView mPrevMsgItem = null;
  private MessageItemSubView mSelf = null;
  private ScrollView mSubScrollView = null;
  private TextView mTitle = null;
  private RelativeLayout mTitleLayout = null;
  private int mTitleLayoutBackground = 0;
  private Hashtable<MessageItemData.MessageActionType, String> mTitleTable = null;
  private Handler mUiHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      Object localObject = paramAnonymousMessage.obj;
      Bundle localBundle = null;
      if (localObject != null)
      {
        boolean bool = localObject instanceof Bundle;
        localBundle = null;
        if (bool)
        {
          Logger.i(MessageItemSubView.TAG, "paramsObj is an instance of Bundle!");
          localBundle = (Bundle)localObject;
        }
      }
      int i = paramAnonymousMessage.what;
      Logger.i(MessageItemSubView.TAG, "msgType=" + i);
      switch (i)
      {
      default:
        Logger.i(MessageItemSubView.TAG, "default mode!");
      case 0:
      }
      String str1;
      String str2;
      do
      {
        return;
        str1 = "1";
        str2 = "";
        if (localBundle.containsKey("buttons"))
        {
          str1 = localBundle.getString("buttons");
          Logger.i(MessageItemSubView.TAG, "whichButton=" + str1);
        }
        if (localBundle.containsKey("title"))
        {
          str2 = localBundle.getString("title");
          Logger.i(MessageItemSubView.TAG, "newBtnName=" + str2);
        }
        if (TextUtils.isEmpty(str2))
        {
          Logger.i(MessageItemSubView.TAG, "newBtnName is empty!");
          return;
        }
      }
      while ((!"1".equals(str1)) || (MessageItemSubView.this.mActionButton1 == null));
      MessageItemSubView.this.mActionButton1.setText(str2);
      Logger.i(MessageItemSubView.TAG, "successfully set first button!");
    }
  };
  private ImageView mUnreadImg = null;
  private Animation mUpAnimation = null;

  public MessageItemSubView(Context paramContext, ScrollView paramScrollView, MessageItemData paramMessageItemData)
  {
    super(paramContext);
    this.mParentScrollView = paramScrollView;
    this.mMsgItem = paramMessageItemData;
    this.mContext = paramContext;
    this.mSelf = this;
    int i;
    if (this.mMsgItem != null)
    {
      Logger.i(TAG, "初始化一条消息视图，数据为" + this.mMsgItem.toString());
      this.mButtonList = this.mMsgItem.getMessageButtonBodies();
      if (this.mButtonList != null)
      {
        i = this.mButtonList.size();
        if (i <= 1)
          break label415;
        this.mFirstButton = ((MessageButtonBody)this.mButtonList.get(1));
        this.mButtonList.clear();
        this.mButtonList.add(this.mFirstButton);
      }
    }
    while (true)
    {
      this.mTitleTable = new Hashtable();
      this.mTitleTable.put(MessageItemData.MessageActionType.PLAY_VIDEO, "播放");
      this.mTitleTable.put(MessageItemData.MessageActionType.VIEW_DETAIL, "查看");
      this.mTitleTable.put(MessageItemData.MessageActionType.OPEN_PAGE, "查看");
      this.mTitleTable.put(MessageItemData.MessageActionType.SPECIAL_PAGE, "查看");
      init();
      initAbbrevLayout();
      initDetailedLayout();
      initData();
      return;
      label415: if (1 == i)
        this.mFirstButton = ((MessageButtonBody)this.mButtonList.get(0));
    }
  }

  private IMQTTMessageDBUpdater.TopicTableUpdateActionType getActionType(String paramString)
  {
    if ("wish_order_cancel".equals(paramString))
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_WISH_ORDER_CANCEL;
    if ("wish_order".equals(paramString))
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_WISH_ORDER;
    if ("back_play_cancel".equals(paramString))
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_BACK_PLAY_CANCEL;
    if ("back_play".equals(paramString))
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_BACK_PLAY;
    if ("chase_drama_cancel".equals(paramString))
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_CHASE_DRAMA_CANCEL;
    if ("chase_drama".equals(paramString))
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_CHASE_DRAMA;
    if ("live_show_cancel".equals(paramString))
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_LIVE_SHOW_CANCEL;
    if ("live_show".equals(paramString))
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_LIVE_SHOW;
    if ("turn_play_cancel".equals(paramString));
    while (true)
    {
      return null;
      if (!"turn_play".equals(paramString));
    }
  }

  private String getOrderState(String paramString)
  {
    Logger.i(TAG, "getOrderState action=" + paramString);
    String str = "";
    if (!TextUtils.isEmpty(paramString))
      if ((!"reservetopic".equals(paramString)) && (!"reserveturnplay".equals(paramString)) && (!"back_play".equals(paramString)) && (!"chase_drama".equals(paramString)) && (!"live_show".equals(paramString)) && (!"wish_order".equals(paramString)) && (!"turn_play".equals(paramString)))
        break label139;
    for (str = "order"; ; str = "cancel_order")
      label139: 
      do
      {
        Logger.i(TAG, "orderState = " + str);
        return str;
      }
      while ((!"back_play_cancel".equals(paramString)) && (!"chase_drama_cancel".equals(paramString)) && (!"live_show_cancel".equals(paramString)) && (!"wish_order_cancel".equals(paramString)) && (!"turn_play_cancel".equals(paramString)));
  }

  private IMQTTMessageDBUpdater.TopicTableUpdateActionType getUpdateActionType(MessageView.MessageSubItemActionType paramMessageSubItemActionType)
  {
    MessageItemData.SecondaryMessageType localSecondaryMessageType;
    if (this.mMsgItem != null)
    {
      localSecondaryMessageType = this.mMsgItem.getSecondaryMessageType();
      switch (6.$SwitchMap$com$starcor$hunan$msgsys$widget$MessageView$MessageSubItemActionType[paramMessageSubItemActionType.ordinal()])
      {
      default:
      case 1:
      case 2:
      case 3:
      }
    }
    do
    {
      do
      {
        do
        {
          return null;
          if (localSecondaryMessageType.equals(MessageItemData.SecondaryMessageType.PUBLIC_TOPIC_MESSAAGE))
            return IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_PUBLIC_TOPIC_MESSAGE;
          if (localSecondaryMessageType.equals(MessageItemData.SecondaryMessageType.PRIVATE_TOPOIC_MESSAGE))
            return IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_PRIVATE_TOPIC_MESSAGE;
          if (localSecondaryMessageType.equals(MessageItemData.SecondaryMessageType.ADMIN_TOPOIC_MESSAGE))
            return IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ADMIN_TOPIC_MESSAGE;
        }
        while (!localSecondaryMessageType.equals(MessageItemData.SecondaryMessageType.RESERVE_TOPIC_MESSAGE));
        return IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_RESERVE_TOPIC_MESSAGE;
        if (localSecondaryMessageType.equals(MessageItemData.SecondaryMessageType.PUBLIC_TOPIC_MESSAAGE))
          return IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PUBLIC_TOPIC_MESSAGE_READ;
        if (localSecondaryMessageType.equals(MessageItemData.SecondaryMessageType.PRIVATE_TOPOIC_MESSAGE))
          return IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PRIVATE_TOPIC_MESSAGE_READ;
        if (localSecondaryMessageType.equals(MessageItemData.SecondaryMessageType.ADMIN_TOPOIC_MESSAGE))
          return IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ADMIN_TOPIC_MESSAGE_READ;
      }
      while (!localSecondaryMessageType.equals(MessageItemData.SecondaryMessageType.RESERVE_TOPIC_MESSAGE));
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_RESERVE_TOPIC_MESSAGE_READ;
      if (localSecondaryMessageType.equals(MessageItemData.SecondaryMessageType.PUBLIC_TOPIC_MESSAAGE))
        return IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PUBLIC_TOPIC_MESSAGE_UNREAD;
      if (localSecondaryMessageType.equals(MessageItemData.SecondaryMessageType.PRIVATE_TOPOIC_MESSAGE))
        return IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PRIVATE_TOPIC_MESSAGE_UNREAD;
      if (localSecondaryMessageType.equals(MessageItemData.SecondaryMessageType.ADMIN_TOPOIC_MESSAGE))
        return IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ADMIN_TOPIC_MESSAGE_UNREAD;
    }
    while (!localSecondaryMessageType.equals(MessageItemData.SecondaryMessageType.RESERVE_TOPIC_MESSAGE));
    return IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_RESERVE_TOPIC_MESSAGE_UNREAD;
  }

  private void handleButtonAction(String paramString)
  {
    Logger.i(TAG, "handleButtonAction action=" + paramString);
    if ((this.mContext instanceof DialogActivity))
      ((DialogActivity)this.mContext).isMsgListClick = true;
    String str10;
    if ("page".equals(paramString))
    {
      str10 = this.mFirstButton.getArgs();
      if (TextUtils.isEmpty(str10))
        Logger.i(TAG, "由于page url为空，因此打开网页失败！");
    }
    String[] arrayOfString1;
    do
    {
      String str1;
      do
      {
        do
        {
          return;
          Logger.i(TAG, "打开网页" + str10);
          try
          {
            str11 = URLDecoder.decode(str10, "utf-8");
            Logger.i(TAG, "decode pageUrl=" + str11);
            openWebDialog(str11);
            return;
          }
          catch (Exception localException4)
          {
            while (true)
            {
              localException4.printStackTrace();
              String str11 = this.mFirstButton.getArgs();
            }
          }
          if (!"topic".equals(paramString))
            break;
          Object localObject = this.mFirstButton.getArgs();
          if (TextUtils.isEmpty((CharSequence)localObject))
          {
            Logger.i(TAG, "由于action args为空，不能打开专题页！");
            return;
          }
          String[] arrayOfString3 = ((String)localObject).split("&");
          if ((arrayOfString3 != null) && (arrayOfString3.length > 1))
          {
            int m = 0;
            if (m < arrayOfString3.length)
            {
              String str9 = arrayOfString3[m];
              if (TextUtils.isEmpty(str9));
              String[] arrayOfString5;
              do
              {
                m++;
                break;
                arrayOfString5 = str9.split("=");
              }
              while ((arrayOfString5 == null) || (arrayOfString5.length != 2) || (!"special_id".equals(arrayOfString5[0])));
              Logger.i(TAG, "found speical id==>" + arrayOfString5[0]);
              localObject = str9;
            }
          }
          String[] arrayOfString4 = ((String)localObject).split("=");
          if ((arrayOfString4 != null) && (arrayOfString4.length == 2))
          {
            Logger.i(TAG, "专题packet_id=" + arrayOfString4[1]);
            Intent localIntent = new Intent(this.mContext, SpecialActivityV2.class);
            MetadataInfo localMetadataInfo = new MetadataInfo();
            localMetadataInfo.packet_id = arrayOfString4[1];
            try
            {
              localMetadataInfo.packet_id = URLDecoder.decode(arrayOfString4[1], "utf-8");
              Logger.i(TAG, "decode packet_id=" + localMetadataInfo.packet_id);
              localIntent.putExtra("MetaDataInfo", localMetadataInfo);
              localIntent.addFlags(8388608);
              this.mContext.startActivity(localIntent);
              return;
            }
            catch (Exception localException3)
            {
              while (true)
              {
                localException3.printStackTrace();
                localMetadataInfo.packet_id = arrayOfString4[1];
              }
            }
          }
        }
        while (this.mAssetCallBack == null);
        this.mAssetCallBack.onError(paramString, "很抱歉，该影片资源已被删除或不存在");
        return;
        Logger.i(TAG, "current mActionButton1 title =" + this.mActionButton1.getText());
        if ("预约成功".equals(this.mActionButton1.getText()))
        {
          UITools.ShowCustomToast(this.mContext, "您已预约成功，请勿重复操作！");
          return;
        }
        str1 = this.mFirstButton.getArgs();
      }
      while ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(paramString)));
      arrayOfString1 = str1.split("&");
    }
    while ((arrayOfString1 == null) || (arrayOfString1.length <= 0));
    int i = arrayOfString1.length;
    Bundle localBundle = new Bundle();
    int j = 0;
    if (j < i)
    {
      String str3 = arrayOfString1[j];
      if (TextUtils.isEmpty(str3));
      while (true)
      {
        j++;
        break;
        String[] arrayOfString2 = str3.split("=");
        if (arrayOfString2 != null)
          if (arrayOfString2.length == 2)
          {
            String str7 = arrayOfString2[1];
            try
            {
              str8 = URLDecoder.decode(str7, "utf-8");
              Logger.i(TAG, "decode value=" + str8);
              localBundle.putString(arrayOfString2[0], str8);
            }
            catch (Exception localException2)
            {
              while (true)
              {
                localException2.printStackTrace();
                String str8 = arrayOfString2[1];
              }
            }
          }
          else if (arrayOfString2.length > 2)
          {
            int k = str3.indexOf("=");
            String str4 = str3.substring(0, k);
            String str5 = "";
            if (k + 1 < str3.length())
              str5 = str3.substring(k + 1);
            try
            {
              str6 = URLDecoder.decode(str5, "utf-8");
              Logger.i(TAG, "decode value=" + str6);
              localBundle.putString(str4, str6);
            }
            catch (Exception localException1)
            {
              while (true)
              {
                localException1.printStackTrace();
                String str6 = arrayOfString2[1];
              }
            }
          }
      }
    }
    Logger.i(TAG, "action = " + paramString);
    if (("reservetopic".equals(paramString)) || ("reserveturnplay".equals(paramString)) || ("video".equals(paramString)) || ("detail".equals(paramString)))
      showLoadingDialog(null);
    String str2;
    MessageItemData.SecondaryMessageType localSecondaryMessageType;
    if (localBundle != null)
    {
      if (!localBundle.containsKey("message_id"))
      {
        Logger.i(TAG, "put message id(" + this.mMsgItem.getMessageId() + ") into argBundle");
        localBundle.putString("message_id", this.mMsgItem.getMessageId());
      }
      if (!localBundle.containsKey("action"))
      {
        Logger.i(TAG, "put action(" + paramString + ") into argBundle");
        localBundle.putString("action", paramString);
      }
      if (!localBundle.containsKey("topic"))
      {
        str2 = PublicTopicMessageData.class.getSimpleName();
        localSecondaryMessageType = this.mMsgItem.getSecondaryMessageType();
        if (!MessageItemData.SecondaryMessageType.PRIVATE_TOPOIC_MESSAGE.equals(localSecondaryMessageType))
          break label1200;
        str2 = PrivateTopicMessageData.class.getSimpleName();
      }
    }
    while (true)
    {
      Logger.i(TAG, "put topic(" + str2 + ") into argBundle");
      localBundle.putString("topic", str2);
      MediaPlayerHelper localMediaPlayerHelper = new MediaPlayerHelper(this.mContext, paramString, localBundle);
      localMediaPlayerHelper.setListener(this.mAssetCallBack);
      localMediaPlayerHelper.startExecuteAction();
      return;
      label1200: if (MessageItemData.SecondaryMessageType.RESERVE_TOPIC_MESSAGE.equals(localSecondaryMessageType))
        str2 = ReserveTopicMessageData.class.getSimpleName();
    }
  }

  private void handleOrderOperation(String paramString)
  {
    Logger.i(TAG, "handleOrderOperation");
    String str1 = getOrderState(paramString);
    Bundle localBundle;
    if (!TextUtils.isEmpty(str1))
    {
      String str2 = this.mMsgItem.getMessageId();
      localBundle = new Bundle();
      localBundle.putString("message_id", str2);
      localBundle.putString("action", paramString);
      if (!"order".equals(str1))
        break label113;
      localBundle.putString("name", "预约成功");
    }
    while (true)
    {
      MediaPlayerHelper localMediaPlayerHelper = new MediaPlayerHelper(this.mContext, paramString, localBundle);
      localMediaPlayerHelper.setListener(new MediaAssetHelperCallback()
      {
        public void onError(String paramAnonymousString1, String paramAnonymousString2)
        {
          if ((!"reservetopic".equals(paramAnonymousString1)) && ("reserveturnplay".equals(paramAnonymousString1)));
        }

        public void onSuccess(String paramAnonymousString, Bundle paramAnonymousBundle)
        {
          if (("reservetopic".equals(paramAnonymousString)) || ("reserveturnplay".equals(paramAnonymousString)))
          {
            String str = "";
            if (("reservetopic".equals(paramAnonymousString)) || ("reserveturnplay".equals(paramAnonymousString)))
              str = "预约成功";
            if (MessageItemSubView.this.mFirstButton != null)
              MessageItemSubView.this.mFirstButton.setLabel(str);
            if (MessageItemSubView.this.mActionButton1 != null)
              MessageItemSubView.this.mActionButton1.setText(str);
          }
        }
      });
      localMediaPlayerHelper.startExecuteAction();
      return;
      label113: if ("cancel_order".equals(str1))
        localBundle.putString("name", "预约");
    }
  }

  private void hideLoadingDialog(Bundle paramBundle)
  {
    if (this.mNotifier != null)
    {
      Logger.i(TAG, "hideLoadingDialog");
      this.mNotifier.hideLoadingDialog(paramBundle);
    }
  }

  private void init()
  {
    setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    setOrientation(1);
  }

  private void initAbbrevLayout()
  {
    this.mTitleLayout = new RelativeLayout(this.mContext);
    this.mTitleLayout.setClickable(true);
    this.mTitleLayout.setFocusable(true);
    this.mTitleLayout.setOnClickListener(this);
    this.mTitleLayout.setOnKeyListener(this);
    this.mTitleLayout.setOnFocusChangeListener(this);
    RelativeLayout localRelativeLayout = this.mTitleLayout;
    int i = mId;
    mId = i + 1;
    localRelativeLayout.setId(i);
    this.mTitleLayout.setPadding(App.Operation(25.0F), App.Operation(10.0F), App.Operation(78.0F), App.Operation(6.0F));
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-2, -2);
    this.mUnreadImg = new ImageView(this.mContext);
    this.mUnreadImg.setImageResource(2130837674);
    ImageView localImageView = this.mUnreadImg;
    int j = mId;
    mId = j + 1;
    localImageView.setId(j);
    localLayoutParams1.setMargins(App.Operation(-2.0F), App.Operation(11.0F), 0, 0);
    this.mTitleLayout.addView(this.mUnreadImg, localLayoutParams1);
    this.mUnreadImg.getLayoutParams().width = App.OperationWidth(16);
    this.mUnreadImg.getLayoutParams().height = App.OperationHeight(16);
    if (!this.mMsgItem.getUnreadFlag())
      this.mUnreadImg.setVisibility(4);
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
    this.mTitle = new TextView(this.mContext);
    this.mTitle.setTextColor(1728053247);
    TextView localTextView1 = this.mTitle;
    int k = mId;
    mId = k + 1;
    localTextView1.setId(k);
    this.mTitle.setTextSize(0, App.OperationHeight(24));
    this.mTitle.getPaint().setFakeBoldText(true);
    this.mTitle.setSingleLine(true);
    this.mTitle.setGravity(16);
    this.mTitle.setPadding(App.Operation(8.0F), 0, 0, App.Operation(10.0F));
    localLayoutParams2.addRule(1, this.mUnreadImg.getId());
    this.mTitleLayout.addView(this.mTitle, localLayoutParams2);
    RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
    this.mDate = new TextView(this.mContext);
    this.mDate.setTextColor(-1711276033);
    this.mDate.setTextSize(0, App.OperationHeight(23));
    TextView localTextView2 = this.mDate;
    int m = mId;
    mId = m + 1;
    localTextView2.setId(m);
    this.mDate.setGravity(16);
    this.mDate.setPadding(App.Operation(8.0F), App.Operation(5.0F), 0, 0);
    localLayoutParams3.addRule(11, this.mTitle.getId());
    this.mTitleLayout.addView(this.mDate, localLayoutParams3);
    RelativeLayout.LayoutParams localLayoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
    this.mDescription = new TextView(this.mContext);
    this.mDescription.setTextColor(-1711276033);
    this.mDescription.setTextSize(0, App.OperationHeight(20));
    this.mDescription.setSingleLine(true);
    this.mDescription.setEllipsize(TextUtils.TruncateAt.END);
    TextView localTextView3 = this.mDescription;
    int n = mId;
    mId = n + 1;
    localTextView3.setId(n);
    this.mDescription.setGravity(16);
    localLayoutParams4.addRule(3, this.mTitle.getId());
    this.mTitleLayout.addView(this.mDescription, localLayoutParams4);
    this.mDescription.setPadding(App.Operation(20.0F), App.Operation(-4.0F), 0, App.Operation(5.0F));
    RelativeLayout.LayoutParams localLayoutParams5 = new RelativeLayout.LayoutParams(-1, -2);
    addView(this.mTitleLayout, localLayoutParams5);
  }

  private void initDetailedLayout()
  {
    this.mDetailedContentLayout = new RelativeLayout(this.mContext);
    this.mDetailedContentLayout.setBackgroundResource(2130837594);
    RelativeLayout localRelativeLayout1 = this.mDetailedContentLayout;
    int i = mId;
    mId = i + 1;
    localRelativeLayout1.setId(i);
    this.mDetailedContentLayout.setFocusable(false);
    this.mDetailedContentLayout.setFocusableInTouchMode(false);
    this.mDetailedContentLayout.setPadding(App.Operation(25.0F), App.Operation(4.0F), App.Operation(76.0F), App.Operation(4.0F));
    RelativeLayout localRelativeLayout2 = new RelativeLayout(this.mContext);
    int j = mId;
    mId = j + 1;
    localRelativeLayout2.setId(j);
    localRelativeLayout2.setFocusable(false);
    localRelativeLayout2.setFocusableInTouchMode(false);
    this.mDetailedUnreadImg = new ImageView(this.mContext);
    ImageView localImageView = this.mDetailedUnreadImg;
    int k = mId;
    mId = k + 1;
    localImageView.setId(k);
    this.mDetailedUnreadImg.setImageResource(2130837674);
    if (!this.mMsgItem.getUnreadFlag())
      this.mDetailedUnreadImg.setVisibility(4);
    this.mDetailedTitle = new TextView(this.mContext);
    this.mDetailedTitle.setTextColor(1728053247);
    this.mDetailedTitle.setTextSize(0, App.OperationHeight(24));
    this.mDetailedTitle.getPaint().setFakeBoldText(true);
    this.mDetailedTitle.setSingleLine(true);
    this.mDetailedTitle.setGravity(16);
    this.mDetailedTitle.setPadding(App.Operation(5.0F), App.Operation(11.0F), 0, App.Operation(8.0F));
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams1.setMargins(App.Operation(-2.0F), App.Operation(11.0F), 0, 0);
    localRelativeLayout2.addView(this.mDetailedUnreadImg, localLayoutParams1);
    this.mDetailedUnreadImg.getLayoutParams().width = App.OperationWidth(16);
    this.mDetailedUnreadImg.getLayoutParams().height = App.OperationHeight(16);
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams2.addRule(1, this.mDetailedUnreadImg.getId());
    localRelativeLayout2.addView(this.mDetailedTitle, localLayoutParams2);
    this.mDetailedDate = new TextView(this.mContext);
    this.mDetailedDate.setTextColor(-1711276033);
    this.mDetailedDate.setTextSize(0, App.OperationHeight(23));
    this.mDetailedDate.setGravity(16);
    this.mDetailedDate.setPadding(App.Operation(2.0F), App.Operation(15.0F), 0, 0);
    RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams3.addRule(11);
    localRelativeLayout2.addView(this.mDetailedDate, localLayoutParams3);
    RelativeLayout.LayoutParams localLayoutParams4 = new RelativeLayout.LayoutParams(-1, -2);
    this.mDetailedContentLayout.addView(localRelativeLayout2, localLayoutParams4);
    this.mContentLayout = new LinearLayout(this.mContext);
    LinearLayout localLinearLayout = this.mContentLayout;
    int m = mId;
    mId = m + 1;
    localLinearLayout.setId(m);
    this.mContentLayout.setFocusable(false);
    this.mContentLayout.setFocusableInTouchMode(false);
    this.mSubScrollView = new ScrollView(this.mContext);
    this.mSubScrollView.setFocusable(false);
    this.mSubScrollView.setFocusableInTouchMode(false);
    LinearLayout.LayoutParams localLayoutParams5 = new LinearLayout.LayoutParams(-1, -2);
    this.mContentLayout.addView(this.mSubScrollView, localLayoutParams5);
    this.mDetailedDescription = new TextView(this.mContext);
    TextView localTextView = this.mDetailedDescription;
    int n = mId;
    mId = n + 1;
    localTextView.setId(n);
    this.mDetailedDescription.setTextSize(0, App.OperationHeight(22));
    this.mDetailedDescription.setTextColor(-1711276033);
    this.mDetailedDescription.setLineSpacing(4.0F, 1.2F);
    this.mDetailedDescription.setSingleLine(false);
    LinearLayout.LayoutParams localLayoutParams6 = new LinearLayout.LayoutParams(-1, -1);
    this.mSubScrollView.addView(this.mDetailedDescription, localLayoutParams6);
    this.mDetailedDescription.setPadding(App.Operation(20.0F), App.Operation(-4.0F), 0, App.Operation(5.0F));
    RelativeLayout.LayoutParams localLayoutParams7 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams7.addRule(3, localRelativeLayout2.getId());
    this.mDetailedContentLayout.addView(this.mContentLayout, localLayoutParams7);
    this.mBtnLinearLayout = new LinearLayout(this.mContext);
    this.mBtnLinearLayout.setOrientation(0);
    this.mBtnLinearLayout.setFocusable(false);
    this.mBtnLinearLayout.setFocusableInTouchMode(false);
    this.mMsgItem.getActionType();
    this.mMsgItem.getMessageButtonBodies();
    int i1 = 50;
    int i2 = 25;
    if (DeviceInfo.isXiaoMi())
    {
      Logger.i(TAG, "isXiaomi adjust font and button height!");
      i1 = 52;
      i2 = 22;
    }
    this.mActionButton0 = new Button(this.mContext);
    Button localButton1 = this.mActionButton0;
    int i3 = mId;
    mId = i3 + 1;
    localButton1.setId(i3);
    this.mActionButton0.setText("收起");
    this.mActionButton0.setTextColor(-1);
    this.mActionButton0.setTextSize(0, App.OperationHeight(i2));
    this.mActionButton0.setOnKeyListener(this);
    this.mActionButton0.setOnClickListener(this);
    this.mActionButton0.setOnFocusChangeListener(this);
    this.mActionButton0.setBackgroundResource(2130837590);
    LinearLayout.LayoutParams localLayoutParams8 = new LinearLayout.LayoutParams(-2, -2);
    localLayoutParams8.setMargins(0, 0, App.Operation(18.0F), App.Operation(23.0F));
    this.mBtnLinearLayout.addView(this.mActionButton0, localLayoutParams8);
    UITools.setViewSize(this.mActionButton0, 170, i1);
    if ((this.mFirstButton != null) && (!TextUtils.isEmpty(this.mFirstButton.getArgs())))
    {
      this.mActionButton1 = new Button(this.mContext);
      Button localButton3 = this.mActionButton1;
      int i5 = mId;
      mId = i5 + 1;
      localButton3.setId(i5);
      this.mActionButton1.setText(this.mFirstButton.getLabel());
      this.mActionButton1.setTextColor(-1);
      this.mActionButton1.setTextSize(0, App.OperationHeight(i2));
      this.mActionButton1.setOnClickListener(this);
      this.mActionButton1.setOnKeyListener(this);
      this.mActionButton1.setOnFocusChangeListener(this);
      this.mActionButton1.setBackgroundResource(2130837590);
      LinearLayout.LayoutParams localLayoutParams12 = new LinearLayout.LayoutParams(-2, -2);
      localLayoutParams12.setMargins(0, 0, App.Operation(18.0F), App.Operation(23.0F));
      this.mBtnLinearLayout.addView(this.mActionButton1, localLayoutParams12);
      UITools.setViewSize(this.mActionButton1, 170, i1);
    }
    this.mActionButton2 = new Button(this.mContext);
    Button localButton2 = this.mActionButton2;
    int i4 = mId;
    mId = i4 + 1;
    localButton2.setId(i4);
    this.mActionButton2.setText("删除");
    this.mActionButton2.setTextColor(-1);
    this.mActionButton2.setTextSize(0, App.OperationHeight(i2));
    this.mActionButton2.setOnKeyListener(this);
    this.mActionButton2.setOnClickListener(this);
    this.mActionButton2.setOnFocusChangeListener(this);
    this.mActionButton2.setNextFocusRightId(this.mActionButton2.getId());
    this.mActionButton2.setBackgroundResource(2130837590);
    LinearLayout.LayoutParams localLayoutParams9 = new LinearLayout.LayoutParams(-2, -2);
    this.mBtnLinearLayout.addView(this.mActionButton2, localLayoutParams9);
    UITools.setViewSize(this.mActionButton2, 170, i1);
    this.mBtnLinearLayout.setPadding(0, App.Operation(58.0F), 0, App.Operation(23.0F));
    RelativeLayout.LayoutParams localLayoutParams10 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams10.addRule(3, this.mContentLayout.getId());
    localLayoutParams10.addRule(11);
    this.mDetailedContentLayout.addView(this.mBtnLinearLayout, localLayoutParams10);
    LinearLayout.LayoutParams localLayoutParams11 = new LinearLayout.LayoutParams(-1, -2);
    addView(this.mDetailedContentLayout, localLayoutParams11);
    this.mDetailedContentLayout.setVisibility(8);
  }

  private void openWebDialog(String paramString)
  {
    if (this.mNotifier != null)
      this.mNotifier.loadWebDialog(paramString);
  }

  private void restoreAbbrevView(final int paramInt)
  {
    if (this.mDetailedContentLayout.getVisibility() == 0)
    {
      this.mUpAnimation.setAnimationListener(new Animation.AnimationListener()
      {
        public void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          MessageItemSubView.this.mDetailedContentLayout.setVisibility(8);
          if (MessageItemSubView.this.mTitleLayout.getVisibility() == 8)
          {
            Logger.i("onAnimationEnd", "缩略显示标题!");
            if ((20 == paramInt) || (19 == paramInt) || (21 == paramInt) || (23 == paramInt))
            {
              MessageItemSubView.this.mTitleLayout.setVisibility(0);
              if (MessageItemSubView.this.mNotifier != null)
              {
                if (20 != paramInt)
                  break label118;
                MessageItemSubView.this.mNotifier.handleDownEventFocus(MessageItemSubView.this.mSelf);
              }
            }
          }
          label118: 
          do
          {
            return;
            if (21 == paramInt)
            {
              MessageItemSubView.this.mNotifier.handleLeftEventFocus();
              return;
            }
          }
          while (23 != paramInt);
          MessageItemSubView.this.mSelf.requestFocus();
        }

        public void onAnimationRepeat(Animation paramAnonymousAnimation)
        {
        }

        public void onAnimationStart(Animation paramAnonymousAnimation)
        {
        }
      });
      this.mDetailedContentLayout.startAnimation(this.mAlphaAnimation);
      this.mDetailedContentLayout.startAnimation(this.mUpAnimation);
    }
  }

  private void showLoadingDialog(Bundle paramBundle)
  {
    if (this.mNotifier != null)
    {
      Logger.i(TAG, "showLoadingDialog");
      this.mNotifier.displayLoadingDialog(paramBundle);
    }
  }

  private void updateDBTable(MessageView.MessageSubItemActionType paramMessageSubItemActionType)
  {
    MQTTMessageDBUpdater.getInstance(new DBProvider(getContext()), null, this.mMsgItem.getMessageId(), getUpdateActionType(paramMessageSubItemActionType), null).runTask();
  }

  public boolean alreadyRead()
  {
    return (this.mUnreadImg != null) && (this.mUnreadImg.getVisibility() == 8);
  }

  public MessageItemData getMessageItem()
  {
    return this.mMsgItem;
  }

  public RelativeLayout getTitleLayout()
  {
    return this.mTitleLayout;
  }

  public int getTitleLayoutBackground()
  {
    return this.mTitleLayoutBackground;
  }

  public void initData()
  {
    this.mTitle.setText(this.mMsgItem.getTitle());
    String str = "";
    if (!TextUtils.isEmpty(this.mMsgItem.getDate()))
      str = this.mMsgItem.getDate().substring(0, 11).replace('-', '/');
    this.mDate.setText(str);
    this.mDetailedDate.setText(str);
    this.mDescription.setText(this.mMsgItem.getContent());
    this.mDetailedTitle.setText(this.mMsgItem.getTitle());
    this.mDetailedDescription.setText(this.mMsgItem.getContent());
    this.mDetailedDescription.setText(this.mMsgItem.getContent());
    this.mAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
    this.mAlphaAnimation.setDuration(50L);
    this.mUpAnimation = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 0.0F, 1, -1.0F);
    this.mUpAnimation.setDuration(200L);
    this.mDownAnimation = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, -1.0F, 1, 0.0F);
    this.mDownAnimation.setDuration(200L);
    this.mLeftToRightAnimation = new TranslateAnimation(1, 0.0F, 1, 1.0F, 1, 0.0F, 1, 0.0F);
    this.mLeftToRightAnimation.setDuration(200L);
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if ((this.mActionButton1 != null) && (this.mActionButton1.getId() == i) && (this.mFirstButton != null))
      handleButtonAction(this.mFirstButton.getActions());
    label144: label155: int j;
    do
    {
      do
      {
        do
        {
          return;
          if (this.mActionButton2.getId() != i)
            break;
        }
        while (this.mNotifier == null);
        if ((this.mUnreadImg.getVisibility() == 0) || (this.mDetailedUnreadImg.getVisibility() == 0))
        {
          MessageItemData.SecondaryMessageType localSecondaryMessageType2 = this.mMsgItem.getSecondaryMessageType();
          if (localSecondaryMessageType2 == null)
            break label155;
          if (!MessageItemData.SecondaryMessageType.RESERVE_TOPIC_MESSAGE.equals(localSecondaryMessageType2))
            break label144;
          this.mNotifier.updateReadMyMsgNumber(1);
        }
        while (true)
        {
          updateDBTable(MessageView.MessageSubItemActionType.DELETE_MESSAGE);
          this.mLeftToRightAnimation.setAnimationListener(new Animation.AnimationListener()
          {
            public void onAnimationEnd(Animation paramAnonymousAnimation)
            {
              MessageItemSubView.this.mDelMsgHandler.sendEmptyMessage(0);
            }

            public void onAnimationRepeat(Animation paramAnonymousAnimation)
            {
            }

            public void onAnimationStart(Animation paramAnonymousAnimation)
            {
            }
          });
          startAnimation(this.mLeftToRightAnimation);
          return;
          this.mNotifier.updateReadSysMsgNumber(1);
          continue;
          this.mNotifier.updateReadSysMsgNumber(1);
        }
      }
      while (this.mTitleLayout.getId() != i);
      int[] arrayOfInt = new int[2];
      this.mTitleLayout.getLocationOnScreen(arrayOfInt);
      j = arrayOfInt[1];
    }
    while (this.mDetailedContentLayout == null);
    if (this.mDetailedContentLayout.getVisibility() == 8)
    {
      this.mDetailedContentLayout.setVisibility(0);
      this.mDetailedContentLayout.startAnimation(this.mAlphaAnimation);
      this.mDetailedContentLayout.startAnimation(this.mDownAnimation);
      if (this.mActionButton0 == null)
        if (this.mActionButton1 == null)
        {
          this.mActionButton2.requestFocus();
          label265: if (this.mUnreadImg.getVisibility() == 0)
          {
            this.mUnreadImg.setVisibility(4);
            MessageItemData.SecondaryMessageType localSecondaryMessageType1 = this.mMsgItem.getSecondaryMessageType();
            if (localSecondaryMessageType1 == null)
              break label407;
            if (!MessageItemData.SecondaryMessageType.RESERVE_TOPIC_MESSAGE.equals(localSecondaryMessageType1))
              break label396;
            this.mNotifier.updateReadMyMsgNumber(1);
          }
        }
      while (true)
      {
        updateDBTable(MessageView.MessageSubItemActionType.SET_MESSAGE_READ);
        if (this.mDetailedUnreadImg.getVisibility() == 0)
          this.mDetailedUnreadImg.setVisibility(4);
        this.mTitleLayout.setVisibility(8);
        if (App.SCREEN_HEIGHT - j >= 250)
          break;
        this.mParentScrollView.scrollBy(0, 280);
        return;
        this.mActionButton1.requestFocus();
        break label265;
        this.mActionButton0.requestFocus();
        break label265;
        label396: this.mNotifier.updateReadSysMsgNumber(1);
        continue;
        label407: this.mNotifier.updateReadSysMsgNumber(1);
      }
    }
    this.mDetailedContentLayout.setVisibility(8);
  }

  public void onFocusChange(View paramView, boolean paramBoolean)
  {
    int i = paramView.getId();
    if (paramBoolean)
      if (this.mTitleLayout.getId() == i)
        this.mTitleLayout.setBackgroundResource(2130837593);
    while (this.mTitleLayout.getId() != i)
      return;
    this.mTitleLayout.setBackgroundResource(this.mTitleLayoutBackground);
  }

  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    int i = paramView.getId();
    Logger.i("onKeyEvent view id = ", "" + i);
    Logger.i(TAG, "mIsLastItem=" + this.mIsLastItem);
    switch (paramKeyEvent.getAction())
    {
    default:
    case 0:
    }
    do
    {
      do
      {
        do
        {
          do
          {
            return false;
            if ((this.mActionButton0 == null) || (this.mActionButton0.getId() != i))
              break;
            if ((21 == paramInt) || (19 == paramInt) || (20 == paramInt) || (23 == paramInt))
            {
              if ((20 == paramInt) && (this.mIsLastItem))
                return true;
              restoreAbbrevView(paramInt);
            }
            if ((22 == paramInt) && (this.mActionButton1 == null) && (this.mActionButton2 == null))
            {
              restoreAbbrevView(paramInt);
              return false;
            }
            if ((22 == paramInt) && (this.mActionButton1 != null))
            {
              this.mActionButton1.requestFocus();
              return true;
            }
          }
          while ((22 != paramInt) || (this.mActionButton2 == null));
          this.mActionButton2.requestFocus();
          return true;
          if ((this.mActionButton1 == null) || (this.mActionButton1.getId() != i))
            break;
          if ((19 == paramInt) || (20 == paramInt))
          {
            if ((20 == paramInt) && (this.mIsLastItem))
              return true;
            restoreAbbrevView(paramInt);
          }
        }
        while ((21 != paramInt) || (this.mActionButton0 == null));
        this.mActionButton0.requestFocus();
        return true;
        if (this.mActionButton2.getId() != i)
          break;
        if ((19 == paramInt) || (20 == paramInt))
        {
          if ((20 == paramInt) && (this.mIsLastItem))
            return true;
          restoreAbbrevView(paramInt);
        }
        if ((21 == paramInt) && (this.mActionButton1 == null) && (this.mActionButton0 == null))
        {
          restoreAbbrevView(paramInt);
          return false;
        }
        if ((21 == paramInt) && (this.mActionButton1 != null))
        {
          this.mActionButton1.requestFocus();
          return true;
        }
      }
      while ((21 != paramInt) || (this.mActionButton0 == null));
      this.mActionButton0.requestFocus();
      return true;
    }
    while ((this.mTitleLayout.getId() != i) || (!this.mIsLastItem) || (20 != paramInt));
    return true;
  }

  public void setLastItemFlag(boolean paramBoolean)
  {
    Logger.i(TAG, "设置mIsLastItem=" + paramBoolean);
    this.mIsLastItem = paramBoolean;
    this.mTitleLayout.setTag(Boolean.valueOf(this.mIsLastItem));
  }

  public void setMessageItemUpdateNotifier(AbstractMQTTUIUpdateNotifier paramAbstractMQTTUIUpdateNotifier)
  {
    this.mNotifier = paramAbstractMQTTUIUpdateNotifier;
  }

  public boolean setMsgRead()
  {
    int i = this.mUnreadImg.getVisibility();
    boolean bool = false;
    if (i == 0)
    {
      this.mUnreadImg.setVisibility(4);
      bool = true;
    }
    if (this.mDetailedUnreadImg.getVisibility() == 0)
      this.mDetailedUnreadImg.setVisibility(4);
    return bool;
  }

  public boolean setMsgUnRead()
  {
    int i = this.mUnreadImg.getVisibility();
    boolean bool = false;
    if (i == 4)
    {
      this.mUnreadImg.setVisibility(0);
      bool = true;
    }
    if (this.mDetailedUnreadImg.getVisibility() == 4)
      this.mDetailedUnreadImg.setVisibility(0);
    return bool;
  }

  public void setTitleLayoutBackground(int paramInt)
  {
    this.mTitleLayoutBackground = paramInt;
    if (hasFocus())
    {
      this.mTitleLayout.setBackgroundResource(2130837593);
      return;
    }
    this.mTitleLayout.setBackgroundResource(this.mTitleLayoutBackground);
  }

  private class MediaAssetHelperListener
    implements MediaAssetHelperCallback
  {
    private MediaAssetHelperListener()
    {
    }

    public void onError(String paramString1, String paramString2)
    {
      Logger.i(MessageItemSubView.TAG, "MediaAssetHelperListener onError action=" + paramString1 + " errMsg=" + paramString2);
      if (!TextUtils.isEmpty(paramString2))
      {
        Bundle localBundle = new Bundle();
        localBundle.putString("message", paramString2);
        MessageItemSubView.this.hideLoadingDialog(localBundle);
      }
    }

    public void onSuccess(String paramString, Bundle paramBundle)
    {
      Logger.i(MessageItemSubView.TAG, "MediaAssetHelperListener onSuccess action=" + paramString + " params=" + paramBundle);
      if (("reservetopic".equals(paramString)) || ("reserveturnplay".equals(paramString)))
      {
        if (MessageItemSubView.this.mFirstButton != null)
          MessageItemSubView.this.mFirstButton.setLabel("预约成功");
        if (MessageItemSubView.this.mActionButton1 != null)
        {
          localMessage = new Message();
          localMessage.what = 0;
          localBundle = new Bundle();
          localBundle.putString("buttons", "1");
          localBundle.putString("title", "预约成功");
          localMessage.obj = localBundle;
          MessageItemSubView.this.mUiHandler.sendMessage(localMessage);
        }
        paramBundle.putString("message", "预约成功");
        MessageItemSubView.this.hideLoadingDialog(paramBundle);
      }
      while ((!"detail".equals(paramString)) && (!"video".equals(paramString)))
      {
        Message localMessage;
        Bundle localBundle;
        return;
      }
      MessageItemSubView.this.hideLoadingDialog(paramBundle);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.widget.MessageItemSubView
 * JD-Core Version:    0.6.2
 */