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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class notes extends Fragment {
    //
    RecyclerSwipeHelper recyclerSwipeHelper;

    // Elemnets in XML
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    ConstraintLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView =  inflater.inflate(R.layout.fragment_notes, container, false);
        recyclerView = myView.findViewById(R.id.noteList);
        layout = myView.findViewById(R.id.noteLayout);

        CreateList();

        swipeHelper();
        Watcher();


        return myView;
    }


    private void swipeHelper() {
        recyclerSwipeHelper = new RecyclerSwipeHelper(getResources().getColor(R.color.color3), getResources().getColor(R.color.color1), R.drawable.ic_edit, R.drawable.ic_trash, getContext()){
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int pos = viewHolder.getAdapterPosition();
                Notes notes = noteAdapter.getSelectedData(pos);

                //calling the notifyItemChanged() method is absolutely essential to redraw the RecyclerView item and remove the icons we had drawn.
                noteAdapter.notifyItemChanged(viewHolder.getAdapterPosition());

                if (direction == ItemTouchHelper.LEFT){

                    //handle left swipe

                    FirebaseDatabase.getInstance().getReference().child("Notes").child(notes.getKey()).removeValue();
                    noteAdapter.notifyDataSetChanged();


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
        List<Notes> notes = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    notes.clear();
                    for (DataSnapshot i : snapshot.getChildren()) {
                        Notes notes1 = i.getValue(Notes.class);
                        notes1.setKey(i.getKey());
                        Log.i("KEY", notes1.getKey());
                        notes.add(notes1);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        noteAdapter.setNotesList(notes);

    }



    private void CreateList() {
        noteAdapter = new NoteAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(noteAdapter);
    }

    // Recycler view Adapter
    public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.viewHolder> {

        private List<Notes> notesList = new ArrayList<>();
        private Notes note;



        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list, parent, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NoteAdapter.viewHolder holder, int position) {

            holder.note.setText(notesList.get(position).getDescription());
            int noteColor = notesList.get(position).getColor();

            switch (noteColor) {
                case 1:
                    holder.headLine.setBackgroundColor(getResources().getColor(R.color.radio1));
                    break;
                case 2:
                    holder.headLine.setBackgroundColor(getResources().getColor(R.color.radio2));
                    break;
                case 3:
                    holder.headLine.setBackgroundColor(getResources().getColor(R.color.radio3));
                    break;
                case 4:
                    holder.headLine.setBackgroundColor(getResources().getColor(R.color.radio4));
                    break;
                case 5:
                    holder.headLine.setBackgroundColor(getResources().getColor(R.color.radio5));
                    break;
            }


        }

        @Override
        public int getItemCount() {
            return notesList.size();
        }

        public void setNotesList(List<Notes> notesList) {
            this.notesList = notesList;
            notifyDataSetChanged();
        }

        public Notes getSelectedData(int pos) {
            note = notesList.get(pos);
            return note;
        }



        public class viewHolder extends RecyclerView.ViewHolder {

            TextView note;
            View headLine;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                note = itemView.findViewById(R.id.nameProject);
                headLine = itemView.findViewById(R.id.head);
            }
        }
    }



}
