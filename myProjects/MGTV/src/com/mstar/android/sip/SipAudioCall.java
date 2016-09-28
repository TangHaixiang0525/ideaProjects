package com.mstar.android.sip;

import android.content.Context;
import android.os.Message;
import com.mstar.android.rtp.AudioGroup;
import com.mstar.android.rtp.AudioStream;

public class SipAudioCall
{
  public SipAudioCall(Context paramContext, SipProfile paramSipProfile)
  {
  }

  public void answerCall(int paramInt)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public void attachCall(SipSession paramSipSession, String paramString)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public void close()
  {
    throw new RuntimeException("stub");
  }

  public void continueCall(int paramInt)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public void endCall()
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public AudioGroup getAudioGroup()
  {
    throw new RuntimeException("stub");
  }

  public AudioStream getAudioStream()
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

  public SipSession getSipSession()
  {
    throw new RuntimeException("stub");
  }

  public int getState()
  {
    throw new RuntimeException("stub");
  }

  public void holdCall(int paramInt)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public boolean isInCall()
  {
    throw new RuntimeException("stub");
  }

  public boolean isMuted()
  {
    throw new RuntimeException("stub");
  }

  public boolean isOnHold()
  {
    throw new RuntimeException("stub");
  }

  public void makeCall(SipProfile paramSipProfile, SipSession paramSipSession, int paramInt)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public void sendDtmf(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public void sendDtmf(int paramInt, Message paramMessage)
  {
    throw new RuntimeException("stub");
  }

  public void setAudioGroup(AudioGroup paramAudioGroup)
  {
    throw new RuntimeException("stub");
  }

  public void setListener(Listener paramListener)
  {
    throw new RuntimeException("stub");
  }

  public void setListener(Listener paramListener, boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public void setSpeakerMode(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public void startAudio()
  {
    throw new RuntimeException("stub");
  }

  public void toggleMute()
  {
    throw new RuntimeException("stub");
  }

  public static class Listener
  {
    public void onCallBusy(SipAudioCall paramSipAudioCall)
    {
      throw new RuntimeException("stub");
    }

    public void onCallEnded(SipAudioCall paramSipAudioCall)
    {
      throw new RuntimeException("stub");
    }

    public void onCallEstablished(SipAudioCall paramSipAudioCall)
    {
      throw new RuntimeException("stub");
    }

    public void onCallHeld(SipAudioCall paramSipAudioCall)
    {
      throw new RuntimeException("stub");
    }

    public void onCalling(SipAudioCall paramSipAudioCall)
    {
      throw new RuntimeException("stub");
    }

    public void onChanged(SipAudioCall paramSipAudioCall)
    {
      throw new RuntimeException("stub");
    }

    public void onError(SipAudioCall paramSipAudioCall, int paramInt, String paramString)
    {
      throw new RuntimeException("stub");
    }

    public void onReadyToCall(SipAudioCall paramSipAudioCall)
    {
      throw new RuntimeException("stub");
    }

    public void onRinging(SipAudioCall paramSipAudioCall, SipProfile paramSipProfile)
    {
      throw new RuntimeException("stub");
    }

    public void onRingingBack(SipAudioCall paramSipAudioCall)
    {
      throw new RuntimeException("stub");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.sip.SipAudioCall
 * JD-Core Version:    0.6.2
 */