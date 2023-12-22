package com.example.pencatatkesehatan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class addData extends AppCompatActivity {

    private EditText BeratBadan;
    private EditText DetakJantung;
    private EditText KadarGula;
    private EditText TekananDarah;
    private EditText Kolestrol;
    private Button buttonSubmit;
    private LinearLayout buttonHome;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        BeratBadan = findViewById(R.id.BeratBadan);
        DetakJantung = findViewById(R.id.DetakJantung);
        KadarGula = findViewById(R.id.KadarGula);
        TekananDarah = findViewById(R.id.TekananDarah);
        Kolestrol = findViewById(R.id.Kolestrol);

        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonHome = findViewById(R.id.buttonHome);

        databaseHelper = new DatabaseHelper(this);

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addData.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {
                String beratBadan = BeratBadan.getText().toString();
                String detakJantung = DetakJantung.getText().toString();
                String kadarGula = KadarGula.getText().toString();
                String tekananDarah = TekananDarah.getText().toString();
                String kolestrol = Kolestrol.getText().toString();


                if (!beratBadan.isEmpty() || !detakJantung.isEmpty() || !kadarGula.isEmpty() ||
                        !tekananDarah.isEmpty() || !kolestrol.isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDate = sdf.format(new Date());
                    boolean insertData = databaseHelper.addData(currentDate, beratBadan, detakJantung, kadarGula, tekananDarah, kolestrol);

                    if (insertData) {
                        Cursor data = databaseHelper.getAllData();
                        data.moveToNext();
                        data.moveToNext();
                        Toast.makeText(addData.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        BeratBadan.setText("");
                        DetakJantung.setText("");
                        KadarGula.setText("");
                        TekananDarah.setText("");
                        Kolestrol.setText("");
                    } else {
                        Toast.makeText(addData.this, "Gagal menambahkan data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(addData.this, "Silahkan isi minimal satu data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}