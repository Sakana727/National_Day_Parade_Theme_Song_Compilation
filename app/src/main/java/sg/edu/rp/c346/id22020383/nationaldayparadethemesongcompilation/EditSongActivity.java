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

public class EditSongActivity extends AppCompatActivity {

    EditText etTitle, etSingers, etYear;
    RadioGroup rgStars;
    Button btnUpdate, btnDelete;

    DBHelper dbHelper;
    Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_song);

        etTitle = findViewById(R.id.etTitle);
        etSingers = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);
        rgStars = findViewById(R.id.rgStars);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            song = (Song) intent.getSerializableExtra("song");
            if (song != null) {
                etTitle.setText(song.getTitle());
                etSingers.setText(song.getSingers());
                etYear.setText(String.valueOf(song.getYear()));
                selectStars(song.getStars());
            }
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String singers = etSingers.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());
                int stars = getSelectedStars();
                song.setTitle(title); // Update the title directly
                song.setSingers(singers);
                song.setYear(year);
                song.setStars(stars);
                dbHelper.updateSong(song);
                Toast.makeText(EditSongActivity.this, "Song updated successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteSong(song.getId());
                Toast.makeText(EditSongActivity.this, "Song deleted successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void selectStars(int stars) {
        RadioButton radioButton = null;
        switch (stars) {
            case 1:
                radioButton = findViewById(R.id.rb1Star);
                break;
            case 2:
                radioButton = findViewById(R.id.rb2Stars);
                break;
            case 3:
                radioButton = findViewById(R.id.rb3Stars);
                break;
            case 4:
                radioButton = findViewById(R.id.rb4Stars);
                break;
            case 5:
                radioButton = findViewById(R.id.rb5Stars);
                break;
        }
        if (radioButton != null) {
            radioButton.setChecked(true);
        }
    }

    private int getSelectedStars() {
        int selectedId = rgStars.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        return Integer.parseInt(radioButton.getText().toString());
    }
}