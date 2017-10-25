package com.schoolInfo.bartosz.schoolinfo.Rest;


public class MainInformationAboutUserAndClass {

    POJOClassInfo pojoClassInfo = new POJOClassInfo();
    UserBaseInformation UBI = new UserBaseInformation();
    int withScreenOnTop = 0;

    public int getWithScreenOnTop() {
        return withScreenOnTop;
    }

    public void setWithScreenOnTop(int withScreenOnTop) {
        this.withScreenOnTop = withScreenOnTop;
    }

    public POJOClassInfo getPojoClassInfo() {
        return pojoClassInfo;
    }

    public void setPojoClassInfo(POJOClassInfo pojoClassInfo) {
        this.pojoClassInfo = pojoClassInfo;
    }

    public UserBaseInformation getUBI() {
        return UBI;
    }

    public void setUBI(UserBaseInformation UBI) {
        this.UBI = UBI;
    }
}
