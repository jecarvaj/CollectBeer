package com.example.jean.collectbeer.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.collectbeer.R;
import com.example.jean.collectbeer.db.CervezasDbContract;
import com.example.jean.collectbeer.db.CervezasDbHelper;

import java.io.File;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;

public class NuevoActivity extends AppCompatActivity {

    //Para las fotos!
    private static String APP_DIRECTORY="CollectBeerApp/";
    private static String MEDIA_DIRECTORY=APP_DIRECTORY+"CBeerPhotos";
    private final int MY_PERMISSIONS=100;
    private final int PHOTO_CODE=200;
    private final int SELECT_PICTURE=300;
    static final int ADDED=1;
    private String mPath;
    private LinearLayout layout;
    private EditText etNombre, etVariedad, etPais, etAlcohol, etOtro;
    private RatingBar ratingBar;
    private Button btGuardar, btCamara;
    private TextView textFoto;
    String nombre;
    String variedad;
    String pais;
    String otro;
    String alcohol;
    String uriFoto;
    float calificacion;

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
        textFoto=(TextView) findViewById(R.id.textViewFoto);
        layout=(LinearLayout) findViewById(R.id.layoutPrincipal);


        if(mayRequestStoragePermission()){
            Toast.makeText(NuevoActivity.this, "Camara oermiso true", Toast.LENGTH_SHORT).show();
            btCamara.setEnabled(true);
        }else{
            Toast.makeText(NuevoActivity.this, "camara permiso FALSEE", Toast.LENGTH_SHORT).show();
            btCamara.setEnabled(false);
        }

        btCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre=etNombre.getText().toString();
                variedad=etVariedad.getText().toString();
                pais=etVariedad.getText().toString();
                alcohol=etAlcohol.getText().toString();
                otro=etOtro.getText().toString();
                calificacion= ratingBar.getRating();

                if(nombre.isEmpty()){
                    Toast.makeText(NuevoActivity.this, "Debe especificar por lo menos un nombre!", Toast.LENGTH_SHORT).show();
                }else{
                    agregarCerveza();
                }
            }
        });
    }

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

    private void openCamera() {
        //Guardo la ruta del almacenamiento interno del dspositivos
        File file=new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated=file.exists(); //Si el directorio está creado o no
        if(!isDirectoryCreated)
            isDirectoryCreated=file.mkdirs(); //Si el directorio no está creado, lo creamos

        if(isDirectoryCreated){
            Long timestamp=System.currentTimeMillis()/1000; //para nombre unico
            String imageName="beer_"+timestamp.toString()+".jpg"; //nombre de imagen
            mPath=Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator+imageName;
            File newFile=new File(mPath);

            //abro camara y le paso la url de la foto que sacaré..creo?
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }

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
                    MediaScannerConnection.scanFile(this,new String[]{mPath},
                            null,
                            new MediaScannerConnection.OnScanCompletedListener(){
                                @Override
                                public void onScanCompleted(String path, final Uri uri){
                                    uriFoto=uri.toString();

                                    runOnUiThread(new Runnable() { //para actualizar correctamente la vista
                                        @Override
                                        public void run() {
                                            textFoto.setText(R.string.msgExitoSubirFoto);
                                        }
                                    });



                                }
                            });
                    break;
                case SELECT_PICTURE:
                    uriFoto=data.getDataString();

                    break;
            }
        }
    }

    private void abrirGaleria() {
        Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*"); //seleccionar imagen cualquier formato
        startActivityForResult(Intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE); //Escoger que app de imagen
    }

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
        CervezasDbHelper dbHelper = new CervezasDbHelper(getApplicationContext());
        Log.i("DATABASESS", "Comienza getwritableatabase");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.i("DATABASESS", "TERMINA getwritableatabase");

        ContentValues valores = new ContentValues();
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_NOMBRE, nombre);
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_VARIEDAD, variedad);
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_PAIS, pais);
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_ALCOHOL, alcohol);
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_OTRO, otro);
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_FOTO, uriFoto);
        valores.put(CervezasDbContract.Cervezas.COLUMN_NAME_CALIFICACION, calificacion);


        long insertado = db.insert(CervezasDbContract.Cervezas.TABLE_NAME, null, valores);
        if (insertado > 0) {

            Log.i("DATABASESS", "INSERTADO DATO TABLA");
            Intent intent=new Intent();
            setResult(1,intent);
            finish();//finishing activity
        }else{
            Log.i("DATABASESS", "ERROR INSETRTADO");
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
}
