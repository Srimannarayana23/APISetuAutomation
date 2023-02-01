package constants;

public class EndPoints {
    public final static String GET_ALL_STATES = "/admin/location/states";
    public final static String GET_DISTRICTS_FOR_STATE = "/admin/location/districts/{state_id}";

    public final static String GET_HOSPITALS_FOR_DISTRICT = "/appointment/sessions/public/findByDistrict";

}
