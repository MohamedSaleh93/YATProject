package com.mohamed.yatproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        EmojiView emojiView1 = findViewById(R.id.emoji1);
        EmojiView emojiView2 = findViewById(R.id.emoji2);

        emojiView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emojiView1.getHappinessState() == EmojiView.HAPPY) {
                    emojiView1.setHappinessState(EmojiView.SAD);
                } else {
                    emojiView1.setHappinessState(EmojiView.HAPPY);
                }
            }
        });

        emojiView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emojiView2.getHappinessState() == EmojiView.HAPPY) {
                    emojiView2.setHappinessState(EmojiView.SAD);
                } else {
                    emojiView2.setHappinessState(EmojiView.HAPPY);
                }
            }
        });
    }
}
