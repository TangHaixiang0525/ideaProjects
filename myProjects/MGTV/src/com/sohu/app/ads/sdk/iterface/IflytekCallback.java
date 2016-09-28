package com.sohu.app.ads.sdk.iterface;

import com.sohu.app.ads.sdk.model.emu.VoiceStatus;

public abstract interface IflytekCallback
{
  public abstract void onStatus(VoiceStatus paramVoiceStatus, String paramString);

  public abstract void onVoice(int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.iterface.IflytekCallback
 * JD-Core Version:    0.6.2
 */