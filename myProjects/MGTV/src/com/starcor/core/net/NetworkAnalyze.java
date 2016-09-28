package com.starcor.core.net;

import com.starcor.core.utils.Logger;
import java.util.ArrayList;

public class NetworkAnalyze
{
  private static final String TAG = "NetworkAnalyze";
  private static NetworkAnalyze networkAnalyzer;
  private NetworkAnalyzeListener analyzeListener = null;
  private NetSpeedTest currentSpeedTester;
  private int currentTesterId = 1;
  private ArrayList<NetSpeedTest> speedTesters;

  private NetworkAnalyze()
  {
    if (this.speedTesters == null)
    {
      this.speedTesters = new ArrayList();
      return;
    }
    this.speedTesters.clear();
  }

  public static NetworkAnalyze getInstance()
  {
    try
    {
      if (networkAnalyzer == null)
        networkAnalyzer = new NetworkAnalyze();
      NetworkAnalyze localNetworkAnalyze = networkAnalyzer;
      return localNetworkAnalyze;
    }
    finally
    {
    }
  }

  private void notifyAnalyzeState(int paramInt1, int paramInt2, int paramInt3, ArrayList<NetSpeedTest.DownloadSpeed> paramArrayList, ArrayList<NetSpeedTest.ScDownloadSpeed> paramArrayList1)
  {
    if (paramInt1 != this.currentTesterId)
      Logger.i("NetworkAnalyze", "notifyAnalyzeState(), id:" + paramInt1 + ", currentTesterId:" + this.currentTesterId);
    while (this.analyzeListener == null)
      return;
    this.analyzeListener.analyzeState(paramInt2, paramInt3, paramArrayList, paramArrayList1);
    if (paramArrayList == null)
      Logger.i("NetworkAnalyze", "notifyAnalyzeState(), networkPath:" + paramInt2 + ", analyzeState:" + paramInt3 + ", mgtvSpeeds:null");
    while (paramArrayList1 == null)
    {
      Logger.i("NetworkAnalyze", "notifyAnalyzeState(), networkPath:" + paramInt2 + ", analyzeState:" + paramInt3 + ", scSpeed:null");
      return;
      Logger.i("NetworkAnalyze", "notifyAnalyzeState(), networkPath:" + paramInt2 + ", analyzeState:" + paramInt3 + ", mgtvSpeeds.size:" + paramArrayList.size());
    }
    Logger.i("NetworkAnalyze", "notifyAnalyzeState(), networkPath:" + paramInt2 + ", analyzeState:" + paramInt3 + ", scSpeed.size:" + paramArrayList1.size());
  }

  private void notifyOptimizeTimeLength(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 != this.currentTesterId)
      Logger.i("NetworkAnalyze", "notifyAnalyzeState(), id:" + paramInt1 + ", currentTesterId:" + this.currentTesterId);
    while (this.analyzeListener == null)
      return;
    this.analyzeListener.optimizeTimeLength(paramInt2, paramInt3);
    Logger.i("NetworkAnalyze", "notifyAnalyzeState(), serverCount:" + paramInt2 + ", timeLength:" + paramInt3);
  }

  public void setNetworkAnalyzeListener(NetworkAnalyzeListener paramNetworkAnalyzeListener)
  {
    this.analyzeListener = paramNetworkAnalyzeListener;
  }

  public void startNetworkOptimize()
  {
    this.speedTesters.clear();
    this.currentTesterId = (1 + this.currentTesterId);
    this.currentSpeedTester = new NetSpeedTest();
    this.currentSpeedTester.setID(this.currentTesterId);
    this.currentSpeedTester.setNetSpeedTestListener(new NetSpeedTest.NetSpeedTestListener()
    {
      public void optimizeTimeLength(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        NetworkAnalyze.this.notifyOptimizeTimeLength(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
      }

      public void testState(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, ArrayList<NetSpeedTest.DownloadSpeed> paramAnonymousArrayList, ArrayList<NetSpeedTest.ScDownloadSpeed> paramAnonymousArrayList1)
      {
        NetworkAnalyze.this.notifyAnalyzeState(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousArrayList, paramAnonymousArrayList1);
      }
    });
    this.speedTesters.add(this.currentSpeedTester);
    this.currentSpeedTester.startNetworkOptimize();
  }

  public void startNetworkSpeedTest()
  {
    this.speedTesters.clear();
    this.currentTesterId = (1 + this.currentTesterId);
    this.currentSpeedTester = new NetSpeedTest();
    this.currentSpeedTester.setID(this.currentTesterId);
    this.currentSpeedTester.setNetSpeedTestListener(new NetSpeedTest.NetSpeedTestListener()
    {
      public void optimizeTimeLength(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        NetworkAnalyze.this.notifyOptimizeTimeLength(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
      }

      public void testState(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, ArrayList<NetSpeedTest.DownloadSpeed> paramAnonymousArrayList, ArrayList<NetSpeedTest.ScDownloadSpeed> paramAnonymousArrayList1)
      {
        NetworkAnalyze.this.notifyAnalyzeState(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousArrayList, paramAnonymousArrayList1);
      }
    });
    this.speedTesters.add(this.currentSpeedTester);
    this.currentSpeedTester.startNetworkSpeedTest();
  }

  public void stopNetworkOptimize()
  {
    if (this.currentSpeedTester == null)
      return;
    this.currentSpeedTester.stopNetworkSpeedTest();
  }

  public void stopNetworkSpeedTest()
  {
    if (this.currentSpeedTester == null)
      return;
    this.currentSpeedTester.stopNetworkSpeedTest();
  }

  public static abstract interface NetworkAnalyzeListener
  {
    public abstract void analyzeState(int paramInt1, int paramInt2, ArrayList<NetSpeedTest.DownloadSpeed> paramArrayList, ArrayList<NetSpeedTest.ScDownloadSpeed> paramArrayList1);

    public abstract void optimizeTimeLength(int paramInt1, int paramInt2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.net.NetworkAnalyze
 * JD-Core Version:    0.6.2
 */