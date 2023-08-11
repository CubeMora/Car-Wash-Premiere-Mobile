package com.carwashpremiere.carwashpremieremobile.activities

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.carwashpremiere.carwashpremieremobile.R

class Act_AdminCrud : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        actionBar?.hide()
        setContentView(R.layout.activity_admin_crud)
        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        // Datos de prueba para la tabla
        val data = arrayOf(
            arrayOf("Nombre", "Edad", "País", "Teléfono", "Correo", "Ocupación", "Salario"),
            arrayOf(
                "Juan",
                "25",
                "España",
                "1234567890",
                "juan@example.com",
                "Desarrollador",
                "$50000"
            ),
            arrayOf(
                "María",
                "30",
                "México",
                "9876543210",
                "maria@example.com",
                "Diseñadora",
                "$45000"
            ),
            arrayOf(
                "Carlos",
                "22",
                "Argentina",
                "5678901234",
                "carlos@example.com",
                "Estudiante",
                "$0"
            )
        )

        // Crear la fila de encabezados
        val headerRow = TableRow(this)
        for (header in data[0]) {
            val headerTextView = TextView(this)
            headerTextView.text = header
            headerTextView.setPadding(16, 16, 16, 16)
            headerRow.addView(headerTextView)
        }
        tableLayout.addView(headerRow)

        // Llenar la tabla con datos de prueba y botón de acciones
        for (i in 1 until data.size) {
            val dataRow = TableRow(this)
            for (j in data[i].indices) {
                val dataTextView = TextView(this)
                dataTextView.text = data[i][j]
                dataTextView.setPadding(16, 16, 16, 16)
                dataRow.addView(dataTextView)
            }

            // Agregar el botón en la última columna
            val actionButton = Button(this)
            actionButton.text = "Acciones"
            actionButton.setOnClickListener { showPopupMenu() }
            dataRow.addView(actionButton)
            tableLayout.addView(dataRow)
        }
    }

    private fun showPopupMenu() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.pop_up_menu, null)
        dialog.setContentView(view)
        val btnEdit = view.findViewById<Button>(R.id.btn_edit)
        val btnDelete = view.findViewById<Button>(R.id.btn_delete)
        btnEdit.setOnClickListener { // Mostrar el popup de edición aquí
//                showEditPopup();
            dialog.dismiss()
        }
        btnDelete.setOnClickListener { showDeleteConfirmationPopup(dialog) }
        dialog.show()
    }

    private fun showDeleteConfirmationPopup(previousDialog: Dialog) {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.pop_up_confirm, null)
        dialog.setContentView(view)
        val btnConfirmDelete = view.findViewById<Button>(R.id.btn_confirm_delete)
        val btnCancelDelete = view.findViewById<Button>(R.id.btn_cancel_delete)
        btnConfirmDelete.setOnClickListener {
            // Perform delete action here
            previousDialog.dismiss() // Dismiss the previous dialog
            dialog.dismiss() // Dismiss the confirmation dialog
        }
        btnCancelDelete.setOnClickListener {
            dialog.dismiss() // Dismiss the confirmation dialog
        }
        dialog.show()
    }
}