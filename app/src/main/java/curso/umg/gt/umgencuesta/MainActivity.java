package curso.umg.gt.umgencuesta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText v_nombre, v_anios;
    private Spinner v_spiLang;
    String[] datosSpiLang = {"PHP","JAVA","C++","C#"};
    RadioGroup rg;
    Switch like;

//    Toast toLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v_nombre = (EditText) findViewById(R.id.etName);
        v_anios = (EditText) findViewById(R.id.etAnios);

        v_spiLang = (Spinner) findViewById(R.id.spiLenguajes);
        ArrayAdapter<String> adaptadorLang = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datosSpiLang);
        v_spiLang.setAdapter(adaptadorLang);

        rg = (RadioGroup) findViewById(R.id.rgTipo);

        like = (Switch) findViewById(R.id.sLike);

    }

    public void insertEncuesta(View view){
        String s_nombre = v_nombre.getText().toString();
//        int s_anios = Integer.parseInt(v_anios.getText().toString());
        String s_anios = v_anios.getText().toString();
        String s_lang = v_spiLang.getSelectedItem().toString();
        String s_tipo = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        String s_leGusta;
        String tipoOperacion = "registro";

        if (like.isChecked() == true){
            s_leGusta= "Si";
        }else {
            s_leGusta = "No";
        }
        Conexion conexion = new Conexion(this);
        conexion.execute(tipoOperacion, s_nombre, s_anios, s_lang, s_tipo, s_leGusta);

        v_nombre.setText("");
        v_anios.setText("");
        v_spiLang.setSelection(0);
    }
}
