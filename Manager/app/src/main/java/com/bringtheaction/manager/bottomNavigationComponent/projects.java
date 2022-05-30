package com.bringtheaction.manager.bottomNavigationComponent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bringtheaction.manager.R;
import com.bringtheaction.manager.RecyclerSwipeHelper;
import com.bringtheaction.manager.appData.Notes;
import com.bringtheaction.manager.appData.Projects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class projects extends Fragment {
    RecyclerSwipeHelper recyclerSwipeHelper;

    // Elemnets in XML
    RecyclerView recyclerView;
    projectAdapter projectAdapter;
    ConstraintLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        recyclerView = view.findViewById(R.id.projectList);
        CreateList();

        Watcher();
        swipeHelper();

        return view;
    }
    private void swipeHelper() {
        recyclerSwipeHelper = new RecyclerSwipeHelper(getResources().getColor(R.color.color3), getResources().getColor(R.color.color1), R.drawable.ic_edit, R.drawable.ic_trash, getContext()){
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int pos = viewHolder.getAdapterPosition();
                Projects projects = projectAdapter.getSelectedData(pos);

                //calling the notifyItemChanged() method is absolutely essential to redraw the RecyclerView item and remove the icons we had drawn.
                projectAdapter.notifyItemChanged(viewHolder.getAdapterPosition());

                if (direction == ItemTouchHelper.LEFT){
                    //handle left swipe

                    FirebaseDatabase.getInstance().getReference().child("Projects").child(projects.getKey()).removeValue();
                    projectAdapter.notifyDataSetChanged();


                }

                else{

                    // TODO:EDIT
                    Toast.makeText(getActivity(), "Feature yet to Come", Toast.LENGTH_SHORT).show();

//                    //handle right swipe
//                    Intent EditNote = new Intent(getContext(), AddNote.class);
//                    EditNote.putExtra("noteToEdit", notes);
//                    startActivity(EditNote);

                }
            }
        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(recyclerSwipeHelper);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }


    private void Watcher() {
        List<Projects> project = new ArrayList<>();


        //TODO: Project

        FirebaseDatabase.getInstance().getReference().child("Projects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                project.clear();
                if (snapshot.exists()){
                    for (DataSnapshot i : snapshot.getChildren()) {

                        Projects projects = i.getValue(Projects.class);
                        projects.setKey(i.getKey());
                        project.add(projects);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        projectAdapter.setProjectsList(project);

    }













    private void CreateList() {
        projectAdapter = new projectAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(projectAdapter);
    }


    public class projectAdapter extends RecyclerView.Adapter<projectAdapter.viewHolder> {

        private List<Projects> projectsList = new ArrayList<>();
        private Projects Projects;

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list, parent, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {
            holder.note.setText(projectsList.get(position).getProjectName());
        }

        @Override
        public int getItemCount() {
            return projectsList.size();
        }

        public void setProjectsList(List<Projects> projectsList) {
            this.projectsList = projectsList;
            notifyDataSetChanged();
        }

        public Projects getSelectedData(int pos) {
            Projects = projectsList.get(pos);
            return Projects;
        }



        public class viewHolder extends RecyclerView.ViewHolder {

            TextView note;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                note = itemView.findViewById(R.id.nameProject);
            }
        }
    }

}