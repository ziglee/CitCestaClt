package com.ciandt.cestaclt.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ciandt.cestaclt.R;

public class ValeRefeicaoEditActivity extends Activity {

	private EditText editText;
	private Button button;
	private float valeRefeicao;
	private float valeAlimentacao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vale_refeicao_edit);

		Bundle extras = getIntent().getExtras();
		valeRefeicao = extras.getFloat(Home.VALE_REF_RESULT_VALUE);
		valeAlimentacao = extras.getFloat(Home.VALE_ALIM_RESULT_VALUE);

		editText = (EditText) findViewById(R.id.vale_refeicao_edit_text);
		button = (Button) findViewById(R.id.vale_refeicao_edit_button);

		editText.setText(Float.toString(valeRefeicao));

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Mínimo de R$110,00.\nMáximo de até R$800,00 na soma do Visa Vale Refeição e Visa Vale Alimentação.");
		builder.setCancelable(true);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		final AlertDialog alert = builder.create();
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String valueText = editText.getText().toString();
				if(valueText.trim().equals("")){
					alert.show();
					return;
				}
				
				valeRefeicao = Float.parseFloat(valueText);
				if(valeRefeicao < 110 || (valeRefeicao + valeAlimentacao) > 800){
					alert.show();
					return;
				}
				
				Bundle bundle = new Bundle();
				bundle.putFloat(Home.VALE_REF_RESULT_VALUE, -valeRefeicao);

				Intent intent = new Intent();
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
}
