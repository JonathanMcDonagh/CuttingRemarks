package ie.cr.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import ie.cr.R;
import ie.cr.models.Barber;

public class BarberItem {
    public View view;

    public BarberItem(Context context, ViewGroup parent,
                      View.OnClickListener deleteListener, Barber barber)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.barbercard, parent, false);
        view.setId(barber.barberId);

        updateControls(barber);

        ImageView imgDelete = view.findViewById(R.id.rowDeleteImg);
        imgDelete.setTag(barber);
        imgDelete.setOnClickListener(deleteListener);
    }

    private void updateControls(Barber barber) {
        ((TextView) view.findViewById(R.id.rowBarberName)).setText(barber.barberName);

        ((TextView) view.findViewById(R.id.rowBarberShop)).setText(barber.shop);
        ((TextView) view.findViewById(R.id.rowRating)).setText(barber.rating + " ⭐");
        ((TextView) view.findViewById(R.id.rowPrice)).setText("€" +
                new DecimalFormat("0.00").format(barber.price));

        ImageView imgIcon = view.findViewById(R.id.rowFavouriteImg);

        if (barber.favourite == true)
            imgIcon.setImageResource(R.drawable.goldstar);
        else
            imgIcon.setImageResource(R.drawable.star);


    }
}