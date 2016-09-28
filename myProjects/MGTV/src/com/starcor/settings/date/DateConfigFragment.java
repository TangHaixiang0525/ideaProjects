package com.starcor.settings.date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.starcor.setting.service.ISettingService;
import com.starcor.settings.R.color;
import com.starcor.settings.R.id;
import com.starcor.settings.R.layout;
import com.starcor.settings.SettingActivity;
import com.starcor.settings.utils.Debug;
import com.starcor.settings.utils.Tools;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateConfigFragment extends Fragment
{
  private static final String DATE_PREF = "date_pref";
  private static final String IS_AUTO_DADE = "is_auto_dade";
  public static final String TAG = DateConfigFragment.class.getSimpleName();
  private ImageView arrowLeft;
  private ImageView arrowRight;
  private View autoDateView;
  private String btnText;
  private boolean canPageDown = false;
  private boolean canPageUp = false;
  private CheckBox dateCheckBox;
  View.OnClickListener dateClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      DateConfigFragment.this.setDateTimeView.setSelected(true);
      DateConfigFragment.this.displayTimeView.setVisibility(0);
      DateConfigFragment.this.save.setVisibility(0);
    }
  };
  private TextView dateTimeText;
  private Button dayBtn;
  private GridView digitPicker;
  private View digitView;
  private View displayTimeView;
  private Button hourBtn;
  private boolean isLastItem = false;
  private DateHelper mDateHelper;
  private DigitType mDigitType;
  private int mFirstDigit = -1;
  private Button minuteBtn;
  private Button monthBtn;
  private TextView save;
  private TextView setDateTimeText;
  private View setDateTimeView;
  private Button yearBtn;

  private void checkisAutoDate()
  {
    if (getActivity().getSharedPreferences("date_pref", 0).getBoolean("is_auto_dade", true))
    {
      this.dateCheckBox.setChecked(true);
      this.setDateTimeText.setTextColor(getResources().getColor(R.color.dark_gray));
      this.setDateTimeView.setOnClickListener(null);
      this.mDateHelper.refreshTimeFromNetwork(this);
      return;
    }
    this.dateCheckBox.setChecked(false);
    this.setDateTimeText.setTextColor(getResources().getColor(17170443));
    this.setDateTimeView.setOnClickListener(this.dateClickListener);
  }

  private void initBaseLayout(View paramView)
  {
    this.autoDateView = paramView.findViewById(R.id.auto_confirm_date_containter);
    this.dateCheckBox = ((CheckBox)paramView.findViewById(R.id.auto_date_checkbox));
    this.setDateTimeView = paramView.findViewById(R.id.set_time_and_date_containter);
    this.setDateTimeText = ((TextView)paramView.findViewById(R.id.set_date_and_time));
    this.dateTimeText = ((TextView)paramView.findViewById(R.id.date_and_time));
    this.displayTimeView = paramView.findViewById(R.id.display_time_layout);
    this.digitView = paramView.findViewById(R.id.digit_layout);
    this.autoDateView.setVisibility(8);
    this.setDateTimeText.setTextColor(getResources().getColor(17170443));
    this.setDateTimeView.setOnClickListener(this.dateClickListener);
  }

  private void initDigitPicker(View paramView)
  {
    this.digitPicker = ((GridView)paramView.findViewById(R.id.gridView));
    this.save = ((TextView)paramView.findViewById(R.id.save));
    this.digitPicker.setOnKeyListener(new View.OnKeyListener()
    {
      private void nextYearPage()
      {
        DateConfigFragment localDateConfigFragment = DateConfigFragment.this;
        localDateConfigFragment.mFirstDigit = (32 + localDateConfigFragment.mFirstDigit);
        if (DateConfigFragment.this.mFirstDigit > 2066)
        {
          DateConfigFragment.this.mFirstDigit = 2066;
          if (DateConfigFragment.this.mFirstDigit <= 2065)
            break label106;
          DateConfigFragment.this.arrowRight.setVisibility(4);
        }
        while (true)
        {
          DateConfigFragment.this.arrowLeft.setVisibility(0);
          return;
          DateConfigFragment.this.digitPicker.setSelection(0);
          DateConfigFragment.this.refreshDigitsPicker(DateConfigFragment.DigitType.YEAR, DateConfigFragment.this.mFirstDigit);
          break;
          label106: DateConfigFragment.this.arrowRight.setVisibility(0);
        }
      }

      private void prevYearPage()
      {
        DateConfigFragment localDateConfigFragment = DateConfigFragment.this;
        localDateConfigFragment.mFirstDigit = (-32 + localDateConfigFragment.mFirstDigit);
        if (DateConfigFragment.this.mFirstDigit < 1970)
        {
          DateConfigFragment.this.mFirstDigit = 1970;
          if (DateConfigFragment.this.mFirstDigit >= 2002)
            break label106;
          DateConfigFragment.this.arrowLeft.setVisibility(4);
        }
        while (true)
        {
          DateConfigFragment.this.arrowRight.setVisibility(0);
          return;
          DateConfigFragment.this.digitPicker.setSelection(0);
          DateConfigFragment.this.refreshDigitsPicker(DateConfigFragment.DigitType.YEAR, DateConfigFragment.this.mFirstDigit);
          break;
          label106: DateConfigFragment.this.arrowLeft.setVisibility(0);
        }
      }

      public boolean onKey(View paramAnonymousView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((DateConfigFragment.this.isLastItem) && (paramAnonymousKeyEvent.getAction() == 0) && ((paramAnonymousInt == 20) || (paramAnonymousInt == 22)))
        {
          Debug.e(DateConfigFragment.TAG, "缈讳笅涓�椤�");
          if (DateConfigFragment.this.mDigitType == DateConfigFragment.DigitType.YEAR)
            nextYearPage();
        }
        do
        {
          do
          {
            do
              return true;
            while ((DateConfigFragment.this.mDigitType != DateConfigFragment.DigitType.MINUTE) || (DateConfigFragment.this.mFirstDigit != 0));
            DateConfigFragment.this.digitPicker.setSelection(0);
            DateConfigFragment.this.refreshDigitsPicker(DateConfigFragment.DigitType.MINUTE, 1);
            return true;
            if ((!DateConfigFragment.this.canPageDown) || (paramAnonymousKeyEvent.getAction() != 0) || (paramAnonymousInt != 22))
              break;
            Debug.e(DateConfigFragment.TAG, "缈讳笅涓�椤�");
            if (DateConfigFragment.this.mDigitType == DateConfigFragment.DigitType.YEAR)
            {
              nextYearPage();
              return true;
            }
          }
          while ((DateConfigFragment.this.mDigitType != DateConfigFragment.DigitType.MINUTE) || (DateConfigFragment.this.mFirstDigit != 0));
          DateConfigFragment.this.arrowLeft.setVisibility(0);
          DateConfigFragment.this.arrowRight.setVisibility(4);
          DateConfigFragment.this.digitPicker.setSelection(0);
          DateConfigFragment.this.refreshDigitsPicker(DateConfigFragment.DigitType.MINUTE, 1);
          return true;
          if ((!DateConfigFragment.this.canPageUp) || (paramAnonymousKeyEvent.getAction() != 0) || (paramAnonymousInt != 21))
            break;
          Debug.e(DateConfigFragment.TAG, "缈讳笂涓�椤�");
          if (DateConfigFragment.this.mDigitType == DateConfigFragment.DigitType.YEAR)
          {
            prevYearPage();
            return true;
          }
        }
        while ((DateConfigFragment.this.mDigitType != DateConfigFragment.DigitType.MINUTE) || (DateConfigFragment.this.mFirstDigit != 1));
        DateConfigFragment.this.arrowLeft.setVisibility(4);
        DateConfigFragment.this.arrowRight.setVisibility(0);
        DateConfigFragment.this.digitPicker.setSelection(0);
        DateConfigFragment.this.refreshDigitsPicker(DateConfigFragment.DigitType.MINUTE, 0);
        return true;
        return false;
      }
    });
    this.digitPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        TextView localTextView = (TextView)paramAnonymousView.findViewById(R.id.digit);
        label80: label117: DateConfigFragment localDateConfigFragment;
        if (localTextView.getText().toString().equals(DateConfigFragment.this.btnText))
        {
          localTextView.setBackgroundColor(0);
          if (paramAnonymousInt % 8 != 7)
            break label195;
          Debug.w(DateConfigFragment.TAG, "鍙互缈讳笅椤�");
          if (DateConfigFragment.this.mDigitType != DateConfigFragment.DigitType.YEAR)
            break label171;
          DateConfigFragment.this.canPageDown = true;
          if (paramAnonymousInt % 8 != 0)
            break label230;
          Debug.w(DateConfigFragment.TAG, "鍙互缈讳笂椤�");
          if (DateConfigFragment.this.mDigitType != DateConfigFragment.DigitType.YEAR)
            break label206;
          DateConfigFragment.this.canPageUp = true;
          localDateConfigFragment = DateConfigFragment.this;
          if (paramAnonymousInt != -1 + ((Adapter)paramAnonymousAdapterView.getAdapter()).getCount())
            break label241;
        }
        label171: label195: label206: label230: label241: for (boolean bool = true; ; bool = false)
        {
          localDateConfigFragment.isLastItem = bool;
          return;
          ((BaseAdapter)DateConfigFragment.this.digitPicker.getAdapter()).notifyDataSetChanged();
          break;
          if (DateConfigFragment.this.mDigitType != DateConfigFragment.DigitType.MINUTE)
            break label80;
          DateConfigFragment.this.canPageDown = true;
          break label80;
          DateConfigFragment.this.canPageDown = false;
          break label80;
          if (DateConfigFragment.this.mDigitType != DateConfigFragment.DigitType.MINUTE)
            break label117;
          DateConfigFragment.this.canPageUp = true;
          break label117;
          DateConfigFragment.this.canPageUp = false;
          break label117;
        }
      }

      public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView)
      {
      }
    });
    this.digitPicker.setAdapter(new BaseAdapter()
    {
      public int getCount()
      {
        return DateConfigFragment.this.mDateHelper.getDigits().size();
      }

      public Object getItem(int paramAnonymousInt)
      {
        return DateConfigFragment.this.mDateHelper.getDigits().get(paramAnonymousInt);
      }

      public long getItemId(int paramAnonymousInt)
      {
        return paramAnonymousInt;
      }

      public View getView(int paramAnonymousInt, View paramAnonymousView, ViewGroup paramAnonymousViewGroup)
      {
        if (paramAnonymousView == null)
          paramAnonymousView = View.inflate(DateConfigFragment.this.getActivity(), R.layout.date_grid_item, null);
        TextView localTextView = (TextView)paramAnonymousView.findViewById(R.id.digit);
        String str = String.valueOf(DateConfigFragment.this.mDateHelper.getDigits().get(paramAnonymousInt));
        localTextView.setText(str);
        if (str.equals(DateConfigFragment.this.btnText))
        {
          localTextView.setBackgroundColor(-14206892);
          return paramAnonymousView;
        }
        localTextView.setBackgroundColor(0);
        return paramAnonymousView;
      }
    });
    this.digitPicker.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        TextView localTextView = (TextView)paramAnonymousView.findViewById(R.id.digit);
        for (int i = 0; ; i++)
        {
          if (i >= DateConfigFragment.this.digitPicker.getChildCount())
          {
            localTextView.setBackgroundColor(-14206892);
            DateConfigFragment.this.btnText = localTextView.getText().toString();
            int j = Integer.parseInt(localTextView.getText().toString());
            DateConfigFragment.this.mDateHelper.setTimeInMillis(DateConfigFragment.this.mDigitType, j);
            DateConfigFragment.this.setTime();
            return;
          }
          ((TextView)DateConfigFragment.this.digitPicker.getChildAt(i).findViewById(R.id.digit)).setBackgroundColor(0);
        }
      }
    });
    this.digitPicker.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if (!paramAnonymousBoolean);
      }
    });
    this.save.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ISettingService localISettingService = ((SettingActivity)DateConfigFragment.this.getActivity()).getSettingService();
        try
        {
          boolean bool2 = localISettingService.setCurrentTimeMillis(DateConfigFragment.this.mDateHelper.getCalendar().getTimeInMillis());
          bool1 = bool2;
          FragmentActivity localFragmentActivity = DateConfigFragment.this.getActivity();
          if (bool1)
          {
            str = "淇濆瓨鎴愬姛";
            Tools.showDialog(localFragmentActivity, "鎻愮ず", str);
            return;
          }
        }
        catch (RemoteException localRemoteException)
        {
          while (true)
          {
            Debug.w(DateConfigFragment.TAG, localRemoteException.getMessage(), localRemoteException);
            boolean bool1 = false;
            continue;
            String str = "淇濆瓨澶辫触";
          }
        }
      }
    });
  }

  private void initTimeButton(View paramView)
  {
    this.arrowLeft = ((ImageView)paramView.findViewById(R.id.arrow_left));
    this.arrowRight = ((ImageView)paramView.findViewById(R.id.arrow_right));
    this.arrowLeft.setVisibility(4);
    this.arrowRight.setVisibility(4);
    this.yearBtn = ((Button)paramView.findViewById(R.id.year_btn));
    this.monthBtn = ((Button)paramView.findViewById(R.id.month_btn));
    this.dayBtn = ((Button)paramView.findViewById(R.id.day_btn));
    this.hourBtn = ((Button)paramView.findViewById(R.id.hour_btn));
    this.minuteBtn = ((Button)paramView.findViewById(R.id.minute_btn));
    this.yearBtn.setNextFocusDownId(R.id.save);
    this.monthBtn.setNextFocusDownId(R.id.save);
    this.dayBtn.setNextFocusDownId(R.id.save);
    this.hourBtn.setNextFocusDownId(R.id.save);
    this.minuteBtn.setNextFocusDownId(R.id.save);
    this.yearBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DateConfigFragment.this.btnText = DateConfigFragment.this.yearBtn.getText().toString();
        DateConfigFragment.this.btnText = DateConfigFragment.this.btnText.substring(0, -1 + DateConfigFragment.this.btnText.length());
        int i = (-1970 + Integer.parseInt(DateConfigFragment.this.btnText)) / 32;
        int j = 1970 + i * 32;
        if (i == 0)
        {
          DateConfigFragment.this.arrowLeft.setVisibility(4);
          DateConfigFragment.this.arrowRight.setVisibility(0);
        }
        while (true)
        {
          DateConfigFragment.this.refreshDigitsPicker(DateConfigFragment.DigitType.YEAR, j);
          return;
          if (i == 3)
          {
            DateConfigFragment.this.arrowLeft.setVisibility(0);
            DateConfigFragment.this.arrowRight.setVisibility(4);
          }
          else
          {
            DateConfigFragment.this.arrowLeft.setVisibility(0);
            DateConfigFragment.this.arrowRight.setVisibility(0);
          }
        }
      }
    });
    this.monthBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DateConfigFragment.this.btnText = DateConfigFragment.this.monthBtn.getText().toString();
        DateConfigFragment.this.btnText = DateConfigFragment.this.btnText.substring(0, -1 + DateConfigFragment.this.btnText.length());
        DateConfigFragment.this.refreshDigitsPicker(DateConfigFragment.DigitType.MONTH);
      }
    });
    this.dayBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DateConfigFragment.this.btnText = DateConfigFragment.this.dayBtn.getText().toString();
        DateConfigFragment.this.btnText = DateConfigFragment.this.btnText.substring(0, -1 + DateConfigFragment.this.btnText.length());
        DateConfigFragment.this.refreshDigitsPicker(DateConfigFragment.DigitType.DAY);
      }
    });
    this.hourBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DateConfigFragment.this.btnText = DateConfigFragment.this.hourBtn.getText().toString();
        DateConfigFragment.this.btnText = DateConfigFragment.this.btnText.substring(0, -1 + DateConfigFragment.this.btnText.length());
        DateConfigFragment.this.refreshDigitsPicker(DateConfigFragment.DigitType.HOUR);
      }
    });
    this.minuteBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DateConfigFragment.this.btnText = DateConfigFragment.this.minuteBtn.getText().toString();
        DateConfigFragment.this.btnText = DateConfigFragment.this.btnText.substring(0, -1 + DateConfigFragment.this.btnText.length());
        if (Integer.parseInt(DateConfigFragment.this.btnText) > 31)
        {
          DateConfigFragment.this.refreshDigitsPicker(DateConfigFragment.DigitType.MINUTE, 1);
          DateConfigFragment.this.arrowLeft.setVisibility(0);
          DateConfigFragment.this.arrowRight.setVisibility(4);
          return;
        }
        DateConfigFragment.this.refreshDigitsPicker(DateConfigFragment.DigitType.MINUTE, 0);
        DateConfigFragment.this.arrowLeft.setVisibility(4);
        DateConfigFragment.this.arrowRight.setVisibility(0);
      }
    });
  }

  private void refreshDigitsPicker(DigitType paramDigitType)
  {
    refreshDigitsPicker(paramDigitType, -1);
  }

  private void refreshDigitsPicker(DigitType paramDigitType, int paramInt)
  {
    this.mDigitType = paramDigitType;
    this.mFirstDigit = paramInt;
    this.digitView.setVisibility(0);
    this.yearBtn.setNextFocusDownId(R.id.gridView);
    this.monthBtn.setNextFocusDownId(R.id.gridView);
    this.dayBtn.setNextFocusDownId(R.id.gridView);
    this.hourBtn.setNextFocusDownId(R.id.gridView);
    this.minuteBtn.setNextFocusDownId(R.id.gridView);
    this.save.setNextFocusUpId(R.id.gridView);
    this.mDateHelper.refreshDigits(paramDigitType, paramInt);
    ((BaseAdapter)this.digitPicker.getAdapter()).notifyDataSetChanged();
  }

  public static void showAdd(FragmentManager paramFragmentManager)
  {
    DateConfigFragment localDateConfigFragment = new DateConfigFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.add(R.id.fragment_container, localDateConfigFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public static void showReplace(FragmentManager paramFragmentManager)
  {
    DateConfigFragment localDateConfigFragment = new DateConfigFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.replace(R.id.fragment_container, localDateConfigFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (this.mDateHelper == null)
      this.mDateHelper = new DateHelper(paramActivity);
    this.mDateHelper.registerTimeReceiver(paramActivity);
  }

  public boolean onBackPressed()
  {
    if (this.digitView.isShown())
    {
      switch ($SWITCH_TABLE$com$starcor$settings$date$DateConfigFragment$DigitType()[this.mDigitType.ordinal()])
      {
      default:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      }
      while (true)
      {
        this.digitView.setVisibility(4);
        this.yearBtn.setNextFocusDownId(R.id.save);
        this.monthBtn.setNextFocusDownId(R.id.save);
        this.dayBtn.setNextFocusDownId(R.id.save);
        this.hourBtn.setNextFocusDownId(R.id.save);
        this.minuteBtn.setNextFocusDownId(R.id.save);
        return false;
        this.yearBtn.requestFocus();
        continue;
        this.monthBtn.requestFocus();
        continue;
        this.dayBtn.requestFocus();
        continue;
        this.hourBtn.requestFocus();
        continue;
        this.minuteBtn.requestFocus();
      }
    }
    return true;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.fragment_date_config, paramViewGroup, false);
    if (this.mDateHelper == null)
      this.mDateHelper = new DateHelper(getActivity());
    initBaseLayout(localView);
    initTimeButton(localView);
    initDigitPicker(localView);
    try
    {
      long l2 = ((SettingActivity)getActivity()).getSettingService().getCurrentTimeMillis();
      l1 = l2;
      GregorianCalendar localGregorianCalendar = new GregorianCalendar();
      localGregorianCalendar.setTimeInMillis(l1);
      this.mDateHelper.refreshTime(localGregorianCalendar);
      setTime();
      this.setDateTimeView.setNextFocusDownId(this.yearBtn.getId());
      return localView;
    }
    catch (Exception localException)
    {
      while (true)
        long l1 = System.currentTimeMillis();
    }
  }

  public void onDetach()
  {
    this.mDateHelper.unregisterTimeReceiver(getActivity());
    super.onDetach();
  }

  public void setTime()
  {
    Debug.E_printStackTrace(this);
    Debug.v("EE", "in setTime\nYear=" + this.mDateHelper.getYear() + ",Month=" + this.mDateHelper.getMonth() + ",Day=" + this.mDateHelper.getDay() + ",Hour=" + this.mDateHelper.getHour() + ",Minute=" + this.mDateHelper.getMinute());
    this.yearBtn.setText(this.mDateHelper.getYear() + "骞�");
    this.monthBtn.setText(this.mDateHelper.getMonth() + "鏈�");
    this.dayBtn.setText(this.mDateHelper.getDay() + "鏃�");
    this.hourBtn.setText(this.mDateHelper.getHour() + "鏃�");
    this.minuteBtn.setText(this.mDateHelper.getMinute() + "鍒�");
    this.dateTimeText.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(this.mDateHelper.getCalendar().getTime()));
  }

  static enum DigitType
  {
    static
    {
      MONTH = new DigitType("MONTH", 1);
      DAY = new DigitType("DAY", 2);
      HOUR = new DigitType("HOUR", 3);
      MINUTE = new DigitType("MINUTE", 4);
      DigitType[] arrayOfDigitType = new DigitType[5];
      arrayOfDigitType[0] = YEAR;
      arrayOfDigitType[1] = MONTH;
      arrayOfDigitType[2] = DAY;
      arrayOfDigitType[3] = HOUR;
      arrayOfDigitType[4] = MINUTE;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.date.DateConfigFragment
 * JD-Core Version:    0.6.2
 */