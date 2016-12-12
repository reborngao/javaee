package com.visabao.machine;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.StringMap;
import com.google.gson.reflect.TypeToken;
import com.visabao.machine.entity.ImageList;
import com.visabao.machine.fragment.AreaVisaListFragment;
import com.visabao.machine.fragment.HomeFragment;

import com.visabao.machine.fragment.ReminderFragment;
import com.visabao.machine.fragment.message.VisaMessageFrament;
import com.visabao.machine.global.BaseActivity;
import com.visabao.machine.global.Constant;
import com.visabao.machine.global.FramentAppManager;
import com.visabao.machine.global.MessageResults;
import com.visabao.machine.global.TAAppManager;
import com.visabao.machine.global.URL;
import com.visabao.machine.util.HttpClientHelper;
import com.visabao.machine.util.JsonUtil;
import com.visabao.machine.util.Log;
import com.visabao.machine.util.ProgressDialogUtil;
import com.visabao.machine.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 *  主页面 控制器
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends BaseActivity  implements RadioGroup.OnCheckedChangeListener{
   private  Fragment  curentFrament;
    private    List<Fragment> indexFragmentList=new ArrayList<Fragment>();


    private HomeFragment homeFragment;  //
    private  TextView  tvGuidelines;

    RadioGroup radioGroup;

    FramentAppManager  framentAppManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        radioGroup=find(R.id.mian_tab_gadio_group);
        framentAppManager=  new   FramentAppManager(this);
        radioGroup.setOnCheckedChangeListener(this);
        homeFragment=new HomeFragment();
        curentFrament=homeFragment;
        startFrament(null, homeFragment, false,HomeFragment.class.getName());
        tvGuidelines= find(R.id.mian_tv_guidelines);
        findViewById(R.id.rl_management_guidelines).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ManagementGuidelinesActivity.class);
                startActivity(intent);
            }
        });
        requestData(null);
        requestImageData();
    }


    private  void  requestImageData(){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put(Constant.TYPE,Constant.Type.CAROUSEL);
        map.put("f_type","carousel");
        HttpClientHelper.getInstance(this).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                MessageResults<List<ImageList>> messageResults=JsonUtil.fromJson(data, new TypeToken<MessageResults<ArrayList<ImageList>>>() {
                });
                if (messageResults!=null){
                    if(messageResults.isSuccess()){
                        List<ImageList> imageLists=  messageResults.getData();
                        homeFragment.setImageList(imageLists);
                    }
                }

            }

            @Override
            public void onFailure() {

            }
        });
    }


        private  List<StringMap> coutryName; // 国家
        private  List<StringMap> coutryState; //州
        private List<StringMap>  asiaList;  //亚洲
        private List<StringMap>  europeList;  //欧洲
        private List<StringMap>  americaList;  //美洲
        private List<StringMap>  oceanicaList;  //大洋洲
        private List<StringMap>  africaList;  //非洲



      private void  requestData(final  String countryId){
         // ProgressDialogUtil.show(this);
          Map<String,Object>  map=new HashMap<String, Object>();
          map.put(Constant.TYPE, Constant.Type.GETCOUTRY);
          if(countryId!=null){
              map.put("countryId", countryId);
          }
          HttpClientHelper.getInstance(this).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
              @Override
              public void onSuccess(String data) {
                  //  ProgressDialogUtil.dismiss();
                  MessageResults mapMessageResults = JsonUtil.fromJson(data, MessageResults.class);
                  if (mapMessageResults.isSuccess()) {
                      StringMap stringMap = (StringMap) mapMessageResults.getData();
                      if (countryId == null) {
                          coutryName = (List<StringMap>) stringMap.get("coutryName");
                          coutryState = (List<StringMap>) stringMap.get("coutryState");
                          homeFragment.setData(coutryName, true);
                      } else if (countryId.equals("67")) { //亚洲
                          asiaList = (List<StringMap>) stringMap.get("coutryName");
                          homeFragment.setData(asiaList, false);
                      } else if (countryId.equals("66")) { ///欧洲
                          europeList = (List<StringMap>) stringMap.get("coutryName");
                          homeFragment.setData(europeList, false);
                      } else if (countryId.equals("62")) { //北美洲
                          americaList = (List<StringMap>) stringMap.get("coutryName");
                          homeFragment.setData(americaList, false);
                      } else if (countryId.equals("63")) { //大洋洲
                          oceanicaList = (List<StringMap>) stringMap.get("coutryName");
                          homeFragment.setData(oceanicaList, false);
                      } else if (countryId.equals("64")) { //非洲
                          africaList = (List<StringMap>) stringMap.get("coutryName");
                          homeFragment.setData(africaList, false);
                      }
                  } else {
                      Log.e(mapMessageResults.getMsg());
                  }

              }

              @Override
              public void onFailure() {
                  homeFragment.setData(new ArrayList<StringMap>(), false);
              }
          });
      }



      public    void   removeFrament( Fragment fragment){
          FragmentManager  fragmentManager= getFragmentManager();
          FragmentTransaction transaction= fragmentManager.beginTransaction();
          indexFragmentList.remove(fragment);
          transaction.remove(fragment);
          if(indexFragmentList.size()>0){
              transaction.show(indexFragmentList.get(indexFragmentList.size() - 1));
          }
          transaction.commit();
      }

    private void homeSwitch(){
        for (int i=0;i<getFragmentManager().getBackStackEntryCount();i++){
            getFragmentManager().popBackStack();
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(curentFrament);
        removeFramentLit(curentFrament);
        transaction.show( homeFragment);
        curentFrament=homeFragment;
        // indexFragmentList.add(homeFragment);
        transaction.commit();
    }

    public  void setTabSelection( int index) {
        if (curentFrament instanceof  HomeFragment) {

         }
        else {
            if (curentFrament instanceof  VisaMessageFrament){
                if(((VisaMessageFrament)curentFrament).isComplete()){
                    homeSwitch();
                }
                else{
                    showExitDialog(false);
                }
            }
            else {
                homeSwitch();
            }
        }
       Object countryId= radioGroup.findViewById(index).getTag();
        if (countryId==null){
            setHomeGone(View.VISIBLE);
            if (coutryName!=null){
                homeFragment.setData(coutryName,true);

            }
            else{
                requestData(null);
            }
        }
        else if(countryId.toString().equals("67")){ //亚洲
            setHomeGone(View.GONE);
            if (asiaList!=null){
                homeFragment.setData(asiaList,false);
            }
            else{
                requestData(countryId.toString());
            }

        }
        else if(countryId.toString().equals("66")){ ///欧洲
            setHomeGone(View.GONE);
            if (europeList!=null){
                homeFragment.setData(europeList,false);

            } else{
                requestData(countryId.toString());
            }
        }
        else if(countryId.equals("62")){ //北美洲
            setHomeGone(View.GONE);
            if (americaList!=null){
                homeFragment.setData(americaList,false);

            } else{
                requestData(countryId.toString());
            }
        }
        else if(countryId.equals("63")){ //大洋洲
            setHomeGone(View.GONE);
            if (oceanicaList!=null){
                homeFragment.setData(oceanicaList,false);

            } else{
                requestData(countryId.toString());
            }
        }
        else if(countryId.equals("64")){ //非洲
            setHomeGone(View.GONE);
            if (africaList!=null){
                homeFragment.setData(africaList,false);

            } else{
                requestData(countryId.toString());
            }
        }

    }
    private  void  setHomeGone(int visibility){
        homeFragment.getSlideShowView().setVisibility(visibility);
        //homeFragment.getBtnBack().setVisibility(visibility);
        if (visibility==View.VISIBLE){
            homeFragment.getGridView().setNumColumns(5);
        }
        else{
            homeFragment.getGridView().setNumColumns(4);
        }
    }
    public  void startFrament(Fragment from,Fragment to,boolean isAddBackStack ){
        startFrament(from, to, isAddBackStack, null);
    }


    public   void startFrament(Fragment from,Fragment to,boolean isAddBackStack ,String tag){
        FragmentManager  fragmentManager= getFragmentManager();
        FragmentTransaction transaction= fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_horizontal_left_out, R.animator.slide_fragment_horizontal_left_in, R.animator.slide_fragment_horizontal_right_out);
        if (!to.isAdded()){
            if (tag==null){
                transaction.add(R.id.mian_tab_content,to);
            }
            else{
                transaction.add(R.id.mian_tab_content,to,tag);
            }
            if (isAddBackStack){
                transaction.addToBackStack(null);
            }
        }
    /*    if (from!=null){
            transaction.hide(from);
        }*/
       /* if (from==null){
            indexFragmentList.add(to);
        }*/

        indexFragmentList.add(to);
        transaction.commit();
        curentFrament=to;
        Log.e(getFragmentManager().getBackStackEntryCount()+"=====");
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void startFrament(int index){
        Fragment fragment=null;
        switch (index){
            case 0: //签证列表
                 fragment=new AreaVisaListFragment();
                break;
            case  1:// 温馨提示
                fragment =new ReminderFragment();
                break;
            case 2:// 填写签证信息
                fragment =new VisaMessageFrament();
                break;
        }
        if (fragment==null) return;
        if (index==2){
            startFrament(homeFragment,fragment,true,VisaMessageFrament.class.getName());
        }
        else {
            startFrament(homeFragment,fragment,true);
        }
    }




    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        setTabSelection(checkedId);
    }

   public   void  showExitDialog(final boolean isEidt){
       Log.e(getFragmentManager().getBackStackEntryCount()+"=====");
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setMessage("确定退出办理流程?");
       builder.setTitle("");
       builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();


            /*   if (){

               }
               else{*/
                   getFragmentManager().popBackStack();
               getFragmentManager().popBackStack();
                   indexFragmentList.remove(indexFragmentList.size() - 1);
                   curentFrament = indexFragmentList.get(indexFragmentList.size() - 1);
                   if (isEidt) {
                       for (int i = 0; i <curentFrament.getChildFragmentManager().getBackStackEntryCount();i++){
                           curentFrament.getChildFragmentManager().popBackStack();
                       }
                   }
               Log.e(getFragmentManager().getBackStackEntryCount()+"=====");
               }


         //  }
       });
       builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
           }
       });
       builder.show();
   }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Log.e(getFragmentManager().getBackStackEntryCount()+"==============="+curentFrament+"==========>"+indexFragmentList);
        if (curentFrament instanceof  VisaMessageFrament){
            if(((VisaMessageFrament)curentFrament).isComplete()){
                super.onBackPressed();
            }
            else{
                showExitDialog(false);
            }
        }
        else if(getFragmentManager().getBackStackEntryCount()==0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("确定退出签证宝?");
            builder.setTitle("");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    TAAppManager.getAppManager().finishAllActivity();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
        else{

        if (indexFragmentList.size()>0)
          getFragmentManager().popBackStack();
            indexFragmentList.remove(indexFragmentList.size() - 1);
            if (indexFragmentList.size()>0){
                curentFrament = indexFragmentList.get(indexFragmentList.size()-1);
            }

        }
        Log.e(getFragmentManager().getBackStackEntryCount()+"==============="+curentFrament+"==========>"+indexFragmentList);
    }

    public void removeFramentLit(Fragment fragment) {
        indexFragmentList.remove(fragment);
    }
}
