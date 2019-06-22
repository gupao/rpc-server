package com.hugo.v1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by xuyahui on 2019/6/17
 */
public class RpcProxyServer {

    ExecutorService executorService = Executors.newCachedThreadPool();


     public void publisher(Object service, int port){

         ServerSocket serverSocket = null;

         try {
             serverSocket = new ServerSocket(port);
             while (true){
                 Socket socket = serverSocket.accept();
                 executorService.execute(new ProcessorHandler(socket,service));
             }

         } catch (IOException e) {
             e.printStackTrace();
         }finally {
             try {
                 if(serverSocket != null)
                 serverSocket.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }


     }

}
