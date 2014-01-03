
package com.l2a.storeelsemain.widget;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.l2a.storeelsemain.R;
import com.l2a.storeelsemain.widget.ActionAdapter.Action;

public class ActionAdapter extends ArrayAdapter<Action> {
    private final LayoutInflater mInflater;

    public interface Action {

        int getIcon();

        CharSequence getTitle();

        CharSequence getSummary();
        
        void execute();

    }

    public ActionAdapter(Context context) {
        super(context, R.layout.adapter_action, new ArrayList<Action>());

        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Action action = getItem(position);

        ViewHolder holder;
        if (null == view) {
            view = mInflater.inflate(R.layout.adapter_action, parent, false);
            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.icon);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.summary = (TextView) view.findViewById(R.id.summary);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        
        holder.icon.setImageResource(action.getIcon());
        holder.title.setText(action.getTitle());
        holder.summary.setText(action.getSummary());
        
        return view;
    }

    private static class ViewHolder {
        ImageView icon;
        TextView title;
        TextView summary;
    }
}
