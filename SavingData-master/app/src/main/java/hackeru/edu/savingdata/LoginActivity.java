package hackeru.edu.savingdata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private EditText etUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = (EditText) findViewById(R.id.etUserName);
        prefs = getSharedPreferences("Notes", MODE_PRIVATE);
    }

    public void login(View view) {
        String userName = etUserName.getText().toString();
        if (userName.length() < 3) {
            etUserName.setError("Must be at 3 characters");
            return;
        }
        //save the userName.
        //get a reference to the sharedPreferences Editor:
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("UserName", userName);
        editor.commit();

        //goto Main Activity: Via Intent
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
