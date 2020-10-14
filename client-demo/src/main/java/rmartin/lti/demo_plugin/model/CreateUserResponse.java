package rmartin.lti.demo_plugin.model;

import java.util.Optional;

public class CreateUserResponse {
    private String success;
    private Optional<CreateUserRequest> data;
    private Optional<String[]> errors;

    protected CreateUserResponse() {
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Optional<CreateUserRequest> getData() {
        return data;
    }

    public void setData(Optional<CreateUserRequest> data) {
        this.data = data;
    }

    public Optional<String[]> getErrors() {
        return errors;
    }

    public void setErrors(Optional<String[]> errors) {
        this.errors = errors;
    }
}
