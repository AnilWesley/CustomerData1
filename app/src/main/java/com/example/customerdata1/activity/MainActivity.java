package com.example.customerdata1.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerdata1.adapter.NameAdapter;
import com.example.customerdata1.R;
import com.example.customerdata1.RecyclerItemClickListener;
import com.example.customerdata1.model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FileOutputStream outputStream;

    NameAdapter nameAdapter;
    List<Product> list;
    TextView items,grandtotal;
    FloatingActionButton floatingActionButton;
    TextView textView;
    String Name,Number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        textView = (TextView)findViewById(R.id.empty_record_text);
        items =(TextView)findViewById(R.id.text_quantity1);
        grandtotal=(TextView)findViewById(R.id.text_price1);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);



        isStoragePermissionGranted();

        final StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        list = new ArrayList<Product>();
        nameAdapter = new NameAdapter(this,list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(nameAdapter);


        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        assert bundle != null;
         Name= bundle.getString("Name");
         Number = bundle.getString("Number");


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                    }

                    @Override public void onLongItemClick(View view, final int position) {
                        // do whatever

                        final AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this);
                        builder1.setMessage("Are You Sure to Delete ?");
                        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Remove the item on remove/button click
                                list.remove(position);
                                nameAdapter.notifyItemRemoved(position);
                                nameAdapter.notifyItemRangeChanged(position,list.size());
                                // Show the removed item label`enter code here`

                                float totalPrice2 = 0;
                                int size1 = list.size();
                                items.setText("Total Items : "+size1);

                                for (int i = 0; i<list.size(); i++)
                                {
                                    totalPrice2 += Float.parseFloat(list.get(i).getTotalPrice());

                                    grandtotal.setText("Grand Total : "+totalPrice2);
                                }
                                grandtotal.setText("Grand Total : "+totalPrice2);

                                if (list.size()==0)
                                {
                                    textView.setVisibility(View.VISIBLE);
                                }

                            }
                        });
                        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder1.show();

                    }
                })
        );






        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                texttoImage();

            }
        });




    }


    public void texttoImage()
    {
        float totalPrice3 = 0;
        int size1 = list.size();
        for (int i = 0; i<list.size(); i++)
        {
            totalPrice3 += Float.parseFloat(list.get(i).getTotalPrice());


        }



        String BILL = "";

        String currentDate = new SimpleDateFormat("dd-MMM-yy", Locale.getDefault()).format(new Date());

        String currentTime = new SimpleDateFormat("hh:mm", Locale.getDefault()).format(new Date());

        BILL =  "\n"+
                "   Name : "+Name+"\t\t"+"Date : "+currentDate+"\n\n" +
                "   Number : "+Number+"\t\t"+"Time : "+currentTime+"\n\n";

        BILL = BILL + "----------------------------------------------------------------------\n";
        /*BILL = BILL + String.format("%1$-10s" , "Name");
        BILL = BILL + "\n";*/
        BILL = BILL + String.format("%1$10s %2$10s %3$10s %4$10s", "Item","Quantity", "Price","Total");


        for (Product list1: list){
            String name =  list1.getName();
            String qty = list1.getQuantity();
            String price = list1.getPrice();
            String totalprice = list1.getTotalPrice();
            /*BILL = BILL + "\n" + String.format("%1$-10s", name);*/
            BILL = BILL + "\n\n" + String.format("%1$10s %2$10s %3$10s %4$10s ",name, qty, price,totalprice);
        }

        BILL = BILL + "\n----------------------------------------------------------------------";

        BILL = BILL + "\n\n";
        BILL = BILL + "   Total Items : " + " " + size1  + "\n\n";
        BILL = BILL + "   Grand Total : " + " " + totalPrice3  + "\n";



        BILL = BILL + "\n ";
        Log.v("ANIL_PRINT","\n"+BILL);

        String text = BILL;

        final Rect bounds = new Rect();
        TextPaint textPaint = new TextPaint() {
            {
                setColor(Color.BLACK);
                setTextAlign(Align.LEFT);
                setTextSize(15f);
                setAntiAlias(true);
            }
        };
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        StaticLayout mTextLayout = new StaticLayout(text, textPaint,
                bounds.width(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        int maxWidth = -1;
        for (int i = 0; i < mTextLayout.getLineCount(); i++) {
            if (maxWidth < mTextLayout.getLineWidth(i)) {
                maxWidth = (int) mTextLayout.getLineWidth(i);
            }
        }
        final Bitmap bmp = Bitmap.createBitmap(maxWidth , mTextLayout.getHeight(),
                Bitmap.Config.ARGB_8888);
        bmp.eraseColor(Color.WHITE);// just adding black background
        final Canvas canvas = new Canvas(bmp);
        mTextLayout.draw(canvas);

        File filepath = Environment.getExternalStorageDirectory();
       /* File dir = new File(filepath.getAbsolutePath()+"/Customer Data/");
        dir.mkdir();*/

        File file = new File(filepath,System.currentTimeMillis()+".png");
        try {
            outputStream = new FileOutputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "Demo" , "Demo");

        bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // save bitmap to cache directory
        try {

            File cachePath = new File(MainActivity.this.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        File imagePath = new File(MainActivity.this.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(MainActivity.this, "com.example.myapp.fileprovider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("image/png");
            shareIntent.setPackage("com.whatsapp");
            startActivity(shareIntent);
            finish();

        }

    }



    public void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v("TAG","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            final View view = layoutInflater.inflate(R.layout.adddata, null);
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setCancelable(false);
            final EditText editTextName = (EditText) view.findViewById(R.id.edit_name);
            final EditText editTextPrice = (EditText) view.findViewById(R.id.edit_price);
            final EditText editTextTotalPrice = (EditText) view.findViewById(R.id.edit_total_price);
            final EditText editTextQuantity = (EditText) view.findViewById(R.id.edit_quantity);


            editTextQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try{
                        final int quantity = Integer.parseInt(editTextQuantity.getText().toString());
                        final float price =Float.parseFloat((editTextPrice.getText().toString()));


                        float totalprice1 = (price * quantity);
                        editTextTotalPrice.setText("" + totalprice1);

                    }
                    catch(Exception e){
                        editTextTotalPrice.setText("0");

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            editTextPrice.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {


                    try{
                        final int quantity = Integer.parseInt(editTextQuantity.getText().toString());
                        final float price = Float.parseFloat((editTextPrice.getText().toString()));


                        float totalprice1 = (price * quantity);
                        editTextTotalPrice.setText("" + totalprice1);

                    }
                    catch(Exception e){
                        editTextTotalPrice.setText("0");

                    }

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                }

                @Override
                public void afterTextChanged(Editable arg0) {


                }
            });

             alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ADD", new DialogInterface.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String name = editTextName.getText().toString();
                    String quantity = (editTextQuantity.getText().toString());
                    String price2 = ((editTextPrice.getText().toString()));
                    String price = (editTextTotalPrice.getText().toString());


                    if(TextUtils.isEmpty(name) || TextUtils.isEmpty(quantity) || TextUtils.isEmpty(price2)|| TextUtils.isEmpty(price)) {

                        Toast.makeText(MainActivity.this, "Enter Details", Toast.LENGTH_SHORT).show();


                    }else {
                        Product nameDetails1 = new Product(name, quantity,price2, price);
                        list.add(nameDetails1);
                    }




                    if (list.size()>0)
                    {
                        textView.setVisibility(View.GONE);

                    }

                    float totalPrice2 = 0;
                    int size1 = list.size();
                    items.setText("Total Items : "+size1);

                    for (int i = 0; i<list.size(); i++)
                    {
                        totalPrice2 += Float.parseFloat(list.get(i).getTotalPrice());

                        grandtotal.setText("Grand Total : "+totalPrice2);
                    }
                }
            });


            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });


            alertDialog.setView(view);
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
