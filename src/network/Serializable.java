package network;

/**
 *
 */
public interface Serializable {
    public Serializable fromJson(String json);

    public String toJson();
}
