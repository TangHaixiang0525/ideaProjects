package com.starcor.common.tools;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UiTimer
{
  private static final int ALWAYS_LOOP_EVENT_COUNTS_LIMIT = 2;
  private static final int LOOP_EVENT_COUNTS_LIMIT = 10;
  private static final int NOTIFY_UI_EVENT = -102345;
  private static final String TAG = UiTimer.class.getSimpleName();
  private static UiTimer instance;
  private int alwaysLoopEventCounts;
  private int loopEventCounts;
  private Lock mItemsLock = new ReentrantLock();
  private List<EventItem> mItmes = new ArrayList();
  private Handler mUiHandler = new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      UiTimer.EventItem localEventItem;
      if ((paramAnonymousMessage != null) && (paramAnonymousMessage.what == -102345))
      {
        localEventItem = (UiTimer.EventItem)paramAnonymousMessage.obj;
        paramAnonymousMessage.obj = null;
        if (UiTimer.EventItem.access$000(localEventItem))
          sendMessageDelayed(UiTimer.this.getHandlerMsg(localEventItem), localEventItem.interval);
      }
      else
      {
        return;
      }
      UiTimer.this.removeEvent(localEventItem.runnable);
    }
  };

  private Message getHandlerMsg(EventItem paramEventItem)
  {
    Message localMessage = Message.obtain();
    localMessage.obj = paramEventItem;
    localMessage.what = -102345;
    return localMessage;
  }

  public static UiTimer getInstance()
  {
    if (instance == null)
      instance = new UiTimer();
    return instance;
  }

  public void addEvent(int paramInt1, int paramInt2, int paramInt3, Runnable paramRunnable)
  {
    if (this.loopEventCounts > 10)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(10);
      throw new IllegalArgumentException(String.format("can not add more than %d not always loop event!!", arrayOfObject));
    }
    this.mItemsLock.lock();
    try
    {
      EventItem localEventItem = new EventItem(paramInt1, paramInt2, paramRunnable);
      this.mItmes.add(localEventItem);
      if (paramInt3 <= 0)
        this.mUiHandler.sendMessage(getHandlerMsg(localEventItem));
      while (true)
      {
        return;
        this.mUiHandler.sendMessageDelayed(getHandlerMsg(localEventItem), paramInt3);
      }
    }
    finally
    {
      this.mItemsLock.unlock();
    }
  }

  public void addEvent(int paramInt1, int paramInt2, Runnable paramRunnable)
  {
    if (this.alwaysLoopEventCounts > 2)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(2);
      throw new IllegalArgumentException(String.format("can not add more than %d always loop event!!", arrayOfObject));
    }
    this.mItemsLock.lock();
    try
    {
      EventItem localEventItem = new EventItem(paramInt1, paramRunnable);
      this.mItmes.add(localEventItem);
      if (paramInt2 <= 0)
        this.mUiHandler.sendMessage(getHandlerMsg(localEventItem));
      while (true)
      {
        return;
        this.mUiHandler.sendMessageDelayed(getHandlerMsg(localEventItem), paramInt2);
      }
    }
    finally
    {
      this.mItemsLock.unlock();
    }
  }

  public void removeAll()
  {
    this.mItemsLock.lock();
    try
    {
      Iterator localIterator = this.mItmes.iterator();
      while (localIterator.hasNext())
        ((EventItem)localIterator.next()).stop();
    }
    finally
    {
      this.mItemsLock.unlock();
    }
    this.mItmes.clear();
    this.mUiHandler.removeMessages(-102345);
    this.mItemsLock.unlock();
  }

  public boolean removeEvent(Runnable paramRunnable)
  {
    this.mItemsLock.lock();
    try
    {
      Iterator localIterator = this.mItmes.iterator();
      while (localIterator.hasNext())
      {
        EventItem localEventItem = (EventItem)localIterator.next();
        if ((localEventItem.runnable != null) && (localEventItem.runnable.equals(paramRunnable)))
        {
          localEventItem.stop();
          this.mItmes.remove(localEventItem);
          return true;
        }
      }
      return false;
    }
    finally
    {
      this.mItemsLock.unlock();
    }
  }

  private class EventItem
  {
    boolean alwaysRun;
    int interval;
    int repeat;
    Runnable runnable;

    EventItem(int paramInt1, int paramRunnable, Runnable arg4)
    {
      this.repeat = paramInt1;
      this.interval = paramRunnable;
      Object localObject;
      this.runnable = localObject;
      UiTimer.access$308(UiTimer.this);
    }

    EventItem(int paramRunnable, Runnable arg3)
    {
      this.interval = paramRunnable;
      Object localObject;
      this.runnable = localObject;
      this.alwaysRun = true;
      UiTimer.access$408(UiTimer.this);
    }

    private boolean start()
    {
      if ((this.alwaysRun) && (this.runnable != null))
        this.runnable.run();
      do
      {
        return true;
        this.repeat = (-1 + this.repeat);
        if ((this.repeat >= 0) && (this.runnable != null))
          this.runnable.run();
      }
      while (this.repeat > 0);
      return false;
    }

    private void stop()
    {
      if (this.alwaysRun)
        UiTimer.access$410(UiTimer.this);
      while (true)
      {
        this.repeat = 0;
        this.alwaysRun = false;
        this.runnable = null;
        return;
        UiTimer.access$310(UiTimer.this);
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.common.tools.UiTimer
 * JD-Core Version:    0.6.2
 */