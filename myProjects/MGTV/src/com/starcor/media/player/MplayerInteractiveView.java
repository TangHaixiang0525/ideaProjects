package com.starcor.media.player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.msgsys.data.reservetopic.InteractiveMessage;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulRenderContext.IXulRenderHandler;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.InputStream;

public class MplayerInteractiveView extends View
{
  private static final String TAG = MplayerInteractiveView.class.getSimpleName();
  private InteractiveMessage curMsg;
  private XulView interactiveView;
  private Rect rect;
  private XulRenderContext renderContext;
  private String text = "";

  public MplayerInteractiveView(Context paramContext)
  {
    super(paramContext);
  }

  public MplayerInteractiveView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public MplayerInteractiveView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private void initView()
  {
    initXulView();
    XulPage localXulPage = this.renderContext.getPage();
    int i = localXulPage.getX();
    int j = localXulPage.getY();
    int k = localXulPage.getWidth();
    int m = localXulPage.getHeight();
    if (i >= 2147483547)
      i = 0;
    if (j >= 2147483547)
      j = 0;
    if (k >= 2147483547)
      k = localXulPage.getPageWidth() - i;
    if (m >= 2147483547)
      m = localXulPage.getPageHeight() - j;
    int n = (int)(i * localXulPage.getXScalar());
    int i1 = (int)(j * localXulPage.getYScalar());
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams((int)(k * localXulPage.getXScalar()), (int)(m * localXulPage.getYScalar()));
    localLayoutParams.addRule(12);
    localLayoutParams.leftMargin = App.Operation(n);
    localLayoutParams.bottomMargin = App.Operation(i1);
    setLayoutParams(localLayoutParams);
  }

  private void initXulView()
  {
    this.renderContext = XulManager.createXulRender("InteractiveView", new XulRenderContext.IXulRenderHandler()
    {
      private Handler _mHandler = new Handler();

      public IXulExternalView createExternalView(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, XulView paramAnonymousXulView)
      {
        return null;
      }

      public InputStream getAppData(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return null;
      }

      public InputStream getAssets(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return null;
      }

      public void invalidate(Rect paramAnonymousRect)
      {
        if (paramAnonymousRect == null)
        {
          MplayerInteractiveView.this.invalidate();
          return;
        }
        MplayerInteractiveView.this.invalidate(paramAnonymousRect);
      }

      public void onDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
      }

      public void onRenderEvent(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, Object paramAnonymousObject)
      {
      }

      public void onRenderIsReady()
      {
      }

      public String resolve(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return null;
      }

      public void uiRun(Runnable paramAnonymousRunnable)
      {
        this._mHandler.post(paramAnonymousRunnable);
      }

      public void uiRun(Runnable paramAnonymousRunnable, int paramAnonymousInt)
      {
        this._mHandler.postDelayed(paramAnonymousRunnable, paramAnonymousInt);
      }
    });
    if (this.renderContext != null)
      this.interactiveView = this.renderContext.findItemById("interactiveView");
  }

  private void reportShowInteractiveMsgTip(InteractiveMessage paramInteractiveMessage)
  {
    if (paramInteractiveMessage != null)
    {
      Context localContext = getContext();
      if ((localContext instanceof DialogActivity))
        ((DialogActivity)localContext).reportFuncLoad(3, "", paramInteractiveMessage.toJSONString(), 0L, true, 1, "");
    }
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    initView();
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.renderContext != null)
      this.renderContext.destroy();
    this.renderContext = null;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.renderContext != null)
    {
      if (this.rect != null)
        break label58;
      this.rect = new Rect(0, 0, getWidth(), getHeight());
    }
    while (true)
    {
      if (this.renderContext.beginDraw(paramCanvas, this.rect))
        this.renderContext.endDraw();
      return;
      label58: this.rect.set(0, 0, getWidth(), getHeight());
    }
  }

  public void setContentText(String paramString)
  {
    if ((this.renderContext == null) || (TextUtils.isEmpty(paramString)));
    do
    {
      return;
      this.text = paramString;
    }
    while (this.interactiveView == null);
    this.interactiveView.setAttr("text_data", paramString);
    this.interactiveView.resetRender();
  }

  public void setInteractiveMsg(InteractiveMessage paramInteractiveMessage)
  {
    this.curMsg = paramInteractiveMessage;
  }

  public void setVisibility(int paramInt)
  {
    String str1 = TAG;
    StringBuilder localStringBuilder = new StringBuilder().append("set visible: ");
    String str2;
    if (paramInt == 0)
    {
      str2 = "true";
      Logger.d(str1, str2);
      if ((this.renderContext != null) && (this.interactiveView != null))
      {
        if (paramInt != 0)
          break label186;
        if (this.curMsg != null)
          this.interactiveView.setAttr("text_data", this.curMsg.title);
        if (TextUtils.isEmpty(this.curMsg.image))
          break label172;
        this.interactiveView.setAttr("img.0", "effect:circle:" + this.curMsg.image);
        label126: this.interactiveView.setAttr("animation", "enabled");
        reportShowInteractiveMsgTip(this.curMsg);
      }
    }
    while (true)
    {
      this.interactiveView.resetRender();
      this.renderContext.doLayout();
      super.setVisibility(paramInt);
      return;
      str2 = "false";
      break;
      label172: this.interactiveView.setAttr("img.0", "effect:circle:file:///.assets/player/interactive_def.png");
      break label126;
      label186: this.curMsg = null;
      this.interactiveView.setAttr("animation", "disabled");
      this.interactiveView.removeClass("custom_render_item_display");
      this.interactiveView.setAttr("text_data", "");
      this.interactiveView.setAttr("text", "");
      this.interactiveView.setAttr("img.0", "effect:circle:file:///.assets/player/interactive_def.png");
    }
  }

  public void showInteractiveMsgTip(InteractiveMessage paramInteractiveMessage)
  {
    if (paramInteractiveMessage != null)
    {
      setInteractiveMsg(paramInteractiveMessage);
      setVisibility(0);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerInteractiveView
 * JD-Core Version:    0.6.2
 */