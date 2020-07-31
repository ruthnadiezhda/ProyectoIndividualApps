package com.example.proyectoindividualapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    private EditText correoLogin;
    private EditText contraseniaLogin;
    private String correo;
    private String contrasenia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();

        contraseniaLogin = findViewById(R.id.contraseniaLogin);
        correoLogin = findViewById(R.id.correoLogin);
        Button loginBoton = findViewById(R.id.iniciarSesion);

        loginBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = correoLogin.getText().toString();
                contrasenia = contraseniaLogin.getText().toString();

                if (correo.isEmpty()) {
                    correoLogin.setError("Debe ingresar un correo");
                } else if (contrasenia.isEmpty()) {
                    contraseniaLogin.setError("Debe ingresar una contraseña");
                } else {
                    firebaseAuth.signInWithEmailAndPassword(correo, contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(LoginActivity.this, "Antes debió verifica su correo. Revise su bandeja de entrada", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Ocurrió un error en el envío del correo", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            } else  {
                                Toast.makeText(LoginActivity.this, "Ocurrió un error en la verificación. Comuniquese con DTI", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }



}


