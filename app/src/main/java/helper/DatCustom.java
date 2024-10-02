package helper;

import android.media.MediaRouter;

import java.text.SimpleDateFormat;

public class DatCustom {

    public static String dataAtual(){
      long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd/MM/yyyy");
       String dataStrig = simpleDateFormat.format(date);
       return dataStrig;
    }

    public static String mesAnoDataEscolhido(String data){

        String retornoData[] = data.split("/");
        String dia = retornoData[0];
        String mes = retornoData[1];
        String ano = retornoData[2];

        String mesAno = mes + ano;
        return mesAno;
     }
}
