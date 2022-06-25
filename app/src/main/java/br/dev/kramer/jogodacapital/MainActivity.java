package br.dev.kramer.jogodacapital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static final int MAX_QUESTIONS = 5;

    int points, answeredQuestions;
    ConstraintLayout resultLayout;
    TextInputLayout capitalInputLayout;
    TextView greetTextView, stateTextView, infoTextView, resultTextView, correctAnswerTextView;
    int defaultColor, wrongColor, correctColor;
    Button confirmButton, nextQuestionButton;
    EditText capitalTextInput;

    static private HashMap<String, String> stateMap = new HashMap<String, String>();
    private int stateMapIndex;
    private Map.Entry<String, String> stateMapEntry;

    {
        stateMap.put("Acre","Rio Branco");
        stateMap.put("Alagoas","Maceió");
        stateMap.put("Amapá","Macapá");
        stateMap.put("Amazonas","Manaus");
        stateMap.put("Bahia","Salvador");
        stateMap.put("Ceará","Fortaleza");
        stateMap.put("Distrito Federal","Brasília");
        stateMap.put("Espírito Santo","Vitória");
        stateMap.put("Goiás","Goiânia");
        stateMap.put("Maranhão","São Luís");
        stateMap.put("Mato Grosso","Cuiabá");
        stateMap.put("Mato Grosso do Sul","Campo Grande");
        stateMap.put("Minas Gerais","Belo Horizonte");
        stateMap.put("Pará","Belém");
        stateMap.put("Paraíba","João Pessoa");
        stateMap.put("Paraná","Curitiba");
        stateMap.put("Pernambuco","Recife");
        stateMap.put("Piauí","Teresina");
        stateMap.put("Rio de Janeiro","Rio de Janeiro");
        stateMap.put("Rio Grande do Norte","Natal");
        stateMap.put("Rio Grande do Sul","Porto Alegre");
        stateMap.put("Rondônia","Porto Velho");
        stateMap.put("Roraima","Boa Vista");
        stateMap.put("Santa Catarina","Florianópolis");
        stateMap.put("São Paulo","São Paulo");
        stateMap.put("Sergipe","Aracaju");
        stateMap.put("Tocantins","Palmas");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.init();
        this.resetQuizHandler();
    }

    private void init() {
        resultLayout = findViewById(R.id.resultLayout);
        capitalInputLayout = findViewById(R.id.capitalInputLayout);

        greetTextView = findViewById(R.id.greetTextView);
        stateTextView = findViewById(R.id.stateTextView);
        infoTextView = findViewById(R.id.infoTextView);
        resultTextView = findViewById(R.id.resultTextView);
        correctAnswerTextView = findViewById(R.id.correctAnswer);

        defaultColor = getResources().getColor(R.color.black);
        wrongColor = getResources().getColor(R.color.wrong);
        correctColor = getResources().getColor(R.color.correct);

        confirmButton = findViewById(R.id.confirmBtn);
        confirmButton.setOnClickListener(this::confirmHandler);
        nextQuestionButton = findViewById(R.id.nextQuestionBtn);

        capitalTextInput = findViewById(R.id.capitalTextInput);
    }

    public void resetQuizHandler(View v) {
        resetQuizHandler();
    }

    private void resetQuizHandler() {
        points = 0;
        answeredQuestions = 0;
        nextQuestionButton.setOnClickListener(this::generateQuestionHandler);
        nextQuestionButton.setText(R.string.next_question);
        infoTextView.setText(R.string.answer_is);

        generateQuestionHandler();
    }

    private void setQuestionVisible() {
        greetTextView.setVisibility(View.VISIBLE);
        stateTextView.setVisibility(View.VISIBLE);
        capitalInputLayout.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.VISIBLE);
    }

    public void generateQuestionHandler(View v) {
        generateQuestionHandler();
    }

    private void generateQuestionHandler() {
        resultLayout.setVisibility(View.INVISIBLE);
        correctAnswerTextView.setVisibility(View.VISIBLE);

        Random r = new Random();
        int index =  r.nextInt((stateMap.size() - 1));
        this.stateMapIndex = index;
        this.stateMapEntry = (Map.Entry<String, String>) stateMap.entrySet().toArray()[index];

        stateTextView.setText(this.stateMapEntry.getKey());

        setQuestionVisible();
    }

    public String normalize(String text) {
        return Normalizer.normalize(text.trim().toLowerCase(Locale.ROOT), Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
    }

    public void confirmHandler(View view) {
        answeredQuestions++;

        String userText = capitalTextInput.getText().toString();
        userText = normalize(userText);

        if (userText.length() <= 0) {
            Toast.makeText(this, "Digite algo!", Toast.LENGTH_SHORT).show();
            return;
        }

        String matchText = normalize(this.stateMapEntry.getValue());

        boolean isCorrect = userText.equals(matchText);

        greetTextView.setVisibility(View.INVISIBLE);
        stateTextView.setVisibility(View.INVISIBLE);
        capitalInputLayout.setVisibility(View.INVISIBLE);
        confirmButton.setVisibility(View.INVISIBLE);

        if (isCorrect) {
            this.setCorrectAnswer();
        } else {
            this.setWrongAnswer(this.stateMapEntry.getValue());
        }

        if (answeredQuestions >= MAX_QUESTIONS) {
            nextQuestionButton.setText(R.string.end_game);
            nextQuestionButton.setOnClickListener(this::endGameHandler);
        }
        resultLayout.setVisibility(View.VISIBLE);
    }

    private void setCorrectAnswer() {
        points += 10;

        resultTextView.setTextColor(correctColor);
        resultTextView.setText(R.string.correct);

        correctAnswerTextView.setVisibility(View.INVISIBLE);
    }

    private void setWrongAnswer(String correctAnswer) {
        resultTextView.setTextColor(wrongColor);
        resultTextView.setText(R.string.wrong);

        String correctAnswerText = String.format(getResources().getString(R.string.correct_answer), correctAnswer);

        correctAnswerTextView.setText(correctAnswerText);
        correctAnswerTextView.setVisibility(View.VISIBLE);
    }

    public void endGameHandler(View v) {
        endGameHandler();
    }

    private void endGameHandler() {
        resultLayout.setVisibility(View.VISIBLE);
        correctAnswerTextView.setVisibility(View.INVISIBLE);

        String pointsText = String.format(getResources().getString(R.string.points), points);

        infoTextView.setText(R.string.end_game_label);
        resultTextView.setTextColor(defaultColor);
        resultTextView.setText(pointsText);

        nextQuestionButton.setOnClickListener(this::resetQuizHandler);
        nextQuestionButton.setText(R.string.new_game);
    }

}