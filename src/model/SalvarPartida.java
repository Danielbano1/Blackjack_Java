package model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SalvarPartida {

    public static void salvarPartida(Partida partida) {
        File arquivo = escolherArquivoParaSalvar();
        if (arquivo == null) return;

        // Garante que o arquivo tenha a extensão .txt
        if (!arquivo.getName().toLowerCase().endsWith(".txt")) {
            arquivo = new File(arquivo.getAbsolutePath() + ".txt");
        }

        try (PrintWriter writer = new PrintWriter(arquivo)) {
            writer.write(listaCartas2Texto(partida.getBaralho().getCartas()));
            writer.write(";");
            writer.write(Integer.toString(partida.getBaralho().getCartasUsadas()));
            writer.write(";");
            writer.write(Boolean.toString(partida.isSplit()));
            writer.write(";");
            writer.write(Integer.toString(partida.getDinheiro()));
            writer.write(";");
            writer.write(Integer.toString(partida.getTurnos()));
            writer.write(";");
            writer.write(listaCartas2Texto(partida.getMaoDealer().getLista_cartas()));
            writer.write(";");
            writer.write(Integer.toString(((MaoJogador) partida.getMaos().get(0)).getAposta()));
            writer.write(";");
            writer.write(listaCartas2Texto(partida.getMaos().get(0).getLista_cartas()));
            
            if (partida.isSplit()) {
                writer.write(";");
                writer.write(Integer.toString(((MaoJogador) partida.getMaos().get(1)).getAposta()));
                writer.write(";");
                writer.write(listaCartas2Texto(partida.getMaos().get(1).getLista_cartas()));
            }
            writer.write(";");

            JOptionPane.showMessageDialog(null, "Jogo salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar o jogo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static List<Object> carregarPartida() {
        File arquivo = escolherArquivoParaCarregar();
        if (arquivo == null) return null;

        List<Object> retorno = new ArrayList<>();
        try {
            String conteudo = Files.readString(Paths.get(arquivo.getAbsolutePath()));
            String[] partes = conteudo.split(";");
            retorno.add(texto2ListaCartas(partes[0]));
            retorno.add(Integer.parseInt(partes[1]));
            retorno.add(Boolean.parseBoolean(partes[2]));
            retorno.add(Integer.parseInt(partes[3]));
            retorno.add(Integer.parseInt(partes[4]));
            retorno.add(texto2ListaCartas(partes[5]));
            retorno.add(Integer.parseInt(partes[6]));
            retorno.add(texto2ListaCartas(partes[7]));
            
            if (Boolean.parseBoolean(partes[2])) {
                retorno.add(Integer.parseInt(partes[8]));
                retorno.add(texto2ListaCartas(partes[9]));
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar o jogo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return retorno;
    }

    private static File escolherArquivoParaSalvar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha onde salvar a partida");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Aplica o filtro para arquivos TXT
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        int escolha = fileChooser.showSaveDialog(null);
        if (escolha == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    private static File escolherArquivoParaCarregar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha o arquivo da partida");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Aplica o filtro para arquivos TXT
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        int escolha = fileChooser.showOpenDialog(null);
        if (escolha == JFileChooser.APPROVE_OPTION) {
            File arquivoSelecionado = fileChooser.getSelectedFile();
            if (!arquivoSelecionado.getName().toLowerCase().endsWith(".txt")) {
                JOptionPane.showMessageDialog(null, "Selecione um arquivo .txt válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            return arquivoSelecionado;
        }
        return null;
    }

    private static String listaCartas2Texto(List<Carta> cartas) {
        StringBuilder retorno = new StringBuilder();
        for (Carta carta : cartas) {
            retorno.append(carta.getNaipe()).append(".");
            retorno.append(carta.getValor()).append(",");
        }
        return retorno.toString();
    }

    private static List<Carta> texto2ListaCartas(String texto) {
        List<Carta> cartas = new ArrayList<>();
        String[] partes = texto.split(",");
        for (String parte : partes) {
            String[] naipeEValor = parte.split("\\.");
            String naipe = naipeEValor[0];
            int valor = Integer.parseInt(naipeEValor[1]);
            cartas.add(new Carta(naipe, valor));
        }
        return cartas;
    }
}