package com.example.orderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity=1;
    public void increase(View view){
        if(quantity==100){
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }
    public void decrease(View view){
        if(quantity==1){
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }


    public void submitOrder(View view){
        EditText nameField = (EditText) findViewById(R.id.gettingName);
        Editable nameEditable = nameField.getText();
        String name = nameEditable.toString();

        CheckBox whippedCreamBox = (CheckBox) findViewById(R.id.whipped);
        boolean hasWhippedCream=whippedCreamBox.isChecked();

        CheckBox iceCreamBox = (CheckBox) findViewById(R.id.cream);
        boolean hasCream=iceCreamBox.isChecked();

        int price = priceOfOrder(hasWhippedCream,hasCream);

        String message =createOrderSummary(name,price,hasWhippedCream,hasCream);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Order by"+name);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if(intent.resolveActivity(getPackageManager())==null) {
            startActivity(intent);
        }

    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addCream){
        String priceMessage = "Name"+name;
        priceMessage+="\nWhipped Cream: "+addWhippedCream;
        priceMessage+="\nIce cream: "+addCream;
        priceMessage+="\nQuantity: "+quantity;
        priceMessage+="\nPrice: "+priceOfOrder(addWhippedCream,addCream);
        priceMessage+="\n"+"Thankyou!";
        return priceMessage;
    }

    private int priceOfOrder(boolean addWhippedCream, boolean addCream){
        int basePrice=5;
        if(addWhippedCream){
            basePrice+=1;
        }
        if(addCream){
            basePrice+=2;
        }
        return basePrice*quantity;
    }


    private void displayQuantity(int numberOfCoffee){
        TextView quantityText = (TextView) findViewById(R.id.quantity);
        quantityText.setText(""+numberOfCoffee);
    }
}