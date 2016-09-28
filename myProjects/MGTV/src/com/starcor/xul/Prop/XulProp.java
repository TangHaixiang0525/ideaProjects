package com.starcor.xul.Prop;

import android.text.TextUtils;
import com.starcor.xul.XulUtils;

public class XulProp
{
  String _binding;
  private XulBinding _bindingSource;
  String _desc;
  int _nameId = -1;
  boolean _pending = true;
  int _priority = 0;
  boolean _referent = true;
  Object _value;

  XulProp()
  {
  }

  XulProp(XulProp paramXulProp)
  {
    this._nameId = paramXulProp._nameId;
    this._value = paramXulProp._value;
    this._binding = paramXulProp._binding;
    this._desc = paramXulProp._desc;
    this._pending = paramXulProp._pending;
    this._priority = paramXulProp._priority;
    this._bindingSource = paramXulProp._bindingSource;
    this._referent = false;
  }

  public String getBinding()
  {
    return this._binding;
  }

  public XulBinding getBindingSource()
  {
    return this._bindingSource;
  }

  public String getName()
  {
    return XulPropNameCache.id2Name(this._nameId);
  }

  public int getNameId()
  {
    return this._nameId;
  }

  public int getPriority()
  {
    return this._priority;
  }

  public String getStringValue()
  {
    if (this._value == null)
      return "";
    return String.valueOf(this._value);
  }

  public Object getValue()
  {
    return this._value;
  }

  public boolean isBinding()
  {
    return !TextUtils.isEmpty(this._binding);
  }

  public boolean isBindingPending()
  {
    if (this._bindingSource != null)
      return this._bindingSource.isUpdated();
    return (isBinding()) && (this._pending);
  }

  public boolean isReferent()
  {
    return this._referent;
  }

  public XulProp makeClone()
  {
    return this;
  }

  public void setBinding(String paramString)
  {
    this._binding = XulUtils.getCachedString(paramString);
  }

  public void setBindingReady()
  {
    this._pending = false;
  }

  public void setBindingSource(XulBinding paramXulBinding)
  {
    this._bindingSource = paramXulBinding;
    if (this._bindingSource == null)
      this._pending = true;
  }

  public void setPriority(int paramInt)
  {
    this._priority = paramInt;
  }

  public void setValue(Object paramObject)
  {
    if ((paramObject instanceof String))
    {
      this._value = XulUtils.getCachedString((String)paramObject);
      return;
    }
    this._value = paramObject;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Prop.XulProp
 * JD-Core Version:    0.6.2
 */