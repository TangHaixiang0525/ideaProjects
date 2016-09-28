package com.starcor.media.player;

import android.content.Context;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.report.enums.BufferEnum;

public abstract class MplayerDataReporter
{
  MplayerDataCollector collector;

  public abstract void onAddPlayTask2Play(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt);

  public abstract void onError(String paramString);

  public abstract void onPlayerBufferBegin(BufferEnum paramBufferEnum, long paramLong);

  public abstract void onPlayerBufferEnd(long paramLong);

  public abstract void onPlayerCreate(String paramString);

  public abstract void onPlayerFirstStart(String paramString1, int paramInt, long paramLong1, long paramLong2, long paramLong3, String paramString2, long paramLong4, PlayerIntentParams paramPlayerIntentParams);

  public abstract void onPlayerPause(long paramLong);

  public abstract void onPlayerSeekBegin(long paramLong);

  public abstract void onPlayerSeekEnd(long paramLong);

  public abstract void onPlayerStart(long paramLong);

  public abstract void onPlayerStop(long paramLong);

  public void onStartCollector(MplayerDataCollector paramMplayerDataCollector)
  {
    this.collector = paramMplayerDataCollector;
  }

  public abstract void release();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerDataReporter
 * JD-Core Version:    0.6.2
 */