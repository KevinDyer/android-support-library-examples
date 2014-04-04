
package com.l2a.main.widget;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.l2a.main.widget.ActionAdapter.Action;

public class ActionAdapter extends ArrayAdapter<Action> {
    private final LayoutInflater mInflater;

    public interface Action {
        CharSequence getTitle();

        CharSequence getSummary();

        void execute();
    }

    public ActionAdapter(Context context) {
        super(context, 0, new ArrayList<Action>());

        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Action action = getItem(position);

        ViewHolder holder;
        if (null == view) {
            view = mInflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(android.R.id.text1);
            holder.summary = (TextView) view.findViewById(android.R.id.text2);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.title.setText(action.getTitle());
        holder.summary.setText(action.getSummary());

        return view;
    }

    private static class ViewHolder {
        TextView title;
        TextView summary;
    }
}
