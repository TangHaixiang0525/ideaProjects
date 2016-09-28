package com.starcor.message;

import com.starcor.utils.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MessageHandler
  implements MessageTriggerProcess
{
  private static final String TAG = MessageHandler.class.getSimpleName();
  private static MessageHandler instance = null;
  private static List<MessageObserver> recevierList = Collections.synchronizedList(new ArrayList());
  private MessageTrigger mMsgTrigger = new MessageTrigger();

  private MessageHandler()
  {
    this.mMsgTrigger.setProcess(this);
  }

  private void doNotify(Message paramMessage, MessageObserver paramMessageObserver)
  {
    if (paramMessageObserver == null);
    int i;
    do
    {
      return;
      i = paramMessage.getFlags();
    }
    while (!paramMessageObserver.containsFlags(i));
    paramMessageObserver.setCurFlags(paramMessageObserver.getCommonFlags(i));
    paramMessageObserver.doNotify(paramMessage);
  }

  public static MessageHandler instance()
  {
    if (instance == null)
      instance = new MessageHandler();
    return instance;
  }

  public void clear()
  {
    recevierList.clear();
  }

  public void doNotify(Message paramMessage)
  {
    Logger.i(TAG, "doNotify!msg flag is: " + paramMessage.getFlags());
    this.mMsgTrigger.trigger(paramMessage);
  }

  void excuteNotify(Message paramMessage)
  {
    if ((recevierList.size() == 0) || (paramMessage == null));
    while (true)
    {
      return;
      Iterator localIterator = recevierList.iterator();
      while (localIterator.hasNext())
        doNotify(paramMessage, (MessageObserver)localIterator.next());
    }
  }

  public GroupMessageFactory obtainGroupMessageHandler(Class<?> paramClass)
  {
    GroupMessageFactory localGroupMessageFactory = new GroupMessageFactory();
    localGroupMessageFactory.mSrc = paramClass;
    return localGroupMessageFactory;
  }

  public Message obtainMessage()
  {
    return new Message();
  }

  public Message obtainMessage(Class<?> paramClass)
  {
    if (paramClass == null)
      return null;
    return new Message(paramClass);
  }

  public void process(Message paramMessage)
  {
    excuteNotify(paramMessage);
  }

  public Boolean register(Integer paramInteger, MessageObserver paramMessageObserver)
  {
    if ((paramMessageObserver == null) || (recevierList.contains(paramMessageObserver)))
      return Boolean.valueOf(false);
    paramMessageObserver.addFlags(paramInteger.intValue());
    Logger.i(TAG, "register!flag is: " + paramInteger);
    recevierList.add(paramMessageObserver);
    return Boolean.valueOf(true);
  }

  public Boolean unregister(MessageObserver paramMessageObserver)
  {
    if ((paramMessageObserver == null) || (recevierList.size() == 0) || (!recevierList.contains(paramMessageObserver)))
      return Boolean.valueOf(false);
    recevierList.remove(paramMessageObserver);
    paramMessageObserver.clear();
    Logger.i(TAG, "unregister!recevierList size is: " + recevierList.size());
    return Boolean.valueOf(true);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.message.MessageHandler
 * JD-Core Version:    0.6.2
 */