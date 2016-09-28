package org.eclipse.paho.client.mqttv3.logging;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.MemoryHandler;

public class JSR47Logger
  implements Logger
{
  private String catalogID = null;
  private java.util.logging.Logger julLogger = null;
  private ResourceBundle logMessageCatalog = null;
  private String loggerName = null;
  private String resourceName = null;
  private ResourceBundle traceMessageCatalog = null;

  protected static void dumpMemoryTrace47(java.util.logging.Logger paramLogger)
  {
    if (paramLogger != null)
    {
      Handler[] arrayOfHandler = paramLogger.getHandlers();
      for (int i = 0; i < arrayOfHandler.length; i++)
        if ((arrayOfHandler[i] instanceof MemoryHandler))
          synchronized (arrayOfHandler[i])
          {
            ((MemoryHandler)arrayOfHandler[i]).push();
            return;
          }
      dumpMemoryTrace47(paramLogger.getParent());
    }
  }

  private String getResourceMessage(ResourceBundle paramResourceBundle, String paramString)
  {
    try
    {
      String str = paramResourceBundle.getString(paramString);
      return str;
    }
    catch (MissingResourceException localMissingResourceException)
    {
    }
    return paramString;
  }

  private void logToJsr47(Level paramLevel, String paramString1, String paramString2, String paramString3, ResourceBundle paramResourceBundle, String paramString4, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    String str = paramString4;
    if (paramString4.indexOf("=====") == -1)
      str = MessageFormat.format(getResourceMessage(paramResourceBundle, paramString4), paramArrayOfObject);
    LogRecord localLogRecord = new LogRecord(paramLevel, this.resourceName + ": " + str);
    localLogRecord.setSourceClassName(paramString1);
    localLogRecord.setSourceMethodName(paramString2);
    localLogRecord.setLoggerName(this.loggerName);
    if (paramThrowable != null)
      localLogRecord.setThrown(paramThrowable);
    this.julLogger.log(localLogRecord);
  }

  private Level mapJULLevel(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return null;
    case 1:
      return Level.SEVERE;
    case 2:
      return Level.WARNING;
    case 3:
      return Level.INFO;
    case 4:
      return Level.CONFIG;
    case 5:
      return Level.FINE;
    case 6:
      return Level.FINER;
    case 7:
    }
    return Level.FINEST;
  }

  public void config(String paramString1, String paramString2, String paramString3)
  {
    log(4, paramString1, paramString2, paramString3, null, null);
  }

  public void config(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    log(4, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }

  public void config(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    log(4, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }

  public void dumpTrace()
  {
    dumpMemoryTrace47(this.julLogger);
  }

  public void fine(String paramString1, String paramString2, String paramString3)
  {
    trace(5, paramString1, paramString2, paramString3, null, null);
  }

  public void fine(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    trace(5, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }

  public void fine(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    trace(5, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }

  public void finer(String paramString1, String paramString2, String paramString3)
  {
    trace(6, paramString1, paramString2, paramString3, null, null);
  }

  public void finer(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    trace(6, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }

  public void finer(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    trace(6, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }

  public void finest(String paramString1, String paramString2, String paramString3)
  {
    trace(7, paramString1, paramString2, paramString3, null, null);
  }

  public void finest(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    trace(7, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }

  public void finest(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    trace(7, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }

  public String formatMessage(String paramString, Object[] paramArrayOfObject)
  {
    try
    {
      String str = this.logMessageCatalog.getString(paramString);
      return str;
    }
    catch (MissingResourceException localMissingResourceException)
    {
    }
    return paramString;
  }

  public void info(String paramString1, String paramString2, String paramString3)
  {
    log(3, paramString1, paramString2, paramString3, null, null);
  }

  public void info(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    log(3, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }

  public void info(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    log(3, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }

  public void initialise(ResourceBundle paramResourceBundle, String paramString1, String paramString2)
  {
    this.traceMessageCatalog = this.logMessageCatalog;
    this.resourceName = paramString2;
    this.loggerName = paramString1;
    this.julLogger = java.util.logging.Logger.getLogger(this.loggerName);
    this.logMessageCatalog = paramResourceBundle;
    this.traceMessageCatalog = paramResourceBundle;
    this.catalogID = this.logMessageCatalog.getString("0");
  }

  public boolean isLoggable(int paramInt)
  {
    return this.julLogger.isLoggable(mapJULLevel(paramInt));
  }

  public void log(int paramInt, String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    Level localLevel = mapJULLevel(paramInt);
    if (this.julLogger.isLoggable(localLevel))
      logToJsr47(localLevel, paramString1, paramString2, this.catalogID, this.logMessageCatalog, paramString3, paramArrayOfObject, paramThrowable);
  }

  public void setResourceName(String paramString)
  {
    this.resourceName = paramString;
  }

  public void severe(String paramString1, String paramString2, String paramString3)
  {
    log(1, paramString1, paramString2, paramString3, null, null);
  }

  public void severe(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    log(1, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }

  public void severe(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    log(1, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }

  public void trace(int paramInt, String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    Level localLevel = mapJULLevel(paramInt);
    if (this.julLogger.isLoggable(localLevel))
      logToJsr47(localLevel, paramString1, paramString2, this.catalogID, this.traceMessageCatalog, paramString3, paramArrayOfObject, paramThrowable);
  }

  public void warning(String paramString1, String paramString2, String paramString3)
  {
    log(2, paramString1, paramString2, paramString3, null, null);
  }

  public void warning(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    log(2, paramString1, paramString2, paramString3, paramArrayOfObject, null);
  }

  public void warning(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject, Throwable paramThrowable)
  {
    log(2, paramString1, paramString2, paramString3, paramArrayOfObject, paramThrowable);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.logging.JSR47Logger
 * JD-Core Version:    0.6.2
 */