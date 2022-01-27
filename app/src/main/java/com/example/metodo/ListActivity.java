package com.example.metodo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.metodo.Utility.Personaldetails;
import com.example.metodo.database.MyAdapter;
import com.example.metodo.database.MyCustomListAdapter;
import com.example.metodo.databinding.ListViewBinding;
import com.example.metodo.databinding.AlertprofileCustomlistBinding;
import com.example.metodo.databinding.ListViewBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
 private     MyAdapter myAdapter;
    private  Context context;
  private    Cursor cursor;
         ListViewBinding mainBinding;
         AlertprofileCustomlistBinding alertprofileBinding;
        private int clickedposition;
    Boolean isAllFabsVisible;


//    int DAY_OF_MONTH,MONTH,YEAR;
//    Calendar calendar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ListViewBinding.inflate(getLayoutInflater());
        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are
        // invisible
        isAllFabsVisible = false;
        setContentView(mainBinding.getRoot());
        context = this;
        myAdapter = new MyAdapter(context);
        myAdapter.openDataBase();
        mainBinding.addFab.shrink();
       mainBinding.taskDeleteFab.hide();
        mainBinding.addPersonFab.hide();
         mainBinding.addPersonActionText.setVisibility(View.GONE);
         mainBinding.taskDeleteText.setVisibility(View.GONE);

        mainBinding.listview.setOnItemClickListener(this);
        cursor=myAdapter.getAllRecords();
        registerForContextMenu(mainBinding.listview);
//           myAdapter.deleteAllRecords(context);
        loaddata();
//        DatePickerDialog pickerDialog =new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int dayofmonth, int month, int year) {
//
//                String setdate=dayofmonth+"-"+(month+1)+"-"+year;
//                alertprofileBinding.etdate.setText(setdate);
//
//            }
//        },DAY_OF_MONTH,MONTH,YEAR);
        mainBinding.addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAllFabsVisible) {

                    // when isAllFabsVisible becomes
                    // true make all the action name
                    // texts and FABs VISIBLE.
                    mainBinding.taskDeleteFab.show();
                    mainBinding.addPersonFab.show();
                    mainBinding.addPersonActionText.setVisibility(View.VISIBLE);
                    mainBinding.taskDeleteText.setVisibility(View.VISIBLE);
                    // Now extend the parent FAB, as
                    // user clicks on the shrinked
                    // parent FAB
                    mainBinding.addFab.extend();

                    // make the boolean variable true as
                    // we have set the sub FABs
                    // visibility to GONE
                    isAllFabsVisible = true;
                    // when isAllFabsVisible becomes
                    // true make all the action name
                    // texts and FABs VISIBLE.

                }else {
                    // when isAllFabsVisible becomes
                    // true make all the action name
                    // texts and FABs GONE.
                    mainBinding.taskDeleteFab.hide();
                    mainBinding.addPersonFab.hide();
                    mainBinding.addPersonActionText.setVisibility(View.GONE);
                    mainBinding.taskDeleteText.setVisibility(View.GONE);

                    // Set the FAB to shrink after user
                    // closes all the sub FABs
                    mainBinding.addFab.shrink();

                    // make the boolean variable false
                    // as we have set the sub FABs
                    // visibility to GONE
                    isAllFabsVisible = false;
                }
                }
        });
        mainBinding.addPersonFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertprofileBinding=AlertprofileCustomlistBinding.inflate(getLayoutInflater());
                Dialog dialog = new Dialog(context);
                dialog.setContentView(alertprofileBinding.getRoot());
                dialog.setCancelable(false);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.width =WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;

                alertprofileBinding.submit.setOnClickListener(new View.OnClickListener() {
                    @Override
//                    ,alertprofileBinding.etdate.getEditableText().toString()
                    public void onClick(View view) {
                        myAdapter.insertRecord(context,alertprofileBinding.titleedit.getEditableText().toString(),alertprofileBinding.taskedit.getEditableText().toString());/*,alertprofileBinding.titledate.getEditText().toString()*** */
                        dialog.dismiss();
//                             getDetainList();
//                        cursor=myAdapter.getAllRecords(); no1
                        loaddata();
                    }
                });
                dialog.show();
                dialog.getWindow().setAttributes(layoutParams);
            }
        });
        mainBinding.taskDeleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAdapter.deleteAllRecords(context);
