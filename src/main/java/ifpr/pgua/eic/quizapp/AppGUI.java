package ifpr.pgua.eic.quizapp;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AppGUI extends Application{

    private Scene cena;
    private VBox raiz;

    private Text questaoAtual;
    private Text enunciado;
    private Button alternativa01;
    private Button alternativa02;
    private Button alternativa03;
    private Button alternativa04;
    private Text resultado;
    private Button proxima;
    private Text resultadoFinal;
    private Button reiniciar;

    private ControladorQuiz controladorQuiz;

    @Override
    public void init() throws Exception {
        super.init();

        ArrayList<Questao> lista = new ArrayList<>();

        lista.add(new Questao("Qual a capital do Paraná?", "Curitiba", new String[]{"Floripa", "Porto Alegre", "São Paulo"}));
        lista.add(new Questao("Qual a capital de São Paulo?", "São Paulo", new String[]{"Floripa", "Rio de Janeiro", "Campo Grande"}));
        lista.add(new Questao("Qual a capital do Acre?", "Rio Branco", new String[]{"Manaus","Palmas", "João Pessoa"}));
        lista.add(new Questao("Qual a capital de Rondonia?", "Porto Velho", new String[]{"Belém", "Macapá", "Manaus"}));

        controladorQuiz = new ControladorQuiz(lista);


    }

    private void atualizaTela(){
        Questao questao = controladorQuiz.getQuestao();

        String textoAtual = "Pergunta "+(controladorQuiz.indiceQuestaoAtual()+1)+"/"+controladorQuiz.totalQuestoes();

        questaoAtual.setText(textoAtual);
        enunciado.setText(questao.getEnunciado());
        alternativa01.setText(questao.getAlternativas().get(0));
        alternativa02.setText(questao.getAlternativas().get(1));
        alternativa03.setText(questao.getAlternativas().get(2));
        alternativa04.setText(questao.getAlternativas().get(3));

        resultado.setVisible(false);
        proxima.setVisible(false);
        resultadoFinal.setVisible(false);
        reiniciar.setVisible(false);

        alternativa01.setDisable(false);
        alternativa02.setDisable(false);
        alternativa03.setDisable(false);
        alternativa04.setDisable(false);
        
    }


    private void inicializaComponentes(){
        
        questaoAtual = new Text();
        enunciado = new Text("Hello World, agora com GUI!!!");
        alternativa01 = new Button("Alternativa 01");
        alternativa01.setOnAction(this::respondePergunta);
        alternativa02 = new Button("Alternativa 02");
        alternativa02.setOnAction(this::respondePergunta);
        alternativa03 = new Button("Alternativa 03");
        alternativa03.setOnAction(this::respondePergunta);
        alternativa04 = new Button("Alternativa 04");
        alternativa04.setOnAction(this::respondePergunta);
        resultado = new Text("Resultado");
        proxima = new Button("Próxima");
        proxima.setOnAction(this::proximaPergunta);
        
        resultadoFinal = new Text();
        reiniciar = new Button("Reiniciar");
        reiniciar.setOnAction(this::reiniciar);


        raiz  = new VBox();
        raiz.getChildren().add(questaoAtual);
        raiz.getChildren().add(enunciado);
        raiz.getChildren().add(alternativa01);
        raiz.getChildren().add(alternativa02);
        raiz.getChildren().add(alternativa03);
        raiz.getChildren().add(alternativa04);
        raiz.getChildren().add(resultado);
        raiz.getChildren().add(proxima);
        raiz.getChildren().add(resultadoFinal);
        raiz.getChildren().add(reiniciar);

        raiz.setAlignment(Pos.CENTER);
        raiz.setSpacing(10);


    }

    @Override
    public void start(Stage palco) throws Exception {
        
        inicializaComponentes();
        atualizaTela();

        double largura = 600;
        double altura = 400;
        cena = new Scene(raiz,largura,altura);

        palco.setTitle("App de Quiz");
        palco.setScene(cena);
        palco.show();
        
    }


    private void respondePergunta(ActionEvent evento){
        Button clicado = (Button)evento.getSource();
        String resposta = clicado.getText();

        boolean ret = controladorQuiz.respondeQuestao(resposta);
        
        System.out.println("Respondeu:"+resposta);
        if(ret == true){
            resultado.setText("Acertou...");
        }else{
            resultado.setText("Errou...");
        }

        resultado.setVisible(true);

        if(!controladorQuiz.temProximaQuestao()){
            proxima.setText("Resultado");
        }

        proxima.setVisible(true);

        alternativa01.setDisable(true);
        alternativa02.setDisable(true);
        alternativa03.setDisable(true);
        alternativa04.setDisable(true);
        
    }

    private void proximaPergunta(ActionEvent evento){
        if(controladorQuiz.temProximaQuestao()){
            controladorQuiz.proximaQuestao();
            atualizaTela();
        }else{
            questaoAtual.setVisible(false);
            enunciado.setVisible(false);
            alternativa01.setVisible(false);
            alternativa02.setVisible(false);
            alternativa03.setVisible(false);
            alternativa04.setVisible(false);
            resultado.setVisible(false);
            proxima.setVisible(false);

            String textoResultadoFinal = "Acertos:"+controladorQuiz.getAcertos()+" Erros:"+controladorQuiz.getErros();
            resultadoFinal.setText(textoResultadoFinal);

            resultadoFinal.setVisible(true);
            reiniciar.setVisible(true);

        }

    }

    private void reiniciar(ActionEvent evento){
        controladorQuiz.reiniciar();

        questaoAtual.setVisible(true);
        enunciado.setVisible(true);
        alternativa01.setVisible(true);
        alternativa02.setVisible(true);
        alternativa03.setVisible(true);
        alternativa04.setVisible(true);

        atualizaTela();

    }





    public static void main(String[] args) {
        launch(args);
    }




    
}
