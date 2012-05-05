package halo.web.taglib;

public class RadioAreaTag extends BaseContentTag {

    private static final long serialVersionUID = 1825365925472611360L;

    private Object name;

    private Object clazz;

    private Object checkedvalue;

    private boolean databefore;

    private Object forcecheckedvalue;

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getClazz() {
        return clazz;
    }

    public void setClazz(Object clazz) {
        this.clazz = clazz;
    }

    public Object getCheckedvalue() {
        return checkedvalue;
    }

    public void setCheckedvalue(Object checkedvalue) {
        this.checkedvalue = checkedvalue;
    }

    public boolean isDatabefore() {
        return databefore;
    }

    public void setDatabefore(boolean databefore) {
        this.databefore = databefore;
    }

    public Object getForcecheckedvalue() {
        return forcecheckedvalue;
    }

    public void setForcecheckedvalue(Object forcecheckedvalue) {
        this.forcecheckedvalue = forcecheckedvalue;
    }
}