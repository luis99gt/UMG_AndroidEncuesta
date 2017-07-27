package curso.umg.gt.umgencuesta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by L99 on 7/27/2017.
 */

public class Conexion extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;

    Conexion(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String registro_url = "http://umgandroidencuesta.txoljasupervisi.com/register.php";
        if (type.equals("registro")) {
            try {
                String usr_nombres = params[1];
                String usr_anios = params[2];
                String usr_lang = params[3];
                String usr_tipo = params[4];
                String usr_like = params[5];
                URL url = new URL(registro_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data =
                        URLEncoder.encode("u_nombres", "UTF-8") + "=" + URLEncoder.encode(usr_nombres, "UTF-8") + "&"
                                +URLEncoder.encode("u_anios", "UTF-8") + "=" + URLEncoder.encode(usr_anios, "UTF-8") + "&"
                                +URLEncoder.encode("u_lang", "UTF-8") + "=" + URLEncoder.encode(usr_lang, "UTF-8") + "&"
                                +URLEncoder.encode("u_tipo", "UTF-8") + "=" + URLEncoder.encode(usr_tipo, "UTF-8") + "&"
                                + URLEncoder.encode("u_like", "UTF-8") + "=" + URLEncoder.encode(usr_like, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
//        super.onPreExecute();
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Envio Satisfactorio");
    }

    @Override
    protected void onPostExecute(String result) {
//        super.onPostExecute(aVoid);


        if(result.toString().equals("ok"))
        {
            Intent i = new Intent(context, Resultado.class);
            context.startActivity(i);
        }else
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        }


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void ir(View view){

    }
}

