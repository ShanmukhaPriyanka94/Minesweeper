package com.example.priyanka.minesweeper;

/**
 * Created by priyanka on 17/10/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomGridAdaptor extends BaseAdapter{
    private Context context;
    private ArrayList<String> items = new ArrayList<String>();
    LayoutInflater inflater;

    public CustomGridAdaptor(Context context,ArrayList<String> items)
    {
        this.context=context;
        this.items= items;
        inflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view=inflater.inflate(R.layout.cell,null);
        }
        TextView tv=view.findViewById(R.id.textview);
        tv.setBackgroundResource(R.drawable.gridborder);

        tv.setText(items.get(i));

        return view;
    }
}
