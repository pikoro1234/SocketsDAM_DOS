package com.example.sockets;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView txtipUser,txtpuertoConexion,txtmensajeUser,txtBandejaEntrada;

    private Button botonEnviar,botonConectar,botonDesconectar;

    private String fecha,hora,recibido;

    private Socket socket;

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

                Conectar conectar = new Conectar();

                conectar.execute();
            }
        });

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        DataInputStream in;

                        DataOutputStream out;

                        try {

                            while (true){

                                in = new DataInputStream(socket.getInputStream());

                                out = new DataOutputStream(socket.getOutputStream());

                                out.writeUTF(txtmensajeUser.getText().toString());

                                recibido = in.readUTF();

                                System.out.println(recibido);

                                Conectar con = new Conectar();

                                con.execute();

                            }

                        } catch (IOException e) {

                            e.printStackTrace();
                        }

                    }
                }).start();

                txtBandejaEntrada.setText(txtmensajeUser.getText().toString());

            }
        });

        botonDesconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Desconectar desconectar = new Desconectar();

                desconectar.execute();

            }
        });
    }



    class Conectar extends AsyncTask<String,Void,Void>{

        boolean interruptor;

        @Override
        protected void onPreExecute() {

        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(String... strings) {

            try {

                socket = new Socket(txtipUser.getText().toString(),Integer.parseInt(txtpuertoConexion.getText().toString()));

                interruptor = true;
            } catch (ConnectException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (interruptor){
                showMessage("CONECTAMOS");
                txtBandejaEntrada.setText(fecha+" conectamos");
            }else{
                showMessage("NO CONECTAMOS");
            }

        }
    }

    class Desconectar extends AsyncTask<String,Void,Void>{

        @Override
        protected void onPreExecute() {

            showMessage("DESCONECTADO");

            txtBandejaEntrada.setText("desconectado");
        }

        @Override
        protected Void doInBackground(String... strings) {

            try {

                socket.close();

            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    //metodo atajo para el toast vista usuario
    public void showMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
