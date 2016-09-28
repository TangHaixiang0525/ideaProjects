package com.starcor.xulapp.debug;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import com.starcor.hunan.App;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.widget.XULDialog;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

public class XulDebugAdapter
{
  private static Handler _mainLooperHandler;

  public static void dispatchKeyEvent(Object paramObject, KeyEvent paramKeyEvent)
  {
    if ((paramObject instanceof DialogActivity))
      ((DialogActivity)paramObject).dispatchKeyEvent(paramKeyEvent);
    while (!(paramObject instanceof XULDialog))
      return;
    ((XULDialog)paramObject).getOwnerActivity().dispatchKeyEvent(paramKeyEvent);
  }

  public static void drawPage(Object paramObject, Canvas paramCanvas)
  {
    View localView;
    if ((paramObject instanceof DialogActivity))
      localView = ((DialogActivity)paramObject).xulGetRootView();
    while (localView == null)
    {
      return;
      boolean bool = paramObject instanceof XULDialog;
      localView = null;
      if (bool)
        localView = ((XULDialog)paramObject).xulGetRootView();
    }
    localView.draw(paramCanvas);
  }

  public static void finishPage(Object paramObject)
  {
    if ((paramObject instanceof DialogActivity))
      ((DialogActivity)paramObject).finish();
    while (!(paramObject instanceof XULDialog))
      return;
    ((XULDialog)paramObject).dismiss();
  }

  public static Context getAppContext()
  {
    return App.getAppContext();
  }

  public static String getPackageName()
  {
    return App.getInstance().getPackageName();
  }

  public static String getPageId(Object paramObject)
  {
    if ((paramObject instanceof DialogActivity))
    {
      XulRenderContext localXulRenderContext2 = ((DialogActivity)paramObject).xulGetRenderContext();
      if ((localXulRenderContext2 != null) && (localXulRenderContext2.getPage() != null))
        return localXulRenderContext2.getPage().getId();
    }
    else if ((paramObject instanceof XULDialog))
    {
      XulRenderContext localXulRenderContext1 = ((XULDialog)paramObject).xulGetRenderContext();
      if ((localXulRenderContext1 != null) && (localXulRenderContext1.getPage() != null))
        return localXulRenderContext1.getPage().getId();
    }
    return "";
  }

  public static XulRenderContext getPageRenderContext(Object paramObject)
  {
    if ((paramObject instanceof DialogActivity))
      return ((DialogActivity)paramObject).xulGetRenderContext();
    if ((paramObject instanceof XULDialog))
      return ((XULDialog)paramObject).xulGetRenderContext();
    return null;
  }

  public static boolean isPageFinished(Object paramObject)
  {
    if ((paramObject instanceof DialogActivity))
      return ((DialogActivity)paramObject).isFinishing();
    if ((paramObject instanceof XULDialog))
      return ((XULDialog)paramObject).isShowing();
    return true;
  }

  public static void postToMainLooper(Runnable paramRunnable)
  {
    if (_mainLooperHandler == null)
      _mainLooperHandler = new Handler(App.getInstance().getMainLooper());
    _mainLooperHandler.post(paramRunnable);
  }

  public static void startActivity(Intent paramIntent)
  {
    App.getAppContext().startActivity(paramIntent);
  }

  public static void writePageSpecifiedAttribute(DialogActivity paramDialogActivity, XmlSerializer paramXmlSerializer)
    throws IOException
  {
    String str1 = getPageId(paramDialogActivity);
    if (!TextUtils.isEmpty(str1))
      paramXmlSerializer.attribute(null, "pageId", str1);
    String str2 = paramDialogActivity.getClass().getSimpleName();
    if (!TextUtils.isEmpty(str2))
      paramXmlSerializer.attribute(null, "pageClassName", str2);
  }

  public static void writePageSpecifiedAttribute(XULDialog paramXULDialog, XmlSerializer paramXmlSerializer)
    throws IOException
  {
    String str1 = getPageId(paramXULDialog);
    if (!TextUtils.isEmpty(str1))
      paramXmlSerializer.attribute(null, "pageId", str1);
    String str2 = paramXULDialog.getClass().getSimpleName();
    if (!TextUtils.isEmpty(str2))
      paramXmlSerializer.attribute(null, "pageClassName", str2);
  }

  public static void writePageSpecifiedAttribute(Object paramObject, XmlSerializer paramXmlSerializer)
    throws IOException
  {
    if ((paramObject instanceof DialogActivity))
      writePageSpecifiedAttribute((DialogActivity)paramObject, paramXmlSerializer);
    while (!(paramObject instanceof XULDialog))
      return;
    writePageSpecifiedAttribute((XULDialog)paramObject, paramXmlSerializer);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xulapp.debug.XulDebugAdapter
 * JD-Core Version:    0.6.2
 */