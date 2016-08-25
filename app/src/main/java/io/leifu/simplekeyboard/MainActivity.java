package io.leifu.simplekeyboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SimpleIME mSimpleIME;
    private EditText mEditText;
    private Button mButton;
    private RelativeLayout mRelativeLayout;
    private RelativeLayout mKeyboard;
    private List<Button> mButtons;
    private List<ImageButton> mImageButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

//        mSimpleIME = new SimpleIME(getApplicationContext(), mRelativeLayout);
//        mSimpleIME.registerEditText(R.id.editText);
        mEditText = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Confirm!", Toast.LENGTH_LONG).show();
            }
        });

        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mKeyboard = (RelativeLayout) vi.inflate(R.layout.view_keyboard, null).findViewById(R.id.custom_keyboard);

        RelativeLayout.LayoutParams keyboardParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        keyboardParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, mKeyboard.getId());
        mKeyboard.setLayoutParams(keyboardParams);
        mRelativeLayout.addView(mKeyboard);
        mButtons = new ArrayList<>();
        mButtons.add((Button) findViewById(R.id.pin_code_button_0));
        mButtons.add((Button) findViewById(R.id.pin_code_button_1));
        mButtons.add((Button) findViewById(R.id.pin_code_button_2));
        mButtons.add((Button) findViewById(R.id.pin_code_button_3));
        mButtons.add((Button) findViewById(R.id.pin_code_button_4));
        mButtons.add((Button) findViewById(R.id.pin_code_button_5));
        mButtons.add((Button) findViewById(R.id.pin_code_button_6));
        mButtons.add((Button) findViewById(R.id.pin_code_button_7));
        mButtons.add((Button) findViewById(R.id.pin_code_button_8));
        mButtons.add((Button) findViewById(R.id.pin_code_button_9));

        final Editable editable = mEditText.getText();
        final int cursorPosition = mEditText.getSelectionStart();
        for (final Button button : mButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editable.insert(cursorPosition, button.getText());
                    mEditText.setText(editable);
                    mEditText.setSelection(mEditText.getText().length());

                }
            });
        }
        mImageButtons = new ArrayList<>();
        mImageButtons.add((ImageButton) findViewById(R.id.pin_code_button_del));
        mImageButtons.add((ImageButton) findViewById(R.id.pin_code_button_done));
        for (final ImageButton imageButton : mImageButtons) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mCursorPosition = 3;
                    if (view.getId() == R.id.pin_code_button_del) {
                        if (editable != null) {
                            editable.delete(mCursorPosition - 1, mCursorPosition);
                        }
                    } else {
                        mButton.performClick();
                    }

                }
            });
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}
