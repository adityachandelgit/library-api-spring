package org.adityachandel.libraryapispring.model.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Credentials {
    private String username;
    private String password;
}
