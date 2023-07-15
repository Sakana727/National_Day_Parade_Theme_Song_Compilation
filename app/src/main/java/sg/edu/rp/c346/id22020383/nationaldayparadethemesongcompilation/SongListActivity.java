package sg.edu.rp.c346.id22020383.nationaldayparadethemesongcompilation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SongListActivity extends AppCompatActivity {

    ListView listView;
    Button btnShowFiveStarSongs;
    Spinner spinnerYear;
    DBHelper dbHelper;
    ArrayAdapter<Song> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        listView = findViewById(R.id.listView);
        btnShowFiveStarSongs = findViewById(R.id.btnShowFiveStarSongs);
        spinnerYear = findViewById(R.id.spinnerYear);
        dbHelper = new DBHelper(this);

        // Populate the spinner with distinct years from the database
        populateSpinner();

        ArrayList<Song> songs = dbHelper.getAllSongs();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songs);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song selectedSong = (Song) parent.getItemAtPosition(position);
                Intent intent = new Intent(SongListActivity.this, EditSongActivity.class);
                intent.putExtra("song", selectedSong);
                startActivityForResult(intent, 1);
            }
        });

        btnShowFiveStarSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Song> fiveStarSongs = dbHelper.getFiveStarSongs();
                adapter.clear();
                adapter.addAll(fiveStarSongs);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void populateSpinner() {
        spinnerYear = findViewById(R.id.spinnerYear); // Initialize the Spinner

        ArrayList<Integer> years = dbHelper.getDistinctYears();

        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(spinnerAdapter);

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedYear = (int) parent.getItemAtPosition(position);
                ArrayList<Song> songsByYear = dbHelper.getSongsByYear(selectedYear);
                adapter.clear();
                adapter.addAll(songsByYear);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SongListActivity.this, "No year selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                refreshListView();
            }
        }
    }

    private void refreshListView() {
        ArrayList<Song> songs = dbHelper.getAllSongs();
        adapter.clear();
        adapter.addAll(songs);
        adapter.notifyDataSetChanged();
    }
}

