package com.starcor.hunan.widget;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.starcor.core.domain.CommonCateProblem;
import com.starcor.core.domain.CommonProblem;
import com.starcor.core.domain.FAQStruct;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.interfaces.ServiceSelectBtnCallBack;
import com.starcor.sccms.api.SccmsApiGetFAQListTask.ISccmsApiGetFAQListTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.util.ArrayList;

public class CommonProblemView extends LinearLayout
  implements ServiceSelectBtnCallBack
{
  public static final String TAG = "commonProblemView";
  private Context context = null;
  private ArrayList<FAQStruct> list = null;
  private LinearLayout listView = null;
  private ScrollView scrollView = null;
  private ArrayList<FAQStruct> tempList = null;

  public CommonProblemView(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    initSelf();
    initSub();
  }

  private void initListView()
  {
    this.scrollView = new ScrollView(this.context);
    this.listView = new LinearLayout(this.context);
    this.listView.setOrientation(1);
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-1, -1);
    this.scrollView.addView(this.listView, localLayoutParams1);
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-1, -1);
    addView(this.scrollView, localLayoutParams2);
    this.list = new ArrayList();
  }

  private void initSelf()
  {
    setOrientation(1);
    setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
  }

  private void initSub()
  {
    initListView();
    sendMessage2GetDate();
  }

  private void refreshListView(ArrayList<FAQStruct> paramArrayList)
  {
    if (paramArrayList == null)
      return;
    if (this.list == null)
      this.list = new ArrayList();
    this.listView.removeAllViews();
    this.list.removeAll(this.list);
    this.list.addAll(paramArrayList);
    boolean bool = false;
    int i = this.list.size();
    for (int j = 0; j < i; j++)
    {
      if (j == i - 1)
        bool = true;
      ProblemCateItem localProblemCateItem = new ProblemCateItem(this.context, this.scrollView);
      localProblemCateItem.setCateProblemData((FAQStruct)this.list.get(j), bool);
      this.listView.addView(localProblemCateItem);
    }
    LinearLayout localLinearLayout = new LinearLayout(this.context);
    localLinearLayout.setBackgroundColor(0);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, App.OperationHeight(280));
    this.listView.addView(localLinearLayout, localLayoutParams);
  }

  private void sendMessage2GetDate()
  {
    try
    {
      ((Activity)this.context).showDialog(5, null);
      label13: ServerApiManager.i().APIGetFAQList(new SccmsApiGetFAQListTask.ISccmsApiGetFAQListTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.w("commonProblemView", "APIGetFAQList failed!!" + paramAnonymousServerApiCommonError.toString());
          try
          {
            ((Activity)CommonProblemView.this.context).dismissDialog(5);
            return;
          }
          catch (Exception localException)
          {
          }
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<FAQStruct> paramAnonymousArrayList)
        {
          try
          {
            ((Activity)CommonProblemView.this.context).dismissDialog(5);
            label14: CommonProblemView.this.refreshListView(paramAnonymousArrayList);
            return;
          }
          catch (Exception localException)
          {
            break label14;
          }
        }
      });
      return;
    }
    catch (Exception localException)
    {
      break label13;
    }
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    Logger.i("commonProblemView", "dispatchKeyEvent refresh");
    postInvalidate();
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public ArrayList<CommonCateProblem> getTempData()
  {
    ArrayList localArrayList = new ArrayList();
    CommonCateProblem localCommonCateProblem1 = new CommonCateProblem();
    CommonProblem localCommonProblem1 = new CommonProblem();
    localCommonCateProblem1.cateName = "常见类";
    localCommonProblem1.title = "1、 “芒果互联网电视”有哪些客服渠道? ";
    localCommonProblem1.desc = "答：是由湖南卫视全资子公司湖南快乐阳光互动娱乐传媒有限公司重磅推出的全新互联网电视业务。用户通过使用 互联网电视一体机或互联互电视机顶盒，连接公用网络，即可在家中享受海量网络高清影视娱乐服务。";
    localCommonCateProblem1.itemList.add(localCommonProblem1);
    CommonProblem localCommonProblem2 = new CommonProblem();
    localCommonProblem2.title = "2、什么是“芒果网络机顶盒-HM01”? ";
    localCommonProblem2.desc = "答：HM01型机顶盒：是芒果互联网电视运营中心推出的首款高清网络机顶盒。该款机顶盒以家庭媒体中心、家庭互联中心为设计理念，采用安卓智能操作系统，搭载芒果互联网电视内容播控平台，完美实现3D高清播放与多屏互动及空鼠遥控，为用户带来超强体验；在功能方面，该产品支持高清影音播放、3D影片观看、三屏互动、视频通话、体感游戏、网络应用及在线支付等功能。";
    localCommonCateProblem1.itemList.add(localCommonProblem2);
    CommonProblem localCommonProblem3 = new CommonProblem();
    localCommonProblem3.title = "3、什么是芒果通行证、用户入网号、终端MAC地址，通过哪里可以查看?";
    localCommonProblem3.desc = "答：芒果通行证：为芒果网络生态圈统一的用户账号，可同时在金鹰网、他在网、芒果TV及芒果游戏中使用；用户入网号：为15位数字串码；终端MAC：电视机终端唯一编号，由电视机厂家生成。您可以通过进入“芒果互联网电视”主界面，选择进入“我的服务”页，查看相关账号及地址信息。";
    localCommonCateProblem1.itemList.add(localCommonProblem3);
    CommonProblem localCommonProblem4 = new CommonProblem();
    localCommonProblem4.title = "4、通过电视机或机顶盒观看“芒果互联网电视”是否会自动扣费? ";
    localCommonProblem4.desc = "答：您可以免费使用部分业务；收费业务需要您二次确认后才会扣费，请放心使用。";
    localCommonCateProblem1.itemList.add(localCommonProblem4);
    localArrayList.add(localCommonCateProblem1);
    CommonCateProblem localCommonCateProblem2 = new CommonCateProblem();
    localCommonCateProblem2.cateName = "咨询类 ";
    CommonProblem localCommonProblem5 = new CommonProblem();
    localCommonProblem5.title = "1、什么是“芒果互联网电视”?";
    localCommonProblem5.desc = "答：是由湖南卫视全资子公司湖南快乐阳光互动娱乐传媒有限公司重磅推出的全新互联网电视业务。用户通过使用互联网电视一体机或互联互电视机顶盒，连接公用网络，即可在家中享受海量网络高清影视娱乐服务。";
    localCommonCateProblem2.itemList.add(localCommonProblem5);
    CommonProblem localCommonProblem6 = new CommonProblem();
    localCommonProblem6.title = "2、什么是“芒果网络机顶盒-HM01”? ";
    localCommonProblem6.desc = "答：HM01型机顶盒：是芒果互联网电视运营中心推出的首款高清网络机顶盒。该款机顶盒以家庭媒体中心、家庭互联中心为设计理念，采用安卓智能操作系统，搭载芒果互联网电视内容播控平台，完美实现3D高清播放与多屏互动及空鼠遥控，为用户带来超强体验；在功能方面，该产品支持高清影音播放、3D影片观看、三屏互动、视频通话、体感游戏、网络应用及在线支付等功能。";
    localCommonCateProblem2.itemList.add(localCommonProblem6);
    CommonProblem localCommonProblem7 = new CommonProblem();
    localCommonProblem7.title = "3、什么是芒果通行证、用户入网号、终端MAC地址，通过哪里可以查看? ";
    localCommonProblem7.desc = "答：芒果通行证：为芒果网络生态圈统一的用户账号，可同时在金鹰网、他在网、芒果TV及芒果游戏中使用；用户 入网号：为15位数字串码；终端MAC：电视机终端唯一编号，由电视机厂家生成。您可以通过进入“芒果互联网电视” 主界面，选择进入“我的服务”页，查看相关账号及地址信息。";
    localCommonCateProblem2.itemList.add(localCommonProblem7);
    CommonProblem localCommonProblem8 = new CommonProblem();
    localCommonProblem8.title = "4、通过电视机或机顶盒观看“芒果互联网电视”是否会自动扣费? ";
    localCommonProblem8.desc = "答：您可以免费使用部分业务；收费业务需要您二次确认后才会扣费，请放心使用。";
    localCommonCateProblem2.itemList.add(localCommonProblem8);
    CommonProblem localCommonProblem9 = new CommonProblem();
    localCommonProblem9.title = "5、我家里已经有电视机，是否能使用“芒果互联网电视”的服务? ";
    localCommonProblem9.desc = "答：如果您的电视机是我们与电视机厂家合作推出的一体机，则通过电视机主界面，选择“芒果互联网电视”进入 使用即可；如果您的电视机带有网络功能，但不是芒果合作推出的一体机，您可联系客服人员，获取“芒果互联网电视” APK程序包自行安装即可使用；如果您的电视机不具备网络功能，建议您购买芒果网络机顶盒进行观看。";
    localCommonCateProblem2.itemList.add(localCommonProblem9);
    CommonProblem localCommonProblem10 = new CommonProblem();
    localCommonProblem10.title = "6、芒果互联网电视中3D影片如何观看？ ";
    localCommonProblem10.desc = "答：进行芒果平台中的3D影片专区，使用电视机遥控器将信号切换为3D模式，连接电视机自带的3D眼镜（具体使用方法参见电视机说明书），再点击播放需观看的3D影片，戴上3D眼镜即可观看。";
    localCommonCateProblem2.itemList.add(localCommonProblem10);
    CommonProblem localCommonProblem11 = new CommonProblem();
    localCommonProblem11.title = "7、芒果互联网电视中应用如何收费？ ";
    localCommonProblem11.desc = "答：芒果互联网电视中的部分应用由合作伙伴提供，具体应用的收费标准不同，详见相关应用中的资费标准。";
    localCommonCateProblem2.itemList.add(localCommonProblem11);
    CommonProblem localCommonProblem12 = new CommonProblem();
    localCommonProblem12.title = "8、未购买“电影包”“电视剧包”“综艺包”“儿童包”是否能观看该栏目中的内容？";
    localCommonProblem12.desc = "答：只要您开通了基础服务，即可免费使用所有栏目中的免费内容与免费应用。";
    localCommonCateProblem2.itemList.add(localCommonProblem12);
    CommonProblem localCommonProblem13 = new CommonProblem();
    localCommonProblem13.title = "9、什么是“电影包”、“电视剧包”、“综艺包”、“儿童包”？ ";
    localCommonProblem13.desc = "答：收费业务包均为相应栏目中所有收费影片的打包促销优惠，如您购买了“电影包”则可以免费观看该频道中所有收费内容，无需再按次交纳点播费用。该业务包有效期默认为一个月，您可以选择包季、包半年或者直接购买一年。";
    localCommonCateProblem2.itemList.add(localCommonProblem13);
    CommonProblem localCommonProblem14 = new CommonProblem();
    localCommonProblem14.title = "10、单点收费影片的有效期是多久？";
    localCommonProblem14.desc = "答：单点收费影片的有效期默认为72小时，具体影片可能有所不同，详见购买该影片时的提示。";
    localCommonCateProblem2.itemList.add(localCommonProblem14);
    CommonProblem localCommonProblem15 = new CommonProblem();
    localCommonProblem15.title = "11、芒果网络机顶盒的空鼠功能如何使用？ ";
    localCommonProblem15.desc = "答：打开遥控器，按住“确认”键，连续左右甩动几次，当屏幕上出现光标键后即可使用象鼠标一样进行操作";
    localCommonCateProblem2.itemList.add(localCommonProblem15);
    CommonProblem localCommonProblem16 = new CommonProblem();
    localCommonProblem16.title = "12、电源故障怎么办？ ";
    localCommonProblem16.desc = "答：请用户确保适配器电源线正确连接。 ";
    localCommonCateProblem2.itemList.add(localCommonProblem16);
    CommonProblem localCommonProblem17 = new CommonProblem();
    localCommonProblem17.title = "13、图像显示不正常？";
    localCommonProblem17.desc = "答：请用户检查视频输出电缆连接是否正确、牢固。检查视频输出接口是否正确连接。重新启动电视机或者显示设备。";
    localCommonCateProblem2.itemList.add(localCommonProblem17);
    CommonProblem localCommonProblem18 = new CommonProblem();
    localCommonProblem18.title = "14、没有声音？";
    localCommonProblem18.desc = "请用户检查音频输出电缆连接是否正确、牢固。请确保没有启用“静音”功能。检查电视机或者功放和音响的声音设置是否正确。如果采用电视机的HDMI输出视频，无声音输出，或者本机模拟6声道无声音，请尝试使用同轴数字音频接口 （COAXIAL）或光纤数字音频接口（OPTICAL）输出声音信号，利用数字功放进行声音解码。";
    localCommonCateProblem2.itemList.add(localCommonProblem18);
    CommonProblem localCommonProblem19 = new CommonProblem();
    localCommonProblem19.title = "15、遥控器操作问题？ ";
    localCommonProblem19.desc = "请用户确保电池已按正确极性安装（正极对正极，负极对负极）。更换新的电池。保证遥控器与播放机之间没有障碍物。保证遥控器在有效的遥控距离之内。";
    localCommonCateProblem2.itemList.add(localCommonProblem19);
    CommonProblem localCommonProblem20 = new CommonProblem();
    localCommonProblem20.title = "16、HDMI 数字接口视频输出有干扰？";
    localCommonProblem20.desc = "请用户检查连接是否有松动。尝试更换HDMI线。";
    localCommonCateProblem2.itemList.add(localCommonProblem20);
    localArrayList.add(localCommonCateProblem2);
    CommonCateProblem localCommonCateProblem3 = new CommonCateProblem();
    localCommonCateProblem3.cateName = "投诉类 ";
    CommonProblem localCommonProblem21 = new CommonProblem();
    localCommonProblem21.title = "1、提示网络未连接？ ";
    localCommonProblem21.desc = "答：处理方式：请检查网线是否已正常连接；请检查网络设置是否正确配置；请使用电视中其他需连网应用查看是否能正常使用；请检查网络提示灯是否显示正常；如仍提示该错误，则按照用户投诉类问题处理。";
    localCommonCateProblem3.itemList.add(localCommonProblem21);
    CommonProblem localCommonProblem22 = new CommonProblem();
    localCommonProblem22.title = "2、提示网络超时？ ";
    localCommonProblem22.desc = "答：处理方式：请检查网络是否正常连接；如仍提示该错误，则按照用户投诉类问题处理。";
    localCommonCateProblem3.itemList.add(localCommonProblem22);
    CommonProblem localCommonProblem23 = new CommonProblem();
    localCommonProblem23.title = "3、提示获取EPG数据失败？ ";
    localCommonProblem23.desc = "答：处理方式：请检查网络是否正常连接；如仍提示该错误，则按照用户投诉类问题处理。";
    localCommonCateProblem3.itemList.add(localCommonProblem23);
    CommonProblem localCommonProblem24 = new CommonProblem();
    localCommonProblem24.title = "4、芒果互联网电视”应用程序无响应,系统提示ANR错误？";
    localCommonProblem24.desc = "答：处理方式：应用程序出现ANR错误，请重新启动机顶盒；如仍提示该错误，则按照用户投诉类问题处理。";
    localCommonCateProblem3.itemList.add(localCommonProblem24);
    CommonProblem localCommonProblem25 = new CommonProblem();
    localCommonProblem25.title = "5、提示超出收藏数量？";
    localCommonProblem25.desc = "答：处理方式：收藏节目数量达到最大限制，请删除收藏列表中不需要的记录，再尝试收藏操作。";
    localCommonCateProblem3.itemList.add(localCommonProblem25);
    CommonProblem localCommonProblem26 = new CommonProblem();
    localCommonProblem26.title = "6、提示正在支付？ ";
    localCommonProblem26.desc = "答：处理方式：请用户重启机器后，再次尝试操作，如仍存在问题，则则按照用户投诉类问题处理。";
    localCommonCateProblem3.itemList.add(localCommonProblem26);
    localArrayList.add(localCommonCateProblem3);
    return localArrayList;
  }

  public void moveToTop()
  {
    if (this.tempList == null)
      sendMessage2GetDate();
    while (true)
    {
      this.scrollView.scrollTo(0, 0);
      return;
      refreshListView(this.tempList);
    }
  }

  public void setNextLeftButton(HighLightButton paramHighLightButton)
  {
    int i = this.listView.getChildCount();
    for (int j = 0; j < i - 1; j++)
    {
      ProblemCateItem localProblemCateItem = (ProblemCateItem)this.listView.getChildAt(j);
      int k = localProblemCateItem.getChildCount();
      for (int m = 2; m < k; m++)
      {
        ProblemItem localProblemItem = (ProblemItem)localProblemCateItem.getChildAt(m);
        localProblemItem.getTitleView().setNextFocusLeftId(paramHighLightButton.getId());
        localProblemItem.scrollView.setNextFocusLeftId(paramHighLightButton.getId());
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.CommonProblemView
 * JD-Core Version:    0.6.2
 */