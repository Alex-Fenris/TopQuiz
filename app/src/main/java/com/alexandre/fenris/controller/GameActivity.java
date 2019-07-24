package com.alexandre.fenris.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alexandre.fenris.R;
import com.alexandre.fenris.model.Question;
import com.alexandre.fenris.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionBank = this.generateQuestions();

        mScore = 0;
        mNumberOfQuestions = 4;

        // Wire widgets
        mQuestionTextView = findViewById(R.id.activity_game_question_text);
        mAnswerButton1 = findViewById(R.id.activity_game_answer1_btn);
        mAnswerButton2 = findViewById(R.id.activity_game_answer2_btn);
        mAnswerButton3 = findViewById(R.id.activity_game_answer3_btn);
        mAnswerButton4 = findViewById(R.id.activity_game_answer4_btn);

        // Use the tag property to "name" the buttons
        mAnswerButton1.setTag(0);
        mAnswerButton2.setTag(1);
        mAnswerButton3.setTag(2);
        mAnswerButton4.setTag(3);

        mAnswerButton4.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton1.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    @Override
    public void onClick(View view) {
        int responseIndex = (int) view.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            // Good answer
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            // Wrong answer
            Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (--mNumberOfQuestions == 0) {
            // End the game
            endGame();
        } else {
            mCurrentQuestion = mQuestionBank.getQuestion();
            displayQuestion(mCurrentQuestion);
        }
    }

    private void endGame() {
        AlertDialog.Builder  builder= new  AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // End the activity
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    private void displayQuestion(final Question question) {
        mQuestionTextView.setText(question.getQuestion());
        mAnswerButton1.setText(question.getChoiceList().get(0));
        mAnswerButton2.setText(question.getChoiceList().get(1));
        mAnswerButton3.setText(question.getChoiceList().get(2));
        mAnswerButton4.setText(question.getChoiceList().get(3));

    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("What is the name of the network of computers from which the Internet has emerged?",
                Arrays.asList("Internet, Arpanet, HTML, Linux"),
                1);

        Question question2 = new Question("In what year was Google launched on the web?",
                Arrays.asList("1996, 1997, 1998, 1999"),
                2);

        Question question3 = new Question("What is the country top-level domain of Belgium?",
                Arrays.asList(".be, .com, .net, org"),
                0);

        Question question4 = new Question("Which unit is an indication for the sound quality of MP3?",
                Arrays.asList("Db, Nax, Epndb, Kbps"),
                3);

        Question question5 = new Question("In computing what is Ram short for?",
                Arrays.asList("Random access memory, Usual memory, Internal memory,External memory"),
                0);

        Question question6 = new Question("In which Nintendo DS game do you have to raise a puppy as well as possible?",
                Arrays.asList("Tamaguci, Doc Dogs, Nintendogs, Dogs & Cats"),
                2);
        return new QuestionBank(Arrays.asList(question1,
                                                question2,
                                                question3,
                                                question4,
                                                question5,
                                                question6));
    }

}
