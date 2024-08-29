package com.mycompany.nqueensparallel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NQueensParallel {

    // Lista para armazenar todas as soluções encontradas
    private static final List<int[][]> solutions = new ArrayList<>();

    // Função principal para resolver o problema das N rainhas de forma paralela
    public static void solveNQueensParallel(int N) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // Cria um pool de threads
        for (int col = 0; col < N; col++) {
            // Para cada coluna da primeira linha, cria uma nova tarefa para explorar soluções
            final int startCol = col;
            executor.submit(() -> {
                int[][] board = new int[N][N];
                board[0][startCol] = 1; // Coloca a rainha na coluna inicial
                solveNQueens(board, 1, N); // Resolve o problema a partir da segunda linha
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS); // Espera todas as threads terminarem

        // Imprime o número de soluções encontradas
        System.out.println("Número de soluções encontradas: " + solutions.size());
        for (int[][] solution : solutions) {
            printSolution(solution); // Imprime cada solução
        }
    }

    // Função recursiva para resolver o problema das N rainhas
    private static void solveNQueens(int[][] board, int row, int N) {
        // Caso base: Se todas as rainhas estiverem posicionadas, salva a solução
        if (row == N) {
            synchronized (solutions) { // Sincroniza o acesso à lista de soluções
                solutions.add(copyBoard(board));
            }
            return;
        }

        // Tenta posicionar a rainha em cada coluna na linha atual
        for (int col = 0; col < N; col++) {
            if (isSafe(board, row, col, N)) { // Verifica se é seguro posicionar a rainha
                board[row][col] = 1; // Posiciona a rainha
                solveNQueens(board, row + 1, N); // Chama recursivamente para a próxima linha
                board[row][col] = 0; // Backtrack: remove a rainha para tentar outra posição
            }
        }
    }

    // Função que verifica se é seguro posicionar uma rainha em board[row][col]
    private static boolean isSafe(int[][] board, int row, int col, int N) {
        // Verifica a coluna atual
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) return false;
        }

        // Verifica a diagonal superior esquerda
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) return false;
        }

        // Verifica a diagonal superior direita
        for (int i = row, j = col; i >= 0 && j < N; i--, j++) {
            if (board[i][j] == 1) return false;
        }

        return true; // Seguro posicionar a rainha
    }

    // Função que copia o tabuleiro atual para salvar a solução
    private static int[][] copyBoard(int[][] board) {
        int N = board.length;
        int[][] newBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    // Função que imprime uma solução no console
    private static void printSolution(int[][] board) {
        int N = board.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 1) {
                    System.out.print("Q "); // Rainha
                } else {
                    System.out.print(". "); // Espaço vazio
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Função principal para executar o programa
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o valor de N para o problema das N Rainhas: ");
        int N = scanner.nextInt(); // Recebe o valor de N do usuário

        long startTime = System.currentTimeMillis(); // Marca o tempo inicial

        solveNQueensParallel(N); // Chama a função paralela para resolver o problema

        long endTime = System.currentTimeMillis(); // Marca o tempo final
        long duration = endTime - startTime; // Calcula a duração

        System.out.println("Tempo de execução: " + duration + " ms");
    }
}