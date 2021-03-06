package com.plim.kati_app.kati.domain.temp.logout.view;

import android.os.Bundle;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;
import com.plim.kati_app.kati.domain.TempMainActivity;

public class LogOutActivity extends KatiViewModelActivity {

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_log_out);
    }
    @Override
    public void katiEntityUpdated() {
        if(this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){
            this.dataset.remove(KatiEntity.EKatiData.AUTHORIZATION);
            this.dataset.remove(KatiEntity.EKatiData.EMAIL);
            this.dataset.remove(KatiEntity.EKatiData.PASSWORD);
            this.dataset.put(KatiEntity.EKatiData.AUTO_LOGIN, KatiEntity.EKatiData.FALSE.name());
            this.showOkDialog();
        }else{
            this.showNoDialog();
        }
    }
    public void showOkDialog(){ this.showDialog(Constant.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_TITLE, Constant.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_MESSAGE); }
    public void showNoDialog(){ this.showDialog(Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE, Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_MESSAGE); }
    public void showDialog(String title, String message){ KatiDialog.simplerAlertDialog(this, title, message, (dialog, which) -> this.startActivity(TempMainActivity.class)); }

    @Override protected void associateView() { }
    @Override protected void initializeView() { }
}