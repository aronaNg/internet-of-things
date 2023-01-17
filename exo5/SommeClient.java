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
	    System.out.println("connexion serveur impossible: "+e.getMessage());
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
		if (line == null) {
		    line = "";
		    stop = true;
		    ps.println(line);		    
		}
		else if (line.equals("")) {
		    stop = true;
		    ps.println(line);		    
		}
		else {
		    String[] lstInt = line.split(",");		    
		    int x = 0;
		    boolean ok = true;
		    for(int i=0;i<lstInt.length;i++) {
			try {
			    x = Integer.parseInt(lstInt[i]);
			}
			catch(NumberFormatException e) {
			    ok = false;
			}
			if (!ok) break; 
		    }

		    if (ok) {
			ps.println(lstInt.length);
			for(int i=0;i<lstInt.length;i++) {
			    ps.println(lstInt[i]);
			}

			line = br.readLine();
			System.out.println("somme = "+line);
		    }
		    else {
			System.out.println("requete malformée!");
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

