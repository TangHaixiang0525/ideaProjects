package com.starcor.hunan.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.xul.XULJSCmdHost;
import com.starcor.hunan.xul.XULJSCmdLogic;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.Prop.XulStyle;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_Border;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulRenderContext.IXulRenderHandler;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulUtils.ticketMarker;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import com.starcor.xulapp.debug.XulDebugMonitor;
import com.starcor.xulapp.debug.XulDebugServer;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.json.JSONObject;

public class XULDialog extends BaseDialog
{
  public static final String CMD_CLOSE_DIALOG = "xul-close-dialog";
  private static final String TAG = XULDialog.class.getSimpleName();
  private boolean _blurBkg = false;
  private Paint _clipPaint;
  private Path _clipPath = null;
  XulDebugMonitor _dbgMonitor;
  private ArrayList<XulView> _focusStack = new ArrayList();
  XULJSCmdLogic _jsCmdLogic;
  private JSONObject _startData = null;
  private View _xulBlurView = null;
  private View _xulDrawingView = null;
  private boolean _xulHandleKeyEvent = false;
  private XulRenderContext _xulRenderContext = null;
  private RelativeLayout _xulView = null;
  private Context context = null;
  private boolean isDealBackspaceKey = false;

  public XULDialog(Context paramContext, String paramString, JSONObject paramJSONObject)
  {
    super(paramContext, 2131296258);
    this.context = paramContext;
    init(paramString, paramJSONObject);
  }

  public XULDialog(Context paramContext, String paramString, JSONObject paramJSONObject, int paramInt)
  {
    super(paramContext, paramInt);
    this.context = paramContext;
    init(paramString, paramJSONObject);
  }

  private void _destroyBlurView()
  {
    if (this._xulBlurView != null)
    {
      getOwnerActivity().getWindowManager().removeViewImmediate(this._xulBlurView);
      this._xulBlurView = null;
    }
  }

  private void _doBlurBkg()
  {
    if (this._blurBkg)
    {
      Activity localActivity = getOwnerActivity();
      if (localActivity != null)
      {
        View localView = localActivity.getWindow().getDecorView();
        XulUtils.ticketMarker localticketMarker = new XulUtils.ticketMarker("blur ", false);
        localticketMarker.mark();
        int i = localView.getWidth() / 8;
        int j = localView.getHeight() / 8;
        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        localCanvas.scale(0.125F, 0.125F);
        localCanvas.save();
        localView.draw(localCanvas);
        localCanvas.restore();
        localCanvas.setBitmap(null);
        ByteBuffer localByteBuffer = ByteBuffer.allocateDirect(localBitmap.getByteCount());
        localBitmap.copyPixelsToBuffer(localByteBuffer);
        localticketMarker.mark("init");
        XulUtils.doBlurOnBuffer(localByteBuffer, i, j, 2);
        localticketMarker.mark("blur");
        localByteBuffer.rewind();
        localBitmap.copyPixelsFromBuffer(localByteBuffer);
        localticketMarker.mark("cpyPXBack");
        Logger.d(TAG, localticketMarker.toString());
        this._xulBlurView = new View(localActivity);
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.width = -1;
        localLayoutParams.height = -1;
        localLayoutParams.x = 0;
        localLayoutParams.y = 0;
        localActivity.getWindowManager().addView(this._xulBlurView, localLayoutParams);
        this._xulBlurView.setBackgroundDrawable(new BitmapDrawable(localBitmap));
        this._xulBlurView.setVisibility(8);
      }
    }
  }

  private boolean handleMontionEvent(MotionEvent paramMotionEvent)
  {
    return (this._xulRenderContext != null) && (this._xulRenderContext.onMotionEvent(paramMotionEvent));
  }

