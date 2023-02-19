package Model;

import java.util.List;

public class ProductoAjustado implements Producto{
	
	private ProductoMenu base;
	
	private List<Ingrediente> agregados;
	private List<Ingrediente> eliminados; 

//------------ Constructor ------------
	
	public ProductoAjustado(ProductoMenu base, List<Ingrediente> agregados, List<Ingrediente> eliminados) {
		this.base = base;
		this.agregados = agregados;
		this.eliminados = eliminados;
	}
	
//------------ Métodos ------------

	
	public int getPrecio() {
		
		int precio = base.getPrecio();
		
		for (Ingrediente ingrediente : agregados) {
			precio += ingrediente.getCostoAdicional();
		}
		
		return precio;
	}

	public String getNombre() {
		
		return base.getNombre();
	}

	public String generarTextoFactura() {
		
		return null;
	}

}
