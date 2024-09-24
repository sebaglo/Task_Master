package com.example.task_masterfinal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button agregar;
    Button borrar;
    EditText tarea_nueva;
    TextView lista_tarea;
    ProgressBar progressBar;
    Spinner spinner;
    String lista;
    String tn;
    int numeroTareas = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        agregar = (Button) findViewById(R.id.btnAdd);
        borrar = (Button) findViewById(R.id.btnDelete);
        tarea_nueva = (EditText) findViewById(R.id.etTarea);
        lista_tarea = (TextView) findViewById(R.id.txtLista);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.prioridades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista = lista_tarea.getText().toString();
                tn = tarea_nueva.getText().toString();
                String prioridad = spinner.getSelectedItem().toString(); // Obtener la prioridad seleccionada

                if(numeroTareas < 10 && !tn.isEmpty()){
                    lista = lista + "\n" + tn + " (Prioridad: " + prioridad + ")"; // Agregar tarea con prioridad
                    lista_tarea.setText(lista);
                }

                //solo agregar si hay espacio
                if(numeroTareas < 10 && !tn.isEmpty()){
                    lista = lista + "\n" +tn;
                    lista_tarea.setText(lista);
                }

                //incrementar contador y actualizar barra
                numeroTareas++;
                progressBar.setProgress(numeroTareas);
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista = lista_tarea.getText().toString();

                //SI LA LISTA ESTA VACIA, ELIMINA LA ULTIMA TAREA
                if (!lista.isEmpty()){
                    String[] tareas = lista.split("\n");
                    if (tareas.length > 1){
                        StringBuilder nuevaLista = new StringBuilder();
                        for (int i = 0; i < tareas.length - 1; i++){
                            nuevaLista.append(tareas[i]).append("\n");
                        }
                        lista_tarea.setText(nuevaLista.toString().trim());
                    }else{
                        lista_tarea.setText("");//SI ES LA UNICA TAREA SE BOORARA
                    }

                    //decrementar contador de tareas y actualizar barra
                    if(numeroTareas > 0){
                        numeroTareas--;
                        progressBar.setProgress(numeroTareas);
                    }
                }
            }
        });
    };
}
