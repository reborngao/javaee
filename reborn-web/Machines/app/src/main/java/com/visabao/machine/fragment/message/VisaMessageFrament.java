package com.visabao.machine.fragment.message;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.visabao.machine.MainActivity;
import com.visabao.machine.R;
import com.visabao.machine.entity.Notes;
import com.visabao.machine.entity.Person;
import com.visabao.machine.entity.SoAdd;
import com.visabao.machine.entity.User;
import com.visabao.machine.fragment.AreaVisaListFragment;
import com.visabao.machine.fragment.HomeFragment;
import com.visabao.machine.fragment.ReminderFragment;
import com.visabao.machine.global.BaseFrament;
import com.visabao.machine.util.ImageLoaderUtil;
import com.visabao.machine.util.Log;
import com.visabao.machine.widget.MianRadioButton;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class VisaMessageFrament extends BaseFrament  {
    public boolean isComplete(){
        return tabRadioGroup.getCheckedRadioButtonId()==R.id.message_rb_complete;
    }
    @Override
    public int getContentLayoutId() {
        return R.layout.visa_message;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(getFragmentManager().getBackStackEntryCount()+"=====");
    }

    private RadioGroup  tabRadioGroup=null;
    @Override
    public void initData(View view) {
        tabRadioGroup = (RadioGroup) view.findViewById(R.id.message_tab_radio_grop);
        MessageDateFrament frament= new  MessageDateFrament();
        frament.setArguments(getArguments());
       toTabIndex(null, frament, true);
        TextView  tvTitle= (TextView) view.findViewById(R.id.visa_tv_title);
        tvTitle.setText(getArguments().getString(AreaVisaListFragment.TITLE));
         ImageView ivTitleIcon= (ImageView) view.findViewById(R.id.visa_title_icon);
        ImageLoader.getInstance().displayImage(getArguments().getString(HomeFragment.IMAGE_URL_S), ivTitleIcon, ImageLoaderUtil.getDisplayImageOptions());
        Log.e(getFragmentManager().getBackStackEntryCount() + "=====");

    }
    public  void onBack(int tabUpIndex){
            if (tabUpIndex==0){
                if (getActivity() instanceof MainActivity){
                    Log.e(getFragmentManager().getBackStackEntryCount() + "=====");
                    ((MainActivity)getActivity()).showExitDialog(true);
                }
            }
        else {
                getChildFragmentManager().popBackStack();
            }
        tabRadioGroup.check(tabUpIndex);
    }


    public void toTabIndex(Fragment from,Fragment to,boolean isAddBack){
          FragmentManager  fragmentManager= getChildFragmentManager();
        FragmentTransaction  fragmentTransaction= fragmentManager.beginTransaction();
        if(!to.isAdded()){
            fragmentTransaction.add(R.id.message_contentview, to);
            if (isAddBack){
               fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commitAllowingStateLoss();
        }
        changeRadioGroup(to);
    }

    private  void changeRadioGroup(Fragment to){
        if (to instanceof  MessageDateFrament ){//选择出行日期
            tabRadioGroup.check(R.id.message_rb_date);
        }
        else  if (to instanceof  WriteMessageFrament ){ // 基本信息
            tabRadioGroup.check(R.id.message_rb_message);
        }
        else  if (to instanceof  MessageAddFrament ){// 增加签证人
            tabRadioGroup.check(R.id.message_rb_add);
        }
        else  if (to instanceof  MessageSelectFrament ){ //选择服务
            tabRadioGroup.check(R.id.message_rb_select);
        } else  if (to instanceof  MessageConmitFrament ){// 确认订单
            tabRadioGroup.check(R.id.message_rb_conmit);
        } else  if (to instanceof  MessageCompleteFrament ){ // 完成订单
            tabRadioGroup.check(R.id.message_rb_complete);
        }
    }
    private  User user;
    private  Notes notes;
    private  SoAdd soAdd;
    public void setUser(User user) {
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public SoAdd getSoAdd() {
        return soAdd;
    }

    public void setSoAdd(SoAdd soAdd) {
        this.soAdd = soAdd;
    }
    private  Map<String ,String> messageMap;
    public void setMeeageMap(Map<String, String> messageMap) {
      this.messageMap=messageMap;
    }

    public Map<String, String> getMessageMap() {
        return messageMap;
    }
    private  List<Person> personList;
    public void setPersonData(List<Person> personList) {
            this.personList=personList;
    }

    public List<Person> getPersonList() {
        return personList;
    }
}
