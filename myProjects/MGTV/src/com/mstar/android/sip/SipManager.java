package com.mstar.android.sip;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class SipManager
{
  public static final String ACTION_SIP_ADD_PHONE = "com.android.phone.SIP_ADD_PHONE";
  public static final String ACTION_SIP_INCOMING_CALL = "com.android.phone.SIP_INCOMING_CALL";
  public static final String ACTION_SIP_REMOVE_PHONE = "com.android.phone.SIP_REMOVE_PHONE";
  public static final String ACTION_SIP_SERVICE_UP = "android.net.sip.SIP_SERVICE_UP";
  public static final String EXTRA_CALL_ID = "android:sipCallID";
  public static final String EXTRA_LOCAL_URI = "android:localSipUri";
  public static final String EXTRA_OFFER_SD = "android:sipOfferSD";
  public static final int INCOMING_CALL_RESULT_CODE = 101;

  public static Intent createIncomingCallBroadcast(String paramString1, String paramString2)
  {
    throw new RuntimeException("stub");
  }

  public static String getCallId(Intent paramIntent)
  {
    throw new RuntimeException("stub");
  }

  public static String getOfferSessionDescription(Intent paramIntent)
  {
    throw new RuntimeException("stub");
  }

  public static boolean isApiSupported(Context paramContext)
  {
    throw new RuntimeException("stub");
  }

  public static boolean isIncomingCallIntent(Intent paramIntent)
  {
    throw new RuntimeException("stub");
  }

  public static boolean isSipWifiOnly(Context paramContext)
  {
    throw new RuntimeException("stub");
  }

  public static boolean isVoipSupported(Context paramContext)
  {
    throw new RuntimeException("stub");
  }

  public static SipManager newInstance(Context paramContext)
  {
    throw new RuntimeException("stub");
  }

  public void close(String paramString)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public SipSession createSipSession(SipProfile paramSipProfile, SipSession.Listener paramListener)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public SipProfile[] getListOfProfiles()
  {
    throw new RuntimeException("stub");
  }

  public SipSession getSessionFor(Intent paramIntent)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public boolean isOpened(String paramString)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public boolean isRegistered(String paramString)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public SipAudioCall makeAudioCall(SipProfile paramSipProfile1, SipProfile paramSipProfile2, SipAudioCall.Listener paramListener, int paramInt)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public SipAudioCall makeAudioCall(String paramString1, String paramString2, SipAudioCall.Listener paramListener, int paramInt)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public void open(SipProfile paramSipProfile)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public void open(SipProfile paramSipProfile, PendingIntent paramPendingIntent, SipRegistrationListener paramSipRegistrationListener)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public void register(SipProfile paramSipProfile, int paramInt, SipRegistrationListener paramSipRegistrationListener)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public void setRegistrationListener(String paramString, SipRegistrationListener paramSipRegistrationListener)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public SipAudioCall takeAudioCall(Intent paramIntent, SipAudioCall.Listener paramListener)
    throws SipException
  {
    throw new RuntimeException("stub");
  }

  public void unregister(SipProfile paramSipProfile, SipRegistrationListener paramSipRegistrationListener)
    throws SipException
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.sip.SipManager
 * JD-Core Version:    0.6.2
 */