package com.plim.kati_app.domain.view.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.domain.view.user.findPW.FindPasswordActivity;
import com.plim.kati_app.domain.view.user.register.RegisterActivity;
import com.plim.kati_app.tech.RestAPIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginHomeFragment extends Fragment {
    /*
     * 로그인 버튼 클릭 시 나오는 화면
     * */
    // Associate
    // View
    private EditText idText, pwText;
    private Button loginButton, idFindButton, pwFindButton, accountCreateButton;
    private CheckBox autologinCheckBox;

    /**
     * System Callback
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login_home_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.idText = view.findViewById(R.id.loginActivity_idText);
        this.pwText = view.findViewById(R.id.loginActivity_pwText);
        this.loginButton = view.findViewById(R.id.loginActivity_loginButton);
        this.idFindButton = view.findViewById(R.id.loginActivity_idFindButton);
        this.pwFindButton = view.findViewById(R.id.loginActivity_pwFindButton);
        this.accountCreateButton = view.findViewById(R.id.loginActivity_accountCreateButton);
        this.autologinCheckBox=view.findViewById(R.id.loginActivity_autologinCheckBox);

        //디버그 시 입력 귀찮아서 미리 입력해놓는 코드. 주석 해놓을 것.
//        this.idText.setText("remember@rem.com");
//        this.pwText.setText("1234");

        this.pwFindButton.setOnClickListener(v->this.startActivity(new Intent(this.getContext(), FindPasswordActivity.class)));

        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(this.getContext());
            if (database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION) != null) {
                getActivity().runOnUiThread(() ->
                        KatiDialog.simpleAlertDialog(getContext(),getString(R.string.login_already_signed_dialog),getString(R.string.login_already_signed_content_dialog),(dialog, which) -> startMainActivity(),
                                this.getResources().getColor(R.color.kati_coral, getActivity().getTheme())).showDialog()
                );
            }
        }).start();

        this.autologinCheckBox.setOnClickListener((v -> {
            this.setAutoLogin();
        }));

        /*
         * 회원가입 버튼 클릭
         */
        this.accountCreateButton.setOnClickListener(v -> {
            this.startActivity(new Intent(getContext(), RegisterActivity.class));
        });

        /*
         * 로그인 버튼 클릭
         * 401 -> 인증 실패
         * 500 -> 서버 오류
         * 200 -> 인증 성공
         * */
        this.loginButton.setOnClickListener(v -> {
            new Thread(()->{
                this.retrofitLogin();
            }).start();
        });
    }

    /**
     * 로그인 요청.
     */
    private void retrofitLogin(){

        String email = idText.getText().toString();
        String password = pwText.getText().toString();

        LoginRequest login = new LoginRequest(email, password);
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<LoginRequest> call = retrofitClient.apiService.postRetrofitData(login);
        call.enqueue(new Callback<LoginRequest>() {
            @Override
            public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {
                if (response.isSuccessful()) {
                    Log.d("연결 성공적 : ", "code : " + response.code());

                    KatiDialog.simpleAlertDialog(
                            getContext(),
                            getString(R.string.login_success_dialog),
                            getString(R.string.login_success_content_dialog),
                            (dialog, which) -> {
                                //헤더값에서 토큰값을 꺼내서 넣기.
                                KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
                                new Thread(() -> {

                                    database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, response.headers().get(KatiDatabase.AUTHORIZATION)));
                                    if(autologinCheckBox.isChecked()){
                                        database.katiDataDao().insert(new KatiData(KatiDatabase.EMAIL,idText.getText().toString()));
                                        database.katiDataDao().insert(new KatiData(KatiDatabase.PASSWORD,pwText.getText().toString()));
                                    }

                                }).start();
                                startMainActivity();
                            }, getResources().getColor(R.color.kati_coral, getContext().getTheme())).showDialog();

                } else {
                    Log.e("연결 비정상적 : ", "error code : " + response.code());
                    KatiDialog.simpleAlertDialog(getContext(),
                            getString(R.string.login_failed_dialog),
                            getString(R.string.login_failed_content_dialog),
                            (dialog, which) -> {
                                idText.setText("");
                                pwText.setText("");
                            }
                            , getResources().getColor(R.color.kati_coral, getContext().getTheme())).showDialog();
                    return;
                }
            }

            @Override
            public void onFailure(Call<LoginRequest> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    /**
     * 오토로그인을 하도록 혹은 안하도록 설정값을 저장한다.
     */
    private void setAutoLogin() {
        new Thread(()->{
            KatiDatabase katiDatabase=KatiDatabase.getAppDatabase(getContext());
            String data=this.autologinCheckBox.isChecked()?"1":"0";
            katiDatabase.katiDataDao().insert(new KatiData(KatiDatabase.AUTO_LOGIN,data));
            Log.d("카티 데이터 오토 로그인",data);
        }).start();
    }

    /**
     * 메인 액티비티를 시작한다.
     */
    public void startMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

}