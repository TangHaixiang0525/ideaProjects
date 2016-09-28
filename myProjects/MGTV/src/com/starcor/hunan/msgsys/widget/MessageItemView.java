package com.starcor.hunan.msgsys.widget;

import android.content.Context;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.msgsys.data.MessageItemData;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import java.util.List;

public class MessageItemView extends LinearLayout
{
  private static final String TAG = MessageItemView.class.getSimpleName();
  private Context mContext = null;
  private List<MessageItemData> mMessageItems = null;
  private MessageView.MessageViewType mMessageViewType = null;
  private MessageItemDoubleLinkedList mMsgLinkedList;
  private MyMessageNotifier mMyNotifier = new MyMessageNotifier(null);
  private AbstractMQTTUIUpdateNotifier mNotifier = null;
  private ScrollView mParentScrollView = null;
  private boolean mSetBackgroundFlag = true;

  public MessageItemView(Context paramContext, ScrollView paramScrollView, List<MessageItemData> paramList)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mParentScrollView = paramScrollView;
    this.mMessageItems = paramList;
    init();
    initMessageList();
  }

  private void init()
  {
    setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    setOrientation(1);
  }

  private void initMessageList()
  {
    if ((this.mMessageItems == null) || (this.mMessageItems.isEmpty()))
    {
      Logger.i(TAG, "当前没有任何的消息数据记录！");
      return;
    }
    int i = this.mMessageItems.size();
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    Logger.i(TAG, "初始化消息视图数量为" + i);
    if (this.mMsgLinkedList == null)
      this.mMsgLinkedList = new MessageItemDoubleLinkedList();
    int j = 0;
    label95: MessageItemSubView localMessageItemSubView;
    if (j < i)
    {
      localMessageItemSubView = new MessageItemSubView(this.mContext, this.mParentScrollView, (MessageItemData)this.mMessageItems.get(j));
      this.mMsgLinkedList.addNodeToHead(localMessageItemSubView);
      if (!this.mSetBackgroundFlag)
        break label181;
      localMessageItemSubView.setTitleLayoutBackground(2130837592);
    }
    for (this.mSetBackgroundFlag = false; ; this.mSetBackgroundFlag = true)
    {
      localMessageItemSubView.setMessageItemUpdateNotifier(this.mMyNotifier);
      addView(localMessageItemSubView, 0, localLayoutParams);
      j++;
      break label95;
      break;
      label181: localMessageItemSubView.setTitleLayoutBackground(2130837591);
    }
  }

  private void removeSubItemView(MessageItemSubView paramMessageItemSubView)
  {
    if (paramMessageItemSubView == null)
      return;
    this.mMessageItems.remove(paramMessageItemSubView.getMessageItem());
    this.mMsgLinkedList.removeNode(paramMessageItemSubView);
    removeView(paramMessageItemSubView);
  }

  public void clearMessageItems()
  {
    if (this.mMessageItems != null)
      this.mMessageItems.clear();
  }

  public void deleteMessageSubView(String paramString)
  {
    Logger.i(TAG, "开始删除消息" + paramString);
    boolean bool1;
    MessageItemSubView localMessageItemSubView3;
    if (this.mMsgLinkedList != null)
    {
      MessageItemSubView localMessageItemSubView1 = this.mMsgLinkedList.getNode(paramString);
      if (localMessageItemSubView1 != null)
      {
        bool1 = localMessageItemSubView1.alreadyRead();
        Logger.i(TAG, "待删除的消息为已读？" + bool1);
        findFocus();
        boolean bool2 = localMessageItemSubView1.hasFocus();
        MessageItemSubView localMessageItemSubView2 = localMessageItemSubView1.mNextMsgItem;
        localMessageItemSubView3 = localMessageItemSubView1.mPrevMsgItem;
        removeSubItemView(localMessageItemSubView1);
        if (this.mNotifier != null)
        {
          if (!bool2)
            break label228;
          Logger.i(TAG, "当前待删除的子视图有焦点！");
          if (localMessageItemSubView2 == null)
            break label214;
          localMessageItemSubView2.requestFocus();
        }
      }
    }
    while (true)
    {
      if (this.mMessageItems != null)
      {
        Logger.i(TAG, "mMsgLinkedList.currentListIsEmpty()=" + this.mMsgLinkedList.currentListIsEmpty());
        if (this.mMsgLinkedList.currentListIsEmpty())
          this.mNotifier.updateFocusOnSetAllBtn();
      }
      this.mNotifier.updateMessageNumberText(this.mMessageViewType, 1);
      if (!bool1)
        this.mMyNotifier.updateReadSysMsgNumber(1);
      return;
      label214: if (localMessageItemSubView3 != null)
      {
        localMessageItemSubView3.requestFocus();
        continue;
        label228: Logger.i(TAG, "当前待删除的子视图无焦点！");
      }
    }
  }

  public void removeAllSubItems()
  {
    clearMessageItems();
    this.mMsgLinkedList.removeAllNodes();
    removeAllViews();
  }

  public void setMessageItemUpdateNotifier(MessageView.MessageViewType paramMessageViewType, AbstractMQTTUIUpdateNotifier paramAbstractMQTTUIUpdateNotifier)
  {
    this.mMessageViewType = paramMessageViewType;
    this.mNotifier = paramAbstractMQTTUIUpdateNotifier;
  }

  public void updateMessageItemSubView(MessageItemData paramMessageItemData)
  {
    MessageItemSubView localMessageItemSubView = new MessageItemSubView(this.mContext, this.mParentScrollView, paramMessageItemData);
    if (this.mMessageItems != null)
      this.mMessageItems.add(paramMessageItemData);
    if (this.mSetBackgroundFlag)
      localMessageItemSubView.setTitleLayoutBackground(2130837592);
    for (this.mSetBackgroundFlag = false; ; this.mSetBackgroundFlag = true)
    {
      localMessageItemSubView.setMessageItemUpdateNotifier(this.mMyNotifier);
      if (this.mMsgLinkedList == null)
        this.mMsgLinkedList = new MessageItemDoubleLinkedList();
      this.mMsgLinkedList.addNodeToHead(localMessageItemSubView);
      addView(localMessageItemSubView, 0, new LinearLayout.LayoutParams(-1, -2));
      return;
      localMessageItemSubView.setTitleLayoutBackground(2130837591);
    }
  }

  private class MessageItemDoubleLinkedList
  {
    private MessageItemSubView mRoot = null;
    private MessageItemSubView mTail = null;

    public MessageItemDoubleLinkedList()
    {
    }

    public void addNodeToHead(MessageItemSubView paramMessageItemSubView)
    {
      if (paramMessageItemSubView == null)
        return;
      MessageItemSubView localMessageItemSubView = this.mTail;
      if ((localMessageItemSubView == this.mRoot) && (localMessageItemSubView == null))
      {
        Logger.i(MessageItemView.TAG, "添加消息为唯一节点！");
        paramMessageItemSubView.mNextMsgItem = null;
        paramMessageItemSubView.mPrevMsgItem = null;
        this.mRoot = paramMessageItemSubView;
        this.mTail = this.mRoot;
        this.mTail.setLastItemFlag(true);
        return;
      }
      Logger.i(MessageItemView.TAG, "添加消息到首位！");
      this.mRoot.mPrevMsgItem = paramMessageItemSubView;
      paramMessageItemSubView.mNextMsgItem = this.mRoot;
      paramMessageItemSubView.mPrevMsgItem = null;
      this.mRoot = paramMessageItemSubView;
      this.mRoot.setLastItemFlag(false);
    }

    public void addNodeToTail(MessageItemSubView paramMessageItemSubView)
    {
      if (paramMessageItemSubView == null)
        return;
      MessageItemSubView localMessageItemSubView = this.mTail;
      if ((localMessageItemSubView == this.mRoot) && (localMessageItemSubView == null))
      {
        Logger.i(MessageItemView.TAG, "添加消息为唯一节点！");
        paramMessageItemSubView.mNextMsgItem = null;
        paramMessageItemSubView.mPrevMsgItem = null;
        this.mRoot = paramMessageItemSubView;
        this.mTail = this.mRoot;
        this.mTail.setLastItemFlag(true);
        return;
      }
      Logger.i(MessageItemView.TAG, "添加消息到表尾！");
      localMessageItemSubView.setLastItemFlag(false);
      localMessageItemSubView.mNextMsgItem = paramMessageItemSubView;
      paramMessageItemSubView.mPrevMsgItem = localMessageItemSubView;
      paramMessageItemSubView.mNextMsgItem = null;
      this.mTail = paramMessageItemSubView;
      this.mTail.setLastItemFlag(true);
    }

    public boolean currentListIsEmpty()
    {
      return (this.mRoot == this.mTail) && (this.mRoot == null);
    }

    public MessageItemSubView findFocusSubView()
    {
      if ((this.mRoot == this.mTail) && (this.mRoot == null))
      {
        Logger.i(MessageItemView.TAG, "链表为空！");
        return null;
      }
      for (MessageItemSubView localMessageItemSubView = this.mRoot; localMessageItemSubView != null; localMessageItemSubView = localMessageItemSubView.mNextMsgItem)
        if (localMessageItemSubView.hasFocus())
        {
          Logger.i(MessageItemView.TAG, "找到有焦点的子视图！data=" + localMessageItemSubView.getMessageItem().toString());
          return localMessageItemSubView;
        }
      return null;
    }

    public MessageItemSubView getNode(String paramString)
    {
      if ((this.mRoot == this.mTail) && (this.mRoot == null))
      {
        Logger.i(MessageItemView.TAG, "链表为空！");
        return null;
      }
      if (TextUtils.isEmpty(paramString))
      {
        Logger.i(MessageItemView.TAG, "msgId为空！");
        return null;
      }
      for (MessageItemSubView localMessageItemSubView = this.mRoot; localMessageItemSubView != null; localMessageItemSubView = localMessageItemSubView.mNextMsgItem)
        if (localMessageItemSubView.getMessageItem().getMessageId().equals(paramString))
        {
          Logger.i(MessageItemView.TAG, "找到msgId=" + paramString + "所对应的子视图！");
          return localMessageItemSubView;
        }
      return null;
    }

    public void removeAllNodes()
    {
      MessageItemSubView localMessageItemSubView = this.mRoot;
      if ((MessageItemView.this.mMessageItems != null) && (MessageItemView.this.mMessageItems.size() <= 0))
        MessageItemView.this.mMessageItems.clear();
      if ((localMessageItemSubView == this.mTail) && (localMessageItemSubView == null))
        return;
      while (true)
      {
        if (localMessageItemSubView != this.mTail)
        {
          Logger.i(MessageItemView.TAG, "删除全部消息 -- 删除其中一条消息！");
          localMessageItemSubView.mNextMsgItem.mPrevMsgItem = null;
          this.mRoot = localMessageItemSubView.mNextMsgItem;
        }
        for (localMessageItemSubView = localMessageItemSubView.mNextMsgItem; localMessageItemSubView == null; localMessageItemSubView = null)
        {
          Logger.i(MessageItemView.TAG, "删除全部消息 -- 删除完毕！");
          return;
          Logger.i(MessageItemView.TAG, "删除全部消息 -- 删除最后一条消息！");
          this.mRoot = null;
          this.mTail = this.mRoot;
        }
      }
    }

    public void removeNode(MessageItemSubView paramMessageItemSubView)
    {
      if ((this.mRoot == this.mTail) && (this.mRoot == null))
        return;
      if ((MessageItemView.this.mMessageItems != null) && (MessageItemView.this.mMessageItems.size() <= 0))
        MessageItemView.this.mMessageItems.remove(paramMessageItemSubView.getMessageItem());
      if ((this.mRoot == this.mTail) && (this.mRoot == paramMessageItemSubView))
      {
        Logger.i(MessageItemView.TAG, "删除唯一消息！");
        this.mRoot = null;
        this.mTail = this.mRoot;
        return;
      }
      if (this.mRoot == paramMessageItemSubView)
      {
        Logger.i(MessageItemView.TAG, "删除第一条消息！");
        int k;
        if (paramMessageItemSubView.getTitleLayoutBackground() == 2130837592)
        {
          k = 1;
          if (k == 0)
            break label172;
          MessageItemView.access$602(MessageItemView.this, true);
        }
        while (true)
        {
          paramMessageItemSubView.mNextMsgItem.mPrevMsgItem = null;
          this.mRoot = paramMessageItemSubView.mNextMsgItem;
          paramMessageItemSubView.mNextMsgItem = null;
          return;
          k = 0;
          break;
          label172: MessageItemView.access$602(MessageItemView.this, false);
        }
      }
      if (this.mTail == paramMessageItemSubView)
      {
        Logger.i(MessageItemView.TAG, "删除最后一条消息！");
        paramMessageItemSubView.mPrevMsgItem.setLastItemFlag(true);
        paramMessageItemSubView.mPrevMsgItem.mNextMsgItem = null;
        this.mTail = paramMessageItemSubView.mPrevMsgItem;
        paramMessageItemSubView.mPrevMsgItem = null;
        return;
      }
      Logger.i(MessageItemView.TAG, "删除其中的某条消息！");
      paramMessageItemSubView.mNextMsgItem.mPrevMsgItem = paramMessageItemSubView.mPrevMsgItem;
      paramMessageItemSubView.mPrevMsgItem.mNextMsgItem = paramMessageItemSubView.mNextMsgItem;
      MessageItemSubView localMessageItemSubView = paramMessageItemSubView.mPrevMsgItem;
      paramMessageItemSubView.mNextMsgItem = null;
      paramMessageItemSubView.mPrevMsgItem = null;
      if (paramMessageItemSubView.getTitleLayoutBackground() == 2130837591)
      {
        Logger.i(MessageItemView.TAG, "删除的消息的背景色为浅色！");
        j = 0;
        label295: if (localMessageItemSubView == null)
          break label377;
        if (this.mRoot == localMessageItemSubView)
        {
          if (j == 0)
            break label379;
          MessageItemView.access$602(MessageItemView.this, false);
        }
        label321: if (j == 0)
          break label391;
        Logger.i(MessageItemView.TAG, "设置为深色！");
        localMessageItemSubView.setTitleLayoutBackground(2130837592);
      }
      for (int j = 0; ; j = 1)
      {
        localMessageItemSubView = localMessageItemSubView.mPrevMsgItem;
        break label295;
        int i = paramMessageItemSubView.getTitleLayoutBackground();
        j = 0;
        if (i != 2130837592)
          break label295;
        Logger.i(MessageItemView.TAG, "删除的消息的背景色为深色！");
        j = 1;
        break label295;
        label377: break;
        label379: MessageItemView.access$602(MessageItemView.this, true);
        break label321;
        label391: Logger.i(MessageItemView.TAG, "设置为浅色！");
        localMessageItemSubView.setTitleLayoutBackground(2130837591);
      }
    }
  }

  private class MyMessageNotifier extends AbstractMQTTUIUpdateNotifier
  {
    private MyMessageNotifier()
    {
    }

    public void displayLoadingDialog(Object paramObject)
    {
      if (MessageItemView.this.mNotifier != null)
        MessageItemView.this.mNotifier.displayLoadingDialog(paramObject);
    }

    public void handleDownEventFocus(MessageItemSubView paramMessageItemSubView)
    {
      if (paramMessageItemSubView == null);
      MessageItemSubView localMessageItemSubView;
      do
      {
        return;
        super.handleDownEventFocus(paramMessageItemSubView);
        localMessageItemSubView = paramMessageItemSubView.mNextMsgItem;
      }
      while (localMessageItemSubView == null);
      localMessageItemSubView.requestFocus();
    }

    public void handleLeftEventFocus()
    {
      super.handleLeftEventFocus();
      if (MessageItemView.this.mNotifier != null)
        MessageItemView.this.mNotifier.handleLeftEventFocus();
    }

    public void hideLoadingDialog(Object paramObject)
    {
      if (MessageItemView.this.mNotifier != null)
        MessageItemView.this.mNotifier.hideLoadingDialog(paramObject);
    }

    public void loadWebDialog(String paramString)
    {
      super.loadWebDialog(paramString);
      if (MessageItemView.this.mNotifier != null)
        MessageItemView.this.mNotifier.loadWebDialog(paramString);
    }

    public void removeMessage(MessageItemSubView paramMessageItemSubView)
    {
      if (paramMessageItemSubView == null)
        return;
      super.removeMessage(paramMessageItemSubView);
      MessageItemSubView localMessageItemSubView1 = paramMessageItemSubView.mNextMsgItem;
      MessageItemSubView localMessageItemSubView2 = paramMessageItemSubView.mPrevMsgItem;
      if (localMessageItemSubView1 != null)
        localMessageItemSubView1.requestFocus();
      while (true)
      {
        MessageItemView.this.mMsgLinkedList.removeNode(paramMessageItemSubView);
        MessageItemView.this.mMessageItems.remove(paramMessageItemSubView.getMessageItem());
        MessageItemView.this.removeView(paramMessageItemSubView);
        if (MessageItemView.this.mNotifier == null)
          break;
        MessageItemView.this.mNotifier.updateMessageNumberText(MessageItemView.this.mMessageViewType, 1);
        return;
        if (localMessageItemSubView2 != null)
        {
          localMessageItemSubView2.requestFocus();
        }
        else
        {
          Logger.i(MessageItemView.TAG, "当前焦点在消息列表视图中！");
          if (MessageItemView.this.mNotifier != null)
            MessageItemView.this.mNotifier.updateFocusOnSetAllBtn();
        }
      }
    }

    public void updateReadMyMsgNumber(int paramInt)
    {
      super.updateReadMyMsgNumber(paramInt);
      MessageItemView.this.mNotifier.updateReadMyMsgNumber(paramInt);
    }

    public void updateReadSysMsgNumber(int paramInt)
    {
      super.updateReadSysMsgNumber(paramInt);
      MessageItemView.this.mNotifier.updateReadSysMsgNumber(paramInt);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.widget.MessageItemView
 * JD-Core Version:    0.6.2
 */