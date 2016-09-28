package com.starcor.hunan.msgsys.interfaces;

import android.os.Bundle;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.msgsys.data.MessageItemData;
import com.starcor.hunan.msgsys.data.MessageItemData.MessageType;
import com.starcor.hunan.msgsys.widget.MessageItemSubView;
import com.starcor.hunan.msgsys.widget.MessageView.MessageViewType;
import java.util.List;

public abstract class AbstractMQTTUIUpdateNotifier
  implements IMQTTUIUpdateNotifier
{
  public void addMessage(MessageItemSubView paramMessageItemSubView)
  {
  }

  public void displayLoadingDialog(Object paramObject)
  {
    Logger.i("displayLoadingDialog");
  }

  public void finishAddMessage(String paramString, Object paramObject)
  {
    Logger.i("finishAddMessage", "添加完毕" + paramString + "频道的数据！");
    if (paramObject != null)
      Logger.i("finishAddMessage", "添加的数据为" + paramObject.toString());
  }

  public void finishDeleteMessage(String paramString)
  {
    Logger.i("finishDeleteMessage", "完成删除消息" + paramString);
  }

  public void finishDeleteMessage(String paramString1, String paramString2)
  {
    Logger.i("finishDeleteMessage", "完成删除" + paramString1 + "消息" + paramString2);
  }

  public void finishLoadingAllAdminMsg(List<MessageItemData> paramList)
  {
  }

  public void finishLoadingAllMsg(List<MessageItemData> paramList1, List<MessageItemData> paramList2, int paramInt1, int paramInt2)
  {
    Logger.i("finishLoadingAllMsg", "系统消息栏目的未读消息数为 " + paramInt1 + "我的消息栏目的未读消息数为 " + paramInt2);
  }

  public void finishLoadingAllPrivateMsg(List<MessageItemData> paramList)
  {
  }

  public void finishLoadingAllPublicAndPrivateMsg(List<MessageItemData> paramList1, List<MessageItemData> paramList2, int paramInt1, int paramInt2)
  {
    Logger.i("finishLoadingAllPublicAndPrivateMsg", "未读的公共频道消息数为 " + paramInt1 + "未读的私有频道消息数为 " + paramInt2);
  }

  public void finishLoadingAllPublicMsg(List<MessageItemData> paramList)
  {
  }

  public void finishUpdatingMessageValue(String paramString, Bundle paramBundle)
  {
    Logger.i("finishUpdatingMessageValue", "action=" + paramString + " msgParams=" + paramBundle);
  }

  public void handleDownEventFocus(MessageItemSubView paramMessageItemSubView)
  {
  }

  public void handleLeftEventFocus()
  {
  }

  public void hideLoadingDialog(Object paramObject)
  {
    Logger.i("hideLoadingDialog");
  }

  public void loadWebDialog(String paramString)
  {
    Logger.i("loadWebDialog", "url=" + paramString);
  }

  public void onError(String paramString, Object paramObject)
  {
    Logger.i("onError", "action=" + paramString + " params=" + paramObject);
  }

  public void removeAllMessage(MessageItemData.MessageType[] paramArrayOfMessageType)
  {
  }

  public void removeMessage(MessageItemData.MessageType paramMessageType, String paramString)
  {
    Logger.i("removeMessage", "删除的消息类型为" + paramMessageType + " 消息ID为" + paramString);
  }

  public void removeMessage(MessageItemSubView paramMessageItemSubView)
  {
  }

  public void setAllMessageRead(MessageItemData.MessageType[] paramArrayOfMessageType)
  {
  }

  public void setAllMessageUnread(MessageItemData.MessageType[] paramArrayOfMessageType)
  {
  }

  public void setMessageRead(MessageItemData.MessageType paramMessageType, String paramString)
  {
  }

  public void setMessageUnRead(MessageItemData.MessageType paramMessageType, String paramString)
  {
  }

  public void updateFocusOnSetAllBtn()
  {
  }

  public void updateMessageNumberText(MessageView.MessageViewType paramMessageViewType, int paramInt)
  {
  }

  public void updateReadMyMsgNumber(int paramInt)
  {
  }

  public void updateReadSysMsgNumber(int paramInt)
  {
  }

  public void updateTotalUnreadMsgNum(int paramInt1, int paramInt2)
  {
    Logger.i("updateTotalUnreadPublicMsgNum", "当前总共的系统消息未读总数为" + paramInt1);
    Logger.i("updateTotalUnreadPrivateMsgNum", "当前总共的我的消息未读总数为" + paramInt2);
  }

  public void updateUnreadMyMsgNumber(int paramInt)
  {
  }

  public void updateUnreadSysMsgNumber(int paramInt)
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier
 * JD-Core Version:    0.6.2
 */