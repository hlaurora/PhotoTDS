package main;

import java.awt.EventQueue;

import gui.VentanaEntrada;

public class Lanzador {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaEntrada ventanaEntrada = new VentanaEntrada();
					ventanaEntrada.mostrarVentana();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
