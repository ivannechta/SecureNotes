package ru.nechta.securenotes.Interface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.nechta.securenotes.MessageRecord;
import ru.nechta.securenotes.R;

public class BoxAdapter extends BaseAdapter {
    private Context context;
    ArrayList<MessageRecord>Records;

    private final int []Ico={R.drawable.bell,R.drawable.info,R.drawable.error};

    public BoxAdapter(Context cnt, ArrayList<MessageRecord> R){
        context=cnt;
        Records=R;
    }
    @Override
    public int getCount() {
        return Records.size();
    }

    @Override
    public Object getItem(int position) {
        return Records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate (R.layout.onerecord, parent, false);

        ImageView img=rowView.findViewById(R.id.imageView);
        TextView Caption = rowView.findViewById(R.id.Caption);
        TextView Message = rowView.findViewById(R.id.Message);


        img.setImageResource(Ico[Records.get(position).Icon]);
        Caption.setText(Records.get(position).Caption);
        Message.setText(Records.get(position).Message);

        return rowView;
    }
}
