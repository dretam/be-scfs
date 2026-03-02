package bank_mega.corsys.domain.model.ldap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LDAPResponse {
    private String username;
    private Boolean verified;
}
