package com.starcor.xul.Wrapper;

import android.graphics.Rect;
import com.starcor.xul.Render.XulImageRender;
import com.starcor.xul.XulView;

public class XulImageItemWrapper extends XulViewWrapper
{
  XulImageItemWrapper(XulView paramXulView)
  {
    super(paramXulView);
  }

  public static XulImageItemWrapper fromXulView(XulView paramXulView)
  {
    if (paramXulView == null);
    while (!(paramXulView.getRender() instanceof XulImageRender))
      return null;
    return new XulImageItemWrapper(paramXulView);
  }

  public int getImageHeight(int paramInt)
  {
    XulImageRender localXulImageRender = (XulImageRender)this._view.getRender();
    if (localXulImageRender == null)
      return -1;
    return localXulImageRender.getImageHeight(paramInt);
  }

  public float getImageOpacity(int paramInt)
  {
    XulImageRender localXulImageRender = (XulImageRender)this._view.getRender();
    if (localXulImageRender == null)
      return -1.0F;
    return localXulImageRender.getImageOpacity(paramInt);
  }

  public Rect getImagePadding(int paramInt)
  {
    XulImageRender localXulImageRender = (XulImageRender)this._view.getRender();
    if (localXulImageRender == null)
      return null;
    return localXulImageRender.getImagePadding(paramInt);
  }

  public String getImageResolvedUrl(int paramInt)
  {
    XulImageRender localXulImageRender = (XulImageRender)this._view.getRender();
    if (localXulImageRender == null)
      return null;
    return localXulImageRender.getImageResolvedUrl(paramInt);
  }

  public String getImageUrl(int paramInt)
  {
    XulImageRender localXulImageRender = (XulImageRender)this._view.getRender();
    if (localXulImageRender == null)
      return null;
    return localXulImageRender.getImageUrl(paramInt);
  }

  public int getImageWidth(int paramInt)
  {
    XulImageRender localXulImageRender = (XulImageRender)this._view.getRender();
    if (localXulImageRender == null)
      return -1;
    return localXulImageRender.getImageWidth(paramInt);
  }

  public boolean hasImageLayer(int paramInt)
  {
    XulImageRender localXulImageRender = (XulImageRender)this._view.getRender();
    if (localXulImageRender == null)
      return false;
    return localXulImageRender.hasImageLayer(paramInt);
  }

  public boolean isImageLoaded(int paramInt)
  {
    XulImageRender localXulImageRender = (XulImageRender)this._view.getRender();
    if (localXulImageRender == null)
      return false;
    return localXulImageRender.isImageLoaded(paramInt);
  }

  public boolean isImageVisible(int paramInt)
  {
    XulImageRender localXulImageRender = (XulImageRender)this._view.getRender();
    if (localXulImageRender == null)
      return false;
    return localXulImageRender.isImageVisible(paramInt);
  }

  public boolean reloadImage(int paramInt)
  {
    XulImageRender localXulImageRender = (XulImageRender)this._view.getRender();
    if (localXulImageRender == null)
      return false;
    return localXulImageRender.reloadImage(paramInt);
  }

  public boolean resetAnimation(int paramInt)
  {
    XulImageRender localXulImageRender = (XulImageRender)this._view.getRender();
    if (localXulImageRender == null)
      return false;
    return localXulImageRender.resetAnimation(paramInt);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Wrapper.XulImageItemWrapper
 * JD-Core Version:    0.6.2
 */