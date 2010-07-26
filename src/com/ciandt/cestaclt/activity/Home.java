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
	
	public static final String BEN_DISP_VALUE = "BEN_DISP_VALUE";
	public static final String PLANO_SAUDE_VALUE = "PLANO_SAUDE_VALUE";
	
	public static final int PICK_BEN_DISP_VALUE_REQUEST = 0;
	public static final int PLANO_SAUDE_EDIT_REQUEST = 1;

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
        if (requestCode == PICK_BEN_DISP_VALUE_REQUEST) {
            if (resultCode == RESULT_OK) {
            	beneficioDisp = data.getExtras().getFloat(BEN_DISP_VALUE);
				beneficioDispTV.setText(formatarMoeda(beneficioDisp));
            }
        }else if (requestCode == PICK_BEN_DISP_VALUE_REQUEST){
        	if (resultCode == RESULT_OK) {
        		planoSaude = data.getExtras().getFloat(PLANO_SAUDE_VALUE);
				planoSaudeTV.setText(formatarMoeda(planoSaude));
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
				i.putExtra(BEN_DISP_VALUE, beneficioDisp);
				startActivityForResult(i, PICK_BEN_DISP_VALUE_REQUEST);
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
    }
    
    private void loadPreferences(){
    	SharedPreferences settings = getSharedPreferences(PlanoSaudeEditActivity.PREFS_NAME, MODE_PRIVATE);
    	
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
    
    private void calculateAndUpdateSubtotal(){
    	float subtotal = calculateSubtotal();
    	
    	if(subtotal < 0){
    		subtotalTV.setTextColor(Color.RED);
        }else{
        	subtotalTV.setTextColor(Color.LTGRAY);
        }
    	subtotalTV.setText(formatarMoeda(subtotal));
    }
    
    private float calculateSubtotal(){
    	return beneficioDisp + seguroVida + planoSaude + 
    		planoOdonto + valeRefeicao + valeAlimentacao + transporte + 
    		previdenciaPriv + educacao + equipamentos;
    }
    
    public static String formatarMoeda(float valor){
    	return Util.currencyFormat.format(valor);
    }
    
    private void writePreferences(){
    	SharedPreferences settings = getSharedPreferences(PlanoSaudeEditActivity.PREFS_NAME, MODE_PRIVATE);
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
