package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import javax.imageio.ImageIO;
import controller.Controller;

public class JanelaBanca extends JFrame {
    private Image fundo;
    private Image[] fichas = new Image[6];
    private Rectangle[] fichaBounds = new Rectangle[6];
    private String placarTexto = null; // Texto do placar
    private boolean exibirPlacar = false; // Controle de exibição do placar


    public JanelaBanca() {
        configurarJanela();
        carregarImagens();
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("Blackjack - Mesa da Banca");
        setSize(1006, 761);
        setMinimumSize(new Dimension(1006, 761));
        setMaximumSize(new Dimension(1366, 768));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void carregarImagens() {
        String[] paths = { "resources/blackjack.bmp", "resources/ficha1.png", "resources/ficha5.png",
                "resources/ficha10.png", "resources/ficha20.png", "resources/ficha50.png", "resources/ficha100.png" };
        try {
            fundo = ImageIO.read(new File(paths[0]));
            for (int i = 1; i < paths.length; i++) {
                fichas[i - 1] = ImageIO.read(new File(paths[i]));
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagens: " + e.getMessage());
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        if (fundo != null) {
            g2.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
        }

        int largura = 60, altura = 60, espacamento = 5;
        int total = fichas.length * largura + (fichas.length - 1) * espacamento;
        int xInicio = (getWidth() - total) / 2;
        int y = 45;

        for (int i = 0; i < fichas.length; i++) {
            if (fichas[i] != null) {
                int x = xInicio + i * (largura + espacamento);
                g2.drawImage(fichas[i], x, y, largura, altura, this);
                fichaBounds[i] = new Rectangle(x, y, largura, altura);
            }
        }
        // Desenha o placar se necessário
        if (exibirPlacar && placarTexto != null) {
            g2.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 24));
            g2.setColor(Color.YELLOW);
            int xTexto = (getWidth() / 2) - 150;
            int yTexto = getHeight() - 300;
            g2.drawString(placarTexto, xTexto, yTexto);
        }
    }

    public Rectangle[] getFichaBounds() {
        return fichaBounds;
    }

    public void exibePlacar(String resultado) {
        // Define o texto do placar com base no resultado
        placarTexto = resultado;
        exibirPlacar = true;

        // Repaint para atualizar a tela
        repaint();
    }
}