package activity.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import config.configuracaoFirebase;
import helper.Base64Custom;
import helper.DatCustom;

public class Movimentacao {

    private String data;
    private String categoria;
    private  String descricao;
    private  String tipo;

    private  Double valor;

    public Movimentacao() {
    }

    public void salvar(String dataEscolhida){

        FirebaseAuth autenticacao = configuracaoFirebase.getFirebaseAutenticacacao();
        String idUsusario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        String mesAno = DatCustom.mesAnoDataEscolhido(dataEscolhida);

        DatabaseReference firebase = configuracaoFirebase.getFirebaseDatabase();
        firebase.child("movimentacao")
                .child(idUsusario)
                .child(mesAno)
                .push()
                .setValue(this);

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
