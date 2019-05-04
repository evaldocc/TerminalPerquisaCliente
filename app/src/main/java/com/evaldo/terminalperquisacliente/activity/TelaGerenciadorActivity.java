package com.evaldo.terminalperquisacliente.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.evaldo.terminalperquisacliente.R;
import com.evaldo.terminalperquisacliente.classes.PerguntasQuestionario;
import com.evaldo.terminalperquisacliente.telasPerguntas.PerguntaAbertaActivity;
import com.evaldo.terminalperquisacliente.telasPerguntas.PerguntaEmotion3DivulgacaoActivity;
import com.evaldo.terminalperquisacliente.telasPerguntas.PerguntaEmotion6Activity;
import com.evaldo.terminalperquisacliente.telasPerguntas.PerguntaSimOuNaoActivity;
import com.evaldo.terminalperquisacliente.telasPerguntas.PerguntaTelefoneOuEmailActivity;
import com.evaldo.terminalperquisacliente.telasPerguntas.TelaFinalAgradecimentoActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.evaldo.terminalperquisacliente.activity.PrincipalActivity.chamarPrimeiraTelaUmVez;
import static com.evaldo.terminalperquisacliente.activity.PrincipalActivity.pularTela;


public class TelaGerenciadorActivity extends AppCompatActivity {
    //Iniciando o firebase
    public static PerguntasQuestionario perguntasQuestionario;
    private DatabaseReference reference;

    //Variaveis uteis para todas as classes das telas
    public static String perguntaAtual = "Pergunta atual não recebeu dado", tipoRespostaAtual = "Tipo resposta atual não recebeu dado",
            tipoRespostaProximaTela = "Tipo resposta proxima tela não recebeu dado";
    public static boolean chamarProximaPergunta;
    public static Context contextDinamico, contextAnterior;
    public static boolean interagiu = false;

    //Variaveis contendo o conteudo de perguntas e respostas (como se fosse carregando o objeto PerguntasQuestionario)
    public static int contPerguntas;
    public static String idDispositivo, idRespostaQuestionario, administradorResponsavel,
            pergunta1, pergunta2, pergunta3, pergunta4, pergunta5, pergunta6, pergunta7, pergunta8, pergunta9, pergunta10,
            tipoResposta1, tipoResposta2, tipoResposta3, tipoResposta4, tipoResposta5, tipoResposta6, tipoResposta7, tipoResposta8, tipoResposta9, tipoResposta10;


