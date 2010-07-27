package com.ciandt.cestaclt;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Util {

	public static final Locale localePtBR = new Locale("pt","BR");
	public static final DecimalFormat currencyFormat = new DecimalFormat("¤0.00;-¤0.00");
	
	static{
		currencyFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(localePtBR));
	}

    public static String formatarMoeda(float valor){
    	return currencyFormat.format(valor);
    }
}
