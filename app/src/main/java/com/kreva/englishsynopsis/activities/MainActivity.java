package com.kreva.englishsynopsis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import com.kreva.englishsynopsis.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.newWord.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), NewWordActivity.class);
            startActivity(intent);
            finish();
        });
        binding.learnEnglishWords.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MemorizingEnglishActivity.class);
            startActivity(intent);
            finish();
        });

        binding.learnRussianWords.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MemorizingRussianActivity.class);
            startActivity(intent);
            finish();
        });
        binding.learnWords.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AllWordActivity.class);
            startActivity(intent);
            finish();
        });
        binding.spellingWord.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SpellingOfWordsActivity.class);
            startActivity(intent);
            finish();
        });
    }


}