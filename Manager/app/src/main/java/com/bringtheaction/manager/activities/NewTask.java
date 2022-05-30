package com.bringtheaction.manager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bringtheaction.manager.R;
import com.bringtheaction.manager.appData.Projects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class NewTask extends AppCompatActivity{

    // ViewModel

    // Internal Components
    ImageButton back, attach, delete;
    EditText taskTitle, taskDescription;
    TextView taskImageName, mem1, mem2, mem3, mem4, mem5;
    ImageView tasKImage, Imem1, Imem2, Imem3, Imem4, Imem5;
    Button dateSelector, projectSelector,TimeSelector, add, AddMember,EditMember;
    ListView projectList;

    // Time var
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int h = calendar.get(calendar.HOUR_OF_DAY);
    int m = calendar.get(calendar.MINUTE);
    String meridiem = "AM";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);


        assignId();
        assignOnClickListeners();

    }

    private void assignId() {
        back = findViewById(R.id.backBtn);

        taskTitle = findViewById(R.id.taskTitle);
        taskDescription = findViewById(R.id.taskDescription);

        tasKImage = findViewById(R.id.taskImage);
        taskImageName = findViewById(R.id.taskImageName);
        attach = findViewById(R.id.attach);
        delete = findViewById(R.id.deleteAttach);

        //TODO: the day I find the way to have a functionality to add or attach images to your tasks
        //TODO: that day work on EDITTASK, NEWTASK, TASKS --> classes
        // and use delete and attach ID'S

        dateSelector = findViewById(R.id.dateSelector);
        TimeSelector = findViewById(R.id.TimeSelector);
        projectSelector = findViewById(R.id.projectSelect);
        projectList = findViewById(R.id.listProject);

        AddMember = findViewById(R.id.AddMember);
        EditMember = findViewById(R.id.EditMember);

        Imem1 = findViewById(R.id.mem1);
        Imem2 = findViewById(R.id.mem2);
        Imem3 = findViewById(R.id.mem3);
        Imem4 = findViewById(R.id.mem4);
        Imem5 = findViewById(R.id.mem5);

        Imem1.setClipToOutline(true);
        Imem2.setClipToOutline(true);
        Imem3.setClipToOutline(true);
        Imem4.setClipToOutline(true);
        Imem5.setClipToOutline(true);

        add = findViewById(R.id.add);
    }

    private void assignOnClickListeners() {

        back.setOnClickListener(view -> finish());

        dateSelector.setOnClickListener(view -> {
            datePickerDialog = new DatePickerDialog(NewTask.this,
                    (datePicker, y, m, d) -> {
                        year =y; month = m; day = d;
                        dateSelector.setText(y + "-" + m + "-" +d);
                    },year, month,day);
            datePickerDialog.updateDate(year, month, day);
            datePickerDialog.show();

        });

        TimeSelector.setOnClickListener(view -> {

            timePickerDialog = new TimePickerDialog(NewTask.this, (timePicker, hourOfDay, minute) -> {
                h = hourOfDay;
                m = minute;

                if (hourOfDay > 12 & hourOfDay < 24) {
                    meridiem = "PM";
                    h = h - 12;
                }
                else {
                    switch (hourOfDay) {
                        case 12:
                            meridiem = "PM";
                            break;
                        case 24:
                            h = h-12;
                            meridiem = "AM";
                            break;
                        default:
                            meridiem = "AM";
                            break;
                    }
                }



                TimeSelector.setText(h + ":" + minute + " " + meridiem);

            },h,m,false);

            timePickerDialog.updateTime(h,m);
            timePickerDialog.show();

        });

        AddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewTask.this, "Feature yet to Come", Toast.LENGTH_SHORT).show();

            }
        });
        List<String> project = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.project_list, project);
        projectList.setAdapter(adapter);
        projectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                projectSelector.setText(adapter.getItem(i).toString());
                projectSelector.setVisibility(View.VISIBLE);
                projectList.setVisibility(View.GONE);
            }
        });

        projectSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //TODO: Project

                FirebaseDatabase.getInstance().getReference().child("Projects").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        project.clear();
                        if (snapshot.exists()){
                            for (DataSnapshot i : snapshot.getChildren()) {

                                String s = i.getValue(Projects.class).getProjectName();
                                project.add(s);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                projectSelector.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                projectList.setVisibility(View.VISIBLE);
            }
        });
        // TODO : fix the spinner show






        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = taskTitle.getText().toString();
                String description = taskDescription.getText().toString();
                String date = "2021-10-13";
                String time = TimeSelector.getText().toString();
                String project = projectSelector.getText().toString();


                if (TextUtils.isEmpty(taskTitle.getText()) || (dateSelector.getText().toString()).equals("Due Date")) {
                    Toast.makeText(NewTask.this, "Please Enter Title and Due Date!!", Toast.LENGTH_SHORT).show();
                }
                else {



                    HashMap<String, Object> m = new HashMap<String, Object>();
                    m.put("title", title);
                    m.put("description", description);
                    m.put("date", date);
                    m.put("time", time);
                    m.put("project", project);
                    m.put("complete", false);

                    //m.put("Members")

                    FirebaseDatabase.getInstance().getReference().child("Tasks").push().setValue(m);


                    Toast.makeText(NewTask.this, "New Task succesfully added!!", Toast.LENGTH_SHORT).show();

                    finish();

                }

            }
        });
    }


}