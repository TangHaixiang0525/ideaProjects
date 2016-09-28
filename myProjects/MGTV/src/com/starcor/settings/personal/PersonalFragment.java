package com.starcor.settings.personal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.starcor.settings.R.id;
import com.starcor.settings.R.layout;

public class PersonalFragment extends Fragment
{
  public static final String TAG = PersonalFragment.class.getSimpleName();

  public static void showAdd(FragmentManager paramFragmentManager)
  {
    PersonalFragment localPersonalFragment = new PersonalFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.add(R.id.fragment_container, localPersonalFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public static void showReplace(FragmentManager paramFragmentManager)
  {
    PersonalFragment localPersonalFragment = new PersonalFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.replace(R.id.fragment_container, localPersonalFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(R.layout.fragment_personal, paramViewGroup, false);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.personal.PersonalFragment
 * JD-Core Version:    0.6.2
 */