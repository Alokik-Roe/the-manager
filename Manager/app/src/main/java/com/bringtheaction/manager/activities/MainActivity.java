package com.bringtheaction.manager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bringtheaction.manager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView  = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHost);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu();
            }
        });





    }

    private void menu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_add , viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        Button addTask, addNote, addProject;

        addTask = dialogView.findViewById(R.id.task);
        addNote = dialogView.findViewById(R.id.note);
        addProject = dialogView.findViewById(R.id.projectAdd);


        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent task = new Intent(MainActivity.this, NewTask.class);
                alertDialog.dismiss();
                startActivity(task);

            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent note = new Intent(MainActivity.this, QuickNote.class);
                alertDialog.dismiss();
                startActivity(note);
            }
        });

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button cancel, save;

                cancel = dialogView.findViewById(R.id.cancel);
                save = dialogView.findViewById(R.id.saveProject);

                EditText Name  = dialogView.findViewById(R.id.projectName);

                cancel.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                Name.setVisibility(View.VISIBLE);

                addNote.setVisibility(View.GONE);
                addTask.setVisibility(View.GONE);
                addProject.setVisibility(View.GONE);


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<String, Object> m = new HashMap<>();
                        m.put("projectName", Name.getText().toString());
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();

                        myRef.child("Projects").push().setValue(m);
                        Toast.makeText(MainActivity.this, "New Project has been created", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });



            }
        });








        alertDialog.show();


    }
}