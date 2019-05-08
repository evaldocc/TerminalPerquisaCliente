package com.evaldo.terminalperquisacliente.telasPerguntas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.evaldo.terminalperquisacliente.R;
import com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.evaldo.terminalperquisacliente.activity.PrincipalActivity.pularTela;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.contextAnterior;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.contextDinamico;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.decidirNumeroDaPerguntaETipoRespostasEChamarTelas;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.decidirTipoDeTela;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.idRespostaQuestionario;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.perguntaAtual;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.verificarLimitePergunta;
import static com.evaldo.terminalperquisacliente.util.DialogTimeoutListener.cronometro;
import static com.evaldo.terminalperquisacliente.util.DialogTimeoutListener.cronometroChamarDialog;

public class PerguntaAbertaActivity extends AppCompatActivity {

    private TextView tvPerguntaAberta;
    private AutoCompleteTextView ACTVRespostaAberta;

    String pergunta = perguntaAtual;
    String respostaAberta;
    DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    DatabaseReference bancoRespostasQuestionarioReferencia = databaseReferencia.child("Banco Respostas Questionário");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pergunta_aberta);

        tvPerguntaAberta = findViewById(R.id.tv_pertunta_Aberta);
        tvPerguntaAberta.setText(pergunta);

        ACTVRespostaAberta = findViewById(R.id.tv_respostaAberta);


        contextDinamico = this;


        cronometroChamarDialog();

    }


    private void chamarProximaTela() {

        cronometro.cancel();

        pularTela++;

        verificarLimitePergunta();

        decidirNumeroDaPerguntaETipoRespostasEChamarTelas();

        contextAnterior = contextDinamico;

        decidirTipoDeTela(contextDinamico);
    }


    public void clickComentarioPular(View view) {

        enviarQuestionario(pularTela + "." + pergunta, "(Não deixou comentario)");
        chamarProximaTela();
    }
    public void clickBotaoEnviarComentario(View view) {
        respostaAberta = "(" + respostaAberta + "" + ACTVRespostaAberta.getText() + ")";
        enviarQuestionario(pularTela + "." + pergunta, respostaAberta);
        chamarProximaTela();
    }

    private void enviarQuestionario(String pergunta, String resposta) {


        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("pergunta" + pularTela).setValue(pergunta);
        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("resposta" + pularTela).setValue(resposta);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "VOLTAR", Toast.LENGTH_SHORT).show();

        pularTela--;

        cronometro.cancel();

        verificarLimitePergunta();

        decidirNumeroDaPerguntaETipoRespostasEChamarTelas();

        decidirTipoDeTela(contextAnterior);

        finish();
    }

}
