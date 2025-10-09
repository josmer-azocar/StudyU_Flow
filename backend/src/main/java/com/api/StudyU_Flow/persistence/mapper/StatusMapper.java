package com.api.StudyU_Flow.persistence.mapper;
import com.api.StudyU_Flow.domain.Enum.Status;
import org.mapstruct.Named;

public class StatusMapper {
    @Named("stringToStatus")
    public static Status stringToStatus(String status){
        if (status == null) return null;

        return switch (status.toUpperCase()){
            case "PASSED" -> Status.PASSED;
            case "PENDING" -> Status.PENDING;
            case "IN_PROGRESS" -> Status.IN_PROGRESS;
            case "FAILED" -> Status.FAILED;
            default -> null;
        };
    }

    @Named("statusToString")
    public static String statusToString(Status status){
        if (status == null) return null;

        return switch (status){
            case IN_PROGRESS -> "IN_PROGRESS";
            case FAILED -> "FAILED";
            case PENDING -> "PENDING";
            case PASSED -> "PASSED";
        };
    }
}
