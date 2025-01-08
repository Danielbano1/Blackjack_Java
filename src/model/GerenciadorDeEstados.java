package model;

public class GerenciadorDeEstados {
    // Estado atual do jogo
    private Estado estadoAtual;

    public GerenciadorDeEstados() {
        estadoAtual = Estado.APOSTA;
    }

    public Estado getEstadoAtual() {
        return estadoAtual;
    }

    public void proxEstado() {
        switch (estadoAtual) {
            case APOSTA:
                estadoAtual = Estado.JOGO;
                break;

            case JOGO:
                estadoAtual = Estado.DEALER;
                break;

            case DEALER:
                estadoAtual = Estado.FIM;
                break;

            case FIM:
                estadoAtual = Estado.APOSTA;
                break;

        }
    }

    public void setEstadoAtual(Estado novoEstado) {
        this.estadoAtual = novoEstado;
    }

}
