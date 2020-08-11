package hackeru.edu.savingdata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {
    private EditText etNote;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private SharedPreferences prefs;

    private int noteCounter = 1;
    private int currentNote = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        etNote = (EditText) findViewById(R.id.etNote);

        etNote.addTextChangedListener(this);
        fab.setOnClickListener(this);

        setSupportActionBar(toolbar);
        prefs = getSharedPreferences("Notes", MODE_PRIVATE);

        String userName = prefs.getString("UserName", null);

        if (userName == null) {
            //Not Logged in yet.
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            //We already have a user:
            Toast.makeText(this, "Hi There, " + userName, Toast.LENGTH_SHORT).show();
        }
        //if we have a userName in the prefs->
        //say Hi, +"User Name"
        //else-> send the user to the login Activity.
        /*
        * Intent intent = new Intent(this , LoginActivity.class);
        * startActivity(intent);
        * */
        loadNoteCounter();
        loadNote();
    }

    private void loadNoteCounter() {
        currentNote = prefs.getInt("CurrentNote", 1);
        noteCounter = prefs.getInt("NoteCounter", 1);
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        saveNote();
    }




    private void loadNote() {
        String note = prefs.getString("note" + currentNote, ""/*if no data yet, give me an Empty String*/);
        etNote.setText(note);
    }


    private void saveNote() {
        //xml Notes.xml
        //1) get a reference to sharedPreferences:  -> Notes.xml
        //   SharedPreferences prefs = getSharedPreferences("Notes", MODE_PRIVATE);

        //2) use the prefs Editor to write some data in key value pairs
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("note" + currentNote, etNote.getText().toString());

        //3) commit the changes / Or apply them
        //editor.commit(); //immediately save the changes. Blocking code.
        editor.apply(); //eventually save the changes. Non Blocking code.
        /*
        * Notes.xml
        * <item name = "note1">
        *   Hi, mom. I'm writing a note.
        * </item>
        *
        * <item name = "note2">
        *   Hi, mom. I Quit my Job Don't call me -> I'll call you.
        * </item>
        *
        * <item name = "UserName">
        *   Hi, mom. I Quit my Job Don't call me -> I'll call you.
        * </item>
        *
        * */
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_back:
                previousNote();
                return true;
            case R.id.action_next:
                nextNote();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * New Note Clicked
     */
    @Override
    public void onClick(View v) {
        noteCounter++;
        currentNote = noteCounter;
        etNote.setText("");
        saveNoteCounter();
    }

    private void saveNoteCounter() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("NoteCounter", noteCounter);
        editor.putInt("CurrentNote", currentNote);
        editor.commit();
    }

    private void previousNote() {
        currentNote--;
        if (currentNote < 1) {
            currentNote = 1;
            Toast.makeText(this, "The First Note", Toast.LENGTH_SHORT).show();
        }
        loadNote();
        saveNoteCounter();
    }

    private void nextNote() {
        currentNote++;
        if (currentNote > noteCounter){
            currentNote = noteCounter;
            Toast.makeText(this, "Last Note", Toast.LENGTH_SHORT).show();
        }
        loadNote();
        saveNoteCounter();
    }

}
