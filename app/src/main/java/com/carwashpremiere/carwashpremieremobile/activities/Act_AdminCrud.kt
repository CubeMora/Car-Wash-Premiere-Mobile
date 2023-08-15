package com.carwashpremiere.carwashpremieremobile.activities

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.carwashpremiere.carwashpremieremobile.R

class Act_AdminCrud : AppCompatActivity() {
    private var selectedRow: TableRow? = null
    var btn_add: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        actionBar?.hide()
        setContentView(R.layout.activity_admin_crud)
        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        btn_add = findViewById(R.id.btn_add)

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
            actionButton.setOnClickListener {
                selectedRow = dataRow
                showPopupMenu() }
            dataRow.addView(actionButton)
            tableLayout.addView(dataRow)
        }

        btn_add?.setOnClickListener {
            showAddDialog()
        }
    }

    private fun showEditDialog() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.modal_crud_test, null)
        dialog.setContentView(view)

        val linLayoutEditText = view.findViewById<LinearLayout>(R.id.linLayout_BtnLogin)

        val fieldTitles = arrayOf("Nombre", "Edad", "País", "Teléfono", "Correo", "Ocupación", "Salario")

        // Obtener los valores actuales de la fila seleccionada
        val currentValues = mutableListOf<String>()
        selectedRow?.let {
            for (i in 0 until it.childCount - 1) {
                val textView = it.getChildAt(i) as TextView
                currentValues.add(textView.text.toString())
            }
        }

        for ((i, title) in fieldTitles.withIndex()) {
            val textView = TextView(this)
            textView.text = title
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            linLayoutEditText.addView(textView)

            val editText = EditText(this)
            editText.hint = "Ingrese $title"
            editText.setText(currentValues.getOrNull(i) ?: "")
            editText.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            linLayoutEditText.addView(editText)
        }

        val btnCancel = view.findViewById<Button>(R.id.btn_cancelCrud)
        val btnSave = view.findViewById<Button>(R.id.btn_updateCrud)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnSave.setOnClickListener {
            // Obtener los valores ingresados en los EditText y realizar las acciones necesarias
            // Por ejemplo, puedes actualizar los valores en la tabla dinámica
            selectedRow?.removeAllViews() // Eliminar las vistas existentes en la fila
            for (i in 0 until linLayoutEditText.childCount step 2) {
                val value = (linLayoutEditText.getChildAt(i + 1) as EditText).text.toString()
                val textView = TextView(this)
                textView.text = value
                textView.setPadding(16, 16, 16, 16)
                selectedRow?.addView(textView)
            }
            val actionButton = Button(this)
            actionButton.text = "Acciones"
            actionButton.setOnClickListener {
                showPopupMenu()
            }
            selectedRow?.addView(actionButton)

            dialog.dismiss()
        }

        dialog.show()
    }


    private fun showPopupMenu() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.pop_up_menu, null)
        dialog.setContentView(view)
        val btnEdit = view.findViewById<Button>(R.id.btn_edit)
        val btnDelete = view.findViewById<Button>(R.id.btn_delete)
        btnEdit.setOnClickListener {
            showEditDialog()
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
            selectedRow?.let {
                val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
                tableLayout.removeView(it) // Remove the selected row
            }
            previousDialog.dismiss() // Dismiss the previous dialog
            dialog.dismiss() // Dismiss the confirmation dialog
        }
        btnCancelDelete.setOnClickListener {
            dialog.dismiss() // Dismiss the confirmation dialog
        }
        dialog.show()
    }

    private fun showAddDialog() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.modal_crud_test, null)
        dialog.setContentView(view)

        val linLayoutEditText = view.findViewById<LinearLayout>(R.id.linLayout_BtnLogin)

        val fieldTitles = arrayOf("Nombre", "Edad", "País", "Teléfono", "Correo", "Ocupación", "Salario")

        for (title in fieldTitles) {
            val textView = TextView(this)
            textView.text = title
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            linLayoutEditText.addView(textView)

            val editText = EditText(this)
            editText.hint = "Ingrese $title"
            editText.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            linLayoutEditText.addView(editText)
        }

        val btnCancel = view.findViewById<Button>(R.id.btn_cancelCrud)
        val btnSave = view.findViewById<Button>(R.id.btn_updateCrud)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnSave.setOnClickListener {
            // Obtener los valores ingresados en los EditText y realizar las acciones necesarias
            // Por ejemplo, puedes agregar los valores a la tabla dinámica
            val newRow = TableRow(this)
            for (i in 0 until linLayoutEditText.childCount step 2) {
                val value = (linLayoutEditText.getChildAt(i + 1) as EditText).text.toString()
                val textView = TextView(this)
                textView.text = value
                textView.setPadding(16, 16, 16, 16)
                newRow.addView(textView)
            }
            val actionButton = Button(this)
            actionButton.text = "Acciones"
            actionButton.setOnClickListener {
                selectedRow = newRow
                showPopupMenu()
            }
            newRow.addView(actionButton)
            val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
            tableLayout.addView(newRow)

            dialog.dismiss()
        }

        dialog.show()
    }



}