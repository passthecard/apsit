package com.example.passthecard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class sdrvadapter extends RecyclerView.Adapter<sdrvadapter.ViewHolder> {
    private Context context;
    private ArrayList<studentdetailsmodel> studentdetailsmodelsarraylist;
    private Recyclerviewlistner  listner;

    public sdrvadapter(Context context, ArrayList<studentdetailsmodel> studentdetailsmodels, Recyclerviewlistner listner) {
        this.context = context;
        this.listner=listner;
        this.studentdetailsmodelsarraylist=studentdetailsmodels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View studentview= LayoutInflater.from(parent.getContext()).inflate(R.layout.studentdetails,parent,false);

        return new ViewHolder(studentview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.firstname.setText(studentdetailsmodelsarraylist.get(position).getFirstname());
        holder.lastname.setText(studentdetailsmodelsarraylist.get(position).getLastname());
        holder.moodleid.setText(studentdetailsmodelsarraylist.get(position).getMoodleid());
        holder.srno.setText(studentdetailsmodelsarraylist.get(position).getSrno());


    }

    @Override
    public int getItemCount() {
        return studentdetailsmodelsarraylist.size();
    }

    public interface Recyclerviewlistner{
        void onClick(View v, int position);



    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView firstname;
        TextView lastname;
        TextView moodleid;
        TextView srno;
        public ViewHolder(@NonNull View itemView) {


            super(itemView);
            firstname=itemView.findViewById(R.id.firstnamerv);
            lastname=itemView.findViewById(R.id.lastnamerv);
            moodleid= itemView.findViewById(R.id.moodleidrv);
            srno=itemView.findViewById(R.id.srnorv);

            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View view) {
            listner.onClick(itemView,getAdapterPosition());
            Intent intent= new Intent(context,flagthem.class);


        }
    }
}
