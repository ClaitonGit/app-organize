package activity;

import activity.model.Movimentacao;
import activity.model.Usuario;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import config.configuracaoFirebase;
import helper.Base64Custom;
import helper.DatCustom;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ReceitaActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = configuracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = configuracaoFirebase.getFirebaseAutenticacacao();
    private Double receitaTotal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        campoCategoria = findViewById(R.id.editcategoria);
        campoValor = findViewById(R.id.editValor);
        campoData = findViewById(R.id.editdata);
        campoDescricao = findViewById(R.id.editdescricao);

        campoData.setText(DatCustom.dataAtual());
        recuperarReceita();


    }



    public void salvarReceita(View view){


        if (validarCamposReceita()){

            movimentacao = new Movimentacao();
            String data = campoData.getText().toString();
            Double valorRecuperado = Double.parseDouble(campoValor.getText().toString());

            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("r");

           Double receitaAtualizada = receitaTotal + valorRecuperado;
            atualizarReceita(receitaAtualizada);

            movimentacao.salvar(data);

        }
    }

    public Boolean validarCamposReceita(){

        String textoValor = campoValor.getText().toString();
        String textodata = campoData.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();


        if( !textoValor.isEmpty()){
            if( !textodata.isEmpty()){
                if( !textoCategoria.isEmpty()){
                    if( !textoDescricao.isEmpty()){
                        return true;
                    }else {
                        Toast.makeText(ReceitaActivity.this,
                                "Descrição não preenchido!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                }else {
                    Toast.makeText(ReceitaActivity.this,
                            "Categoria não preenchido!",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

            }else {
                Toast.makeText(ReceitaActivity.this,
                        "Data não preenchido!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

        }else {
            Toast.makeText(ReceitaActivity.this,
                    "valor não preenchido!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

    }



    public void recuperarReceita(){
        String emailUlsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64( emailUlsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child( idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue( Usuario.class);
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void atualizarReceita(Double receita){
        String emailUlsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64( emailUlsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child( idUsuario);


        usuarioRef.child("receitaTotal").setValue(receita);



    }

}