package ie.cr.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ie.cr.R;
import ie.cr.models.Barber;

public class BarberListAdapter extends ArrayAdapter<Barber>
{
    private Context context;
    private View.OnClickListener deleteListener;
    public List<Barber> barberList;

    public BarberListAdapter(Context context, View.OnClickListener deleteListener, List<Barber> barberList)
    {
        super(context, R.layout.barberrow, barberList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.barberList = barberList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BarberItem item = new BarberItem(context, parent,
                                         deleteListener, barberList.get(position));
        return item.view;
    }

    @Override
    public int getCount() {
        return barberList.size();
    }


}
