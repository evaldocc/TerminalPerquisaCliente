package com.evaldo.terminalperquisacliente.telasPerguntas;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import static com.evaldo.terminalperquisacliente.util.DialogTimeoutListener.cronometro;
import static com.evaldo.terminalperquisacliente.util.DialogTimeoutListener.cronometroChamarDialog;
import static com.evaldo.terminalperquisacliente.util.DialogTimeoutListener.dialog;


public class PerguntaSimOuNaoActivity extends AppCompatActivity {

    private TextView tvPergunta;
    private Button btSim, btNao;

    String pergunta = perguntaAtual;
    DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    DatabaseReference bancoRespostasQuestionarioReferencia = databaseReferencia.child("Banco Respostas Questionário");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pergunta_sim_ou_nao);


        tvPergunta = findViewById(R.id.tv_pergunta_SimOuNao);
        btSim = findViewById(R.id.bt_pergunta_sim);
        btNao = findViewById(R.id.bt_pergunta_nao);

        tvPergunta.setText(pergunta);

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

    public void clickRespostaSim(View view) {
        enviarQuestionario(pularTela + "." + pergunta, "(Sim)");
        chamarProximaTela();

    }

    public void clickRespostaNao(View view) {
        enviarQuestionario(pularTela + "." + pergunta, "(Não)");
        chamarProximaTela();
    }

    private void enviarQuestionario(String pergunta, String resposta) {


        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("pergunta" + pularTela).setValue(pergunta);
        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("resposta" + pularTela).setValue(resposta);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "VOLTAR", Toast.LENGTH_SHORT).show();

        cronometro.cancel();

        pularTela--;

        verificarLimitePergunta();

        decidirNumeroDaPerguntaETipoRespostasEChamarTelas();

        decidirTipoDeTela(contextAnterior);

        finish();


    }

}
