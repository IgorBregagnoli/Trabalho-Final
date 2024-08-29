package com.mycompany.nqueens;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NQueens {
    // Função principal para resolver o problema das N rainhas sequencialmente
    public static void solveNQueensSequential(int N) {
        // Criação do tabuleiro NxN
        int[][] board = new int[N][N];
        // Lista para armazenar as soluções
        List<int[][]> solutions = new ArrayList<>();
        // Chama a função recursiva para encontrar todas as soluções
        solveNQueens(board, 0, solutions);
        // Imprime o número de soluções encontradas
        System.out.println("Número de soluções encontradas: " + solutions.size());
        for (int[][] solution : solutions) {
            // Imprime cada solução
            printSolution(solution);
        }
    }

    // Função recursiva para resolver o problema das N rainhas
    private static void solveNQueens(int[][] board, int row, List<int[][]> solutions) {
        int N = board.length;
        // Caso base: Se todas as rainhas estiverem posicionadas, salva a solução
        if (row == N) {
            solutions.add(copyBoard(board));
            return;
        }
        
        // Tenta posicionar a rainha em cada coluna na linha atual
        for (int col = 0; col < N; col++) {
            // Verifica se é seguro posicionar a rainha
            if (isSafe(board, row, col)) {
                // Posiciona a rainha
                board[row][col] = 1;
                // Chama recursivamente para a próxima linha
                solveNQueens(board, row + 1, solutions);
                // Backtrack: remove a rainha para tentar outra posição
                board[row][col] = 0;
            }
        }
    }

    // Função que verifica se é seguro posicionar uma rainha em board[row][col]
    private static boolean isSafe(int[][] board, int row, int col) {
        int N = board.length;
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
        // Seguro posicionar a rainha
        return true;
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
                if(board[i][j] == 1) {
                    // Rainha
                    System.out.print("Q ");
                } else {
                    // Espaço vazio
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // Função principal para executar o programa
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o valor de N para o problema das N Rainhas: ");
        // Recebe o valor de N do usuário
        int N = scanner.nextInt();
        // Marca o tempo inicial
        long startTime = System.currentTimeMillis();
        // Chama a função sequencial para resolver o problema
        solveNQueensSequential(N);
        // Marca o tempo final
        long endTime = System.currentTimeMillis();
        // Calcula a duração
        long duration = endTime - startTime;
        System.out.println("Tempo de execução: " + duration + " ms");
    }
}