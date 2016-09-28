package com.starcor.xul.Utils;

import java.io.InputStream;

public class XulPullParser
{
  public static final int CDSECT = 5;
  public static final int END_DOCUMENT = 1;
  public static final int END_TAG = 3;
  public static final int EVENT_ERROR = -1;
  public static final int START_DOCUMENT = 0;
  public static final int START_TAG = 2;
  public static final int TEXT = 4;
  static String[] _NAME_ID_MAP_ = { "", "action", "area", "attr", "background-color", "background-image", "binding", "bindingUpdated", "bindingFinished", "blur", "border", "check", "checked", "class", "click", "custom", "data", "desc", "direction", "event", "focus", "focusable", "font-align", "font-color", "font-shadow", "font-size", "font-style-italic", "font-style-underline", "font-weight", "grid", "group", "height", "id", "image", "img.0", "img.0.align", "img.0.auto_hide", "img.0.height", "img.0.mode", "img.0.padding", "img.0.round_rect", "img.0.shadow", "img.0.visible", "img.0.width", "img.1", "img.1.align", "img.1.auto_hide", "img.1.height", "img.1.mode", "img.1.padding", "img.1.round_rect", "img.1.shadow", "img.1.visible", "img.1.width", "img.2", "img.2.align", "img.2.auto_hide", "img.2.height", "img.2.mode", "img.2.padding", "img.2.round_rect", "img.2.shadow", "img.2.visible", "img.2.width", "img.3", "img.3.align", "img.3.auto_hide", "img.3.height", "img.3.mode", "img.3.padding", "img.3.round_rect", "img.3.shadow", "img.3.visible", "img.3.width", "item", "label", "layout", "load", "margin", "margin-bottom", "margin-left", "margin-right", "margin-top", "marquee", "name", "nofocus", "padding", "padding-bottom", "padding-left", "padding-right", "padding-top", "page", "priority", "radio", "ready", "script/javascript", "slider", "style", "template", "text", "type", "unchecked", "width", "x", "starcor.xul", "y", "z-index" };
  private long _parser = _load_xml(paramInputStream);

  static
  {
    System.loadLibrary("starcor_xul");
  }

  public XulPullParser(InputStream paramInputStream)
  {
  }

  private native int _get_attr_count(long paramLong);

  private native String _get_attr_name(long paramLong, int paramInt);

  private native int _get_attr_name_id(long paramLong, int paramInt);

  private native String _get_attr_value(long paramLong, int paramInt);

  private native String _get_attr_value(long paramLong, String paramString);

  private native int _get_attr_value_id(long paramLong, int paramInt);

  private native int _get_attr_value_id(long paramLong, String paramString);

  private native int _get_tag_id(long paramLong);

  private native String _get_tag_name(long paramLong);

  private native String _get_text(long paramLong);

  private native int _load_position(long paramLong1, long paramLong2);

  private native long _load_xml(InputStream paramInputStream);

  private native int _next_event(long paramLong);

  private native void _release(long paramLong);

  private native int _reset(long paramLong);

  private native long _store_position(long paramLong);

  protected void finalize()
    throws Throwable
  {
    _release(this._parser);
    super.finalize();
  }

  public int getAttributeCount()
  {
    return _get_attr_count(this._parser);
  }

  public String getAttributeName(int paramInt)
  {
    int i = _get_attr_name_id(this._parser, paramInt);
    if (i < 0)
      return _get_attr_name(this._parser, paramInt);
    return _NAME_ID_MAP_[i];
  }

  public String getAttributeValue(int paramInt)
  {
    int i = _get_attr_value_id(this._parser, paramInt);
    if (i < 0)
      return _get_attr_value(this._parser, paramInt);
    return _NAME_ID_MAP_[i];
  }

  public String getAttributeValue(String paramString1, String paramString2)
  {
    if (-1 < 0)
      return _get_attr_value(this._parser, paramString2);
    return _NAME_ID_MAP_[-1];
  }

  public String getName()
  {
    int i = _get_tag_id(this._parser);
    if (i < 0)
      return _get_tag_name(this._parser);
    return _NAME_ID_MAP_[i];
  }

  public String getText()
  {
    return _get_text(this._parser);
  }

  public int loadPosition(Object paramObject)
  {
    return _load_position(this._parser, ((Long)paramObject).longValue());
  }

  public int nextToken()
  {
    return _next_event(this._parser);
  }

  public Object storePosition()
  {
    return Long.valueOf(_store_position(this._parser));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulPullParser
 * JD-Core Version:    0.6.2
 */