package Moap.TravelWith.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class PostingSearchDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endDate;
    String query;
    Integer money;

    // ==> startDate: null인경우 1900몇년..
    // ==> endDate: null인경우 2100몇년..
    //빈 string도 줘야한다
    //Integer 빈값이면 ==> 100억으로 설정..


}
