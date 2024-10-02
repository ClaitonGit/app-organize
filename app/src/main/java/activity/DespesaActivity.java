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

public class DespesaActivity extends AppCompatActivity {

   private TextInputEditText campoData, campoCategoria, campoDescricao;
   private EditText campoValor;
   private Movimentacao movimentacao;
   private DatabaseReference firebaseRef = configuracaoFirebase.getFirebaseDatabase();
   private FirebaseAuth autenticacao = configuracaoFirebase.getFirebaseAutenticacacao();
   private Double despesaTotal;

   private Double despesaAtualizada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        campoCategoria = findViewById(R.id.editcategoria);
        campoValor = findViewById(R.id.editValor);
        campoData = findViewById(R.id.editdata);
        campoDescricao = findViewById(R.id.editdescricaco);

        campoData.setText(DatCustom.dataAtual());
        recuperarDespesa();
    }

    public void salvarDespesa(View view){


        if (validarCamposDespesa()){

            movimentacao = new Movimentacao();
            String data = campoData.getText().toString();
            Double valorRecuperado = Double.parseDouble(campoValor.getText().toString());

            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("d");

            despesaAtualizada = despesaTotal + valorRecuperado;
            atulizarDespesa(despesaAtualizada);

            movimentacao.salvar(data);

        }
    }

    public Boolean validarCamposDespesa(){

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
                        Toast.makeText(DespesaActivity.this,
                                "Descrição não preenchido!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                }else {
                    Toast.makeText(DespesaActivity.this,
                            "Categoria não preenchido!",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

            }else {
                Toast.makeText(DespesaActivity.this,
                        "Data não preenchido!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

        }else {
            Toast.makeText(DespesaActivity.this,
                    "valor não preenchido!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void recuperarDespesa(){
        String emailUlsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64( emailUlsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child( idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue( Usuario.class);
                despesaTotal = usuario.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void atulizarDespesa(Double despesa){
        String emailUlsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64( emailUlsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child( idUsuario);


        usuarioRef.child("despesaTotal").setValue(despesa);



    }

}