import java.io.*;
import java.net.*;
 
class EchoClient  {
 
    public static void main(String []args) {

	if (args.length != 2) {
	    System.err.println("usage : java EchoClient ip port");
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
	    System.out.println("problème de connexion au serveur: "+e.getMessage());
	    System.exit(1);
	}

	// le flux pour lire des lignes au clavier  
	BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
	try {
	    String line ="@";
	    boolean stop = false;
	    while(! stop) {
		System.out.print("mesage à afficher:");
		line = consoleIn.readLine();
		// dans la cas crtl + d
		if (line == null) {
		    line = "";
		    stop = true;
		}
		else  if (line.equals("")) {
		    stop = true;
		}
		// dans le cas ligne vide
		
		ps.println(line);
		if (!stop) {
		    line = br.readLine();
		    System.out.println("reponses serveur: "+line);
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
