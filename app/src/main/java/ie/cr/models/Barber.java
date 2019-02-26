package ie.cr.models;

import java.io.Serializable;
import java.util.UUID;

public class Barber implements Serializable
{
	public int barberId;
	public String barberName;
	public String shop;
	public double rating;
	public double price;
	public boolean favourite;

	public Barber() {}

	public Barber(String name, String shop, double rating, double price, boolean fav)
	{
		this.barberName = name;
		this.shop = shop;
		this.rating = rating;
		this.price = price;
		this.favourite = fav;
	}

	@Override
	public String toString() {
		return barberId + " " + barberName + ", " + shop + ", " + rating
				+ ", " + price + ", fav =" + favourite;
	}
}
