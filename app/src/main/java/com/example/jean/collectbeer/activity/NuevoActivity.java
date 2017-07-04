package com.example.jean.collectbeer.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.collectbeer.Beer;
import com.example.jean.collectbeer.R;
import com.example.jean.collectbeer.db.CervezasDbContract;
import com.example.jean.collectbeer.db.CervezasDbHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;

public class NuevoActivity extends AppCompatActivity {

    //Para las fotos!
    private static String APP_DIRECTORY="CollectBeer/";
    private static String MEDIA_DIRECTORY=APP_DIRECTORY+"CBeerPhotos"; //LE pongo punto para no ser visible en galeria
    private final int MY_PERMISSIONS=100;
    private final int PHOTO_CODE=200;
    private final int SELECT_PICTURE=300;
    static final int ADDED=1;
    private String mPath;
    private LinearLayout layout;
    private EditText etNombre, etVariedad, etPais, etAlcohol, etOtro;
    private RatingBar ratingBar;
    private Button btGuardar, btCamara;
    private ImageView imgViewBeer;
    String nombre;
    String variedad;
    String pais;
    String otro;
    String uriFoto;
    Float calificacion, alcohol;
    public static final String LOGCAT="PRUEBA";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        etNombre=(EditText)findViewById(R.id.etNombre);
        etVariedad=(EditText)findViewById(R.id.etVariedad);
        etPais=(EditText)findViewById(R.id.etPais);
        etAlcohol=(EditText)findViewById(R.id.etAlcohol);
        etOtro=(EditText)findViewById(R.id.etOtro);
        ratingBar=(RatingBar) findViewById(R.id.ratingBar);
        btGuardar=(Button) findViewById(R.id.btGuardar);
        btCamara=(Button) findViewById(R.id.btCamara);
        layout=(LinearLayout) findViewById(R.id.layoutPrincipal);
        imgViewBeer=(ImageView) findViewById(R.id.imgBeerNuevo);
        Log.i(LOGCAT, "ON CREATE!!");
        //Agrego esto para que no se abra el keyboard automaticamente en el scrollview!!
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        /*-------------------------------------------------------------------------------------------
         * INICIO ARREGLO PROBLEMA DEL RATING BAR
         * PARA PODER VER LAS ESTRELLAS EN COLORES
         * ANDROID 5, API 21 Y 22
         */
        if (ratingBar.getProgressDrawable() instanceof LayerDrawable) {
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            DrawableCompat.setTint(stars.getDrawable(2), ContextCompat.getColor(this, R.color.colorAccent));
        }
        else {
            // for Android 4.3, ratingBar.getProgressDrawable()=DrawableWrapperHoneycomb
            DrawableCompat.setTint(ratingBar.getProgressDrawable(), ContextCompat.getColor(this, R.color.colorAccent));
        }

        /*
         * FIN ARREGLO RATINGBAR
         --------------------------------------------------------------------------------------------*/


        //Verifico si tengo permisos de cámara y habilito o no el boton para sacar foto
        if(mayRequestStoragePermission()){
           // Toast.makeText(NuevoActivity.this, "Camara oermiso true", Toast.LENGTH_SHORT).show();
            btCamara.setEnabled(true);
        }else{
           // Toast.makeText(NuevoActivity.this, "camara permiso FALSEE", Toast.LENGTH_SHORT).show();
            btCamara.setEnabled(false);
        }


        //BOTON CAMARA:
        //Al apretar boton de camara se muestran las opciones en un dialogo(tomar foto/de galeria/cancelar)
        btCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showOptions(); //ELIMINO OPCION DE GALERIA POR AHORA
                openCamera();
            }
        });


        //BOTON GUARDAR:
        //Toma los datos de los inputs y llama a agregarCerveza() para guardarlos en la DB
        //mientras haya un nombre por lo menos
        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre=etNombre.getText().toString();
                variedad=etVariedad.getText().toString();
                pais=etPais.getText().toString();
                alcohol=Float.parseFloat(etAlcohol.getText().toString());
                otro=etOtro.getText().toString();
                calificacion= ratingBar.getRating();

                if(nombre.isEmpty()){
                    Log.i(LOGCAT, "FALTA NOMBREE");
                    Toast.makeText(NuevoActivity.this, "Debe especificar por lo menos un nombre!", Toast.LENGTH_SHORT).show();
                }else{
                    Log.i(LOGCAT, "ELSEE AGREGAR CERVEA");
                    agregarCerveza();
                }
            }
        });
    }


    /** ELIMINO OPCION DE CARGAR DE GALERIA POR AHORA
     ---------------------------------------------------------------------------------------------
    //Muestra el dialogo de opciones al apretar boton de camara
    private void showOptions() {
        final CharSequence[] option={"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setTitle("Elige una opcion");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0: //Tomar foto
                        openCamera();
                        break;
                    case 1: //Elegir de galeria
                        abrirGaleria();
                        break;
                    case 2: //Cancelar
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.show();
    }

    FIN ELIMINO OPCION DE CARGAR DE GALERIA POR AHORA
     ---------------------------------------------------------------------------------------------*/

    private void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PHOTO_CODE);

    }

    //Para que no se pierda el nombre del path al cambiar a la camara
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPath=savedInstanceState.getString("file_path");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    if(data.getExtras().get("data")!=null){
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        //Bitmap resized = Bitmap.createScaledBitmap(photo, 480, 640, true);
                        Bitmap resized = Bitmap.createScaledBitmap(photo, 260, 347, true);
                        imgViewBeer.setImageBitmap(resized);
                        Toast.makeText(this, "SI HAY HAY FOTO >"+data.getExtras().get("data").toString(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "NO HAY FOTO >"+data.getExtras().get("data").toString(), Toast.LENGTH_SHORT).show();
                    }

                    break;
               /**
                * ELIMINO GALERIA POR AHORA
                 case SELECT_PICTURE:
                    uriFoto=data.getDataString();

                    break;
                */
            }
        }
    }

    /**
     * ELIMINO ABRIRGALERIA() POR AHORA
     * ---------------------------------------------------------------------------------------
    private void abrirGaleria() {
        Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*"); //seleccionar imagen cualquier formato
        startActivityForResult(Intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE); //Escoger que app de imagen
    }
------------------------------------------------------------------------------------------------------*/

    private boolean mayRequestStoragePermission() {
        //verificar si version es menor a 6
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M)
            return true;

        //si los permisos estan aceptados ya, retornamos true
        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                && (checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED))
            return true;

        //Si usamos un mensaje extra
        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))) {
            Snackbar.make(layout, "Permisos necesarios para usar la aplicación", Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            }).show();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }
        return false;
    }

    private void agregarCerveza() {
        Beer cerveza=new Beer(nombre,variedad,pais,otro,imageViewToByte(imgViewBeer),calificacion,alcohol);
        CervezasDbHelper dbHelper =CervezasDbHelper.getInstance(getApplicationContext());

        long insertado=dbHelper.addBeer(cerveza);
        if ( insertado>0){
            Log.i(LOGCAT, "INSERTADO DATO TABLA");
            Intent intent=new Intent();
            setResult(RESULT_OK,intent);
            finish();//finishing activity
        }else{
            Log.i(LOGCAT, "ERROR INSETRTADO");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_PERMISSIONS){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                btCamara.setEnabled(true);
            }
        }else{
            explicar();
        }
    }

    private void explicar() {
        AlertDialog.Builder builder=new AlertDialog.Builder(NuevoActivity.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Debe aceptar los permisos para tomar fotos!");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri=Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
           //     finish();
            }
        });
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
