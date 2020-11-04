package com.example.mynotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.HashSet;

public class MainActivity2 extends AppCompatActivity {

    EditText mainBody;
    Intent intent;
    int ID;

    public void save()
    {
        if(ID!=-1)
        {
            MainActivity.notes.set(ID, mainBody.getText().toString());
        }
        else
        {
            MainActivity.notes.add(mainBody.getText().toString());
        }
        MainActivity.arrayAdapter.notifyDataSetChanged();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.mynotes", MODE_PRIVATE);
        HashSet<String> set = new HashSet<>(MainActivity.notes);
        sharedPreferences.edit().putStringSet("notes", set).apply();
    }

    public void discard()
    {
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Leave note")
                .setMessage("Do you want to save unsaved changes?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        save();
                        finish();
                    }

                })
                .setNegativeButton("Discard", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        discard();
                        finish();
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black));
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.teal_700)));
//        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.black));

        mainBody = (EditText) this.findViewById(R.id.mainBody);
        intent = getIntent();
        ID = intent.getIntExtra("noteID", -1);

        if(ID!=-1)
        {
            mainBody.setText(MainActivity.notes.get(ID));
        }
    }
}