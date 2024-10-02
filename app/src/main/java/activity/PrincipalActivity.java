package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.organizze.databinding.ActivityPrincipalBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

public class PrincipalActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
private ActivityPrincipalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(com.github.clans.fab.R.id.fab_label)
                        .setAction("Action", null).show();
            }
        });
    }

    public void adicionarReceita(View view){
        startActivity(new Intent(this,ReceitaActivity.class));
    }

    public void adicionarDespesa(View view){
        startActivity(new Intent(this,DespesaActivity.class));

    }

}