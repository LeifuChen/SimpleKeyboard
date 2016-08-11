package io.leifu.simplekeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SimpleIME {

    KeyboardView mKeyboardView;
    Context mActivity;
    View mView;
    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            EditText editText = getEntryField();
            if (editText == null || !(editText instanceof EditText)) {
                return;
            } else {
                Editable editable = editText.getText();
                int cursorPosition = editText.getSelectionStart();
                switch (primaryCode) {
                    case Keyboard.KEYCODE_DELETE:
                        if (editable != null && cursorPosition > 0)
                            editable.delete(cursorPosition - 1, cursorPosition);
                        break;
                    case Keyboard.KEYCODE_DONE:
                        Toast.makeText(mActivity.getApplicationContext(), "Done!", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        editable.insert(cursorPosition, Character.toString((char) primaryCode));
                        editText.setText(editable);
                        editText.setSelection(editText.getText().length());
                }
            }
        }

        @Override
        public void onPress(int arg0) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeUp() {
        }
    };

    private EditText getEntryField() {
        EditText editText = (EditText) mView.findFocus();
        return editText;
    }

    public SimpleIME(Context context, View view) {
        mActivity = context;
        mView = view;

        LayoutInflater vi = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mKeyboardView = (KeyboardView) vi.inflate(R.layout.keyboard, null).findViewById(R.id.custom_pinpad);
        mKeyboardView.setKeyboard(new Keyboard(context, R.xml.pin_pad));
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);

        keyboardAlignParentBottom();
    }

    private void keyboardAlignParentBottom() {
        RelativeLayout.LayoutParams keyboardParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        keyboardParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, mKeyboardView.getId());
        mKeyboardView.setLayoutParams(keyboardParams);
        ((ViewGroup) mView).addView(mKeyboardView);
    }

    public void registerEditText(int resid) {
        EditText editText = (EditText) mView.findViewById(resid);
        editText.setOnFocusChangeListener(showKeyboardWhenInputFieldHasFocus());
        editText.setOnClickListener(showKeyboardWhenInputFieldClicked());
        editText.setOnTouchListener(systemKeyboardDisabled());
        editText.setOnKeyListener(backPressedWhenKeyboardIsVisible());
    }

    public View getView() {
        return mKeyboardView;
    }

    @NonNull
    private View.OnFocusChangeListener showKeyboardWhenInputFieldHasFocus() {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showCustomKeyboard();
                } else {
                    hideCustomKeyboard();
                }
            }
        };
    }

    @NonNull
    private View.OnKeyListener backPressedWhenKeyboardIsVisible() {
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && isCustomKeyboardVisible()) {
                    hideCustomKeyboard();
                    return true;
                }
                return false;
            }
        };
    }

    @NonNull
    private View.OnTouchListener systemKeyboardDisabled() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event); // Call native handler
                edittext.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD); // Restore input type
                return true; // Consume touch event
            }
        };
    }

    @NonNull
    private View.OnClickListener showKeyboardWhenInputFieldClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomKeyboard();
            }
        };
    }

    public void hideCustomKeyboard() {
        mKeyboardView.setVisibility(View.GONE);
        mKeyboardView.setEnabled(false);
    }

    public void showCustomKeyboard() {
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
    }

    public boolean isCustomKeyboardVisible() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

}
