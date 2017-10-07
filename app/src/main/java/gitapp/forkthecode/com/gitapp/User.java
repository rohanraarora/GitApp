package gitapp.forkthecode.com.gitapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ralph on 06/10/17.
 */

public class User {

    String login;
    long id;
    String name;

    @SerializedName("avatar_url")
    String avatarUrl;

    @SerializedName("html_url")
    String htmlUrl;
    int followers;
    int following;

}
