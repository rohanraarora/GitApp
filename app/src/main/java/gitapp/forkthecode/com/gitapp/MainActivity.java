package gitapp.forkthecode.com.gitapp;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

        fetchUser("rohanraarora");

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
