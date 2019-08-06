package com.alexandre.fenris.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alexandre.fenris.R;
import com.alexandre.fenris.model.ItemRowScore;

import java.util.List;

public class ScoreAdapter extends ArrayAdapter<ItemRowScore> {

    //scoreList is the model list show
    public ScoreAdapter(Context context, List<ItemRowScore> scoreList) {
        super(context, 0, scoreList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_score, parent, false);
        }

        ScoreViewHolder holder = (ScoreViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ScoreViewHolder();
            holder.pseudo = (TextView) convertView.findViewById(R.id.row_pseudo);
            holder.score = (TextView) convertView.findViewById(R.id.row_score);
            holder.date = (TextView)convertView.findViewById(R.id.row_date);
            convertView.setTag(holder);
        }

        ItemRowScore currentItem = getItem(position);

        assert currentItem != null;
        holder.pseudo.setText(currentItem.getPseudo());
        holder.score.setText(currentItem.getScore());
        holder.date.setText(currentItem.getDate());

        return convertView;
    }

    private class ScoreViewHolder {
        public TextView pseudo;
        public TextView score;
        public TextView date;
    }
}