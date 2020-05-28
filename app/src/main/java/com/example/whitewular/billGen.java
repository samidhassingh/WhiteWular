package com.example.whitewular;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class billGen extends AppCompatActivity{
    Button button;
    private FirebaseAuth mAuth;
    int x;
    int y;
    EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_gen);
        mAuth = FirebaseAuth.getInstance();
        editText2=(EditText) findViewById(R.id.editText2);
        button=findViewById(R.id.button);
       ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        createPDF();

    }

   private void createPDF() {
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
               String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
               PdfDocument myPdfDocument=new PdfDocument();
               Paint myPaint= new Paint();
               Paint titlePaint=new Paint();
               PdfDocument.PageInfo myPageInfo1=new PdfDocument.PageInfo.Builder(250,400,1).create();
               PdfDocument.Page myPage1=myPdfDocument.startPage(myPageInfo1);
               Canvas canvas=myPage1.getCanvas();

               titlePaint.setTextAlign(Paint.Align.CENTER);
               titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
               titlePaint.setTextSize(25);
               canvas.drawText("White Wular",myPageInfo1.getPageWidth()/2,30,titlePaint);

               titlePaint.setTextAlign(Paint.Align.CENTER);
               titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
               titlePaint.setTextSize(20);
               canvas.drawText("INVOICE",myPageInfo1.getPageWidth()/2,60,titlePaint);

               myPaint.setTextAlign(Paint.Align.LEFT);
               //myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
               myPaint.setTextSize(10f);
               myPaint.setColor(Color.BLACK);
               canvas.drawText("Customer ID:",10,80,myPaint);
               canvas.drawText(mAuth.getCurrentUser().getEmail(),10,95,myPaint);
               canvas.drawText("Date:"+currentDate,10,110,myPaint);
               canvas.drawText("Time:"+currentTime,10,125,myPaint);
               myPaint.setTextSize(13f);
               myPaint.setTextAlign(Paint.Align.LEFT);
               myPaint.setStyle(Paint.Style.FILL);
               myPaint.setColor(Color.BLACK);
               canvas.drawText("Item",10,160,myPaint);
               canvas.drawText("Price",70,160,myPaint);
               canvas.drawText("Qty",120,160,myPaint);
               canvas.drawText("Item Total",160,160,myPaint);

               int total=0;

                   canvas.drawText("Mask", 10, 180, myPaint);
                   canvas.drawText("100", 70, 180, myPaint);
                   canvas.drawText(editText2.getText().toString(), 120, 180, myPaint);
                   total = Integer.parseInt(editText2.getText().toString()) * 100;
                   canvas.drawText(String.valueOf(total), 160, 180, myPaint);

                   double fp = total - (total * 0.1);
                   canvas.drawLine(10, 190, myPageInfo1.getPageWidth() - 10, 190, myPaint);
                   myPaint.setTextAlign(Paint.Align.LEFT);
                   canvas.drawText("Discount(%): 10", 10, 205, myPaint);
                   myPaint.setColor(Color.RED);
                   myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                   canvas.drawText("Final Price: " + fp, 10, 220, myPaint);


               myPdfDocument.finishPage(myPage1);
               File file=new File(Environment.getExternalStorageDirectory(),"/FirstPDF.pdf");
               try {
                   myPdfDocument.writeTo(new FileOutputStream(file));
                   Toast.makeText(billGen.this, "Invoice has been successfully downloaded", Toast.LENGTH_SHORT).show();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               myPdfDocument.close();

           }
       });

   }


   /* public void onBackPressed(){
        super.onBackPressed();
        mAuth.signOut();
    }*/
}
