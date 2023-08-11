package com.carwashpremiere.carwashpremieremobile;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Act_AdminCrud extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_admin_crud);

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // Datos de prueba para la tabla
        String[][] data = {
                {"Nombre", "Edad", "País", "Teléfono", "Correo", "Ocupación", "Salario"},
                {"Juan", "25", "España", "1234567890", "juan@example.com", "Desarrollador", "$50000"},
                {"María", "30", "México", "9876543210", "maria@example.com", "Diseñadora", "$45000"},
                {"Carlos", "22", "Argentina", "5678901234", "carlos@example.com", "Estudiante", "$0"}
        };

        // Crear la fila de encabezados
        TableRow headerRow = new TableRow(this);
        for (String header : data[0]) {
            TextView headerTextView = new TextView(this);
            headerTextView.setText(header);
            headerTextView.setPadding(16, 16, 16, 16);
            headerRow.addView(headerTextView);
        }
        tableLayout.addView(headerRow);

        // Llenar la tabla con datos de prueba y botón de acciones
        for (int i = 1; i < data.length; i++) {
            TableRow dataRow = new TableRow(this);
            for (int j = 0; j < data[i].length; j++) {
                TextView dataTextView = new TextView(this);
                dataTextView.setText(data[i][j]);
                dataTextView.setPadding(16, 16, 16, 16);
                dataRow.addView(dataTextView);
            }

            // Agregar el botón en la última columna
            Button actionButton = new Button(this);
            actionButton.setText("Acciones");
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu();
                }
            });
            dataRow.addView(actionButton);

            tableLayout.addView(dataRow);
        }
    }

    private void showPopupMenu() {
        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.pop_up_menu, null);
        dialog.setContentView(view);

        Button btnEdit = view.findViewById(R.id.btn_edit);
        Button btnDelete = view.findViewById(R.id.btn_delete);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el popup de edición aquí
//                showEditPopup();
                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationPopup(dialog);
            }
        });


        dialog.show();
    }


    private void showDeleteConfirmationPopup(Dialog previousDialog) {
        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.pop_up_confirm, null);
        dialog.setContentView(view);

        Button btnConfirmDelete = view.findViewById(R.id.btn_confirm_delete);
        Button btnCancelDelete = view.findViewById(R.id.btn_cancel_delete);

        btnConfirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform delete action here
                previousDialog.dismiss(); // Dismiss the previous dialog
                dialog.dismiss(); // Dismiss the confirmation dialog
            }
        });

        btnCancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Dismiss the confirmation dialog
            }
        });

        dialog.show();
    }

}
