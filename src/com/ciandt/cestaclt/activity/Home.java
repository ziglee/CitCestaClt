package com.ciandt.cestaclt.activity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ciandt.cestaclt.R;

public class Home extends Activity {

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
	
	private ImageView editBeneficioDisp;
	
	private float beneficioDisp = 1199.00f;
	private float seguroVida = -11.04f;
	private float planoSaude = -140.44f;
	private float planoOdonto = -13.94f;
	private float valeRefeicao = -300f;
	private float valeAlimentacao = -200f;
	private float transporte = -433.58f;
	private float previdenciaPriv = -100f;
	private float educacao = 0;
	private float equipamentos = 0;
	
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
        
        editBeneficioDisp = (ImageView)findViewById(R.id.edit_beneficio_disp);
        
        float subtotal = beneficioDisp + seguroVida + planoSaude + planoOdonto + 
        	valeRefeicao + valeAlimentacao + transporte + previdenciaPriv + educacao +
        	equipamentos;
        
        beneficioDispTV.setText(formatarMoeda(beneficioDisp));
        seguroVidaTV.setText(formatarMoeda(seguroVida));
        planoSaudeTV.setText(formatarMoeda(planoSaude));
        planoOdontoTV.setText(formatarMoeda(planoOdonto));
        valeRefeicaoTV.setText(formatarMoeda(valeRefeicao));
        valeAlimentacaoTV.setText(formatarMoeda(valeAlimentacao));
        transporteTV.setText(formatarMoeda(transporte));
        previdenciaPrivTV.setText(formatarMoeda(previdenciaPriv));
        educacaoTV.setText(formatarMoeda(educacao));
        equipamentosTV.setText(formatarMoeda(equipamentos));
        subtotalTV.setText(formatarMoeda(subtotal));
    }
    
    public static String formatarMoeda(float valor){
    	DecimalFormat numberFormat = new DecimalFormat("0.00 ¤;-0.00 ¤");
    	numberFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale("pt","BR")));
    	return numberFormat.format(valor);
    }
}
