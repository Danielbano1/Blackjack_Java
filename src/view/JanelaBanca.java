package view;

import javax.swing.*;
import java.awt.*;

public class JanelaBanca extends JFrame {
    private Image fundo;
    private Image[] fichas = new Image[6];
    private Rectangle[] fichasBounds = new Rectangle[6];

    public JanelaBanca(){
        setTitle("Blackjack - Janela Banca");
        setSize(1006, 761);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        setMinimumSize(new Dimension(1006, 761));
        setMaximumSize(new Dimension(1366, 768));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        carregarImagens();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        desenhaImagens(g2d);
    }

    private void carregarImagens() {
        // Lista de imagens
        String[] nomesDasFichas = {"ficha1.png", "ficha5.png", "ficha10.png", "ficha20.png",
                "ficha50.png", "ficha100.png"};
        String nomeDoFundo = "blackjack.png";

        // Carregar a imagen do fundo usando Toolkit
        fundo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/" + nomeDoFundo));

        // Carregar as imagens das fichas usando Toolkit
        for (int i = 0; i < nomesDasFichas.length; i++) {
            fichas[i] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/" + nomesDasFichas[i]));
        }
    }

    // Tambem cria os retangulos das fichas
    private void desenhaImagens(Graphics2D g2d){
        // Desenha o fundo
        if (fundo != null) {
            g2d.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
        }

        // Desenha as fichas
        int largura = 60, altura = 60, espacamento = 5;
        int total = fichas.length * largura + (fichas.length - 1) * espacamento;
        int xInicio = (getWidth() - total) / 2;
        int y = 45;

        for (int i = 0; i < fichas.length; i++) {
            if (fichas[i] != null) {
                int x = xInicio + i * (largura + espacamento);
                g2d.drawImage(fichas[i], x, y, largura, altura, this);
                fichasBounds[i] = new Rectangle(x, y, largura, altura);
            }
        }
    }

    public Rectangle[] getFichaBounds(){
        return fichasBounds;
    }

}
