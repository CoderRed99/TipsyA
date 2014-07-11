package com.app.dianna.tipsy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by dianna on 7/3/14.  following guidance from CodePath
 * https://github.com/thecodepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */

public class GuestsAdapter extends ArrayAdapter<Guest>
{           // View lookup cache
    private static class ViewHolder
    {
        TextView name;
        TextView total;
        TextView tip;
        TextView meal;
    }

    public GuestsAdapter(Context context, ArrayList<Guest> guests)
    {
        super(context, R.layout.guest_row_layout, guests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {       // Get the data item for this position
        Guest guest = getItem(position);

        // view lookup cache stored in tag
        ViewHolder holder;
        if (convertView == null)
        {       // Check if an existing view is being reused, otherwise inflate the view
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.guest_row_layout, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.tvName);
            holder.total = (TextView) convertView.findViewById(R.id.tvTotal);
            holder.tip = (TextView) convertView.findViewById(R.id.tvTip);
            holder.meal = (TextView) convertView.findViewById(R.id.tvMeal);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
                    // Populate the data into the template view using the data object
/*        holder.name.setText(guest.name);
        NumberFormat nfc = NumberFormat.getNumberInstance();
        holder.total.setText(nfc.format(guest.total));
        holder.tip.setText(nfc.format(guest.tip));
        holder.meal.setText(nfc.format(guest.meal));*/

            // Return the completed view to render on screen
        return convertView;
    }
}