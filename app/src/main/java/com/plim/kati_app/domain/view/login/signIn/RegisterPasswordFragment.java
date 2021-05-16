package com.plim.kati_app.domain.view.login.signIn;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.katiCrossDomain.domain.view.AbstractFragment1;
import com.plim.kati_app.domain.katiCrossDomain.domain.model.forFrontend.KatiEntity;

public class RegisterPasswordFragment extends AbstractFragment1 {

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void initializeView() {
        super.initializeView();
        this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.mainTextView.setText(getString(R.string.register_password_maintext));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(getString(R.string.register_password_hint));
        this.button.setText(getString(R.string.button_ok));
    }

    /**
     * Callback
     */
    @Override
    protected void buttonClicked() {
        this.dataset.put(KatiEntity.EKatiData.PASSWORD, this.editText.getText().toString());
        this.navigateTo(R.id.action_register2Fragment_to_register3Fragment);
    }
}