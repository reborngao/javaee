package com.visabao.machine.global;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class BaseActivity  extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAAppManager.getAppManager().addActivity(this);
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TAAppManager.getAppManager().addActivity(this);
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TAAppManager.getAppManager().removeActivity(this);
    }
    public <T> T  find(int id){
        return (T) findViewById(id);
    }
    public void startIntent( Class<?> cls){
        Intent intent =new Intent(this,cls);
        startActivity(intent);
    }





    public  void click(int resId){
        findViewById(resId).setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {

    }
}
