package com.pb.app.caloriecalculator.data;

import com.pb.app.caloriecalculator.api.RetrofitCall;
import com.pb.app.caloriecalculator.data.model.LoggedInUser;
import com.pb.app.caloriecalculator.entity.AuthorisationEntity;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
//        Сделать проверку в БД
    }

    public void logout() {
        user = null;
        dataSource.logout();
        // Удаление из БД
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // Здесь записать в БД локал
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(AuthorisationEntity authorisationEntity) {
        // handle login
        RetrofitCall.getInstance().setAuthorisationEntity(authorisationEntity);
        Result<LoggedInUser> result = dataSource.login(authorisationEntity.getToken(), authorisationEntity.getUserName());
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    public LoggedInUser getUser() {
        return user;
    }
}
