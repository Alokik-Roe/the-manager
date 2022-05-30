package com.bringtheaction.manager.bottomNavigationComponent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bringtheaction.manager.R;
import com.bringtheaction.manager.appData.Projects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class profile extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView projects, tasks, notes;

        projects = view.findViewById(R.id.txt1);
        tasks = view.findViewById(R.id.txt2);
        notes = view.findViewById(R.id.txt3);


        final int[] p = {0};
        final int[] t = {0};
        final int[] n = {0};
        projects.setText(String.valueOf(p[0]) + " Projects");
        tasks.setText(String.valueOf(t[0]) + " Tasks");
        notes.setText(String.valueOf(n[0]) + " Notes");

        //TODO: Project

        FirebaseDatabase.getInstance().getReference().child("Projects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot i : snapshot.getChildren()) {
                        p[0]++;
                        projects.setText(String.valueOf(p[0]) + " Projects");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot i : snapshot.getChildren()) {
                        t[0]++;
                        tasks.setText(String.valueOf(t[0]) + " Tasks");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot i : snapshot.getChildren()) {
                        n[0]++;
                        Log.i("NOTES", String.valueOf(n[0]));
                        notes.setText(String.valueOf(n[0]) + " Notes");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;

    }
}