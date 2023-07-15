package sg.edu.rp.c346.id22020383.nationaldayparadethemesongcompilation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etSingers, etYear;
    RadioGroup rgStars;
    Button btnInsert, btnShowList;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etSingers = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);
        rgStars = findViewById(R.id.rgStars);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        dbHelper = new DBHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String singers = etSingers.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());
                int stars = getSelectedStars();
                long id = dbHelper.insertSong(title, singers, year, stars);
                if (id != -1) {
                    Toast.makeText(MainActivity.this, "Song inserted successfully", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to insert song", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SongListActivity.class);
                startActivity(intent);
            }
        });
    }

    private int getSelectedStars() {
        int selectedId = rgStars.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        return Integer.parseInt(radioButton.getText().toString());
    }

    private void clearInputFields() {
        etTitle.setText("");
        etSingers.setText("");
        etYear.setText("");
        rgStars.clearCheck();
    }
}