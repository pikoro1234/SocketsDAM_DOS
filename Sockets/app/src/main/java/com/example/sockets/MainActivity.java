package com.example.sockets;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private TextView txtipUser,txtpuertoConexion,txtmensajeUser;

    private Button botonEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtipUser = (TextView) findViewById(R.id.txtIpUsuario);

        txtpuertoConexion = (TextView) findViewById(R.id.txtPuerto);

        txtmensajeUser = (TextView) findViewById(R.id.txtEnviarMensaje);

        botonEnviar = (Button) findViewById(R.id.btnEnviar);

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            DataOutputStream dataOutputStream;

                            PrintWriter printWriter;

                            Socket socketconexion;

                            socketconexion = new Socket(txtipUser.getText().toString(), Integer.parseInt(txtpuertoConexion.getText().toString()));

                            printWriter = new PrintWriter(socketconexion.getOutputStream());

                            printWriter.write(txtmensajeUser.getText().toString());

                            printWriter.flush();

                            printWriter.close();

                            socketconexion.close();

                            System.out.println("datos "+txtipUser.getText().toString()+" "+Integer.parseInt(txtpuertoConexion.getText().toString())+" "+txtmensajeUser.getText().toString()+" FIN");

                        }catch (UnknownHostException e) {

                            e.printStackTrace();

                        } catch (IOException e) {
                            
                            e.printStackTrace();

                        }
                    }
                }).start();

                System.out.println("chivato");

                System.out.println("datos "+txtipUser.getText().toString()+" "+Integer.parseInt(txtpuertoConexion.getText().toString())+" "+txtmensajeUser.getText().toString()+" FIN");

                showMessage("el boton presionado");
            }
        });
    }

    //metodo atajo para el toast vista usuario
    protected void showMessage(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
