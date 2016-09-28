package com.starcor.settings.network;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.starcor.settings.R.id;
import com.starcor.settings.R.layout;

public class BroadbandDialUpConfigFragment extends Fragment
{
  public static final String TAG = BroadbandDialUpConfigFragment.class.getSimpleName();
  private EditText accountEdit;

  public static void showAdd(FragmentManager paramFragmentManager)
  {
    BroadbandDialUpConfigFragment localBroadbandDialUpConfigFragment = new BroadbandDialUpConfigFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.add(R.id.network_fragment_container, localBroadbandDialUpConfigFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public static void showReplace(FragmentManager paramFragmentManager)
  {
    BroadbandDialUpConfigFragment localBroadbandDialUpConfigFragment = new BroadbandDialUpConfigFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.replace(R.id.network_fragment_container, localBroadbandDialUpConfigFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.fragment_bdu_config, paramViewGroup, false);
    this.accountEdit = ((EditText)localView.findViewById(R.id.account_edit));
    this.accountEdit.setNextFocusUpId(R.id.broadband_dial_up);
    return localView;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.network.BroadbandDialUpConfigFragment
 * JD-Core Version:    0.6.2
 */