//                cursor=myAdapter.getAllRecords(); no2
                loaddata();
            }
        });
    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String name = parent.getItemAtPosition(position).toString();
//        showToast(name);
        Personaldetails personDetails = (Personaldetails)parent.getItemAtPosition(position);
//        Log.d("PersonDetails", ""+personDetails);
    }
    private  List<Personaldetails> getDetainList() {
        cursor=myAdapter.getAllRecords();
        List<Personaldetails> finalist = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
           String SerialNo =cursor.getString(0);//serialno.
                String title =cursor.getString(1);//title
                String content = cursor.getString(2);//content
//                String date = cursor.getString(3);//date ***
                Personaldetails personaldetails =new Personaldetails(title,content);/*,date *** */
                finalist.add(personaldetails);
            }while (cursor.moveToNext());
        }
        return finalist;
    }
    private void loaddata(){
        if (getDetainList().size() >0){
            mainBinding.listview.setVisibility(View.VISIBLE);
            MyCustomListAdapter myCustomListAdapter =new MyCustomListAdapter(getDetainList(),context);
                mainBinding.listview.setAdapter(myCustomListAdapter);
        }else {
            Toast.makeText(context, "NO data found", Toast.LENGTH_SHORT).show();
            mainBinding.listview.setVisibility(View.GONE);
        }

    }


    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.db_insetation,menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//         switch (item.getItemId()){
//             //TODO:insert record
//             case R.id.insert:
//               alertprofileBinding=AlertprofileCustomlistBinding.inflate(getLayoutInflater());
//                 Dialog dialog = new Dialog(context);
//                dialog.setContentView(alertprofileBinding.getRoot());
//                dialog.setCancelable(false);
//                 WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//                 layoutParams.width =WindowManager.LayoutParams.MATCH_PARENT;
//                 layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
//
//                 alertprofileBinding.submit.setOnClickListener(new View.OnClickListener() {
//                     @Override
//                     public void onClick(View view) {
//                         myAdapter.insertRecord(context,alertprofileBinding.titleedit.getEditableText().toString(),alertprofileBinding.taskedit.getEditableText().toString());
//                             dialog.dismiss();
////                             getDetainList();
//                           cursor=myAdapter.getAllRecords();
//                           loaddata();
//
//                     }
//                 });
//                 dialog.show();
////                 dialog.getWindow().setAttributes(layoutParams);
//             break;
//             case R.id.deleted_all:
////                 myAdapter.deleteAllRecords(context);
////                 cursor=myAdapter.getAllRecords();
////                 loaddata();
//                 break;
//         }

//        return super.onOptionsItemSelected(item);
//    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
         getMenuInflater().inflate(R.menu.context_menu_items,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                //TODO:to delete record
                cursor.moveToPosition(clickedposition);
                String colRow = cursor.getString(0);
                myAdapter.deleteRecord(colRow, context);
//                cursor=myAdapter.getAllRecords();
                loaddata();
                break;
            case R.id.upgrade:
                //TODO: to upgrade record
                AlertprofileCustomlistBinding updateprofileCustomBinding=AlertprofileCustomlistBinding.inflate(getLayoutInflater());
         Dialog dialog =new Dialog(context);
         dialog.setCancelable(false);
         dialog.setContentView(updateprofileCustomBinding.getRoot());
         updateprofileCustomBinding.submit.setText("UPGRADE");
//                cursor.moveToPosition(clickedposition);
      WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
      layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
      layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;

                cursor.moveToPosition(clickedposition);

                String rowId = cursor.getString(0);
                String title = cursor.getString(1);// title
                String content = cursor.getString(2);// content
//                String date=cursor.getString(3);//date ***
                updateprofileCustomBinding.titleedit.setText(title);
                updateprofileCustomBinding.taskedit.setText(content);
//                updateprofileCustomBinding.etdate.setText(date);//***
                                         dialog.show();
                updateprofileCustomBinding.submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
        myAdapter.updateRecord(context,rowId,updateprofileCustomBinding.titleedit.getEditableText().toString(),updateprofileCustomBinding.taskedit.getEditableText().toString());/*,updateprofileCustomBinding.etdate.getEditableText().toString() *** */
                        dialog.dismiss();
                        loaddata();


                    }
                });

//                dialog.show();
                dialog.getWindow().setAttributes(layoutParams);
                break;
        }


        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                       clickedposition=position;
        return false;
    }
}