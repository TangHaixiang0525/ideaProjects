package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class Token
{
  static final String className = Token.class.getName();
  private IMqttActionListener callback = null;
  private IMqttAsyncClient client = null;
  private volatile boolean completed = false;
  private MqttException exception = null;
  private String key;
  Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);
  protected MqttMessage message = null;
  public int messageID = 0;
  public boolean notified = false;
  private boolean pendingComplete = false;
  private MqttWireMessage response = null;
  private Object responseLock = new Object();
  private boolean sent = false;
  private Object sentLock = new Object();
  private String[] topics = null;
  private Object userContext = null;

  public Token(String paramString)
  {
    this.log.setResourceName(paramString);
  }

  public boolean checkResult()
    throws MqttException
  {
    if (getException() != null)
      throw getException();
    return true;
  }

  public IMqttActionListener getActionCallback()
  {
    return this.callback;
  }

  public IMqttAsyncClient getClient()
  {
    return this.client;
  }

  public MqttException getException()
  {
    return this.exception;
  }

  public String getKey()
  {
    return this.key;
  }

  public MqttMessage getMessage()
  {
    return this.message;
  }

  public int getMessageID()
  {
    return this.messageID;
  }

  public String[] getTopics()
  {
    return this.topics;
  }

  public Object getUserContext()
  {
    return this.userContext;
  }

  public MqttWireMessage getWireMessage()
  {
    return this.response;
  }

  public boolean isComplete()
  {
    return this.completed;
  }

  protected boolean isCompletePending()
  {
    return this.pendingComplete;
  }

  protected boolean isInUse()
  {
    return (getClient() != null) && (!isComplete());
  }

  public boolean isNotified()
  {
    return this.notified;
  }

  protected void markComplete(MqttWireMessage paramMqttWireMessage, MqttException paramMqttException)
  {
    Logger localLogger = this.log;
    String str = className;
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = getKey();
    arrayOfObject[1] = paramMqttWireMessage;
    arrayOfObject[2] = paramMqttException;
    localLogger.fine(str, "markComplete", "404", arrayOfObject);
    synchronized (this.responseLock)
    {
      if ((paramMqttWireMessage instanceof MqttAck))
        this.message = null;
      this.pendingComplete = true;
      this.response = paramMqttWireMessage;
      this.exception = paramMqttException;
      return;
    }
  }

  protected void notifyComplete()
  {
    Logger localLogger = this.log;
    String str = className;
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = getKey();
    arrayOfObject[1] = this.response;
    arrayOfObject[2] = this.exception;
    localLogger.fine(str, "notifyComplete", "404", arrayOfObject);
    while (true)
    {
      synchronized (this.responseLock)
      {
        if ((this.exception == null) && (this.pendingComplete))
        {
          this.completed = true;
          this.pendingComplete = false;
          this.responseLock.notifyAll();
        }
      }
      synchronized (this.sentLock)
      {
        this.sent = true;
        this.sentLock.notifyAll();
        return;
        this.pendingComplete = false;
        continue;
        localObject2 = finally;
        throw localObject2;
      }
    }
  }

  protected void notifySent()
  {
    Logger localLogger = this.log;
    String str = className;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = getKey();
    localLogger.fine(str, "notifySent", "403", arrayOfObject);
    synchronized (this.responseLock)
    {
      this.response = null;
      this.completed = false;
    }
    synchronized (this.sentLock)
    {
      this.sent = true;
      this.sentLock.notifyAll();
      return;
      localObject2 = finally;
      throw localObject2;
    }
  }

  public void reset()
    throws MqttException
  {
    if (isInUse())
      throw new MqttException(32201);
    Logger localLogger = this.log;
    String str = className;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = getKey();
    localLogger.fine(str, "reset", "410", arrayOfObject);
    this.client = null;
    this.completed = false;
    this.response = null;
    this.sent = false;
    this.exception = null;
    this.userContext = null;
  }

  public void setActionCallback(IMqttActionListener paramIMqttActionListener)
  {
    this.callback = paramIMqttActionListener;
  }

  protected void setClient(IMqttAsyncClient paramIMqttAsyncClient)
  {
    this.client = paramIMqttAsyncClient;
  }

  public void setException(MqttException paramMqttException)
  {
    synchronized (this.responseLock)
    {
      this.exception = paramMqttException;
      return;
    }
  }

  public void setKey(String paramString)
  {
    this.key = paramString;
  }

  public void setMessage(MqttMessage paramMqttMessage)
  {
    this.message = paramMqttMessage;
  }

  public void setMessageID(int paramInt)
  {
    this.messageID = paramInt;
  }

  public void setNotified(boolean paramBoolean)
  {
    this.notified = paramBoolean;
  }

  public void setTopics(String[] paramArrayOfString)
  {
    this.topics = paramArrayOfString;
  }

  public void setUserContext(Object paramObject)
  {
    this.userContext = paramObject;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("key=" + getKey());
    localStringBuffer.append(" ,topics=");
    if (getTopics() != null)
      for (int i = 0; i < getTopics().length; i++)
        localStringBuffer.append(getTopics()[i] + ", ");
    localStringBuffer.append(" ,usercontext=" + getUserContext());
    localStringBuffer.append(" ,isComplete=" + isComplete());
    localStringBuffer.append(" ,isNotified=" + isNotified());
    localStringBuffer.append(" ,exception=" + getException());
    localStringBuffer.append(" ,actioncallback=" + getActionCallback());
    return localStringBuffer.toString();
  }

  public void waitForCompletion()
    throws MqttException
  {
    waitForCompletion(-1L);
  }

  public void waitForCompletion(long paramLong)
    throws MqttException
  {
    Logger localLogger1 = this.log;
    String str1 = className;
    Object[] arrayOfObject1 = new Object[3];
    arrayOfObject1[0] = getKey();
    arrayOfObject1[1] = new Long(paramLong);
    arrayOfObject1[2] = this;
    localLogger1.fine(str1, "waitForCompletion", "407", arrayOfObject1);
    if ((waitForResponse(paramLong) == null) && (!this.completed))
    {
      Logger localLogger2 = this.log;
      String str2 = className;
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = getKey();
      arrayOfObject2[1] = this;
      localLogger2.fine(str2, "waitForCompletion", "406", arrayOfObject2);
      throw new MqttException(32000);
    }
    checkResult();
  }

  protected MqttWireMessage waitForResponse()
    throws MqttException
  {
    return waitForResponse(-1L);
  }

  protected MqttWireMessage waitForResponse(long paramLong)
    throws MqttException
  {
    while (true)
    {
      String str2;
      synchronized (this.responseLock)
      {
        Logger localLogger1 = this.log;
        String str1 = className;
        Object[] arrayOfObject1 = new Object[7];
        arrayOfObject1[0] = getKey();
        arrayOfObject1[1] = new Long(paramLong);
        arrayOfObject1[2] = new Boolean(this.sent);
        arrayOfObject1[3] = new Boolean(this.completed);
        if (this.exception == null)
        {
          str2 = "false";
          arrayOfObject1[4] = str2;
          arrayOfObject1[5] = this.response;
          arrayOfObject1[6] = this;
          localLogger1.fine(str1, "waitForResponse", "400", arrayOfObject1, this.exception);
          if (this.completed)
            break label295;
          MqttException localMqttException = this.exception;
          if (localMqttException != null);
        }
      }
      try
      {
        Logger localLogger3 = this.log;
        String str4 = className;
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = getKey();
        arrayOfObject3[1] = new Long(paramLong);
        localLogger3.fine(str4, "waitForResponse", "408", arrayOfObject3);
        if (paramLong == -1L)
          this.responseLock.wait();
        while (true)
        {
          if ((this.completed) || (this.exception == null))
            break label295;
          this.log.fine(className, "waitForResponse", "401", null, this.exception);
          throw this.exception;
          localObject2 = finally;
          throw localObject2;
          str2 = "true";
          break;
          this.responseLock.wait(paramLong);
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        while (true)
          this.exception = new MqttException(localInterruptedException);
      }
    }
    label295: Logger localLogger2 = this.log;
    String str3 = className;
    Object[] arrayOfObject2 = new Object[2];
    arrayOfObject2[0] = getKey();
    arrayOfObject2[1] = this.response;
    localLogger2.fine(str3, "waitForResponse", "402", arrayOfObject2);
    return this.response;
  }

  public void waitUntilSent()
    throws MqttException
  {
    synchronized (this.sentLock)
    {
      synchronized (this.responseLock)
      {
        if (this.exception != null)
          throw this.exception;
      }
    }
    boolean bool = this.sent;
    if (!bool);
    try
    {
      Logger localLogger = this.log;
      String str = className;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = getKey();
      localLogger.fine(str, "waitUntilSent", "409", arrayOfObject);
      this.sentLock.wait();
      label100: if (!this.sent)
      {
        if (this.exception == null)
          throw ExceptionHelper.createMqttException(6);
        throw this.exception;
      }
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label100;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.Token
 * JD-Core Version:    0.6.2
 */