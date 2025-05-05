package com.vsu.myapp.controller;

import com.vsu.myapp.dto.ProfileDTO;
import com.vsu.myapp.dto.RecordDTO;
import com.vsu.myapp.request.CreateProfileRequest;
import com.vsu.myapp.request.CreateRecordRequest;
import com.vsu.myapp.request.UpdateRecordRequest;
import com.vsu.myapp.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/by-username")
    public ProfileDTO getById(@RequestParam String name) {
        return profileService.findByLogin(name);
    }

    @PostMapping
    public ProfileDTO create(@Valid @RequestBody CreateProfileRequest createProfileRequest) {
        return profileService.createProfile(createProfileRequest);
    }
    @PostMapping("/{profileId}/record")
    public RecordDTO create(@PathVariable long profileId, @Valid @RequestBody CreateRecordRequest createRecordRequest) {
        return profileService.createRecord(createRecordRequest,profileId);

    }
    @PutMapping("/{profileId}/record/edit")
    public RecordDTO update(@PathVariable long profileId, @Valid @RequestBody UpdateRecordRequest updateRecordRequest) {
        return profileService.updateRecord(updateRecordRequest,profileId);
    }
    @GetMapping("/{profileId}/record/{id}")
    public RecordDTO getRecord(@PathVariable long profileId, @PathVariable Long id) {
        return profileService.findRecordById(id,profileId);
    }
    @GetMapping("/{profileId}/record/address/{address}")
    public List<RecordDTO> getRecordByAddress(@PathVariable long profileId, @PathVariable String address) {
        return profileService.findRecordByAddress(profileId,address);
    }
    @DeleteMapping("/{profileId}/record/delete/{id}")
    public void deleteRecord(@PathVariable long profileId, @PathVariable Long id) {
        profileService.deleteRecordById(id,profileId);
    }
}
