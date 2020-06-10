package com.zachgarber.grocerylistdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zachgarber.grocerylistdemo.Activities.MainActivity;
import com.zachgarber.grocerylistdemo.Data.DataBaseHandler;
import com.zachgarber.grocerylistdemo.Model.Grocery;
import com.zachgarber.grocerylistdemo.UI.Adapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    DataBaseHandler db;
    RecyclerView recyclerView;
    List<Grocery> groceryList=new ArrayList<>();
    ArrayList<Grocery> listItems=new ArrayList<>();
    RecyclerView.Adapter recyclerViewAdapter;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText groceryQty;
    private EditText groceryItem;
    private Button saveButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createPopupDialogue();
            }
        });

        Button toMealsButton=findViewById(R.id.mealsButton);
        toMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListActivity.this, Meal_List.class);
                startActivity(intent);
            }
        });


        db =new DataBaseHandler(this);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryList=new ArrayList<>();
        listItems=new ArrayList<>();


        groceryList=db.getAllGroceries();
        for (Grocery c: groceryList){
            Grocery grocery=new Grocery();
            grocery.setGroceryItem(c.getGroceryItem());
            grocery.setQuantity("Qty:" + c.getQuantity());
            grocery.setId(c.getId());
            grocery.setDateAdded("Added on:"+ c.getDateAdded());

            listItems.add(grocery);
        }
        recyclerViewAdapter=new Adapter(this, listItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

    }

    private void saveGroceryToDB(View view){

        Grocery grocery=new Grocery();
        String newGrocery=groceryItem.getText().toString();
        String newGroceryQuantity=groceryQty.getText().toString();

        grocery.setQuantity(newGroceryQuantity);
        grocery.setGroceryItem(newGrocery);

        //saves to DB
        db.addGrocery(grocery);

        Snackbar.make(view, "Item Saved!", Snackbar.LENGTH_LONG).show();

        Log.d("Item Added ID: ", String.valueOf(db.getGroceriesCount()));
    }

    private void createPopupDialogue(){
        dialogBuilder=new AlertDialog.Builder(this);
        View v= getLayoutInflater().inflate(R.layout.pop_up, null);

        groceryItem=v.findViewById(R.id.groceryItem);
        groceryQty=v.findViewById(R.id.groceryQty);
        saveButton=v.findViewById(R.id.saveButton);

        dialogBuilder.setView(v);
        dialog=dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!groceryItem.getText().toString().isEmpty()){
                    saveGroceryToDB(v);
                    recyclerViewAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }


            }
        });

    }

}
