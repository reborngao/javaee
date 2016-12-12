package com.visabao.machine.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.visabao.machine.R;
import com.visabao.machine.util.Log;



public class MianRadioButton extends LinearLayout  implements Checkable {
    private boolean mChecked;
    private boolean mBroadcasting;
    private OnCheckedChangeListener mOnCheckedChangeListener;

    private  Drawable iconDrawabl,toRightDrawable;
    private  ColorStateList colorStateListTextColor;
    private String tabText;
    ImageView ivIcon;
    TextView tvText;
    View toRightView;

    public static interface OnCheckedChangeListener {
        void onCheckedChanged(MianRadioButton buttonView, boolean isChecked);
    }

    public MianRadioButton(Context context) {
        super(context);
        initView();
    }

    public MianRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        getStyleable(context, attrs);
        initView();
    }
    private void getStyleable(Context context, AttributeSet attrs){
         TypedArray  typeArray = context.obtainStyledAttributes(attrs, R.styleable.MianRadioButton);
        iconDrawabl=  typeArray.getDrawable(R.styleable.MianRadioButton_tabIcon);
         colorStateListTextColor=  typeArray.getColorStateList(R.styleable.MianRadioButton_tabTextColor);
        toRightDrawable=   typeArray.getDrawable(R.styleable.MianRadioButton_tabTopRightBackground);
        tabText= typeArray.getString(R.styleable.MianRadioButton_tabText);
        typeArray.recycle();
    }
    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
    }


    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
            ivIcon.mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }
    public void toggle() {
        setChecked(!mChecked);
    }


    @Override
    public void setChecked(boolean checked) {
        Log.e("============================>" + mChecked);
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
            if (mBroadcasting) {
                return;
            }
            mBroadcasting = true;
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
            }
               /* if ( tvText.getTextColors()!=null){
                    tvText.getTextColors().getColorForState(isChecked() ? CHECKED_STATE_SET : new int[]{0}, Color.BLACK);
                }*/
            //if (ivIcon.getDrawable()!=null){

            //}
            if ( toRightView.getBackground()!=null) {
                toRightView.getBackground().setState(isChecked() ? CHECKED_STATE_SET : new int[]{0});
            }



            mBroadcasting = false;
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }


    private void initView() {
        findViewId();
    }
    View contentView;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void findViewId() {
        contentView = View.inflate(getContext(), R.layout.mian_radio_button, null);
        ivIcon = (ImageView) contentView.findViewById(R.id.tab_iv_icon);
        tvText = (TextView) contentView.findViewById(R.id.tab_tv_text);
        toRightView= contentView.findViewById(R.id.tab_toright);

        if(iconDrawabl!=null)
            ivIcon.setImageDrawable(iconDrawabl);
        if(colorStateListTextColor!=null)
            tvText.setTextColor(colorStateListTextColor);
        if(tabText!=null) tvText.setText(tabText);
        if(toRightView!=null)
            toRightView.setBackground(toRightDrawable);
        addView(contentView,new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
    }
}
