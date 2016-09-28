package com.starcor.hunan.msgsys.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.SpecialActivityV2;
import com.starcor.hunan.msgsys.data.PopupDialogButtonInfo;
import com.starcor.hunan.msgsys.data.PopupDialogInfo;
import com.starcor.hunan.msgsys.mediaplayerhelper.MediaPlayerHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.MediaAssetHelperCallback;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.widget.BaseDialog;
import com.starcor.report.ReportFunc;
import com.starcor.ui.UITools;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulPendingInputStream;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulRenderContext.IXulRenderHandler;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class MessageDialogView extends BaseDialog
{
  private static final String DIALOG_REPORT_AREA = "message_dialog";
  private static final String TAG = MessageDialogView.class.getSimpleName();
  private static final String TAG_ACTION = "action";
  private static final String TAG_ARGUMENT = "a";
  private static final String TAG_ARGUMENT_LIST = "arg_list";
  private static final String TAG_BUTTON = "button";
  private static final String TAG_BUTTON_AREA_LEFT_X = "btnAreaLeftXPos";
  private static final String TAG_CLICK = "click";
  private static final String TAG_CONTENT = "content";
  private static final String TAG_DEFAULT_ICON = "default_icon";
  private static final String TAG_FOCUSED_ICON = "focused_icon";
  private static final String TAG_JAVASCRIPT_COMMAND = "jsCmd";
  private static final String TAG_KEY = "k";
  private static final String TAG_MESSAGE_ICON = "msgIcon";
  private static final String TAG_POPUP_DIALOG = "popupDialog";
  private static final String TAG_SUBTITLE = "subtitle";
  private static final String TAG_TITLE = "title";
  private static final String TAG_USER_COMMAND = "usr_cmd";
  private static final String TAG_VALUE = "v";
  public static final int TYPE_MSG_SYS_DIALOG = 1;
  private ArrayList<XulView> _focusStack = new ArrayList();
  private boolean _xulHandleKeyEvent = false;
  private XulRenderContext _xulRenderContext = null;
  private View _xulView = null;
  private Context mContext = null;
  private XulView mFirstButton = null;
  private MessageDialogListener mMessageDialogListener = null;
  private PopupDialogInfo mPopupDialogInfo = null;
  private XulPendingInputStream popupDialogInfoStream = new XulPendingInputStream();

  public MessageDialogView(Context paramContext)
  {
    super(paramContext, 2131296258);
    this.mContext = paramContext;
    initXul("SystemPopUpDialogV2");
  }

  private void findFirstButton()
  {
    XulArrayList localXulArrayList = xulFindViewsByClass("dialog_btn");
    if ((localXulArrayList != null) && (localXulArrayList.size() > 0))
    {
      Logger.i(TAG, "find first button!");
      this.mFirstButton = ((XulView)localXulArrayList.get(0));
    }
  }

  private Bundle getArgListBundle(Object paramObject)
  {
    if ((paramObject != null) && ((paramObject instanceof XulDataNode)))
      return xulArgListToBundle((XulDataNode)paramObject);
    Logger.i(TAG, "userdata is null! or userdata is not an instance of XulDataNode!");
    return null;
  }

  private ByteArrayInputStream getPopupDialogInfo(PopupDialogInfo paramPopupDialogInfo)
  {
    if (paramPopupDialogInfo == null)
    {
      Logger.i(TAG, "getPopupDialogInfo dialogInfo is null!");
      return null;
    }
    while (true)
    {
      XmlSerializer localXmlSerializer;
      StringWriter localStringWriter;
      int i;
      int j;
      try
      {
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "popupDialog");
        writeNodeValue(localXmlSerializer, "title", paramPopupDialogInfo.mDialogTitle);
        writeNodeValue(localXmlSerializer, "subtitle", paramPopupDialogInfo.mDialogSubTitle);
        writeNodeValue(localXmlSerializer, "content", paramPopupDialogInfo.mDialogDesc);
        writeNodeValue(localXmlSerializer, "msgIcon", "file:///.assets/msg_dialog/msg_icon.png");
        ArrayList localArrayList = paramPopupDialogInfo.mButtonList;
        localXmlSerializer.startTag(null, "buttons");
        if (localArrayList == null)
          break label311;
        i = localArrayList.size();
        Logger.i(TAG, "popupDialogButtonInfos.size()=" + i);
        if (i <= 0)
          break label311;
        j = 0;
        if (j < i)
        {
          PopupDialogButtonInfo localPopupDialogButtonInfo = (PopupDialogButtonInfo)localArrayList.get(j);
          if (localPopupDialogButtonInfo == null)
          {
            Logger.i(TAG, "null == info");
          }
          else
          {
            localXmlSerializer.startTag(null, "button");
            writeNodeValue(localXmlSerializer, "title", localPopupDialogButtonInfo.mButtonTitle);
            writeNodeValue(localXmlSerializer, "default_icon", localPopupDialogButtonInfo.mDefaultIcon);
            writeNodeValue(localXmlSerializer, "action", localPopupDialogButtonInfo.mAction);
            writeNodeValue(localXmlSerializer, "focused_icon", localPopupDialogButtonInfo.mFocusedIcon);
            writeArgumentList(localXmlSerializer, localPopupDialogButtonInfo.mKeyValueList);
            localXmlSerializer.endTag(null, "button");
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        this.popupDialogInfoStream.cancel();
        return null;
      }
      if (i > 1)
        writeNodeValue(localXmlSerializer, "btnAreaLeftXPos", "135");
      while (true)
      {
        label311: localXmlSerializer.endTag(null, "buttons");
        localXmlSerializer.endTag(null, "popupDialog");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
        String str = localStringWriter.toString();
        Logger.i(TAG, "getPopupDialogInfo stringWriter.toString()=" + str);
        return new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
        if (1 == i)
          writeNodeValue(localXmlSerializer, "btnAreaLeftXPos", "240");
      }
      j++;
    }
  }

  private void hideLoadingDialog(Bundle paramBundle)
  {
    if (this.mMessageDialogListener != null)
    {
      Logger.i(TAG, "hideLoadingDialog");
      this.mMessageDialogListener.hideLoadingDialog(paramBundle);
    }
  }

  private void processDetailButtonOnClick(Object paramObject)
  {
    Logger.i(TAG, "processDetailButtonOnClick");
    processOtherButtonsOnClick("detail", paramObject);
  }

  private void processOtherButtonsOnClick(String paramString, Object paramObject)
  {
    Logger.i(TAG, "processOtherButtonsOnClick");
    ReportFunc.reportDialogClick("message_dialog", 11, this.mPopupDialogInfo.mMessageId);
    Bundle localBundle = getArgListBundle(paramObject);
    if (localBundle == null)
    {
      Logger.i(TAG, "argListBundle is null!");
      return;
    }
    if (this.mFirstButton != null)
    {
      Logger.i(TAG, "mFirstButton.getAttr(\"text\").getName()=" + this.mFirstButton.getAttr("text").getStringValue());
      if ("预约成功".equals(this.mFirstButton.getAttr("text").getStringValue()))
      {
        UITools.ShowCustomToast(this.mContext, "您已预约成功，请勿重复操作！");
        return;
      }
    }
    String str1;
    String str2;
    if (this.mPopupDialogInfo != null)
    {
      str1 = this.mPopupDialogInfo.mMessageId;
      str2 = this.mPopupDialogInfo.mTopic;
      Logger.i(TAG, "msgId=" + str1 + " topic=" + str2);
      if (!localBundle.containsKey("message_id"))
        break label358;
      Logger.i(TAG, "bundle msgId=" + localBundle.getString("message_id"));
      label226: if (!localBundle.containsKey("topic"))
        break label379;
      Logger.i(TAG, "bundle topic=" + localBundle.getString("topic"));
      label268: if (!localBundle.containsKey("action"))
        break label400;
      Logger.i(TAG, "bundle action=" + localBundle.getString("action"));
    }
    while (true)
    {
      MediaPlayerHelper localMediaPlayerHelper = new MediaPlayerHelper(this.mContext, paramString, localBundle);
      localMediaPlayerHelper.setListener(new MediaAssetHelperCallback()
      {
        public void onError(String paramAnonymousString1, String paramAnonymousString2)
        {
          if (!TextUtils.isEmpty(paramAnonymousString2))
          {
            Bundle localBundle = new Bundle();
            localBundle.putString("message", paramAnonymousString2);
            MessageDialogView.this.hideLoadingDialog(localBundle);
          }
        }

        public void onSuccess(String paramAnonymousString, Bundle paramAnonymousBundle)
        {
          if (("reservetopic".equals(paramAnonymousString)) || ("reserveturnplay".equals(paramAnonymousString)))
          {
            Logger.i(MessageDialogView.TAG, "reserve success");
            if (MessageDialogView.this.mFirstButton != null)
            {
              MessageDialogView.this.mFirstButton.setAttr("text", "预约成功");
              MessageDialogView.this.mFirstButton.resetRender();
            }
            paramAnonymousBundle.putString("message", "预约成功");
            MessageDialogView.this.hideLoadingDialog(paramAnonymousBundle);
            return;
          }
          MessageDialogView.this.hideLoadingDialog(paramAnonymousBundle);
        }
      });
      localMediaPlayerHelper.startExecuteAction();
      if (this.mMessageDialogListener == null)
        break;
      this.mMessageDialogListener.dismissDialog();
      return;
      label358: Logger.i(TAG, "put bundle msgId");
      localBundle.putString("message_id", str1);
      break label226;
      label379: Logger.i(TAG, "put bundle topic");
      localBundle.putString("topic", str2);
      break label268;
      label400: Logger.i(TAG, "put bundle action");
      localBundle.putString("action", paramString);
    }
  }

  private void processPageButtonClick(Object paramObject)
  {
    Logger.i(TAG, "processPageButtonClick");
    ReportFunc.reportDialogClick("message_dialog", 11, this.mPopupDialogInfo.mMessageId);
    Bundle localBundle = getArgListBundle(paramObject);
    if (localBundle == null)
      Logger.i(TAG, "argListBundle is null!");
    do
    {
      return;
      String str = localBundle.getString("http_url");
      MetadataInfo localMetadataInfo = new MetadataInfo();
      localMetadataInfo.action_type = "web";
      localMetadataInfo.url = str;
      Intent localIntent = new Intent();
      localIntent.putExtra("MetaDataInfo", localMetadataInfo);
      localIntent.setClass(this.mContext, ActivityAdapter.getInstance().getWebActivity());
      this.mContext.startActivity(localIntent);
    }
    while (this.mMessageDialogListener == null);
    this.mMessageDialogListener.dismissDialog();
  }

  private void processPlayButtonOnClick(Object paramObject)
  {
    Logger.i(TAG, "processPlayButtonOnClick");
    processOtherButtonsOnClick("video", paramObject);
  }

  private void processTopicButtonOnClick(Object paramObject)
  {
    Logger.i(TAG, "processTopicButtonOnClick");
    ReportFunc.reportDialogClick("message_dialog", 11, this.mPopupDialogInfo.mMessageId);
    Bundle localBundle1 = getArgListBundle(paramObject);
    if (localBundle1 == null)
      Logger.i(TAG, "argListBundle is null!");
    do
    {
      return;
      String str = localBundle1.getString("binding_id");
      if ((TextUtils.isEmpty(str)) && (localBundle1.containsKey("special_id")))
      {
        Logger.i(TAG, "try KEY_SPECIAL_ID");
        str = localBundle1.getString("special_id");
      }
      Logger.i(TAG, "专题packet_id=" + str);
      if ((TextUtils.isEmpty(str)) && (this.mMessageDialogListener != null))
      {
        Bundle localBundle2 = new Bundle();
        localBundle2.putString("message", "很抱歉，该影片资源已被删除或不存在");
        this.mMessageDialogListener.hideLoadingDialog(localBundle2);
        return;
      }
      Intent localIntent = new Intent(this.mContext, SpecialActivityV2.class);
      MetadataInfo localMetadataInfo = new MetadataInfo();
      localMetadataInfo.packet_id = str;
      localIntent.putExtra("MetaDataInfo", localMetadataInfo);
      localIntent.addFlags(8388608);
      this.mContext.startActivity(localIntent);
    }
    while (this.mMessageDialogListener == null);
    this.mMessageDialogListener.dismissDialog();
  }

  private void showLoadingDialog(Bundle paramBundle)
  {
    if (this.mMessageDialogListener != null)
    {
      Logger.i(TAG, "showLoadingDialog");
      this.mMessageDialogListener.showLoadingDialog(paramBundle);
    }
  }

  private void writeArgumentList(XmlSerializer paramXmlSerializer, Hashtable<String, String> paramHashtable)
    throws IOException
  {
    paramXmlSerializer.startTag(null, "arg_list");
    Enumeration localEnumeration = paramHashtable.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      String str2 = (String)paramHashtable.get(str1);
      paramXmlSerializer.startTag(null, "a");
      writeNodeValue(paramXmlSerializer, "k", str1);
      writeNodeValue(paramXmlSerializer, "v", str2);
      paramXmlSerializer.endTag(null, "a");
    }
    paramXmlSerializer.endTag(null, "arg_list");
  }

  private void writeNodeValue(XmlSerializer paramXmlSerializer, String paramString1, String paramString2)
    throws IOException
  {
    if (paramString2 == null)
      paramString2 = "";
    paramXmlSerializer.startTag(null, paramString1);
    paramXmlSerializer.text(paramString2);
    paramXmlSerializer.endTag(null, paramString1);
  }

  private static Bundle xulArgListToBundle(XulDataNode paramXulDataNode)
  {
    Bundle localBundle = new Bundle();
    XulDataNode localXulDataNode1 = paramXulDataNode.getFirstChild();
    if (localXulDataNode1 != null)
    {
      XulDataNode localXulDataNode2 = localXulDataNode1.getFirstChild();
      if (localXulDataNode2 == null);
      while (true)
      {
        localXulDataNode1 = localXulDataNode1.getNext();
        break;
        String str1 = "";
        String str2 = "";
        if (localXulDataNode2 != null)
        {
          String str3 = localXulDataNode2.getName();
          if ("k".equals(str3))
            str1 = localXulDataNode2.getValue();
          while (true)
          {
            localXulDataNode2 = localXulDataNode2.getNext();
            break;
            if ("v".equals(str3))
              str2 = localXulDataNode2.getValue();
          }
        }
        localBundle.putString(str1, str2);
      }
    }
    return localBundle;
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if ((this._xulHandleKeyEvent) && (this._xulRenderContext != null) && (this._xulRenderContext.onKeyEvent(paramKeyEvent)))
      return true;
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public void initInfo(PopupDialogInfo paramPopupDialogInfo)
  {
    Logger.i(TAG, "initInfo");
    if (paramPopupDialogInfo == null)
    {
      Logger.i(TAG, "dialogInfo is null!");
      return;
    }
    this.mPopupDialogInfo = paramPopupDialogInfo;
    this.popupDialogInfoStream.setBaseStream(getPopupDialogInfo(paramPopupDialogInfo));
  }

  public void initInfo(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (this._xulRenderContext == null);
    XulView localXulView4;
    do
    {
      return;
      XulView localXulView1 = this._xulRenderContext.findItemById("dialog_title");
      XulView localXulView2 = this._xulRenderContext.findItemById("dialog_content");
      XulView localXulView3 = this._xulRenderContext.findItemById("dialog_ok_btn");
      localXulView4 = this._xulRenderContext.findItemById("dialog_sub_title");
      if (localXulView1 != null)
      {
        localXulView1.setAttr("text", paramString1);
        localXulView1.resetRender();
      }
      if (localXulView2 != null)
      {
        localXulView2.setAttr("text", paramString3);
        localXulView2.resetRender();
      }
      if (localXulView3 != null)
      {
        localXulView3.setAttr("text", paramString4);
        localXulView3.resetRender();
      }
    }
    while (localXulView4 == null);
    localXulView4.setAttr("text", paramString2);
    localXulView4.resetRender();
  }

  public void initXul(String paramString)
  {
    initXul(paramString, true);
    if (this._xulRenderContext != null)
    {
      RectF localRectF = this._xulRenderContext.getLayout().getFocusRc();
      WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
      localLayoutParams.width = -2;
      localLayoutParams.height = -2;
      ViewGroup.LayoutParams localLayoutParams1 = this._xulView.getLayoutParams();
      localLayoutParams1.width = ((int)localRectF.width());
      localLayoutParams1.height = ((int)localRectF.height());
    }
  }

  public void initXul(String paramString, boolean paramBoolean)
  {
    this._xulHandleKeyEvent = paramBoolean;
    this._xulView = new View(this.mContext)
    {
      Rect _drawingRc = new Rect();

      protected void onDraw(Canvas paramAnonymousCanvas)
      {
        if (MessageDialogView.this._xulRenderContext != null)
        {
          Rect localRect = paramAnonymousCanvas.getClipBounds();
          if ((localRect == null) || ((localRect.left == 0) && (localRect.top == 0) && (localRect.right == 0) && (localRect.bottom == 0)))
          {
            getDrawingRect(this._drawingRc);
            localRect = this._drawingRc;
          }
          if (MessageDialogView.this._xulRenderContext.beginDraw(paramAnonymousCanvas, localRect))
          {
            MessageDialogView.this._xulRenderContext.endDraw();
            return;
          }
        }
        super.onDraw(paramAnonymousCanvas);
      }

      protected void onFocusChanged(boolean paramAnonymousBoolean, int paramAnonymousInt, Rect paramAnonymousRect)
      {
        super.onFocusChanged(paramAnonymousBoolean, paramAnonymousInt, paramAnonymousRect);
        if (!paramAnonymousBoolean)
        {
          Logger.d(MessageDialogView.TAG, "focus lost!!!!");
          post(new Runnable()
          {
            public void run()
            {
              Logger.d(MessageDialogView.TAG, "update focus!!!!");
              View localView = MessageDialogView.1.this.findFocus();
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
                  XulView localXulView = MessageDialogView.this._xulRenderContext.findCustomItemByExtView(localIXulExternalView);
                  MessageDialogView.this._xulRenderContext.getLayout().requestFocus(localXulView);
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
    WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
    localLayoutParams.width = -1;
    localLayoutParams.height = -1;
    super.setContentView(this._xulView, localLayoutParams);
    this._xulRenderContext = XulManager.createXulRender(paramString, new XulRenderContext.IXulRenderHandler()
    {
      Handler _handler = new Handler();

      public IXulExternalView createExternalView(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, XulView paramAnonymousXulView)
      {
        IXulExternalView localIXulExternalView = MessageDialogView.this.xulCreateExternalView(paramAnonymousString, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousXulView);
        if (localIXulExternalView != null)
          return localIXulExternalView;
        return null;
      }

      public InputStream getAppData(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return MessageDialogView.this.xulGetAppData(paramAnonymousString);
      }

      public InputStream getAssets(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return null;
      }

      public void invalidate(Rect paramAnonymousRect)
      {
        if (MessageDialogView.this._xulView == null)
          return;
        if (paramAnonymousRect == null)
        {
          MessageDialogView.this._xulView.invalidate();
          return;
        }
        MessageDialogView.this._xulView.invalidate(paramAnonymousRect);
      }

      public void onDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        if (MessageDialogView.this.xulDoAction(paramAnonymousString1, paramAnonymousString2, paramAnonymousString3, paramAnonymousObject))
          Logger.i(MessageDialogView.TAG, "onDoAction");
      }

      public void onRenderEvent(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, Object paramAnonymousObject)
      {
      }

      public void onRenderIsReady()
      {
        MessageDialogView.this.xulOnRenderIsReady();
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
  }

  public void onBackPressed()
  {
    super.onBackPressed();
    ReportFunc.reportDialogExit("message_dialog", 1, this.mPopupDialogInfo.mMessageId);
  }

  protected void onStop()
  {
    Logger.i(TAG, "onStop");
    if (this._xulRenderContext != null)
      this._xulRenderContext.destroy();
    super.onStop();
  }

  public void setMessageDialogListener(MessageDialogListener paramMessageDialogListener)
  {
    this.mMessageDialogListener = paramMessageDialogListener;
  }

  public void show()
  {
    super.show();
    ReportFunc.reportDialogLoad("message_dialog", 1, this.mPopupDialogInfo.mMessageId);
  }

  public void xulClearFocus()
  {
    if ((this._xulRenderContext == null) || (this._focusStack == null))
      return;
    this._xulRenderContext.getLayout().requestFocus(null);
  }

  protected IXulExternalView xulCreateExternalView(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, XulView paramXulView)
  {
    return null;
  }

  protected boolean xulDoAction(String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.i(TAG, "xulDoAction action=" + paramString1 + " type=" + paramString2 + " command=" + paramString3 + " userdata=" + paramObject);
    if (!xulIsReady())
      Logger.i(TAG, "!xulIsReady()");
    do
    {
      do
      {
        return false;
        if ("bindingUpdated".equals(paramString1))
          findFirstButton();
        if ((!"usr_cmd".equals(paramString2)) && (!"jsCmd".equals(paramString2)))
        {
          Logger.d(TAG, "!((usr_cmd.equals(type))||(jsCmd).equals(type))");
          return false;
        }
      }
      while (!"click".equals(paramString1));
      if ((this.mContext instanceof DialogActivity))
        ((DialogActivity)this.mContext).isMsgDialogClick = true;
      if ("video".equals(paramString3))
      {
        showLoadingDialog(null);
        processPlayButtonOnClick(paramObject);
        return false;
      }
      if ("detail".equals(paramString3))
      {
        showLoadingDialog(null);
        processDetailButtonOnClick(paramObject);
        return false;
      }
      if ("page".equals(paramString3))
      {
        processPageButtonClick(paramObject);
        return false;
      }
      if ("topic".equals(paramString3))
      {
        processTopicButtonOnClick(paramObject);
        return false;
      }
      if (!"action_on_click_msg_list_button".equals(paramString3))
        break;
    }
    while (this.mMessageDialogListener == null);
    String str = "";
    if ((this.mPopupDialogInfo != null) && (!TextUtils.isEmpty(this.mPopupDialogInfo.mTopic)))
      str = this.mPopupDialogInfo.mTopic;
    this.mMessageDialogListener.onViewMsgListPageBtnClick(str);
    ReportFunc.reportDialogClick("message_dialog", 10, this.mPopupDialogInfo.mMessageId);
    return false;
    if (("reservetopic".equals(paramString1)) || ("reserveturnplay".equals(paramString1)))
    {
      showLoadingDialog(null);
      processOtherButtonsOnClick(paramString3, paramObject);
      return false;
    }
    processOtherButtonsOnClick(paramString3, paramObject);
    return false;
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

  protected InputStream xulGetAppData(String paramString)
  {
    Logger.i(TAG, "xulGetAppData path=" + paramString);
    if ("api/get_pop_up_dialog_info".equals(paramString))
      return this.popupDialogInfoStream;
    return null;
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
    findFirstButton();
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

  public void xulRefreshBinding(String paramString1, String paramString2)
  {
    if (this._xulRenderContext == null)
      return;
    this._xulRenderContext.refreshBinding(paramString1, paramString2);
  }

  public void xulRequestFocus(XulView paramXulView)
  {
    if ((this._xulRenderContext == null) || (this._focusStack == null));
    while ((paramXulView == null) || (!paramXulView.isEnabled()) || (!paramXulView.isVisible()) || (!paramXulView.focusable()))
      return;
    this._xulRenderContext.getLayout().requestFocus(paramXulView);
  }

  public static abstract interface MessageDialogListener
  {
    public abstract void dismissDialog();

    public abstract void hideLoadingDialog(Object paramObject);

    public abstract void onViewMsgListPageBtnClick(String paramString);

    public abstract void showLoadingDialog(Object paramObject);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.widget.MessageDialogView
 * JD-Core Version:    0.6.2
 */