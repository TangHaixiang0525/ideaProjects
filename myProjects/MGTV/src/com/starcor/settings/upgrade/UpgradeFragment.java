package com.starcor.settings.upgrade;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.starcor.settings.R.id;
import com.starcor.settings.R.layout;
import com.starcor.settings.download.DownloadHelper;
import com.starcor.settings.download.DownloadService;
import com.starcor.settings.download.Downloads.Item;

public class UpgradeFragment extends Fragment
{
  public static final String TAG = UpgradeFragment.class.getSimpleName();
  private Button checkNewVersion;
  private TextView currentVersion;
  private TextView newVersion;
  ProgressBar progressBar;
  private Button upgradeNow;

  public static void showAdd(FragmentManager paramFragmentManager)
  {
    UpgradeFragment localUpgradeFragment = new UpgradeFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.add(R.id.fragment_container, localUpgradeFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public static void showReplace(FragmentManager paramFragmentManager)
  {
    UpgradeFragment localUpgradeFragment = new UpgradeFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.replace(R.id.fragment_container, localUpgradeFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.fragment_upgrade, paramViewGroup, false);
    this.currentVersion = ((TextView)localView.findViewById(R.id.current_version));
    this.checkNewVersion = ((Button)localView.findViewById(R.id.check_new_version));
    this.newVersion = ((TextView)localView.findViewById(R.id.new_version));
    this.upgradeNow = ((Button)localView.findViewById(R.id.upgrade_now));
    this.progressBar = ((ProgressBar)localView.findViewById(16908301));
    this.upgradeNow.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DownloadHelper.getInstance(UpgradeFragment.this.getActivity()).addDownload("http://192.168.2.252/%E5%B9%BF%E8%A5%BF%E5%A4%9A%E5%B1%8F%E7%BB%88%E7%AB%AF-OTT%E5%B9%B3%E5%8F%B0-%E7%BB%88%E7%AB%AF%E5%AF%B9%E6%8E%A5.doc");
        UpgradeFragment.this.getActivity().startService(new Intent(UpgradeFragment.this.getActivity(), DownloadService.class));
      }
    });
    this.progressBar.setMax(100);
    getActivity().getContentResolver().registerContentObserver(Downloads.Item.CONTENT_URI, true, new ChangeObserver());
    return localView;
  }

  private class ChangeObserver extends ContentObserver
  {
    public ChangeObserver()
    {
      super();
    }

    public boolean deliverSelfNotifications()
    {
      return true;
    }

    public void onChange(boolean paramBoolean)
    {
      UpgradeFragment.this.getActivity().runOnUiThread(new Runnable()
      {
        public void run()
        {
          Cursor localCursor = DownloadHelper.getInstance(UpgradeFragment.this.getActivity()).queryAllDownloads();
          if (localCursor != null)
          {
            if (localCursor.moveToFirst())
            {
              long l1 = localCursor.getLong(localCursor.getColumnIndex("current_bytes"));
              long l2 = localCursor.getLong(localCursor.getColumnIndex("total_bytes"));
              UpgradeFragment.this.progressBar.setProgress((int)(100L * l1 / l2));
            }
            localCursor.close();
          }
          Log.d("lx", "download observer change");
        }
      });
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.upgrade.UpgradeFragment
 * JD-Core Version:    0.6.2
 */