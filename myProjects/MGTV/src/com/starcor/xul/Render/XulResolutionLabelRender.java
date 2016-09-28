package com.starcor.xul.Render;

import android.text.TextUtils;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulPropNameCache.TagId;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulView;
import java.util.StringTokenizer;

public class XulResolutionLabelRender extends XulLabelRender
{
  private static final String TAG = XulResolutionLabelRender.class.getSimpleName();

  public XulResolutionLabelRender(XulRenderContext paramXulRenderContext, XulView paramXulView)
  {
    super(paramXulRenderContext, paramXulView);
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("item", "resolution_label", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulResolutionLabelRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulItem));
        return new XulResolutionLabelRender(paramAnonymousXulRenderContext, paramAnonymousXulView);
      }
    });
  }

  protected void syncTextInfo(boolean paramBoolean)
  {
    String str1 = this._view.getAttr("resolutionText").getStringValue().replaceAll("recom", "");
    String str2 = "";
    if (!TextUtils.isEmpty(str1))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(str1, "|");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str3 = GlobalLogic.getInstance().getVideoQualityValue(localStringTokenizer.nextToken());
        if (!TextUtils.isEmpty(str3))
          str2 = str2 + str3 + "|";
      }
      if ((str2.length() > 0) && (str2.lastIndexOf("|") == -1 + str2.length()))
        str2 = str2.substring(0, -1 + str2.length());
      this._view.setAttr(XulPropNameCache.TagId.TEXT, str2);
    }
    super.syncTextInfo(paramBoolean);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulResolutionLabelRender
 * JD-Core Version:    0.6.2
 */