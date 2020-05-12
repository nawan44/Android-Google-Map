package id.co.hendevane.universitas;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvAddress;
    private TextView tvWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvName = (TextView) findViewById(R.id.tv_name);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvWeb = (TextView) findViewById(R.id.tv_web);

        University university = (University) getIntent().getSerializableExtra("data");
        actionBar.setTitle(university.name);
        tvName.setText(university.name);
        tvAddress.setText(university.address);
        tvWeb.setText(university.web);
    }
}
