package com.vsu.myapp.mappers;

import com.vsu.myapp.Entity.Profile;
import com.vsu.myapp.dto.ProfileDTO;
import com.vsu.myapp.request.CreateProfileRequest;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public ProfileDTO toProfileDTO(Profile profile) {
        return new ProfileDTO(profile.getId(), profile.getLogin());
    }
/*    public ProfileDTO toProfileDTO(CreateProfileRequest createProfileRequest) {
        return new ProfileDTO(createProfileRequest.getId,createProfileRequest.getUserName());
    }*/
}
