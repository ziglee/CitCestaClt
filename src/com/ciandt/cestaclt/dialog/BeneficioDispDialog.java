package com.ciandt.cestaclt.dialog;

import com.ciandt.cestaclt.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BeneficioDispDialog extends Dialog {

	private EditText valorText;
	private Button okButton;
	private Button cancelButton;
	private float valorObtido;
	
	public BeneficioDispDialog(Context context, final float valor) {
		super(context);
		
        setContentView(R.layout.beneficio_disp_dialog);
        setTitle("Benefício Disponível");

        valorText = (EditText) findViewById(R.id.ben_valor_text);
        okButton = (Button) findViewById(R.id.ben_valor_ok_button);
        cancelButton = (Button) findViewById(R.id.ben_valor_cancel_button);
        
        cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
        });
        
        okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				valorObtido = valor;
			}
        });
        
        valorText.setText(Float.toString(valor));
	}

	public float getValorObtido() {
		return valorObtido;
	}
}
