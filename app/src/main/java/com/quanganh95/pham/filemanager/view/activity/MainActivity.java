package com.quanganh95.pham.filemanager.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.quanganh95.pham.filemanager.R;
import com.quanganh95.pham.filemanager.entity.DetailFile;
import com.quanganh95.pham.filemanager.view.adapter.FileAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FileAdapter.OnItemClickListener {
    private static final int REQUEST_CODE_PERMISION = 8080;
    private static final String TAG = "MainActivity";

    private RecyclerView rcvListFile;
    private FileAdapter fileAdapter;
    private List<DetailFile> files;

    private TextView txtSoLuongTepTin;
    private File rootDirFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        // Xin quyền
        if (!requestPermision()) {
            String path = Environment.getExternalStorageDirectory().toString();
            initializeFile(path);
        }
        if (files != null) {
            fileAdapter = new FileAdapter(this, files);
            rcvListFile.setAdapter(fileAdapter);
            txtSoLuongTepTin.setText(fileAdapter.getCountFiles() + " tệp tin");
            fileAdapter.setOnItemClickListener(this);
        }
    }

    private boolean requestPermision() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        } else {
            if (isGranted(Manifest.permission.READ_EXTERNAL_STORAGE) && isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                return false;
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        REQUEST_CODE_PERMISION
                );
                return true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                String path = Environment.getExternalStorageDirectory().toString();
                initializeFile(path);
                fileAdapter = new FileAdapter(this, files);
                rcvListFile.setAdapter(fileAdapter);
                txtSoLuongTepTin.setText(fileAdapter.getCountFiles() + " tệp tin");
                fileAdapter.setOnItemClickListener(this);
            } else {
                Toast.makeText(this, "Close App", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private boolean isGranted(String permision) {
        return ActivityCompat.checkSelfPermission(this, permision) == PackageManager.PERMISSION_GRANTED;
    }

    private void initializeComponents() {
        rcvListFile = findViewById(R.id.rcvListFile);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvListFile.setLayoutManager(layoutManager);
        txtSoLuongTepTin = findViewById(R.id.txtSoLuongTepTin);
    }

    // Hàm lấy ra thư mục hay file trong đường dẫn path
    private void initializeFile(String path) {
        if (files == null) {
            files = new ArrayList<>();
        }
        rootDirFile = new File(path);
        File[] listFile = rootDirFile.listFiles();
        for (File oneFile : listFile) {
            if (isShowFile(oneFile.getName())) {
                Date lastModDate = new Date(oneFile.lastModified());
                if (oneFile.isDirectory()) {
                    files.add(new DetailFile(true, R.drawable.folder, oneFile.getName(), "Directory", "" + printDateCreateFile(lastModDate.toString())));
                } else {
                    files.add(new DetailFile(false, printFormatFile(oneFile.getName()), oneFile.getName(), oneFile.getUsableSpace() + "", "" + printDateCreateFile(lastModDate.toString())));
                }
            }
        }
    }


    /**
     * @param name tên thư mục or file
     * @return true nếu như có dấu . đằng trước
     */
    private boolean isShowFile(String name) {
        Character c = name.charAt(0);
        if (c == '.' || c == '_') {
            return false;
        }
        return true;
    }

    /**
     * @param s chuỗi tên file
     * @return format file
     */
    private int printFormatFile(String s) {
        String result = s.substring(s.indexOf(".") + 1, s.length());
        switch (result) {
            case "txt":
                return R.drawable.text;
            case "mp3":
                return 0;

        }
        return 0;
    }

    /**
     * @param s truyền vào là chuỗi thời gian mặc định
     * @return chuỗi thời gian theo định dạng dd/mm/yyyy hh:mm
     */
    private String printDateCreateFile(String s) {
        String result = "";
        // default:  s = Tue Jul 17 23:09:58 GMT+07:00 2018
        // custom: s = 18/01/2018 23:09

        String year = s.substring(s.length() - 4, s.length());
        int i1 = s.indexOf(" ", 0);
        int i2 = s.indexOf(" ", i1 + 1);
        int i3 = s.indexOf(" ", i2 + 1);
        String m = s.substring(i1 + 1, i2);
        int month = 0;
        switch (m) {
            case "Jan":
                month = 1;
                break;
            case "Feb":
                month = 2;
                break;
            case "Mar":
                month = 3;
                break;
            case "Apr":
                month = 4;
                break;
            case "May":
                month = 5;
                break;
            case "Jun":
                month = 6;
                break;
            case "Jul":
                month = 7;
                break;
            case "Aug":
                month = 8;
                break;
            case "Sep":
                month = 9;
                break;
            case "Oct":
                month = 10;
                break;
            case "Nov":
                month = 11;
                break;
            case "Dec":
                month = 12;
                break;
            default:
                month = 0;
                break;
        }
        int day = Integer.parseInt(s.substring(i2 + 1, i3));
        result = day + "/" + month + "/" + year;
        return result;
    }

    // Cập nhật lại giá trị số lượng tập tin
    private void updateTextFileCount() {
        txtSoLuongTepTin.setText(fileAdapter.getCountFiles() + " tệp tin");
    }

    @Override
    public void onItemClickListener(DetailFile detailFile) {
        if (detailFile.isDirectory()) {
            // Đây là thư mục
            // 1. Reset dữ liệu files bên FileAdapter
            fileAdapter.resetListFile();
            // 2: Cập nhật đường dẫn để gọi ra các thư mục tập tin bên trong thư mục đã Click
            String path = rootDirFile.getPath() + "/" + detailFile.getNameFile() + "/";
            initializeFile(path);
            // 3: Cập nhật lại rcvListFile
            fileAdapter.notifyDataSetChanged();
            // 4. Cập nhật lại giá trị số lượng tập tin
            updateTextFileCount();
        } else {
            Toast.makeText(this, detailFile.getNameFile(), Toast.LENGTH_SHORT).show();
        }

    }
}
