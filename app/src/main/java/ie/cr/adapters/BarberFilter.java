package ie.cr.adapters;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import ie.cr.models.Barber;

public class BarberFilter extends Filter {
	public List<Barber> originalBarberList;
	public String filterText;
	public BarberListAdapter adapter;

	public BarberFilter(List<Barber> originalBarberList, String filterText,
						BarberListAdapter adapter) {
		super();
		this.originalBarberList = originalBarberList;
		this.filterText = filterText;
		this.adapter = adapter;
	}

	public void setFilter(String filterText) {
		this.filterText = filterText;
	}

	@Override
	protected FilterResults performFiltering(CharSequence prefix) {
		FilterResults results = new FilterResults();

		List<Barber> newBarbers;
		String barberName;

		if (prefix == null || prefix.length() == 0) {
			newBarbers = new ArrayList<>();
			if (filterText.equals("all")) {
				results.values = originalBarberList;
				results.count = originalBarberList.size();
			} else {
				if (filterText.equals("favourites")) {
					for (Barber c : originalBarberList)
						if (c.favourite)
							newBarbers.add(c);
				}
				results.values = newBarbers;
				results.count = newBarbers.size();
			}
		} else {
			String prefixString = prefix.toString().toLowerCase();
			newBarbers = new ArrayList<>();

			for (Barber c : originalBarberList) {
				barberName = c.barberName.toLowerCase();
				if (barberName.contains(prefixString)) {
					if (filterText.equals("all")) {
						newBarbers.add(c);
					} else if (c.favourite) {
						newBarbers.add(c);
					}}}
			results.values = newBarbers;
			results.count = newBarbers.size();
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void publishResults(CharSequence prefix, FilterResults results) {

		adapter.barberList = (ArrayList<Barber>) results.values;

		if (results.count >= 0)
			adapter.notifyDataSetChanged();
		else {
			adapter.notifyDataSetInvalidated();
			adapter.barberList = originalBarberList;
		}
		Log.v("Cutting Remarks", "publishResults : " + adapter.barberList);
	}
}