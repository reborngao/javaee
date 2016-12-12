package com.visabao.machine.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.internal.StringMap;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.visabao.machine.R;
import com.visabao.machine.global.BaseFrament;
import com.visabao.machine.global.BaseItemAdpter;
import com.visabao.machine.global.Constant;
import com.visabao.machine.global.MessageResults;
import com.visabao.machine.global.URL;
import com.visabao.machine.util.HttpClientHelper;
import com.visabao.machine.util.ImageLoaderUtil;
import com.visabao.machine.util.JsonUtil;
import com.visabao.machine.util.ProgressDialogUtil;
import com.visabao.machine.util.SkuUtil;
import com.visabao.machine.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 州签证列表
 */
public class AreaVisaListFragment extends BaseFrament implements BaseItemAdpter.BaseItemViewListenter{
    ListView listView;

    public  static  final   String  COUNTRY_ACRONYM="countryAcronym";
    public  static  final   String  COUNTRY_NAME="countryName";
    public  static  final   String  IMAGE_URL="image_url";


    private    BaseItemAdpter adpter;

    public   List<StringMap> dataList;
    private View llNotData;
    private String imageURL;

    @Override
    public int getContentLayoutId() {
        return R.layout.area_visa_list;
    }
    @Override
    public void initData(View view) {
        listView= (ListView) view.findViewById(R.id.listview);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
          TextView tvTitle= (TextView) view.findViewById(R.id.area_visa_tv_title);
        imageURL = getArguments().getString(IMAGE_URL);
        tvTitle.setText(getArguments().getString(COUNTRY_NAME));
        dataList=new ArrayList<StringMap>();
        adpter= new BaseItemAdpter(getActivity(), R.layout.item_visa_list, dataList, this, R.id.item_btn_sign,R.id.item_iv_icon,R.id.item_tv_title,R.id.item_tv_day_number,
                R.id.item_tv_stay_day_number,R.id.item_tv_validity,R.id.item_iv_difficulty,R.id.item_tv_price);
        listView.setAdapter(adpter);
        requestData();
        llNotData= view.findViewById(R.id.ll_not_data);
    }

    private void  requestData( ){
        ProgressDialogUtil.show(getActivity());
        Map<String,Object> map=new HashMap<String, Object>();
        map.put(Constant.TYPE, Constant.Type.GETSKU);
        map.put("country", getArguments().get(COUNTRY_ACRONYM));
        HttpClientHelper.getInstance(getActivity()).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                ProgressDialogUtil.dismiss();
                MessageResults mapMessageResults = JsonUtil.fromJson(data, MessageResults.class);
                if (mapMessageResults.isSuccess()) {
                     StringMap stringMap= (StringMap) mapMessageResults.getData();
                    llNotData.setVisibility(View.GONE);
                    if ( stringMap.get("skus")!=null){
                        List < StringMap >   mapList=(List < StringMap >) stringMap.get("skus");
                        dataList.addAll(mapList);
                        adpter.notifyDataSetChanged();
                    }
                } else {
                    llNotData.setVisibility(View.VISIBLE);
                    com.visabao.machine.util.Log.e(mapMessageResults.getMsg());
                }
            }
            @Override
            public void onFailure() {
                ProgressDialogUtil.dismiss();
            }
        },true);
    }

    public  final static  String  SKUKVDESCRIPTION="SKUKVDESCRIPTION";
    public  final static  String  SKUSHORTCODE="SKUSHORTCODE";
    public  final static  String  SKUCODE="SKUCODE";
    public  final static  String  PRICE="PRICE";
    public  final static  String  TITLE="TITLE";
    public  final static  String  TRAVRL_DAYS="TRAVRL_DAYS"; //出行日期

    public  final  static  String DAY_NUMBER="DAY_NUMBER";
    @Override
    public void handerItemView(SparseArray<View> viewHolder, int position, View convertView, ViewGroup parent) {

      final   StringMap stringMap= dataList.get(position);

          String []  skukvdescription= stringMap.get("skukvdescription").toString().split("-");
        final  TextView tvTtile= (TextView) viewHolder.get(R.id.item_tv_title);
        String []  spukvdescription= stringMap.get("spukvdescription").toString().split("-");
         tvTtile.setText(SkuUtil.getPruductName(stringMap.get("spukvdescription").toString()));//  签证类型
        final    TextView tvDayNumber= (TextView) viewHolder.get(R.id.item_tv_day_number); // 办理天数
        if (spukvdescription.length>6){
            tvDayNumber.setText(spukvdescription[6]+"个工作日");
        }
          TextView tvStayDayNumber= (TextView) viewHolder.get(R.id.item_tv_stay_day_number); // 可停留天数

        if (spukvdescription.length>9){
            tvStayDayNumber.setText(spukvdescription[9]);
        }

        TextView tvValidity= (TextView) viewHolder.get(R.id.item_tv_validity); // 签证年限

        if (spukvdescription.length>7){
            tvValidity.setText(spukvdescription[7]);
        }
   //     TextView tvDifficulty= (TextView) viewHolder.get(R.id.item_tv_difficulty); // 难度
     /*   if (skukvdescription.length>8){
            tvDifficulty.setText(skukvdescription[8]);
        }*/
        final TextView tvPrice= (TextView) viewHolder.get(R.id.item_tv_price); // 价格
        if (skukvdescription.length>8){
            if(!StringUtil.isEmpty(skukvdescription[8])){
                tvPrice.setText(StringUtil.formatTowDouble(skukvdescription[8]));
            }
        }
        ImageView imageView= (ImageView) viewHolder.get(R.id.item_iv_icon);
        if (imageURL!=null){
            ImageLoader.getInstance().displayImage(imageURL, imageView, ImageLoaderUtil.getDisplayRoundedBitmapOptions(10, R.mipmap.iv_default));
        }
        Button button= (Button) viewHolder.get(R.id.item_btn_sign);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderFragment reminderFragment = new ReminderFragment();
                Bundle bundle = getArguments();
                bundle.putString(SKUCODE, stringMap.get("skucode").toString());
                bundle.putString(TITLE, tvTtile.getText().toString());
                bundle.putString(SKUKVDESCRIPTION, stringMap.get("skukvdescription").toString());
                bundle.putString(SKUSHORTCODE, stringMap.get("skushortcode").toString());
                bundle.putString(PRICE, tvPrice.getText().toString());
                bundle.putString(DAY_NUMBER, tvDayNumber.getText().toString());
                bundle.putString(TRAVRL_DAYS, stringMap.get("travel_days").toString());


                reminderFragment.setArguments(bundle);
                startFrament(AreaVisaListFragment.this, reminderFragment, true, ReminderFragment.class.getName());
            }
        });
    }
}
