package com.ciandt.cestaclt.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ciandt.cestaclt.R;
import com.ciandt.cestaclt.Util;

public class PlanoOdontoEditActivity extends Activity {

	public static final String PREFS_NAME = "PlanoOdontoPrefsFile";
	
	private int planoOption;
	private float outro;
	private float total;
	
	private RadioGroup radioGroup;
	private RadioButton uniodontoRadio;
	private RadioButton proMasterRadio;
	private RadioButton proGoldRadio;
	private RadioButton proDiamondRadio;
	private EditText outroEditText;
	private TextView totalTV;
	private Button ok;
	
	private static final float uniodontoValue = 13.94f;
	private static final float prodentMasterValue = 13.5f;
	private static final float prodentGoldValue = 50.05f;
	private static final float prodentDiamondValue = 69.34f;
	
	private static final int UNIODONTO_OPTION = 1;
	private static final int PRO_MASTER_OPTION = 2;
	private static final int PRO_GOLD_OPTION = 3;
	private static final int PRO_DIAMOND_OPTION = 4;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plano_odonto_edit);
		loadPreferences();
		bindComponents();
		calculateAndUpdateTotal();
	}

	private void bindComponents() {
		radioGroup = (RadioGroup) findViewById(R.id.plano_odonto_radio_group);
		uniodontoRadio = (RadioButton) findViewById(R.id.uniodonto_radio);
		proMasterRadio = (RadioButton) findViewById(R.id.pro_master_radio);
		proGoldRadio = (RadioButton) findViewById(R.id.pro_gold_radio);
		proDiamondRadio = (RadioButton) findViewById(R.id.pro_diamond_radio);
		totalTV = (TextView) findViewById(R.id.plano_odonto_subtotal);
		outroEditText = (EditText) findViewById(R.id.plano_odonto_outro);
		ok = (Button) findViewById(R.id.plano_odonto_save);
		
		uniodontoRadio.setChecked(false);
		proMasterRadio.setChecked(false);
		proGoldRadio.setChecked(false);
		proDiamondRadio.setChecked(false);
		
		switch (planoOption) {
		case UNIODONTO_OPTION:
			uniodontoRadio.setChecked(true);
			break;
		case PRO_MASTER_OPTION:
			proMasterRadio.setChecked(true);
			break;
		case PRO_GOLD_OPTION:
			proGoldRadio.setChecked(true);
			break;
		case PRO_DIAMOND_OPTION:
			proDiamondRadio.setChecked(true);
			break;
		}
		
	    outroEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				calculateAndUpdateTotal();
			}
		});
	    
	    radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int checkedRadioButtonId = group.getCheckedRadioButtonId();
				switch (checkedRadioButtonId) {
				case R.id.uniodonto_radio:
					planoOption = UNIODONTO_OPTION;
					break;
				case R.id.pro_master_radio:
					planoOption = PRO_MASTER_OPTION;
					break;
				case R.id.pro_gold_radio:
					planoOption = PRO_GOLD_OPTION;
					break;
				case R.id.pro_diamond_radio:
					planoOption = PRO_DIAMOND_OPTION;
					break;
				}
				calculateAndUpdateTotal();
			}
		});
		
		outroEditText.setText(Float.toString(outro));
		
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				calculateAndUpdateTotal();
				writePreferences();
				
				Bundle bundle = new Bundle();
				bundle.putFloat(Home.PLANO_ODONTO_RESULT_VALUE, -total);

				Intent intent = new Intent();
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
	
	private void loadPreferences(){
    	SharedPreferences settings = getSharedPreferences(PlanoOdontoEditActivity.PREFS_NAME, MODE_PRIVATE);
    	
    	planoOption = settings.getInt("planoOption", UNIODONTO_OPTION);
		outro = settings.getFloat("outro", 0f);
    }
	
	private void writePreferences(){
    	SharedPreferences settings = getSharedPreferences(PlanoOdontoEditActivity.PREFS_NAME, MODE_PRIVATE);
    	SharedPreferences.Editor editor = settings.edit();
   	 
    	editor.putInt("planoOption", planoOption);
    	editor.putFloat("outro", outro);
    	
    	editor.commit();
    }

	private void calculateAndUpdateTotal() {
		total = 0f;
		
		switch (planoOption) {
		case UNIODONTO_OPTION:
			total += uniodontoValue;
			break;
		case PRO_MASTER_OPTION:
			total += prodentMasterValue;
			break;
		case PRO_GOLD_OPTION:
			total += prodentGoldValue;
			break;
		case PRO_DIAMOND_OPTION:
			total += prodentDiamondValue;
			break;
		}
		
		if(!outroEditText.getText().toString().trim().equals("")){
			outro = Float.parseFloat(outroEditText.getText().toString());
		}else{
			outro = 0f;
		}
		
		total += outro;
		totalTV.setText(Util.formatarMoeda(total));
	}
}
