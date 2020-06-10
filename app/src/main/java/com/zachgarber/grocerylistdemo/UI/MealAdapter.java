package com.zachgarber.grocerylistdemo.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.zachgarber.grocerylistdemo.Model.Meals;
import com.zachgarber.grocerylistdemo.R;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    List<Meals> mealList=new ArrayList<>();
    private Context context;

    public MealAdapter(List<Meals> mealList, Context context) {
        this.mealList = mealList;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        TextView mealItem;
        TextView ingredients;
        TextView cookingNotes;
        Button editButton;
        Button deleteButton;



        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context=ctx;
            mealItem=itemView.findViewById(R.id.mealName);
            ingredients=itemView.findViewById(R.id.ingredients);
            cookingNotes= itemView.findViewById(R.id.cookingNotes);
            editButton= itemView.findViewById(R.id.editButton);
            deleteButton=itemView.findViewById(R.id.deleteButton);

        }

        @Override
        public void onClick(View v) {

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_meal__list, viewGroup, false);
        ViewHolder vh=new ViewHolder(v,context);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Meals meal=mealList.get(i);
        viewHolder.mealItem.setText(meal.getMeal());
        viewHolder.ingredients.setText(meal.getIngredients());
        viewHolder.cookingNotes.setText(meal.getCookingNote());



    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }
}
