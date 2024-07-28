package Controller;

import java.awt.Panel;
import javax.swing.JComboBox;
import Model.Modelo;
import java.awt.TextField;

public class Controlador {
    private Modelo modelo;

    public Controlador(Panel filCol) {
        this.modelo = new Modelo(filCol);
    }

    public void controladorBFS() {
        modelo.metodoBFS();
    }

    public void controladorDFS() {
        modelo.metodoDFS();
    }

    public void controladorRecursivo() {
        modelo.metodoRecursivo();
    }

    public void controladorCache() {
        modelo.metodoConCache();
    }

    public JComboBox<String> controladorLista() {
        return modelo.listaDesplegable();
    }

    public void cerrarVentanaExtra() {
        modelo.cerrarVentanaExtra();
    }

    public void controladorEliminar(TextField txtFila, TextField txtCol) {
        modelo.eliminarCuadricula(txtFila, txtCol);
    }

    public void controladorCuadricula(int filas, int columnas) {
        modelo.crearCuadricula(filas, columnas);
    }
}
