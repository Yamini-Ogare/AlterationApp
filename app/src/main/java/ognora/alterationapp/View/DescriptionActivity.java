package ognora.alterationapp.View;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import ognora.alterationapp.Model.ProductModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.CategoryViewModel;
import ognora.alterationapp.ViewModel.ProductViewModel;

public class DescriptionActivity extends AppCompatActivity {

    public ImageView imageView;
    public TextView t1, t2, t3, t4 ;
    public Button button ;
    CategoryViewModel categoryViewModel ;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        final ProductViewModel viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        imageView = findViewById(R.id.desimg);
        t1 = findViewById(R.id.desname);
        t2 = findViewById(R.id.short_description);
        t3 = findViewById(R.id.description);
        t4 = findViewById(R.id.price_product);

        button = findViewById(R.id.addToCart);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Description");


        Bundle bundle = getIntent().getExtras();
        final ProductModel product = (ProductModel) bundle.get("product");

        t1.setText(product.getService_type());
        t2.setText(product.getDescription());
        t3.setText(product.getDescription());
        t4.setText("Price : Rs. "+product.getAlteration_price().toString());

        Glide.with(this)
                .load(product.getImage_url())
                .into(imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   viewModel.addToCart(product);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.cart :  // show items in cart
                Intent intent = new Intent(this, CartActivity.class);
                this.startActivity(intent);
                break;
            case R.id.profile :
                break;
        }
        return super.onOptionsItemSelected(item);
    }





}


