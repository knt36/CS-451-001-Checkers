package network;

/**
 * Created by mblaho on 8/16/16.
 *
 * POJO for Server acknowledgement.
 *
 * State is true or false,
 */
public class Acknowledgement {
    boolean failed = false;

    @Override
    public String toString() {
        return "Acknowledgement{" +
                "failed=" + failed +
                ", message='" + message + '\'' +
                '}';
    }

    String message = "";
}
