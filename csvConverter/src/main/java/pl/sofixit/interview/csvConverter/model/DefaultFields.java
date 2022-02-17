package pl.sofixit.interview.csvConverter.model;

public enum DefaultFields {
    _TYPE("_type"), _ID("_id"), TYPE("type"), LATITUDE("latitude"), LONGITUDE("longitude");

    public final String field;

    DefaultFields(String field) {
        this.field = field;
    }
}
