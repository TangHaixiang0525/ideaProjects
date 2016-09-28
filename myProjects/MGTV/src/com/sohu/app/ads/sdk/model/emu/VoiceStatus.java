package com.sohu.app.ads.sdk.model.emu;

public enum VoiceStatus
{
  static
  {
    END = new VoiceStatus("END", 1);
    ERROR = new VoiceStatus("ERROR", 2);
    SUCESS = new VoiceStatus("SUCESS", 3);
    FAILED = new VoiceStatus("FAILED", 4);
    VoiceStatus[] arrayOfVoiceStatus = new VoiceStatus[5];
    arrayOfVoiceStatus[0] = START;
    arrayOfVoiceStatus[1] = END;
    arrayOfVoiceStatus[2] = ERROR;
    arrayOfVoiceStatus[3] = SUCESS;
    arrayOfVoiceStatus[4] = FAILED;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.model.emu.VoiceStatus
 * JD-Core Version:    0.6.2
 */