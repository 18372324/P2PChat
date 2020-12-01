package main;

import java.awt.EventQueue;

import view.LoginFrame;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            try {
            	LoginFrame lf = new LoginFrame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}
}
