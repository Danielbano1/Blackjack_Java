package controller;

import model.Carta;
import model.MaoJogador;
import model.Partida;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SalvarPartida {
    private static String caminhoPasta = "partidas_salvas/";
    private static int contador = 1;

    public static void salvarPartida(Partida partida){
        try (PrintWriter writer = new PrintWriter(caminhoPasta + "Partida_" + Integer.toString(contador))) {
            writer.write(listaCartas2Texto(partida.getBaralho().getCartas()));
            writer.write(";");
            writer.write(Integer.toString(partida.getBaralho().getCartasUsadas()));
            writer.write(";");
            writer.write(Boolean.toString(partida.isSplit()));
            writer.write(";");
            writer.write((Integer.toString(partida.getDinheiro())));
            writer.write(";");
            writer.write(Integer.toString(partida.getTurnos()));
            writer.write(";");
            writer.write(listaCartas2Texto(partida.getMaoDealer().getLista_cartas()));
            writer.write(";");
            writer.write(Integer.toString(((MaoJogador)partida.getMaos().get(0)).getAposta()));
            writer.write(";");
            writer.write(listaCartas2Texto(partida.getMaos().get(0).getLista_cartas()));
            if(partida.isSplit()) {
                writer.write(";");
                writer.write(Integer.toString(((MaoJogador) partida.getMaos().get(1)).getAposta()));
                writer.write(";");
                writer.write(listaCartas2Texto(partida.getMaos().get(1).getLista_cartas()));
            }
            writer.write(";");

            System.out.println("Arquivo escrito com sucesso!");
            contador += 1;
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public static List<Object> carregarPartida(int nome){
        List<Object> retorno = new ArrayList<Object>();

        try {
            String conteudo = Files.readString(Paths.get(caminhoPasta + "Partida_" + Integer.toString(nome)));
            String[] partes = conteudo.split(";");
            retorno.add(texto2ListaCartas(partes[0]));
            retorno.add(Integer.parseInt(partes[1]));
            retorno.add(Boolean.parseBoolean(partes[2]));
            retorno.add(Integer.parseInt(partes[3]));
            retorno.add(Integer.parseInt(partes[4]));
            retorno.add(texto2ListaCartas(partes[5]));
            retorno.add(Integer.parseInt(partes[6]));
            retorno.add(texto2ListaCartas(partes[7]));
            
            if(Boolean.parseBoolean(partes[2])) {
            	System.out.println("AA" + Boolean.parseBoolean(partes[2]));
                retorno.add(Integer.parseInt(partes[8]));
                retorno.add(texto2ListaCartas(partes[9]));
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return retorno;
    }

    private static String listaCartas2Texto(List<Carta> cartas){
        StringBuilder retorno = new StringBuilder();
        for(Carta carta : cartas) {
            retorno.append(carta.getNaipe());
            retorno.append(".");
            retorno.append(Integer.toString(carta.getValor()));
            retorno.append(",");
        }

        return retorno.toString();
    }

    private static List<Carta> texto2ListaCartas (String texto){
        List<Carta> cartas = new ArrayList<Carta>();
        // Dividir o texto pelo separador de cartas (vírgula)
        String[] partes = texto.split(",");
        for (String parte : partes) {
            // Dividir cada carta no formato naipe.valor
            String[] naipeEValor = parte.split("\\.");

            // Extrair o naipe e o valor
            String naipe = naipeEValor[0];
            int valor = Integer.parseInt(naipeEValor[1]);

            // Criar a carta e adicionar à lista
            cartas.add(new Carta(naipe, valor));
        }
        return cartas;
    }


}
