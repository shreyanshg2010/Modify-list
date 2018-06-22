package co.shrey.contacts;



import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ebanx.swipebtn.SwipeButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

//adapter class is needed because we have to show it in list form
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    Intent intent;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    List<UserInformation> list;
    UserInformation uinfo;
    private Context context;
    List<UserInformation> arrayList = new ArrayList<>();
    SwipeButton swipeButton;
    private static final int REQUEST_CALL = 1;

    public void delete(int position) {
        remove(list.get(position));
        list.remove(position);
        notifyDataSetChanged();
        // notifyItemRemoved(position);
    }

    public ListAdapter(Context ctx, List<UserInformation> list) {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        this.context = ctx;
        this.list = list;
        arrayList.addAll(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtname.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void remove(UserInformation userInformation) {
        ref.child(userInformation.getKey()).removeValue();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView txtname;

        public ViewHolder(View itemView) {
            super(itemView);
            txtname = (TextView) itemView.findViewById(R.id.name);
            /* swipeButton = (SwipeButton) itemView.findViewById(R.id.swipe);*/
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        //we implement serializable to the word data to find the position of the data.Basically compressing the data so that displayable data is seen
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, Details.class);
            intent.putExtra("data", list.get(getAdapterPosition()));
            context.startActivity(intent);

        }

        @Override
        public boolean onLongClick(View v) {
            createDialog();

            return true;
        }


        private void createDialog() {
            AlertDialog.Builder alrt = new AlertDialog.Builder(context);
            alrt.setCancelable(true);
            alrt.setTitle("Alert");
            alrt.setMessage("Are u sure u want to delete it?");
            alrt.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });
            alrt.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    delete(getPosition());

                }
            });
            alrt.show();
        }


    }


    //searching an element from the list
    public void setfilter(String newText) {
        list.clear();
        if (!newText.equals("")) {
            for (UserInformation uinfo : arrayList) {
                if (uinfo.getName().toLowerCase().startsWith(newText))
                    list.add(uinfo);
            }

        } else {
            list.addAll(arrayList);
        }
        notifyDataSetChanged();
}

    public void phonecall(UserInformation userInformation) {
        String number = userInformation.getPhone();
        String dial = "tel:" + number;
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_CALL);
            return;
        }
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

    }
    public void message(UserInformation userInformation){
        String number = userInformation.getPhone();
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.setData(Uri.parse("sms:" + number));
        context.startActivity(smsIntent);
    }

}

