package com.visabao.machine.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.visabao.machine.R;

public class ImageAdapter extends BaseAdapter 
{
	private ImageView[] mImages;		// ���浹ӰͼƬ������

	private Context mContext;
	public List<Map<String, Object>> list;
	

	public Integer[] imgs/* = { R.drawable.image01, R.drawable.image02, R.drawable.image03,
							  R.drawable.image04, R.drawable.image05}*/;

	public ImageAdapter(Context c)
	{
		this.mContext = c;
		list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < imgs.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("image", imgs[i]);
			list.add(map);
		}
		mImages = new ImageView[list.size()];
	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public  void createReflectedImages(){
		final int Height = 300;
		int index = 0;
		for (Map<String, Object> map : list) {
			Integer id = (Integer) map.get("image");
			Bitmap originalImage = BitmapFactory.decodeResource(mContext.getResources(), id);
			int width = originalImage.getWidth();
			int height = originalImage.getHeight();
			float scale = Height / (float) height;

			Matrix sMatrix = new Matrix();
			sMatrix.postScale(scale, scale);
			Bitmap miniBitmap = Bitmap.createBitmap(originalImage, 0, 0,
					originalImage.getWidth(), originalImage.getHeight(), sMatrix, true);

			originalImage.recycle();

			int mwidth = miniBitmap.getWidth();
			int mheight = miniBitmap.getHeight();
			Matrix matrix = new Matrix();
			matrix.preScale(1, -1);
			Bitmap bitmapWithReflection = Bitmap.createBitmap(mwidth, (mheight + mheight / 2), Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmapWithReflection);
			canvas.drawBitmap(miniBitmap, 0, 0, null);
			Paint paint = new Paint();

			ImageView imageView = new ImageView(mContext);
			imageView.setAlpha(0.5f);
			imageView.setImageBitmap(bitmapWithReflection);
			imageView.setLayoutParams(new GalleryView.LayoutParams((int) (width * scale),
					(int) (mheight * 3 / 2.0)));
			imageView.setScaleType(ScaleType.MATRIX);
			mImages[index++] = imageView;
		}
	}
	@Override
	public int getCount() {
		return imgs.length;
	}

	@Override
	public Object getItem(int position) {
		return mImages[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return mImages[position];
	}

	public float getScale(boolean focused, int offset) {
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}

}
