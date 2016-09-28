package com.starcor.message;

import com.starcor.utils.Logger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MessageTrigger
{
  private static final int MESSAGE_QUEUE_LIMIT = 40;
  private static final String TAG = MessageTrigger.class.getSimpleName();
  private static final int THREAD_SLEEP_TIME = 200;
  private ExecutorService mCachedThreadPool;
  private MessageTriggerProcess mProcess;
  private BlockingQueue<Message> msgQueue = null;

  public MessageTrigger()
  {
    init();
  }

  public void doTrigger(Message paramMessage)
  {
    if (this.mProcess != null)
      this.mProcess.process(paramMessage);
  }

  void init()
  {
    if (Runtime.getRuntime().availableProcessors() <= 2);
    for (int i = 3; ; i = 6)
    {
      this.mCachedThreadPool = Executors.newFixedThreadPool(i);
      return;
    }
  }

  public boolean push(Message paramMessage)
  {
    if (paramMessage == null)
    {
      Logger.e(TAG, "空消息");
      return false;
    }
    if (this.msgQueue.size() < 40)
    {
      this.msgQueue.add(paramMessage);
      return true;
    }
    Logger.e(TAG, "Queue full");
    return false;
  }

  public void setProcess(MessageTriggerProcess paramMessageTriggerProcess)
  {
    this.mProcess = paramMessageTriggerProcess;
  }

  public boolean trigger(final Message paramMessage)
  {
    if (paramMessage.getExcuteMode() == 2)
      doTrigger(paramMessage);
    while (true)
    {
      return false;
      if (paramMessage.getExcuteMode() == 1)
        this.mCachedThreadPool.execute(new Runnable()
        {
          public void run()
          {
            MessageTrigger.this.doTrigger(paramMessage);
          }
        });
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.message.MessageTrigger
 * JD-Core Version:    0.6.2
 */