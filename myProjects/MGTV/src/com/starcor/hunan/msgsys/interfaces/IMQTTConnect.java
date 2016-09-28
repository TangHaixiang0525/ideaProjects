package com.starcor.hunan.msgsys.interfaces;

public abstract interface IMQTTConnect
{
  public abstract void connectAndSubscribe();

  public abstract void disconnectFromBroker();

  public abstract boolean isAlreadyConnected();

  public abstract boolean publishTopicMessage(String paramString1, String paramString2);

  public abstract void resetReconnectionTimes();

  public abstract void scheduleNextPing();

  public abstract void subscribeSingleTopic(String paramString, int paramInt);

  public abstract void tryToReconnect();

  public abstract void unsubscribeSingleTopic(String paramString);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.interfaces.IMQTTConnect
 * JD-Core Version:    0.6.2
 */