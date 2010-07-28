package com.ciandt.cestaclt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ciandt.cestaclt.R;

public class SimpleEditActivity extends Activity {

	private TextView title;
	private EditText editText;
	private Button button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_edit);

		Bundle extras = getIntent().getExtras();
		Float value = extras.getFloat(Home.SIMPLE_RESULT_VALUE);
		int editRequest = extras.getInt("EDIT_REQUEST");

		title = (TextView) findViewById(R.id.simple_edit_title);
		editText = (EditText) findViewById(R.id.previdencia_edit_text);
		button = (Button) findViewById(R.id.previdencia_edit_button);
		
		switch (editRequest) {
		case Home.PREVIDENCIA_EDIT_REQUEST:
			title.setText(R.string.previdencia);
			break;
		case Home.EDUCACAO_EDIT_REQUEST:
			title.setText(R.string.educacao);
			break;
		case Home.EQUIPAMENTOS_EDIT_REQUEST:
			title.setText(R.string.equipamentos);
			break;
		}
		
		value = Math.abs(value);

		editText.setText(value.toString());

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String valueText = editText.getText().toString();
				
				if(valueText.trim().equals("")) valueText = "0";

				Bundle bundle = new Bundle();
				bundle.putFloat(Home.SIMPLE_RESULT_VALUE, -Float.parseFloat(valueText));

				Intent intent = new Intent();
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
}
