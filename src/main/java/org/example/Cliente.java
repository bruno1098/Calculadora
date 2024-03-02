package org.example;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    static Conexao c;
    static Socket socket;

    public Cliente (){
        try {
            socket = new Socket("localhost", 9600); // localhost = 127.0.0.1
        }catch (Exception e){
            System.err.println("erro host");
        }
    }

    public static void main(String[] args) throws IOException{
        try{
            new Cliente();
            float op1, op2;
            char operacao;
            Scanner in = new Scanner(System.in);

            System.out.println("********************************");
            System.out.println("*** Calculadora Distribuida ****");
            System.out.println("********************************");

            System.out.println("Numero1: ");
            op1 = in.nextFloat();

            System.out.println("Numero2: ");
            op2 = in.nextFloat();

            System.out.println("Esolha uma operação: ");
            System.out.println("(+)SOMA (-)SUBTRAÇÃO (*)MULTIPLICAÇÃO (/)DIVISÃO  ");
            operacao = in.next().charAt(0);

            Requisicao msgReq = new Requisicao(op1, op2, operacao);
            c.send(socket, msgReq);
            Resposta msgRep = (Resposta) c.receive(socket);

            switch (msgRep.getStatus()){
                case 0:
                    System.out.println("resultado: " + msgRep.getResult());
                    break;
                case 1:
                    System.out.println("Opeação não implementada");
                    break;
                default:
                    System.out.println("Divisão por 0");
                    break;
            }

        }catch (Exception e){
            System.out.println("Problemas ao fechar o socket");
        }finally {
            socket.close();
        }
    }

}
