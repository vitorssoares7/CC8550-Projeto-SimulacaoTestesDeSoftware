package models;

public class ValidatorResponse {
    private boolean isSuccess;
    private String errorMessage;

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean bool) {
        isSuccess = bool;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String error) {
        errorMessage = error;
    }
}
