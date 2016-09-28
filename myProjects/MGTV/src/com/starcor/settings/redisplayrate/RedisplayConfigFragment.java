package com.starcor.settings.redisplayrate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.starcor.settings.R.id;
import com.starcor.settings.R.layout;

public class RedisplayConfigFragment extends Fragment
{
  public static final String TAG = RedisplayConfigFragment.class.getSimpleName();

  public static void showAdd(FragmentManager paramFragmentManager)
  {
    RedisplayConfigFragment localRedisplayConfigFragment = new RedisplayConfigFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.add(R.id.fragment_container, localRedisplayConfigFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public static void showReplace(FragmentManager paramFragmentManager)
  {
    RedisplayConfigFragment localRedisplayConfigFragment = new RedisplayConfigFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.replace(R.id.fragment_container, localRedisplayConfigFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView1 = paramLayoutInflater.inflate(R.layout.fragment_display_config, paramViewGroup, false);
    View localView2 = localView1.findViewById(R.id.display_containter);
    localView2.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        TextView localTextView = this.val$tv;
        if (paramAnonymousBoolean);
        for (int i = -13619152; ; i = -10461088)
        {
          localTextView.setTextColor(i);
          paramAnonymousView.setSelected(paramAnonymousBoolean);
          return;
        }
      }
    });
    localView2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent();
        localIntent.setClass(RedisplayConfigFragment.this.getActivity(), RedisplayRateActivity.class);
        RedisplayConfigFragment.this.startActivity(localIntent);
      }
    });
    return localView1;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.redisplayrate.RedisplayConfigFragment
 * JD-Core Version:    0.6.2
 */