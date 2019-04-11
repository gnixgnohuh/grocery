package cache;

public class CacheBO {

    public CacheBO() {
    }

    public CacheBO(String username) {
        this.username = username;
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
