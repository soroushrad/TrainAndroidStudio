package plazmid.trainandroidstudio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class webserver extends AppCompatActivity {

    private Button show_btn;
    private ListView my_list;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webserver);

        final String url = "http://172.26.92.55/wordpress/api/get_recent_posts/";
        show_btn = (Button) findViewById(R.id.show_btn);
        my_list = (ListView) findViewById(R.id.my_list);
        title = (TextView) findViewById(R.id.title);


        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                GetValueByAssync(url);


            }
        });

    }

    private void GetValueByAssync(String url) {

        AsyncHttpClient contact_client = new AsyncHttpClient();
        contact_client.post(url, null, new JsonHttpResponseHandler() {


//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                getJSON(response);
//
//                super.onSuccess(statusCode, headers, response);
//            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                getJSON(response);

                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }

    private String getJSON(JSONObject response) {

        try {

            JSONObject all_obj = new JSONObject(String.valueOf(response));
            JSONArray posts_arry = all_obj.getJSONArray("posts");
            StringBuffer finalBufferedData = new StringBuffer();

            for (int i = 0; i < posts_arry.length(); i++) {
                JSONObject jsonobject = posts_arry.getJSONObject(i);
                String content_str = jsonobject.getString("content");
                String title_str = jsonobject.getString("title");
                Object image_int = jsonobject.get("thumbnail");

                finalBufferedData.append(content_str + "****" + title_str + "@@@@@" + image_int);
            }


            title.setText(finalBufferedData);

            return finalBufferedData.toString();


        } catch (Exception e) {

            Toast.makeText(this, "Error" + e, Toast.LENGTH_SHORT).show();


        }


        return null;
    }
}
