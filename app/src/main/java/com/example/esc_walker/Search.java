package com.example.esc_walker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Search extends AppCompatActivity {

    private Button search_btn_lookup;
    private ImageButton search_ibtn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_ibtn_back = findViewById(R.id.search_ibtn_back);
        search_btn_lookup = findViewById(R.id.serach_btn_lookup);

        search_ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}