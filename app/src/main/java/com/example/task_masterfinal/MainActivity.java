package com.example.task_masterfinal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button agregar, borrar, editar;
    EditText tarea_nueva;
    ProgressBar progressBar;
    Spinner spinner;
    ListView listViewTareas;
    ArrayList<String> listaTareas;
    ArrayAdapter<String> tareasAdapter;
    String fileName = "tareas.txt";
    int numeroTareas = 0;
    int tareaSeleccionada = -1;  // Para saber cuál tarea se está editando

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        agregar = findViewById(R.id.btnAdd);
        borrar = findViewById(R.id.btnDelete);
        editar = findViewById(R.id.btnEdit);
        tarea_nueva = findViewById(R.id.etTarea);
        progressBar = findViewById(R.id.progress);
        spinner = findViewById(R.id.spinner);
        listViewTareas = findViewById(R.id.listViewTareas);

        listaTareas = new ArrayList<>();

        // Configuración del adaptador para el ListView
        tareasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaTareas);
        listViewTareas.setAdapter(tareasAdapter);

        // Configuración del adaptador para el Spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.prioridades, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // Cargar las tareas desde el archivo
        cargarTareas();

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tn = tarea_nueva.getText().toString();
                String prioridad = spinner.getSelectedItem().toString();

                if (!tn.isEmpty()) {
                    if (numeroTareas < 10) {
                        String tareaConPrioridad = tn + " (Prioridad: " + prioridad + ")";
                        listaTareas.add(tareaConPrioridad);
                        guardarTareas();
                        tarea_nueva.setText("");
                        Toast.makeText(MainActivity.this, "Tarea agregada", Toast.LENGTH_SHORT).show();
                        numeroTareas++;
                        progressBar.setProgress(numeroTareas);
                        tareasAdapter.notifyDataSetChanged();  // Actualizar la lista
                    } else {
                        Toast.makeText(MainActivity.this, "Máximo de 10 tareas alcanzado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tareaSeleccionada != -1 && tareaSeleccionada < listaTareas.size()) {
                    listaTareas.remove(tareaSeleccionada);
                    guardarTareas();
                    Toast.makeText(MainActivity.this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
                    numeroTareas--;
                    progressBar.setProgress(numeroTareas);
                    tarea_nueva.setText("");  // Limpiar el campo de entrada
                    tareaSeleccionada = -1;  // Resetear selección
                    tareasAdapter.notifyDataSetChanged();  // Actualizar la lista
                } else {
                    Toast.makeText(MainActivity.this, "Selecciona una tarea para eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tareaSeleccionada != -1 && tareaSeleccionada < listaTareas.size()) {
                    String tn = tarea_nueva.getText().toString();
                    String prioridad = spinner.getSelectedItem().toString();

                    if (!tn.isEmpty()) {
                        String tareaConPrioridad = tn + " (Prioridad: " + prioridad + ")";
                        listaTareas.set(tareaSeleccionada, tareaConPrioridad);
                        guardarTareas();
                        Toast.makeText(MainActivity.this, "Tarea editada", Toast.LENGTH_SHORT).show();
                        tarea_nueva.setText("");  // Limpiar el campo de entrada
                        tareaSeleccionada = -1;  // Resetear selección
                        tareasAdapter.notifyDataSetChanged();  // Actualizar la lista
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Selecciona una tarea para editar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Seleccionar una tarea del ListView
        listViewTareas.setOnItemClickListener((parent, view, position, id) -> {
            tareaSeleccionada = position;  // Guardar el índice de la tarea seleccionada
            tarea_nueva.setText(listaTareas.get(position).split(" \\(Prioridad")[0]);  // Mostrar la tarea en el campo de texto
        });
    }

    // Método para guardar las tareas en un archivo
    private void guardarTareas() {
        try {
            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
            for (String tarea : listaTareas) {
                fos.write((tarea + "\n").getBytes());
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para cargar las tareas desde el archivo
    private void cargarTareas() {
        try {
            FileInputStream fis = openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            listaTareas.clear();
            numeroTareas = 0;
            while ((line = reader.readLine()) != null) {
                listaTareas.add(line);
                numeroTareas++;
            }
            progressBar.setProgress(numeroTareas);
            tareasAdapter.notifyDataSetChanged();  // Actualizar la lista en el ListView
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
