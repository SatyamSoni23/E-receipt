package com.easybusiness.e_receipt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class pdfMainActivity extends AppCompatActivity {
    ListView listPdf;
    public  static ArrayList<File> fileList = new ArrayList<>();
    PDFAdapter objAdapter;
    public static  int REQUEST_PERMISSIONS = 1;
    boolean booleanPermission;
    File dir;
    String dirpath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_main);
        listPdf = findViewById(R.id.listPdf);
        init();
    }
    private void init(){
        dirpath = android.os.Environment.getExternalStorageDirectory().toString();
        final String path = dirpath + "/E-receipt/";
        dir = new File(path);
        fnPermission();
        listPdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PdfActivity.class);
                intent.putExtra("position", i);
                startActivity(intent);

                Log.e("Position", i + "");
            }
        });
    }
    public ArrayList<File> getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    getfile(listFile[i]);

                } else {

                    boolean booleanpdf = false;
                    if (listFile[i].getName().endsWith(".pdf")) {

                        for (int j = 0; j < fileList.size(); j++) {
                            if (fileList.get(j).getName().equals(listFile[i].getName())) {
                                booleanpdf = true;
                            } else {

                            }
                        }

                        if (booleanpdf) {
                            booleanpdf = false;
                        } else {
                            fileList.add(listFile[i]);

                        }
                    }
                }
            }
        }
        return fileList;
    }
    private void fnPermission(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(pdfMainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            }
            else{
                ActivityCompat.requestPermissions(pdfMainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
            }
        }
        else{
            booleanPermission = true;
            getfile(dir);
            objAdapter = new PDFAdapter(getApplicationContext(), fileList);
            listPdf.setAdapter(objAdapter);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                booleanPermission = true;
                getfile(dir);

                objAdapter = new PDFAdapter(getApplicationContext(), fileList);
                listPdf.setAdapter(objAdapter);

            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }
}