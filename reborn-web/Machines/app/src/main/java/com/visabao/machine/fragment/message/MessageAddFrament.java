package com.visabao.machine.fragment.message;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.visabao.machine.LoginAcitivity;
import com.visabao.machine.R;
import com.visabao.machine.entity.Person;
import com.visabao.machine.entity.User;
import com.visabao.machine.global.BaseItemAdpter;
import com.visabao.machine.util.Log;
import com.visabao.machine.util.StringUtil;
import com.visabao.machine.util.ToastUtil;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

public class MessageAddFrament extends MessageBaseFrament implements BaseItemAdpter.BaseItemViewListenter {

    public final static String PERSON = "PERSON";
    private VisaMessageFrament messageFrament;

    @Override
    public Fragment getTabNextIndex() {
        if (checkMsg()) {
            MessageSelectFrament selectFrament = new MessageSelectFrament();
            Bundle bundle = getArguments();
            bundle.putSerializable(PERSON, (Serializable) data);
            selectFrament.setArguments(bundle);
            return selectFrament;
        }
        return null;
    }

    private boolean checkMsg() {
        if (data.size() == 0) {
            ToastUtil.showToast(getActivity(), "请增加签证人");
            return false;
        }
        return true;
    }

    @Override
    public int getTabUpIndex() {

        messageFrament.setPersonData(data);
        return R.id.message_rb_message;
    }

    List<Person> data;
    BaseItemAdpter adpter;

    @Override
    public void initData(View view) {
        super.initData(view);
        data = new ArrayList<Person>();
        Fragment fragment = getParentFragment();
        if (fragment instanceof VisaMessageFrament) {
            messageFrament = ((VisaMessageFrament) fragment);
            if (messageFrament.getPersonList()!=null){
                data=messageFrament.getPersonList();
            }
        }

        ListView listView = (ListView) view.findViewById(R.id.message_add_listview);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        adpter = new BaseItemAdpter(getActivity(), R.layout.item_add_preson, data, this, R.id.item_tv_name, R.id.item_tv_name, R.id.item_iv_icon, R.id.item_mobile, R.id.item_tv_detele, R.id.item_tv_eidt);
        listView.setAdapter(adpter);
        LinearLayout llAdd = (LinearLayout) view.findViewById(R.id.ll_add);
        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(-1);
            }
        });
    }

    Dialog dialog = null;
    RadioGroup radioGroup;
    TextView tvName;
    EditText etPassportNumber;
    EditText etMobile;

    private void showDialog(final int postion) {
        if (dialog == null) {
            dialog = new Dialog(getActivity(), R.style.myDialogTheme);
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        dialog.setContentView(R.layout.add_dialog);
        radioGroup = (RadioGroup) dialog.findViewById(R.id.dialog_sex);
        etPassportNumber = (EditText) dialog.findViewById(R.id.dialog_et_passport_number);
        etMobile = (EditText) dialog.findViewById(R.id.dialog_et_mobie);
        tvName = (TextView) dialog.findViewById(R.id.dialog_tv_name);
        if (postion > -1) {
            Person person = data.get(postion);
            radioGroup.check(person.getSex() == Person.MALE ? R.id.dialog_rb_male : R.id.dialog_rb_female);
            etPassportNumber.setText(person.getPassportNumber() + "");
            etMobile.setText(person.getMobile());
            tvName.setText(person.getName());
        } else {
            radioGroup.check(R.id.dialog_rb_male);
        }


        Button btnCommit = (Button) dialog.findViewById(R.id.dialog_btn_conmit);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListDataPerson(postion);

            }
        });
        dialog.show();
    }

    private boolean isCheckMsg() {
        if (StringUtil.isEmpty(tvName.getText().toString())) {
            ToastUtil.showToast(getActivity(), "请输入中文姓名");
            return false;
        }
      /*  if (StringUtil.isEmpty(etPassportNumber.getText().toString())){
            ToastUtil.showToast(getActivity(),"请输入护照号码");
            return false;
        }*/
        if (!StringUtil.isMobileNo(etMobile.getText().toString())) {
            ToastUtil.showToast(getActivity(), "请输入联系电话");
            return false;
        }
        return true;
    }

    private void addListDataPerson(int postion) {
        if (isCheckMsg()) {
            Person person = new Person();
            person.setMobile(etMobile.getText().toString());
            person.setName(tvName.getText().toString());
            person.setPassportNumber(etPassportNumber.getText().toString());
            person.setSex(radioGroup.getCheckedRadioButtonId() == R.id.dialog_rb_male ? Person.MALE : Person.FEMABLE);
          if (postion==-1){
              for (Person p: data){
                  if (p.getMobile().equals(person.getMobile())&&p.getName().equals(person.getName())) {
                      ToastUtil.showToast(getActivity(),"签证人信息已存在");
                      person=null;
                      break;
                  }
              }
          }
            if (person != null) {
                if (postion > -1) {
                    data.set(postion, person);
                } else {
                    data.add(person);
                }
                adpter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }
    }


    @Override
    public int getContentLayoutId() {
        return R.layout.message_add;
    }

    @Override
    public void handerItemView(SparseArray<View> viewHolder, final int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) viewHolder.get(R.id.item_iv_icon);
        Person person = data.get(position);
        if (person.getSex() == Person.FEMABLE) {
            imageView.setImageResource(R.mipmap.female);
        } else {
            imageView.setImageResource(R.mipmap.male);
        }
        TextView tvName = (TextView) viewHolder.get(R.id.item_tv_name);
        tvName.setText(person.getName());
        TextView tvMobile = (TextView) viewHolder.get(R.id.item_mobile);
        tvMobile.setText(person.getMobile());

        TextView tvDetele = (TextView) viewHolder.get(R.id.item_tv_detele);
        tvDetele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog = new AlertDialog.Builder(getActivity()).
                        setTitle("")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                data.remove(position);
                                adpter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                                // .setNeutralButton(skip, mOnRequestListener)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.setMessage("是否删除签证人？");
                dialog.show();

            }
        });

        TextView tvEidt = (TextView) viewHolder.get(R.id.item_tv_eidt);
        tvEidt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });
    }
}
