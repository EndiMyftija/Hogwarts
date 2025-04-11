package it.polito.cs.hogwartsartifactsonline.system;

public class StatusCode {

    public static int SUCCESS = 200; // Success

    public static int INVALID_ARGUMENT = 400; // Bad Request, e.g., invalid parameters

    public static int UNAUTHORIZED = 401; // Username or password incorrect

    public static int FORBIDDEN = 403; // No permission

    public static int NOT_FOUND = 404; //Not Found

    public static int INTERNAL_SERVER_ERROR = 500; // Server Internal Error
}
