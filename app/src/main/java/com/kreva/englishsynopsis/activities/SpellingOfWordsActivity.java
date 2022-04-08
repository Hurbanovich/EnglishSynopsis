package com.kreva.englishsynopsis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.kreva.englishsynopsis.databinding.ActivitySpellingOfWordsBinding;
import com.kreva.englishsynopsis.entity.Word;
import com.kreva.englishsynopsis.sql.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class SpellingOfWordsActivity extends AppCompatActivity {
    private ActivitySpellingOfWordsBinding binding;
    private List<Word> allWorld = new ArrayList<>();
    private AppDatabase database;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpellingOfWordsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = AppDatabase.getAppDatabase(this);

        checkDB();

        allWorld.addAll(database.wordDAO().getAllWord());

        activityCheck();

        setListeners();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setListeners() {
        binding.backMain.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        binding.back.setOnClickListener(v -> {
            decrementIndex();
            if (isValidListWords()) {
                binding.correctText.setText(null);
                binding.errorText.setText(null);
                if (index < 0) {
                    textToTranslate("click next");
                    resetText();

                } else if (index == 0) {
                    textToTranslate(allWorld.get(index).getRussianWord());
                    resetText();
                } else if (checkValidIndex()) {
                    textToTranslate(allWorld.get(index).getRussianWord());
                    resetText();
                }
            }

        });

        binding.next.setOnClickListener(v -> {
            incrementIndex();
//            binding.imageCheck.setImageResource(R.drawable.ic_edit);
            if (isValidListWords()) {
                binding.correctText.setText(null);
                binding.errorText.setText(null);
                if (index < 0 || index == 0) {
                    index = 0;
                    textToTranslate(allWorld.get(index).getRussianWord());
                    resetText();
                } else if (checkValidIndex()) {
                    textToTranslate(allWorld.get(index).getRussianWord());
                    resetText();
                } else if (index >= allWorld.size()) {
                    index = 0;
                    textToTranslate(allWorld.get(index).getRussianWord());
                    resetText();
                }
            }

        });

        binding.checkSpelling.setOnClickListener(v -> {
            if (isValidListWords()) {
                if (checkValidIndex()) {
                    trueTranslate(allWorld.get(index).getEnglishWord());
                } else {
                    textToTranslate("click next");
                    resetText();
                }
            }
        });

        binding.suggest.setOnClickListener(v -> {
            if (isValidListWords() && isValidSignDetails() && checkValidIndex()) {
                checkInputText(index);
            } else {
                resetText();
            }
        });

    }

    private void activityCheck() {
        int i = allWorld.size();
        do {
            i--;
            if (allWorld.get(i).getActive() == 0) {
                allWorld.remove(allWorld.get(i));
            }
        }while (i>0);
    }

    private void resetText() {
        trueTranslate(null);
        suggestText(null);
        binding.enteredText.setText(null);
        binding.correctText.setText(null);
        binding.errorText.setText(null);
    }

    private void checkInputText(int index) {
        if (binding.enteredText.getText().toString().trim().equalsIgnoreCase(allWorld.get(index).getEnglishWord().trim())) {
            binding.correctText.setText("correct");
            binding.errorText.setText(null);
            suggestText(allWorld.get(index).getEnglishWord());

        } else {
            binding.errorText.setText("error");
            binding.correctText.setText(null);
            suggestText(binding.enteredText.getText().toString());

        }

        binding.enteredText.setText(null);
    }


    private boolean checkValidIndex() {
        if (index < allWorld.size() && index >= 0) {
            return true;
        }
        return false;
    }

    private Boolean isValidListWords() {
        if (allWorld.size() == 0) {
            showToast("нет активных слов");
            return false;
        } else {
            return true;
        }
    }

    private Boolean isValidSignDetails() {
        if (binding.enteredText.getText().toString().trim().isEmpty()) {
            showToast("Enter english word");
            return false;
        } else {
            return true;
        }

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void suggestText(String text) {
        binding.suggestText.setText(text);

    }

    private void trueTranslate(String text) {
        binding.trueTranslate.setText(text);

    }

    private void textToTranslate(String text) {
        binding.textToTranslate.setText(text);

    }

    private void checkDB() {
        if (database.wordDAO().getAllWord().size() == 0) {
            Intent intent = new Intent(getApplicationContext(), NewWordActivity.class);
            startActivity(intent);
            finish();
        } else {
            textToTranslate("click next");
        }
    }


    private int getIndex() {
        return index;
    }

    private void setIndex(int index) {
        this.index = index;
    }

    private void incrementIndex() {
        setIndex(getIndex() + 1);
    }

    private void decrementIndex() {
        setIndex(getIndex() - 1);
    }


}