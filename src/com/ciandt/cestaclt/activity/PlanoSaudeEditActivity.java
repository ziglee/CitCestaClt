package com.ciandt.cestaclt.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.ciandt.cestaclt.R;

public class PlanoSaudeEditActivity extends Activity {

	public static final String PREFS_NAME = "PlanoSaudePrefsFile";
	
	private EditText qtdeColabDepColetivoEditText;
	private Button qtdeColabDepColetivoPlus;
	private Button qtdeColabDepColetivoMinus;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plano_saude_edit);
		
		qtdeColabDepColetivoEditText = (EditText) findViewById(R.id.qtde_colab_dep_coletivo);
		qtdeColabDepColetivoPlus = (Button) findViewById(R.id.qtde_colab_dep_coletivo_plus);
		qtdeColabDepColetivoMinus = (Button) findViewById(R.id.qtde_colab_dep_coletivo_minus);
	}
}
