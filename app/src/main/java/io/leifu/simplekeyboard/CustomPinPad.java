package io.leifu.simplekeyboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class CustomPinPad {

    private RelativeLayout mRelativeLayout;
    private RelativeLayout mKeyboard;
    private List<View> mKeys;
    private EditText mEditText;
    private Button mButton;
    private Context mContext;

    public CustomPinPad(RelativeLayout viewRoot, EditText editText, Button button, Context context) {
        mRelativeLayout = viewRoot;
        mEditText = editText;
        mButton = button;
        mContext = context;
        setPinPadView();
        registerEditText();
        setKeyMap(mRelativeLayout);
        assignKeyListener();
    }

    private void registerEditText() {
        mEditText.setOnFocusChangeListener(showKeyboardWhenInputFieldHasFocus());
        mEditText.setOnClickListener(showKeyboardWhenInputFieldClicked());
        mEditText.setOnKeyListener(backPressedWhenKeyboardIsVisible());
        mEditText.setOnTouchListener(systemKeyboardDisabled());
    }

    private void setPinPadView() {
        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mKeyboard = (RelativeLayout) vi.inflate(R.layout.view_keyboard, null).findViewById(R.id.custom_keyboard);

        RelativeLayout.LayoutParams keyboardParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        keyboardParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, mKeyboard.getId());
        mKeyboard.setLayoutParams(keyboardParams);
    }

    @NonNull
    private View.OnTouchListener systemKeyboardDisabled() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                EditText edittext = (EditText) view;
                int inputType = edittext.getInputType();
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(motionEvent); // Call native handler
                edittext.setInputType(inputType); // Restore input type
                edittext.setSelection(mEditText.getText().length());
                return true; // Consume touch event
            }
        };
    }

    @NonNull
    private View.OnKeyListener backPressedWhenKeyboardIsVisible() {
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && isCustomKeyboardVisible()) {
                    hideCustomKeyboard();
                    return true;
                }
                return false;
            }
        };
    }

    @NonNull
    private View.OnClickListener showKeyboardWhenInputFieldClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomKeyboard();
            }
        };
    }

    @NonNull
    private View.OnFocusChangeListener showKeyboardWhenInputFieldHasFocus() {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showCustomKeyboard();
                } else {
                    hideCustomKeyboard();
                }
            }
        };
    }

    private void assignKeyListener() {
        final Editable editable = mEditText.getText();
        for (final View button : mKeys) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int cursorPosition = mEditText.getSelectionStart();
                    switch (view.getId()) {
                        case R.id.pin_code_button_del:
                            if (editable != null && cursorPosition > 0) {
                                editable.delete(cursorPosition - 1, cursorPosition);
                            }
                            break;
                        case R.id.pin_code_button_done:
                            mButton.performClick();
                            break;
                        default:
                            editable.insert(cursorPosition, ((Button)button).getText());
                    }
                    mEditText.setText(editable);
                    mEditText.setSelection(mEditText.getText().length());

                }
            });
        }
    }

    private void setKeyMap(View view) {
        mRelativeLayout.addView(mKeyboard);
        mKeys = new ArrayList<>();
        mKeys.add(view.findViewById(R.id.pin_code_button_0));
        mKeys.add(view.findViewById(R.id.pin_code_button_1));
        mKeys.add(view.findViewById(R.id.pin_code_button_2));
        mKeys.add(view.findViewById(R.id.pin_code_button_3));
        mKeys.add(view.findViewById(R.id.pin_code_button_4));
        mKeys.add(view.findViewById(R.id.pin_code_button_5));
        mKeys.add(view.findViewById(R.id.pin_code_button_6));
        mKeys.add(view.findViewById(R.id.pin_code_button_7));
        mKeys.add(view.findViewById(R.id.pin_code_button_8));
        mKeys.add(view.findViewById(R.id.pin_code_button_9));
        mKeys.add(view.findViewById(R.id.pin_code_button_del));
        mKeys.add(view.findViewById(R.id.pin_code_button_done));
    }

    public void hideCustomKeyboard() {
        mKeyboard.setVisibility(View.GONE);
        mKeyboard.setEnabled(false);
    }

    public void showCustomKeyboard() {
        mKeyboard.setVisibility(View.VISIBLE);
        mKeyboard.setEnabled(true);
    }

    public boolean isCustomKeyboardVisible() {
        return mKeyboard.getVisibility() == View.VISIBLE;
    }
}
