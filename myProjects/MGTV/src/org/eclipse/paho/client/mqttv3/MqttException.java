package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.MessageCatalog;

public class MqttException extends Exception
{
  public static final short REASON_CODE_BROKER_UNAVAILABLE = 3;
  public static final short REASON_CODE_CLIENTID_NOT_EXIST = 6;
  public static final short REASON_CODE_CLIENTID_OUT_OF_DATE = 7;
  public static final short REASON_CODE_CLIENT_ALREADY_DISCONNECTED = 32101;
  public static final short REASON_CODE_CLIENT_CLOSED = 32111;
  public static final short REASON_CODE_CLIENT_CONNECTED = 32100;
  public static final short REASON_CODE_CLIENT_DISCONNECTING = 32102;
  public static final short REASON_CODE_CLIENT_DISCONNECT_PROHIBITED = 32107;
  public static final short REASON_CODE_CLIENT_EXCEPTION = 0;
  public static final short REASON_CODE_CLIENT_NOT_CONNECTED = 32104;
  public static final short REASON_CODE_CLIENT_TIMEOUT = 32000;
  public static final short REASON_CODE_CONNECTION_LOST = 32109;
  public static final short REASON_CODE_CONNECT_IN_PROGRESS = 32110;
  public static final short REASON_CODE_FAILED_AUTHENTICATION = 4;
  public static final short REASON_CODE_INVALID_CLIENT_ID = 2;
  public static final short REASON_CODE_INVALID_MESSAGE = 32108;
  public static final short REASON_CODE_INVALID_PROTOCOL_VERSION = 1;
  public static final short REASON_CODE_MAX_INFLIGHT = 32202;
  public static final short REASON_CODE_NOT_AUTHORIZED = 5;
  public static final short REASON_CODE_NO_MESSAGE_IDS_AVAILABLE = 32001;
  public static final short REASON_CODE_SERVER_CONNECT_ERROR = 32103;
  public static final short REASON_CODE_SOCKET_FACTORY_MISMATCH = 32105;
  public static final short REASON_CODE_SSL_CONFIG_ERROR = 32106;
  public static final short REASON_CODE_TOKEN_INUSE = 32201;
  public static final short REASON_CODE_UNEXPECTED_ERROR = 8;
  private static final long serialVersionUID = 300L;
  private Throwable cause;
  private int reasonCode;

  public MqttException(int paramInt)
  {
    this.reasonCode = paramInt;
  }

  public MqttException(int paramInt, Throwable paramThrowable)
  {
    this.reasonCode = paramInt;
    this.cause = paramThrowable;
  }

  public MqttException(Throwable paramThrowable)
  {
    this.reasonCode = 0;
    this.cause = paramThrowable;
  }

  public Throwable getCause()
  {
    return this.cause;
  }

  public String getMessage()
  {
    return MessageCatalog.getMessage(this.reasonCode);
  }

  public int getReasonCode()
  {
    return this.reasonCode;
  }

  public String toString()
  {
    String str = getMessage() + " (" + this.reasonCode + ")";
    if (this.cause != null)
      str = str + " - " + this.cause.toString();
    return str;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.MqttException
 * JD-Core Version:    0.6.2
 */