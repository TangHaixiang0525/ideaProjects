package com.starcor.hunan.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.remote_key.AirControlHost;
import com.starcor.remote_key.AirControlHost.InitParams;

public class DeviceNameView extends LinearLayout
{
  private String TAG = "DeviceNameView";
  String devicename;
  protected boolean isSafeDeviceName;
  private boolean isShoulSafeDeviceName;
  private Context mContext;
  String[] names = { "自定义", "芒果TV", "客厅的芒果TV", "卧室的芒果", "办公室的芒果TV", "不知道哪的芒果TV" };
  DeviceItemView selectedView;

  public DeviceNameView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    init();
  }

  private void init()
  {
    setOrientation(1);
    TextView localTextView1 = new TextView(getContext());
    localTextView1.setText("命名你的设备");
    localTextView1.setTextSize(0, App.Operation(23.0F));
    localTextView1.getPaint().setFakeBoldText(true);
    localTextView1.setTextColor(-1);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    localLayoutParams.bottomMargin = App.Operation(30.0F);
    addView(localTextView1, localLayoutParams);
    int i = 0;
    if (i < this.names.length)
    {
      Context localContext = getContext();
      if (i == 0);
      for (boolean bool = true; ; bool = false)
      {
        final DeviceItemView localDeviceItemView = new DeviceItemView(localContext, bool);
        localDeviceItemView.setData(this.names[i], DeviceInfo.getMac());
        localDeviceItemView.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (paramAnonymousView == DeviceNameView.this.selectedView)
              return;
            localDeviceItemView.setSelected(true);
            if (DeviceNameView.this.selectedView != null)
            {
              if (DeviceNameView.this.selectedView.hasEdit)
                DeviceNameView.this.selectedView.setBackgroundResource(2130837679);
              DeviceNameView.this.selectedView.setSelected(false);
            }
            DeviceNameView.this.selectedView = localDeviceItemView;
            DeviceNameView.this.devicename = localDeviceItemView.nameText.getText().toString();
            DeviceNameView.access$002(DeviceNameView.this, true);
          }
        });
        addView(localDeviceItemView, new LinearLayout.LayoutParams(-1, -2));
        if (i < -1 + this.names.length)
        {
          TextView localTextView2 = new TextView(getContext());
          localTextView2.setBackgroundColor(-7829368);
          addView(localTextView2, new LinearLayout.LayoutParams(-1, 1));
        }
        i++;
        break;
      }
    }
  }

  private void resetAirControlHost(String paramString)
  {
    if (!AppFuncCfg.FUNCTION_ENABLE_AIR_CONTROL)
      return;
    if (TextUtils.isEmpty(AppInfo.URL_n31_a))
    {
      Logger.e(this.TAG, "n31_a is empty!!!");
      return;
    }
    AirControlHost.InitParams localInitParams = new AirControlHost.InitParams();
    localInitParams.deviceId = DeviceInfo.getMac();
    localInitParams.deviceName = paramString;
    Logger.i(this.TAG, "deviceName  ==" + paramString);
    localInitParams.mac = DeviceInfo.getMac();
    localInitParams.url = webUrlFormatter.i().formatUrl(AppInfo.URL_n31_a, "N31_A");
    Logger.d(this.TAG, "initAirControl : " + localInitParams.toString());
    AirControlHost.startUp(this.mContext, localInitParams);
  }

  private void showDialog(String paramString)
  {
    CommDialog localCommDialog = new CommDialog(this.mContext, 2131296258);
    localCommDialog.setMessage(paramString);
    localCommDialog.setType(1);
    localCommDialog.setTitle("提示  按“返回”键取消");
    localCommDialog.setNegativeButton(new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        DeviceNameView.access$002(DeviceNameView.this, false);
        paramAnonymousDialogInterface.dismiss();
      }
    });
    localCommDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        int i = paramAnonymousKeyEvent.getAction();
        boolean bool = false;
        if (i == 0)
        {
          bool = false;
          if (paramAnonymousInt == 4)
          {
            DeviceNameView.access$002(DeviceNameView.this, false);
            paramAnonymousDialogInterface.dismiss();
            bool = true;
          }
        }
        return bool;
      }
    });
    localCommDialog.show();
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if ((paramKeyEvent.getAction() == 0) && (paramKeyEvent.getKeyCode() == 4) && (this.isShoulSafeDeviceName) && (this.devicename != null))
    {
      GeneralUtils.writePreferences("devicename", this.devicename, this.mContext);
      Logger.i(this.TAG, "devicename==writePreferences_" + this.devicename);
      AirControlHost.shutdown();
      resetAirControlHost(GeneralUtils.readPreferences("devicename", null, this.mContext));
      showDialog("当前设备命名为：" + GeneralUtils.readPreferences("devicename", null, this.mContext));
      return true;
    }
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  class DeviceItemView extends LinearLayout
  {
    EditText editText;
    String edittextname;
    TextView flagtext;
    boolean hasEdit;
    TextView macText;
    TextView nameText;

    DeviceItemView(Context paramBoolean, boolean arg3)
    {
      super();
      boolean bool;
      this.hasEdit = bool;
      setOrientation(0);
      setFocusable(true);
      setBackgroundResource(2130837537);
      setPadding(App.Operation(50.0F), App.Operation(20.0F), App.Operation(50.0F), App.Operation(20.0F));
      this.nameText = new TextView(getContext());
      this.nameText.setTextSize(0, App.Operation(20.0F));
      this.nameText.setTextColor(-1);
      LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-2, -2);
      addView(this.nameText, localLayoutParams1);
      if (bool)
      {
        setFocusable(false);
        this.flagtext = new TextView(getContext());
        this.flagtext.setTextSize(0, App.Operation(20.0F));
        this.flagtext.setTextColor(-1);
        this.flagtext.setText(":");
        LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        addView(this.flagtext, localLayoutParams3);
        this.editText = new EditText(getContext());
        this.editText.setTextSize(0, App.Operation(20.0F));
        this.editText.setSingleLine();
        this.editText.setTextColor(-1);
        this.editText.setText(GeneralUtils.readPreferences("deviceeditText", null, DeviceNameView.this.mContext));
        this.editText.setHint("输入自定义名称");
        this.editText.setPadding(App.Operation(20.0F), 0, App.Operation(20.0F), 0);
        this.editText.setBackgroundResource(2130837679);
        this.editText.addTextChangedListener(new TextWatcher()
        {
          public void afterTextChanged(Editable paramAnonymousEditable)
          {
          }

          public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
          {
          }

          public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
          {
            DeviceNameView.this.devicename = DeviceNameView.DeviceItemView.this.editText.getText().toString();
          }
        });
        this.editText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
          public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
          {
            if (paramAnonymousBoolean)
            {
              DeviceNameView.DeviceItemView.this.setBackgroundResource(2130837631);
              DeviceNameView.DeviceItemView.this.flagtext.setVisibility(0);
            }
            while (true)
            {
              DeviceNameView.DeviceItemView.this.setPadding(App.Operation(50.0F), App.Operation(20.0F), App.Operation(50.0F), App.Operation(20.0F));
              return;
              DeviceNameView.DeviceItemView.this.flagtext.setVisibility(4);
              if (DeviceNameView.DeviceItemView.this.isSelected())
              {
                DeviceNameView.this.devicename = DeviceNameView.DeviceItemView.this.editText.getText().toString();
                DeviceNameView.DeviceItemView.this.edittextname = DeviceNameView.this.devicename;
                DeviceNameView.DeviceItemView.this.setBackgroundResource(2130837536);
                if (DeviceNameView.DeviceItemView.this.edittextname != null)
                  GeneralUtils.writePreferences("deviceeditText", DeviceNameView.DeviceItemView.this.edittextname, DeviceNameView.this.mContext);
              }
              else
              {
                DeviceNameView.DeviceItemView.this.setBackgroundResource(2130837679);
              }
            }
          }
        });
        this.editText.setOnKeyListener(new View.OnKeyListener()
        {
          public boolean onKey(View paramAnonymousView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
          {
            if (DeviceNameView.DeviceItemView.this == DeviceNameView.this.selectedView);
            do
            {
              return false;
              switch (paramAnonymousInt)
              {
              default:
                return false;
              case 23:
              case 66:
              }
            }
            while (paramAnonymousKeyEvent.getAction() != 0);
            DeviceNameView.DeviceItemView.this.setSelected(true);
            if (DeviceNameView.this.selectedView != null)
              DeviceNameView.this.selectedView.setSelected(false);
            DeviceNameView.this.selectedView = DeviceNameView.DeviceItemView.this;
            DeviceNameView.access$002(DeviceNameView.this, true);
            return false;
          }
        });
        LinearLayout.LayoutParams localLayoutParams4 = new LinearLayout.LayoutParams(-2, -2);
        addView(this.editText, localLayoutParams4);
      }
      while (true)
      {
        this.macText = new TextView(getContext());
        this.macText.setTextSize(0, App.Operation(20.0F));
        this.macText.setTextColor(-1);
        this.macText.setGravity(5);
        this.macText.setVisibility(4);
        this.macText.setPadding(App.Operation(250.0F), 0, 0, 0);
        LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-1, -2);
        addView(this.macText, localLayoutParams2);
        return;
        setFocusable(true);
      }
    }

    void setData(String paramString1, String paramString2)
    {
      this.nameText.setText(paramString1);
      this.macText.setText("MAC:" + paramString2);
    }

    public void setSelected(boolean paramBoolean)
    {
      super.setSelected(paramBoolean);
      TextView localTextView = this.macText;
      if (paramBoolean);
      for (int i = 0; ; i = 4)
      {
        localTextView.setVisibility(i);
        return;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.DeviceNameView
 * JD-Core Version:    0.6.2
 */