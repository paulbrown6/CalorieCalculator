package com.pb.app.caloriecalculator.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.util.Patterns;

import com.pb.app.caloriecalculator.api.App;
import com.pb.app.caloriecalculator.data.LoginRepository;
import com.pb.app.caloriecalculator.data.Result;
import com.pb.app.caloriecalculator.data.database.DatabaseSQL;
import com.pb.app.caloriecalculator.data.model.LoggedInUser;
import com.pb.app.caloriecalculator.R;
import com.pb.app.caloriecalculator.entity.AuthorisationEntity;
import com.pb.app.caloriecalculator.entity.RegistrationEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    static AuthorisationEntity auth = new AuthorisationEntity();
    static RegistrationEntity registr = new RegistrationEntity();
    private static App app = new App();

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        app.getApi().getSignin(username, password).enqueue(new Callback<AuthorisationEntity>() {
            @Override
            public void onResponse(Call<AuthorisationEntity> call, Response<AuthorisationEntity> response) {
                if (response.code() == 202){
                    auth = response.body();
                    Log.d("API", "он вошел " + response.code() + " || " + response.body() + " || " + response.message());
                    Result<LoggedInUser> result = loginRepository.login(auth);
                    if (result instanceof Result.Success) {
                        LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                        loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
                    } else {
                        loginResult.setValue(new LoginResult(R.string.login_failed));
                    }
                } else {
                    Log.d("API", "он не вошел" + response.message() + " || код " + response.code());
                    loginResult.setValue(new LoginResult(R.string.login_failed));
                }

            }

            @Override
            public void onFailure(Call<AuthorisationEntity> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void registration(String username, String email, String password) {
        app.getApi().getRegistration(username, email, password).enqueue(new Callback<RegistrationEntity>() {
            @Override
            public void onResponse(Call<RegistrationEntity> call, Response<RegistrationEntity> response) {
                if (response.code() == 201){
                    registr = response.body();
                    auth = new AuthorisationEntity();
                    auth.setUserID(registr.getUserID());
                    auth.setToken(registr.getToken());
                    auth.setUserName(registr.getUserName());
                    Log.d("API", "он зарегистрировался " + response.code() + " || " + response.body() + " || " + response.message());
                    Result<LoggedInUser> result = loginRepository.login(auth);
                    if (result instanceof Result.Success) {
                        LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                        loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
                    } else {
                        loginResult.setValue(new LoginResult(R.string.login_failed));
                    }
                } else {
                    Log.d("API", "он не зарегистрировался" + response.message() + " || код " + response.code());
                    loginResult.setValue(new LoginResult(R.string.login_failed));
                }

            }

            @Override
            public void onFailure(Call<RegistrationEntity> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
