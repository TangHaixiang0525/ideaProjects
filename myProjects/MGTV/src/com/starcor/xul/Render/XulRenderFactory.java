package com.starcor.xul.Render;

import android.text.TextUtils;
import com.starcor.xul.Utils.XulCachedHashMap;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulView;

public final class XulRenderFactory
{
  private static XulCachedHashMap<String, RenderBuilder> _builderMap = new XulCachedHashMap();

  static
  {
    XulAreaRender.register();
    XulCustomViewRender.register();
    XulGridAreaRender.register();
    XulImageRender.register();
    XulItemRender.register();
    XulLabelRender.register();
    XulResolutionLabelRender.register();
    XulSpannedLabelRender.register();
    XulPageSliderAreaRender.register();
    XulSliderAreaRender.register();
    XulGroupRender.register();
    XulLayerRender.register();
    XulComponentRender.register();
    XulMassiveRender.register();
  }

  public static XulViewRender createRender(String paramString1, String paramString2, XulRenderContext paramXulRenderContext, XulView paramXulView, boolean paramBoolean)
  {
    if (TextUtils.isEmpty(paramString2))
      paramString2 = "*";
    String str1 = paramString1 + "." + paramString2;
    RenderBuilder localRenderBuilder = (RenderBuilder)_builderMap.get(str1);
    if (localRenderBuilder == null)
    {
      String str2 = paramString1 + ".*";
      localRenderBuilder = (RenderBuilder)_builderMap.get(str2);
    }
    if (localRenderBuilder == null)
      return null;
    XulViewRender localXulViewRender = localRenderBuilder.createRender(paramXulRenderContext, paramXulView);
    localXulViewRender.setPreload(paramBoolean, paramBoolean);
    localXulViewRender.setUpdateAll();
    localXulViewRender.markDirtyView();
    return localXulViewRender;
  }

  public static void registerBuilder(String paramString1, String paramString2, RenderBuilder paramRenderBuilder)
  {
    String str = paramString1 + "." + paramString2;
    _builderMap.put(str, paramRenderBuilder);
  }

  public static abstract class RenderBuilder
  {
    protected abstract XulViewRender createRender(XulRenderContext paramXulRenderContext, XulView paramXulView);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulRenderFactory
 * JD-Core Version:    0.6.2
 */