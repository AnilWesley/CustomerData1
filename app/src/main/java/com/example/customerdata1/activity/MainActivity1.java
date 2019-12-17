package com.example.customerdata1.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import java.util.Objects;

public class MainActivity1 extends AppCompatActivity implements NameAdapter.OnItemClickListener {

    NameAdapter.OnItemClickListener onItemClickListener;
    RecyclerView recyclerView;
    FileOutputStream outputStream;

    NameAdapter nameAdapter;
    List<Product> list;
    TextView items,grandtotal;
    FloatingActionButton floatingActionButton;
    TextView textView;
    String Name,Number;
    Button addData;
    EditText editTextName,editTextPrice,editTextTotalPrice,editTextQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        onItemClickListener = (NameAdapter.OnItemClickListener)this;
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        textView = (TextView)findViewById(R.id.empty_record_text);
        items =(TextView)findViewById(R.id.text_quantity1);
        grandtotal=(TextView)findViewById(R.id.text_price1);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setEnabled(false);

        addData = (Button) findViewById(R.id.addData);




        isStoragePermissionGranted();

        final StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        list = new ArrayList<Product>();


        nameAdapter = new NameAdapter(this, list,onItemClickListener);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(nameAdapter);



        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        assert bundle != null;
         Name= bundle.getString("Name");
         Number = bundle.getString("Number");


          editTextName = (EditText) findViewById(R.id.edit_name);
          editTextPrice = (EditText) findViewById(R.id.edit_price);
          editTextTotalPrice = (EditText) findViewById(R.id.edit_total_price);
          editTextQuantity = (EditText) findViewById(R.id.edit_quantity);



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


        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextName.getText().toString();
                String quantity = (editTextQuantity.getText().toString());
                String price2 = ((editTextPrice.getText().toString()));
                String price = (editTextTotalPrice.getText().toString());




                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(quantity) || TextUtils.isEmpty(price2)|| TextUtils.isEmpty(price)) {

                    Toast.makeText(MainActivity1.this, "Enter Details", Toast.LENGTH_SHORT).show();


                }else {
                    Product nameDetails1 = new Product(name, quantity,price2, price,false);
                    list.add(nameDetails1);

                    nameAdapter.notifyDataSetChanged();
                    editTextName.setText("");
                    editTextQuantity.setText("");
                    editTextPrice.setText("");
                    editTextTotalPrice.setText("");
                    editTextName.requestFocus();

                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }




                if (list.size()>0)
                {
                    textView.setVisibility(View.GONE);
                    floatingActionButton.setEnabled(true);

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



        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity1.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                    }

                    @Override public void onLongItemClick(View view, final int position) {
                        // do whatever

                        final AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity1.this);
                        builder1.setTitle("Confirm Modification...!!!");
                        builder1.setIcon(R.drawable.ic_delete_forever_black_24dp);
                        builder1.setMessage("Are You Sure to Modify ?");
                        builder1.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
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
                                    floatingActionButton.setEnabled(false);
                                }

                            }
                        });
                        builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder1.setNeutralButton("UPDATE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Remove the item on remove/button click


                                editTextName.setText(list.get(position).getName());
                                editTextQuantity.setText(list.get(position).getQuantity());
                                editTextPrice.setText(list.get(position).getPrice());
                                editTextTotalPrice.setText(list.get(position).getTotalPrice());

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
                                    floatingActionButton.setEnabled(false);
                                }
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
                "   Number : "+Number+"\t\t"+"Time : "+currentTime+"\n\n"+
                "   * ";

        BILL = BILL + "--------------------------------------------------------------------------------\n";
        /*BILL = BILL + String.format("%1$-10s" , "Name");
        BILL = BILL + "\n";*/
        BILL = BILL + String.format("%1$10s %2$10s %3$10s %4$10s  %5$10s", "Item","Quantity", "Price","Total","Selected");


        for (Product list1: list) {
            String name = list1.getName();
            String qty = list1.getQuantity();
            String price = list1.getPrice();
            String totalprice = list1.getTotalPrice();
            boolean checked = list1.isSelected();
            if (checked) {
                BILL = BILL + "\n\n" + String.format("%1$10s %2$10s %3$10s %4$10s %5$10s", name, qty, price, totalprice, "\u2713");

            } else {
                /*BILL = BILL + "\n" + String.format("%1$-10s", name);*/
                BILL = BILL + "\n\n" + String.format("%1$10s %2$10s %3$10s %4$10s %5$10s", name, qty, price, totalprice, "");
            }
        }

        BILL = BILL + "\n--------------------------------------------------------------------------------";

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

            File cachePath = new File(MainActivity1.this.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        File imagePath = new File(MainActivity1.this.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(MainActivity1.this, "com.example.myapp.fileprovider", newFile);

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
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
    public void onItemClick(View view, final int position) {
        final AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity1.this);
        builder1.setTitle("Confirm Modification...!!!");
        builder1.setIcon(R.drawable.ic_delete_forever_black_24dp);
        builder1.setMessage("Are You Sure to Modify ?");
        builder1.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
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
                    floatingActionButton.setEnabled(false);
                }

            }
        });
        builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder1.setNeutralButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Remove the item on remove/button click


                editTextName.setText(list.get(position).getName());
                editTextQuantity.setText(list.get(position).getQuantity());
                editTextPrice.setText(list.get(position).getPrice());
                editTextTotalPrice.setText(list.get(position).getTotalPrice());

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
                    floatingActionButton.setEnabled(false);
                }
            }
        });

        builder1.show();
    }
}
