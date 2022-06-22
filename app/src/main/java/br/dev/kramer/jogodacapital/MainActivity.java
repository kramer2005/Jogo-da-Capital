package br.dev.kramer.jogodacapital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

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

        // @todo - Setar o estado que o usuário deve dizer a capital
        stateTextView.setText("Bahia");

        setQuestionVisible();
    }

    public void confirmHandler(View view) {
        answeredQuestions++;
        Random r = new Random();
        boolean isCorrect = r.nextBoolean();

        // @todo - Validar se a resposta está correta e remover o random usado para teste
        // Lembrar de validar removendo acentos e com todas as letras minusculas
        // tanto no correto quanto na resposta do usuário

        greetTextView.setVisibility(View.INVISIBLE);
        stateTextView.setVisibility(View.INVISIBLE);
        capitalInputLayout.setVisibility(View.INVISIBLE);
        confirmButton.setVisibility(View.INVISIBLE);


        if (isCorrect) {
            this.setCorrectAnswer();
        } else {
            // @todo - Passar a resposta correta como parâmetro
            this.setWrongAnswer("Lorem Ipsum");
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