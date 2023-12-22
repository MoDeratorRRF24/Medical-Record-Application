package com.example.pencatatkesehatan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class editActivity extends AppCompatActivity {
    private EditText beratBadan, detakJantung, kadarGula,
            tekananDarah, kolestrol;
    private Button buttonSubmit;

    private LinearLayout buttonHome;

    private DatabaseHelper databaseHelper;
    private int itemId;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        beratBadan = findViewById(R.id.BeratBadan);
        detakJantung = findViewById(R.id.DetakJantung);
        kadarGula = findViewById(R.id.KadarGula);
        tekananDarah = findViewById(R.id.TekananDarah);
        kolestrol = findViewById(R.id.Kolestrol);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonHome = findViewById(R.id.buttonHome);

        databaseHelper = new DatabaseHelper(this);

        itemId = getIntent().getIntExtra("itemId", -1);

        Cursor data = databaseHelper.getDataById(itemId);
        if (data.moveToFirst()) {
            // Mengisi kolom formulir edit dengan nilai yang ada
            beratBadan.setText(data.getString(data.getColumnIndex(DatabaseHelper.COL_BERAT_BADAN)));
            detakJantung.setText(data.getString(data.getColumnIndex(DatabaseHelper.COL_DETAK_JANTUNG)));
            kadarGula.setText(data.getString(data.getColumnIndex(DatabaseHelper.COL_KADAR_GULA)));
            tekananDarah.setText(data.getString(data.getColumnIndex(DatabaseHelper.COL_TEKANAN_DARAH)));
            kolestrol.setText(data.getString(data.getColumnIndex(DatabaseHelper.COL_KOLESTROL)));
        }

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(editActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengambil nilai dari kolom formulir edit
                String date = data.getString(data.getColumnIndex(DatabaseHelper.COL_DATE));
                String beratBadans = beratBadan.getText().toString().trim();
                String detakJantungs = detakJantung.getText().toString().trim();
                String kadarGulas = kadarGula.getText().toString().trim();
                String tekananDarahs = tekananDarah.getText().toString().trim();
                String kolestrols = kolestrol.getText().toString().trim();

                boolean updated = databaseHelper.updateData(itemId, date, beratBadans, detakJantungs, kadarGulas,
                        tekananDarahs, kolestrols);
                if (updated) {
                    Toast.makeText(editActivity.this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(editActivity.this, lihatData.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(editActivity.this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}