package de.edlly.bkkstundenplan.bkkstundenplan.model.data;


@SuppressWarnings("unused")
class Data implements Idata {
    public Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                '}';
    }
}
