package com.starcor.hunan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
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
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.InputStream;
import java.util.ArrayList;

public class XULFloatView
{
  private String TAG = "XULFloatView";
  private XulRenderContext.IXulRenderHandler _IXulRenderHandler = null;
  private Paint _clipPaint;
  private Path _clipPath = null;
  private ArrayList<XulView> _focusStack = new ArrayList();
  private View _xulDrawingView = null;
  private boolean _xulHandleKeyEvent = false;
  private XulRenderContext _xulRenderContext = null;
  private RelativeLayout _xulView = null;
  private Context mContext = null;
  private WindowManager.LayoutParams mParams = null;
  private WindowManager wManager = null;

  public XULFloatView(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public void cancel()
  {
    if (this.wManager != null)
    {
      this.wManager.removeView(this._xulView);
      this._xulRenderContext.destroy();
      this._xulRenderContext = null;
      this.wManager = null;
    }
  }

  public void initXul(String paramString, boolean paramBoolean)
  {
    this._xulHandleKeyEvent = paramBoolean;
    this._xulDrawingView = new View(this.mContext)
    {
      Rect _drawingRc = new Rect();

      void beginClip(Canvas paramAnonymousCanvas)
      {
        if (XULFloatView.this._clipPath != null)
          paramAnonymousCanvas.saveLayer(null, null, 31);
      }

      void endClip(Canvas paramAnonymousCanvas)
      {
        if (XULFloatView.this._clipPath != null)
        {
          paramAnonymousCanvas.drawPath(XULFloatView.this._clipPath, XULFloatView.this._clipPaint);
          paramAnonymousCanvas.restore();
        }
      }

      protected void onDraw(Canvas paramAnonymousCanvas)
      {
        if (XULFloatView.this._xulRenderContext != null)
        {
          Rect localRect = paramAnonymousCanvas.getClipBounds();
          if ((localRect == null) || ((localRect.left == 0) && (localRect.top == 0) && (localRect.right == 0) && (localRect.bottom == 0)))
          {
            getDrawingRect(this._drawingRc);
            localRect = this._drawingRc;
          }
          beginClip(paramAnonymousCanvas);
          if (XULFloatView.this._xulRenderContext.beginDraw(paramAnonymousCanvas, localRect))
          {
            XULFloatView.this._xulRenderContext.endDraw();
            endClip(paramAnonymousCanvas);
            return;
          }
          endClip(paramAnonymousCanvas);
        }
        super.onDraw(paramAnonymousCanvas);
      }
    };
    this._xulDrawingView.setFocusable(false);
    this._xulView = new RelativeLayout(this.mContext)
    {
      protected void onFocusChanged(boolean paramAnonymousBoolean, int paramAnonymousInt, Rect paramAnonymousRect)
      {
        super.onFocusChanged(paramAnonymousBoolean, paramAnonymousInt, paramAnonymousRect);
        if (!paramAnonymousBoolean)
        {
          Logger.d(XULFloatView.this.TAG, "focus lost!!!!");
          post(new Runnable()
          {
            public void run()
            {
              Logger.d(XULFloatView.this.TAG, "update focus!!!!");
              View localView = XULFloatView.2.this.findFocus();
              IXulExternalView localIXulExternalView = null;
              if (localView != null)
              {
                if (!(localView instanceof IXulExternalView))
                  break label78;
                localIXulExternalView = (IXulExternalView)localView;
              }
              while (true)
              {
                if (localIXulExternalView != null)
                {
                  XulView localXulView = XULFloatView.this._xulRenderContext.findCustomItemByExtView(localIXulExternalView);
                  XULFloatView.this._xulRenderContext.getLayout().requestFocus(localXulView);
                }
                return;
                label78: for (ViewParent localViewParent = localView.getParent(); (localViewParent != null) && (!(localViewParent instanceof IXulExternalView)); localViewParent = localViewParent.getParent());
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
    this._IXulRenderHandler = new XulRenderContext.IXulRenderHandler()
    {
      Handler _handler = new Handler();

      public IXulExternalView createExternalView(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, XulView paramAnonymousXulView)
      {
        return null;
      }

      public InputStream getAppData(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return XULFloatView.this.xulGetAppData(paramAnonymousString);
      }

      public InputStream getAssets(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return null;
      }

      public void invalidate(Rect paramAnonymousRect)
      {
        if (XULFloatView.this._xulDrawingView == null)
          return;
        if (paramAnonymousRect == null)
        {
          XULFloatView.this._xulDrawingView.invalidate();
          return;
        }
        XULFloatView.this._xulDrawingView.invalidate(paramAnonymousRect);
      }

      public void onDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        if (XULFloatView.this.xulDoAction(paramAnonymousXulView, paramAnonymousString1, paramAnonymousString2, paramAnonymousString3, paramAnonymousObject))
          Logger.i(XULFloatView.this.TAG, "onDoAction");
      }

      public void onRenderEvent(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, Object paramAnonymousObject)
      {
      }

      public void onRenderIsReady()
      {
        XULFloatView.this.xulOnRenderIsReady();
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
    };
    this._xulRenderContext = XulManager.createXulRender(paramString, this._IXulRenderHandler, App.SCREEN_WIDTH, App.SCREEN_HEIGHT);
    XulLayout localXulLayout = this._xulRenderContext.getLayout();
    if (this._xulRenderContext != null)
    {
      RectF localRectF = localXulLayout.getFocusRc();
      XulPage localXulPage1 = this._xulRenderContext.getPage();
      int i = XulUtils.roundToInt(localXulPage1.getX() * localXulPage1.getXScalar());
      int j = XulUtils.roundToInt(localXulPage1.getY() * localXulPage1.getYScalar());
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
          XulPage localXulPage2 = localXulLayout.getOwnerPage();
          localPath.addRoundRect(new RectF(localRectF), localXulPage2.getXScalar() * localxulParsedStyle_Border.xRadius, localXulPage2.getYScalar() * localxulParsedStyle_Border.yRadius, Path.Direction.CCW);
          this._clipPath = localPath;
        }
      }
      ViewGroup.LayoutParams localLayoutParams1 = this._xulDrawingView.getLayoutParams();
      localLayoutParams1.width = ((int)localRectF.width());
      localLayoutParams1.height = ((int)localRectF.height());
      this.wManager = ((WindowManager)this.mContext.getApplicationContext().getSystemService("window"));
      this.mParams = new WindowManager.LayoutParams();
      this.mParams.type = 2003;
      this.mParams.format = -3;
      WindowManager.LayoutParams localLayoutParams = this.mParams;
      localLayoutParams.flags = (0x8 | localLayoutParams.flags);
      this.mParams.width = ((int)localRectF.width());
      this.mParams.height = ((int)localRectF.height());
      this.mParams.x = i;
      this.mParams.y = j;
      this.mParams.gravity = 51;
      this.wManager.addView(this._xulView, this.mParams);
      ViewGroup.LayoutParams localLayoutParams2 = this._xulView.getLayoutParams();
      localLayoutParams2.width = -2;
      localLayoutParams2.height = -2;
    }
  }

  public void refreshBinding(String paramString)
  {
    if (this._xulRenderContext == null)
      return;
    this._xulRenderContext.refreshBinding(paramString);
  }

  public void show(String paramString)
  {
    if (this._xulRenderContext == null)
      initXul(paramString, false);
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    return false;
  }

  protected void xulExecute(String paramString, String[] paramArrayOfString)
  {
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
    if (this._xulRenderContext == null)
      return false;
    return XulPage.invokeActionWithArgs(this._xulRenderContext.getLayout(), paramString, paramArrayOfObject);
  }

  protected InputStream xulGetAppData(String paramString)
  {
    return null;
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
 * Qualified Name:     com.starcor.hunan.widget.XULFloatView
 * JD-Core Version:    0.6.2
 */