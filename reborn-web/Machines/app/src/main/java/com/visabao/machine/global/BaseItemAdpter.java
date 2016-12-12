package com.visabao.machine.global;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.visabao.machine.entity.Refund;

import java.util.List;

public class BaseItemAdpter extends BaseAdapter {
	private BaseItemViewListenter baseItemViewListenter;
	private Context context;
	private int layoutId;
	private int[] itemViewId;
	private int headViewSize = 0;
	private List<?> data;
	private BaseItemAdpter(){}
	
	private  Object[] dataArray;
	
	/***
	 *  
	 * @param context 上下文
	 * @param layoutId   布局资源ID
	 * @param data  //数据
	 * @param baseItemViewListenter  // 每一项监听
	 * @param itemViewId      每一项 View ID
	 */
	public BaseItemAdpter(Context context,int  layoutId,List<?> data,BaseItemViewListenter baseItemViewListenter,int ...itemViewId){
		this.context=context;
		this.layoutId=layoutId;
		this.itemViewId=itemViewId;
		this.baseItemViewListenter=baseItemViewListenter;
		this.data=data;
	}
	
	
	public BaseItemAdpter(Context context,int  layoutId,Object[] dataArray,BaseItemViewListenter baseItemViewListenter,int ...itemViewId){
		this.context=context;
		this.layoutId=layoutId;
		this.itemViewId=itemViewId;
		this.baseItemViewListenter=baseItemViewListenter;
		this.dataArray=dataArray;
	}



	public void setHeadSize(int size){
		this.headViewSize = size;
	}
	@Override
	public int getCount() {
		return data==null?dataArray.length:data.size()+headViewSize;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView= View.inflate(context, layoutId, null);
			if(itemViewId!=null){
				SparseArray<View> viewHolder=new SparseArray<View>(itemViewId.length);
				for( int i=0;i<itemViewId.length;i++){
					View v=convertView.findViewById(itemViewId[i]);
					viewHolder.put(itemViewId[i],v);
				}
				convertView.setTag(viewHolder);
			}
		}
		if(baseItemViewListenter!=null)
			baseItemViewListenter.handerItemView((SparseArray<View>)convertView.getTag(),position, convertView, parent);
		return convertView;
	}
   public  interface BaseItemViewListenter{
	   public abstract void handerItemView(SparseArray<View> viewHolder, int position, View convertView, ViewGroup parent);
   }
	
}
