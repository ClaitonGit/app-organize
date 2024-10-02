package activity;

import activity.model.Usuario;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import config.configuracaoFirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class LoginActivity extends AppCompatActivity {
    private EditText campoEmail, campoSenha;
    private Button botaoEntrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoEntrar = findViewById(R.id.buttonEntrar);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                    if (!textoEmail.isEmpty()) {
                        if (!textoSenha.isEmpty()) {

                            usuario = new Usuario();
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            validarLogin();


                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Preencha a senha!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Preencha o email!",
                                Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }


    public void validarLogin(){
        autenticacao = configuracaoFirebase.getFirebaseAutenticacacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                   abrirTelaPrincipal();

                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao = "Usuário não está cadastrado!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Email e senha não encontrados!";
                    } catch (Exception e){
                        excecao = "Erro ao cadastrar usuário" + e.getMessage();
                        e.printStackTrace();
                    }


                    Toast.makeText(LoginActivity.this,
                            "Erro ao fazer Login!",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }

    public static class MainActivity extends IntroActivity {

        private FirebaseAuth autenticacao;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //setContentView(R.layout.activity_main);


            setButtonBackVisible(false);
            setButtonNextVisible(false);

            addSlide(new FragmentSlide.Builder()
                    .background(android.R.color.white)
                    .fragment(R.layout.intro_1)
                    .build()
            );
            addSlide(new FragmentSlide.Builder()
                    .background(android.R.color.white)
                    .fragment(R.layout.intro_2)
                    .build()
            );
            addSlide(new FragmentSlide.Builder()
                    .background(android.R.color.white)
                    .fragment(R.layout.intro_3)
                    .build()
            );
            addSlide(new FragmentSlide.Builder()
                    .background(android.R.color.white)
                    .fragment(R.layout.intro_4)
                    .build()
            );

            addSlide(new FragmentSlide.Builder()
                    .background(android.R.color.white)
                    .fragment(R.layout.into_cadastro)
                    .canGoForward(false)
                    .build()
            );




        }

        @Override
        protected void onStart() {
            super.onStart();
            verificarUserLogado();
        }

        public void btEntrar(View view){
           startActivities(new Intent[]{new Intent(this, LoginActivity.class)});

        }
        public void btCadastrar(View view){
            startActivities(new Intent[]{new Intent(this, CadastroActivity.class)});
        }

        public void verificarUserLogado(){

            autenticacao = configuracaoFirebase.getFirebaseAutenticacacao();
           autenticacao.signOut();
            if(autenticacao.getCurrentUser() != null){
                abrirTelaPrincipal();
            }
        }

        public void abrirTelaPrincipal(){
            startActivity(new Intent(this, PrincipalActivity.class));
        }

    }
}