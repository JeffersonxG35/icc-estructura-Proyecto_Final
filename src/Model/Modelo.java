package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class Modelo {
    private JComboBox<String> listaDesplegable;
    public Panel filCol;
    private Frame ventanaExtra;
    private Button[][] buttons;
    private boolean metodoEjecutado;

    public Modelo(Panel filCol) {
        this.filCol = filCol;
        this.metodoEjecutado = false;
    }

    private void resetearMetodoEjecutado() {
        this.metodoEjecutado = false;
    }

    private boolean verificarYMostrarMensaje() {
        if (metodoEjecutado) {
            JOptionPane.showMessageDialog(null, "Debe reiniciar la cuadrícula antes de ejecutar otro método.");
            return false;
        }
        metodoEjecutado = true;
        return true;
    }


    //METODO BFS
    public void metodoBFS() {
        if (!verificarYMostrarMensaje()) return;
    
        boolean[][] grid = obtenerGridDePanel();
        Celda start = new Celda(0, 0);
        Celda end = new Celda(grid.length - 1, grid[0].length - 1);
    
        if (!grid[start.row][start.col] || !grid[end.row][end.col]) {
            JOptionPane.showMessageDialog(null, "El punto de inicio o el punto final están bloqueados.");
            return;
        }
    
        List<Celda> path = bfs(grid, start, end);
   
        if (!path.isEmpty()) {     
            pintarCamino(path);
        } else {
            return;
       
        }
    }

    private List<Celda> bfs(boolean[][] grid, Celda start, Celda end) {
        List<Celda> path = new ArrayList<>();
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        boolean[][]visited = new boolean[grid.length][grid[0].length];
        Queue<Celda> queue = new LinkedList<>();
        Map<Celda, Celda> parent = new HashMap<>();
    
        queue.add(start);
        visited[start.row][start.col] = true;
    
        Timer timer = new Timer(220, new ActionListener() {
            private Celda current;
    
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!queue.isEmpty()) {
                    current = queue.poll();
                    buttons[current.row][current.col].setBackground(Color.GREEN);
                    filCol.revalidate();
                    filCol.repaint();
    
                    if (current.equals(end)) {
                        Timer sourceTimer = (Timer) e.getSource();
                        sourceTimer.stop();
                        Celda step = current;
                        while (step != null) {
                            path.add(0, step);
                            step = parent.get(step);
                        }
                        pintarUltimaCelda(path);
                        return;
                    }
    
                    for (int[] direction : directions) {
                        int newRow = current.row + direction[0];
                        int newCol = current.col + direction[1];
                        Celda next = new Celda(newRow, newCol);
    
                        if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length &&
                            grid[newRow][newCol] && !visited[newRow][newCol]) {
                            queue.add(next);
                            visited[newRow][newCol] = true;
                            parent.put(next, current);
                        }
                    }
                } else {
                    ((Timer) e.getSource()).stop();
                    if (path.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No se encontró un camino.");
                    }
                }
            }
        });
    
        timer.start();
        return path;
    }

    //METODO QUE PINTA LA ULTIMA CELDA VISITADA DE CELESTE
    private void pintarUltimaCelda(List<Celda> path) {
        if (!path.isEmpty()) {
            Celda lastCell = path.get(path.size() - 1);
            buttons[lastCell.row][lastCell.col].setBackground(Color.cyan);
            filCol.revalidate();
            filCol.repaint();
        }
    }
    //METODO BFS
    public void metodoDFS() {
        if (!verificarYMostrarMensaje()) return;

        boolean[][] grid = obtenerGridDePanel();
        Celda start = new Celda(0, 0);
        Celda end = new Celda(grid.length - 1, grid[0].length - 1);

        if (!grid[start.row][start.col] || !grid[end.row][end.col]) {
            JOptionPane.showMessageDialog(null, "El punto de inicio o el punto final están bloqueados.");
            return;
        }

        List<Celda> path = new ArrayList<>();
        boolean found = dfs(grid, start, end, path, new boolean[grid.length][grid[0].length]);
        //Verificar si existe un camino
        if (found) {
            pintarCamino(path);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un camino.");
        }
    }
    
    private boolean dfs(boolean[][] grid, Celda current, Celda end, List<Celda> path, boolean[][] visited) {
        if (current.row < 0 || current.col < 0 || current.row >= grid.length || current.col >= grid[0].length ||
            !grid[current.row][current.col] || visited[current.row][current.col]) {
            return false;
        }

        path.add(current);
        visited[current.row][current.col] = true;

        if (current.equals(end)) {
            return true;
        }

        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] direction : directions) {
            Celda next = new Celda(current.row + direction[0], current.col + direction[1]);
            if (dfs(grid, next, end, path, visited)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }
    //METODO CON RECURSIVIDAD
    public void metodoRecursivo() {
        if (!verificarYMostrarMensaje()) return;

        boolean[][] grid = obtenerGridDePanel();
        Celda start = new Celda(0, 0);
        Celda end = new Celda(grid.length - 1, grid[0].length - 1);

        if (!grid[start.row][start.col] || !grid[end.row][end.col]) {
            JOptionPane.showMessageDialog(null, "El punto de inicio o el punto final están bloqueados.");
            return;
        }

        List<Celda> path = new ArrayList<>();
        if (findPath(grid, start, end, path)) {
            pintarCamino(path);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un camino.");
        }
    }

    private boolean findPath(boolean[][] grid, Celda current, Celda end, List<Celda> path) {
        // Verifica límites del laberinto y accesibilidad de la celda actual
        if (current.row < 0 || current.col < 0 || current.row >= grid.length || current.col >= grid[0].length || !grid[current.row][current.col]) {
            return false;
        }
    
        // Si la celda actual ya está en el camino, evita bucles infinitos
        if (path.contains(current)) {
            return false;
        }
    
        path.add(current);
    
        if (current.equals(end)) {
            return true;
        }
    
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] direction : directions) {
            Celda next = new Celda(current.row + direction[0], current.col + direction[1]);
            if (findPath(grid, next, end, path)) {
                return true;
            }
        }
    
        path.remove(path.size() - 1);
        return false;
    }
    
    //METODO RECURSIVO CON CACHE
    public void metodoConCache() {
        if (!verificarYMostrarMensaje()) return;
    
        boolean[][] grid = obtenerGridDePanel();
        Celda start = new Celda(0, 0);
        Celda end = new Celda(grid.length - 1, grid[0].length - 1);
    
        if (!grid[start.row][start.col] || !grid[end.row][end.col]) {
            JOptionPane.showMessageDialog(null, "El punto de inicio o el punto final están bloqueados.");
            return;
        }
    
        List<Celda> path = getPath(grid, start, end);
        if (!path.isEmpty()) {
            pintarCamino(path);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un camino.");
        }
    }

    private List<Celda> getPath(boolean[][] grid, Celda start, Celda end) {
        List<Celda> path = new ArrayList<>();
        Map<Celda, Boolean> cache = new HashMap<>();
        if (getPath(grid, start, end, path, cache)) {
            return path;
        }
        return new ArrayList<>();
    }

    private boolean getPath(boolean[][] grid, Celda current, Celda end, List<Celda> path, Map<Celda, Boolean> cache) {
        if (current.row < 0 || current.col < 0 || current.row >= grid.length || current.col >= grid[0].length || !grid[current.row][current.col]) {
            return false;
        }
    
        if (cache.containsKey(current)) {
            return cache.get(current);
        }
    
        // Si la celda actual ya está en el camino, evita bucles infinitos
        if (path.contains(current)) {
            return false;
        }
    
        path.add(current);
    
        if (current.equals(end)) {
            cache.put(current, true);
            return true;
        }
    
        boolean success = false;
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] direction : directions) {
            Celda next = new Celda(current.row + direction[0], current.col + direction[1]);
            if (getPath(grid, next, end, path, cache)) {
                success = true;
                break;
            }
        }
    
        if (!success) {
            path.remove(path.size() - 1);
        }
    
        cache.put(current, success);
        return success;
    }
    
    private boolean[][] obtenerGridDePanel() {
        int filas = buttons.length;
        int columnas = buttons[0].length;
        boolean[][] grid = new boolean[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                grid[i][j] = buttons[i][j].getBackground() != Color.BLACK;
                System.out.println("Celda (" + i + ", " + j + ") - " + (grid[i][j] ? "Libre" : "Obstáculo"));
            }
        }

        return grid;
    }
    //METODO QUE PINTA EL CAMINO DEL LABERINTO
    private void pintarCamino(List<Celda> path) {
        System.out.println("Pintando el camino:");

        Timer timer = new Timer(300, new ActionListener() {
            private int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < path.size()) {
                    Celda celda = path.get(index);
                    System.out.println(celda);
                    if (index == path.size() - 1) {
                        buttons[celda.row][celda.col].setBackground(Color.cyan); // Última celda de otro color
                    } else {
                        buttons[celda.row][celda.col].setBackground(Color.green);
                    }
                    index++;
                    filCol.revalidate();
                    filCol.repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        timer.start();
    }
    //LISTA DESPLEGABLE
    public JComboBox<String> listaDesplegable() {
        String[] opciones = {"Version Normal"};
        listaDesplegable = new JComboBox<>(opciones);
        return listaDesplegable;
    }

   //METODO ANTERIOR QUE SE USABA PARA CERRAR LA VENTANA EXTRA
    public void cerrarVentanaExtra() {
        if (ventanaExtra != null) {
            ventanaExtra.dispose();
            ventanaExtra = null;
        }
    }
    //METODO QUE CREA LAS CUADRICULAS
    public void crearCuadricula(int filas, int columnas) {
        filCol.removeAll();
        filCol.setLayout(new GridLayout(filas, columnas));
        buttons = new Button[filas][columnas];
        resetearMetodoEjecutado();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                final int row = i;
                final int col = j;
                Button button = new Button();
                button.setBackground(Color.LIGHT_GRAY);
                button.setForeground(Color.BLACK);

                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button.setBackground(Color.BLACK);
                    }
                });

                filCol.add(button);
                buttons[row][col] = button;
            }
        }

        filCol.revalidate();
        filCol.repaint();
    }

    //FUNCION DEL BOTON ELIMINAR CUADRICULA
    public void eliminarCuadricula(TextField txtFila, TextField txtCol) {
        filCol.removeAll();
        filCol.revalidate();
        filCol.repaint();

        resetearMetodoEjecutado();
    }
}
