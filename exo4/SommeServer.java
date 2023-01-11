import java.io.*;
import java.net.*;
 
class SommeServer  {
 
    public static void main(String []args) {

	if (args.length != 1) {
	    System.err.println("usage : java EchoServer port");
	    System.exit(1);
	}
	
	ServerSocket conn = null;
	Socket sock = null;
	BufferedReader br = null;
	PrintStream ps = null;
	
	try {
	    conn = new ServerSocket(Integer.parseInt(args[0]));
	}
	catch(IOException e) {
	    System.out.println("cannot create server socket : "+e.getMessage());
	    System.exit(1);
	}
	
	try {
	    String line ="";
	    while (true) {
		sock = conn.accept();

		try { // dedicated try for communiation with client
		    br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		    ps = new PrintStream(sock.getOutputStream());

		    boolean stop = false;
		    while(! stop) {
			line = br.readLine();
			// femeture subite
			if (line == null) {
			    System.out.println("fermé par client");
			    stop = true;
			    
			}
			else if (line.equals("")) {
			    System.out.println("signal du client");
			    stop = true;
			}
			else {
			    // compute the somme
			    String[] lstInt = line.split(",");
			    int somme = 0;
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
				somme += x;
			    }
			    if (ok) {
				System.out.println("envoie "+somme+" au client");
				ps.println(somme);
			    }
			    else {
				ps.println("REQ_ERR");
			    }
			}
		    }
		    br.close();
		    ps.close();		    
		}
		catch(IOException e) {
		    System.out.println("communication impossible avec le client: "+e.getMessage());
		}
	    }
	}
	catch(IOException e) {
	    System.out.println("cannot accept connections: "+e.getMessage());
	}
    }
}


