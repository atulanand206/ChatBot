package com.creations.mvvm.models.props;

import com.creations.condition.Preconditions;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContactProps implements Serializable {

    @Nullable
    private String name;
    @Nullable
    private String phone;
    @Nullable
    private String email;

    public ContactProps() {
    }

    public ContactProps(@Nullable final String pName,
                        @Nullable final String pPhone,
                        @Nullable final String pEmail) {
        name = pName;
        phone = pPhone;
        email = pEmail;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setName(@NonNull final String pName) {
        name = Preconditions.requiresNonNull(pName, "Name");
    }

    public void setPhone(@NonNull final String pPhone) {
        phone = Preconditions.requiresNonNull(pPhone, "Phone");
    }

    public void setEmail(@NonNull final String pEmail) {
        email = Preconditions.requiresNonNull(pEmail, "Email");
    }
}
