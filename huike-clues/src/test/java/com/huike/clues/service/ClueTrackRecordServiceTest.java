package com.huike.clues.service;

import org.apache.http.protocol.HttpService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@WebServlet("/getData")
class ClueTrackRecordServiceTest extends HttpServlet {



//        int port = 8082;
//
//        try (ServerSocket serverSocket = new ServerSocket(port)) {
//            System.out.println("Server is listening on port " + port);
//
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                System.out.println("New connection from " + clientSocket.getInetAddress().getHostAddress());
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
//                    if (line.isEmpty()) {
//                        break;
//                    }
//                }
//
//                clientSocket.close();
//            }
//        }


        //这是一个服务端，接收前端的请求后返回数据
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // 设置响应内容类型和字符编码
            response.setContentType("application/json;charset=UTF-8");

            // 获取输出流，用于输出响应数据
            PrintWriter out = response.getWriter();

            // 返回数据给前端
            String responseData = "{\"name\": \"张三\"}";
            out.print(responseData);

            // 关闭输出流
            out.flush();
            out.close();
        }

    }

