package com.example.memberapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.memberapp.Model.Rule;
import com.example.memberapp.R;

import java.util.List;

public class RuleList extends ArrayAdapter<Rule>{
    private Activity context;
    private List<Rule> ruleList;

    public RuleList(Activity context, List<Rule> ruleList) {
        super(context,R.layout.ruleshow, ruleList);
        this.context = context;
        this.ruleList = ruleList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.ruleshow, null, true);

        TextView textViewrule = listViewItem.findViewById(R.id.rule_text_view);

        Rule rule = ruleList.get(position);

        textViewrule.setText("➡ " + rule.getRule());

        return listViewItem;
    }

}
