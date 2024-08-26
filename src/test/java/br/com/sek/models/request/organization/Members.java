package br.com.sek.models.request.organization;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter

public class Members {
    private String createdTime;
    private String updatedTime;
    private String activatedTime;
    private String deactivatedTime;
    private MemberDetails memberDetails;

    @Override
    public String toString() {
        return "Members{" +
                "createdTime: '" + createdTime + '\'' +
                ", updatedTime: '" + updatedTime + '\'' +
                ", activatedTime: '" + activatedTime + '\'' +
                ", deactivatedTime: '" + deactivatedTime + '\'' +
                ", member: '" + memberDetails + '\'' +
                '}';
    }
}
