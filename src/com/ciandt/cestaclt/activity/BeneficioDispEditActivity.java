package com.ciandt.cestaclt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ciandt.cestaclt.R;

public class BeneficioDispEditActivity extends Activity {

	private EditText editText;
	private Button button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ben_disp_edit);

		Bundle extras = getIntent().getExtras();
		Float value = extras.getFloat(Home.BEN_DISP_VALUE);

		editText = (EditText) findViewById(R.id.ben_disp_edit_text);
		button = (Button) findViewById(R.id.ben_disp_edit_button);

		editText.setText(value.toString());

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String valueText = editText.getText().toString();

				if(!valueText.trim().equals("")){
					Bundle bundle = new Bundle();
					bundle.putFloat(Home.BEN_DISP_VALUE, Float.parseFloat(valueText));
	
					Intent intent = new Intent();
					intent.putExtras(bundle);
					setResult(RESULT_OK, intent);
					finish();
				}else{
					Toast.makeText(BeneficioDispEditActivity.this, "Digite algum valor decimal válido", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
