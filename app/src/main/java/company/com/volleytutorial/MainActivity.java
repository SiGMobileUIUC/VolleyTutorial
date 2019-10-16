package company.com.volleytutorial;

import androidx.appcompat.app.AppCompatActivity;
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
    public static final String KEY = "API_KEY_HERE";
    public static final String BASE_URL = "https://developer.cumtd.com/api/v2.2/json/getstopsbylatlon";

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
        textView = findViewById(R.id.textview);
        button = findViewById(R.id.button);
        edtLat = findViewById(R.id.edttext_lat);
        edtLon = findViewById(R.id.edttext_lon);

        volleyQueue = Volley.newRequestQueue(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat = edtLat.getText().toString();
                String lon = edtLon.getText().toString();
                if (lat.equals("") || lon.equals("")) return;
                startJsonRequest(formatURL(KEY, lat, lon));
            }
        });
    }

    public String formatURL(String key, String lat, String lon){
        return BASE_URL + "?key=" + key + "&lat=" + lat + "&lon=" + lon;
    }

    private void startJsonRequest(String url){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        ts = gson.fromJson(response.toString(), TransportationStops.class);
                        String stopName = ts.getStops()[0].getStop_name();
                        textView.setText(stopName);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { }
                });

        volleyQueue.add(jsObjRequest);
    }
}
