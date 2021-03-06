package ie.cr.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

import ie.cr.R;
import ie.cr.main.CuttingRemarksApp;
import ie.cr.models.Barber;

public class EditFragment extends Fragment {

    public boolean isFavourite;
    public Barber aBarber;
    public ImageView editFavourite;
    private EditText name, shop, price;
    private RatingBar ratingBar;
    public CuttingRemarksApp app;

    private DatabaseReference mDatabaseReference;


    private OnFragmentInteractionListener mListener;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(Bundle barberBundle) {
        EditFragment fragment = new EditFragment();
        fragment.setArguments(barberBundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        app = (CuttingRemarksApp) Objects.requireNonNull(getActivity()).getApplication();

        if(getArguments() != null)
            aBarber = app.dbManager.get(getArguments().getInt("barberId"));
    }

    private Barber getBarberObject(int id) {

        for (Barber b : app.dbManager.getAll())
            if (b.barberId == id)
                return b;

        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit, container, false);

        ((TextView)v.findViewById(R.id.editTitleTV)).setText(aBarber.barberName);

        name = v.findViewById(R.id.editNameET);
        shop = v.findViewById(R.id.editShopET);
        price = v.findViewById(R.id.editPriceET);
        ratingBar = v.findViewById(R.id.editRatingBar);
        editFavourite = v.findViewById(R.id.editFavourite);

        name.setText(aBarber.barberName);
        shop.setText(aBarber.shop);
        price.setText(""+aBarber.price);
        ratingBar.setRating((float)aBarber.rating);

        if (aBarber.favourite == true) {
            editFavourite.setImageResource(R.drawable.goldstar);
            isFavourite = true;
        } else {
            editFavourite.setImageResource(R.drawable.star);
            isFavourite = false;
        }
        return v;
    }

    public void saveBarber(View v) {
        if (mListener != null) {
            String barberName = name.getText().toString();
            String barberShop = shop.getText().toString();
            String barberPriceStr = price.getText().toString();
            double ratingValue = ratingBar.getRating();

            double barberPrice;
            try {
                barberPrice = Double.parseDouble(barberPriceStr);
            } catch (NumberFormatException e)
            {            barberPrice = 0.0;        }

            if ((barberName.length() > 0) && (barberShop.length() > 0) && (barberPriceStr.length() > 0)) {
                aBarber.barberName = barberName;
                aBarber.shop = barberShop;
                aBarber.price = barberPrice;
                aBarber.rating = ratingValue;

                //app.dbManager.update(aBarber);

                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getActivity().getSupportFragmentManager().popBackStack();
                    return;
                }
            }
        } else
            Toast.makeText(getActivity(), "You must Enter Something for Name and Shop", Toast.LENGTH_SHORT).show();
    }

    public void toggle(View v) {

        if (isFavourite) {
            aBarber.favourite = false;
            Toast.makeText(getActivity(), "Removed from your Favourites", Toast.LENGTH_SHORT).show();
            isFavourite = false;
            editFavourite.setImageResource(R.drawable.star);
        } else {
            aBarber.favourite = true;
            Toast.makeText(getActivity(), "Added to your Favourites", Toast.LENGTH_SHORT).show();
            isFavourite = true;
            editFavourite.setImageResource(R.drawable.goldstar);
        }
        app.dbManager.update(aBarber);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void toggle(View v);
        void saveBarber(View v);
    }


}
