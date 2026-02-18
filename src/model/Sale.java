package model;

import java.util.ArrayList; 

public class Sale {
	Client client;
	ArrayList<Product> products;
	double amount;

	public Sale(Client client, ArrayList<Product> products, double amount) {
		this.client = client;
		this.products = products;
		this.amount = amount;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Sale [client=" + client.getName() + ", products=" + products + ", amount=" + amount + "]";
	}
}