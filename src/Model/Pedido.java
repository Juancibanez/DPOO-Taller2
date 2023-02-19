package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

	private int numeroPedidos;
	
	private int idPedido;
	
	private String nombreCliente;
	
	private String direccionCliente;
	
	private List<Producto> itemsPedido;

	
//----------- Constructor --------------
	
	public Pedido(String nombreCliente, String direccionCliente) {

		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.itemsPedido = new ArrayList<>();
	}

//----------- Métodos --------------
	
	public int getIdPedido() {
		return idPedido;
	}

	public void agregarProducto(Producto nuevoItem) {
		
		itemsPedido.add(nuevoItem);

	}
	
	
	private int getPrecioNetoPedido() {
		return 0;
	}
	
	private int getPrecioTotalPedido() {
		return 0;
	}

	private int getPrecioIVAPedido() {
		return 0;
	}
	
	private String generarTextoFactura() {
		return null;
	}
	
	
	public void guardadFactura(File archivo) {
		
	}
	
}
