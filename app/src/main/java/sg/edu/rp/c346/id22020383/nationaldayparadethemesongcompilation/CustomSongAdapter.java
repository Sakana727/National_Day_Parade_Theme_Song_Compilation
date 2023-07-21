package sg.edu.rp.c346.id22020383.nationaldayparadethemesongcompilation;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomSongAdapter extends ArrayAdapter<Song> {

    public CustomSongAdapter(Context context, ArrayList<Song> songs) {
        super(context, 0, songs);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Song song = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_song_item, parent, false);
        }

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvSingers = convertView.findViewById(R.id.tvSingers);
        TextView tvYear = convertView.findViewById(R.id.tvYear);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBar);

        String title = song.getTitle();
        String singers = song.getSingers();
        int year = song.getYear();
        int stars = song.getStars();

        SpannableStringBuilder titleBuilder = new SpannableStringBuilder(title);
        SpannableStringBuilder singersBuilder = new SpannableStringBuilder(singers);
        titleBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#6a0eee")), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        singersBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#7cfffb")), 0, singers.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvTitle.setText(titleBuilder);
        tvSingers.setText(singersBuilder);
        tvYear.setText(String.valueOf(year));
        ratingBar.setRating(stars);

        ratingBar.setBackgroundColor(Color.parseColor("#eb3976"));

        return convertView;
    }
}