package sk.dudas.appengine.viacnezsperk.domain;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 12.1.2013
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
@Entity(name = "galleryItem")
public class GalleryItem extends BaseEntity {

    private String gphotoId;
    private Integer index;
    private String imageUrl;
    private String thumbUrl;

    public GalleryItem() {
    }

    public GalleryItem(String gphotoId, String imageUrl, String thumbUrl) {
        this.gphotoId = gphotoId;
        this.imageUrl = imageUrl;
        this.thumbUrl = thumbUrl;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getGphotoId() {
        return gphotoId;
    }

    public void setGphotoId(String gphotoId) {
        this.gphotoId = gphotoId;
    }
}
