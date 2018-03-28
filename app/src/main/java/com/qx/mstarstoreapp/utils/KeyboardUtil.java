package com.qx.mstarstoreapp.utils;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.qx.mstarstoreapp.R;
import com.qx.mstarstoreapp.inter.KeyboardFinish;


public class KeyboardUtil {
    private final InputMethodManager imm;
    private KeyboardView keyboardView;
    private Keyboard k1;// 字母键盘
    private Keyboard k2;// 数字键盘
    private Keyboard k3;// 符号键盘
    private boolean isNum = false;// 是否数据键盘
    private boolean isUpper = true;// 是否大写
    private boolean isSymbol = false;// 是否符号

    private static final int SYMBOL_CODE = -7;//符号键盘
    private static final int ELLIPSES_CODE = -8;//省略号
    private static final int CHINESE_HORIZONTAL_LINE_CODE = -9;//中文横线
    private static final int SMILING_FACE_CODE = -10;//笑脸
    private static final int LEFT_CODE = -11;//中文横线
    private static final int RIGHT_CODE = -12;//中文横线
    private static final int HEE_CODE = -13;//哈哈
    private static final int AWKWARD_CODE = -14;//尴尬

    private ViewGroup rootView;
    private EditText ed;
    private Activity activity;
    private KeyboardFinish keyboardFinish;

    private KeyboardUtil(Activity activity, EditText edit, View rootview) {
        this.ed = edit;
        k1 = new Keyboard(activity, R.xml.letter);
        k2 = new Keyboard(activity, R.xml.number);
        k3 = new Keyboard(activity, R.xml.symbol);

        this.activity = activity;
        keyboardFinish = (KeyboardFinish) activity;
        keyboardView = new KeyboardView(activity, null);
        keyboardView.setKeyboard(k1);
        k1.setShifted(isUpper);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(onKeyboardActionListener);

        rootView = (ViewGroup) rootview;
        imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    OnKeyboardActionListener onKeyboardActionListener = new OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                keyboardFinish.keyboardFinish();
                hideKeyboard();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换
                isUpper = !isUpper;
                k1.setShifted(isUpper);
                keyboardView.invalidateAllKeys();
            } else if (primaryCode == SYMBOL_CODE) {// 符号键盘
                if (isSymbol) {
                    isSymbol = false;
                    keyboardView.setKeyboard(k2);
                } else {
                    isSymbol = true;
                    keyboardView.setKeyboard(k3);
                }
            } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {// 数字键盘切换
                hideKeyboard();
                imm.showSoftInput(ed, InputMethodManager.SHOW_FORCED);

            } else if (primaryCode == LEFT_CODE) { //向左
                if (start > 0) {
                    ed.setSelection(start - 1);
                }
            } else if (primaryCode == RIGHT_CODE) { // 向右
                if (start < ed.length()) {
                    ed.setSelection(start + 1);
                }
            } else if (primaryCode == ELLIPSES_CODE) { //省略号
                editable.insert(start, "...");
            } else if (primaryCode == CHINESE_HORIZONTAL_LINE_CODE) {
                editable.insert(start, "——");
            } else if (primaryCode == SMILING_FACE_CODE) {
                editable.insert(start, "^_^");
            } else if (primaryCode == HEE_CODE) {
                editable.insert(start, "^o^");
            } else if (primaryCode == AWKWARD_CODE) {
                editable.insert(start, ">_<");
            } else {
                String str = Character.toString((char) primaryCode);
                if (isWord(str)) {
                    if (isUpper) {
                        str = str.toUpperCase();
                    } else {
                        str = str.toLowerCase();
                    }
                }
                editable.insert(start, str);

            }
        }
    };

    private boolean isShow = false;

    public void showKeyboard() {

        if (!isShow) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            rootView.addView(keyboardView, layoutParams);
            isShow = true;
        }
    }

    public void hideKeyboard() {
        if (rootView != null && keyboardView != null && isShow) {
            isShow = false;
            rootView.removeView(keyboardView);
        }
        mInstance = null;
    }

    private boolean isWord(String str) {
        return str.matches("[a-zA-Z]");
    }

    private static KeyboardUtil mInstance;

    public static KeyboardUtil shared(Activity activity, EditText edit, View rootview) {
        if (mInstance == null) {
            mInstance = new KeyboardUtil(activity, edit, rootview);
        }
        mInstance.ed = edit;
        return mInstance;
    }



}
