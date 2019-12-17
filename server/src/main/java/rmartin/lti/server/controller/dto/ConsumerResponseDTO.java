package rmartin.lti.server.controller.dto;

import rmartin.lti.server.model.Consumer;

public class ConsumerResponseDTO {
    private long id;
    private String name;

    public ConsumerResponseDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ConsumerResponseDTO(Consumer c) {
        this.id = c.getId();
        this.name = c.getUsername();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
