package com.carwashpremiere.carwashpremieremobile.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.carwashpremiere.carwashpremieremobile.functions.Function_NetworkRequests
import com.carwashpremiere.carwashpremieremobile.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.itextpdf.barcodes.BarcodeQRCode
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.property.TextAlignment
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException

class Act_OrderDetail : AppCompatActivity() {
    var btn_WA: Button? = null
    var btn_Next: Button? = null
    var btn_Back: Button? = null
    var hasClickedWA = false
    var phoneNumber: String? = null
    var bundleType: String? = null
    var phoneNumberTemp: String? = null
    var ServiceChipGrout: ChipGroup? = null
    var ExtraDetailsChipGrout: ChipGroup? = null
    var bundle: Bundle? = null
    var networkRequests: Function_NetworkRequests? = null
    var cardView_TitleSpecifications: CardView? = null
    var cardView_Specifications: CardView? = null
    var cardView_TitleCarService: CardView? = null
    var cardView_CarService: CardView? = null
    var cardView_ExtraServicesSpecifications: CardView? = null
    var cardView_DetailsSpecifications: CardView? = null
    var txt_TotalPrice: TextView? = null
    var txt_ServiceTitle: TextView? = null
    var txt_objectDetail: TextView? = null
    var txt_ExtraServices: TextView? = null
    var txt_Details: TextView? = null
    var txt_Specifications: TextView? = null
    private var messageContent: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_act_order_detail)
        initUI()
        networkRequests = Function_NetworkRequests(this)
        getPhoneNumberFromServer(networkRequests!!)
        btn_WA!!.setOnClickListener {
            hasClickedWA = true
            val allChipDetailsTexts = StringBuilder()
            val allChipServicesTexts = StringBuilder()
            for (i in 0 until ServiceChipGrout!!.childCount) {
                val child = ServiceChipGrout!!.getChildAt(i)
                // Verificar que el hijo sea un Chip
                if (child is Chip) {
                    // Obtener el texto del chip y agregarlo a la variable
                    val chipText = child.text.toString()
                    allChipServicesTexts.append(chipText).append("\n")
                }
            }
            for (i in 0 until ExtraDetailsChipGrout!!.childCount) {
                val child = ExtraDetailsChipGrout!!.getChildAt(i)
                // Verificar que el hijo sea un Chip
                if (child is Chip) {
                    // Obtener el texto del chip y agregarlo a la variable
                    val chipText = child.text.toString()
                    allChipDetailsTexts.append(chipText).append("\n")
                }
            }
            val allChipTextsServiceString = allChipServicesTexts.toString()
            val allChipTextsDetailsString = allChipDetailsTexts.toString()
            var message = ""
            if (bundleType == "car") {
                val service_title = txt_ServiceTitle!!.text.toString()
                val car_type = txt_objectDetail!!.text.toString()
                message = ("Cotización para el servicio de " + service_title
                        + " con el siguiente detalle: \n\n"
                        + "Tipo de vehículo: " + car_type + "\n"
                        + "Servicios extra: " + allChipTextsServiceString + "\n"
                        + "Detalles adicionales: " + allChipDetailsTexts + "\n\n"
                        + "Precio total: " + String.format(
                    "$%.2f",
                    bundle!!.getDouble("totalPrice")
                ) + "\n\n")
                val qrCode = BarcodeQRCode("Cotización para el servicio de $service_title")
            } else if (bundleType == "object") {
                val object_title = bundle!!.getString("objectTitle")
                val object_specifications = txt_Specifications!!.text.toString()
                message = """
                    Cotización para el objeto:  $object_title.
                    
                    Con las siguientes especificaciones: $object_specifications
                    
                    Servicios extra: $allChipTextsServiceString
                    
                    Detalles adicionales: $allChipDetailsTexts
                    
                    Precio total: ${String.format("$%.2f", bundle!!.getDouble("totalPrice"))}
                    
                    
                    """.trimIndent()
            }

            // Store the message content to use later in onRequestPermissionsResult
            messageContent = message

            // Check if the storage write permission is granted
            if (ContextCompat.checkSelfPermission(
                    this@Act_OrderDetail,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission granted, generate the PDF file
                generatePdfFile(messageContent)
            } else {
                // Request the storage write permission
                ActivityCompat.requestPermissions(
                    this@Act_OrderDetail,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            }
        }
        btn_Next!!.setOnClickListener {
            val intent = Intent(this@Act_OrderDetail, Menu::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
        btn_Back!!.setOnClickListener(View.OnClickListener {
            if (bundle != null) {
                val intent: Intent
                intent = if (bundleType.contentEquals("car")) {
                    Intent(this@Act_OrderDetail, Act_CarParameters::class.java)
                } else if (bundleType.contentEquals("object")) {
                    Intent(this@Act_OrderDetail, Act_ObjectParameters::class.java)
                } else {
                    // Handle the case if the bundleType is unknown
                    return@OnClickListener
                }
                intent.putExtras(bundle!!)
                setResult(RESULT_OK, intent)
                finish()
            }
        })
    }

    private fun generatePdfFile(content: String?) {
        val fileName = "cotizacion.pdf"
        val folderPath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        val filePath = folderPath + File.separator + fileName
        try {
            // Create a PDF document
            val writer = PdfWriter(filePath)
            val pdfDocument = PdfDocument(writer)
            val document = Document(pdfDocument)
            pdfDocument.defaultPageSize = PageSize.A6
            val d = getDrawable(R.drawable.banner)
            val bitmap = (d as BitmapDrawable?)!!.bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val bitmapData = stream.toByteArray()
            val imageData = ImageDataFactory.create(bitmapData)
            val image = Image(imageData)

            // Add content to the PDF document
            val paragraph = Paragraph(content)
            paragraph.setTextAlignment(TextAlignment.LEFT)
            document.add(image)
            document.add(paragraph)


            // Close the document
            document.close()
            Toast.makeText(
                this,
                "PDF file created and saved in Downloads folder.",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error: " + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            // Check if the permission is granted
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, generate the PDF file
                generatePdfFile(messageContent)
            } else {
                // Permission denied, show a toast or handle accordingly
                Toast.makeText(this, "Storage write permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getPhoneNumberFromServer(networkRequests: Function_NetworkRequests) {
        phoneNumber = ""
        networkRequests.getPhone({ response ->
            try {
                val jsonObject = JSONObject(response)
                phoneNumberTemp = jsonObject.getString("phone")
                Log.e("ON RESPONSE", phoneNumberTemp!!)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }) { error ->
            Toast.makeText(
                this@Act_OrderDetail,
                "Error al recuperar información: " + error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onResume() {
        super.onResume()
        if (hasClickedWA) {
            btn_Next!!.visibility = View.VISIBLE
        } else {
            btn_WA!!.visibility = View.VISIBLE
            btn_Next!!.visibility = View.VISIBLE
        }
    }

    fun initUI() {
        btn_WA = findViewById(R.id.btn_NextObjectOrderDetail)
        btn_Back = findViewById(R.id.btn_Back)
        ServiceChipGrout = findViewById(R.id.ChipGrup_Service)
        ExtraDetailsChipGrout = findViewById(R.id.ChipGrup_ExtraDetails)
        txt_TotalPrice = findViewById(R.id.txt_TotalPrice)
        txt_ServiceTitle = findViewById(R.id.txt_ServiceDescription)
        txt_objectDetail = findViewById(R.id.txt_ObjectDetail)
        txt_Specifications = findViewById(R.id.txt_ObjectSpecifications)
        btn_Next = findViewById(R.id.btn_MainMenu)
        cardView_CarService = findViewById(R.id.card_ServicesSpecifications)
        cardView_TitleCarService = findViewById(R.id.card_TitulosParameterCar)
        cardView_Specifications = findViewById(R.id.card_objectSpecifications)
        cardView_ExtraServicesSpecifications = findViewById(R.id.card_ExtraServicesSpecifications)
        cardView_DetailsSpecifications = findViewById(R.id.card_DetailsSpecifications)
        bundle = intent.extras
        if (bundle != null) {
            bundleType = bundle!!.getString("bundleType")
            val totalPrice = bundle!!.getDouble("totalPrice")
            val formattedTotalPrice = String.format("$%.2f", totalPrice)
            if (bundleType.contentEquals("car")) {
                cardView_Specifications!!.setVisibility(View.GONE)
                txt_TotalPrice!!.setText(formattedTotalPrice)
                txt_ServiceTitle!!.setText(bundle!!.getString("serviceTitle"))
                txt_objectDetail!!.setText("Automóvil tipo: " + bundle!!.getString("carType"))
            } else if (bundleType.contentEquals("object")) {
                txt_TotalPrice!!.setText(String.format("Total: $%.2f", totalPrice))
                cardView_TitleCarService!!.setVisibility(View.GONE)
                cardView_CarService!!.setVisibility(View.GONE)
                txt_objectDetail!!.setText("Objeto: " + bundle!!.getString("objectTitle"))
                txt_Specifications!!.setText(
                    """Tamaño: ${bundle!!.getString("objectSize")} cm
Forma: ${bundle!!.getString("objectForm")}
Material: ${bundle!!.getString("objectMaterial")}"""
                )
            }
            val extraServices = bundle!!.getStringArrayList("extraServices")
            if (extraServices != null && extraServices.size > 0) {
                val servicesText = StringBuilder()
                for (service in extraServices) {
                    val chip = layoutInflater.inflate(R.layout.adapter_chip, null) as Chip
                    chip.text = service
                    ServiceChipGrout!!.addView(chip)
                    servicesText.append("- ").append(service).append("\n")
                }
            } else {
                cardView_ExtraServicesSpecifications!!.setVisibility(View.GONE)
            }
            val details = bundle!!.getStringArrayList("details")
            if (details != null && details.size > 0) {
                val detailsText = StringBuilder()
                for (detail in details) {
                    val chip = layoutInflater.inflate(R.layout.adapter_chip, null) as Chip
                    chip.text = detail
                    ExtraDetailsChipGrout!!.addView(chip)
                    detailsText.append("- ").append(detail).append("\n")
                }
            } else {
                cardView_DetailsSpecifications!!.setVisibility(View.GONE)
            }
        }
    }
}