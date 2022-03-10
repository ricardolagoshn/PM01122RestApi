package com.aplicacion.pm01122restapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ActivityImagen extends AppCompatActivity {

    ImageView imageView;
    Button btngaleria, btnUploadImage;
    TextView textview1;
    Bitmap photo;
    static final int RESULT_GALLERY_IMG = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);

        imageView = (ImageView) findViewById(R.id.imageView);
        btngaleria = (Button) findViewById(R.id.btnGaleria);
        btnUploadImage = (Button) findViewById(R.id.btnUploadImage);
        textview1 = (TextView) findViewById(R.id.textView1);

        btngaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GaleriaImagenes();
            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String imageBase64 = GetStringImage( photo);
                //textview1.setText(imageBase64);
                SendImage();

            }
        });
    }

    private void SendImage()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestApiMethods.EndPointImageUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d("Respuesta ", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Respuesta ", error.toString());
            }
        })
        {

            protected Map<String, String> getParams()
            {
                String image = GetStringImage(photo);
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("IMAGEN", image);
                return parametros;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    private String GetStringImage(Bitmap photo)
    {
        try {
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 50, ba);
            byte[] imagebyte = ba.toByteArray();
            String encode = Base64.encodeToString(imagebyte, Base64.DEFAULT);
            return encode;
        }
        catch (Exception ex)
        { ex.toString(); }

        return "";

    }

    private void GaleriaImagenes()
    {
        Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentGaleria, RESULT_GALLERY_IMG );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri imageUri;

        if(resultCode == RESULT_OK && requestCode == RESULT_GALLERY_IMG)
        {
            try
            {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                photo = rotateImageIfRequired(photo, imageUri);
            }
            catch (Exception ex)
            {

            }
        }
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
}