  private void init(String paramString, JSONObject paramJSONObject)
  {
    this._dbgMonitor = XulDebugServer.getMonitor();
    if (this._dbgMonitor != null)
      this._dbgMonitor.onPageCreate(this);
    setOwnerActivity((Activity)this.context);
    this._startData = paramJSONObject;
    this._jsCmdLogic = new XULJSCmdLogic(this.context, new XULJSCmdHost()
    {
      public void close()
      {
        XULDialog.this.dismiss();
      }

      public void finish()
      {
        XULDialog.this.getOwnerActivity().finish();
      }

      public JSONObject getJSInitData()
      {
        return XULDialog.this._startData;
      }

      public void hideProgressInfo()
      {
        ((DialogActivity)XULDialog.this.getOwnerActivity()).dismissLoaddingDialog();
      }

      public void showProgressInfo()
      {
        ((DialogActivity)XULDialog.this.getOwnerActivity()).showLoaddingDialog();
      }

      public void startActivity(Intent paramAnonymousIntent)
      {
        XULDialog.this.context.startActivity(paramAnonymousIntent);
      }

      public void xulExecute(String paramAnonymousString, String[] paramAnonymousArrayOfString)
      {
        XULDialog.this.xulExecute(paramAnonymousString, paramAnonymousArrayOfString);
      }

      public XulView xulFindViewById(String paramAnonymousString)
      {
        return ((DialogActivity)XULDialog.this.getOwnerActivity()).xulFindViewById(paramAnonymousString);
      }

      public void xulFireEvent(String paramAnonymousString, Object[] paramAnonymousArrayOfObject)
      {
        XULDialog.this.xulFireEvent(paramAnonymousString, paramAnonymousArrayOfObject);
      }

      public void xulPostDelay(Runnable paramAnonymousRunnable, int paramAnonymousInt)
      {
        ((DialogActivity)XULDialog.this.getOwnerActivity()).xulPostDelay(paramAnonymousRunnable, paramAnonymousInt);
      }
    });
    if (this._dbgMonitor != null)
      this._dbgMonitor.onPageResumed(this);
    initXul(paramString, true);
    _doBlurBkg();
  }

