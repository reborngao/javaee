package com.visabao.machine.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

import com.visabao.machine.util.Log;

public class GalleryView extends Gallery 
{
	private Camera mCamera = new Camera();
	private int mMaxRotationAngle = 30;
	private int mMaxZoom = -80;

	public GalleryView(Context context) {
		super(context);
	}

	public GalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GalleryView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	private int getCenterOfCoverflow()
	{
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
	}

	private static int getCenterOfView(View view) {
		return view.getLeft() + view.getWidth() / 2;
	}
	private int mCoveflowCenter;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mCoveflowCenter = getCenterOfCoverflow();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected boolean getChildStaticTransformation(View child, Transformation trans) 
	{
		final int childCenter = getCenterOfView(child);
		final int childWidth = child.getWidth();
		int rotationAngle = 0;
		trans.clear();
		trans.setTransformationType(Transformation.TYPE_BOTH);
		rotationAngle = (int) ( ( (float) (mCoveflowCenter - childCenter) / childWidth ) * mMaxRotationAngle );
		transformImageBitmap((ImageView) child, trans, child == getSelectedView() ? 0 :rotationAngle);
		trans.setAlpha( child == getSelectedView() ? 1.0f :0.1f);
		return true;
	}

	private void transformImageBitmap(ImageView child, Transformation trans, int rotationAngle) 
	{
		mCamera.save();
		
		final Matrix imageMatrix = trans.getMatrix();
		final int imageHeight = child.getLayoutParams().height;
		final int imageWidth = child.getLayoutParams().width;
		final int rotation = Math.abs(rotationAngle);

		mCamera.translate(0.0f, 0.0f, -100.0f);

		if (rotation < mMaxRotationAngle)
		{
			float zoomAmount = (float) (mMaxZoom + (rotation * 1.5));
			mCamera.translate(0.0f, 0.0f, zoomAmount);
		}

		mCamera.rotateY(rotationAngle);		// rotationAngle Ϊ����y��������ת�� Ϊ������y������ת
		
		mCamera.getMatrix(imageMatrix);
		imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
		imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
		
		mCamera.restore();
	}
}