    //Quem deve setar o questionario atual é o administrador AINDA FALTA FAZER!
    public static String questionarioAtual = "teste";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_gerenciador);

        idDispositivo = pegarIDDispositivo();

        chamarPrimeiraTelaUmVez = true;


        //System.out.println("TelaGerenciadorActivicty");

        //Observação importante sempre será a primeira tela a ser chamada pois possui atributos iniciais que só roda nela

        //System.out.println("Tela gerenciado Activity pularTela = " + pularTela);

        firebaseBuscarPerguntasEChamarPrimeiraTela();
        //System.out.println("perguntasQuestionario = " + perguntasQuestionario);
        salvarDadosPerguntaQuestionarioEmVariaveisStaticas();

        verificarLimitePergunta();

        //System.out.println("antes de = decidirNumeroDaPerguntaETipoRespostasEChamarTelas");
        decidirNumeroDaPerguntaETipoRespostasEChamarTelas();

        // System.out.println("antes de = decidirTipoDeTela");
        contextDinamico = TelaGerenciadorActivity.this;
        decidirTipoDeTela(contextDinamico);

        //decidirTipoDeTela();
        pausarTelaGerenciador();

        System.out.println("___________________________Principal perguntasQuestionario = " + perguntasQuestionario);

        //exibirDadosRecebidosDoFibaseArmazenadosNoSharePreferences();

    }


    public static void chamarPrimeiraTela(Context context) {
        ///Essa sempre será a primeira tela pois criar atributos como key hora para o questionário
        //System.out.println("chamarPrimeiraTela");
        if (chamarPrimeiraTelaUmVez) {
            perguntaAtual = pergunta1;
            System.out.println("Chamando a primeira tela obrigatoria tela 3 emotion");

            chamarPrimeiraTelaUmVez = false;

            Intent intent1 = new Intent(context, PerguntaEmotion3DivulgacaoActivity.class);

            context.startActivity(intent1);
            //finish();

        } else {
            System.out.println("Primeira tela já foi chamada");
        }

    }

    public static boolean verificarLimitePergunta() {
        System.out.println("---------Verificar Limite Perguntas--------------");
        System.out.println("pularTela =" + pularTela);
        if (pularTela <= contPerguntas) {

            chamarProximaPergunta = true;

            System.out.println("verificarLimitePergunta() = " + chamarProximaPergunta);


        } else if (pularTela > contPerguntas) {

            chamarProximaPergunta = false;
            tipoRespostaAtual = "TelaFinal";
            System.out.println("verificarLimitePergunta() = " + chamarProximaPergunta);

            chamarTelaFinal();


        }
        return chamarProximaPergunta;
    }

    public static void decidirNumeroDaPerguntaETipoRespostasEChamarTelas() {


        System.out.println("decidirNumeroDaPerguntaETipoRespostasEChamarTelas()");
        if (chamarProximaPergunta) {
            System.out.println("if chamarProximaPergunta");
            System.out.println("pularTela = " + pularTela);
            switch (pularTela) {

                case 2:

                    System.out.println("case 2");

                    perguntaAtual = pergunta2;

                    tipoRespostaAtual = tipoResposta2;


                    break;

                case 3:
                    System.out.println("case 3");

                    perguntaAtual = pergunta3;

                    tipoRespostaAtual = tipoResposta3;

                    break;

                case 4:
                    System.out.println("case 4");
                    perguntaAtual = pergunta4;
                    tipoRespostaAtual = tipoResposta4;

                    break;

                case 5:
                    System.out.println("case 5");
                    perguntaAtual = pergunta5;
                    tipoRespostaAtual = tipoResposta5;

                    break;

                case 6:
                    System.out.println("case 6");
                    perguntaAtual = pergunta6;
                    tipoRespostaAtual = tipoResposta6;

                    break;

                case 7:
                    System.out.println("case 7");
                    perguntaAtual = pergunta7;
                    tipoRespostaAtual = tipoResposta7;

                    break;

                case 8:
                    System.out.println("case 8");
                    perguntaAtual = pergunta8;
                    tipoRespostaAtual = tipoResposta8;

                    break;

                case 9:
                    System.out.println("case 9");
                    perguntaAtual = pergunta9;
                    tipoRespostaAtual = tipoResposta9;

                    break;

                case 10:
                    System.out.println("case 10");
                    perguntaAtual = pergunta10;
                    tipoRespostaAtual = tipoResposta10;

                    break;
                default:
            }
        }
    }


    public static void chamarTelaFinalAgradecimento(Context context) {
        System.out.println("chamarTelaFinalAgradecimento()");
        Intent intent2 = new Intent(context, TelaFinalAgradecimentoActivity.class);
        context.startActivity(intent2);
    }

    private void firebaseBuscarPerguntasEChamarPrimeiraTela() {
        reference = FirebaseDatabase.getInstance().getReference().child("Banco Perguntas Questionario").child("id");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chamarPrimeiraTela(TelaGerenciadorActivity.this);

                // System.out.println("TelaGerenciador Atualizou ouvindo Questionario Perguntas!");

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("nomeQuestionario").getValue().equals(questionarioAtual)) {
                        // System.out.println("Questionario Localizado");
                        //System.out.println(data);
                        perguntasQuestionario = data.getValue(PerguntasQuestionario.class);
                        if (perguntasQuestionario != null) {
                            //System.out.println("Questionario atual é = " + perguntasQuestionario.getNomeQuestionario());
                            salvarDadosPerguntasDoQuestionarioNoSharePreferences(

                                    perguntasQuestionario.getResposta1(), perguntasQuestionario.getResposta2(), perguntasQuestionario.getResposta3(), perguntasQuestionario.getResposta4(), perguntasQuestionario.getResposta5(), perguntasQuestionario.getResposta6(), perguntasQuestionario.getResposta7(), perguntasQuestionario.getResposta8(), perguntasQuestionario.getResposta9(), perguntasQuestionario.getResposta10(),
                                    perguntasQuestionario.getContPerguntas(),
                                    perguntasQuestionario.getPergunta1(), perguntasQuestionario.getPergunta2(), perguntasQuestionario.getPergunta3(), perguntasQuestionario.getPergunta4(), perguntasQuestionario.getPergunta5(), perguntasQuestionario.getPergunta6(), perguntasQuestionario.getPergunta7(), perguntasQuestionario.getPergunta8(), perguntasQuestionario.getPergunta9(), perguntasQuestionario.getPergunta10(),
                                    perguntasQuestionario.getAdministradorResponsavel());
                        }

                    } else {
                        //System.out.println("Questionario não localizado!");
                    }
                }
                if (perguntasQuestionario == null) {
                    chamarPrimeiraTela(TelaGerenciadorActivity.this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TelaGerenciadorActivity.this, "Erro no firebase", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void decidirTipoDeTela(Context context) {
        System.out.println("decidirTipoDeTela");

        Intent intent3emotion = new Intent(context, PerguntaEmotion3DivulgacaoActivity.class);
        Intent intent6emotion = new Intent(context, PerguntaEmotion6Activity.class);
        Intent intentSimOuNao = new Intent(context, PerguntaSimOuNaoActivity.class);
        Intent intentAberta = new Intent(context, PerguntaAbertaActivity.class);
        Intent intentTelefoneOuEmail = new Intent(context, PerguntaTelefoneOuEmailActivity.class);
        Intent intentFinalAgradecimento = new Intent(context, TelaFinalAgradecimentoActivity.class);


        if (tipoRespostaAtual.equals("Princial 3 Emotions e Ouvidoria")) {

            System.out.println("if 3emotion" + tipoRespostaAtual);

            context.startActivity(intent3emotion);

        } else if (tipoRespostaAtual.equals("6 Emotions")) {

            System.out.println("if 6emotion" + tipoRespostaAtual);

            context.startActivity(intent6emotion);

        } else if (tipoRespostaAtual.equals("(Sim) ou (Não)")) {

            System.out.println("if SimOuNao" + tipoRespostaAtual);

            context.startActivity(intentSimOuNao);

        } else if (tipoRespostaAtual.equals("Resposta Aberta")) {

            System.out.println("if Aberta" + tipoRespostaAtual);

            context.startActivity(intentAberta);

        } else if (tipoRespostaAtual.equals("Telefone e Email")) {

            System.out.println("if TelefoneOuEmail" + tipoRespostaAtual);

            context.startActivity(intentTelefoneOuEmail);

        } else if (tipoRespostaAtual.equals("TelaFinal")) {
            context.startActivity(intentFinalAgradecimento);
        }


    }

    public void salvarDadosPerguntasDoQuestionarioNoSharePreferences(String tipoResposta1, String tipoResposta2, String tipoResposta3, String tipoResposta4, String tipoResposta5, String tipoResposta6, String tipoResposta7, String tipoResposta8, String tipoResposta9, String tipoResposta10,
                                                                     int contPerguntas,
                                                                     String perg1, String perg2, String perg3, String perg4, String perg5, String perg6, String perg7, String perg8, String perg9, String perg10,
                                                                     String adminResponsavel) {
        SharedPreferences myPreferences = getSharedPreferences("Arquivo", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = myPreferences.edit();
        mEditor.putInt("ContadorPerguntas", contPerguntas);
        mEditor.putString("AdministradorResponsavel", adminResponsavel);
        mEditor.putString("Pergunta1", perg1);
        mEditor.putString("Pergunta2", perg2);
        mEditor.putString("Pergunta3", perg3);
        mEditor.putString("Pergunta4", perg4);
        mEditor.putString("Pergunta5", perg5);
        mEditor.putString("Pergunta6", perg6);
        mEditor.putString("Pergunta7", perg7);
        mEditor.putString("Pergunta8", perg8);
        mEditor.putString("Pergunta9", perg9);
        mEditor.putString("Pergunta10", perg10);
        mEditor.putString("TipoResposta1", tipoResposta1);
        mEditor.putString("TipoResposta2", tipoResposta2);
        mEditor.putString("TipoResposta3", tipoResposta3);
        mEditor.putString("TipoResposta4", tipoResposta4);
        mEditor.putString("TipoResposta5", tipoResposta5);
        mEditor.putString("TipoResposta6", tipoResposta6);
        mEditor.putString("TipoResposta7", tipoResposta7);
        mEditor.putString("TipoResposta8", tipoResposta8);
        mEditor.putString("TipoResposta9", tipoResposta9);
        mEditor.putString("TipoResposta10", tipoResposta10);
        mEditor.commit();

    }


    private void salvarDadosPerguntaQuestionarioEmVariaveisStaticas() {

        SharedPreferences myPreferences = getSharedPreferences("Arquivo", MODE_PRIVATE);

        contPerguntas = myPreferences.getInt("ContadorPerguntas", -1);

        administradorResponsavel = myPreferences.getString("AdministradorResponsavel", "Administrador Responsavel Respostas questionario não carregador, verifique receberAdministradorResponsavelPerguntasDoQuestionario() ");

        idRespostaQuestionario =

                tipoResposta1 = myPreferences.getString("TipoResposta1", "tipo de resposta 1 não Carregada verificar receberPergunta1Questionario()");
        tipoResposta2 = myPreferences.getString("TipoResposta2", "tipo de resposta 2 não Carregada verificar receberPergunta1Questionario()");
        tipoResposta3 = myPreferences.getString("TipoResposta3", "tipo de resposta 3 não Carregada verificar receberPergunta1Questionario()");
        tipoResposta4 = myPreferences.getString("TipoResposta4", "tipo de resposta 4 não Carregada verificar receberPergunta1Questionario()");
        tipoResposta5 = myPreferences.getString("TipoResposta5", "tipo de resposta 5 não Carregada verificar receberPergunta1Questionario()");
        tipoResposta6 = myPreferences.getString("TipoResposta6", "tipo de resposta 6 não Carregada verificar receberPergunta1Questionario()");
        tipoResposta7 = myPreferences.getString("TipoResposta7", "tipo de resposta 7 não Carregada verificar receberPergunta1Questionario()");
        tipoResposta8 = myPreferences.getString("TipoResposta8", "tipo de resposta 8 não Carregada verificar receberPergunta1Questionario()");
        tipoResposta9 = myPreferences.getString("TipoResposta9", "tipo de resposta 9 não Carregada verificar receberPergunta1Questionario()");
        tipoResposta10 = myPreferences.getString("TipoResposta10", "tipo de resposta 10 não Carregada verificar receberPergunta1Questionario()");

        pergunta1 = myPreferences.getString("Pergunta1", "tipo de pergunta 1 não Carregada verificar receberTipoResposta1PerguntasDoQuestionario()");
        pergunta2 = myPreferences.getString("Pergunta2", "tipo de pergunta 2 não Carregada verificar receberTipoResposta1PerguntasDoQuestionario()");
        pergunta3 = myPreferences.getString("Pergunta3", "tipo de pergunta 3 não Carregada verificar receberTipoResposta1PerguntasDoQuestionario()");
        pergunta4 = myPreferences.getString("Pergunta4", "tipo de pergunta 4 não Carregada verificar receberTipoResposta1PerguntasDoQuestionario()");
        pergunta5 = myPreferences.getString("Pergunta5", "tipo de pergunta 5 não Carregada verificar receberTipoResposta1PerguntasDoQuestionario()");
        pergunta6 = myPreferences.getString("Pergunta6", "tipo de pergunta 6 não Carregada verificar receberTipoResposta1PerguntasDoQuestionario()");
        pergunta7 = myPreferences.getString("Pergunta7", "tipo de pergunta 7 não Carregada verificar receberTipoResposta1PerguntasDoQuestionario()");
        pergunta8 = myPreferences.getString("Pergunta8", "tipo de pergunta 8 não Carregada verificar receberTipoResposta1PerguntasDoQuestionario()");
        pergunta9 = myPreferences.getString("Pergunta9", "tipo de pergunta 9 não Carregada verificar receberTipoResposta1PerguntasDoQuestionario()");
        pergunta10 = myPreferences.getString("Pergunta10", "tipo de pergunta 10 não Carregada verificar receberTipoResposta1PerguntasDoQuestionario()");

    }

    private void exibirDadosRecebidosDoFibaseArmazenadosNoSharePreferences() {
        System.out.println("------------------Exibindo dados do Share Preferences----------------------");
        System.out.println("receberContPerguntasPerguntasDoQuestionario() = " + contPerguntas);
        System.out.println("receberAdministradorResponsavelPerguntasDoQuestionario() = " + administradorResponsavel);
        System.out.println("receberTipoResposta1PerguntasDoQuestionario() = " + tipoResposta1);
        System.out.println("receberTipoResposta2PerguntasDoQuestionario() = " + tipoResposta2);
        System.out.println("receberTipoResposta3PerguntasDoQuestionario() = " + tipoResposta3);
        System.out.println("receberTipoResposta4PerguntasDoQuestionario() = " + tipoResposta4);
        System.out.println("receberTipoResposta5PerguntasDoQuestionario() = " + tipoResposta5);
        System.out.println("receberTipoResposta6PerguntasDoQuestionario() = " + tipoResposta6);
        System.out.println("receberTipoResposta7PerguntasDoQuestionario() = " + tipoResposta7);
        System.out.println("receberTipoResposta8PerguntasDoQuestionario() = " + tipoResposta8);
        System.out.println("receberTipoResposta9PerguntasDoQuestionario() = " + tipoResposta9);
        System.out.println("receberTipoResposta10PerguntasDoQuestionario() = " + tipoResposta10);
        System.out.println("receberPergunta1Questionario()" + pergunta1);
        System.out.println("receberPergunta2Questionario()" + pergunta2);
        System.out.println("receberPergunta3Questionario()" + pergunta3);
        System.out.println("receberPergunta4Questionario()" + pergunta4);
        System.out.println("receberPergunta5Questionario()" + pergunta5);
        System.out.println("receberPergunta6Questionario()" + pergunta6);
        System.out.println("receberPergunta7Questionario()" + pergunta7);
        System.out.println("receberPergunta8Questionario()" + pergunta8);
        System.out.println("receberPergunta9Questionario()" + pergunta9);
        System.out.println("receberPergunta10Questionario()" + pergunta10);
    }

    private void chamarPrincipal() {
        Intent intent = new Intent(this, TelaGerenciadorActivity.class);
        finish();
        startActivity(intent);
    }

    private String pegarIDDispositivo() {
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public static void chamarProximaTela(Context context) {


    }

    private void pausarTelaGerenciador() {
        System.out.println("Pausar TelaGerenciadorActivity");
        onStop();
    }

    public static void chamarTelaFinal() {
        Intent intent = new Intent(contextDinamico, TelaFinalAgradecimentoActivity.class);
        contextDinamico.startActivity(intent);

    }

}