  public void dismiss()
  {
    _destroyBlurView();
    if (this._jsCmdLogic != null)
      this._jsCmdLogic.destroy();
    super.dismiss();
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if ((paramKeyEvent.getKeyCode() == 4) && (this.isDealBackspaceKey))
      xulFireEvent("appEvents:Broadcast:XulDialogOnBack", null);
    while ((this._xulHandleKeyEvent) && (this._xulRenderContext != null) && (this._xulRenderContext.onKeyEvent(paramKeyEvent)))
      return true;
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public void hide()
  {
    if (this._xulBlurView != null)
      this._xulBlurView.setVisibility(8);
    super.hide();
  }

  public void initXul(String paramString)
  {
    initXul(paramString, false);
  }

  public void initXul(String paramString, boolean paramBoolean)
  {
    this._xulHandleKeyEvent = paramBoolean;
    this._xulDrawingView = new View(this.context)
    {
      Rect _drawingRc = new Rect();

      void beginClip(Canvas paramAnonymousCanvas)
      {
        if (XULDialog.this._clipPath != null)
          paramAnonymousCanvas.saveLayer(null, null, 31);
      }

      void endClip(Canvas paramAnonymousCanvas)
      {
        if (XULDialog.this._clipPath != null)
        {
          paramAnonymousCanvas.drawPath(XULDialog.this._clipPath, XULDialog.this._clipPaint);
          paramAnonymousCanvas.restore();
        }
      }

      protected void onDraw(Canvas paramAnonymousCanvas)
      {
        XulRenderContext localXulRenderContext = XULDialog.this._xulRenderContext;
        if (localXulRenderContext == null)
        {
          super.onDraw(paramAnonymousCanvas);
          return;
        }
        Rect localRect = paramAnonymousCanvas.getClipBounds();
        if ((localRect == null) || ((localRect.left == 0) && (localRect.top == 0) && (localRect.right == 0) && (localRect.bottom == 0)))
        {
          getDrawingRect(this._drawingRc);
          localRect = this._drawingRc;
        }
        XulDebugMonitor localXulDebugMonitor = XULDialog.this._dbgMonitor;
        if (localXulDebugMonitor != null)
        {
          long l1 = XulUtils.timestamp_us();
          beginClip(paramAnonymousCanvas);
          if (localXulRenderContext.beginDraw(paramAnonymousCanvas, localRect))
          {
            localXulRenderContext.endDraw();
            endClip(paramAnonymousCanvas);
            localXulDebugMonitor.drawDebugInfo(XULDialog.this._xulRenderContext, paramAnonymousCanvas);
            long l2 = XulUtils.timestamp_us();
            localXulDebugMonitor.onPageRefreshed(XULDialog.this, l2 - l1);
            return;
          }
          endClip(paramAnonymousCanvas);
          super.onDraw(paramAnonymousCanvas);
          localXulDebugMonitor.drawDebugInfo(XULDialog.this._xulRenderContext, paramAnonymousCanvas);
          return;
        }
        beginClip(paramAnonymousCanvas);
        if (localXulRenderContext.beginDraw(paramAnonymousCanvas, localRect))
        {
          localXulRenderContext.endDraw();
          endClip(paramAnonymousCanvas);
          return;
        }
        endClip(paramAnonymousCanvas);
        super.onDraw(paramAnonymousCanvas);
      }
    };
    this._xulDrawingView.setFocusable(false);
    this._xulView = new RelativeLayout(this.context)
    {
      protected void onFocusChanged(boolean paramAnonymousBoolean, int paramAnonymousInt, Rect paramAnonymousRect)
      {
        super.onFocusChanged(paramAnonymousBoolean, paramAnonymousInt, paramAnonymousRect);
        if (!paramAnonymousBoolean)
        {
          Logger.d(XULDialog.TAG, "focus lost!!!!");
          post(new Runnable()
          {
            public void run()
            {
              Logger.d(XULDialog.TAG, "update focus!!!!");
              View localView = XULDialog.3.this.findFocus();
              IXulExternalView localIXulExternalView = null;
              if (localView != null)
              {
                if (!(localView instanceof IXulExternalView))
                  break label71;
                localIXulExternalView = (IXulExternalView)localView;
              }
              while (true)
              {
                if (localIXulExternalView != null)
                {
                  XulView localXulView = XULDialog.this._xulRenderContext.findCustomItemByExtView(localIXulExternalView);
                  XULDialog.this._xulRenderContext.getLayout().requestFocus(localXulView);
                }
                return;
                label71: for (ViewParent localViewParent = localView.getParent(); (localViewParent != null) && (!(localViewParent instanceof IXulExternalView)); localViewParent = localViewParent.getParent());
                localIXulExternalView = null;
                if (localViewParent != null)
                  localIXulExternalView = (IXulExternalView)localViewParent;
              }
            }
          });
        }
      }
    };
    this._xulView.setFocusable(paramBoolean);
    this._xulView.addView(this._xulDrawingView);
    this._xulRenderContext = XulManager.createXulRender(paramString, new XulRenderContext.IXulRenderHandler()
    {
      Handler _handler = new Handler();

      public IXulExternalView createExternalView(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, XulView paramAnonymousXulView)
      {
        IXulExternalView localIXulExternalView = XULDialog.this.xulCreateExternalView(paramAnonymousString, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousXulView);
        if (localIXulExternalView != null)
          return localIXulExternalView;
        return null;
      }

      public InputStream getAppData(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return XULDialog.this.xulGetAppData(paramAnonymousString);
      }

      public InputStream getAssets(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return null;
      }

      public void invalidate(Rect paramAnonymousRect)
      {
        if (XULDialog.this._xulDrawingView == null)
          return;
        if (paramAnonymousRect == null)
        {
          XULDialog.this._xulDrawingView.invalidate();
          return;
        }
        XULDialog.this._xulDrawingView.invalidate(paramAnonymousRect);
      }

      public void onDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        if (XULDialog.this.xulDoAction(paramAnonymousXulView, paramAnonymousString1, paramAnonymousString2, paramAnonymousString3, paramAnonymousObject))
          Logger.i(XULDialog.TAG, "onDoAction");
      }

      public void onRenderEvent(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, Object paramAnonymousObject)
      {
      }

      public void onRenderIsReady()
      {
        XULDialog.this.xulOnRenderIsReady();
      }

      public String resolve(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return null;
      }

      public void uiRun(Runnable paramAnonymousRunnable)
      {
        this._handler.post(paramAnonymousRunnable);
      }

      public void uiRun(Runnable paramAnonymousRunnable, int paramAnonymousInt)
      {
        this._handler.postDelayed(paramAnonymousRunnable, paramAnonymousInt);
      }
    }
    , App.SCREEN_WIDTH, App.SCREEN_HEIGHT);
    XulLayout localXulLayout = this._xulRenderContext.getLayout();
    if ("true".equals(localXulLayout.getAttrString("blur-bkg")))
      this._blurBkg = true;
    super.setContentView(this._xulView);
    if (this._xulRenderContext != null)
    {
      RectF localRectF = localXulLayout.getFocusRc();
      XulStyle localXulStyle = localXulLayout.getStyle("border");
      if (localXulStyle != null)
      {
        XulPropParser.xulParsedStyle_Border localxulParsedStyle_Border = (XulPropParser.xulParsedStyle_Border)localXulStyle.getParsedValue();
        if ((localxulParsedStyle_Border.xRadius > 1.0F) && (localxulParsedStyle_Border.yRadius > 1.0F))
        {
          Path localPath = new Path();
          this._clipPaint = new Paint(1);
          this._clipPaint.setColor(-1);
          this._clipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
          XulPage localXulPage = localXulLayout.getOwnerPage();
          localPath.addRoundRect(new RectF(localRectF), localXulPage.getXScalar() * localxulParsedStyle_Border.xRadius, localXulPage.getYScalar() * localxulParsedStyle_Border.yRadius, Path.Direction.CCW);
          this._clipPath = localPath;
        }
      }
      ViewGroup.LayoutParams localLayoutParams1 = this._xulDrawingView.getLayoutParams();
      localLayoutParams1.width = ((int)localRectF.width());
      localLayoutParams1.height = ((int)localRectF.height());
      Window localWindow = getWindow();
      WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
      localLayoutParams.width = localLayoutParams1.width;
      localLayoutParams.height = localLayoutParams1.height;
      localWindow.setAttributes(localLayoutParams);
      ViewGroup.LayoutParams localLayoutParams2 = this._xulView.getLayoutParams();
      localLayoutParams2.width = -2;
      localLayoutParams2.height = -2;
    }
  }

  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    if (handleMontionEvent(paramMotionEvent))
      return true;
    return super.onGenericMotionEvent(paramMotionEvent);
  }

  protected void onStop()
  {
    if (this._dbgMonitor != null)
      this._dbgMonitor.onPageDestroy(this);
    Logger.i(TAG, "onStop");
    _destroyBlurView();
    if (this._xulRenderContext != null)
      this._xulRenderContext.destroy();
    if (this._jsCmdLogic != null)
      this._jsCmdLogic.destroy();
    super.onStop();
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (handleMontionEvent(paramMotionEvent))
      return true;
    return super.onTouchEvent(paramMotionEvent);
  }

  public boolean onTrackballEvent(MotionEvent paramMotionEvent)
  {
    if (handleMontionEvent(paramMotionEvent))
      return true;
    return super.onTrackballEvent(paramMotionEvent);
  }

  public void setIsDealBackKey(boolean paramBoolean)
  {
    this.isDealBackspaceKey = paramBoolean;
  }

  public void show()
  {
    if (this._xulBlurView != null)
      this._xulBlurView.setVisibility(0);
    super.show();
  }

  public void xulClearFocus()
  {
    if ((this._xulRenderContext == null) || (this._focusStack == null))
      return;
    this._xulRenderContext.getLayout().requestFocus(null);
  }

  protected IXulExternalView xulCreateExternalView(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, XulView paramXulView)
  {
    if ("FilmListView".equals(paramString))
    {
      XulExt_FilmListView localXulExt_FilmListView = new XulExt_FilmListView(this.context, paramXulView);
      this._xulView.addView(localXulExt_FilmListView, paramInt3, paramInt4);
      return localXulExt_FilmListView;
    }
    if ("EditBox".equals(paramString))
    {
      XulExt_ExternalEditBox localXulExt_ExternalEditBox = new XulExt_ExternalEditBox(this.context, paramXulView);
      this._xulView.addView(localXulExt_ExternalEditBox, paramInt3, paramInt4);
      return localXulExt_ExternalEditBox;
    }
    return null;
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.i(TAG, "xulDoAction action=" + paramString1 + "type=" + paramString2 + "command=" + paramString3 + "userdata=" + paramObject);
    boolean bool1 = "jsCmd".equals(paramString2);
    boolean bool2 = false;
    if (bool1);
    try
    {
      boolean bool3 = this._jsCmdLogic.doJSCmd(paramXulView, paramString3, paramObject);
      bool2 = bool3;
      return bool2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  protected void xulExecute(String paramString, String[] paramArrayOfString)
  {
    if ("xul-close-dialog" == paramString)
      dismiss();
  }

  public XulView xulFindViewById(String paramString)
  {
    if (this._xulRenderContext == null)
      return null;
    return this._xulRenderContext.findItemById(paramString);
  }

  protected XulArrayList<XulView> xulFindViewsByClass(String paramString)
  {
    if (this._xulRenderContext == null)
      return null;
    return this._xulRenderContext.findItemsByClass(new String[] { paramString });
  }

  public boolean xulFireEvent(String paramString, Object[] paramArrayOfObject)
  {
    if ((this._xulRenderContext == null) || (this._xulRenderContext.getLayout() == null))
      return false;
    return XulPage.invokeActionWithArgs(this._xulRenderContext.getLayout(), paramString, paramArrayOfObject);
  }

  protected InputStream xulGetAppData(String paramString)
  {
    return null;
  }

  public XulRenderContext xulGetRenderContext()
  {
    return this._xulRenderContext;
  }

  public View xulGetRootView()
  {
    return this._xulDrawingView;
  }

  public boolean xulHasFocus(XulView paramXulView)
  {
    if ((this._xulRenderContext == null) || (this._focusStack == null));
    while ((paramXulView == null) || (!paramXulView.isEnabled()) || (!paramXulView.isVisible()) || (!paramXulView.focusable()))
      return false;
    return paramXulView.hasFocus();
  }

  public boolean xulIsReady()
  {
    if (this._xulRenderContext == null)
      return true;
    return this._xulRenderContext.isReady();
  }

  protected void xulOnRenderIsReady()
  {
  }

  public void xulPopFocus()
  {
    if ((this._xulRenderContext == null) || (this._focusStack == null));
    XulView localXulView;
    do
    {
      do
        return;
      while (this._focusStack.isEmpty());
      localXulView = (XulView)this._focusStack.remove(-1 + this._focusStack.size());
    }
    while (localXulView == null);
    this._xulRenderContext.getLayout().requestFocus(localXulView);
  }

  public void xulPushFocus(XulView paramXulView)
  {
    xulPushFocus(null, paramXulView);
  }

  public void xulPushFocus(XulView paramXulView1, XulView paramXulView2)
  {
    if ((this._xulRenderContext == null) || (paramXulView2 == null))
      return;
    if (this._focusStack == null)
      this._focusStack = new ArrayList();
    XulLayout localXulLayout = this._xulRenderContext.getLayout();
    if (paramXulView1 == null)
      paramXulView1 = localXulLayout.getFocus();
    this._focusStack.add(paramXulView1);
    localXulLayout.requestFocus(paramXulView2);
  }

  public void xulRequestFocus(XulView paramXulView)
  {
    if ((this._xulRenderContext == null) || (this._focusStack == null));
    while ((paramXulView == null) || (!paramXulView.isEnabled()) || (!paramXulView.isVisible()) || (!paramXulView.focusable()))
      return;
    this._xulRenderContext.getLayout().requestFocus(paramXulView);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.XULDialog
 * JD-Core Version:    0.6.2
 */