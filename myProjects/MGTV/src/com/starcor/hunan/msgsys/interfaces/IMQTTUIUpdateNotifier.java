package com.starcor.hunan.msgsys.interfaces;

import android.os.Bundle;
import com.starcor.hunan.msgsys.data.MessageItemData;
import com.starcor.hunan.msgsys.data.MessageItemData.MessageType;
import com.starcor.hunan.msgsys.widget.MessageItemSubView;
import com.starcor.hunan.msgsys.widget.MessageView.MessageViewType;
import java.util.List;

public abstract interface IMQTTUIUpdateNotifier
{
  public abstract void addMessage(MessageItemSubView paramMessageItemSubView);

  public abstract void displayLoadingDialog(Object paramObject);

  public abstract void finishAddMessage(String paramString, Object paramObject);

  public abstract void finishDeleteMessage(String paramString);

  public abstract void finishDeleteMessage(String paramString1, String paramString2);

  public abstract void finishLoadingAllAdminMsg(List<MessageItemData> paramList);

  public abstract void finishLoadingAllMsg(List<MessageItemData> paramList1, List<MessageItemData> paramList2, int paramInt1, int paramInt2);

  public abstract void finishLoadingAllPrivateMsg(List<MessageItemData> paramList);

  public abstract void finishLoadingAllPublicAndPrivateMsg(List<MessageItemData> paramList1, List<MessageItemData> paramList2, int paramInt1, int paramInt2);

  public abstract void finishLoadingAllPublicMsg(List<MessageItemData> paramList);

  public abstract void finishUpdatingMessageValue(String paramString, Bundle paramBundle);

  public abstract void handleDownEventFocus(MessageItemSubView paramMessageItemSubView);

  public abstract void handleLeftEventFocus();

  public abstract void hideLoadingDialog(Object paramObject);

  public abstract void loadWebDialog(String paramString);

  public abstract void onError(String paramString, Object paramObject);

  public abstract void removeAllMessage(MessageItemData.MessageType[] paramArrayOfMessageType);

  public abstract void removeMessage(MessageItemData.MessageType paramMessageType, String paramString);

  public abstract void removeMessage(MessageItemSubView paramMessageItemSubView);

  public abstract void setAllMessageRead(MessageItemData.MessageType[] paramArrayOfMessageType);

  public abstract void setAllMessageUnread(MessageItemData.MessageType[] paramArrayOfMessageType);

  public abstract void setMessageRead(MessageItemData.MessageType paramMessageType, String paramString);

  public abstract void setMessageUnRead(MessageItemData.MessageType paramMessageType, String paramString);

  public abstract void updateFocusOnSetAllBtn();

  public abstract void updateMessageNumberText(MessageView.MessageViewType paramMessageViewType, int paramInt);

  public abstract void updateReadMyMsgNumber(int paramInt);

  public abstract void updateReadSysMsgNumber(int paramInt);

  public abstract void updateTotalUnreadMsgNum(int paramInt1, int paramInt2);

  public abstract void updateUnreadMyMsgNumber(int paramInt);

  public abstract void updateUnreadSysMsgNumber(int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.interfaces.IMQTTUIUpdateNotifier
 * JD-Core Version:    0.6.2
 */