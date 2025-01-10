package view;

import javax.swing.*;
import java.awt.*;

public class JanelaInicial extends JFrame {

    public JanelaInicial() {
        setTitle("Blackjack - Janela Inicial");
        setSize(1006, 761);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        getContentPane().setBackground(new Color(34, 139, 34));

        setMinimumSize(new Dimension(1006, 761));
        setMaximumSize(new Dimension(1006, 761));
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        escreverNomeJogo(g2d);
        desenharBotoes(g2d);
    }

    public Rectangle getBotaoNovoJogo() {
        int largura = 300;
        int altura = 50;
        int x =  (getWidth() - largura) / 2;
        int y = 350;

        return new Rectangle(x, y, largura, altura);
    }

    public Rectangle getBotaoCarregarJogo() {
        int largura = 300;
        int altura = 50;
        int x =  (getWidth() - largura) / 2;
        int y = 450;

        return new Rectangle(x, y, largura, altura);

    }

    private void escreverNomeJogo(Graphics2D g2d){
        // Configuração do texto
        String black = "Black";
        String jack = "Jack";
        g2d.setFont(new Font("Arial", Font.BOLD, 100));
        FontMetrics fm = g2d.getFontMetrics();

        int larguraBlack = fm.stringWidth(black);
        int larguraJack = fm.stringWidth(jack);
        int xCentro = (getWidth() - (larguraBlack + larguraJack)) / 2;


        // Colocando cor e desenhando
        g2d.setColor(Color.BLACK);
        g2d.drawString(black, xCentro, 200);

        // Colocando cor e desenhando
        g2d.setColor(Color.RED);
        g2d.drawString(jack, xCentro + larguraBlack, 200);
    }

    private void desenharBotoes(Graphics2D g2d) {
        // Configuração
        Color corBotao = new Color(0, 100, 0);
        Color corBorda = Color.WHITE;
        Font fonteBotao = new Font("Arial", Font.PLAIN, 30);
        g2d.setFont(fonteBotao);
        FontMetrics fm = g2d.getFontMetrics();

        // Obtendo os retangulos
        Rectangle btNovoJogo = getBotaoNovoJogo();
        Rectangle btCarregarJogo = getBotaoCarregarJogo();

        // Desenhando os retângulos
        g2d.setColor(corBotao);
        g2d.fill(btNovoJogo);
        g2d.fill(btCarregarJogo);

        g2d.setColor(corBorda);
        g2d.draw(btNovoJogo);
        g2d.draw(btCarregarJogo);

        // Configuração do texto
        String txtNovoJogo = "Novo Jogo";
        String txtCarregarJogo = "Carregar partida";
        escreverTextoBotoes(g2d, txtNovoJogo, btNovoJogo, fm);
        escreverTextoBotoes(g2d, txtCarregarJogo, btCarregarJogo, fm);
    }

    private void escreverTextoBotoes(Graphics2D g2d, String texto, Rectangle rect, FontMetrics fm){
        Color corTexto = Color.WHITE;

        int larguraTexto = fm.stringWidth(texto);
        int alturaTexto = fm.getAscent();

        int larguraBotao = rect.width;
        int alturaBotao = rect.height;

        int xTexto = rect.x + (larguraBotao - larguraTexto) / 2;
        int yTextoFinal = rect.y + ((alturaBotao - alturaTexto) / 2) + alturaTexto;
        g2d.setColor(corTexto);
        g2d.drawString(texto, xTexto, yTextoFinal);
    }


}



