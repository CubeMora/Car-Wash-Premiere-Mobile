package com.carwashpremiere.carwashpremieremobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONException;
import org.json.JSONObject;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Act_OrderDetail extends AppCompatActivity {
    Button btn_WA, btn_Next, btn_Back;
    boolean hasClickedWA = false;
    String phoneNumber;
    String bundleType;
    String phoneNumberTemp;

    ChipGroup ServiceChipGrout, ExtraDetailsChipGrout;
    Bundle bundle;
    Function_NetworkRequests networkRequests;
    CardView cardView_TitleSpecifications, cardView_Specifications, cardView_TitleCarService, cardView_CarService,
            cardView_ExtraServicesSpecifications, cardView_DetailsSpecifications;

    TextView txt_TotalPrice, txt_ServiceTitle, txt_objectDetail, txt_ExtraServices, txt_Details, txt_Specifications;

    private String messageContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_act_order_detail);
        initUI();

        networkRequests = new Function_NetworkRequests(this);
        getPhoneNumberFromServer(networkRequests);

        btn_WA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasClickedWA = true;
                StringBuilder allChipDetailsTexts = new StringBuilder();
                StringBuilder allChipServicesTexts = new StringBuilder();

                for (int i = 0; i < ServiceChipGrout.getChildCount(); i++) {
                    View child = ServiceChipGrout.getChildAt(i);
                    // Verificar que el hijo sea un Chip
                    if (child instanceof Chip) {
                        Chip chip = (Chip) child;
                        // Obtener el texto del chip y agregarlo a la variable
                        String chipText = chip.getText().toString();
                        allChipServicesTexts.append(chipText).append("\n");
                    }
                }

                for (int i = 0; i < ExtraDetailsChipGrout.getChildCount(); i++) {
                    View child = ExtraDetailsChipGrout.getChildAt(i);
                    // Verificar que el hijo sea un Chip
                    if (child instanceof Chip) {
                        Chip chip = (Chip) child;
                        // Obtener el texto del chip y agregarlo a la variable
                        String chipText = chip.getText().toString();
                        allChipDetailsTexts.append(chipText).append("\n");
                    }
                }

                String allChipTextsServiceString = allChipServicesTexts.toString();
                String allChipTextsDetailsString = allChipDetailsTexts.toString();

                String message = "";
                if (bundleType.equals("car")) {
                    String service_title = txt_ServiceTitle.getText().toString();
                    String car_type = txt_objectDetail.getText().toString();

                    message = "Cotización para el servicio de " + service_title
                            + " con el siguiente detalle: \n\n"
                            + "Tipo de vehículo: " + car_type + "\n"
                            + "Servicios extra: " + allChipTextsServiceString + "\n"
                            + "Detalles adicionales: " + allChipDetailsTexts + "\n\n"
                            + "Precio total: " + String.format("$%.2f", bundle.getDouble("totalPrice")) + "\n\n"
                           ;

                    BarcodeQRCode qrCode = new BarcodeQRCode("Cotización para el servicio de " + service_title);


                } else if (bundleType.equals("object")) {
                    String object_title = bundle.getString("objectTitle");
                    String object_specifications = txt_Specifications.getText().toString();

                    message = "Cotización para el objeto:  " + object_title + "." +
                            "\n\n" + "Con las siguientes especificaciones: " + object_specifications + "\n\n" +
                            "Servicios extra: " + allChipTextsServiceString + "\n\n" +
                            "Detalles adicionales: " + allChipDetailsTexts + "\n\n"
                            + "Precio total: " + String.format("$%.2f", bundle.getDouble("totalPrice")) + "\n\n"
                            ;
                }

                // Store the message content to use later in onRequestPermissionsResult
                messageContent = message;

                // Check if the storage write permission is granted
                if (ContextCompat.checkSelfPermission(Act_OrderDetail.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, generate the PDF file
                    generatePdfFile(messageContent);
                } else {
                    // Request the storage write permission
                    ActivityCompat.requestPermissions(Act_OrderDetail.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        });

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_OrderDetail.this, Menu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle != null) {
                    Intent intent;
                    if (bundleType.contentEquals("car")) {
                        intent = new Intent(Act_OrderDetail.this, Act_CarParameters.class);
                    } else if (bundleType.contentEquals("object")) {
                        intent = new Intent(Act_OrderDetail.this, Act_ObjectParameters.class);
                    } else {
                        // Handle the case if the bundleType is unknown
                        return;
                    }
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void generatePdfFile(String content) {
        String fileName = "cotizacion.pdf";
        String folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String filePath = folderPath + File.separator + fileName;

        try {
            // Crear un documento PDF
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            pdfDocument.setDefaultPageSize(PageSize.A6);

            Drawable d = getDrawable(R.drawable.banner);
            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();

            ImageData imageData = ImageDataFactory.create(bitmapData);
            Image image = new Image(imageData);

            // Ajustar el tamaño de la imagen para que ocupe todo el ancho y 1/7 del alto de la hoja
            float pageWidth = PageSize.A6.getWidth();
            float pageHeight = PageSize.A6.getHeight();
            float imageWidth = pageWidth;
            float imageHeight = pageHeight / 7; // 1/7 del alto de la hoja

            image.setWidth(imageWidth);
            image.setHeight(imageHeight);

            // Centrar la imagen en la página
            float xPosition = (pageWidth - imageWidth) / 2;
            float yPosition = pageHeight - imageHeight;

            image.setFixedPosition(xPosition, yPosition);

            // Crear un código QR
            BarcodeQRCode qrCode = new BarcodeQRCode(content, null);
            Image qrCodeImage = new Image(qrCode.createFormXObject(ColorConstants.BLACK, pdfDocument));
            qrCodeImage.setWidth(100);
            qrCodeImage.setHeight(100);

            // Agregar contenido al documento PDF
            document.add(image);

            // Crear un párrafo con formato para el texto
            PdfFont font = PdfFontFactory.createFont(FontConstants.COURIER);
            Paragraph paragraph = new Paragraph(content)
                    .setFont(font)
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setMarginTop(30)
                    .setMarginBottom(10)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, 1)); // Agregar borde

            document.add(paragraph);

            document.add(qrCodeImage);

            // Cerrar el documento
            document.close();

            Toast.makeText(this, "Archivo PDF creado y guardado en la carpeta Descargas.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            // Check if the permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, generate the PDF file
                generatePdfFile(messageContent);
            } else {
                // Permission denied, show a toast or handle accordingly
                Toast.makeText(this, "Storage write permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getPhoneNumberFromServer(Function_NetworkRequests networkRequests) {
        phoneNumber = "";
        networkRequests.getPhone(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    phoneNumberTemp = jsonObject.getString("phone");
                    Log.e("ON RESPONSE", phoneNumberTemp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Act_OrderDetail.this, "Error al recuperar información: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasClickedWA) {
            btn_Next.setVisibility(View.VISIBLE);
        } else {
            btn_WA.setVisibility(View.VISIBLE);
            btn_Next.setVisibility(View.VISIBLE);
        }
    }

    void initUI() {
        btn_WA = findViewById(R.id.btn_NextObjectOrderDetail);
        btn_Back = findViewById(R.id.btn_Back);
        ServiceChipGrout = findViewById(R.id.ChipGrup_Service);
        ExtraDetailsChipGrout = findViewById(R.id.ChipGrup_ExtraDetails);
        txt_TotalPrice = findViewById(R.id.txt_TotalPrice);
        txt_ServiceTitle = findViewById(R.id.txt_ServiceDescription);
        txt_objectDetail = findViewById(R.id.txt_ObjectDetail);
        txt_Specifications = findViewById(R.id.txt_ObjectSpecifications);

        btn_Next = findViewById(R.id.btn_MainMenu);
        cardView_CarService = findViewById(R.id.card_ServicesSpecifications);
        cardView_TitleCarService = findViewById(R.id.card_TitulosParameterCar);
        cardView_Specifications = findViewById(R.id.card_objectSpecifications);
        cardView_ExtraServicesSpecifications = findViewById(R.id.card_ExtraServicesSpecifications);
        cardView_DetailsSpecifications = findViewById(R.id.card_DetailsSpecifications);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            bundleType = bundle.getString("bundleType");
            double totalPrice = bundle.getDouble("totalPrice");
            String formattedTotalPrice = String.format("$%.2f", totalPrice);

            if (bundleType.contentEquals("car")) {
                cardView_Specifications.setVisibility(View.GONE);
                txt_TotalPrice.setText(formattedTotalPrice);
                txt_ServiceTitle.setText(bundle.getString("serviceTitle"));
                txt_objectDetail.setText("Automóvil tipo: " + bundle.getString("carType"));
            } else if (bundleType.contentEquals("object")) {
                txt_TotalPrice.setText(String.format("Total: $%.2f", totalPrice));
                cardView_TitleCarService.setVisibility(View.GONE);
                cardView_CarService.setVisibility(View.GONE);
                txt_objectDetail.setText("Objeto: " + bundle.getString("objectTitle"));
                txt_Specifications.setText("Tamaño: " + bundle.getString("objectSize") + " cm" + "\n" +
                        "Forma: " + bundle.getString("objectForm") + "\n" +
                        "Material: " + bundle.getString("objectMaterial"));
            }

            ArrayList<String> extraServices = bundle.getStringArrayList("extraServices");
            if (extraServices != null && extraServices.size() > 0) {
                StringBuilder servicesText = new StringBuilder();
                for (String service : extraServices) {
                    Chip chip = (Chip) getLayoutInflater().inflate(R.layout.adapter_chip, null);
                    chip.setText(service);
                    ServiceChipGrout.addView(chip);
                    servicesText.append("- ").append(service).append("\n");
                }
            } else {
                cardView_ExtraServicesSpecifications.setVisibility(View.GONE);
            }

            ArrayList<String> details = bundle.getStringArrayList("details");
            if (details != null && details.size() > 0) {
                StringBuilder detailsText = new StringBuilder();
                for (String detail : details) {
                    Chip chip = (Chip) getLayoutInflater().inflate(R.layout.adapter_chip, null);
                    chip.setText(detail);
                    ExtraDetailsChipGrout.addView(chip);
                    detailsText.append("- ").append(detail).append("\n");
                }
            } else {
                cardView_DetailsSpecifications.setVisibility(View.GONE);
            }
        }
    }



}
