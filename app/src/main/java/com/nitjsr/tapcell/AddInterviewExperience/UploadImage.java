package com.nitjsr.tapcell.AddInterviewExperience;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nitjsr.tapcell.R;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UploadImage extends Activity {

    private Uri filePath;
    private  final int PICK_IMAGE_REQUEST = 71;
    Button chooseBtn,uploadBtn;
    ImageView imageView;
    FirebaseStorage storage;
    StorageReference storageReference;
    static String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        chooseBtn=(Button)findViewById(R.id.chooseImage);
        uploadBtn=(Button)findViewById(R.id.uploadImage);
        imageView=(ImageView)findViewById(R.id.imageView);

        storage= FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();

            }
        });
    }

    //to upload the chosen image
    private void uploadImage() {

        if(filePath!=null){
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            progressDialog.setCancelable(false);

            //"images/"+UUID.randomUUID().toString()
//            final StorageReference ref=storageReference.child("images/"+ System.currentTimeMillis()+"."+getFileExtension(filePath));

            final StorageReference ref=storageReference.child(""+ System.currentTimeMillis()+"."+getFileExtension(filePath));
            ref.putFile(filePath).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageUrl = uri.toString();
                                    progressDialog.dismiss();

                                    // send back the uri to display this image in interview expereince activity
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("result","" + uri);
                                    setResult(Activity.RESULT_OK,returnIntent);
                                    finish();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UploadImage.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage((int)progress+"% uploaded");
                        }
                    });
        }else{
            //user haven't chosen an image
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    //to choose an image
    private void chooseImage() {

        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null&& data.getData()!=null ){
            filePath=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);

                Bitmap newBitmap = bitmap;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageInByte = stream.toByteArray();
                long lengthbmp = imageInByte.length / (1024);

                if(lengthbmp > 700){
                    Toast.makeText(this, "Please use image smaller than 700 KB. Pic Size: "+ lengthbmp + " KB", Toast.LENGTH_LONG).show();
                }else
                    imageView.setImageBitmap(newBitmap);

            }catch(Exception e){

            }
        }
    }

    //to get file extension from image like jpg
    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
