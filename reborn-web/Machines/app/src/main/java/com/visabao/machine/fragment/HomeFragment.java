package com.visabao.machine.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.StringMap;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.visabao.machine.R;
import com.visabao.machine.entity.ImageList;
import com.visabao.machine.fragment.refund.RefundInputFrament;
import com.visabao.machine.global.BaseFrament;
import com.visabao.machine.global.BaseItemAdpter;
import com.visabao.machine.util.ImageLoaderUtil;
import com.visabao.machine.util.Log;
import com.visabao.machine.util.ProgressDialogUtil;
import com.visabao.machine.util.StringUtil;
import com.visabao.machine.util.ToastUtil;
import com.visabao.machine.widget.GalleryView;
import com.visabao.machine.widget.ImageAdapter;
import com.visabao.machine.widget.SlideShowView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  首页
 */
public class HomeFragment extends BaseFrament implements BaseItemAdpter.BaseItemViewListenter {
    private GalleryView gallery;

    private ImageAdapter adapter;
    private BaseItemAdpter  baseItemAdpter;
    private SlideShowView slideShowView;
    private View llNotData;
    private Button btnBack;
    private GridView gridView;

    public Button getBtnBack() {
        return btnBack;
    }

    public void setBtnBack(Button btnBack) {
        this.btnBack = btnBack;
    }

    /* android.os.Handler handler=new android.os.Handler(){
         @Override
         public void handleMessage(Message msg) {
             gallery.setAdapter(adapter);
             gallery.setSelection(3);
         }
     };*/


    public SlideShowView getSlideShowView() {
        return slideShowView;
    }



    public  static  final  String SKU_CODE="skucode";
    public  static  final  String F_CODE="fcode";

    @Override
    public int getContentLayoutId() {
        return R.layout.home;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void initData(View view) {
       /* gallery= (GalleryView) view.findViewById(R.id.gallery);
        adapter = new ImageAdapter(getActivity());
         new Thread(){
             @Override
             public void run() {
                 adapter.createReflectedImages();
                 handler.sendEmptyMessage(0);
             }
         }.start();

        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "img " + (position + 1) + " selected", Toast.LENGTH_SHORT).show();
            }
        });*/

        slideShowView= (SlideShowView) view.findViewById(R.id.home_slideshowview);
        slideShowView.setOnItemSlideShowListener(new SlideShowView.OnItemSlideShowListener() {
            @Override
            public void onItemClick(ImageList imageList, int postion) {
                if(imageList.getF_type().equals("carousel_global")){
                    GlobalFrament globalFrament =new GlobalFrament();
                    Bundle bundle=new Bundle();
                    bundle.putString(SKU_CODE,imageList.getSkucode());
                    bundle.putString(F_CODE,imageList.getF_code());
                    bundle.putString(AreaVisaListFragment.SKUKVDESCRIPTION,imageList.getSkukvdescription());
                    globalFrament.setArguments(bundle);
                        startFrament(HomeFragment.this, globalFrament, true);
                }
            }
        });
         gridView= (GridView) view.findViewById(R.id.home_gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AreaVisaListFragment visaListFragment = new AreaVisaListFragment();
                Bundle bundle = new Bundle();
                bundle.putString(AreaVisaListFragment.COUNTRY_ACRONYM, list.get(position).get("countryAcronym").toString());
                bundle.putString(AreaVisaListFragment.COUNTRY_NAME, list.get(position).get("countryName").toString());
                bundle.putString(AreaVisaListFragment.IMAGE_URL, list.get(position).get("image_url").toString());
                bundle.putString(IMAGE_URL_S, list.get(position).get("image_url_s").toString());


                visaListFragment.setArguments(bundle);
                startFrament(HomeFragment.this, visaListFragment, true);
            }

        });
        llNotData= view.findViewById(R.id.ll_not_data);
        baseItemAdpter= new BaseItemAdpter(getActivity(), R.layout.item_home_gridview,  list, this, R.id.item_iv,R.id.item_title);
        gridView.setAdapter(baseItemAdpter);
          btnBack= (Button) view.findViewById(R.id.btn_refund);
//退款
        btnBack .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


           RefundInputFrament refundInputFrament = new RefundInputFrament();
                startFrament(HomeFragment.this, refundInputFrament, true,RefundInputFrament.class.getName());
           //     Intent intent = new Intent("com.pax.CALL_PAYMENT");
            /* //   Map<String, String> requestData = new HashMap<String, String>();
                intent.putExtra("TRANS_TYPE", "003");//
              //  intent.putExtra("ORIG_TRACE_NO", "000018");
                intent.putExtra("ORIG_REF_NO", "987654321000");//
                intent.putExtra("ORIG_TRANS_DATE", "20161125");//
                intent.putExtra("TRANS_AMT","100.00");
                startActivityForResult(intent, 11);*/
            }
        });
    }

    public GridView getGridView() {
        return gridView;
    }


      @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_CANCELED) {
                String reason = data.getStringExtra("reason");
                ProgressDialogUtil.dismiss();
                if (reason!=null){
                    ToastUtil.showToast(getActivity(), reason);
                }

            } else if (resultCode == Activity.RESULT_OK) { //
                String info = data.getStringExtra("info");
                Log.e("==================>" + info);
                //   ToastUtil.showToast(getActivity(), info);
            }
        }
    }

    public  final  static  String IMAGE_URL_S="IMAGE_URL_S";

    private  List<StringMap> list=new ArrayList<StringMap>();
    private List<StringMap>  getData(){
        return  list;
    }

    private  boolean isShow;
    public void setData(List<StringMap>  data,boolean isShow){
        this.isShow=isShow;
        list.clear();
        list.addAll(data);
        baseItemAdpter.notifyDataSetChanged();
        if (data.size()==0){
            llNotData.setVisibility(View.VISIBLE);
            ((TextView) llNotData.findViewById(R.id.tv_not_data)).setTextColor(Color.WHITE);
        }
        else {
            llNotData.setVisibility(View.GONE);
        }
    }

    @Override
    public void handerItemView(SparseArray<View> viewHolder, int position, View convertView, ViewGroup parent) {
            ImageView imgaeView= (ImageView) viewHolder.get(R.id.item_iv);
        imgaeView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,isShow?getResources().getDimensionPixelOffset(R.dimen.home_iv_height):
                getResources().getDimensionPixelOffset(R.dimen.home_iv_city_height)));
         TextView tvTitle= (TextView) viewHolder.get(R.id.item_title);
        StringMap stringMap=  list.get(position);
    //   ImageLoader.getInstance().displayImage(stringMap.get("image_url").toString(),imgaeView, ImageLoaderUtil.getDisplayFilletImageOptions());
         ImageLoader.getInstance().displayImage(stringMap.get("image_url").toString(), imgaeView, ImageLoaderUtil.getDisplayRoundedBitmapOptions(10,R.mipmap.iv_default));
    //
       // ImageLoader.getInstance().displayImage("http://p2.qqyou.com/pifu/UploadPic/2014-10/25/2014102522380764959.jpeg",imgaeView);
      //  tvTitle.setText("Test"+position);
    }
    public void setImageList(List<ImageList> imageLists) {
        slideShowView.setImageList(imageLists);
    }
}
