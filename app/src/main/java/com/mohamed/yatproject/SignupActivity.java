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
import android.util.Log;
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
import com.mohamed.yatproject.database.EmployeesManagerDatabase;
import com.mohamed.yatproject.database.users.User;

public class SignupActivity extends AppCompatActivity {

    private ImageView addUserImage;
    private EditText emailEditText,
            passwordEditText;
    private Button alreadyHaveAccountBtn;
    private Button signUpButton;

    private static final int REQUEST_STORAGE_CODE = 1000;
    private static final int CAMERA_CODE = 1001;
    private static final int OPEN_GALLERY_CODE = 200;
    private static final int TAKE_PHOTO_CODE = 201;
    private boolean isUserAddedImage = false;
    private String imageUri;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        initializeView();
        setViewsOnClickListeners();
    }

    /**
     * To initialize the views
     */
    private void initializeView() {
        addUserImage = findViewById(R.id.addUserImage);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        alreadyHaveAccountBtn = findViewById(R.id.alreadyHaveAccountBtn);
        signUpButton = findViewById(R.id.signUpBtn);
    }

    private void setViewsOnClickListeners() {
        addUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraOrGallery();
            }
        });
        alreadyHaveAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alreadyHaveAccountClicked();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpClicked();
            }
        });
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
                    imageUri = selectedImage.toString();
                    addUserImage.setImageURI(selectedImage);
                }
            } else {
                Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == TAKE_PHOTO_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap selectedImage  = (Bitmap) data.getExtras().get("data");
                if (selectedImage != null) {
                    isUserAddedImage = true;
                    addUserImage.setImageBitmap(selectedImage);
                }
            } else {
                Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void alreadyHaveAccountClicked() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void signUpClicked() {
        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError(getString(R.string.please_enter_email));
        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.setError(getString(R.string.please_enter_password));
        } else if (password.length() < 8) {
            passwordEditText.setError(getString(R.string.password_should_be_greater));
        } else if (!isUserAddedImage) {
            Toast.makeText( this, "Please Add a photo", Toast.LENGTH_LONG).show();
        } else {
            user = new User();
            user.email = email;
            user.password = password;
            user.imagePath = imageUri;
            new SaveUserThread().execute();
        }
    }

    class SaveUserThread extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... voids) {
            EmployeesDatabaseClient.getInstance(getApplicationContext())
                    .getEmployeesManagerDatabase()
                    .usersDAO()
                    .insertUser(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(SignupActivity.this, R.string.user_added_successfully,
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
