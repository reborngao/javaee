package com.visabao.machine.fragment.message;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.visabao.machine.R;
import com.visabao.machine.global.BaseFrament;

public abstract  class MessageBaseFrament   extends  BaseFrament{
    Button nextBtn;
    private View upView;

    public Button getNextBtn() {
        return nextBtn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view=   super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public View getUpView() {
        return upView;
    }

    @Override
    public void initData(View view) {
        View  nextView= view.findViewById(R.id.btn_next);
        if(nextView!=null){
            nextBtn= (Button)nextView;
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    perClick(false);
                }
            });
        }
          upView= view.findViewById(R.id.btn_up);
        if (upView!=null){
            upView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment=   getParentFragment();
                    if (fragment instanceof VisaMessageFrament) {
                        ((VisaMessageFrament) fragment).onBack(getTabUpIndex());
                    }
                }
            });
        }
    }

    public void perClick(boolean saveFlag){
        Fragment fragment=   getParentFragment();
        if (fragment instanceof VisaMessageFrament) {
            Fragment fra= getTabNextIndex();
            if (fra!=null){
                ((VisaMessageFrament) fragment).toTabIndex(MessageBaseFrament.this,fra,true);
            }
        }
        if ( getParentFragment()==null){
            if (this instanceof  WriteMessageFrament){
                Fragment fra= getTabNextIndex();
                startFrament(fragment,fra,true);
                getFragmentManager().beginTransaction().remove(this).commit();
            }
        }

    }



    public  abstract  Fragment getTabNextIndex();
    public  abstract  int getTabUpIndex();
}
