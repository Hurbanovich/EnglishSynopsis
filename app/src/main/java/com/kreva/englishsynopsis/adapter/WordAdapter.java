package com.kreva.englishsynopsis.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kreva.englishsynopsis.R;
import com.kreva.englishsynopsis.entity.Word;
import com.kreva.englishsynopsis.sql.AppDatabase;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    private List<Word> wordlist;
    private Activity context;
    private AppDatabase database;


    public WordAdapter(Activity context, List<Word> wordList) {
        this.context = context;
        this.wordlist = wordList;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WordViewHolder holder, int position) {
        Word word = wordlist.get(position);
        database = AppDatabase.getAppDatabase(context);
        holder.russian.setText(word.getRussianWord());
        holder.english.setText(word.getEnglishWord());
        if (word.getActive() == 1) {
            holder.check.setImageResource(R.drawable.checked_on);
        } else {
            holder.check.setImageResource(R.drawable.checked_of);
        }


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word w = wordlist.get(holder.getAdapterPosition());
                database.wordDAO().delete(w);
                int position = holder.getAdapterPosition();
                wordlist.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, wordlist.size());


            }
        });

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word w = wordlist.get(holder.getAdapterPosition());
                if (w.getActive() == 1){
                database.wordDAO().editWord(0,w.getUid());
                }else {
                    database.wordDAO().editWord(1,w.getUid());
                }
                wordlist.remove(position);
                wordlist.add(position,database.wordDAO().findById(w.getUid()));
                notifyItemRangeChanged(position, wordlist.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return wordlist.size();
    }


    public class WordViewHolder extends RecyclerView.ViewHolder {
        private TextView english;
        private TextView russian;
        private ImageView delete, check;

        private Word word;


        public WordViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            english = itemView.findViewById(R.id.text_en);
            russian = itemView.findViewById(R.id.text_ru);
            delete = itemView.findViewById(R.id.bt_delete);
            check = itemView.findViewById(R.id.active);
        }
    }
}
