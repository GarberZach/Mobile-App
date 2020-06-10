package com.zachgarber.grocerylistdemo.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zachgarber.grocerylistdemo.Activities.MainActivity;
import com.zachgarber.grocerylistdemo.Data.DataBaseHandler;
import com.zachgarber.grocerylistdemo.DetailsActivity;
import com.zachgarber.grocerylistdemo.Model.Grocery;
import com.zachgarber.grocerylistdemo.R;

import org.w3c.dom.Text;

import java.util.List;

import static android.content.ContentValues.TAG;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    private List<Grocery> groceryItems;
   private AlertDialog.Builder confirmationDialog;
   private AlertDialog alertDialog;
   private LayoutInflater inflator;

    public Adapter(Context context, List<Grocery> groceryItems) {
        this.context = context;
        this.groceryItems = groceryItems;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        TextView name;
        TextView Qty;
        TextView dateAdded;
        Button editButton;
        Button deleteButton;
        int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context=ctx;

            name= itemView.findViewById(R.id.title);
            Qty=itemView.findViewById(R.id.info);
            dateAdded=itemView.findViewById(R.id.dateAdded);
            editButton=itemView.findViewById(R.id.editButton);
            deleteButton=itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //This is the click listener for the entire card itself

                    int position =getAdapterPosition();

                    Grocery grocery=groceryItems.get(position);
                    Intent intent =new Intent(context, DetailsActivity.class);
                    intent.putExtra("name",grocery.getGroceryItem());
                    intent.putExtra("qty",grocery.getQuantity());
                    intent.putExtra("id",grocery.getId());
                    intent.putExtra("date",grocery.getDateAdded());
                    context.startActivity(intent);



                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.editButton:
                    int mPosition=getAdapterPosition();
                    Grocery mGrocery =groceryItems.get(mPosition);
                    editItem(mGrocery);

                    break;
                case R.id.deleteButton:
                    int position=getAdapterPosition();
                   Grocery grocery =groceryItems.get(position);
                    deleteItem(grocery.getId());
                    break;
         }
        }
        public void deleteItem(final int id){
///CREATE ALERT Dialog
            confirmationDialog=new AlertDialog.Builder(context);
            inflator=LayoutInflater.from(context);
            View view = inflator.inflate(R.layout.confirmation_dialog,null);

            Button noButton=view.findViewById(R.id.noButton);
            Button yesButton=view.findViewById(R.id.yesButton);

            confirmationDialog.setView(view);

            alertDialog=confirmationDialog.create();
            alertDialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                alertDialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete from database
                    DataBaseHandler db = new DataBaseHandler(context);
                    db.deleteGrocery(id);
                    groceryItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    alertDialog.dismiss();

                }
            });

        }


        public void editItem(final Grocery grocery){

            confirmationDialog=new AlertDialog.Builder(context);
            inflator=LayoutInflater.from(context);
            final View view = inflator.inflate(R.layout.pop_up,null);

            final EditText groceryItem= view.findViewById(R.id.groceryItem);
            final EditText groceryQuantity=view.findViewById(R.id.groceryQty);
            Button saveButton =view.findViewById(R.id.saveButton);

            confirmationDialog.setView(view);
            alertDialog=confirmationDialog.create();
            alertDialog.show();
            
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseHandler db=new DataBaseHandler(context);

                    grocery.setGroceryItem(groceryItem.getText().toString());
                    grocery.setQuantity("Qty: "+groceryQuantity.getText().toString());

                    if (!groceryItem.getText().toString().isEmpty() && !groceryQuantity.getText().toString().isEmpty()){
                        db.updateGrocery(grocery);
                        notifyItemChanged(getAdapterPosition(),grocery);
                    }
                    else Snackbar.make(view, "ADD NAME AND QUANTITY THEN TRY AGAIN",Snackbar.LENGTH_LONG).show();

                }
            });







        }
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row,viewGroup, false);

        return new ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder viewHolder, int i) {

        //BINDS DATA AND THE VIEWS

        Grocery grocery=groceryItems.get(i);
        viewHolder.name.setText(grocery.getGroceryItem());
        viewHolder.Qty.setText(grocery.getQuantity());
        if (viewHolder.Qty.getText().toString().isEmpty()){
            viewHolder.Qty.setText(" ");
        }
    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }
}
