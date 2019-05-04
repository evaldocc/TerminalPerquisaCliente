package com.evaldo.terminalperquisacliente.telasPerguntas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class PerguntaTelefoneOuEmailActivity extends AppCompatActivity {

    private TextView tvPerguntaTelefoneOuEmail;
    private Button enviarTelefoneOuEmail, naoEnviarEPular;
    private EditText etTelefone, etEmail;

    String pergunta = perguntaAtual;
    String telefone, email, telefoneEmail;
    DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    DatabaseReference bancoRespostasQuestionarioReferencia = databaseReferencia.child("Banco Respostas Questionário");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pergunta_telefone_ou_email);

        tvPerguntaTelefoneOuEmail = findViewById(R.id.tv_pertunta_TelefoneOuEmail);
        etTelefone = findViewById(R.id.et_telefone);
        etEmail = findViewById(R.id.et_email);
        enviarTelefoneOuEmail = findViewById(R.id.bt_enviar_telefoneOuEmail);
        naoEnviarEPular = findViewById(R.id.bt_enviar_telefoneOuEmail);

        tvPerguntaTelefoneOuEmail.setText(perguntaAtual) ;

        System.out.println("Tela TelefoneOuEmail pularTela = " + pularTela);

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


    public void clickEnviarTelefoneOuEmail(View view) {

        verificarContato();
        enviarQuestionario(pergunta, "(" + telefoneEmail + ")");
        chamarProximaTela();
    }

    public void clickNaoEnviarTelefoneOuEmail(View view) {
        enviarQuestionario(pergunta, "(Não se identificou)");
        chamarProximaTela();
    }

    private void verificarContato() {
        telefone = String.valueOf(etTelefone.getText());
        email = String.valueOf(etEmail.getText());
        telefoneEmail ="Telefone: " + telefone + " email: " + email;

        System.out.println(telefoneEmail);
        if (telefone.equals("")) {
            telefoneEmail = "Não se identificou";
        }

    }

    private void enviarQuestionario(String pergunta, String resposta) {


        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("pergunta"+pularTela).setValue(pergunta);
        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("resposta"+pularTela).setValue(resposta);
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
