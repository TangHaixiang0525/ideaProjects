package com.starcor.bussines.data.type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.starcor.utils.Logger;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

public class JsonDataType
{
  private String TAG = JsonDataType.class.getName();
  public String action;
  public List<KeyToValue> mArgList;

  private DataInfo parseJsonToInfo(String paramString)
  {
    Gson localGson = new Gson();
    Type localType = new TypeToken()
    {
    }
    .getType();
    try
    {
      DataInfo localDataInfo = (DataInfo)localGson.fromJson(paramString, localType);
      return localDataInfo;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.e(this.TAG, "gson parse error!");
    }
    return null;
  }

  public String getArgsValue(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0))
    {
      Logger.d(this.TAG, "key is  null!");
      return null;
    }
    if ((this.mArgList == null) || (this.mArgList.size() <= 0))
    {
      Logger.d(this.TAG, "mArgList  is  empty!");
      return null;
    }
    Iterator localIterator = this.mArgList.iterator();
    KeyToValue localKeyToValue;
    do
    {
      if (!localIterator.hasNext())
      {
        Logger.d(this.TAG, "arg_list  no key equals!");
        return null;
      }
      localKeyToValue = (KeyToValue)localIterator.next();
    }
    while (!paramString.equals(localKeyToValue.k));
    return localKeyToValue.v;
  }

  public boolean parseData(String paramString)
  {
    DataInfo localDataInfo = parseJsonToInfo(paramString);
    if (localDataInfo.data == null)
    {
      Logger.d(this.TAG, "data  is  empty!");
      return false;
    }
    this.action = localDataInfo.data.action;
    this.mArgList = localDataInfo.data.arg_list;
    return true;
  }

  public class Data
  {
    public String action;
    public List<JsonDataType.KeyToValue> arg_list;

    public Data()
    {
    }
  }

  public class DataInfo
  {
    public JsonDataType.Data data;

    public DataInfo()
    {
    }
  }

  public class KeyToValue
  {
    public String k;
    public String v;

    public KeyToValue()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.bussines.data.type.JsonDataType
 * JD-Core Version:    0.6.2
 */