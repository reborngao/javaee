package com.visabao.machine.util;

import android.util.SparseArray;
import android.view.View;

public class ViewHolder<T> {
    private SparseArray<T> viewArray;
    
    @SuppressWarnings("unused")
	private  ViewHolder(){

    }
    public  ViewHolder(int length){
          viewArray=new SparseArray<T>(length);
    }

    /**
     * ImageView view = ViewHolder.get(convertView, R.id.imageView);
     * @param view
     * @param id
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();

        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public void put(int key ,T value){
    	viewArray.put(key, value);
    }
    
    public <T extends View> T get(int key){
    	return (T)viewArray.get(key);
    }
}
