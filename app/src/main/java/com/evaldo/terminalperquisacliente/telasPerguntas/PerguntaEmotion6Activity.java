package com.evaldo.terminalperquisacliente.telasPerguntas;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.evaldo.terminalperquisacliente.R;
import com.evaldo.terminalperquisacliente.util.DialogTimeoutListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.evaldo.terminalperquisacliente.activity.PrincipalActivity.pularTela;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.chamarTelaFinalAgradecimento;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.contextAnterior;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.contextDinamico;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.decidirNumeroDaPerguntaETipoRespostasEChamarTelas;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.decidirTipoDeTela;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.idRespostaQuestionario;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.interagiu;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.perguntaAtual;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.verificarLimitePergunta;
import static com.evaldo.terminalperquisacliente.util.DialogTimeoutListener.apertouContinuar;
import static com.evaldo.terminalperquisacliente.util.DialogTimeoutListener.cronometro;
import static com.evaldo.terminalperquisacliente.util.DialogTimeoutListener.cronometroChamarDialog;
import static com.evaldo.terminalperquisacliente.util.DialogTimeoutListener.dialog;

public class PerguntaEmotion6Activity extends AppCompatActivity {

    private ImageView exelente, bom, razoavel, ruim, pessimo;

    private TextView tvPergunta;

    String pergunta = perguntaAtual;
    DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    DatabaseReference bancoRespostasQuestionarioReferencia = databaseReferencia.child("Banco Respostas Questionário");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pergunta_emotion6);
        System.out.println("Tela 6emotion pularTela = " + pularTela);

        exelente = findViewById(R.id.iv_excelente6);
        bom = findViewById(R.id.iv_bom6);
        razoavel = findViewById(R.id.iv_razoavel6);
        ruim = findViewById(R.id.iv_ruim6);
        pessimo = findViewById(R.id.iv_pessimo6);

        tvPergunta = findViewById(R.id.tv_pergunta_6emotion);
        tvPergunta.setText(pergunta);

        System.out.println("Pergunta 6 emotion");
        //interagiu = false;
        contextDinamico = this;


        cronometroChamarDialog();

    }





    private void chamarProximaTela() {

        //interagiu = true;
        //dialog.dismiss();

        cronometro.cancel();

        pularTela++;

        verificarLimitePergunta();

        decidirNumeroDaPerguntaETipoRespostasEChamarTelas();

        contextAnterior = contextDinamico;

        decidirTipoDeTela(contextDinamico);
    }

    private void enviarQuestionario(String pergunta, String resposta) {


        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("pergunta" + pularTela).setValue(pergunta);
        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("resposta" + pularTela).setValue(resposta);
    }

    public void clickExelente6Emotion(View view) {

        System.out.println("clickouExcelente");

        enviarQuestionario(pularTela + "." + pergunta, "(Exelente)");

        chamarProximaTela();
    }

    public void clickBom6Emotion(View view) {
        enviarQuestionario(pularTela + "." + pergunta, "(Bom)");

        chamarProximaTela();
    }

    public void clickRazoavel6Emotion(View view) {
        enviarQuestionario(pularTela + "." + pergunta, "(Razoável)");

        chamarProximaTela();
    }

    public void clickRuim6Emotion(View view) {
        enviarQuestionario(pularTela + "." + pergunta, "(Ruim)");

        chamarProximaTela();
    }

    public void clickPessimo6Emotion(View view) {
        enviarQuestionario(pularTela + "." + pergunta, "(Péssimo)");

        chamarProximaTela();
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "VOLTAR", Toast.LENGTH_SHORT).show();

        pularTela--;

        cronometro.cancel();

        /*verificarLimitePergunta();

        decidirNumeroDaPerguntaETipoRespostasEChamarTelas();

        decidirTipoDeTela(contextAnterior);*/

        finish();
    }



    /* @Override
     public boolean onCreateOptionsMenu(Menu menu) {

         getMenuInflater().inflate(R.menu.menu_cliente, menu);

         return super.onCreateOptionsMenu(menu);
     }

     //////////////////////////MENU CARREGANDO//////////////////////////////////////////////////////////////////////////////////////

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {

         switch (item.getItemId()) {

             case R.id.item_voltar:

                 Toast.makeText(this, "Voltar", Toast.LENGTH_LONG).show();

                 //pularTela--;

                 finish();

                 return true;

             case R.id.item_avancar:

                 Toast.makeText(this, "Avançar", Toast.LENGTH_LONG).show();

                 enviarQuestionario(pergunta, "(Não respondeu esta pergunta)");

                 chamarProximaTela();

                 return true;

         }
         return super.onOptionsItemSelected(item);
     }*/

}


