package com.visabao.machine.fragment.message;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.visabao.machine.R;
import com.visabao.machine.fragment.AreaVisaListFragment;
import com.visabao.machine.util.DateUitl;
import com.visabao.machine.util.Log;
import com.visabao.machine.util.StringUtil;
import com.visabao.machine.util.ToastUtil;
import com.visabao.machine.widget.calendar.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MessageDateFrament extends MessageBaseFrament {
    private CalendarView mCalendarView =  null;
    private TextView monthText = null;
    @Override
    public int getContentLayoutId() {
        return R.layout.message_date;
    }


    private  void setTitleYearMonth(){
        monthText.setText(curYear + "年" + curMonth + "月");
    }

    private void  showDate(){
        Calendar cal_select = Calendar.getInstance();
        cal_select.set(Calendar.YEAR, curYear);
        cal_select.set(Calendar.MONTH, curMonth-1);
        cal_select.set(Calendar.DAY_OF_MONTH, 1);
        mCalendarView.rebuildCalendar(cal_select);
        setTitleYearMonth();

    }



    int curYear;
    int curMonth;
    @Override
    public void initData(View view) {
        super.initData(view);
        LinearLayout mLinearLayout = (LinearLayout)view.findViewById(R.id.layout01);
        long startDate=-1;
        String traval_days=  getArguments().getString(AreaVisaListFragment.TRAVRL_DAYS);
        if (!StringUtil.isEmpty(traval_days)){
             Date date= DateUitl.parse(traval_days, DateUitl.DATE_PATTERN);
            startDate= date.getTime();
        }
        else {
            startDate= System.currentTimeMillis();
        }
        mCalendarView = new CalendarView(getActivity(),startDate);
        mLinearLayout.addView(mCalendarView);

        mCalendarView.setHeaderHeight(18);
        mCalendarView.setHeaderTextSize(18);
        mCalendarView.setBackgroundResource(R.color.white);
        mCalendarView.setSelectMinDate(startDate );
        mCalendarView.setOnItemClickListener(new CalendarView.OnCalendarItemClickListener() {

            @Override
            public void onClick(int position) {
                String date = mCalendarView.getStrDateAtPosition(position);
                // ToastUtil.showToast(getActivity(), "点击了" + position + "值：" + date);
            }
        });
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startDate);
        curYear = calendar.get(Calendar.YEAR); // 得到系统年份
        curMonth = calendar.get(Calendar.MONTH) + 1; // 得到系统月份
        monthText = (TextView)view.findViewById(R.id.monthText);
        setTitleYearMonth();
        ImageView leftBtn = (ImageView)view.findViewById(R.id.leftBtn);
        ImageView rightBtn = (ImageView)view.findViewById(R.id.rightBtn);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curMonth--;
              if (curMonth==0){
                  curYear--;
                  curMonth=12;
              }
               Calendar cal=   Calendar.getInstance();
              int year=    cal.get(Calendar.YEAR);
                int month= cal.get(Calendar.MONTH)+1;
                if (curYear==year&&curMonth<month){
                    curMonth=month;
                    curYear=year;
                }
                showDate();
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curMonth++;
                if (curMonth==13){
                    curYear++;
                    curMonth=1;
                }
                showDate();
            }
        });
    }

    public  static final String TIMER="TIMER";

    @Override
    public Fragment getTabNextIndex() {
        WriteMessageFrament messageFrament=new WriteMessageFrament();
        Bundle bundle=getArguments();
        bundle.putString(TIMER, mCalendarView.getCalSelected());
        messageFrament.setArguments(bundle);
        return messageFrament;
    }

    @Override
    public int getTabUpIndex() {
        return 0;
    }
}
