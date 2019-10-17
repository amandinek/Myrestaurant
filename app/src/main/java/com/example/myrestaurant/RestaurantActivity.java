package com.example.myrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.MyRestaurantsArrayAdapter;
import com.example.myrestaurant.R;
import com.example.myrestaurant.adapters.RestaurantListAdapter;
import com.example.myrestaurant.models.Business;
import com.example.myrestaurant.models.Category;
import com.example.myrestaurant.models.YelpBusinessesSearchResponse;
import com.example.myrestaurant.network.YelpApi;
import com.example.myrestaurant.network.YelpClient;

import java.util.List;

public class RestaurantActivity extends AppCompatActivity {


//    @BindView(R.id.locationTextView) TextView mLocationTextView;
//    @BindView(R.id.listView) ListView mListView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.recycleView) RecyclerView mRecyclerView;
    private RestaurantListAdapter mAdapter;

    public List<Business> restaurant;

//    private String[] restaurants = new String[] {"Sweet Hereafter", "Cricket", "Hawthorne Fish House", "Viking Soul Food", "Red Square", "Horse Brass", "Dick's Kitchen", "Taco Bell", "Me Kha Noodle Bar", "La Bonita Taqueria", "Smokehouse Tavern", "Pembiche", "Kay's Bar", "Gnarly Grey", "Slappy Cakes", "Mi Mero Mole" };
//    private String[] cuisines = new String[] {"Vegan Food", "Breakfast", "Fishs Dishs", "Scandinavian", "Coffee", "English Food", "Burgers", "Fast Food", "Noodle Soups", "Mexican", "BBQ", "Cuban", "Bar Food", "Sports Bar", "Breakfast", "Mexican" };

    @Override
    protected  void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        ButterKnife.bind(this);

//        MyRestaurantsArrayAdapter adapter = new MyRestaurantsArrayAdapter(this, android.R.layout.simple_list_item_1, restaurants, cuisines);
//        mListView.setAdapter(adapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
//                String restaurant =((TextView)view).getText().toString();
//                Toast.makeText(RestaurantActivity.this,restaurant, Toast.LENGTH_LONG).show();
//            }
//        });
        Intent intent = getIntent();
        String location =intent.getStringExtra("location");
        YelpApi client = YelpClient.getClient();

        Call<YelpBusinessesSearchResponse> call = client.getRestaurants(location, "restaurants");
//        mLocationTextView.setText("Here are all the restaurant near: "+location);


        call.enqueue(new Callback<YelpBusinessesSearchResponse>() {

            @Override
            public void onResponse(Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) {

                hideProgressBar();

                if (response.isSuccessful()) {
//                    List<Business> restaurantsList = response.body().getBusinesses();
//                    String[] restaurants = new String[restaurantsList.size()];
//                    String[] categories = new String[restaurantsList.size()];
//
//                    for (int i = 0; i < restaurants.length; i++){
//                        restaurants[i] = restaurantsList.get(i).getName();
//                    }
//
//                    for (int i = 0; i < categories.length; i++) {
//                        Category category = restaurantsList.get(i).getCategories().get(0);
//                        categories[i] = category.getTitle();
//                    }

//                    ArrayAdapter adapter = new MyRestaurantsArrayAdapter(RestaurantActivity.this, android.R.layout.simple_list_item_1, restaurants, categories);
//                    mListView.setAdapter(adapter);

                    restaurant = response.body().getBusinesses();
                    mAdapter = new RestaurantListAdapter(RestaurantActivity.this, restaurant);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(RestaurantActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    showRestaurants();
                } else {
                    showUnsuccessfulMessage();
                }
            }

            private void showFailureMessage() {
                mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
                mErrorTextView.setVisibility(View.VISIBLE);
            }

            private void showUnsuccessfulMessage() {
                mErrorTextView.setText("Something went wrong. Please try again later");
                mErrorTextView.setVisibility(View.VISIBLE);
            }

            private void showRestaurants() {
                mRecyclerView.setVisibility(View.VISIBLE);
//        mLocationTextView.setVisibility(View.VISIBLE);
            }

            private void hideProgressBar() {
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {
                hideProgressBar();
                showFailureMessage();
            }
        });

    }

}

