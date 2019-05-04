package com.evaldo.terminalperquisacliente.telasPerguntas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.evaldo.terminalperquisacliente.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.evaldo.terminalperquisacliente.activity.PrincipalActivity.pularTela;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.administradorResponsavel;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.contPerguntas;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.contextDinamico;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.decidirNumeroDaPerguntaETipoRespostasEChamarTelas;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.decidirTipoDeTela;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.idDispositivo;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.idRespostaQuestionario;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.perguntaAtual;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.perguntasQuestionario;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.questionarioAtual;
import static com.evaldo.terminalperquisacliente.activity.TelaGerenciadorActivity.verificarLimitePergunta;

public class PerguntaEmotion3DivulgacaoActivity extends AppCompatActivity {


    private ImageView exelente,razoavel,ruim;

    private TextView tvPergunta;

    private String pergunta = perguntaAtual;

    private DatabaseReference reference;

    DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    DatabaseReference bancoRespostasQuestionarioReferencia = databaseReferencia.child("Banco Respostas Questionário");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perguntas_emotion3_divulgacao);

        exelente = (ImageView) findViewById(R.id.iv_excelente);
        razoavel = (ImageView) findViewById(R.id.iv_razoavel);
        ruim = (ImageView) findViewById(R.id.iv_ruim);
        tvPergunta = findViewById(R.id.tv_pergunta3emotion);
        tvPergunta.setText(pergunta);



        System.out.println("Tela 3motion divulgação pularTela  = " + pularTela);

        contextDinamico = this;

        ouvindoMudançaQuestionario();

        System.out.println("perguntasQuestionario = " + perguntasQuestionario);



    }

    private void ouvindoMudançaQuestionario() {
        System.out.println("ouvindoMudançaQuestionario()");

        reference = FirebaseDatabase.getInstance().getReference().child("Banco Perguntas Questionario").child("id");

        reference.addValueEventListener(new ValueEventListener() {
            int ouveMudanca = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //só atualiza quando chamar na segunda vez pq ele sempre chama a primeira

                ouveMudanca++;

                if (ouveMudanca > 1){
                    System.out.println("Atualizou banco de perguntas");
                    //System.out.println("ouveMudanca = " + ouveMudanca);

                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                            //System.out.println("questionarioAtual = " + questionarioAtual + " novoQuestionario = " + data.child("nomeQuestionario").getValue());

                            chamarReiniciarDados();

                    }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void chamarProximaTela() {
        pularTela =2;

        contextDinamico = PerguntaEmotion3DivulgacaoActivity.this ;

        verificarLimitePergunta();

        decidirNumeroDaPerguntaETipoRespostasEChamarTelas();

        onPause();

        decidirTipoDeTela(contextDinamico);
    }


    public void clickExelente3Emotion(View view) {
        gerarIDRespostasQuestionario();

        enviarPrimeiraRespostaQuestionario(pularTela + "." + pergunta, "(Exelente)");

        chamarProximaTela();


    }
    public void clickRazoavel3Emotion(View view) {
        onPause();
        gerarIDRespostasQuestionario();

        enviarPrimeiraRespostaQuestionario(pergunta, "(Razoável)");

        chamarProximaTela();
    }
    public void clickRuim3Emotion(View view) {

        gerarIDRespostasQuestionario();

        enviarPrimeiraRespostaQuestionario(pergunta, "(Ruim)");

        chamarProximaTela();
    }

    private void enviarPrimeiraRespostaQuestionario(String pergunta, String resposta) {

        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("hora").setValue(pegandoHora());
        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("administradorResponsavel").setValue(administradorResponsavel);
        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("qtdPerguntas").setValue(contPerguntas);
        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("idDispositivo").setValue(idDispositivo);
        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("nomeQuestionario").setValue(questionarioAtual);
        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("pergunta"+pularTela).setValue(pergunta);
        bancoRespostasQuestionarioReferencia.child("id").child(idRespostaQuestionario).child("resposta"+pularTela).setValue(resposta);
        //pularTela+=1;
    }

    //Reinicia os dados quando o banco de pergutnas é atualizado
    private void chamarReiniciarDados(){
        System.out.println("3 emotion chamou reiniciar dados");
        Intent intent = new Intent(this, TelaFinalAgradecimentoActivity.class);
        finish();
        startActivity(intent);
    }

    private void gerarIDRespostasQuestionario() {
        idRespostaQuestionario = UUID.randomUUID().toString();
    }

    private String pegandoHora() {
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
        Date dataCal = new Date();
        Date dataHora = new Date();
        String dataFormatada = formataData.format(dataCal);
        String horaFormatada = formatHora.format(dataHora);
        String dataEHora = "Data " + dataFormatada + " Hora " + horaFormatada ;
        //System.out.println("Data " + dataFormatada + " Hora " + horaFormatada );

        return dataEHora;
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cliente_configuracao, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //////////////////////////MENU CARREGANDO//////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

           case R.id.item_voltar:

               Toast.makeText(this, "VOCÊ JÁ ESTA NA PRIMEIRA PERGUNTA!", Toast.LENGTH_LONG).show();

               return true;

            case R.id.item_avancar:
                gerarIDRespostasQuestionario();

                Toast.makeText(this, "Avançar", Toast.LENGTH_LONG).show();

                enviarPrimeiraRespostaQuestionario(pergunta,"(Não respondeu esta pergunta)");

                onPause();

                chamarProximaTela();

                return true;

            case R.id.item_configuracoes:

                Toast.makeText(this, "Confirgurações", Toast.LENGTH_LONG).show();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Você não pode sair dessa tela!", Toast.LENGTH_LONG).show();
        System.out.println("Não pode volta!");
    }


}
