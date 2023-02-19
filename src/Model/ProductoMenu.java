package Model;

import java.util.ArrayList;

public class ProductoMenu implements Producto{
	
//-------------------- Atributos -------------------

	private String nombre;
	
	private int precioBase;
	
//-------------------- Constructor -------------------

	public ProductoMenu(String nombre, int precioBase)
	{
		this.nombre = nombre;
		this.precioBase = precioBase;
	}
	
//-------------------- Métodos --------------------

	public String getNombre()
	{
		return nombre;
	}
	
	public int getPrecio()
	{
		return precioBase;
	}
	
	public String generarTextoFactura()
	{
		return "\n- " + getNombre() + ": $" + getPrecio();
		
	}

	
}
