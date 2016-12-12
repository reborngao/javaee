package com.visabao.machine;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.RadioGroup;

import com.visabao.machine.global.BaseActivity;

/**
 * 办理指引
 */
public class ManagementGuidelinesActivity extends BaseActivity {

    String[] url = {"job", "liberal_professions ", "retire", "already_adult", "minor", "school_age"};
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.management_guideline);
        View view = find(R.id.mian_tv_guidelines);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
         webView = find(R.id.mg_webview);

        RadioGroup radioGroup = find(R.id.mg_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int tag = Integer.parseInt(group.findViewById(checkedId).getTag().toString());
                webView.loadUrl("file:///android_asset/" + url[tag] + ".html");
            }
        });
        radioGroup.check(R.id.mg_job);
    }
}
