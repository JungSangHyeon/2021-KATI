package com.plim.kati_app.domain.user.register;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.model.KatiViewModel;

public class Register3Fragment extends AbstractFragment1 {

    @Override
    protected void initializeView() {
        this.mainTextView.setText("이름을 알려주세요");
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint("당신의 닉네임을 알려주세요!");
        this.button.setText("확인");
    }

    @Override
    protected void buttonClicked() {
        KatiViewModel katiViewModel = new ViewModelProvider(this.requireActivity()).get(KatiViewModel.class);
        katiViewModel.setHeader(this.editText.getText().toString());

        Navigation.findNavController(this.getView()).navigate(R.id.action_register3Fragment_to_registerFinishedFragment);
    }
}