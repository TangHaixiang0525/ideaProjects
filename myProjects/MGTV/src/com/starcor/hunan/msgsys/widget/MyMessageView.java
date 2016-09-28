package com.starcor.hunan.msgsys.widget;

import android.content.Context;
import com.starcor.hunan.msgsys.data.MessageItemData;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import java.util.List;

public class MyMessageView extends MessageView
{
  public MyMessageView(Context paramContext, List<MessageItemData> paramList)
  {
    super(paramContext, paramList, MessageView.MessageViewType.MY_MESSAGE_PAGE);
  }

  public void setMessageNumberNotifier(AbstractMQTTUIUpdateNotifier paramAbstractMQTTUIUpdateNotifier)
  {
    this.mNotifier = paramAbstractMQTTUIUpdateNotifier;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.widget.MyMessageView
 * JD-Core Version:    0.6.2
 */