package ie.cr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import ie.cr.R;
import ie.cr.models.Barber;

public class Add extends Base {

    private String barberName, barberShop;
    private double 		barberPrice, ratingValue;
    private EditText name, shop, price;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        name = findViewById(R.id.addNameET);
        shop =  findViewById(R.id.addShopET);
        price =  findViewById(R.id.editPriceET);
        ratingBar =  findViewById(R.id.addRatingBar);
    }

    public void addBarber(View v) {

        barberName = name.getText().toString();
        barberShop = shop.getText().toString();
        try {
            barberPrice = Double.parseDouble(price.getText().toString());
        } catch (NumberFormatException e) {
            barberPrice = 0.0;
        }
        ratingValue = ratingBar.getRating();

        if ((barberName.length() > 0) && (barberShop.length() > 0)
                && (price.length() > 0)) {
            Barber c = new Barber(barberName, barberShop, ratingValue,
                    barberPrice, false);

            Log.v("Cutting Remarks","Add : " + app.barberList);
            app.barberList.add(c);
            startActivity(new Intent(this, Home.class));
        } else
            Toast.makeText(
                    this,
                    "You must Enter Something for "
                            + "\'Name\', \'Shop\' and \'Price\'",
                    Toast.LENGTH_SHORT).show();
    }
}
