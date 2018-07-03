package grpc;
/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import protobufgencode.*;
import servidor.Procedures;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

import com.google.protobuf.Empty;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class RPCServer {
  private static final Logger logger = Logger.getLogger(RPCServer.class.getName());

  private Server server;
 
  public void start() throws IOException {
    /* The port on which the server should run */
    int port = 50051;
    server = ServerBuilder.forPort(port)
        .addService(new EpImpl())
        .build()
        .start();
    logger.info("Server started, listening on " + port);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // Use stderr here since the logger may have been reset by its JVM shutdown hook.
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        RPCServer.this.stop();
        System.err.println("*** server shut down");
      }
    });
  }

  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon threads.
   */
  public void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  static class EpImpl extends EPGrpc.EPImplBase {

	@Override
	public void testVoid(Empty request, StreamObserver<Empty> responseObserver) {
		responseObserver.onNext(request);
		responseObserver.onCompleted();
	}

	@Override
	public void testLong(LongRequest request, StreamObserver<LongResponse> responseObserver) {
		long entrada = request.getNumero();
		long resposta = Procedures.longRequest(entrada);
		LongResponse reply = LongResponse.newBuilder().setResposta(resposta).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public void testEightLong(EightLongRequest request, StreamObserver<LongResponse> responseObserver) {
		long resposta = Procedures.eightLongRequest(request.getParam1(), request.getParam2(), request.getParam3(), request.getParam4(), request.getParam5(), request.getParam6(), request.getParam7(), request.getParam8());
		LongResponse reply = LongResponse.newBuilder().setResposta(resposta).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public void testEightLongArray(EightLongArrayRequest request, StreamObserver<LongResponse> responseObserver) {
		long resposta = Procedures.eightLongArrayRequest(request.getArrayList());
		LongResponse reply = LongResponse.newBuilder().setResposta(resposta).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public void testStringLength(StringRequest request, StreamObserver<StringResponse> responseObserver) {
		String resposta = Procedures.testStringLenghtRequest(request.getReq());
		StringResponse reply = StringResponse.newBuilder().setResp(resposta).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public void testComplexType(ComplexRequest request, StreamObserver<ComplexResponse> responseObserver) {
		Resultado resposta = Procedures.testComplex(request.getTurma());
		ComplexResponse reply = ComplexResponse.newBuilder().setResultado(resposta).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public void testRemoteCollection(CollectionRequest request, StreamObserver<CollectionResponse> responseObserver) {
		Collection<Aluno> resposta = Procedures.testCollections(request.getAlunosList());
		CollectionResponse reply = CollectionResponse.newBuilder().addAllAlunos(resposta).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public void testManyArguments(ArgumentsRequest request, StreamObserver<Empty> responseObserver) {
		Empty resposta = Empty.newBuilder().build();
		responseObserver.onNext(resposta);
		responseObserver.onCompleted();
	}

	@Override
	public void testException(Empty request, StreamObserver<Empty> responseObserver) {
		Procedures.testException();
		Empty resposta = Empty.newBuilder().build();
		responseObserver.onNext(resposta);
		responseObserver.onCompleted();
	}
 
	 
	  
    /*@Override
    public void sayHello(HelloRequest req, StreamObserver<HelloResponse> responseObserver) {
      HelloResponse reply = HelloResponse.newBuilder().setMessage("Hello " + req.getName()).build();
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }*/
  }
}