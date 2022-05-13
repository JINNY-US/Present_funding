package com.example.present_funding;

import com.example.present_funding.PostInfo;

public interface OnPostListener {
    void onDelete(PostInfo postInfo);
    void onModify();
}
