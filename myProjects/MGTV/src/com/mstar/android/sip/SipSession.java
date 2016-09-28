package com.mstar.android.sip;

public final class SipSession
{
  public void answerCall(String paramString, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public void changeCall(String paramString, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public void endCall()
  {
    throw new RuntimeException("stub");
  }

  public String getCallId()
  {
    throw new RuntimeException("stub");
  }

  public String getLocalIp()
  {
    throw new RuntimeException("stub");
  }

  public SipProfile getLocalProfile()
  {
    throw new RuntimeException("stub");
  }

  public SipProfile getPeerProfile()
  {
    throw new RuntimeException("stub");
  }

  public int getState()
  {
    throw new RuntimeException("stub");
  }

  public boolean isInCall()
  {
    throw new RuntimeException("stub");
  }

  public void makeCall(SipProfile paramSipProfile, String paramString, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public void register(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public void setListener(Listener paramListener)
  {
    throw new RuntimeException("stub");
  }

  public void unregister()
  {
    throw new RuntimeException("stub");
  }

  public static class Listener
  {
    public void onCallBusy(SipSession paramSipSession)
    {
      throw new RuntimeException("stub");
    }

    public void onCallChangeFailed(SipSession paramSipSession, int paramInt, String paramString)
    {
      throw new RuntimeException("stub");
    }

    public void onCallEnded(SipSession paramSipSession)
    {
      throw new RuntimeException("stub");
    }

    public void onCallEstablished(SipSession paramSipSession, String paramString)
    {
      throw new RuntimeException("stub");
    }

    public void onCallTransferring(SipSession paramSipSession, String paramString)
    {
      throw new RuntimeException("stub");
    }

    public void onCalling(SipSession paramSipSession)
    {
      throw new RuntimeException("stub");
    }

    public void onError(SipSession paramSipSession, int paramInt, String paramString)
    {
      throw new RuntimeException("stub");
    }

    public void onRegistering(SipSession paramSipSession)
    {
      throw new RuntimeException("stub");
    }

    public void onRegistrationDone(SipSession paramSipSession, int paramInt)
    {
      throw new RuntimeException("stub");
    }

    public void onRegistrationFailed(SipSession paramSipSession, int paramInt, String paramString)
    {
      throw new RuntimeException("stub");
    }

    public void onRegistrationTimeout(SipSession paramSipSession)
    {
      throw new RuntimeException("stub");
    }

    public void onRinging(SipSession paramSipSession, SipProfile paramSipProfile, String paramString)
    {
      throw new RuntimeException("stub");
    }

    public void onRingingBack(SipSession paramSipSession)
    {
      throw new RuntimeException("stub");
    }
  }

  public static class State
  {
    public static final int DEREGISTERING = 2;
    public static final int ENDING_CALL = 10;
    public static final int INCOMING_CALL = 3;
    public static final int INCOMING_CALL_ANSWERING = 4;
    public static final int IN_CALL = 8;
    public static final int NOT_DEFINED = 101;
    public static final int OUTGOING_CALL = 5;
    public static final int OUTGOING_CALL_CANCELING = 7;
    public static final int OUTGOING_CALL_RING_BACK = 6;
    public static final int PINGING = 9;
    public static final int READY_TO_CALL = 0;
    public static final int REGISTERING = 1;

    public static String toString(int paramInt)
    {
      throw new RuntimeException("stub");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.sip.SipSession
 * JD-Core Version:    0.6.2
 */