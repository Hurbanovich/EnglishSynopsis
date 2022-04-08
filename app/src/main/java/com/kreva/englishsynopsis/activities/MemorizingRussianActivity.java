package com.kreva.englishsynopsis.activities;

import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.kreva.englishsynopsis.databinding.ActivityMemorizingRussianBinding;
import com.kreva.englishsynopsis.entity.Word;
import com.kreva.englishsynopsis.sql.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class MemorizingRussianActivity extends AppCompatActivity {
    private ActivityMemorizingRussianBinding binding;
    private List<Word> allWorld = new ArrayList<>();
    private AppDatabase database;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemorizingRussianBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = AppDatabase.getAppDatabase(this);
        checkDB();
        allWorld.addAll(database.wordDAO().getAllWord());
        activityCheck();
        setListeners();
    }

    private void setListeners() {
        binding.home.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });
        binding.back.setOnClickListener(v -> {
            decrementIndex();
            if (isValidSignDetails()) {
                if (index < 0) {
                    ruWord("нажмите NEXT для продолжения");
                    enWord(" ");
                    binding.transferCheck.setOnClickListener(v1 -> {
                        enWord("click next to continue");
                    });

                } else if (index == 0) {
                    ruWord(allWorld.get(index).getRussianWord());
                    enWord("");
                    binding.transferCheck.setOnClickListener(v1 -> {
                        enWord(allWorld.get(index).getEnglishWord());

                    });
                } else if (checkValidIndex()) {
                    ruWord(allWorld.get(index).getRussianWord());
                    enWord("");
                    binding.transferCheck.setOnClickListener(v1 -> {
                        enWord(allWorld.get(index).getEnglishWord());

                    });
                }
            }

        });

        binding.next.setOnClickListener(v -> {
            incrementIndex();
            if (isValidSignDetails()) {
                if (index < 0 || index == 0) {
                    index = 0;
                    ruWord(allWorld.get(index).getRussianWord());
                    enWord("");

                    binding.transferCheck.setOnClickListener(v1 -> {
                        enWord(allWorld.get(index).getEnglishWord());

                    });
                } else if (checkValidIndex()) {
                    ruWord(allWorld.get(index).getRussianWord());
                    enWord("");

                    binding.transferCheck.setOnClickListener(v1 -> {
                        enWord(allWorld.get(index).getEnglishWord());

                    });
                } else if (index >= allWorld.size()) {
                    index = allWorld.size();
                    ruWord("все!");
                    enWord("");

                    binding.transferCheck.setOnClickListener(v1 -> {
                        enWord("finish");

                    });
                } else {
                    ruWord("все!");
                    binding.transferCheck.setOnClickListener(v1 -> {
                        enWord("finish");
                    });
                }
            }

        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
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


    private void checkDB() {
        if (database.wordDAO().getAllWord().size() == 0) {
            Intent intent = new Intent(getApplicationContext(), NewWordActivity.class);
            startActivity(intent);
            finish();
        } else {
            enWord("click next to continue");
            ruWord("нажмите NEXT для продолжения");
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

    private boolean checkValidIndex() {
        if (index < allWorld.size() && index >= 0) {
            return true;
        }
        return false;
    }

    private void enWord(String text) {
        binding.textEn.setText(text);

    }

    private void ruWord(String text) {
        binding.textRu.setText(text);
    }

    private Boolean isValidSignDetails() {
        if (allWorld.size() == 0) {
            showToast("нет активных слов для запоминания");
            return false;
        } else {
            return true;
        }

    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}