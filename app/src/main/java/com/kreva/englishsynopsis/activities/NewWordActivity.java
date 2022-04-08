package com.kreva.englishsynopsis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.kreva.englishsynopsis.databinding.ActivityNewWordBinding;
import com.kreva.englishsynopsis.entity.Word;
import com.kreva.englishsynopsis.sql.AppDatabase;

public class NewWordActivity extends AppCompatActivity implements SpellCheckerSession.SpellCheckerSessionListener {
    private ActivityNewWordBinding binding;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void setListeners(){
        binding.back.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });
        binding.saveWord.setOnClickListener(v -> {
            if (isValidSignDetails()){
                database = AppDatabase.getAppDatabase(this);
                Word newWord = new Word(1,binding.english.getText().toString(),binding.russian.getText().toString());
                database.wordDAO().insertWord(newWord);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
    private Boolean isValidSignDetails() {
        if (binding.english.getText().toString().trim().isEmpty()) {
            showToast("Enter english word");
            return false;
        } else if (binding.russian.getText().toString().trim().isEmpty()) {
            showToast("Enter russian world");
            return false;
        } else {
            return true;
        }

    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetSuggestions(SuggestionsInfo[] results) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < results.length; ++i) {
            // Returned suggestions are contained in SuggestionsInfo
            final int len = results[i].getSuggestionsCount();
            sb.append('\n');

            for (int j = 0; j < len; ++j) {
                sb.append("," + results[i].getSuggestionAt(j));
            }

            sb.append(" (" + len + ")");
        }

        runOnUiThread(new Runnable() {
            public void run() {
                binding.saveWord.append(sb.toString());
            }
        });
    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {

    }
}