package ie.cr.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ie.cr.R;
import ie.cr.activities.Home;
import ie.cr.main.CuttingRemarksApp;
import ie.cr.models.Barber;

public class AddFragment extends Fragment {

    private String 		barberName, barberShop;
    private double 		barberPrice, ratingValue;
    private EditText name, shop, price;
    private RatingBar ratingBar;
    private Button saveButton;
    private CuttingRemarksApp app;


    public AddFragment() {
        //Required Empty Constructor
    }

    //Add barber instance
    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (CuttingRemarksApp) getActivity().getApplication();

    }

    //Inflates the layout for add fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        getActivity().setTitle(R.string.addABarberLbl);
        name = v.findViewById(R.id.addNameET);
        shop =  v.findViewById(R.id.addShopET);
        price =  v.findViewById(R.id.addPriceET);
        ratingBar =  v.findViewById(R.id.addRatingBar);
        saveButton = v.findViewById(R.id.addBarberBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBarber();
            }
        });

        return v;
    }

    //Add barber method
    public void addBarber() {

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

            app.dbManager.insert(c);
            startActivity(new Intent(this.getActivity(), Home.class));
        } else
            Toast.makeText(
                    this.getActivity(),
                    "You must Enter Something for "
                            + "\'Name\', \'Shop\' and \'Price\'",
                    Toast.LENGTH_SHORT).show();
    }

}