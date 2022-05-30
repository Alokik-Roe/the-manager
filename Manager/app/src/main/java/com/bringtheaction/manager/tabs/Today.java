package com.bringtheaction.manager.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bringtheaction.manager.R;
import com.bringtheaction.manager.RecyclerSwipeHelper;
import com.bringtheaction.manager.appData.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Today extends Fragment {

    RecyclerSwipeHelper recyclerSwipeHelper;
    // Elements in XML
    RecyclerView recyclerView;
    taskListRecyclerAdapter taskListRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        recyclerView = view.findViewById(R.id.TodayList);
        CreateList();
        swipeHelper();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        watchToday(day);
        return view;
    }

    public void watchToday(int date) {
        List<Tasks> tasks = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot i : snapshot.getChildren()) {
                        Tasks tasks1 = i.getValue(Tasks.class);
                        tasks1.setKey(i.getKey());
                        if(tasks1.getDate() == String.valueOf(date)){
                            tasks.add(tasks1);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        taskListRecyclerAdapter.setData(tasks);
        taskListRecyclerAdapter.notifyDataSetChanged();
    }

    private void swipeHelper() {
        recyclerSwipeHelper = new RecyclerSwipeHelper(getResources().getColor(R.color.color3), getResources().getColor(R.color.color1), R.drawable.ic_edit, R.drawable.ic_trash, getContext()){
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int pos = viewHolder.getAdapterPosition();
                Tasks tasks = taskListRecyclerAdapter.getSelectedData(pos);

                //calling the notifyItemChanged() method is absolutely essential to redraw the RecyclerView item and remove the icons we had drawn.
                taskListRecyclerAdapter.notifyItemChanged(viewHolder.getAdapterPosition());

                if (direction == ItemTouchHelper.LEFT){
                    FirebaseDatabase.getInstance().getReference().child("Tasks").child(tasks.getKey()).removeValue();
                }

                else{
                    //TODO: EDIT
                    Toast.makeText(getActivity(), "Feature yet to Come", Toast.LENGTH_SHORT).show();

                    //handle right swipe
//                    Intent EditTask = new Intent(getContext(), EditTask.class);
//                    EditTask.putExtra("taskToEdit", tasks.getId());
//                    startActivity(EditTask);

                }
            }
        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(recyclerSwipeHelper);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void CreateList() {

        taskListRecyclerAdapter = new taskListRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(taskListRecyclerAdapter);

    }
}