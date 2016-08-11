package io.leifu.simplekeyboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private SimpleIME mSimpleIME;
    private EditText mEditText;
    private Button mButton;
    private RelativeLayout mRelativeLayout;
    private RelativeLayout mKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

//        mSimpleIME = new SimpleIME(getApplicationContext(), mRelativeLayout);
//        mSimpleIME.registerEditText(R.id.editText);

        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mKeyboard = (RelativeLayout) vi.inflate(R.layout.view_keyboard, null).findViewById(R.id.custom_keyboard);

        RelativeLayout.LayoutParams keyboardParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        keyboardParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, mKeyboard.getId());
        mKeyboard.setLayoutParams(keyboardParams);
        ((ViewGroup) mRelativeLayout).addView(mKeyboard);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}
