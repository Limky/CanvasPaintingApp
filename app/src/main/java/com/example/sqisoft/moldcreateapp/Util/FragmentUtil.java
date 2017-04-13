package com.example.sqisoft.moldcreateapp.util;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.sqisoft.moldcreateapp.Fragment.FragmentMain;

public class FragmentUtil {
    private static final String TAG = "FragmentUtil";
 
    private static int defaultContainer = 0;
    private static FragmentManager defaultFragmentManager = null;


    public static void init(int containerResId, FragmentManager manager) {
        defaultContainer = containerResId;
        defaultFragmentManager = manager;

    }

    public static void addFragment(Fragment fragmentType) {
        addFragment(defaultFragmentManager, fragmentType, null);
    }
    public static void addFragment2(Fragment fragmentType) {
        addFragment2(defaultFragmentManager, fragmentType, null);
    }


    public static void addFragment(Fragment fragmentType, Bundle args) {
        addFragment(defaultFragmentManager, fragmentType, args);
    }

    public static void addFragment(FragmentManager manager, Fragment fragmentType) {
        addFragment(manager, fragmentType, null);
    }
    
    public static FragmentManager getFragmentManager() {
        return defaultFragmentManager;
    }

    public static void addFragment(FragmentManager manager, Fragment fragment, Bundle args) {
            if(args != null) {
                fragment.setArguments(args);
            }
            FragmentTransaction transaction = manager.beginTransaction();
//                if(fragmentType.playAnimation())
//                    transaction.setCustomAnimations(R.anim.rtoc, R.anim.ctol, R.anim.ltoc, R.anim.ctor);

            transaction.replace(defaultContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();

    }

    public static void addFragment2(FragmentManager manager, Fragment fragment, Bundle args) {
        if(args != null) {
            fragment.setArguments(args);
        }
        FragmentTransaction transaction = manager.beginTransaction();
//                if(fragmentType.playAnimation())
//                    transaction.setCustomAnimations(R.anim.rtoc, R.anim.ctol, R.anim.ltoc, R.anim.ctor);

        transaction.replace(defaultContainer, fragment);
        transaction.commitAllowingStateLoss();

    }


    /**
     * 메인화면으로 이동한다.
     */
    public static void goMain(FragmentManager manager) {
    	if (manager.getFragments() != null) {
    		// DialogFragment 가 있으면 닫아 주자.
    		for (Fragment f : manager.getFragments()) {
    			if(f instanceof DialogFragment) {
    				((DialogFragment) f).dismiss();
    			}
    		}
    	}

        if(manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            try{
            	manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }catch (Exception e){
            	Log.e(TAG, e.getMessage());
            }
        }
    }

    public static void addRootFragment(Fragment fragment) {
        goMain(defaultFragmentManager);
        if (fragment instanceof FragmentMain) {
            return;
        }
        addFragment(defaultFragmentManager, fragment, null);
    }

    public static void addRootFragment(Fragment fragment, Bundle args) {
        goMain(defaultFragmentManager);
        if (fragment instanceof FragmentMain) {
            return;
        }
        addFragment(defaultFragmentManager, fragment, args);
    }

    public static void addRootFragment(FragmentManager manager, Fragment fragment) {
        goMain(manager);
        if (fragment instanceof FragmentMain) {
            return;
        }
        addFragment(manager, fragment, null);
    }

    public static void addRootFragment(FragmentManager manager, Fragment fragment, Bundle args) {
        goMain(manager);
        if (fragment instanceof FragmentMain) {
            return;
        }
        addFragment(manager, fragment, args);
    }

    public static void reflash() {
        reflash(defaultFragmentManager);
    }

    /**
     * 화면을 갱신한다.
     */
    public static void reflash(FragmentManager manager) {

        Fragment currentFragment = manager.getFragments().get(manager.getBackStackEntryCount());
        FragmentTransaction fragTransaction = manager.beginTransaction();
        fragTransaction.detach(currentFragment);
        fragTransaction.attach(currentFragment);
        fragTransaction.commit();
    }

    public static void goBack() {
        goBack(defaultFragmentManager);
    }

    /**
     * 이전화면으로 넘어 간다.
     */
    public static void goBack(FragmentManager manager) {

      //  Log.d("goBack","goBack 고우 백");
        android.util.Log.d("goBack","goBack 고우 백");
       if(manager.getBackStackEntryCount() > 0) {
            manager.popBackStackImmediate();
        }
        // if (fm.getBackStackEntryCount() == 0)
        // changeLayoutView(true);

    }

    public static void trace() {
        android.util.Log.d("FragmentUtil", "Count:" + defaultFragmentManager.getBackStackEntryCount() + ", " + defaultFragmentManager.getFragments().size());
        for (int i = 0; i < defaultFragmentManager.getFragments().size(); i++) {
            if(defaultFragmentManager.getFragments().get(i) != null)
                android.util.Log.d("FragmentUtil", defaultFragmentManager.getFragments().get(i).toString() + " " + defaultFragmentManager.getFragments().get(i).getTag());
            else
            android.util.Log.d("FragmentUtil", "null");
        }
    }
}
