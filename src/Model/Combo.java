package Model;

import java.util.ArrayList;
import java.util.List;

public class Combo implements Producto{

	private double descuento;
	
	private String nombreCombo;
	
	private List<Producto> itemsCombo;

//------------- Constructor -----------
	
	public Combo(String nombreCombo, double descuento) {
		super();
		this.nombreCombo = nombreCombo;
		this.descuento = descuento;
		
		this.itemsCombo = new ArrayList<>();
	}
	
	public void agregarItemACombo(Producto itemCombo) {
		
		itemsCombo.add(itemCombo);
		
	}

	public int getPrecio() {

		int precio = 0;
		
		for (Producto producto : itemsCombo) {
			precio += producto.getPrecio();
		}
		
		precio *= 1 - (descuento/100);
		
		return precio;
	}
	
	public List<Producto> getItemsCombo() {

		
		return itemsCombo;
	}
	
	public String getNombre() {

		return nombreCombo;
	}

	public String generarTextoFactura() {

		return null;
	}
	
}
