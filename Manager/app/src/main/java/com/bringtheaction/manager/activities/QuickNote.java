package com.bringtheaction.manager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bringtheaction.manager.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class QuickNote extends AppCompatActivity {

    boolean edit = false;

    //Elements in XML
    ImageButton back;
    TextView noteheader;
    EditText noteDescription;
    RadioGroup radioGroup;
    Button addNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_note);
//        PassedNotes = getIntent().getParcelableExtra("noteToEdit");

        assignUIElements();
        assignOnClick();
//
//        if (PassedNotes != null) {
//            setPassedNotes();
//        }

    }

/*    private void setPassedNotes() {
        noteheader.setText("Edit Note");
        noteDescription.setText(PassedNotes.getNoteDescription());
        switch (PassedNotes.getNoteColor()) {
            case 1:
                radioGroup.check(R.id.RB1);
                break;
            case 2:
                radioGroup.check(R.id.RB2);
                break;
            case 3:
                radioGroup.check(R.id.RB3);
                break;
            case 4:
                radioGroup.check(R.id.RB4);
                break;
            case 5:
                radioGroup.check(R.id.RB5);
                break;
        }
        edit = true;
    }*/

    private void assignUIElements() {
        back = findViewById(R.id.backBtn1);
        noteheader = findViewById(R.id.noteHead);
        noteDescription = findViewById(R.id.noteDescription);
        radioGroup = findViewById(R.id.radioGroup);
        addNote = findViewById(R.id.addNote);
    }

    private void assignOnClick() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //
                int headColor;
                String note;

                // get color
                int selectedId = radioGroup.getCheckedRadioButtonId();
                switch (selectedId) {
                    case R.id.RB1:
                        headColor = 1;
                        break;
                    case R.id.RB2:
                        headColor = 2;
                        break;
                    case R.id.RB3:
                        headColor = 3;
                        break;
                    case R.id.RB4:
                        headColor = 4;
                        break;
                    case R.id.RB5:
                        headColor = 5;
                        break;
                    default:
                        headColor = 1;
                        break;
                }

                // get note
                note = noteDescription.getText().toString();

                if (TextUtils.isEmpty(noteDescription.getText())) {
                    Toast.makeText(QuickNote.this, "Please Enter Note Description", Toast.LENGTH_SHORT).show();
                }
                else {
                    HashMap<String, Object> m = new HashMap<String, Object>();
                    m.put("description", note);
                    m.put("color", headColor);

                    if (!edit) {
                        FirebaseDatabase.getInstance().getReference().child("Notes").push().setValue(m);
                        Toast.makeText(QuickNote.this, "Quick note successfully added", Toast.LENGTH_SHORT).show();
                    }
                    else{
                       // FirebaseDatabase.getInstance().getReference().child("Notes").child(String.valueOf(PassedNotes.getId())).setValue(m);
                        //Toast.makeText(AddNote.this, "Quick note successfully updated", Toast.LENGTH_SHORT).show();
                    }

                    finish();

                }





            }
        });


    }


}