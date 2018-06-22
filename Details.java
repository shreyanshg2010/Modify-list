package co.shrey.contacts;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static co.shrey.contacts.R.id.email;
import static co.shrey.contacts.R.id.phone;

public class Details extends AppCompatActivity {
    String a;
    String b;
    String c;
    DatabaseReference ref;
    DatabaseReference pushedPostRef;
    EditText name;
    EditText phone;
    EditText email;
    Uri mSelectedContactUri;
    private static final String TAG = "Details";
    UserInformation userInformation=new UserInformation();
    TextView name_iv,phone_iv,email_iv;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    static Bundle mBundleRecyclerViewState;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<UserInformation> list;
    DatabaseReference firebaseDatabase;
    AlertDialog dialog;
    LinearLayoutManager mLayoutmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);
        name_iv = (TextView) findViewById(R.id.name);
        phone_iv = (TextView) findViewById(R.id.phone);
        email_iv = (TextView) findViewById(R.id.email);
        dialog = new AlertDialog.Builder(this).create();
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        ref=database.getReference();
        userInformation= (UserInformation) getIntent().getSerializableExtra("data");
        initView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:Intent intent = new Intent(this , EditDetails.class);
                intent.putExtra("Editing",userInformation);
                startActivity(intent);
                // User chose the "Settings" item, show the app settings UI...
                return true;
                // User chose the "Settings" item, show the app settings UI...

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    //to display name address and email in the last activity
    private void initView() {
        name_iv.setText(userInformation.getName());
        phone_iv.setText(userInformation.getPhone());
        email_iv.setText(userInformation.getEmail());
    }

}



