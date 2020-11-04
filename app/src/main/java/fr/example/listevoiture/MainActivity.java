package fr.example.listevoiture;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView vehicleListView;
    private RequestQueue mQueue;
    List<Vehicle> vehicles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //liste vehicule
        vehicleListView = findViewById(R.id.vehicleList);

        /*
         *bouton pour accéder à la liste
         * button_parse => dans le xml
         */
        Button buttonParse = findViewById(R.id.button_parse);
        mQueue = Volley.newRequestQueue(this);


        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse(v.getContext());
                buttonParse.setVisibility(View.GONE);
            }
        });

        vehicleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Vehicle vehicleOnClick = (Vehicle) vehicleListView.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "l'id du vehicule " + vehicleOnClick.getName() + " est : " + vehicleOnClick.getId(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void jsonParse(final Context context) {
        /*
         * appel de l'URL pour l'API
         */
        String url = "http://api.kinomap.com/vehicle/list?icon=1&lang=en-gb&forceStandard=1&outputFormat=json&appToken=8qohg5a9c6q6x58szpyxizvp91yary3setxdxutl10dugtel1syjs6gmrp33oo40a356j2cxt6vdcpzg095drsym5blnyen0hi4bdq32j61clfux2i9vtuhr";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                /*
                 * method GET pour réceptionner les donées
                 */
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject vehicleListJson) {
                        try {
                            JSONObject vehicleList = vehicleListJson.getJSONObject("vehicleList");
                            JSONArray response = vehicleList.getJSONArray("response");
                            for (int i  = 0; i< response.length(); i++){
                                JSONObject vehicleJson = response.getJSONObject(i);
                                // nouveau vehicule
                                Vehicle vehicle = new Vehicle();
                                // liste des vehicule en fonction du nom et de l'icone et de l'id
                                vehicle.setName(vehicleJson.getString("name"));
                                vehicle.setId(vehicleJson.getInt("id"));
                                vehicle.setIcon(vehicleJson.getJSONObject("icon").getJSONObject("url").getString("size50x50"));
                                vehicles.add(vehicle);
                            }
                            vehicleListView.setAdapter(new VehicleAdapter(context,0,vehicles));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}
