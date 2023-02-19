package View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Controller.Restaurante;
import Model.Combo;
import Model.Ingrediente;
import Model.Producto;
import Model.ProductoAjustado;
import Model.ProductoMenu;

public class Aplicacion {
	
	private Restaurante restaurante;
	
	public void mostrarMenu()
	{
		System.out.println("\nOpciones de la aplicación\n");
		System.out.println("1. Cargar datos");
		System.out.println("2. Iniciar un nuevo pedido");
		System.out.println("3. Agregar un elemento a un pedido");
		System.out.println("4. Cerrar un pedido y guardar la factura");
		System.out.println("5. Consultar información de pedido según ID");
		System.out.println("6. Salir de la aplicación");
	}
	
	public void ejecutarOpcion() 
	{
		System.out.println("Hamburguesas");

		boolean continuar = true;
		while (continuar)
		{
			try
			{
				mostrarMenu();
				int opcion_seleccionada = Integer.parseInt(input("\nPor favor seleccione una opción"));
				if (opcion_seleccionada == 1) {
					ejecutarCargarInformacion();}
				else if (opcion_seleccionada == 2) {
					ejecutarNuevoPedido();}
				else if (opcion_seleccionada == 3) {
					ejecutarAgregarElemento();}
				else if (opcion_seleccionada == 4) {
					ejecutarCerrarYGuardar();}
				else if (opcion_seleccionada == 5) {
					ejecutarPedidoPorId();}
				else if (opcion_seleccionada == 6){
					System.out.println("\nSaliendo de la aplicación ...");
					continuar = false;}
				else
				{
					System.out.println("\nPor favor seleccione una opción válida.");
				}
			}
			
			catch (NumberFormatException e)
			{
				System.out.println("\nDebe seleccionar uno de los números de las opciones.");
			}
		}
	}
		
	private void ejecutarCargarInformacion() {
	
		this.restaurante = new Restaurante();
		
		File menu = new File("data/menu.txt");
		File ingredientes = new File("data/ingredientes.txt");
		File combos = new File("data/combos.txt");
		
		try {
			restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);
			System.out.println("\nCarga iniciada correctamente");
		} catch (FileNotFoundException e) {

			System.out.println("El archivo indicado no se encontró");
		} catch (IOException e) {

			System.out.println("Hubo un problema leyendo el archivo");
			System.out.println(e.getMessage());
		}
		
	}
	
	private void ejecutarNuevoPedido() {
		
		if (restaurante.getPedidoEnCurso() != null){
			
			System.out.println("Ya hay un pedido en curso");
			return;
		}
		
		String nombreCliente = input("\nPor favor ingrese su nombre");
		String direccionCliente = input("Por favor ingrese su direccion");
		
		restaurante.iniciarPedido(nombreCliente, direccionCliente);
		System.out.println("\nPedido iniciado correctamente");
		
	}
	
	private void ejecutarAgregarElemento() {

		mostrarMenuRestaurante();
		int agregado = Integer.parseInt(input("Seleccione un item para agregar (1. Producto, 2. Combo)"));
		
		if (agregado == 1){
			
			String nombreProducto = input("Ingrese el nombre del producto que quiere agregar");
			ProductoMenu productoBase = restaurante.buscarProducto(nombreProducto);
			if (productoBase == null) {
				System.out.println("Producto inválido");
				return;
			}
			
			List<Ingrediente> ingredientesAgregadosCompletos = new ArrayList<>();
			List<Ingrediente> ingredientesEliminadosCompletos = new ArrayList<>();
			
			int opcionAgregar = Integer.parseInt(input("¿Quiere agregar algún ingrediente? (1. Sí, 2. No)"));
			
			if (opcionAgregar == 1) {
			
				String ingredientesAgregados = input("Ingrese los ingredientes a agregar (Enter para saltar || Agregar separado por comas)");
				String[] listaIngredientesAgregados = ingredientesAgregados.split(",");
				
				
				for(String ingredienteAgregado: listaIngredientesAgregados) {
					
					Ingrediente ingredienteAgregadoCompleto = restaurante.buscarIngrediente(ingredienteAgregado);
					if (ingredienteAgregadoCompleto == null) {
						System.out.println("Ingrediente inválido");
						return;
					}
					
					ingredientesAgregadosCompletos.add(ingredienteAgregadoCompleto);
				}
			}
			
			int opcionEliminar = Integer.parseInt(input("¿Quiere eliminar algún ingrediente? (1. Sí, 2. No)"));
			
			if (opcionEliminar == 1) {
			
				String ingredientesEliminados = input("Ingrese los ingredientes a eliminar (Enter para saltar || Agregar separado por comas)");
				String[] listaIngredientesEliminados = ingredientesEliminados.split(",");
				
				
				for(String ingredienteEliminado: listaIngredientesEliminados) {
					
					Ingrediente ingredienteEliminadoCompleto = restaurante.buscarIngrediente(ingredienteEliminado);
					if (ingredienteEliminadoCompleto == null) {
						System.out.println("Ingrediente inválido");
						return;
					}
					
					ingredientesEliminadosCompletos.add(ingredienteEliminadoCompleto);
				}
			}
			
			Producto producto = null;
			
			if(ingredientesAgregadosCompletos.size()!= 0 || ingredientesEliminadosCompletos.size()!= 0) {
				
				producto = new ProductoAjustado(productoBase, ingredientesAgregadosCompletos, ingredientesEliminadosCompletos);
				
			}else{
				
				producto = productoBase;
				
			}
			
			restaurante.getPedidoEnCurso().agregarProducto(producto);
			
		}else{
			
			String nombreCombo = input("Ingrese el nombre del combo que quiere agregar");
			Combo combo = restaurante.buscarCombo(nombreCombo);
			if (combo == null) {
				System.out.println("Producto inválido");
				return;
			}
			restaurante.getPedidoEnCurso().agregarProducto(combo);
		}
		System.out.println("Producto agregado correctamente");
	}
	
	private void mostrarMenuRestaurante() {
		
		for(ProductoMenu producto: restaurante.getMenuBase()) {
			
			System.out.println(producto.getNombre() + ": $" + producto.getPrecio());
		}
		for(Ingrediente ingrediente: restaurante.getIngredientes()) {
			
			System.out.println(ingrediente.getNombre() + ": $" + ingrediente.getCostoAdicional());
		}
		for(Combo combo: restaurante.getCombos()) {
	
			System.out.println(combo.getNombre());
			
			for(Producto productoCombo: combo.getItemsCombo()) {
				
				System.out.println(productoCombo.getNombre());
			}
			System.out.println(combo.getPrecio());
		}
		
	}

	private void ejecutarCerrarYGuardar() {
		
		try {
			
			restaurante.cerrarYGuardarPedido();
			System.out.println("\nSu pedido se ha realizado exitosamente. Puede consultar su factura.");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void ejecutarPedidoPorId() {

	}
	
	
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		Aplicacion consola = new Aplicacion();
		consola.ejecutarOpcion();
	}

}
