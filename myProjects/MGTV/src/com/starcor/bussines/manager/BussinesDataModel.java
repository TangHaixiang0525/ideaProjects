package com.starcor.bussines.manager;

import com.starcor.common.IntentDataManager;

public abstract class BussinesDataModel
{
  protected static final int TYPE_GET_DATA_OUT = 1;
  protected static final int TYPE_INSERT_DATA_IN = 2;
  private int mType;
  public IntentDataManager manager;

  public void cloneData(BussinesDataModel paramBussinesDataModel)
  {
    this.mType = paramBussinesDataModel.mType;
    this.manager = paramBussinesDataModel.manager;
  }

  public Object getData()
  {
    return this.manager.getData();
  }

  public boolean hasInit()
  {
    return this.manager != null;
  }

  protected String processAction(String paramString)
  {
    switch (this.mType)
    {
    default:
      return paramString;
    case 1:
      return this.manager.getAction();
    case 2:
    }
    this.manager.setAction(paramString);
    return paramString;
  }

  protected boolean processBooleanValue(String paramString, boolean paramBoolean)
  {
    return processBooleanValue(paramString, paramBoolean, false);
  }

  protected boolean processBooleanValue(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    switch (this.mType)
    {
    default:
      return paramBoolean1;
    case 1:
      return this.manager.getBooleanValue(paramString, paramBoolean2);
    case 2:
    }
    this.manager.putBooleanValue(paramString, paramBoolean1);
    return paramBoolean1;
  }

  protected abstract void processData();

  protected int processFlags(int paramInt)
  {
    switch (this.mType)
    {
    default:
      return paramInt;
    case 1:
      return this.manager.getFlags();
    case 2:
    }
    this.manager.setFlags(paramInt);
    return paramInt;
  }

  protected int processIntValue(String paramString, int paramInt)
  {
    return processIntValue(paramString, paramInt, 0);
  }

  protected int processIntValue(String paramString, int paramInt1, int paramInt2)
  {
    switch (this.mType)
    {
    default:
      return paramInt1;
    case 1:
      return this.manager.getIntValue(paramString, paramInt2);
    case 2:
    }
    this.manager.putIntValue(paramString, paramInt1);
    return paramInt1;
  }

  protected String processStringValue(String paramString1, String paramString2)
  {
    return processStringValue(paramString1, paramString2, "");
  }

  protected String processStringValue(String paramString1, String paramString2, String paramString3)
  {
    switch (this.mType)
    {
    default:
      return paramString2;
    case 1:
      return this.manager.getStringValue(paramString1, paramString3);
    case 2:
    }
    this.manager.putStringValue(paramString1, paramString2);
    return paramString2;
  }

  protected void setDataManager(IntentDataManager paramIntentDataManager)
  {
    this.manager = paramIntentDataManager;
  }

  protected void setType(int paramInt)
  {
    this.mType = paramInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.bussines.manager.BussinesDataModel
 * JD-Core Version:    0.6.2
 */