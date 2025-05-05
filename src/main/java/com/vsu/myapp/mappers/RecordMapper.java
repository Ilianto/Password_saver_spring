package com.vsu.myapp.mappers;

import com.vsu.myapp.Entity.Record;
import com.vsu.myapp.dto.RecordDTO;
import com.vsu.myapp.request.CreateRecordRequest;
import com.vsu.myapp.request.UpdateRecordRequest;
import org.springframework.stereotype.Component;

@Component
public class RecordMapper {
    public RecordDTO toRecordDTO(Record record) {
        return new RecordDTO(record.getId(), record.getSiteAddress(),record.getLogin(),record.getPassword());
    }
/*    public RecordDTO toRecordDTO(CreateRecordRequest createRecordRequest) {
        return new RecordDTO(createRecordRequest.getSiteAddress(),createRecordRequest.getLogin(),createRecordRequest.getPassword());
    }
    public RecordDTO toRecordDTO(UpdateRecordRequest updateRecordRequest) {
        return new RecordDTO(updateRecordRequest.getSiteAddress(),updateRecordRequest.getLogin(),updateRecordRequest.getPassword());
    }*/
}
