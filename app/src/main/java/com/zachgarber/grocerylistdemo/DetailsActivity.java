package com.zachgarber.grocerylistdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    public TextView meal;
    public TextView quantity;
    public TextView date;
    public int groceryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        meal=findViewById(R.id.meal);
        quantity=findViewById(R.id.quantity);
        date=findViewById(R.id.date);

        Bundle bundle=getIntent().getExtras();

        if (bundle !=null){
            meal.setText(bundle.getString("name"));
            quantity.setText(bundle.getString("qty"));
            date.setText(bundle.getString("date"));
            groceryId=bundle.getInt("id");

        }

            }
        }



