package com.starcor.bussines.manager;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.starcor.common.IntentDataManager;

public abstract class BussinesData extends BussinesDataModel
{
  protected static final String TAG = BussinesData.class.getSimpleName() + "_special";
  private Activity activity;
  Bussines changeBussines;
  private Context context;
  private int dataType;
  private int initForType;
  private Intent intent;
  boolean needChange;
  private BroadcastReceiver receiver;
  private Service service;

  private void innerStart()
  {
    if ((this.intent != null) && (this.manager == null))
    {
      setDataManager(new IntentDataManager(this.intent));
      setType(1);
      processData();
    }
  }

  public void cloneData(BussinesData paramBussinesData)
  {
    super.cloneData(paramBussinesData);
    this.activity = paramBussinesData.activity;
    this.service = paramBussinesData.service;
    this.receiver = paramBussinesData.receiver;
    this.context = paramBussinesData.context;
    this.intent = paramBussinesData.intent;
    this.initForType = paramBussinesData.initForType;
    this.dataType = paramBussinesData.dataType;
    this.changeBussines = paramBussinesData.changeBussines;
    this.needChange = paramBussinesData.needChange;
  }

  public Activity getActivity()
  {
    return this.activity;
  }

  public Context getContext()
  {
    return this.context;
  }

  public int getDataType()
  {
    return this.dataType;
  }

  public int getInitForType()
  {
    return this.initForType;
  }

  public Intent getIntent()
  {
    return this.intent;
  }

  public Bundle getOriginalData()
  {
    if (hasInit())
      return (Bundle)getData();
    return null;
  }

  public BroadcastReceiver getReceiver()
  {
    return this.receiver;
  }

  public Service getService()
  {
    return this.service;
  }

  protected abstract void preProcessData();

  public void processData()
  {
    preProcessData();
    processWithData();
  }

  protected abstract void processWithData();

  public void setActivity(Activity paramActivity)
  {
    this.activity = paramActivity;
    setContext(paramActivity.getApplicationContext());
  }

  public void setContext(Context paramContext)
  {
    this.context = paramContext;
  }

  public void setDataType(int paramInt)
  {
    this.dataType = paramInt;
  }

  public void setInitForType(int paramInt)
  {
    this.initForType = paramInt;
  }

  public void setIntent(Intent paramIntent)
  {
    this.intent = paramIntent;
  }

  public void setReceiver(BroadcastReceiver paramBroadcastReceiver)
  {
    this.receiver = paramBroadcastReceiver;
  }

  public void setService(Service paramService)
  {
    this.service = paramService;
    setContext(paramService.getApplicationContext());
  }

  public void start()
  {
    innerStart();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.bussines.manager.BussinesData
 * JD-Core Version:    0.6.2
 */