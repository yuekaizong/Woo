package kaizone.songmaya.haiercash.retrofit.beans;

/**
 * Created by yuelb on 2017/7/18.
 */

public class VersionCheck extends Entity{
    public String gh;
    public String lastVersion;
    public String isFix;
    public boolean isForcedUpdate;
    public String beginTime;
    public String endTime;
    public boolean hasNewer;
    public String titleImage;
    public String noticeContent;
    public String noticeTitle;

    public String getGh() {
        return gh;
    }

    public void setGh(String gh) {
        this.gh = gh;
    }

    public String getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(String lastVersion) {
        this.lastVersion = lastVersion;
    }

    public String getIsFix() {
        return isFix;
    }

    public void setIsFix(String isFix) {
        this.isFix = isFix;
    }

    public boolean isForcedUpdate() {
        return isForcedUpdate;
    }

    public void setForcedUpdate(boolean forcedUpdate) {
        isForcedUpdate = forcedUpdate;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isHasNewer() {
        return hasNewer;
    }

    public void setHasNewer(boolean hasNewer) {
        this.hasNewer = hasNewer;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }
}
