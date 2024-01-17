package com.example.a4lingo.Services;

import com.example.a4lingo.item.ProfileItem;

import java.util.Observable;

public class ProfileService{

    public ProfileItem getProfileItem(String userId) {
        ProfileItem sampleProfile = new ProfileItem();
        sampleProfile.setUserName("John Doe");
        sampleProfile.setLoginName("john_doe");
        sampleProfile.setEmail("john@example.com");
        sampleProfile.setPassword("123456789");
        sampleProfile.setPhoneNumber("0908070605");

        return sampleProfile;
    }

    public boolean saveProfile(ProfileItem profileItem) {
        return true;
    }
}
