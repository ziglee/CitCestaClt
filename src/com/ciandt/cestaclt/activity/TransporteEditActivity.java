package com.ciandt.cestaclt.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ciandt.cestaclt.R;
import com.ciandt.cestaclt.Util;

public class TransporteEditActivity extends Activity {

	public static final String PREFS_NAME = "TransportePrefsFile";
	
	private float quilometros;
	private boolean estacionamento;
	private boolean fretado;
	private float total;

	private View quilometrosHelp;
	private TextView quilometrosTV;
	private TextView fretadoTV;
	private TextView estacionamentoTV;
	private TextView totalTV;
	private TextView obs;
	private EditText quilometrosEditText;
	private CheckBox fretadoCheck;
	private CheckBox estacionamentoCheck;
	private Button button;

	private static final float quilometroValue = 1.1f;
	private static final float estacionamentoValue = 60f;
	private static final float fretadoValue = 183f;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transporte_edit);
        bindComponents();
        loadPreferences();
        loadValuesToComponents();
        calculateAndUpdateTotal();
	}

	private void bindComponents() {
		quilometrosHelp = (View) findViewById(R.id.quilometros_help);
		quilometrosTV = (TextView) findViewById(R.id.quilometros_label);
		fretadoTV = (TextView) findViewById(R.id.fretado_label);
		estacionamentoTV = (TextView) findViewById(R.id.estacionamento_label);
		totalTV = (TextView) findViewById(R.id.transporte_total);
		obs = (TextView) findViewById(R.id.transporte_obs);
		quilometrosEditText = (EditText) findViewById(R.id.quilometros);
		fretadoCheck = (CheckBox) findViewById(R.id.fretado_check);
		estacionamentoCheck = (CheckBox) findViewById(R.id.estacionamento_check);
		button = (Button) findViewById(R.id.transporte_edit_button);

		quilometrosTV.setText("Quilômetros ("+ Util.formatarMoeda(quilometroValue) + "/Km)");
		fretadoTV.setText("Fretado ("+ Util.formatarMoeda(fretadoValue) +")");
		estacionamentoTV.setText("Estacionamento Polis ("+ Util.formatarMoeda(estacionamentoValue) +")");
		obs.setText("Obs.: Podem ser solicitados os dois, tanto fretado quanto km, desde que o colaborador assine um termo de que pode fazer uso de ambos.");
		
		quilometrosHelp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(TransporteEditActivity.this);
				builder.setMessage("Digite a distância de sua residência até a Ci&T.\nO valor calculado será de ida e volta de 22 dias desta distância.");
				builder.setCancelable(true);
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
				final AlertDialog alert = builder.create();
				alert.show();
			}
		});
		
		quilometrosEditText.addTextChangedListener(new TextWatcher() {
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
		
		fretadoCheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				calculateAndUpdateTotal();
			}
		});

		estacionamentoCheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				calculateAndUpdateTotal();
			}
		});
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				calculateAndUpdateTotal();
				writePreferences();
				
				Bundle bundle = new Bundle();
				bundle.putFloat(Home.TRANSPORTE_RESULT_VALUE, -total);

				Intent intent = new Intent();
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	private void loadPreferences() {
		SharedPreferences settings = getSharedPreferences(TransporteEditActivity.PREFS_NAME, MODE_PRIVATE);
		
		quilometros = settings.getFloat("quilometros", 0f);
		estacionamento = settings.getBoolean("estacionamento", false);
		fretado = settings.getBoolean("fretado", false);
	}

	private void loadValuesToComponents() {
		quilometrosEditText.setText(Float.toString(quilometros));
		estacionamentoCheck.setChecked(estacionamento);
		fretadoCheck.setChecked(fretado);
	}

	private void calculateAndUpdateTotal() {
		total = 0;
		
		String valueText = quilometrosEditText.getText().toString();
		if(!valueText.trim().equals("")){
			quilometros = Float.parseFloat(valueText);
		}
		
		total += (quilometros * 44) * quilometroValue;
		if(estacionamentoCheck.isChecked()) total += estacionamentoValue;
		if(fretadoCheck.isChecked()) total += fretadoValue;
		
		totalTV.setText(Util.formatarMoeda(total));
	}

	private void writePreferences() {
		SharedPreferences settings = getSharedPreferences(TransporteEditActivity.PREFS_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
	   	 
    	editor.putFloat("quilometros", quilometros);
    	editor.putBoolean("estacionamento", estacionamentoCheck.isChecked());
    	editor.putBoolean("fretado", fretadoCheck.isChecked());
    	
    	editor.commit();
	}
}
