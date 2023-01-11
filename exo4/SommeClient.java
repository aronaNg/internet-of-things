import java.io.*;
import java.net.*;
 
class SommeClient  {
 
    public static void main(String []args) {

	if (args.length != 2) {
	    System.err.println("usage : java SommeClient ip port");
	    System.exit(1);
	}

	Socket sock = null;
	PrintStream ps = null;
	BufferedReader br = null;
	try {
	    sock = new Socket(args[0],Integer.parseInt(args[1]));
	    ps = new PrintStream(sock.getOutputStream());
	    br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	}
	catch(IOException e) {
	    System.out.println("cannot connect to server, or create streams: "+e.getMessage());
	    System.exit(1);
	}

	// le flux pour lire des lignes au clavier  
	BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
	try {
	    String line ="@";
	    boolean stop = false;
	    while(! stop) {
		System.out.print("entiers separé par des virgules:");
		line = consoleIn.readLine();
		// in case of ctrl+d : line <- null
		if (line == null) {
		    line = "";
		    stop = true;
		}
		else  if (line.equals("")) {
		    stop = true;
		}
		// send even empty string to signal the end to the server
		ps.println(line);
		// recv server answer if not stop
		if (!stop) {
		    line = br.readLine();
		    if (line.equals("REQ_ERR")) {
			System.out.println("malformed request: server rejected it !");
		    }
		    else {
			System.out.println("somme = "+line);
		    }
		}		
	    }
	    consoleIn.close();
	    ps.close();
	    br.close();
	}
	catch(IOException e) {
	    System.out.println(e.getMessage());
	}
    }
}
