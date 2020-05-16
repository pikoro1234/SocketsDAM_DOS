package com.example.sockets;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView txtipUser,txtpuertoConexion,txtmensajeUser,txtBandejaEntrada;

    private Button botonEnviar,botonConectar,botonDesconectar;

    private boolean suitAbrirConexion,suitCerrarConexion;

    private Socket socket;

    String fecha,hora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //construccion de formateo de fecha
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        final SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");

        fecha = sdf.format(new Date());

        hora = hourFormat.format(new Date());

        txtipUser = (TextView) findViewById(R.id.txtIpUsuario);

        txtpuertoConexion = (TextView) findViewById(R.id.txtPuerto);

        txtmensajeUser = (TextView) findViewById(R.id.txtEnviarMensaje);

        txtBandejaEntrada = (TextView) findViewById(R.id.txtBandejaEntrada);

        botonEnviar = (Button) findViewById(R.id.btnEnviar);

        botonConectar = (Button)  findViewById(R.id.btnConectar);

        botonDesconectar = (Button) findViewById(R.id.btnDesconectar);

        botonConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            suitAbrirConexion = true;

                            socket = new Socket(txtipUser.getText().toString(), Integer.parseInt(txtpuertoConexion.getText().toString()));

                            System.out.println("LA CONEXION CORRECTA "+txtipUser.getText().toString()+" "+Integer.parseInt(txtpuertoConexion.getText().toString()));

                        } catch (ConnectException exception) {

                            System.out.println("PRIMER CATCH");

                            suitAbrirConexion = false;

                        } catch (IOException e) {

                            System.out.println("SEGUNDO CATCH");

                        }
                    }
                }).start();


                if (suitAbrirConexion){

                    txtBandejaEntrada.setText(hora+" conexion establecida\n");
                }else{

                    showMessage("NO SE ESTABLECIO CONEXIÓN");
                }
            }
        });

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMessage("MENSAJE ENVIADO");
            }
        });

        botonDesconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            suitCerrarConexion = true;

                            socket.close();

                            System.out.println("LA CONEXION SE CERRO ");

                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                }).start();

                if (suitCerrarConexion){

                    txtBandejaEntrada.setText(hora+" conexión cerrada");
                }
            }
        });
    }

    //metodo atajo para el toast vista usuario
    protected void showMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
