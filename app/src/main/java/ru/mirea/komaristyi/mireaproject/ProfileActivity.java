package ru.mirea.komaristyi.mireaproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    String name;
    String mail;
    String vkid;

    public ProfileActivity(){
        super(R.layout.activity_profile);
    }
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Button btnSave = findViewById(R.id.buttonSave);
        EditText editName = findViewById(R.id.editTextName);
        EditText editVKid = findViewById(R.id.editTextVK);
        EditText editMail = findViewById(R.id.editTextMail);

        sharedPref = getSharedPreferences("MIREA_settings", Context.MODE_PRIVATE);

        editName.setText(sharedPref.getString("NAME", "-"));
        editVKid.setText(sharedPref.getString("VKID", "-"));
        editMail.setText(sharedPref.getString("MAIL", "-"));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref = getSharedPreferences("MIREA_settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                name = editName.getText().toString();
                vkid = editVKid.getText().toString();
                mail = editMail.getText().toString();

                editor.putString("NAME", String.valueOf(name));
                editor.putString("MAIL", String.valueOf(vkid));
                editor.putString("VKID", String.valueOf(mail));

                editor.apply();

                Log.d(TAG, sharedPref.getString("NAME", "no inf"));
                Log.d(TAG, sharedPref.getString("MAIL", "no inf"));
                Log.d(TAG, sharedPref.getString("VKID", "no inf"));
            }
        });
    }
    public void onClickHomeActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}