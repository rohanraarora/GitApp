package gitapp.forkthecode.com.gitapp;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ralph on 06/10/17.
 */

public class UserAsyncTask extends AsyncTask<String,Void,User> {

    public interface UserTaskListener{

        void onDownload(User user);
        void onError(String message);

    }

    private UserTaskListener userTaskListener;

    public UserAsyncTask(UserTaskListener listener){
        userTaskListener = listener;
    }

    @Override
    protected User doInBackground(String... strings) {

        try {
            String username = strings[0];
            String urlString = "https://api.github.com/users/" + username;
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String result = "";
            while (scanner.hasNext()){
                result += scanner.next();
            }
            return parseUser(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private User parseUser(String response){


        try {
            JSONObject rootObject = new JSONObject(response);
            String login = rootObject.getString("login");
            long id = rootObject.getLong("id");
            String name = rootObject.getString("name");
            String avatarUrl = rootObject.getString("avatar_url");
            String htmlUrl = rootObject.getString("html_url");
            int followers = rootObject.getInt("followers");
            int following = rootObject.getInt("following");
            User user = new User();
            user.avatarUrl = avatarUrl;
            user.followers = followers;
            user.following = following;
            user.htmlUrl = htmlUrl;
            user.id = id;
            user.login = login;
            user.name = name;
            return user;

        }
        catch (Exception e){
            return null;
        }

    }

    @Override
    protected void onPostExecute(User user) {
        if(user == null){
            userTaskListener.onError("Something Went Wrong");

        }
        else {
            userTaskListener.onDownload(user);
        }
    }
}
