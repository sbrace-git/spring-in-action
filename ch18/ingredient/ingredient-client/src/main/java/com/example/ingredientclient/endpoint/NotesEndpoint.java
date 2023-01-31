package com.example.ingredientclient.endpoint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Endpoint(id = "notes")
//@WebEndpoint(id = "notes")
//@JmxEndpoint(id = "notes")
public class NotesEndpoint {
    private final List<Note> notes = new ArrayList<>();

    @ReadOperation
    public List<Note> notes() {
        return notes;
    }

    @WriteOperation
    public List<Note> addNote(String text) {
        notes.add(new Note(text));
        return notes;
    }

    @DeleteOperation
    public List<Note> deleteNote(int index) {
        if (index < notes.size()) {
            notes.remove(index);
        }
        return notes;
    }


    @RequiredArgsConstructor
    private static class Note {
        @Getter
        private Date time = new Date();
        @Getter
        private final String text;
    }
}
