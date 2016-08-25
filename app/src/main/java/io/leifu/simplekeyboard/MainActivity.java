package io.leifu.simplekeyboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mButton;
    private RelativeLayout mRelativeLayout;
    private CustomPinPad mCustomPinPad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        mEditText = (EditText) findViewById(R.id.editText);
        EditText anotherEditText = (EditText) findViewById(R.id.anotherEditText);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, mEditText.getText(), Toast.LENGTH_LONG).show();
            }
        });


        mCustomPinPad = new CustomPinPad(mRelativeLayout, mEditText, mButton, getApplicationContext());

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}
