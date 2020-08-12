package in.co.iamannitian.iamannitian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MyProfile extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        final TextView tv1= (TextView)findViewById(R.id.tv1);
        final EditText et1=(EditText)findViewById(R.id.et1);
        ImageView edit1= (ImageView)findViewById(R.id.edit1);
        final Button bt1 = (Button)findViewById(R.id.bt1);
        final TextView tv2 = (TextView)findViewById(R.id.tv2);
        final  EditText et2=(EditText)findViewById(R.id.et2);
        ImageView edit2=(ImageView)findViewById(R.id.edit2);
        final Button bt2=(Button)findViewById(R.id.bt2);
        final EditText et3= (EditText)findViewById(R.id.et3);
        final EditText et4=(EditText)findViewById(R.id.et4);
        final TextView tv3=(TextView)findViewById(R.id.tv3);
        final TextView tv4=(TextView)findViewById(R.id.tv4);
        final Button bt3=(Button)findViewById(R.id.bt3);
        ImageView edit3=(ImageView)findViewById(R.id.edit3);
        ImageView profile_pic=(ImageView)findViewById(R.id.profile_pic);


        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setVisibility(View.INVISIBLE);
                et1.setVisibility(View.VISIBLE);
                bt1.setVisibility(View.VISIBLE);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String program = et1.getText().toString();
                tv1.setText(program);
                et1.setVisibility(View.INVISIBLE);
                tv1.setVisibility(View.VISIBLE);
                
            }
        });
        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv2.setVisibility(View.INVISIBLE);
                et2.setVisibility(View.VISIBLE);
                bt2.setVisibility(View.VISIBLE);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String about = et2.getText().toString();
                tv2.setText(about);
                et2.setVisibility(View.INVISIBLE);
                tv2.setVisibility(View.VISIBLE);

            }
        });

        edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv3.setVisibility(View.INVISIBLE);
                tv4.setVisibility(View.INVISIBLE);
                et3.setVisibility(View.VISIBLE);
                et4.setVisibility(View.VISIBLE);
                bt3.setVisibility(View.VISIBLE);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= et3.getText().toString();
                String college= et4.getText().toString();
                tv3.setText(name);
                tv4.setText(college);
                tv3.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.VISIBLE);
                et3.setVisibility(View.INVISIBLE);
                et4.setVisibility(View.INVISIBLE);
            }
        });

       profile_pic.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               select_image();
           }
       });




    }
    private void select_image() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageState(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            ImageView viewImage= (ImageView)findViewById(R.id.viewImage);
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageState().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }

                try {
                    String[] projection = new String[0];
                    final Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            projection, null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
                    if (Build.VERSION.SDK_INT >= 29) {

                        Uri imageUri= ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getInt(0));

                        Uri mediaUri = null;
                        try (ParcelFileDescriptor pfd = this.getContentResolver().openFileDescriptor(mediaUri, "r")) {
                            if (pfd != null) {
                                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                            }
                        } catch (IOException ex) {

                        }
                    }

                    else{


                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    viewImage.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageState()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }} catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath+"");
                viewImage.setImageBitmap(thumbnail);
            }
        }
    }
}
