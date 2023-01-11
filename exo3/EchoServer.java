import java.io.*;
import java.net.*;
 
class EchoServer  {
 
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

		try { 
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
			    System.out.println("écris "+line+" au client");
			    ps.println(line);
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


