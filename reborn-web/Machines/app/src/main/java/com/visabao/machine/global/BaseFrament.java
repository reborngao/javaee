package com.visabao.machine.global;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.visabao.machine.MainActivity;
import com.visabao.machine.util.Log;

/**
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class BaseFrament  extends Fragment{

    private  View contentViw;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentViw= inflater.inflate(getContentLayoutId(),null) ;
        initData(contentViw);
        return contentViw;
    }


    public void  startFrament(int tabIndex){
      if(getActivity() instanceof MainActivity){
                  ((MainActivity) getActivity()).startFrament(tabIndex);
      }
    }

    public   void   removeFrament( Fragment fragment){
        if(getActivity() instanceof MainActivity){
            ((MainActivity) getActivity()).removeFrament(fragment);
        }
    }
    public   void   removeFramentLit( Fragment fragment){
        if(getActivity() instanceof MainActivity){
            ((MainActivity) getActivity()).removeFramentLit(fragment);
        }
    }




    public  void startFrament(Fragment from,Fragment to,boolean isAddBackStack ){
        startFrament(from,to,isAddBackStack,null);
    }

    public  void startFrament(Fragment from,Fragment to,boolean isAddBackStack ,String tag){
        if(getActivity() instanceof MainActivity){
            ((MainActivity) getActivity()).startFrament(from,to,isAddBackStack,tag);
        }
    }





    public abstract int getContentLayoutId();

    /**
     * 初始化数据
     *
     */
    public abstract void initData(View view);


    
}
