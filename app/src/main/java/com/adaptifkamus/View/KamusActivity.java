package com.adaptifkamus.View;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.adaptifkamus.Control.AppControl;
import com.adaptifkamus.Control.SMSAdapter;
import com.adaptifkamus.Model.DBAdapter;
import com.adaptifkamus.R;

import java.util.ArrayList;
import java.util.List;

public class KamusActivity extends AppCompatActivity {
    //variabel view
    EditText masukanKata;
    Button btnCari;
    ListView daftarKata;

    //variabel database
    DBAdapter db;

    //variabel adapter array
    List<String> model = new ArrayList<String>();
    ArrayAdapter<String> adapter = null;
    List<String> listMember = new ArrayList<String>();

    //variabel sms manager
    SMSAdapter smsAdapter;

    AppControl appControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamus);

        //inisiasi database
        appControl = new AppControl(getBaseContext());
        appControl.inisiasiDB();

        db = new DBAdapter(this);


        //inisiasi variabel view
        masukanKata = (EditText) findViewById(R.id.masukanKata);
        btnCari = (Button) findViewById(R.id.btnCari);
        daftarKata = (ListView) findViewById(R.id.daftarKata);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,model);

        daftarKata.setAdapter(adapter);
        btnCari.setOnClickListener(onCari);

        //inisiasi list member
        listMember = appControl.InisiasiMember(db);

        //inisiasi sms
        smsAdapter = new SMSAdapter();
    }

    private View.OnClickListener onCari = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adapter.clear();
            Cursor hasil;
            db.open();
             hasil = db.CariKataArab(masukanKata.getText().toString());
            db.close();

            if (hasil.getCount() != 0) {
                adapter.insert(hasil.getString(0),0);
            } else {
                //smsAdapter.BroadcastSMS(listMember,"AK#R#KW#ARA#"+masukanKata.getText().toString());
                Toast.makeText(getBaseContext(), "Keyword tidak ditemukan", Toast.LENGTH_SHORT).show();
            }



        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kamus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}