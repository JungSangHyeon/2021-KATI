package com.plim.kati_app.domain2.model.forBackend.userAccount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyPasswordRequest {

    private	String beforePassword;
    private	String afterPassword;


}