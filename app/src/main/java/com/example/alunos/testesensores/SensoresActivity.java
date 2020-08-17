package com.example.alunos.testesensores;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SensoresActivity extends AppCompatActivity {
    TextView txtValor;
    Button btnAcelerometro, btnGiroscopio, btnLuminosidade, btnProximidade, btnMagnenometro;
    Sensor sAcelerometro, sGiroscopio, sLuminosidade, sProximidade, sMagnenometro;
    String vAcelerometro, vGiroscopio, vLuminosidade, vProximidade, vMagnenometro;
    int sacudidasX = 0, sacudidasY = 0;
    LinearLayout layoutPrincipal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores);

        txtValor = findViewById(R.id.txtValor);
        btnAcelerometro = findViewById(R.id.btnAcelerometro);
        btnGiroscopio = findViewById(R.id.btnGiroscopio);
        btnLuminosidade = findViewById(R.id.btnLuminosidade);
        btnProximidade = findViewById(R.id.btnProximidade);
        btnMagnenometro = findViewById(R.id.btnMagnenometro);
        layoutPrincipal = findViewById(R.id.layoutPrincipal);

        btnAcelerometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtValor.setText(vAcelerometro);
            }
        });

        btnGiroscopio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtValor.setText(vGiroscopio);
            }
        });

        btnLuminosidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtValor.setText(vLuminosidade);
            }
        });

        btnProximidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnMagnenometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Definir o tipo de sensor de cada Sensor criado
        sAcelerometro = PrincipalActivity.mSensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sGiroscopio = PrincipalActivity.mSensores.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sLuminosidade = PrincipalActivity.mSensores.getDefaultSensor(Sensor.TYPE_LIGHT);

        PrincipalActivity.mSensores.registerListener(
                new Acelerometro(),
                sAcelerometro,
                SensorManager.SENSOR_DELAY_GAME
        );

        PrincipalActivity.mSensores.registerListener(
                new Giroscopio(),
                sGiroscopio,
                SensorManager.SENSOR_DELAY_GAME
        );

        PrincipalActivity.mSensores.registerListener(
                new Luminosidade(),
                sLuminosidade,
                SensorManager.SENSOR_DELAY_GAME
        );


    }

    class Acelerometro implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];

            if(x<-10||x>10){
                sacudidasX++;
            }
            if(sacudidasX>=10){
                sacudidasX = 0;
                finishAffinity();
            }

            if(y<-0||y>20){
                sacudidasY++;
            }
            if(sacudidasY>=10){
                sacudidasY = 0;
                Intent iAbrir = getPackageManager().
                        getLaunchIntentForPackage("com.spotify.music");
                startActivity(iAbrir);
            }




            vAcelerometro = "ÚLTIMOS VALORES DO ACELERÔMETRO\n";
            vAcelerometro += "Valor de x: " + x + " m/s\n";
            vAcelerometro += "Valor de y: " + y + " m/s\n";
            vAcelerometro += "Valor de z: " + z + " m/s";
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    class Giroscopio implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];

            vGiroscopio = "ÚLTIMOS VALORES DO GIROSCÓPIO\n";
            vGiroscopio += "Valor de x: " + x + " m/s\n";
            vGiroscopio += "Valor de y: " + y + " m/s\n";
            vGiroscopio += "Valor de z: " + z + " m/s";
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    class Luminosidade implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            double lux = event.values[0];
            vLuminosidade = "ÚLTIMO VALOR DO SENSOR DE LUMINOSIDADE\n";
            vLuminosidade += "Luminosidade: " + lux + " lux";

            if(lux < 5){
                layoutPrincipal.setBackgroundColor(Color.YELLOW);
            } else {
                layoutPrincipal.setBackgroundColor(Color.BLACK);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    }

}
