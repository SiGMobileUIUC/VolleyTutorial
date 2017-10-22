package company.com.volleytutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Arrays;

/**
 * Websites
 * Volley:
 * https://developer.android.com/training/volley/index.html
 *
 * CUMTD:
 * https://developer.cumtd.com/
 *
 * Notices:
 * Do not forget the internet permission
 * Get a key from https://developer.cumtd.com/ and place it in KEY
 * Check if you are missing any dependencies in build.gradle:
 *   compile 'com.android.volley:volley:1.0.0'
 *   compile 'com.google.code.gson:gson:2.8.1'
 * Take a look at the tutorials in https://developer.android.com/training/volley/index.html.
 */
public class MainActivity extends AppCompatActivity {
    public static final String KEY = "Place your own KEY";
    public static final String URL = "https://developer.cumtd.com/api/v2.2/json/getstopsbylatlon";
    RequestQueue volleyQueue;
    TransportationStops ts;
    TextView textView;
    Button button;
    EditText edtLat;
    EditText edtLon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        button = (Button) findViewById(R.id.button);
        edtLat = (EditText) findViewById(R.id.edttext_lat);
        edtLon = (EditText) findViewById(R.id.edttext_lon);
//        Initialize your volley queue
        volleyQueue = Volley.newRequestQueue(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat = edtLat.getText().toString();
                String lon = edtLon.getText().toString();
                if (lat.equals("")) lat = "12.34";
                if (lon.equals("")) lon = "56.78";
                startJsonRequest(formatURL(KEY, lat, lon));
            }
        });
    }
    public String formatURL(String key, String lat, String lon){
        return URL + "?key=" + key + "&lat=" + lat + "&lon=" + lon;
    }

    private void startJsonRequest(String url){
        //create a request
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        ts = gson.fromJson(response.toString(), TransportationStops.class);
                        textView.setText(Double.toString(ts.getStops()[0].getDistance()));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        //Add to volley queue, will handle everything in background
        volleyQueue.add(jsObjRequest);
    }
}
