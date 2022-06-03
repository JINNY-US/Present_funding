package com.example.present_funding;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder>{

    private Context context;
    private String[] sliderImage;
    private String[] sliderName;
    private String[] sliderBrand;
    private String[] sliderPrice;

    public ImageSliderAdapter(Context context, String[] sliderImage, String[] sliderBrand, String[] sliderName, String[] sliderPrice) {
        this.context = context;
        this.sliderImage = sliderImage;
        this.sliderBrand = sliderBrand;
        this.sliderName = sliderName;
        this.sliderPrice = sliderPrice;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_slider, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindSliderImage(sliderImage[position]);
        holder.mBrandView.setText(sliderBrand[position]);
        holder.mNameView.setText(sliderName[position]);
        holder.mPriceView.setText(sliderPrice[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImage.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mBrandView, mNameView, mPriceView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mBrandView = itemView.findViewById(R.id.txt_slide_brand);
            mNameView = itemView.findViewById(R.id.txt_slide_name);
            mPriceView = itemView.findViewById(R.id.txt_slide_price);
            mImageView = itemView.findViewById(R.id.imageSlider);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();                     // pos = 0, 1, 2...이런 식으로 index값을 가져와서 데이터를 전송함
                    if(pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, DetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 데이터를 전송할 activity 설정

                        intent.putExtra("brand", sliderBrand[pos]);      // pos에 맞는 데이터의 브랜드값을 전송
                        intent.putExtra("name", sliderName[pos]);      // pos에 맞는 데이터의 이름값을 전송
                        intent.putExtra("price", sliderPrice[pos]);        // pos에 맞는 데이터의 가격값을 전송
                        intent.putExtra("img", sliderImage[pos]);        // pos에 맞는 데이터의 이미지값을 전송

                        context.startActivity(intent);                                  // 데이터 전송 후 화면 전환
                    }
                }
            });

        }

        public void bindSliderImage(String imageURL) {
            Glide.with(context)
                    .load(imageURL)
                    .into(mImageView);
        }
    }



}
