package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tv;

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String BMI = prefs.getString("BMI", "");
        String Date = prefs.getString("Date", "");
        String status = prefs.getString("status", "");

        tvBMI.setText(BMI);
        tvDate.setText(Date);
        tv.setText(status);

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preEdit = prefs.edit();

        preEdit.putString("BMI", tvBMI.getText().toString());
        preEdit.putString("Date", tvDate.getText().toString());
        preEdit.putString("status", tv.getText().toString());

        preEdit.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonResetData);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tv = findViewById(R.id.textView);

        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etWeight.getText().toString().isEmpty() && !etHeight.getText().toString().isEmpty()) {

                    float weight = Float.parseFloat(etWeight.getText().toString());
                    float height = Float.parseFloat(etHeight.getText().toString());

                    float total = weight / (height * height);
                    if (total <= 18.5) {
                        tv.setText("You are underweight");
                    } else if (total <= 24.9) {
                        tv.setText("Your BMI is normal");
                    } else if (total <= 29.9) {
                        tv.setText("Tou are overweight");
                    } else if (total >= 30) {
                        tv.setText("Tou are obese");
                    }

                    tvBMI.setText("Last Calculated BMI: " + total);
                    tvDate.setText("Last Calculated Date: " + datetime);
                    etWeight.setText("");
                    etHeight.setText("");
                }
            }
        });

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etWeight.setText("");
                etHeight.setText("");
                tvBMI.setText("Last Calculated BMI: ");
                tvDate.setText("Last Calculated Date: ");
                tv.setText("");

                SharedPreferences.Editor preEdit = prefs.edit();
                preEdit.clear();
                preEdit.commit();
            }
        });



    }


}
