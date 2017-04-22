package org.atheby.sudoku.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import org.atheby.sudoku.R;

public class MainActivity extends Activity {

    private String level;
    private TextView hiddenInfo, duplicatesInfo;
    private Button startBtn;
    private EditText userNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hiddenInfo = (TextView) findViewById(R.id.hiddenInfo);
        duplicatesInfo = (TextView) findViewById(R.id.duplicatesInfo);
        level = "easy";
        setLevelInfo(level);
        startBtn = (Button) findViewById(R.id.startButton);
        startBtn.setEnabled(false);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("level", level);
                intent.putExtra("userName", userNameInput.getText().toString());
                startActivity(intent);
            }
        });
        userNameInput = (EditText) findViewById(R.id.usernameInput);
        userNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0)
                    startBtn.setEnabled(true);
                else
                    startBtn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radioButton_easy:
                if (checked) {
                    level = "easy";
                    setLevelInfo(level);
                }
                break;
            case R.id.radioButton_medium:
                if (checked) {
                    level = "medium";
                    setLevelInfo(level);
                }
                break;
            case R.id.radioButton_hard:
                if (checked) {
                    level = "hard";
                    setLevelInfo(level);
                }
                break;
        }
    }

    private void setLevelInfo(String level) {
        switch(level) {
            case "easy":
                hiddenInfo.setText("- 35 numbers hidden");
                duplicatesInfo.setVisibility(View.VISIBLE);
                break;
            case "medium":
                hiddenInfo.setText("- 40 numbers hidden");
                duplicatesInfo.setVisibility(View.VISIBLE);
                break;
            case "hard":
                hiddenInfo.setText("- 45 numbers hidden");
                duplicatesInfo.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
