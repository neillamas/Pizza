package pe.com.hlamastepsa.pizza;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //calcula la orden de pedido
    public void GetTotal(View vista){
        double dblTotal;
        double dblTipo=0;
        double dblComplemento=0;

        //valida
        RadioGroup RadioGrupo =findViewById(R.id.RadioGrupo);
        if (RadioGrupo.getCheckedRadioButtonId()==-1){
            Toast.makeText(this,"El campo Tipo de Masa es requerido",Toast.LENGTH_SHORT).show();
            return;
        }

        EditText editDireccion=findViewById(R.id.editDireccion);
        if (editDireccion.length()==0){
            Toast.makeText(this,"El campo Dirección de Envío es requerido",Toast.LENGTH_SHORT).show();
            return;
        }

        //obtiene monto complemento
        CheckBox check;
        check=findViewById(R.id.check1);
        dblComplemento=0;
        if (check.isChecked()) {
            dblComplemento=4;
        }
        check=findViewById(R.id.check2);
        if (check.isChecked()){
            dblComplemento+=8;
        }

        //obtiene monto tipo pizza
        Spinner spinTipo=(Spinner)findViewById(R.id.spinTipo);
        int intPosicion= spinTipo.getSelectedItemPosition();
        switch (intPosicion){
            case 0:
                dblTipo=38;
                break;
            case 1:
                dblTipo=42;
                break;
            case 2:
                dblTipo=36;
                break;
            case 3:
                dblTipo=56;
                break;
        }

        //obtiene total
        dblTotal=dblTipo+dblComplemento;

        //si dia de semana es martes al total se le descuenta el 30%
        Calendar c =Calendar.getInstance();
        int intDiaSemana=c.get(Calendar.DAY_OF_WEEK);
        if (intDiaSemana==Calendar.SUNDAY){
            Double dblDescuento;
            dblDescuento=dblTotal*0.30;
            dblTotal=dblTotal-dblDescuento;
        }

        //envia notificacion
        RadioButton radio= findViewById(RadioGrupo.getCheckedRadioButtonId());

        String strMensaje;
        strMensaje="Su pedido de "+ spinTipo.getSelectedItem().toString() + " con " + radio.getText() + " a S/.";
        strMensaje=strMensaje+Double.toString(dblTotal)+" + IGV está en proceso de envío";
        Toast.makeText(this,strMensaje,Toast.LENGTH_SHORT).show();

        strMensaje="Tu pedido está en camino";
        final Toast toast=Toast.makeText(this, strMensaje, Toast.LENGTH_LONG);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                toast.show();
            }
        }, 10000);
    }
}
