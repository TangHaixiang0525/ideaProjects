package org.eclipse.paho.client.mqttv3.internal;

import java.io.PrintStream;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class CommsCallback
  implements Runnable
{
  private static int INBOUND_QUEUE_SIZE = 10;
  static final String className = CommsCallback.class.getName();
  private Thread callbackThread;
  private ClientComms clientComms;
  private ClientState clientState;
  private Vector completeQueue;
  private Object lifecycle = new Object();
  Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);
  private Vector messageQueue;
  private MqttCallback mqttCallback;
  private boolean quiescing = false;
  public boolean running = false;
  private Object spaceAvailable = new Object();
  private Object workAvailable = new Object();

  CommsCallback(ClientComms paramClientComms)
  {
    this.clientComms = paramClientComms;
    this.messageQueue = new Vector(INBOUND_QUEUE_SIZE);
    this.completeQueue = new Vector(INBOUND_QUEUE_SIZE);
    this.log.setResourceName(paramClientComms.getClient().getClientId());
  }

  private void handleActionComplete(MqttToken paramMqttToken)
    throws MqttException
  {
    try
    {
      Logger localLogger = this.log;
      String str = className;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramMqttToken.internalTok.getKey();
      localLogger.fine(str, "handleActionComplete", "705", arrayOfObject);
      paramMqttToken.internalTok.notifyComplete();
      if (!paramMqttToken.internalTok.isNotified())
      {
        if ((this.mqttCallback != null) && ((paramMqttToken instanceof MqttDeliveryToken)) && (paramMqttToken.isComplete()))
          this.mqttCallback.deliveryComplete((MqttDeliveryToken)paramMqttToken);
        fireActionEvent(paramMqttToken);
      }
      if (((paramMqttToken instanceof MqttDeliveryToken)) && (paramMqttToken.isComplete()))
        paramMqttToken.internalTok.setNotified(true);
      if (paramMqttToken.isComplete())
        this.clientState.notifyComplete(paramMqttToken);
      return;
    }
    finally
    {
    }
  }

  private void handleMessage(MqttPublish paramMqttPublish)
    throws MqttException, Exception
  {
    if (this.mqttCallback != null)
    {
      String str1 = paramMqttPublish.getTopicName();
      Logger localLogger = this.log;
      String str2 = className;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = new Integer(paramMqttPublish.getMessageId());
      arrayOfObject[1] = str1;
      localLogger.fine(str2, "handleMessage", "713", arrayOfObject);
      this.mqttCallback.messageArrived(str1, paramMqttPublish.getMessage());
      if (paramMqttPublish.getMessage().getQos() != 1)
        break label122;
      this.clientComms.internalSend(new MqttPubAck(paramMqttPublish), new MqttToken(this.clientComms.getClient().getClientId()));
    }
    label122: 
    while (paramMqttPublish.getMessage().getQos() != 2)
      return;
    this.clientComms.deliveryComplete(paramMqttPublish);
    MqttPubComp localMqttPubComp = new MqttPubComp(paramMqttPublish);
    this.clientComms.internalSend(localMqttPubComp, new MqttToken(this.clientComms.getClient().getClientId()));
  }

  public void asyncOperationComplete(MqttToken paramMqttToken)
  {
    if (this.running)
    {
      this.completeQueue.addElement(paramMqttToken);
      synchronized (this.workAvailable)
      {
        Logger localLogger = this.log;
        String str = className;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramMqttToken.internalTok.getKey();
        localLogger.fine(str, "asyncOperationComplete", "715", arrayOfObject);
        this.workAvailable.notifyAll();
        return;
      }
    }
    try
    {
      handleActionComplete(paramMqttToken);
      return;
    }
    catch (Throwable localThrowable)
    {
      this.log.fine(className, "asyncOperationComplete", "719", null, localThrowable);
      System.err.println("problem in asyncopcomplete " + localThrowable);
      localThrowable.printStackTrace();
      this.clientComms.shutdownConnection(null, new MqttException(localThrowable));
    }
  }

  public void connectionLost(MqttException paramMqttException)
  {
    try
    {
      if ((this.mqttCallback != null) && (paramMqttException != null))
      {
        this.log.fine(className, "connectionLost", "708", new Object[] { paramMqttException });
        this.mqttCallback.connectionLost(paramMqttException);
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      this.log.fine(className, "connectionLost", "720", new Object[] { localThrowable });
    }
  }

  public void fireActionEvent(MqttToken paramMqttToken)
  {
    IMqttActionListener localIMqttActionListener;
    if (paramMqttToken != null)
    {
      localIMqttActionListener = paramMqttToken.getActionCallback();
      if (localIMqttActionListener != null)
      {
        if (paramMqttToken.getException() != null)
          break label73;
        Logger localLogger2 = this.log;
        String str2 = className;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = paramMqttToken.internalTok.getKey();
        localLogger2.fine(str2, "fireActionEvent", "716", arrayOfObject2);
        localIMqttActionListener.onSuccess(paramMqttToken);
      }
    }
    return;
    label73: Logger localLogger1 = this.log;
    String str1 = className;
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = paramMqttToken.internalTok.getKey();
    localLogger1.fine(str1, "fireActionEvent", "716", arrayOfObject1);
    localIMqttActionListener.onFailure(paramMqttToken, paramMqttToken.getException());
  }

  protected Thread getThread()
  {
    return this.callbackThread;
  }

  public boolean isQuiesced()
  {
    return (this.quiescing) && (this.completeQueue.size() == 0) && (this.messageQueue.size() == 0);
  }

  public void messageArrived(MqttPublish paramMqttPublish)
  {
    if (this.mqttCallback != null)
      synchronized (this.spaceAvailable)
      {
        if (!this.quiescing)
        {
          int i = this.messageQueue.size();
          int j = INBOUND_QUEUE_SIZE;
          if (i < j);
        }
      }
    try
    {
      this.log.fine(className, "messageArrived", "709");
      this.spaceAvailable.wait();
      label67: if (!this.quiescing)
        this.messageQueue.addElement(paramMqttPublish);
      synchronized (this.workAvailable)
      {
        this.log.fine(className, "messageArrived", "710");
        this.workAvailable.notifyAll();
        return;
        localObject2 = finally;
        throw localObject2;
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      break label67;
    }
  }

  public void quiesce()
  {
    this.quiescing = true;
    synchronized (this.spaceAvailable)
    {
      this.log.fine(className, "quiesce", "711");
      this.spaceAvailable.notifyAll();
      return;
    }
  }

  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 48	org/eclipse/paho/client/mqttv3/internal/CommsCallback:running	Z
    //   4: ifeq +254 -> 258
    //   7: aload_0
    //   8: getfield 54	org/eclipse/paho/client/mqttv3/internal/CommsCallback:workAvailable	Ljava/lang/Object;
    //   11: astore 6
    //   13: aload 6
    //   15: monitorenter
    //   16: aload_0
    //   17: getfield 48	org/eclipse/paho/client/mqttv3/internal/CommsCallback:running	Z
    //   20: aload_0
    //   21: getfield 75	org/eclipse/paho/client/mqttv3/internal/CommsCallback:messageQueue	Ljava/util/Vector;
    //   24: invokevirtual 311	java/util/Vector:isEmpty	()Z
    //   27: iand
    //   28: ifeq +38 -> 66
    //   31: aload_0
    //   32: getfield 77	org/eclipse/paho/client/mqttv3/internal/CommsCallback:completeQueue	Ljava/util/Vector;
    //   35: invokevirtual 311	java/util/Vector:isEmpty	()Z
    //   38: ifeq +28 -> 66
    //   41: aload_0
    //   42: getfield 66	org/eclipse/paho/client/mqttv3/internal/CommsCallback:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   45: getstatic 42	org/eclipse/paho/client/mqttv3/internal/CommsCallback:className	Ljava/lang/String;
    //   48: ldc_w 312
    //   51: ldc_w 314
    //   54: invokeinterface 298 4 0
    //   59: aload_0
    //   60: getfield 54	org/eclipse/paho/client/mqttv3/internal/CommsCallback:workAvailable	Ljava/lang/Object;
    //   63: invokevirtual 301	java/lang/Object:wait	()V
    //   66: aload 6
    //   68: monitorexit
    //   69: aload_0
    //   70: getfield 48	org/eclipse/paho/client/mqttv3/internal/CommsCallback:running	Z
    //   73: ifeq +69 -> 142
    //   76: aload_0
    //   77: getfield 77	org/eclipse/paho/client/mqttv3/internal/CommsCallback:completeQueue	Ljava/util/Vector;
    //   80: invokevirtual 311	java/util/Vector:isEmpty	()Z
    //   83: ifne +26 -> 109
    //   86: aload_0
    //   87: aload_0
    //   88: getfield 77	org/eclipse/paho/client/mqttv3/internal/CommsCallback:completeQueue	Ljava/util/Vector;
    //   91: iconst_0
    //   92: invokevirtual 318	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   95: checkcast 100	org/eclipse/paho/client/mqttv3/MqttToken
    //   98: invokespecial 211	org/eclipse/paho/client/mqttv3/internal/CommsCallback:handleActionComplete	(Lorg/eclipse/paho/client/mqttv3/MqttToken;)V
    //   101: aload_0
    //   102: getfield 77	org/eclipse/paho/client/mqttv3/internal/CommsCallback:completeQueue	Ljava/util/Vector;
    //   105: iconst_0
    //   106: invokevirtual 321	java/util/Vector:removeElementAt	(I)V
    //   109: aload_0
    //   110: getfield 75	org/eclipse/paho/client/mqttv3/internal/CommsCallback:messageQueue	Ljava/util/Vector;
    //   113: invokevirtual 311	java/util/Vector:isEmpty	()Z
    //   116: ifne +26 -> 142
    //   119: aload_0
    //   120: aload_0
    //   121: getfield 75	org/eclipse/paho/client/mqttv3/internal/CommsCallback:messageQueue	Ljava/util/Vector;
    //   124: iconst_0
    //   125: invokevirtual 318	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   128: checkcast 155	org/eclipse/paho/client/mqttv3/internal/wire/MqttPublish
    //   131: invokespecial 323	org/eclipse/paho/client/mqttv3/internal/CommsCallback:handleMessage	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttPublish;)V
    //   134: aload_0
    //   135: getfield 75	org/eclipse/paho/client/mqttv3/internal/CommsCallback:messageQueue	Ljava/util/Vector;
    //   138: iconst_0
    //   139: invokevirtual 321	java/util/Vector:removeElementAt	(I)V
    //   142: aload_0
    //   143: getfield 50	org/eclipse/paho/client/mqttv3/internal/CommsCallback:quiescing	Z
    //   146: ifeq +11 -> 157
    //   149: aload_0
    //   150: getfield 145	org/eclipse/paho/client/mqttv3/internal/CommsCallback:clientState	Lorg/eclipse/paho/client/mqttv3/internal/ClientState;
    //   153: invokevirtual 326	org/eclipse/paho/client/mqttv3/internal/ClientState:checkQuiesceLock	()Z
    //   156: pop
    //   157: aload_0
    //   158: getfield 56	org/eclipse/paho/client/mqttv3/internal/CommsCallback:spaceAvailable	Ljava/lang/Object;
    //   161: astore_3
    //   162: aload_3
    //   163: monitorenter
    //   164: aload_0
    //   165: getfield 66	org/eclipse/paho/client/mqttv3/internal/CommsCallback:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   168: getstatic 42	org/eclipse/paho/client/mqttv3/internal/CommsCallback:className	Ljava/lang/String;
    //   171: ldc_w 312
    //   174: ldc_w 328
    //   177: invokeinterface 298 4 0
    //   182: aload_0
    //   183: getfield 56	org/eclipse/paho/client/mqttv3/internal/CommsCallback:spaceAvailable	Ljava/lang/Object;
    //   186: invokevirtual 209	java/lang/Object:notifyAll	()V
    //   189: aload_3
    //   190: monitorexit
    //   191: goto -191 -> 0
    //   194: astore 4
    //   196: aload_3
    //   197: monitorexit
    //   198: aload 4
    //   200: athrow
    //   201: astore_2
    //   202: aload_0
    //   203: getfield 66	org/eclipse/paho/client/mqttv3/internal/CommsCallback:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   206: getstatic 42	org/eclipse/paho/client/mqttv3/internal/CommsCallback:className	Ljava/lang/String;
    //   209: ldc_w 312
    //   212: ldc_w 330
    //   215: aconst_null
    //   216: aload_2
    //   217: invokeinterface 216 6 0
    //   222: aload_0
    //   223: iconst_0
    //   224: putfield 48	org/eclipse/paho/client/mqttv3/internal/CommsCallback:running	Z
    //   227: aload_0
    //   228: getfield 68	org/eclipse/paho/client/mqttv3/internal/CommsCallback:clientComms	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
    //   231: aconst_null
    //   232: new 98	org/eclipse/paho/client/mqttv3/MqttException
    //   235: dup
    //   236: aload_2
    //   237: invokespecial 248	org/eclipse/paho/client/mqttv3/MqttException:<init>	(Ljava/lang/Throwable;)V
    //   240: invokevirtual 252	org/eclipse/paho/client/mqttv3/internal/ClientComms:shutdownConnection	(Lorg/eclipse/paho/client/mqttv3/MqttToken;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
    //   243: goto -243 -> 0
    //   246: astore 7
    //   248: aload 6
    //   250: monitorexit
    //   251: aload 7
    //   253: athrow
    //   254: astore_1
    //   255: goto -186 -> 69
    //   258: return
    //
    // Exception table:
    //   from	to	target	type
    //   164	191	194	finally
    //   196	198	194	finally
    //   7	16	201	java/lang/Throwable
    //   69	109	201	java/lang/Throwable
    //   109	142	201	java/lang/Throwable
    //   142	157	201	java/lang/Throwable
    //   157	164	201	java/lang/Throwable
    //   198	201	201	java/lang/Throwable
    //   251	254	201	java/lang/Throwable
    //   16	66	246	finally
    //   66	69	246	finally
    //   248	251	246	finally
    //   7	16	254	java/lang/InterruptedException
    //   251	254	254	java/lang/InterruptedException
  }

  public void setCallback(MqttCallback paramMqttCallback)
  {
    this.mqttCallback = paramMqttCallback;
  }

  public void setClientState(ClientState paramClientState)
  {
    this.clientState = paramClientState;
  }

  public void start(String paramString)
  {
    synchronized (this.lifecycle)
    {
      if (!this.running)
      {
        this.messageQueue.clear();
        this.completeQueue.clear();
        this.running = true;
        this.quiescing = false;
        this.callbackThread = new Thread(this, paramString);
        this.callbackThread.start();
      }
      return;
    }
  }

  public void stop()
  {
    synchronized (this.lifecycle)
    {
      if (this.running)
      {
        this.log.fine(className, "stop", "700");
        this.running = false;
        boolean bool = Thread.currentThread().equals(this.callbackThread);
        if (bool);
      }
      try
      {
        synchronized (this.workAvailable)
        {
          this.log.fine(className, "stop", "701");
          this.workAvailable.notifyAll();
          this.callbackThread.join();
          label96: this.callbackThread = null;
          this.log.fine(className, "stop", "703");
          return;
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        break label96;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.CommsCallback
 * JD-Core Version:    0.6.2
 */