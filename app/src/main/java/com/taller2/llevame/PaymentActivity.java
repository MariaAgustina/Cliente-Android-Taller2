package com.taller2.llevame;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.taller2.llevame.Models.Paymethod;
import com.taller2.llevame.Models.PaymethodCardParameters;
import com.taller2.llevame.Models.PaymethodCashParameters;

public class PaymentActivity extends AppCompatActivity {

    private Switch switchCashPaymethod;
    private Switch cashARS;
    private Switch cashUSD;
    private Switch switchCardPaymethod;
    private TextView cardNumberInput;
    private TextView cardExpirationMonthInput;
    private TextView cardExpirationYearInput;
    private TextView cardSecurityCodeInput;
    private Switch cardVISA;
    private Switch cardMASTER;

    public static final String PAYMENT_SETTINGS = "paymentSettings";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setUpInitials();
        setUpDefaultValues();
    }

    /**
     * set ups initial values
     */
    private void setUpInitials(){

        //cash
        this.switchCashPaymethod = (Switch) findViewById(R.id.switchCashPaymethod);
        this.cashARS = (Switch) findViewById(R.id.cashARS);
        this.cashUSD = (Switch) findViewById(R.id.cashUSD);

        //card
        this.switchCardPaymethod = (Switch) findViewById(R.id.switchCardPaymethod);
        this.cardNumberInput = (TextView) findViewById(R.id.cardNumberInput);
        this.cardExpirationMonthInput = (TextView) findViewById(R.id.cardExpirationMonthInput);
        this.cardExpirationYearInput = (TextView) findViewById(R.id.cardExpirationYearInput);
        this.cardSecurityCodeInput = (TextView) findViewById(R.id.cardSecurityCodeInput);
        this.cardVISA = (Switch) findViewById(R.id.cardVISA);
        this.cardMASTER = (Switch) findViewById(R.id.cardMASTER);

        //cash
        this.switchCashPaymethod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchCardPaymethod.setChecked(!isChecked);
                toggleCard(!isChecked);
                toggleCash(isChecked);
            }
        });

        //card
        this.switchCardPaymethod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchCashPaymethod.setChecked(!isChecked);
                toggleCash(!isChecked);
                toggleCard(isChecked);
            }
        });

        this.cashARS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cashARS.setChecked(isChecked);
                cashUSD.setChecked(!isChecked);
            }
        });


        this.cashUSD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cashUSD.setChecked(isChecked);
                cashARS.setChecked(!isChecked);
            }
        });

        this.cardVISA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cardVISA.setChecked(isChecked);
                cardMASTER.setChecked(!isChecked);
            }
        });


        this.cardMASTER.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cardMASTER.setChecked(isChecked);
                cardVISA.setChecked(!isChecked);
            }
        });
    }

    /**
     * set all values on or off for card
     * @param isOn true if it is on
     */
    private void toggleCard(boolean isOn){
        this.cardVISA.setChecked(isOn);
        this.cardMASTER.setChecked(isOn);
        this.cardNumberInput.setEnabled(isOn);
        this.cardExpirationMonthInput.setEnabled(isOn);
        this.cardExpirationYearInput.setEnabled(isOn);
        this.cardSecurityCodeInput.setEnabled(isOn);
        this.cardVISA.setEnabled(isOn);
        this.cardMASTER.setEnabled(isOn);
        if(isOn){
            this.cardVISA.setChecked(isOn);
        }

    }

    /**
     * set all values on or off for cash
     * @param isOn true if it is on
     */
    private void toggleCash(boolean isOn){
        this.cashARS.setEnabled(isOn);
        this.cashUSD.setEnabled(isOn);
        if(isOn){
            this.cashARS.setChecked(isOn);
        }
    }


    /**
     * Set ups the default values
     */
    private void setUpDefaultValues(){
        this.switchCashPaymethod.setChecked(true);
    }

    public void okButtonPressed(View view) {

        SharedPreferences settings = getSharedPreferences(PAYMENT_SETTINGS, 0);
        SharedPreferences.Editor editor = settings.edit();
        //Forma de pago en efectivo
        if(this.switchCashPaymethod.isChecked()){
            editor.putString("paymethod","cash");
            String paymentCashCurrency = this.cashARS.isChecked() ? "ARS" :"USD";
            editor.putString("paymentCashCurrency",paymentCashCurrency);

            //Forma de pago con tarjeta de credito
        }else{
            if(this.cardNumberInput.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(),R.string.card_number_should_not_empty,Toast.LENGTH_SHORT).show();
                return;
            }

            if(this.cardExpirationMonthInput.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(),R.string.card_expiraton_month_not_empty,Toast.LENGTH_SHORT).show();
                return;
            }

            if(this.cardExpirationYearInput.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(),R.string.card_expiration_year_not_empty,Toast.LENGTH_SHORT).show();
                return;
            }

            if(this.cardSecurityCodeInput.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(),R.string.card_security_code_not_empty,Toast.LENGTH_SHORT).show();
                return;
            }

            editor.putString("paymethod","card");
            String cardType = this.cardVISA.isChecked() ? "visa" : "master";
            editor.putString("cardType",cardType);
            editor.putString("ccvv",this.cardSecurityCodeInput.getText().toString());
            editor.putString("expiration_month",this.cardExpirationMonthInput.getText().toString());
            editor.putString("expiration_year",this.cardExpirationYearInput.getText().toString());
            editor.putString("cardNumber",this.cardNumberInput.getText().toString());

        }

        editor.commit();
        finish();

    }


}
