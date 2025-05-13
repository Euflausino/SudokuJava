package br.com.euflausino;

import br.com.euflausino.model.Board;
import br.com.euflausino.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.euflausino.util.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Main {
    private final static Scanner sc = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {
        final var positions = Stream.of(args).collect(Collectors.toMap(
        k -> k.split(";")[0],
                v-> v.split(";")[1]
                ));
        var option = -1;
        while (true){
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            option = sc.nextInt();

            switch (option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções do menu");
        }
        
    }
}

    private static void finishGame() {
        if(isNull(board)){
            System.out.println("O jogo ainda nao foi iniciado!");
            return;
        }
        if(board.gameIfFinixed()){
            System.out.println("Jogo concluido!");
            showCurrentGame();
            board = null;
        }else if (board.hasErrors()){
            System.out.println("Seu jogo contem erros!");
        }else System.out.println("Voce precisa preencher mais espaços.");
    }

    private static void clearGame() {
        if(isNull(board)){
            System.out.println("O jogo ainda nao foi iniciado!");
            return;
        }
        System.out.println("Tem certeza que quer limpar o jogo? (y/n)");
        var confirm = sc.nextLine();
        while (!confirm.equalsIgnoreCase("y") && !confirm.equalsIgnoreCase("n")){
            System.out.println("Informe (y/n)");
            confirm = sc.nextLine();
        }
        if(confirm.equalsIgnoreCase("y")){
            board.reset();
            System.out.println("Jogo resetado.");
        }else System.out.println("Operaçao cancelada.");
    }

    private static void showGameStatus() {
        if(isNull(board)){
            System.out.println("O jogo ainda nao foi iniciado!");
            return;
        }
        System.out.printf("O jogo se encontra no status: %s\n", board.getStatus().getLabel());

        System.out.println(board.hasErrors() ? "O jogo contem erros" : "O jogo nao contem erros.");
    }

    private static void showCurrentGame() {
        if(isNull(board)){
            System.out.println("O jogo ainda nao foi iniciado!");
            return;
        }
        var args = new Object[81];
        var argPos = 0;
       for (int i = 0; i < BOARD_LIMIT; i++) {
           for (var col: board.getSpace()){
               args[argPos ++] = " " + ((isNull(col.get(i).getValAtual()))? " " : col.get(i).getValAtual());
           }
        }
        System.out.println("Seu jogo se encontra da seguinte forma: ");
        System.out.printf((BOARD_TEMPLATE) + "%n", args);
    }

    private static void removeNumber() {
        if(isNull(board)){
            System.out.println("O jogo ainda nao foi iniciado!");
            return;
        }
        System.out.println("Informe a coluna que deseja inserir o numero: ");
        var col = runUntinGetValidNumber(0, 8);
        System.out.println("Informe a linha que deseja inserir o numero: ");
        var row = runUntinGetValidNumber(0, 8);
        if(!board.clearValue(col, row)){
            System.out.printf("A posiçao: [%s,%s] tem um valor fixo", col, row);
        }
    }

    private static void inputNumber() {
        if(isNull(board)){
            System.out.println("O jogo ainda nao foi iniciado!");
            return;
        }
        System.out.println("Informe a coluna que deseja inserir o numero: ");
        var col = runUntinGetValidNumber(0, 8);
        System.out.println("Informe a linha que deseja inserir o numero: ");
        var row = runUntinGetValidNumber(0, 8);
        System.out.printf("Informe o numero que vai entrar na posiçao: [%s,%s]\n", col, row);
        var value = runUntinGetValidNumber(1,9);
        if(!board.changeValue(col, row, value)){
            System.out.printf("A posiçao: [%s,%s] tem um valor fixo", col, row);
        }


    }

    private static void startGame(Map<String, String> positions) {

        if(nonNull(board)){
            System.out.println("O jogo ja foi iniciado!");
            return;
        }

        List<List<Space>> space = new ArrayList<>();
        for(int i=0;i<BOARD_LIMIT;i++){
            space.add(new ArrayList<>());
            for (int j=0;j<BOARD_LIMIT;j++){
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                space.get(i).add(currentSpace);
            }
        }
        board = new Board(space);
        System.out.println("O jogo esta pronto para iniciar!");
    }

    private static int runUntinGetValidNumber(final int min, final int max){
        var current = sc.nextInt();
        while (current < min || current > max){
            System.out.printf("Informe um numero entre: %s e %s\n", min, max);
            current = sc.nextInt();
        }
        return current;
    }
}
