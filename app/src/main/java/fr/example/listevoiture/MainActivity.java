package fr.example.listevoiture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;


    public RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //voiture_recycler et button_parse dans le activity_main.xml
        mTextViewResult = findViewById(R.id.voiture_recycler);
        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        /*
        * au click du bouton on récupère toutes les données
        * */
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        //URL API
        String url = "http://api.kinomap.com/vehicle/list?icon=1&lang=en-gb&forceStandard=1&outputFormat=json&appToken=8qohg5a9c6q6x58szpyxizvp91yary3setxdxutl10dugtel1syjs6gmrp33oo40a356j2cxt6vdcpzg095drsym5blnyen0hi4bdq32j61clfux2i9vtuhr";

        //OBJET REQUETE sous méthode GET pour récupérer les données de l'API
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject object = new JSONObject("vehicleList");

                            JSONArray jsonArray = object.getJSONArray("response");
                            //Pour récuperer tous les objets de l'API avec une boucle for
                            for (int i  = 0; i< jsonArray.length(); i++){
                                JSONObject vehicleList = jsonArray.getJSONObject(i);

                                //Récupérer l'array "name" de l'API comme demander dans l'exercice
                                String vehicleName = vehicleList.getString("name");
                                int id = vehicleList.getInt("id");

                                //Afficher le résultat sous forme de texte
                                mTextViewResult.append(vehicleName + ',' +  String.valueOf(id) );
                            }
                            //Si erreur;
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
