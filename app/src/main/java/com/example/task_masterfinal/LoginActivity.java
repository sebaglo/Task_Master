package com.example.task_masterfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button login;
    TextView registerText;

    // Credenciales simples para validar el inicio de sesión
    String validUsername = "admin";
    String validPassword = "1234";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);  // Referenciamos el layout de la actividad de login

        username = (EditText) findViewById(R.id.etUsuario);
        password = (EditText) findViewById(R.id.etContraseña);
        login = (Button) findViewById(R.id.btnLogin);
        registerText = findViewById(R.id.txtRegister);

        // Lógica para el botón de inicio de sesión
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                // Validar credenciales
                if (user.equals(validUsername) && pass.equals(validPassword)) {

                    // Iniciar la actividad principal si las credenciales son correctas
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Finalizamos la actividad de login para que no vuelva al hacer back
                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }

                //Listener para ir a la pantalla de registro
                registerText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
