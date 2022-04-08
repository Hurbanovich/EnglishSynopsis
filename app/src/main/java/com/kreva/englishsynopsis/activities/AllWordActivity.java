package com.kreva.englishsynopsis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.kreva.englishsynopsis.adapter.WordAdapter;
import com.kreva.englishsynopsis.databinding.ActivityAllWordBinding;
import com.kreva.englishsynopsis.entity.Word;
import com.kreva.englishsynopsis.sql.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class AllWordActivity extends AppCompatActivity {
    private ActivityAllWordBinding binding;
    private AppDatabase database;
    private WordAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    private List<Word> wordList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        database = AppDatabase.getAppDatabase(this);
        wordList = database.wordDAO().getAllWord();

        linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new WordAdapter(AllWordActivity.this, wordList);
        binding.recyclerView.setAdapter(adapter);

        binding.btAdd.setOnClickListener(v -> {
            Word word = new Word();
            word.setEnglishWord(binding.editEnWord.getText().toString().trim());
            word.setRussianWord(binding.editRuWord.getText().toString().trim());
            word.setActive(1);

            if (!word.getEnglishWord().equals("") && !word.getRussianWord().equals("")) {
                database.wordDAO().insertWord(word);

                binding.editEnWord.setText("");
                binding.editRuWord.setText("");

                wordList.clear();
                wordList.addAll(database.wordDAO().getAllWord());
                adapter.notifyDataSetChanged();

            }
        });

        binding.btReset.setOnClickListener(v -> {
            binding.editEnWord.setText("");
            binding.editRuWord.setText("");
            adapter.notifyDataSetChanged();
        });



    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}