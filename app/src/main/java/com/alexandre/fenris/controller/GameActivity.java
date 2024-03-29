package com.alexandre.fenris.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alexandre.fenris.R;
import com.alexandre.fenris.model.Question;
import com.alexandre.fenris.model.QuestionBank;

import java.util.Arrays;

import static java.lang.System.out;

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
    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";
    private boolean mEnableTouchEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        System.out.println("GameActivity::onCreate");

        mQuestionBank = this.generateQuestions();

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 6;
        }

        mEnableTouchEvents = true;

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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);
        super.onSaveInstanceState(outState);
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
        mEnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;

                // If this is the last question, ends the game.
                // Else, display the next question.
                if (--mNumberOfQuestions == 0) {
                    // End the game
                    endGame();
                } else {
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }

            }
        }, 2000); // LENGTH_SHORT is usually 2 second long
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            return mEnableTouchEvents && super.dispatchTouchEvent(ev);
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
                .setCancelable(false)
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
        Question question1 = new Question("Quel est la capitale de l'Autriche ?",
                Arrays.asList("Berlin", "Vienne", "La Valette", "Chisinau"),
                1);

        Question question2 = new Question("Quel est la capitale de l'Allemagne ?",
                Arrays.asList("Amsterdam", "Munich", "Berlin", "Paris"),
                2);

        Question question3 = new Question("Quel est la capitale du Monténégro ?",
                Arrays.asList("Podgorica", "Skopje", "Prague", "Varsovie"),
                0);

        Question question4 = new Question("Quel est la capitale de la Roumanie?",
                Arrays.asList("Berne", "Bratislava", "Moscou", "Bucarest"),
                3);

        Question question5 = new Question("A quel pays appartient la capitale Stockholm ?",
                Arrays.asList("Suède", "Finlande", "Islande","Danemark"),
                0);

        Question question6 = new Question("A quel pays appartient la capitale de Budapest ?",
                Arrays.asList("Lettonie", "Pologne", "Hongrie", "Ukraine"),
                2);
        return new QuestionBank(Arrays.asList(question1,
                                                question2,
                                                question3,
                                                question4,
                                                question5,
                                                question6));
    }

    @Override
    protected void onStart() {
        super.onStart();

        out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();

        out.println("MainActivity::onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        out.println("MainActivity::onDestroy");
    }

}
