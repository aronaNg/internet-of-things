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
	    System.out.println("impossible de creer server socket : "+e.getMessage());
	    System.exit(1);
	}
	
	try {
	    String line ="";
	    while (true) {
		sock = conn.accept();

		try { 
		    br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		    ps = new PrintStream(sock.getOutputStream());

		    boolean stop = false;
		    while(! stop) {
			line = br.readLine();

			if (line == null) {
			    System.out.println("fermé par le client");
			    stop = true;
			    
			}
			else if (line.equals("")) {
			    System.out.println("signal du client");
			    stop = true;
			}
			else {
		
			    int nb = Integer.parseInt(line);
			    int somme = 0;
			    int x = 0;
			    for(int i=0;i<nb;	i++) {
				line = br.readLine();
				x = Integer.parseInt(line);
				somme += x;
			    }

			    System.out.println("envoie "+somme+" au client");
			    ps.println(somme);
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
	    System.out.println("connexion refusee: "+e.getMessage());
	}
    }
}


