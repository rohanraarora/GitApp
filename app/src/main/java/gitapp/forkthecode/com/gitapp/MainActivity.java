package gitapp.forkthecode.com.gitapp;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    CoordinatorLayout root;
    TextView username;
    TextView name;
    ImageView avatar;
    TextView followers;
    TextView following;

    String profileURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = this.findViewById(R.id.root);
        username =  this.findViewById(R.id.username);
        name = this.findViewById(R.id.name);
        avatar = this.findViewById(R.id.avatar);
        followers = this.findViewById(R.id.followers);
        following = this.findViewById(R.id.following);

//        User user = new User();
//        user.name = "Rohan";
//        user.login = "rohanraarora";
//        user.avatarUrl = "abcd";
//        user.htmlUrl = "xyz";

        //Serialization

        Gson gson = new Gson();
//        String jsonString = gson.toJson(user);
//        Log.i("JSON User",jsonString);



        String jsonString = "{\n" +
                "\"login\": \"rohanraarora\",\n" +
                "\"id\": 6624213,\n" +
                "\"avatar_url\": \"https://avatars3.githubusercontent.com/u/6624213?v=4\",\n" +
                "\"gravatar_id\": \"\",\n" +
                "\"url\": \"https://api.github.com/users/rohanraarora\",\n" +
                "\"html_url\": \"https://github.com/rohanraarora\",\n" +
                "\"followers_url\": \"https://api.github.com/users/rohanraarora/followers\",\n" +
                "\"following_url\": \"https://api.github.com/users/rohanraarora/following{/other_user}\",\n" +
                "\"gists_url\": \"https://api.github.com/users/rohanraarora/gists{/gist_id}\",\n" +
                "\"starred_url\": \"https://api.github.com/users/rohanraarora/starred{/owner}{/repo}\",\n" +
                "\"subscriptions_url\": \"https://api.github.com/users/rohanraarora/subscriptions\",\n" +
                "\"organizations_url\": \"https://api.github.com/users/rohanraarora/orgs\",\n" +
                "\"repos_url\": \"https://api.github.com/users/rohanraarora/repos\",\n" +
                "\"events_url\": \"https://api.github.com/users/rohanraarora/events{/privacy}\",\n" +
                "\"received_events_url\": \"https://api.github.com/users/rohanraarora/received_events\",\n" +
                "\"type\": \"User\",\n" +
                "\"site_admin\": false,\n" +
                "\"name\": \"Rohan Arora\",\n" +
                "\"company\": \"@CodingNinjasCodes @gdgnewdelhi \",\n" +
                "\"blog\": \"\",\n" +
                "\"location\": \"New Delhi\",\n" +
                "\"email\": null,\n" +
                "\"hireable\": true,\n" +
                "\"bio\": null,\n" +
                "\"public_repos\": 59,\n" +
                "\"public_gists\": 2,\n" +
                "\"followers\": 99,\n" +
                "\"following\": 16,\n" +
                "\"created_at\": \"2014-02-08T12:15:26Z\",\n" +
                "\"updated_at\": \"2017-10-01T20:47:54Z\"\n" +
                "}";

       // User user = gson.fromJson(jsonString,User.class);

        //fetchUser("rohanraarora");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubService service = retrofit.create(GithubService.class);

        Call<User> call = service.getUser("codingninjascodes");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                username.setText(user.login);
                name.setText(user.name);
                followers.setText("Followers: " + user.followers);
                following.setText("Following: " + user.following);
                profileURL = user.htmlUrl;
                Picasso.with(MainActivity.this).load(user.avatarUrl).into(avatar);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Snackbar.make(root,t.getMessage(),Snackbar.LENGTH_LONG).show();
            }
        });

    }

    public void fetchUser( String usernameString){

        UserAsyncTask task = new UserAsyncTask(new UserAsyncTask.UserTaskListener() {
            @Override
            public void onDownload(User user) {

                username.setText(user.login);
                name.setText(user.name);
                followers.setText("Followers: " + user.followers);
                following.setText("Following: " + user.following);
                profileURL = user.htmlUrl;
                Picasso.with(MainActivity.this).load(user.avatarUrl).into(avatar);

            }

            @Override
            public void onError(String message) {
                Snackbar.make(root,message,Snackbar.LENGTH_LONG).show();
            }
        });
        task.execute(usernameString);

    }

    public void openProfile(View view){

        if(profileURL != null){
            Intent profileIntent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(profileURL);
            profileIntent.setData(uri);
            startActivity(profileIntent);
        }


    }
}
