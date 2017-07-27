package curso.umg.gt.umgencuesta;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Resultado extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> adapter;
    String listU = "http://umgandroidencuesta.txoljasupervisi.com/list.php";
    InputStream is = null;
    String line = null;
    String result =null;
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        lv = (ListView) findViewById(R.id.lvEncuesta);

        //Allow network in main thread
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        //Retrive
        getDataUser();

        //adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);
        lv.setAdapter(adapter);
    }

    private void getDataUser(){
        try {
            URL url = new URL(listU);
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            is=new BufferedInputStream(con.getInputStream());



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Leer el contenido
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while ((line =br.readLine())!= null ){
                sb.append(line+"\n");
            }
            is.close();
            result = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        //Parse Json Data
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;
            data = new String[ja.length()];

            for (int i=0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String name = jo.getString("nombres");
                data[i]= "Id: "+jo.getString("id")
                        +"\nNombres: "+name
                        +"\nAños de Experiencia: "+jo.getString("experiencia")
                        +"\nLenguaje preferido: "+jo.getString("lenguaje")
                        +"\nTipo: "+jo.getString("tipo")
                        +"\nLe gusta su trabajo: "+jo.getString("satisfaccion");

            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
