package co.shrey.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Collections;


/**
 * Created by shrey on 12-06-2018.
 */

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = "MainActivity";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();;
    private DatabaseReference rootref = database.getReference();
    private DatabaseReference childref = rootref.getRef();
    List<UserInformation>  list = new ArrayList<>();
    FloatingActionButton fab_plus;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    static Bundle mBundleRecyclerViewState;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    LinearLayoutManager mLayoutmanager;
    DividerItemDecoration dividerItemDecoration;
    ListAdapter madapter;
    UserInformation userInfo;
    TextView name_iv,phone_iv,email_iv;
    SearchView searchView;
    ArrayAdapter<String> adapter;
    int swipeFlags;
    int swipe;
    private boolean ascending = true;
    private Paint p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_layout);
        p=new Paint();
        name_iv = (TextView) findViewById(R.id.name);
        phone_iv = (TextView) findViewById(R.id.phone);
        email_iv = (TextView) findViewById(R.id.email);
        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        getdata();
        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  startActivity(new );*/
                Intent intent = new Intent(MainActivity.this,StoringInDatabase.class);
                startActivity(intent);
            }
        });

    }

    protected void getdata() {

        childref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                /*uinfo=new UserInformation();*/
                    userInfo = data.getValue(UserInformation.class);
                    userInfo.setKey(data.getKey());
                    /*Log.e("TAG", userInfo.getAddress() + userInfo.getAge() + userInfo.getName());*/
                    list.add(userInfo);
                }
                //  setupList();
                madapter = new ListAdapter(MainActivity.this,list);
                mLayoutmanager = new LinearLayoutManager(MainActivity.this);
                dividerItemDecoration = new DividerItemDecoration(MainActivity.this, mLayoutmanager.getOrientation());
                recyclerView.setLayoutManager(mLayoutmanager);
                recyclerView.addItemDecoration(dividerItemDecoration);
                recyclerView.setAdapter(madapter);
               this.initializeViews();
               this.initSwipe();
            }
            private void initSwipe() {
                ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                        int position = viewHolder.getAdapterPosition();
                        final UserInformation groupBean = list.get(position);
                         swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
                        return makeMovementFlags(0, swipeFlags);
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        final int position = viewHolder.getAdapterPosition();
                        if (direction == ItemTouchHelper.RIGHT) {
                            final UserInformation Item = list.get(viewHolder.getAdapterPosition());
                            final int Index = viewHolder.getAdapterPosition();
                            madapter.message(list.get(position));
                            madapter.notifyDataSetChanged();

                        }

                        if (direction == ItemTouchHelper.LEFT) {


                            final UserInformation Item = list.get(viewHolder.getAdapterPosition());
                            final int Index = viewHolder.getAdapterPosition();
                            madapter.phonecall(list.get(position));

                           /* Snackbar snackbar = Snackbar
                                    .make(recyclerView, "Group called successfully!", Snackbar.LENGTH_LONG);
                            snackbar.setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // undo is selected, restore the deleted item
                                }
                            });
                            snackbar.addCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    //see Snackbar.Callback docs for event details
                                  *//*  if (isdelete) {
                                        deleteGroup(deletedItem.getGroupID());
                                    }*//*
                                    // Toast.makeText(HomeActivity.this, "dismiss", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onShown(Snackbar snackbar) {
                                    //Toast.makeText(HomeActivity.this, "show", Toast.LENGTH_SHORT).show();
                                    *//*isdelete=true;*//*
                                }
                            });*/
/*
                            snackbar.setActionTextColor(Color.YELLOW);
                            snackbar.show();*/
                            madapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        Bitmap icon;
                        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                            View itemView = viewHolder.itemView;
                            float height = (float) itemView.getBottom() - (float) itemView.getTop();
                            float width = height / 3;

                            if (dX > 0) {
                               p.setColor(Color.parseColor("#F3970B"));
                               RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                               c.drawRect(background, p);
                               icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_message_orange_500_24dp);
                               RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                               c.drawBitmap(icon, null, icon_dest, p);
                            }
                            else {
                                p.setColor(Color.parseColor("#388E3C"));
                                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                                c.drawRect(background, p);
                                icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_phone_green_700_24dp);
                                RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                                c.drawBitmap(icon, null, icon_dest, p);
                            }
                        }
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
                itemTouchHelper.attachToRecyclerView(recyclerView);
            }
        /*    private void initSwipe() {
                ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();

                        if (direction == ItemTouchHelper.LEFT){
                            madapter.removeItem(position);
                        } else {
                            removeView();
                            edit_position = position;
                            alertDialog.setTitle("Edit Country");
                            et_country.setText(countries.get(position));
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                        Bitmap icon;
                        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                            View itemView = viewHolder.itemView;
                            float height = (float) itemView.getBottom() - (float) itemView.getTop();
                            float width = height / 3;

                            if(dX > 0){
                                p.setColor(Color.parseColor("#388E3C"));
                                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                                c.drawRect(background,p);
                                icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit_white);
                                RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                                c.drawBitmap(icon,null,icon_dest,p);
                            } else {
                                p.setColor(Color.parseColor("#D32F2F"));
                                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                                c.drawRect(background,p);
                                icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                                RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                                c.drawBitmap(icon,null,icon_dest,p);
                            }
                        }
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
                itemTouchHelper.attachToRecyclerView(recyclerView);
            }
            }*/

            private void initializeViews() {
                  Collections.sort(list, new Comparator<UserInformation>() {
                    @Override
                    public int compare(UserInformation o1, UserInformation o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                madapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    //for search button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }
    //to recover the state when we press back in element from the list
    @Override
    protected void onPause()
    {
        super.onPause();

        // save RecyclerView state
        try {
            mBundleRecyclerViewState = new Bundle();
            Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
        }
        catch (Exception e){

    }
    }
    @Override
    protected void onResume()
    {
        super.onResume();

        // restore RecyclerView state
      /*  if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            try{
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);}
            catch(Exception e){


            }
        }*/
        if (mBundleRecyclerViewState != null && recyclerView != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            if (recyclerView.getLayoutManager() != null) {
                recyclerView.getLayoutManager().onRestoreInstanceState(listState);
            }
        }
    }
    //searching an element from the list
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        madapter.setfilter(newText);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:

                        Intent intent = new Intent(MainActivity.this,StoringInDatabase.class);
                        startActivity(intent);
                        // User chose the "Settings" item, show the app settings UI...
                        return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }*/

}

