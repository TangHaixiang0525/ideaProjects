package com.starcor.hunan;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.domain.SpecialCategoryContent;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.widget.MovieCouponDialog;
import com.starcor.hunan.widget.MovieCouponDialog.MovieCouponOkDialogListener;
import com.starcor.hunan.widget.MovieCouponDialog.MovieCouponTipDialogListener;
import com.starcor.hunan.xiaomi.XiaoMiOAuthManager;
import com.starcor.report.ReportPageInfo;

public class MplayerPayControl
{
  private static final String TAG = MplayerPayControl.class.getSimpleName();
  public String dialogMessage = "";
  private Context mContext;
  public MovieCouponDialog mDialog = null;

  MplayerPayControl(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public void startPay(SpecialCategoryContent paramSpecialCategoryContent, long paramLong, VideoInfo paramVideoInfo)
  {
    Logger.i(TAG, "startPay :" + paramSpecialCategoryContent.video_name);
    PurchaseParam localPurchaseParam = new PurchaseParam(true, paramSpecialCategoryContent.video_id, "0");
    localPurchaseParam.setImport_id(paramSpecialCategoryContent.import_id);
    localPurchaseParam.setIndex(paramSpecialCategoryContent.video_index);
    localPurchaseParam.setVideoName(paramSpecialCategoryContent.video_name);
    localPurchaseParam.setVideoPlayedTime(paramLong);
    localPurchaseParam.setPackageId(paramSpecialCategoryContent.package_id);
    localPurchaseParam.setCategoryId(paramSpecialCategoryContent.category_id);
    localPurchaseParam.specialId = paramSpecialCategoryContent.special_id;
    if (paramVideoInfo != null)
    {
      VideoIndex localVideoIndex = GlobalLogic.getInstance().getCurrentVideoIndex(paramVideoInfo.indexList, paramSpecialCategoryContent.video_index);
      if (localVideoIndex != null)
        localPurchaseParam.index_import_id = localVideoIndex.import_id;
      localPurchaseParam.def = paramVideoInfo.definition;
    }
    if ((this.mContext instanceof DialogActivity))
      localPurchaseParam.setPagename(((DialogActivity)this.mContext).curPageInfo.page);
    GlobalLogic.getInstance().setPurchaseParam(localPurchaseParam);
    GlobalLogic.getInstance().setAutoJumpToDetailedPage(true);
    Intent localIntent = new Intent(this.mContext, XULActivity.class);
    if (TextUtils.isEmpty(GlobalLogic.getInstance().getWebToken()))
    {
      if (DeviceInfo.isXiaoMi())
      {
        XiaoMiOAuthManager.getInstance().startLogin(this.mContext, "PurchaseVIP", null);
        return;
      }
      localIntent.putExtra("xulPageId", "LoginAndRegistration");
    }
    while (true)
    {
      localIntent.addFlags(8388608);
      this.mContext.startActivity(localIntent);
      return;
      localIntent.putExtra("xulPageId", "PurchaseVIP");
    }
  }

  public void useMovieCoupon(final SpecialCategoryContent paramSpecialCategoryContent, final VideoInfo paramVideoInfo, final long paramLong, final MovieCouponDialogListener paramMovieCouponDialogListener)
  {
    String str1 = "观看本片需要使用1张观影券，兑换成功后您可以在观影券有效期内任意观看。";
    String str2 = "影片兑换成功，请您在观影券有效期内观看，逾期需重新兑换或付费。";
    String str3 = "确定兑换";
    String str4 = "立即观看";
    if (paramLong > 0L)
    {
      str1 = "您已试看结束，继续观看本片需要使用1张观影券，兑换成功后您可以在观影券有效期内任意观看。";
      str2 = "影片兑换成功，请您在观影券有效期内观看，逾期需重新兑换或付费。";
      str3 = "确定兑换";
      str4 = "继续观看";
    }
    this.mDialog = new MovieCouponDialog(this.mContext, str1, str2, str3, str4);
    String str5 = str2;
    this.mDialog.setMovieCouponTipDialogListener(new MovieCouponDialog.MovieCouponTipDialogListener()
    {
      public void onCancel()
      {
        this.val$lsr.onCancel();
      }

      public void onOkButtonClick()
      {
        MplayerPayControl.this.mDialog.dissmissDialog();
        if ((GlobalLogic.getInstance().isVip()) && (paramVideoInfo.coupon_count <= 0))
        {
          MplayerPayControl.this.startPay(paramSpecialCategoryContent, paramLong, paramVideoInfo);
          return;
        }
        MplayerPayControl.this.mDialog.startUserCoupon();
        MplayerPayControl.this.dialogMessage = paramMovieCouponDialogListener;
      }
    });
    this.mDialog.setMovieCouponOkDialogListener(new MovieCouponDialog.MovieCouponOkDialogListener()
    {
      public void onCancel()
      {
        paramMovieCouponDialogListener.onSuccess();
      }

      public void onOkButtonClick()
      {
        paramMovieCouponDialogListener.onSuccess();
      }
    });
    this.mDialog.showCouponTipDialog(paramSpecialCategoryContent.import_id);
    this.dialogMessage = str1;
  }

  public static abstract interface MovieCouponDialogListener
  {
    public abstract void onCancel();

    public abstract void onSuccess();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.MplayerPayControl
 * JD-Core Version:    0.6.2
 */