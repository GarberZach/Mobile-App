package com.zachgarber.grocerylistdemo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.zachgarber.grocerylistdemo.Data.DataBaseHandler;
import com.zachgarber.grocerylistdemo.ListActivity;
import com.zachgarber.grocerylistdemo.Model.Grocery;
import com.zachgarber.grocerylistdemo.R;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText groceryQty;
    private EditText groceryItem;
    private Button saveButton;
    private DataBaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DataBaseHandler(this);
        byPassAddScreen();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialogue();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                }
                Intent intent=new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);

            }
        });

    }

    public void byPassAddScreen(){
        if (db.getGroceriesCount()>0){
            Intent intent=new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
            finish();
        }
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
}
