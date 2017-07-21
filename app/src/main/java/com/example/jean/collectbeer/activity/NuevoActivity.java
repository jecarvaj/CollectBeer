package com.example.jean.collectbeer.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jean.collectbeer.Beer;
import com.example.jean.collectbeer.Helper;
import com.example.jean.collectbeer.R;
import com.example.jean.collectbeer.db.CervezasDbHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.CAMERA;

public class NuevoActivity extends AppCompatActivity {

    //Para las fotos!
    private static String APP_DIRECTORY="CollectBeer3";
    private final int MY_PERMISSIONS=100;
    private final int PHOTO_CODE=200;
    private String mPath;
    private RelativeLayout layout;
    private EditText etNombre, etVariedad, etPais, etAlcohol, etOtro;
    private RatingBar ratingBar;
    private ImageView imgViewBeer;
    String nombre;
    String variedad;
    String pais;
    String otro;
    String uriFoto=null;
    Float calificacion;
    Float alcohol;
    MenuItem itemCamera;
    Boolean permisoCamera=true;
    Bitmap resized;
    public static final String LOGCAT="PRUEBA";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        initView();
        Helper.initToolbar(this);
        Helper.initFAB(this, R.id.fab_new, prepareBeer);
        Helper.colorRatingBar(this, ratingBar);

        //Verifico si tengo permisos de cámara y habilito o no el boton para sacar foto
        if(checkPermisos()){
            permisoCamera=true;
        }else{
            permisoCamera=false;
        }

    }



    private void initView() {
        etNombre=(EditText)findViewById(R.id.etNombre);
        etVariedad=(EditText)findViewById(R.id.etVariedad);
        etPais=(EditText)findViewById(R.id.etPais);
        etAlcohol=(EditText)findViewById(R.id.etAlcohol);
        etOtro=(EditText)findViewById(R.id.etOtro);
        ratingBar=(RatingBar) findViewById(R.id.ratingBar);
        layout=(RelativeLayout) findViewById(R.id.layoutPrincipal);
        imgViewBeer=(ImageView) findViewById(R.id.imgBeerNuevo);
    }

    Runnable prepareBeer=new Runnable() {
        @Override
        public void run() {
            prepareBeer();
        }
    };
    private void prepareBeer() {
        nombre=etNombre.getText().toString();
        variedad=etVariedad.getText().toString();
        pais=etPais.getText().toString();

        if(!etAlcohol.getText().toString().equals("")){
            alcohol=Float.parseFloat(etAlcohol.getText().toString());
        }else{
            alcohol=Float.parseFloat("0.0");
        }
        otro=etOtro.getText().toString();
        calificacion= ratingBar.getRating();

        if(nombre.isEmpty()){
            Log.i(LOGCAT, "FALTA NOMBREE");
            Toast.makeText(NuevoActivity.this, "Debe especificar por lo menos un nombre!", Toast.LENGTH_SHORT).show();
        }else{
            Log.i(LOGCAT, "ELSEE AGREGAR CERVEA");
            addBeerDB();
        }
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new, menu);
         itemCamera = menu.findItem(R.id.action_camara);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        itemCamera.setEnabled(permisoCamera);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_camara:
                Helper.openCamera(this, PHOTO_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    if(data.getExtras().get("data")!=null){
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        resized = Bitmap.createScaledBitmap(photo, 260, 347, true);
                        imgViewBeer.setImageBitmap(resized);
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

    private boolean checkPermisos() {
        //verificar si version es menor a 6
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M)
            return true;

        //si los permisos estan aceptados ya, retornamos true
       if(checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)
           return true;

        //Si usamos un mensaje extra
        if(shouldShowRequestPermissionRationale(CAMERA)) {
            Snackbar.make(this.layout, "Permisos requeridos", Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{CAMERA}, MY_PERMISSIONS);
                }
            }).show();
        }else{
            requestPermissions(new String[]{CAMERA}, MY_PERMISSIONS);
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_PERMISSIONS){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                // btCamara.setEnabled(true);
                itemCamera.setEnabled(true);
            }
        }
    }


    private void addBeerDB() {
        if(resized!=null){
            savePhoto(resized);
        }else{
            uriFoto=null;
        }
        Beer cerveza=new Beer(nombre,variedad,pais,otro,uriFoto,calificacion,alcohol);
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

    private void savePhoto(Bitmap resized) {
        File pictureFile = getOutputMediaFile();
        uriFoto=pictureFile.toURI().toString();

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            resized.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("STORAGE", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("STORAGE", "Error accessing file: " + e.getMessage());
        }

    }

    private  File getOutputMediaFile() {
        ContextWrapper cw=new ContextWrapper(getApplicationContext());
        File file=cw.getDir(APP_DIRECTORY, Context.MODE_PRIVATE);
        boolean isDirectoryCreated = file.exists(); //Si el directorio está creado o no
        if (!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs(); //Si el directorio no está creado, lo creamos

        File newFile = null;
        if (isDirectoryCreated) {
            Long timestamp = System.currentTimeMillis() / 1000; //para nombre unico
            String imageName = nombre+"_" + timestamp.toString() + ".jpg"; //nombre de imagen
            newFile = new File(file,imageName);
        }
        return newFile;
    }

}
