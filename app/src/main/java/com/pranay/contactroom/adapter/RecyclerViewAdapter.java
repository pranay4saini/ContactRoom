package com.pranay.contactroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pranay.contactroom.R;
import com.pranay.contactroom.model.Contact;

import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final OnContactClickListener contactClickListener;
    private final List<Contact> contactList;
    private final Context context;

    public RecyclerViewAdapter(List<Contact> contactList, Context context,OnContactClickListener onContactClickListener) {
        this.contactList = contactList;
        this.context = context;
        this.contactClickListener = onContactClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row,parent,false);
        return new ViewHolder(view,contactClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Contact contact = Objects.requireNonNull(contactList.get(position));
        holder.name.setText(contact.getName());
        holder.occupation.setText(contact.getOccupation());
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(contactList).size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnContactClickListener onContactClickListener;
            public TextView name;
            public TextView occupation;

        public ViewHolder(@NonNull View itemView, OnContactClickListener onContactClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.row_name_textview);
            occupation = itemView.findViewById(R.id.row_ocupation_textview);
            this.onContactClickListener = onContactClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onContactClickListener.onContactClick(getAdapterPosition());
        }
    }


    public interface OnContactClickListener {

        void onContactClick(int position);

    }
}
