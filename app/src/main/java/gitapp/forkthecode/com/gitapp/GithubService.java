package gitapp.forkthecode.com.gitapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ralph on 07/10/17.
 */

public interface GithubService {

    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);


}
