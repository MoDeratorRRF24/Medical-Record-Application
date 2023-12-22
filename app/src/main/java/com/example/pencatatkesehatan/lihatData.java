package com.example.pencatatkesehatan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class lihatData extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private LinearLayout buttonHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);

        buttonHome = findViewById(R.id.buttonHome);

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(lihatData.this, MainActivity.class);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(this);

        LinearLayout noteList = findViewById(R.id.noteList);
        CreateView(noteList);
    }

    @SuppressLint("Range")
    private void CreateView(LinearLayout x) {
        Cursor data = databaseHelper.getAllData();

        while (data.moveToNext()) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.notelayout, null, false);

            x.addView(layout);

            int count = x.getChildCount();
            View v;

            v = x.getChildAt(count - 1);
            TextView daValue = v.findViewById(R.id.date);
            daValue.setText((data.getString(data.getColumnIndex(DatabaseHelper.COL_DATE))));

            LinearLayout noteValues = v.findViewById(R.id.noteValues);

            TextView boValue = noteValues.findViewById(R.id.bodyWeightNum);
            boValue.setText(String.valueOf(data.getString(data.getColumnIndex(DatabaseHelper.COL_BERAT_BADAN))));

            TextView hValue = noteValues.findViewById(R.id.heartRateNum);
            hValue.setText(String.valueOf(data.getString(data.getColumnIndex(DatabaseHelper.COL_DETAK_JANTUNG))));

            TextView sValue = noteValues.findViewById(R.id.sugarLevelNum);
            sValue.setText(String.valueOf(data.getString(data.getColumnIndex(DatabaseHelper.COL_KADAR_GULA))));

            TextView blValue = noteValues.findViewById(R.id.bloodPressureNum);
            blValue.setText(String.valueOf(data.getString(data.getColumnIndex(DatabaseHelper.COL_TEKANAN_DARAH))));

            TextView cValue = noteValues.findViewById(R.id.cholesterolNum);
            cValue.setText(String.valueOf(data.getString(data.getColumnIndex(DatabaseHelper.COL_KOLESTROL))));

            Button editButton = v.findViewById(R.id.editButton);
            final int itemId = data.getInt(data.getColumnIndex(DatabaseHelper.COL_ID));
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("BUTTONS", "Implement fun for editing");
                    Intent intent = new Intent(lihatData.this, editActivity.class);
                    intent.putExtra("itemId", itemId);
                    startActivity(intent);
                }
            });

            Button deleteButton = v.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("BUTTONS", "Implement fun for deleting");
                    boolean deleted = databaseHelper.deleteData(itemId);
                    if (deleted) {
                        Toast.makeText(lihatData.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(lihatData.this, lihatData.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(lihatData.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}