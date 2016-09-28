package com.starcor.hunan.msgsys.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.res.Resources;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.msgsys.data.MessageItemData;
import com.starcor.hunan.msgsys.dbupdater.MQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import com.starcor.hunan.msgsys.utils.SharedPreferencesUtil;
import com.starcor.hunan.widget.CommDialog;
import com.starcor.ui.UITools;
import java.util.Iterator;
import java.util.List;

public abstract class MessageView extends LinearLayout
  implements View.OnClickListener, View.OnKeyListener
{
  private static final String SET_ALL_READ = "set_all_read";
  private static final String TAG = MessageView.class.getSimpleName();
  protected Context mContext = null;
  protected ImageView mDeleteMsgImg = null;
  private MessageItemView mItemView = null;
  protected List<MessageItemData> mMessageItems = null;
  private int mMessageNumber = 0;
  private MessageViewType mMessageViewType = null;
  protected ScrollView mMsgListScrollView = null;
  protected LinearLayout mMsgListView = null;
  private MyMessageNotifier mMyNotifier = new MyMessageNotifier(null);
  protected AbstractMQTTUIUpdateNotifier mNotifier = null;
  protected Button mSetAllReadBtn = null;
  protected TextView mTotalMsgTxtView = null;

  public MessageView(Context paramContext, List<MessageItemData> paramList, MessageViewType paramMessageViewType)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mMessageItems = paramList;
    this.mMessageViewType = paramMessageViewType;
    init();
    loadMessageData();
  }

  private boolean getAllReadFlag()
  {
    return SharedPreferencesUtil.getBoolean(getContext(), "mgtv_client_sp", "set_all_read_" + this.mMessageViewType.name());
  }

  private IMQTTMessageDBUpdater.TopicTableUpdateActionType getUpdateActionType(MessageItemActionType paramMessageItemActionType)
  {
    switch (3.$SwitchMap$com$starcor$hunan$msgsys$widget$MessageView$MessageItemActionType[paramMessageItemActionType.ordinal()])
    {
    default:
      return null;
    case 1:
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_MY_MESSAGE;
    case 2:
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_MY_MESSAGE_READ;
    case 3:
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_MY_MESSAGE_UNREAD;
    case 4:
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_SYSTEM_MESSAGE;
    case 5:
      return IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_SYSTEM_MESSAGE_READ;
    case 6:
    }
    return IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_SYSTEM_MESSAGE_UNREAD;
  }

  private void init()
  {
    int i = getResources().getColor(2131099653);
    View.inflate(this.mContext, 2130903071, this);
    this.mTotalMsgTxtView = ((TextView)findViewById(2131165309));
    this.mTotalMsgTxtView.setTextColor(i);
    this.mTotalMsgTxtView.setTextSize(0, App.OperationHeight(26));
    UITools.setViewMargin(this.mTotalMsgTxtView, App.Operation(8.0F), App.Operation(6.0F), 0, 0);
    this.mSetAllReadBtn = ((Button)findViewById(2131165311));
    this.mSetAllReadBtn.setTextColor(i);
    this.mSetAllReadBtn.setTextSize(0, App.OperationHeight(24));
    this.mSetAllReadBtn.setOnClickListener(this);
    this.mSetAllReadBtn.setOnKeyListener(this);
    Button localButton = this.mSetAllReadBtn;
    if (getAllReadFlag());
    for (String str = "全部标为已读"; ; str = "全部标为未读")
    {
      localButton.setText(str);
      LinearLayout.LayoutParams localLayoutParams1 = (LinearLayout.LayoutParams)this.mSetAllReadBtn.getLayoutParams();
      localLayoutParams1.rightMargin = App.Operation(17.0F);
      localLayoutParams1.width = App.Operation(180.0F);
      localLayoutParams1.height = App.Operation(47.0F);
      this.mDeleteMsgImg = ((ImageView)findViewById(2131165312));
      this.mDeleteMsgImg.setFocusable(true);
      this.mDeleteMsgImg.setClickable(true);
      this.mDeleteMsgImg.setOnClickListener(this);
      this.mDeleteMsgImg.setOnKeyListener(this);
      this.mDeleteMsgImg.setImageResource(2130837575);
      LinearLayout.LayoutParams localLayoutParams2 = (LinearLayout.LayoutParams)this.mDeleteMsgImg.getLayoutParams();
      localLayoutParams2.width = App.Operation(47.0F);
      localLayoutParams2.height = App.Operation(47.0F);
      this.mMsgListView = new LinearLayout(this.mContext);
      this.mMsgListScrollView = new ScrollView(this.mContext);
      LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(-1, -1);
      this.mMsgListScrollView.addView(this.mMsgListView, localLayoutParams3);
      ((RelativeLayout)findViewById(2131165308)).setPadding(App.OperationHeight(40), App.OperationHeight(20), App.OperationHeight(80), 0);
      LinearLayout.LayoutParams localLayoutParams4 = new LinearLayout.LayoutParams(-1, -1);
      LinearLayout localLinearLayout = (LinearLayout)findViewById(2131165313);
      localLinearLayout.setPadding(0, App.OperationHeight(20), 0, 0);
      localLinearLayout.addView(this.mMsgListScrollView, localLayoutParams4);
      return;
    }
  }

  private void loadMessageData()
  {
    this.mItemView = new MessageItemView(this.mContext, this.mMsgListScrollView, this.mMessageItems);
    this.mItemView.setMessageItemUpdateNotifier(this.mMessageViewType, this.mMyNotifier);
    this.mMsgListView.addView(this.mItemView);
    this.mMessageNumber = this.mMessageItems.size();
    Logger.i(TAG, "总共导入信息数mMessageNumber=" + this.mMessageNumber);
    setTotalMessageNumberText(this.mMessageNumber);
  }

  private void setTotalMessageNumberText(int paramInt)
  {
    TextView localTextView = this.mTotalMsgTxtView;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    localTextView.setText(String.format("共%1$s条消息", arrayOfObject));
  }

  private void showDialog(String paramString)
  {
    CommDialog localCommDialog = new CommDialog(this.mContext, 2131296258);
    localCommDialog.setMessage(paramString);
    localCommDialog.setType(1);
    localCommDialog.setTitle("提示  按“返回”键取消");
    localCommDialog.setNegativeButton(new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        if (MessageView.MessageViewType.SYSTEM_MESSAGE_PAGE.equals(MessageView.this.mMessageViewType))
          MessageView.this.updateMessageDB(MessageView.MessageItemActionType.DELETE_ALL_SYSTEM_MESSAGE);
        while (true)
        {
          MessageView.this.mItemView.removeAllSubItems();
          MessageView.this.setTotalMessageNumberText(0);
          MessageView.this.mMessageItems.clear();
          if (MessageView.this.mNotifier != null)
            MessageView.this.mNotifier.updateMessageNumberText(MessageView.this.mMessageViewType, MessageView.this.mMessageNumber);
          MessageView.access$502(MessageView.this, 0);
          Logger.i(MessageView.TAG, "删除所有信息后，现存信息数mMessageNumber=" + MessageView.this.mMessageNumber);
          paramAnonymousDialogInterface.dismiss();
          return;
          if (MessageView.MessageViewType.MY_MESSAGE_PAGE.equals(MessageView.this.mMessageViewType))
            MessageView.this.updateMessageDB(MessageView.MessageItemActionType.DELETE_ALL_MY_MESSAGE);
        }
      }
    });
    localCommDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousKeyEvent.getAction() == 0) && (paramAnonymousInt == 4))
        {
          paramAnonymousDialogInterface.dismiss();
          return true;
        }
        return false;
      }
    });
    localCommDialog.show();
  }

  private void updateMessageDB(MessageItemActionType paramMessageItemActionType)
  {
    DBProvider localDBProvider = new DBProvider(getContext());
    IMQTTMessageDBUpdater.TopicTableUpdateActionType localTopicTableUpdateActionType = getUpdateActionType(paramMessageItemActionType);
    if (localTopicTableUpdateActionType != null)
      MQTTMessageDBUpdater.getInstance(localDBProvider, null, null, localTopicTableUpdateActionType, null).runTask();
  }

  public void deleteAllMessageViews(boolean paramBoolean)
  {
    if (this.mMessageNumber == 0)
      return;
    if (paramBoolean)
    {
      if (!MessageViewType.SYSTEM_MESSAGE_PAGE.equals(this.mMessageViewType))
        break label81;
      updateMessageDB(MessageItemActionType.DELETE_ALL_SYSTEM_MESSAGE);
    }
    while (true)
    {
      this.mItemView.removeAllSubItems();
      setTotalMessageNumberText(0);
      this.mMessageItems.clear();
      if (this.mNotifier != null)
        this.mNotifier.updateMessageNumberText(this.mMessageViewType, this.mMessageNumber);
      this.mMessageNumber = 0;
      return;
      label81: if (MessageViewType.MY_MESSAGE_PAGE.equals(this.mMessageViewType))
        updateMessageDB(MessageItemActionType.DELETE_ALL_MY_MESSAGE);
    }
  }

  public void deleteMessageItemView(String paramString)
  {
    Logger.i(TAG, "deleteMessageItemView msgId" + paramString);
    Iterator localIterator = this.mMessageItems.iterator();
    if (localIterator != null)
      while (localIterator.hasNext())
      {
        MessageItemData localMessageItemData = (MessageItemData)localIterator.next();
        if (localMessageItemData.getMessageId().equals(paramString))
        {
          Logger.i(TAG, "查询到消息" + paramString);
          this.mMessageItems.remove(localMessageItemData);
          this.mItemView.deleteMessageSubView(paramString);
        }
      }
  }

  public int getSetAllReadButtonId()
  {
    if (this.mSetAllReadBtn != null)
      return this.mSetAllReadBtn.getId();
    return -1;
  }

  public RelativeLayout getTitleLayoutOfFirstView()
  {
    MessageItemView localMessageItemView = this.mItemView;
    RelativeLayout localRelativeLayout = null;
    if (localMessageItemView != null)
    {
      int i = this.mItemView.getChildCount();
      localRelativeLayout = null;
      if (i > 0)
      {
        boolean bool = this.mItemView.getChildAt(0) instanceof MessageItemSubView;
        localRelativeLayout = null;
        if (bool)
          localRelativeLayout = ((MessageItemSubView)this.mItemView.getChildAt(0)).getTitleLayout();
      }
    }
    return localRelativeLayout;
  }

  public void moveToTop()
  {
    if (this.mMsgListScrollView != null)
      this.mMsgListScrollView.scrollTo(0, 0);
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if (this.mSetAllReadBtn.getId() == i)
      if (this.mMessageNumber == 0)
        Logger.i(TAG, "mSetAllReadBtn onClick() 当前消息总数为0");
    label32: label302: 
    while (this.mDeleteMsgImg.getId() != i)
    {
      int k;
      int m;
      do
      {
        int j;
        do
        {
          boolean bool;
          do
          {
            do
            {
              do
              {
                break label32;
                break label32;
                break label32;
                break label32;
                do
                  return;
                while (this.mItemView == null);
                j = this.mItemView.getChildCount();
                k = 0;
                m = 0;
                bool = SharedPreferencesUtil.getBoolean(getContext(), "mgtv_client_sp", "set_all_read_" + this.mMessageViewType.name());
                Logger.i(TAG, "currnet mesage view = " + this.mMessageViewType.name() + " hasRead=" + bool);
                if (!MessageViewType.SYSTEM_MESSAGE_PAGE.equals(this.mMessageViewType))
                  break label302;
                if (!bool)
                  break;
                updateMessageDB(MessageItemActionType.SET_ALL_SYSTEM_MESSAGE_READ);
                for (int i3 = 0; i3 < j; i3++)
                {
                  if (((MessageItemSubView)this.mItemView.getChildAt(i3)).setMsgRead())
                    m++;
                  k = 1;
                }
              }
              while (k == 0);
              this.mSetAllReadBtn.setText("全部标为未读");
              saveAllReadFlag(false);
              this.mNotifier.updateReadSysMsgNumber(m);
              return;
              updateMessageDB(MessageItemActionType.SET_ALL_SYSTEM_MESSAGE_UNREAD);
              for (int i2 = 0; i2 < j; i2++)
              {
                if (((MessageItemSubView)this.mItemView.getChildAt(i2)).setMsgUnRead())
                  m++;
                k = 1;
              }
            }
            while (k == 0);
            this.mSetAllReadBtn.setText("全部标为已读");
            saveAllReadFlag(true);
            this.mNotifier.updateUnreadSysMsgNumber(m);
            return;
          }
          while (!MessageViewType.MY_MESSAGE_PAGE.equals(this.mMessageViewType));
          if (!bool)
            break;
          updateMessageDB(MessageItemActionType.SET_ALL_MY_MESSAGE_READ);
          for (int i1 = 0; i1 < j; i1++)
          {
            if (((MessageItemSubView)this.mItemView.getChildAt(i1)).setMsgRead())
              m++;
            k = 1;
          }
        }
        while (k == 0);
        this.mSetAllReadBtn.setText("全部标为未读");
        saveAllReadFlag(false);
        this.mNotifier.updateReadMyMsgNumber(m);
        return;
        updateMessageDB(MessageItemActionType.SET_ALL_MY_MESSAGE_UNREAD);
        for (int n = 0; n < j; n++)
        {
          if (((MessageItemSubView)this.mItemView.getChildAt(n)).setMsgUnRead())
            m++;
          k = 1;
        }
      }
      while (k == 0);
      this.mSetAllReadBtn.setText("全部标为已读");
      saveAllReadFlag(true);
      this.mNotifier.updateUnreadMyMsgNumber(m);
      return;
    }
    if (this.mMessageNumber == 0)
    {
      Logger.i(TAG, "mDeleteMsgImg onClick() 当前消息总数为0");
      return;
    }
    showDialog("确认要删除所有消息吗？");
  }

  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    int i = paramView.getId();
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
            if (20 != paramInt)
              break;
          }
          while ((this.mSetAllReadBtn.getId() != i) && (this.mDeleteMsgImg.getId() != i));
          Logger.i(TAG, "现存信息数mMessageNumber=" + this.mMessageNumber);
        }
        while (this.mMessageNumber > 0);
        return true;
        if (21 != paramInt)
          break;
      }
      while (this.mDeleteMsgImg.getId() != i);
      this.mSetAllReadBtn.requestFocus();
      return true;
    }
    while ((22 != paramInt) || (this.mSetAllReadBtn.getId() != i));
    this.mDeleteMsgImg.requestFocus();
    return true;
  }

  public void saveAllReadFlag(boolean paramBoolean)
  {
    SharedPreferencesUtil.setBoolean(getContext(), "mgtv_client_sp", "set_all_read_" + this.mMessageViewType.name(), paramBoolean);
  }

  public boolean setFocusOnLastestMsg()
  {
    MessageItemView localMessageItemView = this.mItemView;
    boolean bool = false;
    if (localMessageItemView != null)
    {
      int i = this.mItemView.getChildCount();
      bool = false;
      if (i > 0)
        bool = this.mItemView.getChildAt(0).requestFocus();
    }
    return bool;
  }

  public abstract void setMessageNumberNotifier(AbstractMQTTUIUpdateNotifier paramAbstractMQTTUIUpdateNotifier);

  public void updateMessageItemView(MessageItemData paramMessageItemData)
  {
    Logger.i(TAG, "更新MessageItemView item = " + paramMessageItemData.toString());
    this.mMessageItems.add(paramMessageItemData);
    this.mMessageNumber = (1 + this.mMessageNumber);
    setTotalMessageNumberText(this.mMessageNumber);
    Logger.i(TAG, "mqtt服务收到一条消息后，信息数mMessageNumber" + this.mMessageNumber);
    this.mItemView.updateMessageItemSubView(paramMessageItemData);
  }

  public void updateSetAllReadBtnText(boolean paramBoolean)
  {
    Button localButton = this.mSetAllReadBtn;
    if (paramBoolean);
    for (String str = "全部标为已读"; ; str = "全部标为未读")
    {
      localButton.setText(str);
      return;
    }
  }

  public static enum MessageItemActionType
  {
    static
    {
      DELETE_ALL_MY_MESSAGE = new MessageItemActionType("DELETE_ALL_MY_MESSAGE", 1);
      SET_ALL_SYSTEM_MESSAGE_READ = new MessageItemActionType("SET_ALL_SYSTEM_MESSAGE_READ", 2);
      SET_ALL_MY_MESSAGE_READ = new MessageItemActionType("SET_ALL_MY_MESSAGE_READ", 3);
      SET_ALL_MY_MESSAGE_UNREAD = new MessageItemActionType("SET_ALL_MY_MESSAGE_UNREAD", 4);
      SET_ALL_SYSTEM_MESSAGE_UNREAD = new MessageItemActionType("SET_ALL_SYSTEM_MESSAGE_UNREAD", 5);
      MessageItemActionType[] arrayOfMessageItemActionType = new MessageItemActionType[6];
      arrayOfMessageItemActionType[0] = DELETE_ALL_SYSTEM_MESSAGE;
      arrayOfMessageItemActionType[1] = DELETE_ALL_MY_MESSAGE;
      arrayOfMessageItemActionType[2] = SET_ALL_SYSTEM_MESSAGE_READ;
      arrayOfMessageItemActionType[3] = SET_ALL_MY_MESSAGE_READ;
      arrayOfMessageItemActionType[4] = SET_ALL_MY_MESSAGE_UNREAD;
      arrayOfMessageItemActionType[5] = SET_ALL_SYSTEM_MESSAGE_UNREAD;
    }
  }

  public static enum MessageSubItemActionType
  {
    static
    {
      MessageSubItemActionType[] arrayOfMessageSubItemActionType = new MessageSubItemActionType[3];
      arrayOfMessageSubItemActionType[0] = DELETE_MESSAGE;
      arrayOfMessageSubItemActionType[1] = SET_MESSAGE_READ;
      arrayOfMessageSubItemActionType[2] = SET_MESSAGE_UNREAD;
    }
  }

  public static enum MessageViewType
  {
    static
    {
      MessageViewType[] arrayOfMessageViewType = new MessageViewType[2];
      arrayOfMessageViewType[0] = MY_MESSAGE_PAGE;
      arrayOfMessageViewType[1] = SYSTEM_MESSAGE_PAGE;
    }
  }

  private class MyMessageNotifier extends AbstractMQTTUIUpdateNotifier
  {
    private MyMessageNotifier()
    {
    }

    public void displayLoadingDialog(Object paramObject)
    {
      if (MessageView.this.mNotifier != null)
        MessageView.this.mNotifier.displayLoadingDialog(paramObject);
    }

    public void handleLeftEventFocus()
    {
      super.handleLeftEventFocus();
      if (MessageView.this.mNotifier != null)
        MessageView.this.mNotifier.handleLeftEventFocus();
    }

    public void hideLoadingDialog(Object paramObject)
    {
      if (MessageView.this.mNotifier != null)
        MessageView.this.mNotifier.hideLoadingDialog(paramObject);
    }

    public void loadWebDialog(String paramString)
    {
      super.loadWebDialog(paramString);
      if (MessageView.this.mNotifier != null)
        MessageView.this.mNotifier.loadWebDialog(paramString);
    }

    public void removeMessage(MessageItemSubView paramMessageItemSubView)
    {
      super.removeMessage(paramMessageItemSubView);
      if (paramMessageItemSubView != null)
      {
        MessageView.this.mMessageItems.remove(paramMessageItemSubView.getMessageItem());
        if (MessageView.this.mMessageNumber > 0)
        {
          MessageView.access$510(MessageView.this);
          Logger.i(MessageView.TAG, "删除一条信息后，现存信息数mMessageNumber" + MessageView.this.mMessageNumber);
        }
      }
    }

    public void updateFocusOnSetAllBtn()
    {
      super.updateFocusOnSetAllBtn();
      if (MessageView.this.mSetAllReadBtn != null)
        MessageView.this.mSetAllReadBtn.requestFocus();
    }

    public void updateMessageNumberText(MessageView.MessageViewType paramMessageViewType, int paramInt)
    {
      super.updateMessageNumberText(MessageView.this.mMessageViewType, paramInt);
      if (MessageView.this.mMessageNumber > 0)
      {
        MessageView.access$520(MessageView.this, paramInt);
        MessageView.this.setTotalMessageNumberText(MessageView.this.mMessageNumber);
        if (MessageView.this.mNotifier != null)
          MessageView.this.mNotifier.updateMessageNumberText(MessageView.this.mMessageViewType, paramInt);
      }
    }

    public void updateReadMyMsgNumber(int paramInt)
    {
      super.updateReadMyMsgNumber(paramInt);
      MessageView.this.mNotifier.updateReadMyMsgNumber(paramInt);
    }

    public void updateReadSysMsgNumber(int paramInt)
    {
      super.updateReadSysMsgNumber(paramInt);
      MessageView.this.mNotifier.updateReadSysMsgNumber(paramInt);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.widget.MessageView
 * JD-Core Version:    0.6.2
 */