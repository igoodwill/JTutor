package org.igoodwill.jtutorsb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Controller
public class AppController {

	@GetMapping({ "/", "/home" })
	public String homePage(final Model model) {
		
		try {
            		ServerSocket server = new ServerSocket(8953);
            		Socket socket = server.accept();

            		DataInputStream input = new DataInputStream(socket.getInputStream());
            		System.out.println(input.readUTF());

			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
        	}
		
		return "index";
	}
}
