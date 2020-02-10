package rmartin.lti.api.model;

public class LTIContextUpdateRequest {
    LTIContext context;
    boolean updateInDB;

    public LTIContextUpdateRequest(LTIContext context, boolean updateInDB) {
        this.context = context;
        this.updateInDB = updateInDB;
    }

    public LTIContext getContext() {
        return context;
    }

    public boolean isUpdateInDB() {
        return updateInDB;
    }
}
