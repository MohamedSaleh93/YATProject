package com.mohamed.yatproject;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.mohamed.yatproject.database.EmployeesDatabaseClient;
import com.mohamed.yatproject.database.employees.Employee;

public class AddEmployeeActivity extends AppCompatActivity {

    private Button addEmployeeBtn;
    private ImageView employeeImage;
    private EditText employeeNameET;
    private EditText employeePositionET;
    private EditText employeeAgeET;
    private EditText employeePhoneET;
    private EditText employeeSalaryET;

    private static final int REQUEST_STORAGE_CODE = 1000;
    private static final int CAMERA_CODE = 1001;
    private static final int OPEN_GALLERY_CODE = 200;
    private static final int TAKE_PHOTO_CODE = 201;
    private boolean isUserAddedImage = false;
    private String imagePath;
    private Employee employee;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_employees);

        if (savedInstanceState != null) {
            boolean isRotated = savedInstanceState.getBoolean("isRotated");
            if (isRotated) {
                Toast.makeText(this, "Rotated", Toast.LENGTH_LONG).show();
            }
        }
        Intent intent = getIntent();
        initViews();

        addEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    employee = new Employee();
                    employee.name = employeeNameET.getText().toString();
                    employee.age = Integer.valueOf(employeeAgeET.getText().toString());
                    employee.phone = employeePhoneET.getText().toString();
                    employee.position = employeePositionET.getText().toString();
                    employee.salary = Double.valueOf(employeeSalaryET.getText().toString());
                    employee.imagePath = imagePath;
                    new AddEmployeeAsyncTask().execute();
                } else {
                    Toast.makeText(AddEmployeeActivity.this,
                            R.string.add_valid_employee_data, Toast.LENGTH_LONG).show();
                }
            }
        });

        employeeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraOrGallery();
            }
        });
    }

    private void initViews() {
        addEmployeeBtn = findViewById(R.id.addEmployeeBtn);
        employeeImage = findViewById(R.id.employeeImage);
        employeeNameET = findViewById(R.id.employeeNameET);
        employeePositionET = findViewById(R.id.employeePositionET);
        employeeAgeET = findViewById(R.id.employeeAgeET);
        employeePhoneET = findViewById(R.id.employeePhoneET);
        employeeSalaryET = findViewById(R.id.employeeSalary);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRotated", true);
    }

    private boolean validateInputs() {
        if (TextUtils.isEmpty(employeeNameET.getText().toString()) ||
        TextUtils.isEmpty(employeePositionET.getText().toString()) ||
        TextUtils.isEmpty(employeeAgeET.getText().toString()) ||
        TextUtils.isEmpty(employeePhoneET.getText().toString()) ||
        TextUtils.isEmpty(employeeSalaryET.getText().toString()) ||
        !isUserAddedImage) {
            return false;
        }
        return true;
    }

    private void openCameraOrGallery() {
        final CharSequence[] options = {getString(R.string.open_gallery),
                getString(R.string.camera), getString(R.string.cancel)};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(R.string.select_option);
        alertBuilder.setCancelable(false);
        alertBuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (options[which].equals(getString(R.string.open_gallery))) {
                    openGallery();
                } else if (options[which].equals(getString(R.string.camera))) {
                    openCamera();
                }
            }
        });
        alertBuilder.show();
    }

    private void openGallery() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_CODE);
        } else {
            Intent openGalleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGalleryIntent, OPEN_GALLERY_CODE);
        }
    }

    private void openCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_CODE);
        } else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // open the gallery files
                Toast.makeText(this, "Granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,
                        R.string.please_allow_storage_permission_to_open_gallery,
                        Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,
                        R.string.please_allow_camera_permission,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_GALLERY_CODE) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    isUserAddedImage = true;
                    imagePath = selectedImage.toString();
                    employeeImage.setImageURI(selectedImage);
                }
            } else {
                Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == TAKE_PHOTO_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap selectedImage  = (Bitmap) data.getExtras().get("data");
                if (selectedImage != null) {
                    isUserAddedImage = true;
                    employeeImage.setImageBitmap(selectedImage);
                }
            } else {
                Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_LONG).show();
            }
        }
    }

    class AddEmployeeAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            EmployeesDatabaseClient.getInstance(getApplicationContext())
                    .getEmployeesManagerDatabase()
                    .employeesDAO()
                    .insertEmployee(employee);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(AddEmployeeActivity.this, R.string.employee_added, Toast.LENGTH_LONG).show();
            setResult(RESULT_OK);
            finish();
        }
    }
}
