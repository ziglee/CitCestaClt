package com.ciandt.cestaclt.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.ciandt.cestaclt.R;
import com.ciandt.cestaclt.Util;

public class PlanoSaudeEditActivity extends Activity {

	public static final String PREFS_NAME = "PlanoSaudePrefsFile";
	
	private int colabPrivativo;
	private int colabColetivo;
	private int agregPrivativo;
	private int agregColetivo;
	private float outros;
	private float total;
	
	private TextView colabPrivativoTV;
	private TextView colabColetivoTV;
	private TextView agregPrivativoTV;
	private TextView agregColetivoTV;
	private TextView totalTV;
	private TextView obs;
	private Spinner colabPrivativoSpinner;
	private Spinner colabColetivoSpinner;
	private Spinner agregPrivativoSpinner;
	private Spinner agregColetivoSpinner;
	private EditText outrosEditText;
	private Button ok;
	
	private static final float colabPrivativoValue = 140.44f;
	private static final float colabColetivoValue = 82.73f;
	private static final float agregPrivativoValue = 399.23f;
	private static final float agregColetivoValue = 230.77f;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plano_saude_edit);
		loadPreferences();
		bindComponents();
		calculateAndUpdateTotal();
		
		colabPrivativoTV.setText("Privativo para colaboradores e dependentes* ("+ Util.formatarMoeda(colabPrivativoValue) +")");
		colabColetivoTV.setText("Colaborativo para colaboradores e dependentes* ("+ Util.formatarMoeda(colabColetivoValue) +")");
		agregPrivativoTV.setText("Privativo para agregados** ("+ Util.formatarMoeda(agregPrivativoValue) +")");
		agregColetivoTV.setText("Colaborativo para agregados** ("+ Util.formatarMoeda(agregColetivoValue) +")");
		obs.setText("Obs.: * esposo(a) - (comprovado, mediante certidão de casamento ou documento equivalente) e filhos.\n** pai, mãe, sogro e sogra.");
	}

	private void bindComponents() {
		colabPrivativoSpinner = (Spinner) findViewById(R.id.colab_privativo_spinner);
		colabColetivoSpinner = (Spinner) findViewById(R.id.colab_coletivo_spinner);
		agregPrivativoSpinner = (Spinner) findViewById(R.id.agreg_privativo_spinner);
		agregColetivoSpinner = (Spinner) findViewById(R.id.agreg_coletivo_spinner);
		colabPrivativoTV = (TextView) findViewById(R.id.colab_privativo_text);
		colabColetivoTV = (TextView) findViewById(R.id.colab_coletivo_text);
		agregPrivativoTV = (TextView) findViewById(R.id.agreg_privativo_text);
		agregColetivoTV = (TextView) findViewById(R.id.agreg_coletivo_text);
		totalTV = (TextView) findViewById(R.id.plano_saude_subtotal);
		obs = (TextView) findViewById(R.id.plano_saude_obs);
		outrosEditText = (EditText) findViewById(R.id.plano_saude_outros);
		ok = (Button) findViewById(R.id.plano_saude_save);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.quantidade, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
	    colabPrivativoSpinner.setAdapter(adapter);
	    colabColetivoSpinner.setAdapter(adapter);
	    agregPrivativoSpinner.setAdapter(adapter);
	    agregColetivoSpinner.setAdapter(adapter);
	    
	    outrosEditText.addTextChangedListener(new TextWatcher() {
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
		
		OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				calculateAndUpdateTotal();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				calculateAndUpdateTotal();
			}
		};
		
		colabPrivativoSpinner.setOnItemSelectedListener(onItemSelectedListener);
		colabColetivoSpinner.setOnItemSelectedListener(onItemSelectedListener);
		agregPrivativoSpinner.setOnItemSelectedListener(onItemSelectedListener);
		agregColetivoSpinner.setOnItemSelectedListener(onItemSelectedListener);
		
		colabPrivativoSpinner.setSelection(colabPrivativo);
		colabColetivoSpinner.setSelection(colabColetivo);
		agregPrivativoSpinner.setSelection(agregPrivativo);
		agregPrivativoSpinner.setSelection(agregColetivo);
		
		outrosEditText.setText(Float.toString(outros));
		
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(colabPrivativo == 0 && colabColetivo == 0){
					Toast.makeText(PlanoSaudeEditActivity.this, "Plano de saúde é obrigatório!", Toast.LENGTH_SHORT).show();
				}else{
					calculateAndUpdateTotal();
					writePreferences();
					
					Bundle bundle = new Bundle();
					bundle.putFloat(Home.PLANO_SAUDE_RESULT_VALUE, -total);
	
					Intent intent = new Intent();
					intent.putExtras(bundle);
					setResult(RESULT_OK, intent);
					finish();
				}
			}
		});
	}
	
	private void loadPreferences(){
    	SharedPreferences settings = getSharedPreferences(PlanoSaudeEditActivity.PREFS_NAME, MODE_PRIVATE);
    	
		colabPrivativo = settings.getInt("colabPrivativo", 0);
		colabColetivo = settings.getInt("colabColetivo", 0);
		agregPrivativo = settings.getInt("agregPrivativo", 0);
		agregColetivo = settings.getInt("agregColetivo", 0);
		outros = settings.getFloat("outros", 0f);
    }
	
	private void writePreferences(){
    	SharedPreferences settings = getSharedPreferences(PlanoSaudeEditActivity.PREFS_NAME, MODE_PRIVATE);
    	SharedPreferences.Editor editor = settings.edit();
   	 
    	editor.putInt("colabPrivativo", colabPrivativo);
    	editor.putInt("colabColetivo", colabColetivo);
    	editor.putInt("agregPrivativo", agregPrivativo);
    	editor.putInt("agregColetivo", agregColetivo);
    	editor.putFloat("outros", outros);
    	
    	editor.commit();
    }

	private void calculateAndUpdateTotal() {
		colabPrivativo = Integer.parseInt((String)colabPrivativoSpinner.getSelectedItem());
		colabColetivo = Integer.parseInt((String)colabColetivoSpinner.getSelectedItem());
		agregPrivativo = Integer.parseInt((String)agregPrivativoSpinner.getSelectedItem());
		agregColetivo = Integer.parseInt((String)agregColetivoSpinner.getSelectedItem());
		
		if(!outrosEditText.getText().toString().trim().equals("")){
			outros = Float.parseFloat(outrosEditText.getText().toString());
		}else{
			outros = 0f;
		}
		
		total = colabPrivativo * colabPrivativoValue;
		total += colabColetivo * colabColetivoValue;
		total += agregPrivativo * agregPrivativoValue;
		total += agregColetivo * agregColetivoValue;
		total += outros;
		totalTV.setText(Util.formatarMoeda(total));
	}
}
