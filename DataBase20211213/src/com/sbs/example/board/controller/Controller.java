package com.sbs.example.board.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.sbs.example.board.session.Session;

public abstract class Controller {
	Connection conn;
	Scanner sc;
	String cmd;
	Session ss;
	public abstract void doAction();
}
