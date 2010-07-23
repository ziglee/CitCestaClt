package com.ciandt.cestaclt.activity;

import java.text.NumberFormat;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ciandt.cestaclt.R;

public class Home extends Activity {

	public static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	
	private TextView beneficioDispTV;
	private TextView seguroVidaTV;
	private TextView planoSaudeTV;
	private TextView planoOdontoTV;
	private TextView valeRefeicaoTV;
	private TextView valeAlimentacaoTV;
	private TextView transporteTV;
	private TextView previdenciaPrivTV;
	private TextView educacaoTV;
	private TextView equipamentosTV;
	private TextView subtotalTV;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        beneficioDispTV = (TextView)findViewById(R.id.beneficio_disp);
        seguroVidaTV = (TextView)findViewById(R.id.seguro_vida);
        planoSaudeTV = (TextView)findViewById(R.id.plano_saude_total);
        planoOdontoTV = (TextView)findViewById(R.id.plano_odonto_total);
        valeRefeicaoTV = (TextView)findViewById(R.id.vale_refeicao_total);
        valeAlimentacaoTV = (TextView)findViewById(R.id.vale_alimentacao_total);
        transporteTV = (TextView)findViewById(R.id.transporte_total);
        previdenciaPrivTV = (TextView)findViewById(R.id.previdencia_total);
        educacaoTV = (TextView)findViewById(R.id.educacao_total);
        equipamentosTV = (TextView)findViewById(R.id.equipamentos_total);
        subtotalTV = (TextView)findViewById(R.id.subtotal);
        
        float beneficioDisp = 1199.00f;
        float seguroVida = -11.04f;
        float planoSaude = -140.44f;
        float planoOdonto = -13.94f;
        float valeRefeicao = -300f;
        float valeAlimentacao = -200f;
        float transporte = -433.58f;
        float previdenciaPriv = -100f;
        float educacao = 0;
        float equipamentos = 0;
        
        float subtotal = beneficioDisp + seguroVida + planoSaude + planoOdonto + 
        	valeRefeicao + valeAlimentacao + transporte + previdenciaPriv + educacao +
        	equipamentos;
        
        beneficioDispTV.setText(numberFormat.format(beneficioDisp));
        seguroVidaTV.setText(numberFormat.format(seguroVida));
        planoSaudeTV.setText(numberFormat.format(planoSaude));
        planoOdontoTV.setText(numberFormat.format(planoOdonto));
        valeRefeicaoTV.setText(numberFormat.format(valeRefeicao));
        valeAlimentacaoTV.setText(numberFormat.format(valeAlimentacao));
        transporteTV.setText(numberFormat.format(transporte));
        previdenciaPrivTV.setText(numberFormat.format(previdenciaPriv));
        educacaoTV.setText(numberFormat.format(educacao));
        equipamentosTV.setText(numberFormat.format(equipamentos));
        subtotalTV.setText(numberFormat.format(subtotal));
    }
}