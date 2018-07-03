package servidor;

import java.io.IOException;

import grpc.RPCServer;

public class Main {
	
	  /**
	   * Main launches the server from the command line.
	   */
	  public static void main(String[] args) throws IOException, InterruptedException {
	    final RPCServer server = new RPCServer();
	    server.start();
	    server.blockUntilShutdown();
	  }
}
