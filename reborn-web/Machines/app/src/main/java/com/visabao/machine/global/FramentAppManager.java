package com.visabao.machine.global;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.visabao.machine.R;
import com.visabao.machine.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public class FramentAppManager   implements FragmentManager.OnBackStackChangedListener{
    private static Map<String,Fragment> fragmentMap;
    private static FramentAppManager instance;
    private  Activity activity;
    private  FragmentManager fragmentManager;

    private  Fragment lastFragment;

    public FramentAppManager(Activity activity) {
        this.activity=activity;
            init();
    }



    private  void  init(){
        fragmentMap=new HashMap<String, Fragment>();
        fragmentManager= activity.getFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
    }




    /**
     * 添加Fragment堆栈
     */
    public void addFragment(Fragment fragment) {
        lastFragment = fragment;
       String className=   fragment.getClass().getName();
        if (fragmentMap.containsKey(className)){
            fragmentMap.put(className, fragment);
        }
        else{
            hide(fragment);
        }
    }

    public void startFrament(int containerViewId,Fragment fragment,boolean isAddBackStack ){
        startFrament(containerViewId, fragment, isAddBackStack, null);
    }
    public  void startFrament(int containerViewId,Fragment fragment){
        startFrament(containerViewId,fragment,true,null);
    }
    public void startFrament(int containerViewId, Fragment fragment, String tag) {
        startFrament(containerViewId,fragment,true,tag);
    }
    public  void  startFrament(int containerViewId,Fragment fragment,boolean isAddBackStack,String tag){
        FragmentTransaction fragmentTransaction=getFragmentTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in,
                        R.animator.slide_fragment_horizontal_left_out,
                        R.animator.slide_fragment_horizontal_left_in,
                        R.animator.slide_fragment_horizontal_right_out);

        if (isAddBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        if (tag==null||tag.length()==0){
            fragmentTransaction.add(containerViewId, fragment);
        }
        else{
            fragmentTransaction.add(containerViewId, fragment, tag);
        }
        if (fragmentMap.size()>0){
            hide(fragmentMap.get(lastFragment.getClass().getName()));
        }
        fragmentTransaction.commit();
    }




    private  FragmentTransaction  getFragmentTransaction(){
        FragmentTransaction  fragmentTransaction= fragmentManager.beginTransaction();
        return fragmentTransaction;
    }
    private  void hide(Fragment fragment){
        FragmentTransaction fragmentTransaction=getFragmentTransaction();
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }


    private   void   removeFrament( Fragment fragment){
        FragmentTransaction fragmentTransaction=getFragmentTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_fragment_horizontal_left_in, R.animator.slide_fragment_horizontal_right_out, R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_horizontal_left_out);
        fragmentMap.remove(fragment.getClass().getName());
        fragmentTransaction.remove(fragment);
       /* if (fragmentMap.size()>0){
            transaction.show(indexFragmentList.get(indexFragmentList.size()-1));
        }
        transaction.commit();*/
    }


    @Override
    public void onBackStackChanged() {
        Log.e("==============================>"+fragmentManager.getBackStackEntryCount());
    }
}
