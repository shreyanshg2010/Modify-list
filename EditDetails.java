package co.shrey.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditDetails extends AppCompatActivity {
    String a;
    String b;
    String c;
    EditText name;
    EditText phone;
    EditText email;
    DatabaseReference ref;
    DatabaseReference pushedPostRef;
    UserInformation userInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editdetails);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        ref=database.getReference();
        userInformation= (UserInformation) getIntent().getSerializableExtra("Editing");
        insert();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.check, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check:
                edit();
                Intent i = new Intent(this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void edit() {
        a = name.getText().toString();
        b = phone.getText().toString();
        c = email.getText().toString();
            pushedPostRef = ref.child(userInformation.getKey());
        if(a != null && !a.isEmpty() && b.length()==10) {
            pushedPostRef.child("name").setValue(a);
            pushedPostRef.child("phone").setValue(b);
            pushedPostRef.child("email").setValue(c);
           if (a != null && a.length() > 0) {
                name.setText("");
                phone.setText("");
                email.setText("");
            }
        Toast.makeText(this, "Data Successfully Updated!", Toast.LENGTH_SHORT).show();
    }
    else{
            if(a != null && !a.isEmpty())
                Toast.makeText(this, "Number is invalid", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Name is not specified", Toast.LENGTH_SHORT).show();

        }
    }
    private void insert() {
        name.setText(userInformation.getName());
        phone.setText(userInformation.getPhone());
        email.setText(userInformation.getEmail());
        }
}

