package com.starcor.jump.bussines;

import android.text.TextUtils;
import com.starcor.jump.bussines.data.PageControlData;

public class PageControlBussines extends CommonBussines
{
  private static final String TAG = PageControlBussines.class.getSimpleName();

  protected void commonStart()
  {
    setData(new PageControlData());
  }

  protected void startFromActivity()
  {
  }

  protected void startFromReciever()
  {
    PageControlData localPageControlData = (PageControlData)this._data;
    if (!TextUtils.isEmpty(localPageControlData.id))
    {
      Integer.parseInt(localPageControlData.id);
      setAction("com.starcor.hunan.logic_event.ctrl_in_current");
      setExcuteType(1);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.PageControlBussines
 * JD-Core Version:    0.6.2
 */