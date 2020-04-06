package com.ubermenschalone.simplerecyclerviewadapterandsavedata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.ubermenschalone.simplerecyclerviewadapterandsavedata.R;
import com.ubermenschalone.simplerecyclerviewadapterandsavedata.data.TinyDB;
import com.ubermenschalone.simplerecyclerviewadapterandsavedata.model.Person;

import java.util.ArrayList;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {


    private ArrayList<Person> mPersons;
    private Context mContext;
    private TinyDB tinyDB;

    public SimpleAdapter(Context context, ArrayList<Person> persons) {
        mContext = context;
        mPersons = persons;
        tinyDB = new TinyDB (context);
    }

    @Override
    public SimpleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_adapter, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SimpleAdapter.ViewHolder viewHolder, final int position) {
        final Person person = mPersons.get(position);

        TextView textView = viewHolder.textViewName;
        textView.setText(person.getName());

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewName;
        public ImageView imageViewDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
            imageViewDelete.setOnClickListener(this);



        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {

                mPersons.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mPersons.size());

                //Save data
                ArrayList<Object> playerObjects = new ArrayList<>();
                for(Person a : mPersons){
                    playerObjects.add((Object)a);
                }

                tinyDB.putListObject("arrayPersons", playerObjects);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mPersons.size();
    }
}