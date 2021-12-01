package Board.controller;

import java.util.Scanner;

public abstract class Controller {
	Scanner sc = new Scanner(System.in);
	public abstract void doAction(String actionMedthod);
}
