package br.com.sek.models.request.organization;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MemberDetails {
    private String name;

    @Override
    public String toString() {
        return "memberDetails{" +
                "name: '" + name + '\'' +
                '}';
    }
}
