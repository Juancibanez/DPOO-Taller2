package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

	private static int numeroPedidos = 0;
	
	private int idPedido;
	
	private String nombreCliente;
	
	private String direccionCliente;
	
	private List<Producto> itemsPedido;

	
//----------- Constructor --------------
	
	public Pedido(String nombreCliente, String direccionCliente) {

		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.itemsPedido = new ArrayList<>();
		this.idPedido = numeroPedidos;
	}

//----------- Métodos --------------
	
	public int getIdPedido() {
		return idPedido;
	}

	public void agregarProducto(Producto nuevoItem) {
		
		itemsPedido.add(nuevoItem);

	}
	
	
	private int getPrecioNetoPedido() {
		
		int precio = 0;
		
		for(Producto producto: itemsPedido) {
			
			precio += producto.getPrecio();
			
		}
		
		return precio;
	}
	
	private int getPrecioTotalPedido() {

		return getPrecioNetoPedido() + getPrecioIVAPedido();
	}

	private int getPrecioIVAPedido() {
		
		return (int) (getPrecioNetoPedido() * 0.19);
	}
	
	private String generarTextoFactura() {
		
		String textoFactura = "factura\n\nPedido " + idPedido + "\nNombre: " + nombreCliente + "\nDirección: " + direccionCliente + "\n";
		
		for(Producto producto: itemsPedido) {
			
			textoFactura += producto.generarTextoFactura();
			
		}
		
		textoFactura += "\n\nPrecio neto: $" + getPrecioNetoPedido();
		textoFactura += "\n\nPrecio iva: $" + getPrecioIVAPedido();
		textoFactura += "\n\nPrecio total: $" + getPrecioTotalPedido();
		
		return textoFactura;
	}
	
	
	public void guardarFactura(File archivo) throws IOException {
		
		PrintWriter pw = new PrintWriter(new FileWriter(archivo));
		pw.println(generarTextoFactura());
		pw.close();
		
	}
	
	public static void incrementarNumeroPedidos() {
		numeroPedidos++;
	}
}
