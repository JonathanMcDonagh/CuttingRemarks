package ie.cr.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import ie.cr.R;
import ie.cr.models.Barber;

public class Edit extends Base {
    public Context context;
    public boolean isFavourite;
    public Barber aBarber;
    public ImageView editFavourite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        context = this;
        activityInfo = getIntent().getExtras();
        aBarber = getBarberObject(activityInfo.getString("barberId"));

        Log.v("barbermate", "EDIT : " + aBarber);

        ((TextView)findViewById(R.id.editTitleTV)).setText(aBarber.barberName);

        ((EditText)findViewById(R.id.editNameET)).setText(aBarber.barberName);
        ((TextView)findViewById(R.id.editShopET)).setText(aBarber.shop);
        ((EditText)findViewById(R.id.editPriceET)).setText(""+aBarber.price);
        ((RatingBar) findViewById(R.id.editRatingBar)).setRating((float)aBarber.rating);

        editFavourite = findViewById(R.id.editFavourite);

        if (aBarber.favourite == true) {
            editFavourite.setImageResource(R.drawable.favourites_72);
            isFavourite = true;
        } else {
            editFavourite.setImageResource(R.drawable.favourites_72);
            isFavourite = false;
        }
    }

    private Barber getBarberObject(String id) {

        for (Barber c : barberList)
            if (c.barberId.equalsIgnoreCase(id))
                return c;

        return null;
    }


    public void saveBarber(View v) {

        String barberName = ((EditText) findViewById(R.id.editNameET)).getText().toString();
        String barberShop = ((EditText) findViewById(R.id.editShopET)).getText().toString();
        String barberPriceStr = ((EditText) findViewById(R.id.editPriceET)).getText().toString();
        double ratingValue =((RatingBar) findViewById(R.id.editRatingBar)).getRating();
        double barberPrice;

        try {
            barberPrice = Double.parseDouble(barberPriceStr);
        } catch (NumberFormatException e) {
            barberPrice = 0.0;
        }

        if ((barberName.length() > 0) && (barberShop.length() > 0) && (barberPriceStr.length() > 0)) {
            aBarber.barberName = barberName;
            aBarber.shop = barberShop;
            aBarber.price = barberPrice;
            aBarber.rating = ratingValue;

            startActivity(new Intent(this,Home.class));

        } else
            Toast.makeText(this, "You must Enter Something for Name and Shop", Toast.LENGTH_SHORT).show();
    }

    public void toggle(View view) {

        if (isFavourite) {
            aBarber.favourite = false;
            Toast.makeText(this,"Removed From Favourites", Toast.LENGTH_SHORT).show();
            isFavourite = false;
            editFavourite.setImageResource(R.drawable.favourites_72);
        } else {
            aBarber.favourite = true;
            Toast.makeText(this,"Added to Favourites !!", Toast.LENGTH_SHORT).show();
            isFavourite = true;
            editFavourite.setImageResource(R.drawable.favourites_72);
        }
    }
}
