package common;

public class StringCommon {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    public static final String YYYY_MM_DD_REGEX = "^(?:(?:19|20)\\d\\d-(?:(?:0[13578]|1[02])-(?:0[1-9]|[12]\\d|3[01])|(?:0[469]|11)-(?:0[1-9]|[12]\\d|30)|02-(?:0[1-9]|1\\d|2[0-8]))|(?:(?:19|20)(?:[02468][048]|[13579][26])|(?:20)00)-02-29)$";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String NUMBER_REGEX = "[0-9]+";
}
