
package com.mxn.soul.flowingdrawer.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mxn.soul.flowingdrawer.R;
import com.squareup.picasso.Picasso;

/**
 * Created by mxn on 2016/12/13.
 * MenuListFragment
 */

public class MenuListFragment extends Fragment {

    private ImageView ivMenuUserProfilePhoto;
    private TextView tvUserName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);
        ivMenuUserProfilePhoto = (ImageView) view.findViewById(R.id.ivMenuUserProfilePhoto);
        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        NavigationView vNavigation = (NavigationView) view.findViewById(R.id.vNavigation);
        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                mOnMenuClick.setClick(menuItem);
                return false;
            }
        }) ;
        ivMenuUserProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnUserImageClick.imageClick(v);
            }
        });
        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnUserImageClick.imageClick(v);
            }
        });
        setupHeader();
        return  view ;
    }
    public TextView getTvUserName(){
        return tvUserName;
    }
    private void setupHeader() {
        int avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);
        String profilePhoto = getResources().getString(R.string.user_profile_photo);
        Picasso.with(getActivity())
                .load(profilePhoto)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivMenuUserProfilePhoto);
    }
    private onMenuClick mOnMenuClick;

    /**
     * 侧滑菜单
     */
    public interface onMenuClick{
        void setClick(MenuItem menuItem);
    }
    public void setOnMenuClick(onMenuClick mOnMenuClick){
        this.mOnMenuClick = mOnMenuClick;
    }

    /**
     * 用户头像的点击接口
     */
    private onUserImageClick mOnUserImageClick;

    public interface onUserImageClick{
        void imageClick(View view);
    }
    public void setOnUserImageClick(onUserImageClick mOnUserImageClick){
        this.mOnUserImageClick = mOnUserImageClick;
    }


}
