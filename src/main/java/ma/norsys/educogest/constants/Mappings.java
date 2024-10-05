package ma.norsys.educogest.constants;

public final class Mappings {
    // Request Mapping URLS
    public static final String REQUEST_MAPPING_AUTH = "/api/auth";
    // Pages URLS
    public static final String SIGN_IN_PAGE = "/sign-in";
    public static final String SIGN_UP_PAGE = "/register-user";
    public static final String SIGN_YUP_PAGE ="/api/parents";
    public static final String SIGN_PAGE ="/teachers";
    public static final String SCHOOL_PAGE = "/api/school";
    public static final String CERTIFICATE_PAGE = "/api/certificates";

    //STAFF
    public static final String REQUEST_STAFF ="api/staff";
    public static final String CREATE_STAFF ="/save";
    public static final String SHOW_STAFF="/get";

    //ROOM
    public static final String REQUEST_ROOM ="api/room";
    public static final String CREATE_ROOM ="/save";
    public static final String SHOW_ROOM="/get";

    // GROUP

    public static final String REQUEST_GROUP ="api/group";
    public static final String CREATE_GROUP ="/save";
    public static final String SHOW_GROUP="/get";


    private Mappings() {
    }
}
