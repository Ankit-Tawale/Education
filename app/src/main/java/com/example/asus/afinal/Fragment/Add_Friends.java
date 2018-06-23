package com.example.asus.afinal.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.afinal.Contacts;
import com.example.asus.afinal.ContactsRecyclerAdapter;
import com.example.asus.afinal.R;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 */
public class Add_Friends extends Fragment {

    ArrayList<Contacts> selectUsers;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ContactsRecyclerAdapter adapter;
    Cursor phones;
    View root;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;



    public Add_Friends() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_friends, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.contacts_list);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        selectUsers = new ArrayList<Contacts>();
        showContacts();
        return root;
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(getContext(),Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            phones = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            LoadContact loadContact = new LoadContact();
            loadContact.execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(getContext(), "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            if (phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {

                }

                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                    Contacts selectUser = new Contacts();
                    selectUser.setName(name);
                    selectUser.setPhone(phoneNumber);
                    selectUsers.add(selectUser);


                }
            } else {
                Log.e("Cursor close 1", "----------------");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // sortContacts();
            int count=selectUsers.size();
            ArrayList<Contacts> removed=new ArrayList<>();
            ArrayList<Contacts> contacts=new ArrayList<>();
            for(int i=0;i<selectUsers.size();i++){
                Contacts inviteFriendsProjo = selectUsers.get(i);

                if(inviteFriendsProjo.getName().matches("\\d+(?:\\.\\d+)?")||inviteFriendsProjo.getName().trim().length()==0){
                    removed.add(inviteFriendsProjo);
                }else{
                    contacts.add(inviteFriendsProjo);
                }
            }
            contacts.addAll(removed);
            selectUsers=contacts;
            adapter = new ContactsRecyclerAdapter(inflater, selectUsers);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

        }
    }

}
