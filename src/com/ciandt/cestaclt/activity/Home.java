package com.ciandt.cestaclt.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ciandt.cestaclt.R;
import com.ciandt.cestaclt.Util;

public class Home extends Activity {

	public static final String PREFS_NAME = "HomePrefsFile";
	
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
	
	private float beneficioDisp;
	private float seguroVida;
	private float planoSaude;
	private float planoOdonto;
	private float valeRefeicao;
	private float valeAlimentacao;
	private float transporte;
	private float previdenciaPriv;
	private float educacao;
	private float equipamentos;
	private float subtotal;
	
	public static final String BEN_DISP_RESULT_VALUE = "BEN_DISP_RESULT_VALUE";
	public static final String PLANO_SAUDE_RESULT_VALUE = "PLANO_SAUDE_RESULT_VALUE";
	public static final String PLANO_ODONTO_RESULT_VALUE = "PLANO_ODONTO_RESULT_VALUE";
	
	public static final int BEN_DISP_VALUE_REQUEST = 0;
	public static final int PLANO_SAUDE_EDIT_REQUEST = 1;
	public static final int PLANO_ODONTO_EDIT_REQUEST = 2;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        bindComponents();
        loadPreferences();
        loadValuesToComponents();
        calculateAndUpdateSubtotal();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == BEN_DISP_VALUE_REQUEST) {
            if (resultCode == RESULT_OK) {
            	beneficioDisp = data.getExtras().getFloat(BEN_DISP_RESULT_VALUE);
				beneficioDispTV.setText(Util.formatarMoeda(beneficioDisp));
            }
        }else if (requestCode == PLANO_SAUDE_EDIT_REQUEST){
        	if (resultCode == RESULT_OK) {
        		planoSaude = data.getExtras().getFloat(PLANO_SAUDE_RESULT_VALUE);
				planoSaudeTV.setText(Util.formatarMoeda(planoSaude));
        	}
        }else if (requestCode == PLANO_ODONTO_EDIT_REQUEST){
        	if (resultCode == RESULT_OK) {
        		planoOdonto = data.getExtras().getFloat(PLANO_ODONTO_RESULT_VALUE);
				planoOdontoTV.setText(Util.formatarMoeda(planoOdonto));
        	}
        }
        
        calculateAndUpdateSubtotal();
        writePreferences();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	writePreferences();
    }
    
    private void bindComponents(){
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
        
        View beneficioDispRow = (View) findViewById(R.id.beneficio_disp_row);
        beneficioDispRow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Home.this, BeneficioDispEditActivity.class);
				i.putExtra(BEN_DISP_RESULT_VALUE, beneficioDisp);
				startActivityForResult(i, BEN_DISP_VALUE_REQUEST);
			}
		});
        
        View planoSaudeRow = (View) findViewById(R.id.plano_saude_row);
        planoSaudeRow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Home.this, PlanoSaudeEditActivity.class);
				startActivityForResult(i, PLANO_SAUDE_EDIT_REQUEST);
			}
		});
        
        View planoOdontoRow = (View) findViewById(R.id.plano_odonto_row);
        planoOdontoRow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Home.this, PlanoOdontoEditActivity.class);
				startActivityForResult(i, PLANO_ODONTO_EDIT_REQUEST);
			}
		});
    }
    
    private void loadPreferences(){
    	SharedPreferences settings = getSharedPreferences(Home.PREFS_NAME, MODE_PRIVATE);
    	
		beneficioDisp = settings.getFloat("beneficioDisp", 1199.00f);
		seguroVida = settings.getFloat("seguroVida", -11.04f);
		planoSaude = settings.getFloat("planoSaude", -140.44f);
		planoOdonto = settings.getFloat("planoOdonto", -13.94f);
		valeRefeicao = settings.getFloat("valeRefeicao", -300f);
		valeAlimentacao = settings.getFloat("valeAlimentacao", -200f);
		transporte = settings.getFloat("transporte", -433.58f);
		previdenciaPriv = settings.getFloat("previdenciaPriv", -100f);
		educacao = settings.getFloat("educacao", 0f);
		equipamentos = settings.getFloat("equipamentos", 0f);
		subtotal = settings.getFloat("subtotal", 0f);
    }

	private void loadValuesToComponents() {
		beneficioDispTV.setText(Util.formatarMoeda(beneficioDisp));
        seguroVidaTV.setText(Util.formatarMoeda(seguroVida));
        planoSaudeTV.setText(Util.formatarMoeda(planoSaude));
        planoOdontoTV.setText(Util.formatarMoeda(planoOdonto));
        valeRefeicaoTV.setText(Util.formatarMoeda(valeRefeicao));
        valeAlimentacaoTV.setText(Util.formatarMoeda(valeAlimentacao));
        transporteTV.setText(Util.formatarMoeda(transporte));
        previdenciaPrivTV.setText(Util.formatarMoeda(previdenciaPriv));
        educacaoTV.setText(Util.formatarMoeda(educacao));
        equipamentosTV.setText(Util.formatarMoeda(equipamentos));
        subtotalTV.setText(Util.formatarMoeda(subtotal));
	}
    
    private void calculateAndUpdateSubtotal(){
    	float subtotal = calculateSubtotal();
    	
    	if(subtotal < 0){
    		subtotalTV.setTextColor(Color.RED);
        }else{
        	subtotalTV.setTextColor(Color.LTGRAY);
        }
    	subtotalTV.setText(Util.formatarMoeda(subtotal));
    }
    
    private float calculateSubtotal(){
    	return beneficioDisp + seguroVida + planoSaude + 
    		planoOdonto + valeRefeicao + valeAlimentacao + transporte + 
    		previdenciaPriv + educacao + equipamentos;
    }
    
    private void writePreferences(){
    	SharedPreferences settings = getSharedPreferences(Home.PREFS_NAME, MODE_PRIVATE);
    	SharedPreferences.Editor editor = settings.edit();
   	 
    	editor.putFloat("beneficioDisp", beneficioDisp);
    	editor.putFloat("seguroVida", seguroVida);
    	editor.putFloat("planoSaude", planoSaude);
    	editor.putFloat("planoOdonto", planoOdonto);
    	editor.putFloat("valeRefeicao", valeRefeicao);
    	editor.putFloat("valeAlimentacao", valeAlimentacao);
    	editor.putFloat("transporte", transporte);
    	editor.putFloat("previdenciaPriv", previdenciaPriv);
    	editor.putFloat("educacao", educacao);
    	editor.putFloat("equipamentos", equipamentos);
    	editor.putFloat("subtotal", subtotal);
    	
    	editor.commit();
    }
}
