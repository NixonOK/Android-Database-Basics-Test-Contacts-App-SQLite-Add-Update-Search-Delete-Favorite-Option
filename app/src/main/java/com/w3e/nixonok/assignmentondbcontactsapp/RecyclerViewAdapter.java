package com.w3e.nixonok.assignmentondbcontactsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by USER24 on 2/27/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    List<Contact> contacts;
    Context context;
    View view1;
    ViewHolder viewHolder1;


    public RecyclerViewAdapter(Context context,  List<Contact> contacts){

        this.contacts = contacts;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView contactFullName;
        public TextView contactPhone;

        public ViewHolder(View v){

            super(v);

            contactFullName = v.findViewById(R.id.textViewHead);
            contactPhone = v.findViewById(R.id.textViewDesc);
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        view1 = LayoutInflater.from(context).inflate(R.layout.item, parent,false);

        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position){

            holder.contactFullName.setText(contacts.get(position).get_first_name()+" "+contacts.get(position).get_last_name());
            holder.contactPhone.setText(contacts.get(position).get_phone_number());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), AddContactActivity.class);
                intent.putExtra("update", true);
                intent.putExtra("position", position);
                intent.putExtra("contact_to_be_updated", contacts.get(position));
                v.getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener (new View.OnLongClickListener() {
            @SuppressWarnings("rawtypes")
            public boolean onLongClick(final View view) {
                final CharSequence[] items = { "Yes", "No" };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Do you want to delete this contact?");
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        if(item == 0){
                            DatabaseHelper db = new DatabaseHelper(view.getContext());
                            db.deleteContact(contacts.get(position));

                            new AlertDialog.Builder(context)
                                    .setTitle("Deleted!")
                                    .setMessage(""+contacts.get(position).get_phone_number())
                                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            ((MainActivity) view.getContext()).onResume();
                                        }
                                    })
                                    .show();

                        }
                        }



                });

                AlertDialog alert = builder.create();

                alert.show();
                //do your stuff here
                return false;
            }
        });
    }

    @Override
    public int getItemCount(){

        return contacts.size();
    }
}