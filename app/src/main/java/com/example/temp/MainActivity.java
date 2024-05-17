package com.example.temp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS = 1;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);  // Ensure you have the correct layout file

        listView = findViewById(R.id.pdfListView); // Ensure the correct ID

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {
            loadFiles();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filePath = (String) parent.getItemAtPosition(position);
                openFile(filePath);
            }
        });
    }

    private void loadFiles() {
        ArrayList<String> fileList = new ArrayList<>();
        File directory = Environment.getExternalStorageDirectory();
        searchFiles(directory, fileList);

        if (fileList.isEmpty()) {
            Toast.makeText(this, "No files found", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileList);
            listView.setAdapter(adapter);  // Ensure ListView is not null
        }
    }

    private void searchFiles(File directory, ArrayList<String> fileList) {
        List<String> imageExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp", "tiff", "ico");
        List<String> videoExtensions = Arrays.asList("mp4", "avi", "mkv", "mov", "wmv", "flv", "webm", "vob", "ogg");

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchFiles(file, fileList);
                } else {
                    String fileName = file.getName().toLowerCase();
                    String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

                    if (!imageExtensions.contains(fileExtension) && !videoExtensions.contains(fileExtension)) {
                        fileList.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }

    private void openFile(String filePath) {
        File file = new File(filePath);
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, getMimeType(filePath));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to open file", Toast.LENGTH_SHORT).show();
        }
    }

    private String getMimeType(String url) {
        String mimeType;
        String extension = url.substring(url.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "pdf":
                mimeType = "application/pdf";
                break;
            case "doc":
            case "docx":
                mimeType = "application/msword";
                break;
            case "ppt":
            case "pptx":
                mimeType = "application/vnd.ms-powerpoint";
                break;
            case "xls":
            case "xlsx":
                mimeType = "application/vnd.ms-excel";
                break;
            case "rtf":
                mimeType = "application/rtf";
                break;
            case "txt":
                mimeType = "text/plain";
                break;
            default:
                mimeType = "*/*";
                break;
        }
        return mimeType;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadFiles();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}