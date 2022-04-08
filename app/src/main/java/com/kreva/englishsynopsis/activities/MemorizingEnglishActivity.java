package com.kreva.englishsynopsis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.kreva.englishsynopsis.databinding.ActivityMemorizingEnglishBinding;
import com.kreva.englishsynopsis.entity.Word;
import com.kreva.englishsynopsis.sql.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class MemorizingEnglishActivity extends AppCompatActivity {
    private ActivityMemorizingEnglishBinding binding;
    private List<Word> allWorld = new ArrayList<>();
    private AppDatabase database;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMemorizingEnglishBinding.inflate(getLayoutInflater());
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
                    enWord("click next to continue");
                    ruWord("");
                    binding.transferCheck.setOnClickListener(v1 -> {
                        ruWord("нажмите NEXT для продолжения");
                    });

                } else if (index == 0) {
                    enWord(allWorld.get(index).getEnglishWord());
                    ruWord("");
                    binding.transferCheck.setOnClickListener(v1 -> {
                        ruWord(allWorld.get(index).getRussianWord());

                    });
                } else if (checkValidIndex()) {
                    enWord(allWorld.get(index).getEnglishWord());
                    ruWord("");
                    binding.transferCheck.setOnClickListener(v1 -> {
                        ruWord(allWorld.get(index).getRussianWord());

                    });
                }
            }

        });

        binding.next.setOnClickListener(v -> {
            incrementIndex();
            if (isValidSignDetails()) {
                if (index < 0 || index == 0) {
                    index = 0;
                    enWord(allWorld.get(index).getEnglishWord());
                    ruWord("");
                    binding.transferCheck.setOnClickListener(v1 -> {
                        ruWord(allWorld.get(index).getRussianWord());

                    });
                } else if (checkValidIndex()) {
                    enWord(allWorld.get(index).getEnglishWord());
                    ruWord("");
                    binding.transferCheck.setOnClickListener(v1 -> {
                        ruWord(allWorld.get(index).getRussianWord());

                    });
                } else if (index >= allWorld.size()) {
                    index = allWorld.size();
                    enWord("finish");
                    ruWord("");

                    binding.transferCheck.setOnClickListener(v1 -> {
                        ruWord("все!");

                    });
                } else {
                    enWord("finish");
                    ruWord("");

                    binding.transferCheck.setOnClickListener(v1 -> {
                        ruWord("все!");
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


    private void checkDB() {
        if (database.wordDAO().getAllWord().size() == 0) {
            Intent intent = new Intent(getApplicationContext(), NewWordActivity.class);
            startActivity(intent);
            finish();
        } else {
            enWord("click next to continue");
            ruWord(null);
        }
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