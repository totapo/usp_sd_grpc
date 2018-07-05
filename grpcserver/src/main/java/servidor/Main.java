package servidor;

import java.io.IOException;

import grpc.RPCServer;

public class Main {

	/**
	 * Main launches the server from the command line.
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// args 0 = porta servidor
		if (args.length > 0) {
			final RPCServer server = new RPCServer();
			server.start(Integer.parseInt(args[0]));
			server.blockUntilShutdown();
		}
	}
}
