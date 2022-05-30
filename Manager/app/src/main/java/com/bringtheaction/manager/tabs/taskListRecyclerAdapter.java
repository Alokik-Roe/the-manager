package com.bringtheaction.manager.tabs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bringtheaction.manager.R;
import com.bringtheaction.manager.appData.Tasks;

import java.util.ArrayList;
import java.util.List;

public class taskListRecyclerAdapter extends RecyclerView.Adapter<taskListRecyclerAdapter.taskView>{

    //
    private List<Tasks> taskList = new ArrayList<>();
    private Tasks task;

    //
    private OnItemClickListener listener;

    @NonNull
    @Override
    public taskView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View my = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list, parent, false);
        return new taskView(my);
    }

    @Override
    public void onBindViewHolder(@NonNull taskView holder, int position) {
        holder.taskName.setText(taskList.get(position).getTitle());
        holder.taskTime.setText(taskList.get(position).getTime());
        boolean mark = taskList.get(position).getComplete();
        holder.taskCheck.setChecked(mark);
        holder.taskCheck2.setChecked(mark);
        
        holder.taskCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Feature yet To Come, CIAO!", Toast.LENGTH_SHORT).show();
                listener.OnItemClick(holder.getAdapterPosition(), holder.taskCheck.isChecked());
                holder.taskCheck2.setChecked(holder.taskCheck.isChecked());
            }
        });
        
        
    }



    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setData(List<Tasks> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public Tasks getSelectedData(int pos) {
        task = taskList.get(pos);
        return task;
    }


    public interface OnItemClickListener{
        void OnItemClick(int pos, Boolean markComplete);
    }

    public void setOnItemClick(OnItemClickListener listener) {
        this.listener = listener;
    }


    public class taskView extends RecyclerView.ViewHolder {
        TextView taskName, taskTime;
        CheckBox taskCheck, taskCheck2;
        public taskView(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.TaskName);
            taskTime = itemView.findViewById(R.id.TaskTime);
            taskCheck = itemView.findViewById(R.id.taskCheck);
            taskCheck2 = itemView.findViewById(R.id.taskCheck2);
        }
    }



}
