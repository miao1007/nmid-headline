package cn.edu.cqupt.nmid.headline.support.repository.update;

/**
 * Created by leon on 2/16/15.
 */
@Deprecated
public class UpdateResult {
  private int status;
  private String url;
  private byte force;
  private String version;

  public byte getForce() {
    return force;
  }

  public void setForce(byte force) {
    this.force = force;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
