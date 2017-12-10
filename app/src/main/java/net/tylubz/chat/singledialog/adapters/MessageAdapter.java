package net.tylubz.chat.singledialog.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.tylubz.chat.R;
import net.tylubz.chat.singledialog.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Lebedev
 */

public class MessageAdapter extends ArrayAdapter<Message> implements View.OnClickListener {

    private List<Message> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView userName;
        TextView message;
        TextView time;
    }

    public MessageAdapter(List<Message> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Message message = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.userName);
            viewHolder.message = (TextView) convertView.findViewById(R.id.message);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.userName.setText(message.getUserName());
        viewHolder.message.setText(message.getMessage());
        viewHolder.time.setText(message.getTime());
        // Return the completed view to render on screen
        return convertView;
    }
}
