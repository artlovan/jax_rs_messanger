package org.jaxrs.messanger.services;

import org.jaxrs.messanger.models.Profile;
import org.jaxrs.messanger.database.DataBaseStubs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ProfileService {
    private static Map<String, Profile> profiles = DataBaseStubs.getProfiles();

    public List<Profile> getAllProfiles() {
        return new ArrayList<>(profiles.values());
    }

    public Profile getProfile(String profileName) {
        return profiles.get(profileName);
    }

    public Profile addProfile(Profile profile) {
        if (profiles.containsKey(profile.getProfileName())) {
            return null;
        }

        profile.setId(profiles.size() + 1);
        profiles.put(profile.getProfileName(), profile);

        return profile;
    }

    public Profile updateProfile(Profile profile) {
        if (profile.getProfileName().isEmpty()) {
            return null;
        }
        Profile updatedProfile = profiles.get(profile.getProfileName());
        profile.setId(updatedProfile.getId());
        profiles.put(profile.getProfileName(), profile);

        return profile;
    }

    public Profile deleteProfile(String profileName) {
        return profiles.remove(profileName);
    }
}
