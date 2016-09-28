package com.starcor.hunan.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.core.domain.CityInfoById;
import com.starcor.core.domain.CityItemById;
import com.starcor.core.domain.CityStruct;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.service.DownLoadService;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.ServiceActivity;
import com.starcor.hunan.interfaces.ServiceSelectBtnCallBack;
import com.starcor.sccms.api.SccmsApiGetCityInfoTask.ISccmsApiGetCityListTaskListener;
import com.starcor.sccms.api.SccmsApiGetCityListTask.ISccmsApiGetCityListTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.util.ArrayList;
import java.util.Iterator;

public class CityPadView extends LinearLayout
  implements ServiceSelectBtnCallBack
{
  private static final String TAG = CityPadView.class.getSimpleName();
  private static boolean isLastCity = false;
  private static String lastId;
  public static CityItemView lastView;
  private int cityItemId = 10000;
  private CityStruct cityStruct;
  private boolean isCityChanged = false;
  Context mContext;
  private DownLoadService mDownLoadService;
  private HighLightButton rightText;
  private LinearLayout tv_pad_content;
  private TextView tv_pad_notice;
  private TextView tv_pad_title;

  public CityPadView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    initView();
  }

  public CityPadView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    initView();
  }

  private void displaySelectedCityInfo()
  {
    CityItemView localCityItemView = (CityItemView)findViewById(10000);
    if ((localCityItemView == null) || (!this.isCityChanged))
      return;
    String str1 = "您已选择了：  ";
    while (true)
    {
      String str2;
      if (localCityItemView != null)
      {
        str2 = localCityItemView.getCityName();
        if (str2 != null);
      }
      else
      {
        if (str1.length() >= "  >  ".length())
          str1 = str1.substring(0, str1.length() - "  >  ".length());
        addTitle(str1);
        return;
      }
      str1 = str1 + str2 + "  >  ";
      Logger.i("serviceActivity地区设置", "serviceActivity displaySelectedCityInfo(),MSG_GET_SELCITY_INFO,cityInfo:" + str1);
      localCityItemView = localCityItemView.getNext();
    }
  }

  private void initView()
  {
    LinearLayout localLinearLayout = (LinearLayout)View.inflate(this.mContext, 2130903055, this);
    this.tv_pad_title = ((TextView)localLinearLayout.findViewById(2131165272));
    this.tv_pad_notice = ((TextView)findViewById(2131165274));
    this.tv_pad_notice.setText("按\"返回键\"取消");
    this.tv_pad_content = ((LinearLayout)localLinearLayout.findViewById(2131165273));
    this.tv_pad_title.setText("您已经选择：");
    this.tv_pad_content.getLayoutParams().width = App.OperationHeight(200);
    TextView localTextView = (TextView)localLinearLayout.findViewById(2131165274);
    localTextView.setTextSize(0, App.OperationHeight(22));
    localTextView.setPadding(0, App.OperationHeight(20), 0, App.OperationHeight(40));
    this.tv_pad_title.getLayoutParams().width = App.OperationHeight(800);
    this.tv_pad_title.setPadding(0, 0, 0, App.OperationHeight(40));
    this.tv_pad_title.setTextColor(-1);
    this.tv_pad_title.setTextSize(0, App.OperationHeight(22));
    this.mDownLoadService = App.getInstance().getTaskService();
    initViewControl();
    getSelectedCityInfo();
    getCityList("");
  }

  private void loadSelectedCityInfo(CityInfoById paramCityInfoById)
  {
    Logger.d("根据Id获取城市数据");
    if (paramCityInfoById == null)
      return;
    Logger.d(paramCityInfoById.getCount() + "--" + paramCityInfoById.getCityItems().size());
    String str = "您已选择了：";
    Iterator localIterator = paramCityInfoById.getCityItems().iterator();
    while (localIterator.hasNext())
    {
      CityItemById localCityItemById = (CityItemById)localIterator.next();
      Logger.d(str);
      str = str + localCityItemById.getName() + "  >  ";
      Logger.i("serviceActivity地区设置", "CityPadView,handleMessage(),MSG_GET_SELCITY_INFO,name:" + str);
    }
    if (str.length() >= "  >  ".length())
      str = str.substring(0, str.length() - "  >  ".length());
    this.tv_pad_title.setText(str);
  }

  public static void setLastCity(boolean paramBoolean)
  {
    isLastCity = paramBoolean;
  }

  public static void setLastId(String paramString)
  {
    lastId = paramString;
  }

  public void addContent(View paramView)
  {
    this.tv_pad_content.addView(paramView);
  }

  public void addFristTitle(String paramString)
  {
    this.tv_pad_title.setText(this.tv_pad_title.getText().toString().trim() + paramString);
  }

  public void addTitle(String paramString)
  {
    this.tv_pad_title.setText(paramString);
  }

  public void getCityInfo(String paramString)
  {
    ((ServiceActivity)this.mContext).isShouldSafeCityData = true;
    getCityList(paramString);
  }

  public void getCityList(String paramString)
  {
    ServerApiManager.i().APIGetCityList(paramString, new SccmsApiGetCityListTask.ISccmsApiGetCityListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        CityPadView.this.loadCityList(null);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, CityStruct paramAnonymousCityStruct)
      {
        CityPadView.this.loadCityList(paramAnonymousCityStruct);
      }
    });
  }

  public String getLastId()
  {
    return lastId;
  }

  public void getSelectedCityInfo()
  {
    String str = GlobalEnv.getInstance().getCityId();
    if (TextUtils.isEmpty(str))
      return;
    ServerApiManager.i().APIGetCityInfo(str, new SccmsApiGetCityInfoTask.ISccmsApiGetCityListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.w(CityPadView.TAG, "APIGetCityInfo failed! " + paramAnonymousServerApiCommonError.toString());
        CityPadView.this.loadSelectedCityInfo(null);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, CityInfoById paramAnonymousCityInfoById)
      {
        CityPadView.this.loadSelectedCityInfo(paramAnonymousCityInfoById);
      }
    });
  }

  public void initViewControl()
  {
    lastView = null;
    setLastId(null);
    this.isCityChanged = false;
  }

  public boolean isLastCity()
  {
    return isLastCity;
  }

  public void loadCityList(CityStruct paramCityStruct)
  {
    setLastCity(false);
    this.cityStruct = paramCityStruct;
    if (this.cityStruct == null)
      return;
    int i = this.cityStruct.getCount();
    Logger.d("loadCityList(),cityCount=" + i);
    if (i == 0)
    {
      Logger.d("loadCityList(),到了最低层城市");
      displaySelectedCityInfo();
      this.isCityChanged = true;
      setLastCity(true);
      return;
    }
    CityItemView localCityItemView;
    if ((lastView != null) && (lastView.hasNext()))
      localCityItemView = lastView.getNext();
    while (true)
    {
      localCityItemView.setTitle(this.cityStruct.getCity_type());
      LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)localCityItemView.getLayoutParams();
      localLayoutParams.height = App.OperationHeight(480);
      localLayoutParams.width = App.OperationHeight(250);
      localCityItemView.setData(this.cityStruct);
      localCityItemView.changeView();
      localCityItemView.setVisibility(0);
      if (lastView != null)
      {
        localCityItemView.setNextFocusLeftId(lastView.selectView.getId());
        lastView.showArrow();
        lastView.setNext(localCityItemView);
      }
      displaySelectedCityInfo();
      this.isCityChanged = true;
      return;
      localCityItemView = new CityItemView(this.mContext);
      localCityItemView.setOnItemListener(new CityItemView.CityClickListener()
      {
        public void onCityClick(String paramAnonymousString)
        {
          CityPadView.this.getCityInfo(paramAnonymousString);
        }
      });
      localCityItemView.setId(this.cityItemId);
      this.cityItemId = (1 + this.cityItemId);
      addContent(localCityItemView);
      localCityItemView.setLastView(lastView);
    }
  }

  public void setNextLeftButton(HighLightButton paramHighLightButton)
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.CityPadView
 * JD-Core Version:    0.6.2
 */