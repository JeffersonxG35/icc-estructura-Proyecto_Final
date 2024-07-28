package View;

import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import Controller.Controlador;
import java.lang.IllegalArgumentException;


public class Interfaz extends Frame implements ActionListener {
    public Button btnBFS;
    public Button btnDFS;
    public Button btnRecursivo;
    public Button btnCache;
    public Button btnRESETEAR;
    public Panel filCol;
    private TextField txtFila;
    private TextField txtCol;
    private Button btnCrear;
    private int filas;
    private int columnas;

    public Controlador controlador;
    private JComboBox<String> listaDesplegable;

    public Interfaz() {
        filCol = new Panel();
        controlador = new Controlador(filCol);

        interfazNormal();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });
    }

    public void interfazNormal() {
        setTitle("Laberinto Resoluciones");
        setSize(560, 550);
        setLocation(470, 100);

        Panel pnlBotones = new Panel();
        Panel pnlLista = new Panel(new FlowLayout());

        filCol.setBackground(Color.GRAY);

        Label lblFila = new Label("Filas:");
        txtFila = new TextField(4);
        Label lblCol = new Label("Columnas:");
        txtCol = new TextField(4);

        btnBFS = new Button("Metodo BFS");
        btnDFS = new Button("Metodo DFS");
        btnRecursivo = new Button("Metodo Recursivo");
        btnCache = new Button("Metodo con Cache");
        btnRESETEAR = new Button("RESETEAR");
        btnCrear = new Button("Crear Cuadricula");

        listaDesplegable = controlador.controladorLista();

        btnBFS.addActionListener(this);
        btnDFS.addActionListener(this);
        btnRecursivo.addActionListener(this);
        btnCache.addActionListener(this);
        btnCrear.addActionListener(this);
        btnRESETEAR.addActionListener(this);

        listaDesplegable.addActionListener(this);

        pnlBotones.add(btnBFS);
        pnlBotones.add(btnDFS);
        pnlBotones.add(btnRecursivo);
        pnlBotones.add(btnCache);
        pnlBotones.add(btnRESETEAR);

        pnlLista.add(lblFila);
        pnlLista.add(txtFila);
        pnlLista.add(lblCol);
        pnlLista.add(txtCol);

        pnlLista.add(btnCrear);
        pnlLista.add(listaDesplegable);

        add(pnlBotones, BorderLayout.SOUTH);
        add(pnlLista, BorderLayout.NORTH);
        add(filCol, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "Metodo BFS":
                controlador.controladorBFS();
                break;
            case "Metodo DFS":
                controlador.controladorDFS();
                break;
            case "Metodo Recursivo":
                controlador.controladorRecursivo();
                break;
            case "Metodo con Cache":
                controlador.controladorCache();
                break;
            case "Crear Cuadricula":
                try {
                    filas = Integer.parseInt(txtFila.getText());
                    columnas = Integer.parseInt(txtCol.getText());
                    controlador.controladorCuadricula(filas, columnas);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ingrese números válidos para filas y columnas");
                }
                break;

            case "RESETEAR":
                controlador.controladorEliminar(txtFila, txtCol);

                try{
                 controlador.controladorCuadricula(filas, columnas);
                } catch (IllegalArgumentException e1){
                    JOptionPane.showMessageDialog(this, "Debe haber una cuadricula generada para resetear");
                }
           
              
                break;
        }

        if (e.getSource() == listaDesplegable) {
            accionadorLista(e);
        }
    }

    public void accionadorLista(ActionEvent e) {
        String seleccion = (String) listaDesplegable.getSelectedItem();

        if (seleccion.equals("Version Normal")) {
            controlador.cerrarVentanaExtra();
        } 

    }
}
