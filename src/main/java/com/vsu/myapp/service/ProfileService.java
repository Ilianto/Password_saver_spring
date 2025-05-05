package com.vsu.myapp.service;

import com.vsu.myapp.Entity.Profile;
import com.vsu.myapp.Entity.Record;
import com.vsu.myapp.dto.ProfileDTO;
import com.vsu.myapp.dto.RecordDTO;
import com.vsu.myapp.exception.CryptException;
import com.vsu.myapp.exception.NotFoundException;
import com.vsu.myapp.exception.RepositoryException;
import com.vsu.myapp.exception.ValidationException;
import com.vsu.myapp.mappers.ProfileMapper;
import com.vsu.myapp.mappers.RecordMapper;
import com.vsu.myapp.repository.ProfileRepository;
import com.vsu.myapp.repository.RecordRepository;
import com.vsu.myapp.request.CreateProfileRequest;
import com.vsu.myapp.request.CreateRecordRequest;
import com.vsu.myapp.request.UpdateRecordRequest;
import com.vsu.myapp.utils.CryptoUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class ProfileService {
    private final static Logger log = Logger.getLogger(ProfileService.class.getName());
    private final ProfileRepository profileRepository;
    private final RecordRepository recordRepository;
    private final ProfileMapper profileMapper;
    private final RecordMapper recordMapper;


    public ProfileService(ProfileRepository profileRepository, RecordRepository recordRepository, ProfileMapper profileMapper, RecordMapper recordMapper) {
        this.profileRepository = profileRepository;
        this.recordRepository = recordRepository;
        this.profileMapper = profileMapper;
        this.recordMapper = recordMapper;
    }


    public ProfileDTO findByLogin(String login) {
        Profile profile = profileRepository.findByLogin(login).orElseThrow(() -> new NotFoundException("User not found"));
        return profileMapper.toProfileDTO(profile);
    }

    public ProfileDTO createProfile(@Valid CreateProfileRequest createProfileRequest) {
        if (profileRepository.findByLogin(createProfileRequest.getUserName()).isPresent()) {
            throw new ValidationException("User already exists");
        }
        if (profileRepository.create(createProfileRequest) == 1) {
            return profileMapper.toProfileDTO(profileRepository.findByLogin(createProfileRequest.getUserName()).get());
        }
        throw new RepositoryException("Error creating profile");
    }

    public RecordDTO createRecord(@Valid CreateRecordRequest createRecordRequest, long profileId) {
        Optional<Profile> profile = profileRepository.findByID(profileId);
        if (profile.isEmpty()) {
            throw new NotFoundException("User not found");
        } else {
            try {
                Profile p = profile.get();
                Record record = new Record(p.getId(),
                        createRecordRequest.getSiteAddress(),
                        createRecordRequest.getLogin(),
                        CryptoUtils.encrypt(createRecordRequest.getPassword(),
                                p.getSalt()));
                Long id = recordRepository.create(record);
                if ( id!=0) {
                    record.setId(id);
                    return recordMapper.toRecordDTO(record);
                } else {
                    throw new ValidationException("Wrong data");
                }

            } catch (DataAccessException e) {
                throw new RuntimeException("Error data access");
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                     IllegalBlockSizeException | BadPaddingException e) {
                throw new CryptException(e.getMessage());
            }

        }
    }

    public RecordDTO updateRecord(@Valid UpdateRecordRequest updateRecordRequest, long profileId) {
        Optional<Profile> profile = profileRepository.findByID(profileId);
        if (profile.isEmpty()) {
            throw new NotFoundException("User not found");
        } else {
            try {
                Profile p = profile.get();
                Record record = new Record(updateRecordRequest.getId(),
                        p.getId(),
                        updateRecordRequest.getSiteAddress(),
                        updateRecordRequest.getLogin(),
                        CryptoUtils.encrypt(updateRecordRequest.getPassword(),
                                p.getSalt()));

                Long id = recordRepository.update(record);
                if ( id!=0) {
                    record.setId(id);
                    return recordMapper.toRecordDTO(record);
                } else {
                    throw new ValidationException("Wrong data");
                }

            } catch (DataAccessException e) {
                throw new RepositoryException("Error data access");
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                     IllegalBlockSizeException | BadPaddingException e) {
                throw new CryptException(e.getMessage());
            }

        }
    }

    public RecordDTO findRecordById(long id, long profileID) {
        Optional<Profile> profile = profileRepository.findByID(profileID);
        if (profile.isEmpty()) {
            throw new NotFoundException("User not found");
        } else {
            try {
                Record record = recordRepository.findById(id).orElseThrow(() -> new NotFoundException("Record not found"));
                record.setPassword(CryptoUtils.decrypt(record.getPassword(), profile.get().getSalt()));
                return recordMapper.toRecordDTO(record);

            } catch (DataAccessException e) {
                throw new RepositoryException("Error data access");
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                     IllegalBlockSizeException | BadPaddingException e) {
                throw new CryptException(e.getMessage());
            }
        }

    }

    public List<RecordDTO> findRecordByAddress(long profileId, String address) {
        Optional<Profile> profile = profileRepository.findByID(profileId);
        if (profile.isEmpty()) {
            throw new NotFoundException("User not found");
        } else {
            try {
                List<Record> records = recordRepository.findByAddress(profile.get().getId(), address).orElseThrow(() -> new NotFoundException("Records not found"));
                List<RecordDTO> result = new ArrayList<>();
                for (Record record : records) {
                    record.setPassword(CryptoUtils.decrypt(record.getPassword(), profile.get().getSalt()));
                    result.add(recordMapper.toRecordDTO(record));
                }
                return result;

            } catch (DataAccessException e) {
                throw new RepositoryException("Error data access");
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                     IllegalBlockSizeException | BadPaddingException e) {
                throw new CryptException(e.getMessage());
            }
        }
    }

    public void deleteRecordById(long id, long profileId) {
        Optional<Profile> profile = profileRepository.findByID(profileId);
        if (profile.isEmpty()) {
            throw new NotFoundException("User not found");
        } else {
            if (recordRepository.deleteById(id) != 1) {
                throw new NotFoundException("Record not found");
            }
        }
    }
}
